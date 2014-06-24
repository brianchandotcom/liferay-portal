/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.wab.extender.internal.processor;

import aQute.bnd.osgi.Analyzer;

import com.liferay.portal.events.GlobalStartupAction;
import com.liferay.portal.kernel.deploy.auto.AutoDeployException;
import com.liferay.portal.kernel.deploy.auto.AutoDeployListener;
import com.liferay.portal.kernel.deploy.auto.context.AutoDeploymentContext;
import com.liferay.portal.kernel.deploy.hot.DependencyManagementThreadLocal;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.plugin.PluginPackage;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.wab.extender.internal.introspection.ClassLoaderSource;
import com.liferay.portal.wab.extender.internal.introspection.Source;
import com.liferay.portal.wab.extender.internal.util.AntUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;

import java.net.URI;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.depend.DependencyVisitor;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;

/**
 * @author Raymond Augé
 * @author Miguel Pastor
 */
public class WabProcessor {

	public WabProcessor(
		BundleContext bundleContext, ClassLoader classLoader, File file,
		Map<String, String[]> parameters) {

		_bundleContext = bundleContext;
		_classLoader = classLoader;
		_file = file;
		_parameters = parameters;
	}

	public InputStream getInputStream() throws IOException {
		_pluginDir = autoDeploy();

		if ((_pluginDir == null) || !_pluginDir.exists() ||
			!_pluginDir.isDirectory()) {

			return null;
		}

		if (!isValidOSGiBundle()) {
			transformToOSGiBundle();
		}

		// TODO

		return null;
	}

	protected File autoDeploy() {
		String webContextpath = MapUtil.getString(
			_parameters, "Web-ContextPath");

		if (!webContextpath.startsWith(StringPool.SLASH)) {
			webContextpath = StringPool.SLASH.concat(webContextpath);
		}

		AutoDeploymentContext autoDeploymentContext =
			buildAutoDeploymentContext(webContextpath);

		executeAutoDeployers(autoDeploymentContext);

		PluginPackage pluginPackage = autoDeploymentContext.getPluginPackage();

		_context = pluginPackage.getContext();

		File deployDir = autoDeploymentContext.getDeployDir();

		if (!deployDir.exists()) {
			File parentFile = deployDir.getParentFile();

			File[] files = parentFile.listFiles(
				new FilenameFilter() {

					@Override
					public boolean accept(File dir, String name) {
						return name.endsWith(".war");
					}

				});

			if ((files == null) || (files.length == 0)) {
				_log.error("Unable to find any WARs in " + parentFile);

				return null;
			}

			File file = files[0];

			deployDir.mkdirs();

			AntUtil.expandFile(file, deployDir);
		}

		return deployDir;
	}

	protected AutoDeploymentContext buildAutoDeploymentContext(String context) {
		AutoDeploymentContext autoDeploymentContext =
			new AutoDeploymentContext();

		autoDeploymentContext.setContext(context);

		File file = new File(_file.getParentFile(), "deploy");

		file.mkdirs();

		autoDeploymentContext.setDestDir(file.getAbsolutePath());

		autoDeploymentContext.setFile(_file);

		return autoDeploymentContext;
	}

	protected void executeAutoDeployers(
		AutoDeploymentContext autoDeploymentContext) {

		boolean enabled = DependencyManagementThreadLocal.isEnabled();

		try {
			DependencyManagementThreadLocal.setEnabled(false);

			List<AutoDeployListener> autoDeployListeners =
				GlobalStartupAction.getAutoDeployListeners(false);

			for (AutoDeployListener autoDeployListener : autoDeployListeners) {
				try {
					autoDeployListener.deploy(autoDeploymentContext);
				}
				catch (AutoDeployException ade) {
					_log.error(ade, ade);
				}
			}
		}
		finally {
			DependencyManagementThreadLocal.setEnabled(enabled);
		}
	}

	protected String getFileName(String className) {
		return className.replace(CharPool.PERIOD, CharPool.SLASH) + ".class";
	}

	protected Manifest getManifest() throws IOException {
		File manifestFile = getManifestFile();

		Manifest manifest = new Manifest();

		InputStream inputStream = new FileInputStream(manifestFile);

		try {
			manifest.read(inputStream);
		}
		finally {
			inputStream.close();
		}

		return manifest;
	}

	protected File getManifestFile() throws IOException {
		if (_manifestFile != null) {
			return _manifestFile;
		}

		File manifestFile = new File(_pluginDir, "META-INF/MANIFEST.MF");

		if (!manifestFile.exists()) {
			FileUtil.mkdirs(manifestFile.getParent());

			manifestFile.createNewFile();
		}

		_manifestFile = manifestFile;

		return _manifestFile;
	}

	protected Properties getPluginPackageProperties() {
		File file = new File(
			_pluginDir, "WEB-INF/liferay-plugin-package.properties");

		if (!file.exists()) {
			return null;
		}

		try {
			return PropertiesUtil.load(FileUtil.read(file));
		}
		catch (IOException ioe) {
			return new Properties();
		}
	}

	protected boolean isValidOSGiBundle() {
		Manifest manifest = null;

		try {
			manifest = getManifest();
		}
		catch (IOException ioe) {
			return false;
		}

		Attributes attributes = manifest.getMainAttributes();

		String bundleSymbolicName = GetterUtil.getString(
			attributes.getValue(Constants.BUNDLE_SYMBOLICNAME));

		return Validator.isNotNull(bundleSymbolicName);
	}

	protected void processBundleClasspath(Analyzer analyzer)
		throws IOException {

		// Class path order is critical

		Map<String, File> classPath = new LinkedHashMap<String, File>();

		classPath.put(
			"ext/WEB-INF/classes", new File(_pluginDir, "ext/WEB-INF/classes"));
		classPath.put(
			"WEB-INF/classes", new File(_pluginDir, "WEB-INF/classes"));

		Properties pluginPackageProperties = getPluginPackageProperties();

		String[] portalDependencyJars = StringUtil.split(
			pluginPackageProperties.getProperty("portal-dependency-jars",
			StringPool.BLANK));

		processFiles(
			_pluginDir, _pluginDir.toURI(), classPath, portalDependencyJars);

		analyzer.setProperty(
			Constants.BUNDLE_CLASSPATH, StringUtil.merge(classPath.keySet()));

		Collection<File> files = classPath.values();

		analyzer.setClasspath(files.toArray(new File[classPath.size()]));
	}

	protected Set<String> processClass(
		Source source, DependencyVisitor dependencyVisitor, String className) {

		if (className.startsWith("java/")) {
			return Collections.emptySet();
		}

		InputStream inputStream = source.getResourceAsStream(className);

		if (inputStream == null) {
			return Collections.emptySet();
		}

		Set<String> packageNames = new HashSet<String>();

		try {
			ClassReader classReader = new ClassReader(inputStream);

			classReader.accept(dependencyVisitor, 0);

			for (String packageName : dependencyVisitor.getPackages()) {
				packageName = packageName.replaceAll(
					StringPool.SLASH, StringPool.PERIOD);

				if (packageName.startsWith("com.sun.") ||
					packageName.startsWith("sun.")) {

					continue;
				}

				packageNames.add(packageName);
			}

			String superName = classReader.getSuperName();

			if (superName != null) {
				packageNames.addAll(
					processReferencedDependencies(
						source, getFileName(superName)));
			}

			String[] interfaceNames = classReader.getInterfaces();

			if (ArrayUtil.isNotEmpty(interfaceNames)) {
				for (String interfaceName : interfaceNames) {
					packageNames.addAll(
						processReferencedDependencies(
							source, getFileName(interfaceName)));
				}
			}
		}
		catch (Exception e) {
			_log.error("Unable to process class " + className, e);
		}

		return packageNames;
	}

	protected void processFiles(
			File dir, URI uri, Map<String, File> classPath,
			String[] portalDependencyJars)
		throws IOException {

		for (File file : dir.listFiles()) {
			if (file.isDirectory()) {
				processFiles(file, uri, classPath, portalDependencyJars);

				continue;
			}

			uri = uri.relativize(file.toURI());

			String path = uri.getPath();

			if (ArrayUtil.contains(
					PropsValues.MODULE_FRAMEWORK_WEB_EXTENDER_EXCLUDED_PATHS,
					path)) {

				continue;
			}

			if (path.equals("WEB-INF/service.xml")) {
				processServicePackageName(file);
			}

			if (path.startsWith("WEB-INF/lib/")) {
				if (path.endsWith("-service.jar") &&
					!path.endsWith(_context.concat("-service.jar"))) {

					continue;
				}

				String jar = path.substring("WEB-INF/lib/".length());

				if (ArrayUtil.contains(portalDependencyJars, jar)) {
					_ignoredResources.add(path);

					continue;
				}

				classPath.put(path, file);
			}
			else if (path.endsWith(".jsp") || path.endsWith(".jspf")) {
				_importPackageNames.addAll(processJSPDependencies(file));
			}
		}
	}

	protected Set<String> processJSPDependencies(File file) throws IOException {
		Source source = new ClassLoaderSource(_classLoader);

		DependencyVisitor dependencyVisitor = new DependencyVisitor();

		String content = FileUtil.read(file);

		int contentX = -1;
		int contentY = content.length();

		Set<String> packageNames = new HashSet<String>();

		while (true) {
			contentX = content.lastIndexOf("<%@", contentY);

			if (contentX == -1) {
				break;
			}

			contentY = contentX;

			int importX = content.indexOf("import=\"", contentY);
			int importY = -1;

			if (importX != -1) {
				importX = importX + "import=\"".length();
				importY = content.indexOf("\"", importX);
			}

			if ((importX != -1) && (importY != -1)) {
				String s = content.substring(importX, importY);

				packageNames.addAll(
					processClass(source, dependencyVisitor, getFileName(s)));
			}

			contentY -= 3;
		}

		Set<String> globals = dependencyVisitor.getGlobals();

		for (String global : globals) {
			packageNames.add(
				global.replaceAll(StringPool.SLASH, StringPool.PERIOD));
		}

		return packageNames;
	}

	protected Set<String> processReferencedDependencies(
		Source source, String className) {

		DependencyVisitor dependencyVisitor = new DependencyVisitor();

		Set<String> packageNames = processClass(
			source, dependencyVisitor, className);

		Set<String> globals = dependencyVisitor.getGlobals();

		for (String global : globals) {
			packageNames.add(
				global.replaceAll(StringPool.SLASH, StringPool.PERIOD));
		}

		return packageNames;
	}

	protected void processServicePackageName(File file) {
		try {
			Document document = SAXReaderUtil.read(file);

			Element rootElement = document.getRootElement();

			_servicePackageName = rootElement.attributeValue("package-path");
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	protected void transformToOSGiBundle() throws IOException {
		Analyzer analyzer = new Analyzer();

		analyzer.setBase(_pluginDir);
		analyzer.setJar(_pluginDir);

		processBundleClasspath(analyzer);
	}

	private static Log _log = LogFactoryUtil.getLog(WabProcessor.class);

	private BundleContext _bundleContext;
	private ClassLoader _classLoader;
	private String _context;
	private File _file;
	private Set<String> _ignoredResources = new HashSet<String>();
	private Set<String> _importPackageNames = new HashSet<String>();
	private File _manifestFile;
	private Map<String, String[]> _parameters;
	private File _pluginDir;
	private String _servicePackageName;

}