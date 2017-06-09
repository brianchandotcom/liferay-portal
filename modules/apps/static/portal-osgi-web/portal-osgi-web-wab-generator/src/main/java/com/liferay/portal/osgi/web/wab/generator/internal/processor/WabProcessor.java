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

import aQute.bnd.header.Attrs;
import aQute.bnd.header.Parameters;
import aQute.bnd.osgi.Analyzer;
import aQute.bnd.osgi.Builder;
import aQute.bnd.osgi.Constants;
import aQute.bnd.osgi.Descriptors.PackageRef;
import aQute.bnd.osgi.FileResource;
import aQute.bnd.osgi.Jar;
import aQute.bnd.osgi.Packages;
import aQute.bnd.osgi.Resource;
import aQute.bnd.version.Version;

import com.liferay.ant.bnd.jsp.JspAnalyzerPlugin;
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
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.UnsecureSAXReaderUtil;
import com.liferay.portal.kernel.xml.XPath;
import com.liferay.portal.util.PropsValues;
import com.liferay.whip.util.ReflectionUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;

import java.nio.file.Files;
import java.nio.file.Path;

import java.text.Format;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * @author Raymond Augé
 * @author Miguel Pastor
 */
public class WabProcessor {

	public WabProcessor(
		ClassLoader classLoader, File file, Map<String, String[]> parameters) {

		_file = file;
		_parameters = parameters;
	}

	public File getProcessedFile() throws IOException {
		_pluginDir = autoDeploy();

		if ((_pluginDir == null) || !_pluginDir.exists() ||
			!_pluginDir.isDirectory()) {

			return null;
		}

		File outputFile = null;

		try (Jar jar = new Jar(_pluginDir)) {
			if (jar.getBsn() == null) {
				outputFile = transformToOSGiBundle(jar);
			}
		}
		catch (Exception e) {
			ReflectionUtil.throwException(e);
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
				try (Jar jar = new Jar(file)) {
					jar.expand(deployDir);
				}
				catch (Exception e) {
					ReflectionUtil.throwException(e);
				}
			}
		}

		if (_AUTODEPLOYED_WARS_STORE) {
			writeAutoDeployedWar(deployDir);
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

	protected void processBundleClasspath(
			Analyzer analyzer, Properties pluginPackageProperties)
		throws IOException {

		appendProperty(
			analyzer, Constants.BUNDLE_CLASSPATH, "ext/WEB-INF/classes");

		// Class path order is critical

		Map<String, File> classPath = new LinkedHashMap<>();

		classPath.put(
			"WEB-INF/classes", new File(_pluginDir, "WEB-INF/classes"));

		appendProperty(analyzer, Constants.BUNDLE_CLASSPATH, "WEB-INF/classes");

		processFiles(classPath, analyzer);

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

	protected void processClass(Analyzer analyzer, String value) {
		int index = value.lastIndexOf('.');

		if (index == -1) {
			return;
		}

		Packages packages = analyzer.getReferred();

		String packageName = value.substring(0, index);

		PackageRef packageRef = analyzer.getPackageRef(packageName);

		packages.put(packageRef, new Attrs());
	}

	protected void processDeclarativeReferences(Analyzer analyzer)
		throws IOException {

		processDefaultServletPackages();
		processTLDDependencies(analyzer);

		Path pluginPath = _pluginDir.toPath();

		processXMLDependencies(
			analyzer, "WEB-INF/liferay-hook.xml", _XPATHS_HOOK);
		processXMLDependencies(
			analyzer, "WEB-INF/liferay-portlet.xml", _XPATHS_LIFERAY);
		processXMLDependencies(
			analyzer, "WEB-INF/portlet.xml", _XPATHS_PORTLET);
		processXMLDependencies(analyzer, "WEB-INF/web.xml", _XPATHS_JAVAEE);

		Path classes = pluginPath.resolve("WEB-INF/classes/");

		processPropertiesDependencies(
			analyzer, classes, ".properties", _KNOWN_PROPERTY_KEYS);
		processXMLDependencies(analyzer, classes, ".xml", _XPATHS_HBM);
		processXMLDependencies(analyzer, classes, ".xml", _XPATHS_SPRING);
	}

	protected void processDefaultServletPackages() {
		for (String value :
				PropsValues.
					MODULE_FRAMEWORK_WEB_GENERATOR_DEFAULT_SERVLET_PACKAGES) {

			Parameters defaultPackage = new Parameters(value);

			for (String packageName : defaultPackage.keySet()) {
				if (_importPackageParameters.containsKey(packageName)) {
					continue;
				}

				_importPackageParameters.add(packageName, _optionalAttrs);
			}
		}
	}

	protected void processExportPackageNames(Analyzer analyzer) {
		analyzer.setProperty(
			Constants.EXPORT_CONTENTS, _exportPackageParameters.toString());
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
				Parameters parameters = new Parameters(value);

				if (processedKey.equals(Constants.EXPORT_PACKAGE)) {
					_exportPackageParameters.mergeWith(parameters, true);
				}
				else if (processedKey.equals(Constants.IMPORT_PACKAGE)) {
					_importPackageParameters.mergeWith(parameters, true);
				}

				analyzer.setProperty(processedKey, parameters.toString());
			}
		}
	}

	protected void processExtraRequirements() {
		Attrs attrs = new Attrs(_optionalAttrs);

		attrs.put("x-liferay-compatibility:", "spring");

		_importPackageParameters.add("org.eclipse.core.runtime", attrs);

		_importPackageParameters.add("!junit.*", new Attrs());
	}

	protected void processFiles(Map<String, File> classPath, Analyzer analyzer)
		throws IOException {

		Jar jar = analyzer.getJar();

		Map<String, Resource> resources = jar.getResources();

		Set<Entry<String, Resource>> entrySet = resources.entrySet();

		Iterator<Entry<String, Resource>> iterator = entrySet.iterator();

		while (iterator.hasNext()) {
			Entry<String, Resource> entry = iterator.next();

			String path = entry.getKey();
			Resource resource = entry.getValue();

			if (path.equals("WEB-INF/service.xml")) {
				processServicePackageName(entry.getValue());
			}
			else if (path.startsWith("WEB-INF/lib/")) {

				// Remove any other "-service.jar" or ignored jar so that real
				// imports are used

				if ((path.endsWith("-service.jar") &&
					 !path.endsWith(_context.concat("-service.jar"))) ||
					_ignoredResourcePaths.contains(path)) {

					iterator.remove();

					continue;
				}

				if (resource instanceof FileResource) {
					try (FileResource fileResource = (FileResource)resource) {
						classPath.put(path, fileResource.getFile());

						appendProperty(
							analyzer, Constants.BUNDLE_CLASSPATH, path);
					}
				}
			}
			else if (_ignoredResourcePaths.contains(path)) {
				iterator.remove();
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
				(_importPackageParameters.size() * 4) + 1);

			for (Entry<String, Attrs> entry :
					_importPackageParameters.entrySet()) {

				String importPackageName = entry.getKey();
				Attrs attrs = entry.getValue();

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

				if (!attrs.isEmpty()) {
					sb.append(";");
					sb.append(entry.getValue());
				}

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
			Parameters parameters = new Parameters(exportPackage);

			_exportPackageParameters.mergeWith(parameters, true);

			pluginPackageProperties.remove(Constants.EXPORT_PACKAGE);
		}

		String importPackage = pluginPackageProperties.getProperty(
			Constants.IMPORT_PACKAGE);

		if (Validator.isNotNull(importPackage)) {
			Parameters parameters = new Parameters(importPackage);

			_importPackageParameters.mergeWith(parameters, true);

			pluginPackageProperties.remove(Constants.IMPORT_PACKAGE);
		}
	}

	protected void processPropertiesDependencies(
		Analyzer analyzer, File file, String[] knownPropertyKeys) {

		if (!file.exists()) {
			return;
		}

		try (InputStream inputStream = new FileInputStream(file)) {
			Properties properties = new Properties();

			properties.load(inputStream);

			if (properties.isEmpty()) {
				return;
			}

			for (String key : knownPropertyKeys) {
				String value = properties.getProperty(key);

				if (value == null) {
					continue;
				}

				value = value.trim();

				processClass(analyzer, value);
			}
		}
		catch (Exception e) {

			// Ignore this case

		}
	}

	protected void processPropertiesDependencies(
			Analyzer analyzer, Path path, String suffix,
			String[] knownPropertyKeys)
		throws IOException {

		File file = path.toFile();

		if (!file.isDirectory()) {
			return;
		}

		Stream<Path> pathStream = Files.walk(path);

		Stream<File> fileStream = pathStream.map(Path::toFile);

		fileStream.forEach(
			entry -> {
				String pathString = entry.getPath();

				if (pathString.endsWith(suffix)) {
					processPropertiesDependencies(
						analyzer, entry, knownPropertyKeys);
				}
			});
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

	protected void processServicePackageName(Resource resource) {
		try (InputStream inputStream = resource.openInputStream()) {
			Document document = UnsecureSAXReaderUtil.read(inputStream);

			Element rootElement = document.getRootElement();

			_servicePackageName = rootElement.attributeValue("package-path");

			String[] partialPackageNames = {
				"", ".exception", ".model", ".model.impl", ".service",
				".service.base", ".service.http", ".service.impl",
				".service.persistence", ".service.persistence.impl"
			};

			for (String partialPackageName : partialPackageNames) {
				Parameters parameters = new Parameters(
					getVersionedServicePackageName(partialPackageName));

				_exportPackageParameters.mergeWith(parameters, false);
				_importPackageParameters.mergeWith(parameters, false);
			}

			_importPackageParameters.add(
				"com.liferay.portal.osgi.web.wab.generator", _optionalAttrs);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	protected void processTLDDependencies(Analyzer analyzer)
		throws IOException {

		File dir = new File(_pluginDir, "WEB-INF/tld");

		if (!dir.exists() || !dir.isDirectory()) {
			return;
		}

		File[] files = dir.listFiles(new FileFilter(".*\\.tld"));

		for (File file : files) {
			String content = FileUtil.read(file);

			Matcher matcher = _tldPackagesPattern.matcher(content);

			while (matcher.find()) {
				String value = matcher.group(1);

				value = value.trim();

				processClass(analyzer, value);
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
		Analyzer analyzer, File file, String xPathExpression) {

		if (!file.exists()) {
			return;
		}

		Document document = readDocument(file);

		if (!document.hasContent()) {
			return;
		}

		Element rootElement = document.getRootElement();

		XPath xPath = SAXReaderUtil.createXPath(xPathExpression, _xsds);

		List<Node> nodes = xPath.selectNodes(rootElement);

		for (Node node : nodes) {
			String text = node.getText();

			text = text.trim();

			processClass(analyzer, text);
		}
	}

	protected void processXMLDependencies(
			Analyzer analyzer, Path path, String suffix, String xPathExpression)
		throws IOException {

		File file = path.toFile();

		if (!file.isDirectory()) {
			return;
		}

		Stream<Path> pathStream = Files.walk(path);

		Stream<File> fileStream = pathStream.map(Path::toFile);

		fileStream.forEach(
			entry -> {
				String pathString = entry.getPath();

				if (pathString.endsWith(suffix)) {
					processXMLDependencies(analyzer, entry, _XPATHS_SPRING);
				}
			});
	}

	protected void processXMLDependencies(
		Analyzer analyzer, String fileName, String xPathExpression) {

		File file = new File(_pluginDir, fileName);

		processXMLDependencies(analyzer, file, xPathExpression);
	}

	protected Document readDocument(File file) {
		try {
			String content = FileUtil.read(file);

			return UnsecureSAXReaderUtil.read(content);
		}
		catch (Exception de) {
			return SAXReaderUtil.createDocument();
		}
	}

	protected File transformToOSGiBundle(Jar jar) throws IOException {
		Builder analyzer = new Builder();

		analyzer.setBase(_pluginDir);
		analyzer.setJar(jar);
		analyzer.setProperty("-jsp", "*.jsp,*.jspf");
		analyzer.setProperty("Web-ContextPath", getWebContextPath());

		Set<Object> plugins = analyzer.getPlugins();

		plugins.add(new JspAnalyzerPlugin());

		Properties pluginPackageProperties = getPluginPackageProperties();

		if (pluginPackageProperties.containsKey("portal-dependency-jars") &&
			_log.isWarnEnabled()) {

			_log.warn(
				"The property \"portal-dependency-jars\" is deprecated. " +
					"Specified JARs may not be included in the class path.");
		}

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

		processDeclarativeReferences(analyzer);

		processExtraRequirements();

		processPackageNames(analyzer);

		processRequiredDeploymentContexts(analyzer);

		_processExcludedJSPs(analyzer);

		analyzer.setProperties(pluginPackageProperties);

		try {
			jar = analyzer.build();

			File outputFile = analyzer.getOutputFile(null);

			jar.write(outputFile);

			return outputFile;
		}
		catch (Exception e) {
			throw new IOException("Unable to calculate the manifest", e);
		}
		finally {
			analyzer.close();
		}
	}

	protected void writeAutoDeployedWar(File pluginDir) {
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

		sb.append(".autodeployed.");

		sb.append(FileUtil.getExtension(name));

		try (Jar jar = new Jar(pluginDir)) {
			jar.write(new File(dir, sb.toString()));
		}
		catch (Exception e) {
			_log.error("Unable to write JAR file for " + pluginDir, e);
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

		sb.append(".wab.");

		sb.append(FileUtil.getExtension(name));

		FileUtil.copyFile(file, new File(dir, sb.toString()));
	}

	private void _processExcludedJSPs(Analyzer analyzer) {
		File file = new File(_pluginDir, "/WEB-INF/liferay-hook.xml");

		if (!file.exists()) {
			return;
		}

		Document document = readDocument(file);

		if (!document.hasContent()) {
			return;
		}

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

	/**
	 * Used diagnostic testing.
	 */
	private static final boolean _AUTODEPLOYED_WARS_STORE =
		GetterUtil.getBoolean(
			PropsUtil.get(
				"module.framework.web.generator.autodeployed.wars.store"));

	private static final String[] _KNOWN_PROPERTY_KEYS =
		{"jdbc.driverClassName"};

	private static final String _XPATHS_HBM = StringUtil.merge(
		new String[] {
			"//class/@name", "//id/@access", "//import/@class",
			"//property/@type"
		},
		"|");

	private static final String _XPATHS_HOOK = StringUtil.merge(
		new String[] {
			"//indexer-post-processor-impl", "//service-impl",
			"//servlet-filter-impl", "//struts-action-impl"
		},
		"|");

	private static final String _XPATHS_JAVAEE = StringUtil.merge(
		new String[] {
			"//j2ee:filter-class", "//j2ee:listener-class",
			"//j2ee:servlet-class", "//javaee:filter-class",
			"//javaee:listener-class", "//javaee:servlet-class"
		},
		"|");

	private static final String _XPATHS_LIFERAY = StringUtil.merge(
		new String[] {
			"//asset-renderer-factory", "//atom-collection-adapter",
			"//configuration-action-class", "//control-panel-entry-class",
			"//custom-attributes-display", "//friendly-url-mapper-class",
			"//indexer-class", "//open-search-class", "//permission-propagator",
			"//poller-processor-class", "//pop-message-listener-class",
			"//portlet-data-handler-class", "//portlet-layout-listener-class",
			"//portlet-url-class", "//social-activity-interpreter-class",
			"//social-request-interpreter-class", "//url-encoder-class",
			"//webdav-storage-class", "//workflow-handler",
			"//xml-rpc-method-class"
		},
		"|");

	private static final String _XPATHS_PORTLET = StringUtil.merge(
		new String[] {
			"//portlet2:filter-class", "//portlet2:listener-class",
			"//portlet2:portlet-class", "//portlet2:resource-bundle"
		},
		"|");

	private static final String _XPATHS_SPRING = StringUtil.merge(
		new String[] {
			"//beans:bean/@class", "//beans:*/@value-type",
			"//aop:*/@implement-interface", "//aop:*/@default-impl",
			"//context:load-time-weaver/@weaver-class",
			"//jee:jndi-lookup/@expected-type",
			"//jee:jndi-lookup/@proxy-interface", "//jee:remote-slsb/@ejbType",
			"//jee:*/@business-interface", "//lang:*/@script-interfaces",
			"//osgi:*/@interface", "//gemini-blueprint:*/@interface",
			"//blueprint:*/@interface", "//blueprint:*/@class",
			"//util:list/@list-class", "//util:set/@set-class",
			"//util:map/@map-class", "//webflow-config:*/@class"
		},
		"|");

	private static final Log _log = LogFactoryUtil.getLog(WabProcessor.class);

	private static final Attrs _optionalAttrs = new Attrs();
	private static final Map<String, String> _xsds = new ConcurrentHashMap<>();

	static {
		_optionalAttrs.put("resolution:", "optional");

		_xsds.put("aop", "http://www.springframework.org/schema/aop");
		_xsds.put("beans", "http://www.springframework.org/schema/beans");
		_xsds.put("blueprint", "http://www.osgi.org/xmlns/blueprint/v1.0.0");
		_xsds.put("context", "http://www.springframework.org/schema/context");
		_xsds.put(
			"gemini-blueprint",
			"http://www.eclipse.org/gemini/blueprint/schema/blueprint");
		_xsds.put("j2ee", "http://java.sun.com/xml/ns/j2ee");
		_xsds.put("javaee", "http://java.sun.com/xml/ns/javaee");
		_xsds.put("jee", "http://www.springframework.org/schema/jee");
		_xsds.put("jms", "http://www.springframework.org/schema/jms");
		_xsds.put("lang", "http://www.springframework.org/schema/lang");
		_xsds.put("osgi", "http://www.springframework.org/schema/osgi");
		_xsds.put(
			"osgi-compendium",
			"http://www.springframework.org/schema/osgi-compendium");
		_xsds.put(
			"portlet2",
			"http://java.sun.com/xml/ns/portlet/portlet-app_2_0.xsd");
		_xsds.put("tool", "http://www.springframework.org/schema/tool");
		_xsds.put("tx", "http://www.springframework.org/schema/tx");
		_xsds.put("util", "http://www.springframework.org/schema/util");
		_xsds.put(
			"webflow-config",
			"http://www.springframework.org/schema/webflow-config");
		_xsds.put("xsl", "http://www.w3.org/1999/XSL/Transform");
	}

	private String _bundleVersion;
	private String _context;
	private final Parameters _exportPackageParameters = new Parameters();
	private final File _file;
	private final Set<String> _ignoredResourcePaths = SetUtil.fromArray(
		PropsValues.MODULE_FRAMEWORK_WEB_GENERATOR_EXCLUDED_PATHS);
	private final Parameters _importPackageParameters = new Parameters();
	private final Map<String, String[]> _parameters;
	private File _pluginDir;
	private PluginPackage _pluginPackage;
	private String _servicePackageName;
	private final Pattern _tldPackagesPattern = Pattern.compile(
		"<[^>]+?-class>\\p{Space}*?(.*?)\\p{Space}*?</[^>]+?-class>");
	private final Pattern _versionMavenPattern = Pattern.compile(
		"(\\d{1,9})(\\.(\\d{1,9})(\\.(\\d{1,9})(-([-_\\da-zA-Z]+))?)?)?");

}