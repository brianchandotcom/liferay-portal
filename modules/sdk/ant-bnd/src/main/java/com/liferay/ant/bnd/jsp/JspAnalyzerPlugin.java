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

package com.liferay.ant.bnd.jsp;

import aQute.bnd.header.Attrs;
import aQute.bnd.header.OSGiHeader;
import aQute.bnd.header.Parameters;
import aQute.bnd.osgi.Analyzer;
import aQute.bnd.osgi.Clazz;
import aQute.bnd.osgi.Constants;
import aQute.bnd.osgi.Descriptors;
import aQute.bnd.osgi.Descriptors.PackageRef;
import aQute.bnd.osgi.Domain;
import aQute.bnd.osgi.Instruction;
import aQute.bnd.osgi.Instructions;
import aQute.bnd.osgi.Jar;
import aQute.bnd.osgi.Packages;
import aQute.bnd.osgi.Resource;
import aQute.bnd.service.AnalyzerPlugin;

import aQute.lib.io.IO;
import aQute.lib.strings.Strings;

import java.io.InputStream;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import java.util.jar.Manifest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Raymond Augé
 */
public class JspAnalyzerPlugin implements AnalyzerPlugin {

	@Override
	public boolean analyzeJar(Analyzer analyzer) throws Exception {
		addManifestPackageImports(analyzer);

		Parameters parameters = OSGiHeader.parseHeader(
			analyzer.getProperty("-jsp"));

		if (parameters.isEmpty()) {
			return false;
		}

		Instructions instructions = new Instructions(parameters);
		boolean matches = false;

		Jar jar = analyzer.getJar();

		Map<String, Resource> resources = jar.getResources();

		Set<String> keys = new HashSet<String>(resources.keySet());

		for (String key : keys) {
			for (Instruction instruction : instructions.keySet()) {
				if (instruction.matches(key)) {
					if (instruction.isNegated()) {
						break;
					}

					Resource resource = jar.getResource(key);

					String jsp = IO.collect(
						resource.openInputStream(), "UTF-8");

					addApiUses(analyzer, jsp);
					addTaglibRequirements(analyzer, jsp);

					matches = true;
				}
			}
		}

		if (matches) {
			addRequiredPackageImports(analyzer, _REQUIRED_PACKAGE_NAMES);
		}

		return false;
	}

	protected void addApiUses(Analyzer analyzer, String content) {
		int contentX = -1;
		int contentY = content.length();

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
				String contentFragment = content.substring(importX, importY);

				int index = contentFragment.lastIndexOf('.');

				if (index != -1) {
					Packages packages = analyzer.getReferred();

					String packageName = contentFragment.substring(0, index);

					PackageRef packageRef = analyzer.getPackageRef(packageName);

					packages.put(packageRef, new Attrs());

					addApiUses(analyzer, contentFragment, packageRef);
				}
			}

			contentY -= 3;
		}
	}

	protected void addApiUses(
		Analyzer analyzer, String content, PackageRef packageRef) {

		for (Jar jar : analyzer.getClasspath()) {
			addJarApiUses(analyzer, content, packageRef, jar);
		}
	}

	protected void addJarApiUses(
		Analyzer analyzer, String content, PackageRef packageRef, Jar jar) {

		Map<String, Map<String, Resource>> directories = jar.getDirectories();

		Map<String, Resource> resourceMap = directories.get(
			packageRef.getPath());

		if ((resourceMap == null) || resourceMap.isEmpty()) {
			return;
		}

		if (content.endsWith("*")) {
			for (Entry<String, Resource> entry : resourceMap.entrySet()) {
				String key = entry.getKey();

				if (!key.endsWith(".class")) {
					continue;
				}

				addResourceApiUses(analyzer, key, entry.getValue());
			}
		}
		else {
			String fqnToPath = Descriptors.fqnToPath(content);

			if (resourceMap.containsKey(fqnToPath)) {
				Resource resource = resourceMap.get(fqnToPath);

				addResourceApiUses(analyzer, content, resource);
			}
		}
	}

	protected void addManifestPackageImports(Analyzer analyzer) {
		Packages packages = analyzer.getClasspathExports();

		for (Jar jar : analyzer.getClasspath()) {
			try {
				Manifest manifest = jar.getManifest();

				if (manifest == null) {
					continue;
				}

				Domain domain = Domain.domain(manifest);

				Parameters parameters = domain.getExportPackage();

				for (Entry<String, Attrs> entry : parameters.entrySet()) {
					PackageRef packageRef = analyzer.getPackageRef(
						entry.getKey());

					Attrs attrs = packages.get(packageRef);

					if (attrs.isEmpty()) {
						packages.put(packageRef, entry.getValue());
					}
				}
			}
			catch (Exception e) {
			}
		}
	}

	protected void addRequiredPackageImports(
		Analyzer analyzer, String[] packageNames) {

		Packages packages = analyzer.getReferred();

		for (String packageName : packageNames) {
			PackageRef packageRef = analyzer.getPackageRef(packageName);

			Matcher matcher = _packagePattern.matcher(packageRef.getFQN());

			if (matcher.matches() && !packages.containsKey(packageRef)) {
				packages.put(packageRef, new Attrs());
			}
		}
	}

	protected void addResourceApiUses(
		Analyzer analyzer, String fqnToPath, Resource resource) {

		Clazz clazz = null;

		try {
			InputStream inputStream = resource.openInputStream();

			clazz = new Clazz(analyzer, fqnToPath, resource);

			try {
				clazz.parseClassFile();
			}
			finally {
				inputStream.close();
			}
		}
		catch (Throwable e) {
			return;
		}

		Set<PackageRef> packageRefs = clazz.getAPIUses();

		for (PackageRef packageRef : packageRefs) {
			Packages packages = analyzer.getReferred();

			packages.put(packageRef, new Attrs());
		}
	}

	protected void addTaglibRequirement(
		Set<String> taglibRequirements, String uri) {

		Parameters parameters = new Parameters();

		Attrs attrs = new Attrs();

		attrs.put(
			Constants.FILTER_DIRECTIVE,
			"\"(&(osgi.extender=jsp.taglib)(uri=" + uri + "))\"");

		parameters.put("osgi.extender", attrs);

		taglibRequirements.add(parameters.toString());
	}

	protected void addTaglibRequirements(Analyzer analyzer, String content) {
		Set<String> taglibRequirements = new TreeSet<String>();

		for (String uri : getTaglibURIs(content)) {
			if (Arrays.binarySearch(_JSTL_CORE_URIS, uri) < 0) {
				addTaglibRequirement(taglibRequirements, uri);
			}
		}

		if (taglibRequirements.isEmpty()) {
			return;
		}

		String value = analyzer.getProperty(Constants.REQUIRE_CAPABILITY);

		if (value != null) {
			Parameters parameters = OSGiHeader.parseHeader(value);

			for (Entry<String, Attrs> entry : parameters.entrySet()) {
				StringBuilder sb = new StringBuilder(entry.getKey());

				Attrs attrs = entry.getValue();

				if (attrs != null) {
					sb.append(";");

					attrs.append(sb);
				}

				taglibRequirements.add(sb.toString());
			}
		}

		analyzer.setProperty(
			Constants.REQUIRE_CAPABILITY, Strings.join(taglibRequirements));
	}

	protected Set<String> getTaglibURIs(String content) {
		int contentX = -1;
		int contentY = content.length();

		Set<String> taglibURis = new HashSet<String>();

		while (true) {
			contentX = content.lastIndexOf("<%@", contentY);

			if (contentX == -1) {
				break;
			}

			contentY = contentX;

			int importX = content.indexOf("uri=\"", contentY);
			int importY = -1;

			if (importX != -1) {
				importX = importX + "uri=\"".length();
				importY = content.indexOf("\"", importX);
			}

			if ((importX != -1) && (importY != -1)) {
				String s = content.substring(importX, importY);

				taglibURis.add(s);
			}

			contentY -= 3;
		}

		return taglibURis;
	}

	private static final String[] _JSTL_CORE_URIS = new String[] {
		"http://java.sun.com/jsp/jstl/core", "http://java.sun.com/jsp/jstl/fmt",
		"http://java.sun.com/jsp/jstl/functions",
		"http://java.sun.com/jsp/jstl/sql", "http://java.sun.com/jsp/jstl/xml"
	};

	private static final String[] _REQUIRED_PACKAGE_NAMES = new String[] {
		"javax.servlet", "javax.servlet.http"
	};

	private static final Pattern _packagePattern = Pattern.compile(
		"[_A-Za-z$][_A-Za-z0-9$]*(\\.[_A-Za-z$][_A-Za-z0-9$]*)*");

}