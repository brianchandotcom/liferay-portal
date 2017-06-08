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

package com.liferay.ant.bnd.plugin;

import aQute.bnd.header.Attrs;
import aQute.bnd.header.OSGiHeader;
import aQute.bnd.header.Parameters;
import aQute.bnd.osgi.Analyzer;
import aQute.bnd.osgi.Constants;
import aQute.bnd.osgi.Descriptors.PackageRef;
import aQute.bnd.osgi.Instruction;
import aQute.bnd.osgi.Instructions;
import aQute.bnd.osgi.Jar;
import aQute.bnd.osgi.Packages;
import aQute.bnd.osgi.Resource;
import aQute.bnd.service.AnalyzerPlugin;

import aQute.lib.io.IO;
import aQute.lib.strings.Strings;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Raymond Augé
 */
public class JSPAnalyzerPlugin implements AnalyzerPlugin {

	@Override
	public boolean analyzeJar(Analyzer analyzer) throws Exception {
		Parameters header = OSGiHeader.parseHeader(analyzer.getProperty(_JSP));

		if (header.isEmpty()) {
			return false;
		}

		Instructions instructions = new Instructions(header);

		Jar jar = analyzer.getJar();

		Map<String, Resource> resources = jar.getResources();

		Set<String> keySet = new HashSet<String>(resources.keySet());

		for (String key : keySet) {
			for (Instruction instruction : instructions.keySet()) {
				if (instruction.matches(key)) {
					if (instruction.isNegated()) {
						break;
					}

					Resource resource = jar.getResource(key);

					String jsp = IO.collect(
						resource.openInputStream(), "UTF-8");

					addPackageImports(analyzer, key, jsp);
					addTaglibRequirement(analyzer, key, jsp);
				}
			}
		}

		return false;
	}

	private void addPackageImports(
		Analyzer analyzer, String path, String content) {

		Packages referredPackages = analyzer.getReferred();
		Set<String> imports = analyzePackageImports(content);

		for (String packageName : imports) {
			PackageRef packageRef = analyzer.getPackageRef(packageName);

			Matcher matcher = _packagePattern.matcher(packageRef.getFQN());

			if (!matcher.matches()) {
				analyzer.warning(
					"Package does not seem a package in jsp resource " + path +
						": " + packageRef);
			}

			if (!referredPackages.containsKey(packageRef)) {
				referredPackages.put(packageRef, new Attrs());
			}
		}
	}

	private void addTaglibRequirement(Set<String> requires, String uri) {
		Parameters parameters = new Parameters();

		Attrs attrs = new Attrs();

		attrs.put(
			Constants.FILTER_DIRECTIVE,
			"\"(&(osgi.extender=jsp.taglib)(uri=" + uri + "))\"");

		parameters.put("osgi.extender", attrs);

		requires.add(parameters.toString());
	}

	private void addTaglibRequirement(
		Analyzer analyzer, String key, String content) {

		Set<String> taglibURIs = analyzeTagLibURIs(content);
		TreeSet<String> requirements = new TreeSet<String>();

		for (String uri : taglibURIs) {
			if (Arrays.binarySearch(_JSTL_CORE_URIS, uri) < 0) {
				addTaglibRequirement(requirements, uri);
			}
		}

		if (requirements.isEmpty()) {
			return;
		}

		String propertyValue = analyzer.getProperty(
			Constants.REQUIRE_CAPABILITY);

		if (propertyValue != null) {
			Parameters parameters = OSGiHeader.parseHeader(propertyValue);

			for (Entry<String, Attrs> entry : parameters.entrySet()) {
				Attrs value = entry.getValue();
				StringBuilder sb = new StringBuilder(entry.getKey());

				if (value != null) {
					sb.append(";");

					value.append(sb);
				}

				requirements.add(sb.toString());
			}
		}

		String header = Strings.join(requirements);

		analyzer.setProperty(Constants.REQUIRE_CAPABILITY, header);
	}

	private Set<String> analyzePackageImports(String content) {
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

				int index = s.lastIndexOf('.');

				if (index != -1) {
					packageNames.add(s.substring(0, index));
				}
			}

			contentY -= 3;
		}

		return packageNames;
	}

	private Set<String> analyzeTagLibURIs(String content) {
		int contentX = -1;
		int contentY = content.length();

		Set<String> uris = new HashSet<String>();

		while (true) {
			contentX = content.lastIndexOf("<%@", contentY);

			if (contentX == -1) {
				break;
			}

			contentY = contentX;

			int uriX = content.indexOf("uri=\"", contentY);
			int importY = -1;

			if (uriX != -1) {
				uriX = uriX + "uri=\"".length();
				importY = content.indexOf("\"", uriX);
			}

			if ((uriX != -1) && (importY != -1)) {
				String s = content.substring(uriX, importY);

				uris.add(s);
			}

			contentY -= 3;
		}

		return uris;
	}

	private static final String[] _JSTL_CORE_URIS = new String[] {
		"http://java.sun.com/jsp/jstl/core",
		"http://java.sun.com/jsp/jstl/fmt",
		"http://java.sun.com/jsp/jstl/functions",
		"http://java.sun.com/jsp/jstl/sql",
		"http://java.sun.com/jsp/jstl/xml"
	};

	private static final String _JSP = "-jsp";

	private static final Pattern _packagePattern =
		Pattern.compile(
			"[_A-Za-z$][_A-Za-z0-9$]*(\\.[_A-Za-z$][_A-Za-z0-9$]*)*");

}