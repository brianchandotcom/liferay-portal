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

package com.liferay.portal.osgi.web.wab.generator.internal.processor;

import aQute.bnd.component.DSAnnotations;
import aQute.bnd.make.metatype.MetatypePlugin;
import aQute.bnd.metatype.MetatypeAnnotations;
import aQute.bnd.osgi.Analyzer;
import aQute.bnd.osgi.Jar;
import aQute.bnd.osgi.JarResource;
import aQute.bnd.osgi.Resource;
import aQute.bnd.version.Version;

import com.liferay.petra.xml.XMLUtil;
import com.liferay.portal.events.GlobalStartupAction;
import com.liferay.portal.kernel.deploy.auto.AutoDeployException;
import com.liferay.portal.kernel.deploy.auto.AutoDeployListener;
import com.liferay.portal.kernel.deploy.auto.context.AutoDeploymentContext;
import com.liferay.portal.kernel.deploy.hot.DependencyManagementThreadLocal;
import com.liferay.portal.kernel.io.FileFilter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.plugin.PluginPackage;
import com.liferay.portal.kernel.servlet.PortalClassLoaderFilter;
import com.liferay.portal.kernel.servlet.PortalClassLoaderServlet;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.UnsecureSAXReaderUtil;
import com.liferay.portal.kernel.xml.XPath;
import com.liferay.portal.osgi.web.wab.generator.internal.introspection.ClassLoaderSource;
import com.liferay.portal.osgi.web.wab.generator.internal.introspection.Source;
import com.liferay.portal.osgi.web.wab.generator.internal.util.AntUtil;
import com.liferay.portal.osgi.web.wab.generator.internal.util.ManifestUtil;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.net.URI;

import java.text.Format;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.depend.DependencyVisitor;

import org.osgi.framework.Constants;

/**
 * @author Raymond Augé
 * @author Miguel Pastor
 */
public class WabProcessor {

	public WabProcessor(
		ClassLoader classLoader, File file, Map<String, String[]> parameters) {

		_classLoader = classLoader;
		_file = file;
		_parameters = parameters;
	}

	public File getProcessedFile() throws IOException {
		_pluginDir = autoDeploy();

		if ((_pluginDir == null) || !_pluginDir.exists() ||
			!_pluginDir.isDirectory()) {

			return null;
		}

		if (!isValidOSGiBundle()) {
			transformToOSGiBundle();
		}

		if (_file.isDirectory()) {
			return _file;
		}

		File file = _file.getParentFile();

		File outputFile = new File(file, "output.zip");

		try (JarOutputStream jarOutputStream = new JarOutputStream(
				new FileOutputStream(outputFile))) {

			writeJarPath(
				jarOutputStream, _ignoredResources, "META-INF/MANIFEST.MF",
				getManifestFile());

			writeJarPaths(
				jarOutputStream, _ignoredResources, _pluginDir,
				_pluginDir.toURI());
		}

		if (PropsValues.MODULE_FRAMEWORK_WEB_GENERATOR_GENERATED_WABS_STORE) {
			writeGeneratedWab(outputFile);
		}

		return outputFile;
	}

	protected void addElement(Element element, String name, String text) {
		Element childElement = element.addElement(name);

		childElement.addText(GetterUtil.getString(text));
	}

	protected void addWabLib(Analyzer analyzer, Jar wab, File file)
		throws IOException {

		if (!file.exists() || file.isDirectory()) {
			return;
		}

		Jar jar = new Jar(file);

		jar.setDoNotTouchManifest();

		analyzer.addClose(jar);

		String wabLibPath = "WEB-INF/lib/" + file.getName();

		wab.putResource(wabLibPath, new JarResource(jar));

		Manifest manifest = null;

		try {
			manifest = jar.getManifest();

			if (manifest == null) {
				if (_log.isDebugEnabled()) {
					_log.debug("No manifest found for " + jar.getName());
				}

				return;
			}

			Attributes attributes = manifest.getMainAttributes();

			String classPathHeader = attributes.getValue("Class-Path");

			if (Validator.isNull(classPathHeader)) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"No Class-Path header found for " + jar.getName());
				}

				return;
			}

			Collection<String> classPathHeaderParts = Analyzer.split(
				classPathHeader, ",");

			for (String classPathHeaderPart : classPathHeaderParts) {
				File childFile = Analyzer.getFile(
					file.getParentFile(), classPathHeaderPart);

				if (!childFile.exists() ||
					!childFile.getParentFile().equals(file.getParentFile())) {

					analyzer.warning(
						"Invalid Class-Path entry %s in %s must exist and " +
							"reside in same directory",
						childFile, file);
				}
				else {
					wabLibPath = "WEB-INF/lib/" + childFile.getName();

					appendProperty(
						analyzer, Constants.BUNDLE_CLASSPATH, wabLibPath);

					addWabLib(analyzer, wab, childFile);
				}
			}
		}
		catch (Exception e) {
			_log.error("Unable to load manifest for " + jar.getName(), e);
		}
	}

	protected void appendProperty(
		Analyzer analyzer, String property, String string) {

		analyzer.setProperty(
			property, Analyzer.append(analyzer.getProperty(property), string));
	}

	protected File autoDeploy() {
		String webContextpath = getWebContextPath();

		AutoDeploymentContext autoDeploymentContext =
			buildAutoDeploymentContext(webContextpath);

		executeAutoDeployers(autoDeploymentContext);

		_pluginPackage = autoDeploymentContext.getPluginPackage();

		if (_pluginPackage != null) {
			_context = _pluginPackage.getContext();
		}
		else {
			_context = autoDeploymentContext.getContext();
		}

		if (_file.isDirectory()) {
			return _file;
		}

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

			if (file.isDirectory()) {
				FileUtil.move(file, deployDir);
			}
			else {
				AntUtil.expandFile(file, deployDir);
			}
		}

		return deployDir;
	}

	protected AutoDeploymentContext buildAutoDeploymentContext(String context) {
		AutoDeploymentContext autoDeploymentContext =
			new AutoDeploymentContext();

		autoDeploymentContext.setContext(context);

		autoDeploymentContext.setFile(_file);

		if (_file.isDirectory()) {
			return autoDeploymentContext;
		}

		File file = new File(_file.getParentFile(), "deploy");

		file.mkdirs();

		autoDeploymentContext.setDestDir(file.getAbsolutePath());

		return autoDeploymentContext;
	}

	protected void copyOSGI_INFToWab(Analyzer analyzer) {
		Jar jar = analyzer.getJar();

		Map<String, Resource> resources = jar.getResources();

		for (Entry<String, Resource> entry : resources.entrySet()) {
			String path = entry.getKey();

			if (!path.startsWith("OSGI-INF/")) {
				continue;
			}

			Resource resource = entry.getValue();

			try {
				FileUtil.write(
					new File(_pluginDir, path), resource.openInputStream());
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
	}

	protected void executeAutoDeployers(
		AutoDeploymentContext autoDeploymentContext) {

		boolean enabled = DependencyManagementThreadLocal.isEnabled();

		try {
			DependencyManagementThreadLocal.setEnabled(false);

			List<AutoDeployListener> autoDeployListeners =
				GlobalStartupAction.getAutoDeployListeners(false);

			AutoDeployListener autoDeployListener = getAutoDeployListener(
				autoDeploymentContext, autoDeployListeners);

			autoDeployListener.deploy(autoDeploymentContext);
		}
		catch (AutoDeployException ade) {
			throw new RuntimeException(ade);
		}
		finally {
			DependencyManagementThreadLocal.setEnabled(enabled);
		}
	}

	protected void expandServiceJarIntoClassesDir(URI uri, File zipFile)
		throws IOException {

		URI webInfClasesURI = uri.resolve("WEB-INF/classes");

		try (ZipInputStream zipInputStream = new ZipInputStream(
				new FileInputStream(zipFile))) {

			for (ZipEntry zipEntry;
				(zipEntry = zipInputStream.getNextEntry()) != null;
				zipInputStream.closeEntry()) {

				if (zipEntry.isDirectory()) {
					File dir = new File(
						webInfClasesURI.getPath(), zipEntry.getName());

					dir.mkdirs();

					continue;
				}

				File file = new File(
					webInfClasesURI.getPath(), zipEntry.getName());

				File parentFile = file.getParentFile();

				parentFile.mkdirs();

				StreamUtil.transfer(
					StreamUtil.uncloseable(zipInputStream),
					new FileOutputStream(file));
			}
		}
	}

	protected void formatDocument(File file, Document document)
		throws IOException {

		try {
			FileUtil.write(file, XMLUtil.formatXML(document));
		}
		catch (Exception e) {
			throw new IOException(e);
		}
	}

	protected AutoDeployListener getAutoDeployListener(
		AutoDeploymentContext autoDeploymentContext,
		List<AutoDeployListener> autoDeployListeners) {

		List<AutoDeployListener> deployableAutoDeployListeners =
			new ArrayList<>();

		for (AutoDeployListener autoDeployListener : autoDeployListeners) {
			try {
				if (autoDeployListener.isDeployable(autoDeploymentContext)) {
					deployableAutoDeployListeners.add(autoDeployListener);
				}
			}
			catch (AutoDeployException ade) {
				throw new RuntimeException(ade);
			}
		}

		if (deployableAutoDeployListeners.size() > 1) {
			StringBundler sb = new StringBundler(
				3 + (deployableAutoDeployListeners.size() * 2) - 1);

			sb.append("More than one auto deploy listener is available for ");
			sb.append(autoDeploymentContext.getFile());
			sb.append(": ");

			for (int i = 0; i < deployableAutoDeployListeners.size(); i++) {
				AutoDeployListener deployableAutoDeployListener =
					deployableAutoDeployListeners.get(i);

				Class<?> clazz = deployableAutoDeployListener.getClass();

				if (i != 0) {
					sb.append(StringPool.COMMA_AND_SPACE);
				}

				sb.append(clazz.getName());
			}

			throw new RuntimeException(new AutoDeployException(sb.toString()));
		}

		return deployableAutoDeployListeners.get(0);
	}

	protected String getFileName(String className) {
		return className.replace(CharPool.PERIOD, CharPool.SLASH) + ".class";
	}

	protected Manifest getManifest() throws IOException {
		File manifestFile = getManifestFile();

		Manifest manifest = new Manifest();

		try (InputStream inputStream = new FileInputStream(manifestFile)) {
			manifest.read(inputStream);
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
			return new Properties();
		}

		try {
			return PropertiesUtil.load(FileUtil.read(file));
		}
		catch (IOException ioe) {
			return new Properties();
		}
	}

	protected String getVersionedServicePackageName(String partialPackageName) {
		return _servicePackageName + partialPackageName + ";version=" +
			_bundleVersion;
	}

	protected String getWebContextPath() {
		String webContextpath = MapUtil.getString(
			_parameters, "Web-ContextPath");

		if (!webContextpath.startsWith(StringPool.SLASH)) {
			webContextpath = StringPool.SLASH.concat(webContextpath);
		}

		return webContextpath;
	}

	protected boolean isValidOSGiBundle() {
		try {
			return ManifestUtil.isValidOSGiBundle(getManifest());
		}
		catch (IOException ioe) {
			return false;
		}
	}

	protected void processBundleClasspath(
			Analyzer analyzer, Properties pluginPackageProperties)
		throws IOException {

		// Class path order is critical

		Map<String, File> classPath = new LinkedHashMap<>();

		classPath.put(
			"ext/WEB-INF/classes", new File(_pluginDir, "ext/WEB-INF/classes"));
		classPath.put(
			"WEB-INF/classes", new File(_pluginDir, "WEB-INF/classes"));

		String[] portalDependencyJars = new String[0];

		if (pluginPackageProperties != null) {
			portalDependencyJars = StringUtil.split(
				pluginPackageProperties.getProperty(
					"portal-dependency-jars", StringPool.BLANK));
		}

		processFiles(
			_pluginDir, _pluginDir.toURI(), classPath, portalDependencyJars);

		Jar wab = analyzer.getJar();

		for (Entry<String, File> entry : classPath.entrySet()) {
			appendProperty(
				analyzer, Constants.BUNDLE_CLASSPATH, entry.getKey());

			addWabLib(analyzer, wab, entry.getValue());
		}

		Collection<File> files = classPath.values();

		analyzer.setClasspath(files.toArray(new File[classPath.size()]));
	}

	protected void processBundleManifestVersion(Analyzer analyzer) {
		String bundleManifestVersion = MapUtil.getString(
			_parameters, Constants.BUNDLE_MANIFESTVERSION);

		if (Validator.isNull(bundleManifestVersion)) {
			bundleManifestVersion = "2";
		}

		analyzer.setProperty(
			Constants.BUNDLE_MANIFESTVERSION, bundleManifestVersion);
	}

	protected void processBundleSymbolicName(Analyzer analyzer) {
		String bundleSymbolicName = MapUtil.getString(
			_parameters, Constants.BUNDLE_SYMBOLICNAME);

		if (Validator.isNull(bundleSymbolicName)) {
			bundleSymbolicName = _context.substring(1);
		}

		analyzer.setProperty(Constants.BUNDLE_SYMBOLICNAME, bundleSymbolicName);
	}

	protected void processBundleVersion(Analyzer analyzer) {
		_bundleVersion = MapUtil.getString(
			_parameters, Constants.BUNDLE_VERSION);

		if (Validator.isNull(_bundleVersion)) {
			if (_pluginPackage != null) {
				_bundleVersion = _pluginPackage.getVersion();
			}
			else {
				_bundleVersion = "1.0.0";
			}
		}

		if (!Version.isVersion(_bundleVersion)) {

			// Convert from the Maven format to the OSGi format

			Matcher matcher = _versionMavenPattern.matcher(_bundleVersion);

			if (matcher.matches()) {
				StringBuilder sb = new StringBuilder();

				sb.append(matcher.group(1));
				sb.append(".");
				sb.append(matcher.group(3));
				sb.append(".");
				sb.append(matcher.group(5));
				sb.append(".");
				sb.append(matcher.group(7));

				_bundleVersion = sb.toString();
			}
			else {
				_bundleVersion = "0.0.0." + _bundleVersion.replace(".", "_");
			}
		}

		analyzer.setProperty(Constants.BUNDLE_VERSION, _bundleVersion);
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

		Set<String> packageNames = new HashSet<>();

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

	protected void processDeclarativeReferences() throws IOException {
		processDefaultServletPackages();
		processTLDDependencies();

		processXMLDependencies(
			"WEB-INF/liferay-hook.xml",
			new String[] {
				"indexer-post-processor-impl", "service-impl",
				"servlet-filter-impl", "struts-action-impl"
			},
			null, null);

		processXMLDependencies(
			"WEB-INF/liferay-portlet.xml",
			new String[] {
				"asset-renderer-factory", "atom-collection-adapter",
				"configuration-action-class", "control-panel-entry-class",
				"custom-attributes-display", "friendly-url-mapper-class",
				"indexer-class", "open-search-class", "permission-propagator",
				"poller-processor-class", "pop-message-listener-class",
				"portlet-data-handler-class", "portlet-layout-listener-class",
				"portlet-url-class", "social-activity-interpreter-class",
				"social-request-interpreter-class", "url-encoder-class",
				"webdav-storage-class", "workflow-handler",
				"xml-rpc-method-class"
			},
			null, null);

		processXMLDependencies(
			"WEB-INF/portlet.xml",
			new String[] {
				"x:filter-class", "x:listener-class", "x:portlet-class",
				"x:resource-bundle"
			},
			"x", "http://java.sun.com/xml/ns/portlet/portlet-app_2_0.xsd");

		processXMLDependencies(
			"WEB-INF/web.xml",
			new String[] {
				"x:filter-class", "x:listener-class", "x:servlet-class"
			},
			"x", "http://java.sun.com/xml/ns/j2ee");
	}

	protected void processDefaultServletPackages() {
		for (String value :
				PropsValues.
					MODULE_FRAMEWORK_WEB_GENERATOR_DEFAULT_SERVLET_PACKAGES) {

			int index = value.indexOf(StringPool.SEMICOLON);

			if (index != -1) {
				value = value.substring(0, index);
			}

			_importPackageNames.add(value.trim());
		}
	}

	protected void processExportPackageNames(Analyzer analyzer) {
		analyzer.setProperty(
			Constants.EXPORT_PACKAGE,
			StringUtil.merge(_exportPackageNames.toArray()));
	}

	protected void processExtraHeaders(Analyzer analyzer) {
		String bundleSymbolicName = analyzer.getProperty(
			Constants.BUNDLE_SYMBOLICNAME);

		Properties properties = PropsUtil.getProperties(
			PropsKeys.MODULE_FRAMEWORK_WEB_GENERATOR_HEADERS, true);

		Enumeration<Object> keys = properties.keys();

		while (keys.hasMoreElements()) {
			String key = (String)keys.nextElement();

			String value = properties.getProperty(key);

			String processedKey = key;

			if (processedKey.endsWith(StringPool.CLOSE_BRACKET)) {
				String filterString =
					StringPool.OPEN_BRACKET + bundleSymbolicName +
						StringPool.CLOSE_BRACKET;

				if (!processedKey.endsWith(filterString)) {
					continue;
				}

				processedKey = processedKey.substring(
					0, processedKey.indexOf(StringPool.OPEN_BRACKET));
			}

			if (Validator.isNotNull(value)) {
				if (processedKey.equals(Constants.EXPORT_PACKAGE)) {
					Collections.addAll(
						_exportPackageNames, StringUtil.split(value));
				}
				else if (processedKey.equals(Constants.IMPORT_PACKAGE)) {
					Collections.addAll(
						_importPackageNames, StringUtil.split(value));
				}

				analyzer.setProperty(processedKey, value);
			}
		}
	}

	protected void processExtraRequirements() {
		_importPackageNames.add(
			"org.eclipse.core.runtime;x-liferay-compatibility:=spring");
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

			URI relativizedURI = uri.relativize(file.toURI());

			String path = relativizedURI.getPath();

			if (ArrayUtil.contains(
					PropsValues.MODULE_FRAMEWORK_WEB_GENERATOR_EXCLUDED_PATHS,
					path)) {

				continue;
			}

			if (path.equals("WEB-INF/service.xml")) {
				processServicePackageName(uri, file);
			}

			if (path.startsWith("WEB-INF/lib/")) {
				if (path.endsWith("-service.jar")) {
					if (path.endsWith(_context.concat("-service.jar"))) {
						expandServiceJarIntoClassesDir(uri, file);
					}

					// Ignore any other "-service.jar" so they use real imports

					_ignoredResources.add(path);

					continue;
				}

				String jar = path.substring("WEB-INF/lib/".length());

				if (ArrayUtil.contains(portalDependencyJars, jar)) {
					_ignoredResources.add(path);

					continue;
				}

				classPath.put(path, file);
			}
		}
	}

	protected void processImportPackageNames(Analyzer analyzer) {
		String packageName = MapUtil.getString(
			_parameters, Constants.IMPORT_PACKAGE);

		if (Validator.isNotNull(packageName)) {
			analyzer.setProperty(Constants.IMPORT_PACKAGE, packageName);
		}
		else {
			StringBundler sb = new StringBundler(
				(_importPackageNames.size() * 3) + 1);

			for (String importPackageName : _importPackageNames) {
				if (Validator.isNull(importPackageName)) {
					continue;
				}

				boolean containedInClasspath = false;

				for (Jar jar : analyzer.getClasspath()) {
					List<String> packages = jar.getPackages();

					if (packages.contains(importPackageName)) {
						containedInClasspath = true;

						break;
					}
				}

				if (containedInClasspath) {
					continue;
				}

				sb.append(importPackageName);
				sb.append(";resolution:=\"optional\"");
				sb.append(StringPool.COMMA);
			}

			sb.append("*;resolution:=\"optional\"");

			analyzer.setProperty(Constants.IMPORT_PACKAGE, sb.toString());
		}
	}

	protected void processLiferayPortletXML() throws IOException {
		File file = new File(_pluginDir, "WEB-INF/liferay-portlet.xml");

		if (!file.exists()) {
			return;
		}

		Document document = readDocument(file);

		Element rootElement = document.getRootElement();

		for (Element element : rootElement.elements("portlet")) {
			Element strutsPathElement = element.element("struts-path");

			if (strutsPathElement == null) {
				continue;
			}

			String strutsPath = strutsPathElement.getTextTrim();

			if (!strutsPath.startsWith(StringPool.SLASH)) {
				strutsPath = StringPool.SLASH.concat(strutsPath);
			}

			strutsPathElement.setText(
				Portal.PATH_MODULE.substring(1) + _context + strutsPath);
		}

		formatDocument(file, document);
	}

	protected void processPackageNames(Analyzer analyzer) {
		processExportPackageNames(analyzer);
		processImportPackageNames(analyzer);
	}

	protected void processPluginPackagePropertiesExportImportPackages(
		Properties pluginPackageProperties) {

		if (pluginPackageProperties == null) {
			return;
		}

		String exportPackage = pluginPackageProperties.getProperty(
			Constants.EXPORT_PACKAGE);

		if (Validator.isNotNull(exportPackage)) {
			Collections.addAll(
				_exportPackageNames, StringUtil.split(exportPackage));
		}

		String importPackage = pluginPackageProperties.getProperty(
			Constants.IMPORT_PACKAGE);

		if (Validator.isNotNull(importPackage)) {
			Collections.addAll(
				_importPackageNames, StringUtil.split(importPackage));
		}
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

	protected void processRequiredDeploymentContexts(Analyzer analyzer) {
		if (_pluginPackage == null) {
			return;
		}

		List<String> requiredDeploymentContexts =
			_pluginPackage.getRequiredDeploymentContexts();

		if (ListUtil.isEmpty(requiredDeploymentContexts)) {
			return;
		}

		StringBundler sb = new StringBundler(
			(6 * requiredDeploymentContexts.size()) - 1);

		for (int i = 0; i < requiredDeploymentContexts.size(); i++) {
			String requiredDeploymentContext = requiredDeploymentContexts.get(
				i);

			sb.append(requiredDeploymentContext);

			sb.append(StringPool.SEMICOLON);
			sb.append(Constants.BUNDLE_VERSION_ATTRIBUTE);
			sb.append(StringPool.EQUAL);
			sb.append(_bundleVersion);

			if ((i + 1) < requiredDeploymentContexts.size()) {
				sb.append(StringPool.COMMA);
			}
		}

		analyzer.setProperty(Constants.REQUIRE_BUNDLE, sb.toString());
	}

	protected void processServicePackageName(URI uri, File file) {
		try {
			Document document = UnsecureSAXReaderUtil.read(file);

			Element rootElement = document.getRootElement();

			_servicePackageName = rootElement.attributeValue("package-path");

			String[] partialPackageNames = {
				"", ".exception", ".model", ".model.impl", ".service",
				".service.base", ".service.http", ".service.impl",
				".service.persistence", ".service.persistence.impl"
			};

			for (String partialPackageName : partialPackageNames) {
				_exportPackageNames.add(
					getVersionedServicePackageName(partialPackageName));
				_importPackageNames.add(
					getVersionedServicePackageName(partialPackageName));
			}

			_importPackageNames.add(
				"com.liferay.portal.osgi.web.wab.generator");
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	protected void processTLDDependencies() throws IOException {
		File dir = new File(_pluginDir, "WEB-INF/tld");

		if (!dir.exists() || !dir.isDirectory()) {
			return;
		}

		File[] files = dir.listFiles(new FileFilter(".*\\.tld"));

		for (File file : files) {
			String content = FileUtil.read(file);

			DependencyVisitor dependencyVisitor = new DependencyVisitor();

			Matcher matcher = _tldPackagesPattern.matcher(content);

			while (matcher.find()) {
				String value = matcher.group(1);

				value = value.trim();

				processClass(
					new ClassLoaderSource(_classLoader), dependencyVisitor,
					getFileName(value));
			}

			for (String global : dependencyVisitor.getGlobals()) {
				_importPackageNames.add(
					global.replaceAll(StringPool.SLASH, StringPool.PERIOD));
			}
		}
	}

	protected void processWebContextPath(Manifest manifest) {
		Attributes attributes = manifest.getMainAttributes();

		attributes.putValue("Web-ContextPath", getWebContextPath());
	}

	protected void processWebXML(
		Element element, List<Element> initParamElements, Class<?> clazz) {

		if (element == null) {
			return;
		}

		String elementText = element.getTextTrim();

		if (!elementText.equals(clazz.getName())) {
			return;
		}

		for (Element initParamElement : initParamElements) {
			Element paramNameElement = initParamElement.element("param-name");

			String paramNameValue = paramNameElement.getTextTrim();

			if (!paramNameValue.equals(element.getName())) {
				continue;
			}

			Element paramValueElement = initParamElement.element("param-value");

			element.setText(paramValueElement.getTextTrim());

			initParamElement.detach();

			return;
		}
	}

	protected void processWebXML(String path) throws IOException {
		File file = new File(_pluginDir, path);

		if (!file.exists()) {
			return;
		}

		Document document = readDocument(file);

		Element rootElement = document.getRootElement();

		for (Element element : rootElement.elements("filter")) {
			Element filterClassElement = element.element("filter-class");

			processWebXML(
				filterClassElement, element.elements("init-param"),
				PortalClassLoaderFilter.class);
		}

		for (Element element : rootElement.elements("servlet")) {
			Element servletClassElement = element.element("servlet-class");

			processWebXML(
				servletClassElement, element.elements("init-param"),
				PortalClassLoaderServlet.class);
		}

		formatDocument(file, document);
	}

	protected void processXMLDependencies(
			String fileName, String[] xPathExpressions, String prefix,
			String namespace)
		throws IOException {

		File file = new File(_pluginDir, fileName);

		if (!file.exists()) {
			return;
		}

		Document document = readDocument(file);

		Element rootElement = document.getRootElement();

		DependencyVisitor dependencyVisitor = new DependencyVisitor();

		for (String xPathExpression : xPathExpressions) {
			XPath xPath = SAXReaderUtil.createXPath(
				"//" + xPathExpression, prefix, namespace);

			List<Node> nodes = xPath.selectNodes(rootElement);

			for (Node node : nodes) {
				String text = node.getText();

				text = text.trim();

				processClass(
					new ClassLoaderSource(_classLoader), dependencyVisitor,
					getFileName(text));
			}
		}

		for (String global : dependencyVisitor.getGlobals()) {
			_importPackageNames.add(
				global.replaceAll(StringPool.SLASH, StringPool.PERIOD));
		}
	}

	protected Document readDocument(File file) throws IOException {
		String content = FileUtil.read(file);

		try {
			return UnsecureSAXReaderUtil.read(content);
		}
		catch (DocumentException de) {
			throw new IOException(de);
		}
	}

	protected void transformToOSGiBundle() throws IOException {
		Analyzer analyzer = new Analyzer();

		analyzer.setBase(_pluginDir);
		analyzer.setJar(_pluginDir);
		analyzer.setProperty("-jsp", "*.jsp,*.jspf");
		analyzer.setProperty(
			"-plugin", "com.liferay.ant.bnd.jsp.JspAnalyzerPlugin");
		analyzer.setProperty("Web-ContextPath", getWebContextPath());

		Set<Object> plugins = analyzer.getPlugins();

		plugins.add(_dsAnnotations);

		plugins.add(_metatypePlugin);

		plugins.add(_metatypeAnnotations);

		Properties pluginPackageProperties = getPluginPackageProperties();

		processBundleVersion(analyzer);
		processBundleClasspath(analyzer, pluginPackageProperties);
		processBundleSymbolicName(analyzer);
		processExtraHeaders(analyzer);
		processPluginPackagePropertiesExportImportPackages(
			pluginPackageProperties);

		processBundleManifestVersion(analyzer);

		processLiferayPortletXML();
		processWebXML("WEB-INF/web.xml");
		processWebXML("WEB-INF/liferay-web.xml");

		processDeclarativeReferences();

		processExtraRequirements();

		processPackageNames(analyzer);

		processRequiredDeploymentContexts(analyzer);

		_processExcludedJSPs(analyzer);

		analyzer.setProperties(pluginPackageProperties);

		try {
			writeManifest(analyzer.calcManifest());

			copyOSGI_INFToWab(analyzer);
		}
		catch (Exception e) {
			throw new IOException("Unable to calculate the manifest", e);
		}
		finally {
			analyzer.close();
		}
	}

	protected void writeGeneratedWab(File file) throws IOException {
		File dir = new File(
			PropsValues.
				MODULE_FRAMEWORK_WEB_GENERATOR_GENERATED_WABS_STORE_DIR);

		dir.mkdirs();

		StringBundler sb = new StringBundler(5);

		String name = _file.getName();

		sb.append(name.substring(0, name.lastIndexOf(StringPool.PERIOD)));

		sb.append(StringPool.DASH);

		Format format = FastDateFormatFactoryUtil.getSimpleDateFormat(
			PropsValues.INDEX_DATE_FORMAT_PATTERN);

		sb.append(format.format(new Date()));

		sb.append(StringPool.PERIOD);
		sb.append(FileUtil.getExtension(name));

		FileUtil.copyFile(file, new File(dir, sb.toString()));
	}

	protected void writeJarPath(
			JarOutputStream jarOutputStream, Set<String> paths, String path,
			File file)
		throws FileNotFoundException {

		if (paths.contains(path)) {
			return;
		}

		paths.add(path);

		try {
			JarEntry jarEntry = new JarEntry(path);

			jarEntry.setTime(file.lastModified());

			jarOutputStream.putNextEntry(jarEntry);

			StreamUtil.transfer(
				new FileInputStream(file),
				StreamUtil.uncloseable(jarOutputStream));

			jarOutputStream.closeEntry();
		}
		catch (IOException ioe) {
			_log.error(ioe, ioe);
		}
	}

	protected void writeJarPaths(
			JarOutputStream jarOutputStream, Set<String> paths, File dir,
			URI uri)
		throws IOException {

		for (File file : dir.listFiles()) {
			URI relativizedURI = uri.relativize(file.toURI());

			String path = relativizedURI.getPath();

			if (file.isDirectory()) {
				jarOutputStream.putNextEntry(new ZipEntry(path));

				jarOutputStream.closeEntry();

				writeJarPaths(jarOutputStream, paths, file, uri);

				continue;
			}

			if (ArrayUtil.contains(
					PropsValues.MODULE_FRAMEWORK_WEB_GENERATOR_EXCLUDED_PATHS,
					path)) {

				continue;
			}

			if (path.startsWith("WEB-INF/lib/") &&
				path.endsWith("-service.jar") &&
				!path.endsWith(_context.concat("-service.jar"))) {

				continue;
			}

			writeJarPath(jarOutputStream, paths, path, file);
		}
	}

	protected void writeManifest(Manifest manifest) throws IOException {
		File file = getManifestFile();

		try (OutputStream outputStream = new FileOutputStream(file)) {
			manifest.write(outputStream);
		}
	}

	private void _processExcludedJSPs(Analyzer analyzer) throws IOException {
		File file = new File(_pluginDir, "/WEB-INF/liferay-hook.xml");

		if (!file.exists()) {
			return;
		}

		Document document = readDocument(file);

		Element rootElement = document.getRootElement();

		List<Node> nodes = rootElement.selectNodes("//custom-jsp-dir");

		String value = analyzer.getProperty("-jsp");

		for (Node node : nodes) {
			String text = node.getText();

			if (text.startsWith("/")) {
				text = text.substring(1);
			}

			value = "!" + text + "/*," + value;
		}

		analyzer.setProperty("-jsp", value);
	}

	private static final Log _log = LogFactoryUtil.getLog(WabProcessor.class);

	private static final DSAnnotations _dsAnnotations = new DSAnnotations();
	private static final MetatypeAnnotations _metatypeAnnotations =
		new MetatypeAnnotations();
	private static final MetatypePlugin _metatypePlugin = new MetatypePlugin();

	private String _bundleVersion;
	private final ClassLoader _classLoader;
	private String _context;
	private final Set<String> _exportPackageNames = new HashSet<>();
	private final File _file;
	private final Set<String> _ignoredResources = new HashSet<>();
	private final Set<String> _importPackageNames = new HashSet<>();
	private File _manifestFile;
	private final Map<String, String[]> _parameters;
	private File _pluginDir;
	private PluginPackage _pluginPackage;
	private String _servicePackageName;
	private final Pattern _tldPackagesPattern = Pattern.compile(
		"<[^>]+?-class>\\p{Space}*?(.*?)\\p{Space}*?</[^>]+?-class>");
	private final Pattern _versionMavenPattern = Pattern.compile(
		"(\\d{1,9})(\\.(\\d{1,9})(\\.(\\d{1,9})(-([-_\\da-zA-Z]+))?)?)?");

}