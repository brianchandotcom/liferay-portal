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

package com.liferay.ant.bnd.service;

import aQute.bnd.header.Attrs;
import aQute.bnd.header.OSGiHeader;
import aQute.bnd.header.Parameters;
import aQute.bnd.osgi.Analyzer;
import aQute.bnd.osgi.Constants;
import aQute.bnd.osgi.FileResource;
import aQute.bnd.osgi.Instruction;
import aQute.bnd.osgi.Instructions;
import aQute.bnd.osgi.Jar;
import aQute.bnd.osgi.Resource;
import aQute.bnd.service.AnalyzerPlugin;
import aQute.bnd.version.Version;

import java.io.File;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author Andrea Di Giorgi
 * @author Gregory Amerson
 */
public class ServiceAnalyzerPlugin implements AnalyzerPlugin {

	@Override
	public boolean analyzeJar(Analyzer analyzer) throws Exception {
		boolean liferayService = Boolean.parseBoolean(
			analyzer.getProperty("Liferay-Service"));

		if (!liferayService) {
			return false;
		}

		_processProvideCapability(analyzer);

		Jar portalSpringExtenderJar = getClasspathJar(
			analyzer, "com.liferay.portal.spring.extender");

		if (portalSpringExtenderJar == null) {
			return false;
		}

		processRequireCapability(analyzer, portalSpringExtenderJar);
		processSpringContext(analyzer);
		processSpringDependency(analyzer);

		return false;
	}

	protected Jar getClasspathJar(Analyzer analyzer, String bundleSymbolicName)
		throws Exception {

		for (Jar jar : analyzer.getClasspath()) {
			if (bundleSymbolicName.equals(jar.getBsn())) {
				return jar;
			}
		}

		return null;
	}

	protected void merge(Analyzer analyzer, String key, String value) {
		Parameters parameters = new Parameters(analyzer.getProperty(key));

		parameters.mergeWith(new Parameters(value), false);

		analyzer.setProperty(key, parameters.toString());
	}

	protected void processRequireCapability(
			Analyzer analyzer, Jar portalSpringExtenderJar)
		throws Exception {

		Parameters requireCapabilityHeaders = new Parameters(
			analyzer.getProperty(Constants.REQUIRE_CAPABILITY));

		Parameters parameters = new Parameters();

		Attrs attrs = new Attrs();

		Version portalSpringExtenderVersion = Version.parseVersion(
			portalSpringExtenderJar.getVersion());

		StringBuilder sb = new StringBuilder();

		sb.append("(&(");
		sb.append(_LIFERAY_EXTENDER);
		sb.append("=spring.extender)(version>=");
		sb.append(portalSpringExtenderVersion.getMajor());
		sb.append(".0)(!(version>=");
		sb.append(portalSpringExtenderVersion.getMajor() + 1);
		sb.append(".0)))");

		attrs.put(Constants.FILTER_DIRECTIVE, sb.toString());

		parameters.add(_LIFERAY_EXTENDER, attrs);

		requireCapabilityHeaders.mergeWith(parameters, false);

		analyzer.setProperty(
			Constants.REQUIRE_CAPABILITY, requireCapabilityHeaders.toString());
	}

	protected void processSpringContext(Analyzer analyzer) {
		Jar jar = analyzer.getJar();

		Map<String, Map<String, Resource>> directories = jar.getDirectories();

		if (!directories.containsKey("META-INF/spring")) {
			return;
		}

		merge(analyzer, "Liferay-Spring-Context", "META-INF/spring");
	}

	protected void processSpringDependency(Analyzer analyzer) {
		merge(
			analyzer, "-liferay-spring-dependency",
			"com.liferay.portal.spring.extender.service.ServiceReference");
	}

	private String _getAttrValue(Node node, String attrName) {
		NamedNodeMap attributes = node.getAttributes();

		Node item = attributes.getNamedItem(attrName);

		if (item != null) {
			return item.getNodeValue();
		}

		return null;
	}

	private boolean _isValidPackagePath(String packagePath) {
		if ((packagePath != null) && !packagePath.contains("@")) {
			return true;
		}

		return false;
	}

	private List<String> _listServiceClasses(File serviceXmlFile) {
		List<String> serviceClasses = new ArrayList<>();

		try {
			DocumentBuilderFactory factory =
				DocumentBuilderFactory.newInstance();

			DocumentBuilder documentBuilder = factory.newDocumentBuilder();

			Document document = documentBuilder.parse(serviceXmlFile);

			XPathFactory xPathfactory = XPathFactory.newInstance();

			XPath xpath = xPathfactory.newXPath();

			XPathExpression apiPackagePathExpression = xpath.compile(
				"/service-builder/@api-package-path");

			String packagePath = apiPackagePathExpression.evaluate(document);

			if ((packagePath == null) || packagePath.isEmpty()) {
				XPathExpression packagePathExpression = xpath.compile(
					"/service-builder/@package-path");

				packagePath = packagePathExpression.evaluate(document);
			}

			if (_isValidPackagePath(packagePath)) {
				XPathExpression entityExpression = xpath.compile("//entity");

				NodeList entities = (NodeList)entityExpression.evaluate(
					document, XPathConstants.NODESET);

				for (int i = 0; i < entities.getLength(); i++) {
					Node entity = entities.item(i);

					String name = _getAttrValue(entity, "name");
					String localService = _getAttrValue(
						entity, "local-service");
					String remoteService = _getAttrValue(
						entity, "remote-service");

					if ((localService == null) || "true".equals(localService)) {
						serviceClasses.add(
							packagePath + ".service." + name + "LocalService");
					}

					if ("true".equals(remoteService)) {
						serviceClasses.add(
							packagePath + ".service." + name + "Service");
					}
				}
			}
		}
		catch (Exception e) {
		}

		return serviceClasses;
	}

	private void _processProvideCapability(Analyzer analyzer) throws Exception {
		Parameters parameters = OSGiHeader.parseHeader(
			analyzer.getProperty("-liferay-service-xml"));

		if (parameters.isEmpty()) {
			return;
		}

		Instructions instructions = new Instructions(parameters);

		Jar jar = analyzer.getJar();

		Map<String, Resource> resources = jar.getResources();

		Set<String> keys = new HashSet<String>(resources.keySet());

		Set<File> serviceXmlFiles = new HashSet<>();

		for (String key : keys) {
			for (Instruction instruction : instructions.keySet()) {
				if (instruction.matches(key)) {
					Resource resource = resources.get(key);

					if (resource instanceof FileResource) {
						@SuppressWarnings("resource")
						FileResource fileResource = (FileResource)resource;

						File serviceXmlFile = fileResource.getFile();

						serviceXmlFiles.add(serviceXmlFile);
					}
					else {
						Path tempFile = Files.createTempFile("service", "xml");

						Files.copy(
							resource.openInputStream(), tempFile,
							StandardCopyOption.REPLACE_EXISTING);

						File tempServiceXmlFile = tempFile.toFile();

						serviceXmlFiles.add(tempServiceXmlFile);

						tempServiceXmlFile.deleteOnExit();
					}
				}
			}
		}

		if (serviceXmlFiles.isEmpty()) {
			analyzer.warning(
				"This bundle contains Liferay services but does not include " +
					"a service.xml in the final jar. No OSGi service " +
						"capabilities for these will be emitted.");

			return;
		}

		Stream<File> serviceXmls = serviceXmlFiles.stream();

		Set<String> serviceClasses = serviceXmls.flatMap(
			serviceXml -> _listServiceClasses(serviceXml).stream()
		).distinct(
		).collect(
			Collectors.toSet()
		);

		if (!serviceClasses.isEmpty()) {
			Parameters provideCapabilityHeaders = new Parameters(
				analyzer.getProperty(Constants.PROVIDE_CAPABILITY));

			for (String serviceClass : serviceClasses) {
				provideCapabilityHeaders.add(
					"osgi.service",
					Attrs.create("objectClass:List<String>", serviceClass));
			}

			analyzer.setProperty(
				Constants.PROVIDE_CAPABILITY,
				provideCapabilityHeaders.toString());
		}
	}

	private static final String _LIFERAY_EXTENDER = "liferay.extender";

}