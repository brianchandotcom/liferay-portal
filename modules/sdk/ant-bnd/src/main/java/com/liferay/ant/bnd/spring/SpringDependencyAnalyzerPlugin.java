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

package com.liferay.ant.bnd.spring;

import aQute.bnd.header.Attrs;
import aQute.bnd.header.Parameters;
import aQute.bnd.osgi.Analyzer;
import aQute.bnd.osgi.Annotation;
import aQute.bnd.osgi.ClassDataCollector;
import aQute.bnd.osgi.Clazz;
import aQute.bnd.osgi.Descriptors;
import aQute.bnd.osgi.Jar;
import aQute.bnd.osgi.Resource;
import aQute.bnd.osgi.WriteResource;
import aQute.bnd.service.AnalyzerPlugin;
import aQute.bnd.version.Version;
import aQute.bnd.version.VersionRange;

import aQute.lib.io.IO;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Miguel Pastor
 */
public class SpringDependencyAnalyzerPlugin implements AnalyzerPlugin {

	@Override
	public boolean analyzeJar(Analyzer analyzer) throws Exception {
		String property = analyzer.getProperty("-liferay-spring-dependency");

		Parameters parameters = analyzer.parseHeader(property);

		if (parameters.isEmpty()) {
			return false;
		}

		Jar jar = analyzer.getJar();

		Collection<Clazz> classes = analyzer.getClasses();

		Set<String> serviceReferences = new TreeSet<String>();

		for (String key : parameters.keySet()) {
			ServiceReferenceCollector serviceReferenceCollector =
				new ServiceReferenceCollector(key);

			for (Clazz clazz : classes) {
				clazz.parseClassFileWithCollector(serviceReferenceCollector);
			}

			serviceReferences.addAll(
				serviceReferenceCollector.getServiceReferences());
		}

		String releaseInfo = getReleaseInfo(analyzer);

		if (!releaseInfo.equals("")) {
			serviceReferences.add(releaseInfo);
		}

		jar.putResource(
			"OSGI-INF/context/context.dependencies",
			new ContextDependencyWriter(analyzer, serviceReferences));

		return false;
	}

	protected String getReleaseInfo(Analyzer analyzer) throws RuntimeException {
		String property = analyzer.getProperty("Liferay-Require-SchemaVersion");

		if (property == null) {
			return "";
		}

		VersionRange versionRange;

		if (Version.isVersion(property)) {
			Version version = Version.parseVersion(property);

			versionRange = new VersionRange(
				true, new Version(version.getMajor(), version.getMinor(), 0),
				new Version(version.getMajor(), version.getMinor() + 1, 0),
				false);
		}
		else if (VersionRange.isVersionRange(property)) {
			versionRange = VersionRange.parseVersionRange(property);
		}
		else {
			throw new RuntimeException(
				"Format for Liferay-Require-SchemaVersion is invalid. Use a " +
					"version with syntax <major>.<minor>.<micro>[.qualifier] " +
						"or a range of versions.");
		}

		String versionRangeFilter = versionRange.toFilter();

		StringBuilder sb = new StringBuilder();

		sb.append("com.liferay.portal.kernel.model.Release ");
		sb.append("(&(release.bundle.symbolic.name=");

		Map.Entry<String, Attrs> entry = analyzer.getBundleSymbolicName();

		sb.append(entry.getKey());

		sb.append(")");
		sb.append(
			versionRangeFilter.replaceAll("version", "release.schema.version"));
		sb.append("(|(!(release.state=*))(release.state=");
		sb.append(_STATE_GOOD);
		sb.append(")))");

		return sb.toString();
	}

	private static final int _STATE_GOOD = 0;

	private static class ContextDependencyWriter extends WriteResource {

		public ContextDependencyWriter(
			Analyzer analyzer, Set<String> serviceReferences) {

			_serviceReferences = serviceReferences;

			Jar jar = analyzer.getJar();

			_resource = jar.getResource("META-INF/spring/context.dependencies");
		}

		@Override
		public long lastModified() {
			return 0;
		}

		@Override
		public void write(OutputStream outputStream) throws Exception {
			PrintWriter printWriter = new PrintWriter(
				new OutputStreamWriter(outputStream, "UTF-8"));

			String contextDependencies = "";

			if (_resource != null) {
				contextDependencies = IO.collect(
					_resource.openInputStream(), "UTF-8");
			}

			if (!contextDependencies.equals("")) {
				printWriter.println(contextDependencies);
			}

			for (String serviceReference : _serviceReferences) {
				printWriter.println(serviceReference);
			}

			printWriter.flush();
		}

		private final Resource _resource;
		private final Set<String> _serviceReferences;

	}

	private static class ServiceReferenceCollector extends ClassDataCollector {

		public ServiceReferenceCollector(String annotationFQN) {
			_annotationFQN = annotationFQN;
		}

		@Override
		public void annotation(Annotation annotation) throws Exception {
			Descriptors.TypeRef typeRef = annotation.getName();

			String fqn = typeRef.getFQN();

			if (!_annotationFQN.equals(fqn)) {
				return;
			}

			Descriptors.Descriptor descriptor = _fieldDef.getDescriptor();

			typeRef = descriptor.getType();

			fqn = typeRef.getFQN();

			Object filterString = annotation.get("filterString");

			if (filterString != null) {
				fqn += " " + filterString;
			}

			_serviceReferences.add(fqn);
		}

		@Override
		public void field(Clazz.FieldDef fieldDef) {
			_fieldDef = fieldDef;
		}

		public Set<String> getServiceReferences() {
			return _serviceReferences;
		}

		private final String _annotationFQN;
		private Clazz.FieldDef _fieldDef;
		private final Set<String> _serviceReferences = new HashSet<String>();

	}

}