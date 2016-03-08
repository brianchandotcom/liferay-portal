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
import aQute.bnd.header.Parameters;
import aQute.bnd.osgi.Analyzer;
import aQute.bnd.osgi.Constants;
import aQute.bnd.osgi.Jar;
import aQute.bnd.osgi.Resource;
import aQute.bnd.service.AnalyzerPlugin;
import aQute.bnd.version.Version;

import java.util.Map;

/**
 * @author Andrea Di Giorgi
 */
public class ServiceAnalyzerPlugin implements AnalyzerPlugin {

	@Override
	public boolean analyzeJar(Analyzer analyzer) throws Exception {
		boolean liferayService = Boolean.parseBoolean(
			analyzer.getProperty("Liferay-Service"));

		if (!liferayService) {
			return false;
		}

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

	private static final String _LIFERAY_EXTENDER = "liferay.extender";

}