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

package com.liferay.source.formatter.checkstyle;

import com.puppycrawl.tools.checkstyle.api.AbstractLoader;
import com.puppycrawl.tools.checkstyle.api.FilterSet;
import com.puppycrawl.tools.checkstyle.filters.SuppressElement;

import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @author Hugo Huijser
 */
public class SuppressionsLoader extends AbstractLoader {

	public static FilterSet loadSuppressions(InputSource inputSource)
		throws Exception {

		SuppressionsLoader suppressionsLoader = new SuppressionsLoader();

		suppressionsLoader.parseInputSource(inputSource);

		return suppressionsLoader.getFilterChain();
	}

	public SuppressionsLoader()
		throws ParserConfigurationException, SAXException {

		super(_createIdToResourceNameMap());
	}

	public FilterSet getFilterChain() {
		return _filterChain;
	}

	@Override
	public void startElement(
		String namespaceUri, String localName, String qName,
		Attributes attributes) {

		if (!qName.equals("suppress")) {
			return;
		}

		String checks = attributes.getValue("checks");
		String modId = attributes.getValue("id");

		if ((checks == null) && (modId == null)) {
			return;
		}

		String files = attributes.getValue("files");

		SuppressElement suppressElement = new SuppressElement(files);

		if (checks != null) {
			suppressElement.setChecks(checks);
		}

		if (modId != null) {
			suppressElement.setModuleId(modId);
		}

		String columns = attributes.getValue("columns");

		if (columns != null) {
			suppressElement.setColumns(columns);
		}

		String lines = attributes.getValue("lines");

		if (lines != null) {
			suppressElement.setLines(lines);
		}

		_filterChain.addFilter(suppressElement);
	}

	private static Map<String, String> _createIdToResourceNameMap() {
		Map<String, String> map = new HashMap<>();

		map.put(_DTD_PUBLIC_ID_1_1, _DTD_RESOURCE_NAME_1_1);

		return map;
	}

	private static final String _DTD_PUBLIC_ID_1_1 =
		"-//Puppy Crawl//DTD Suppressions 1.1//EN";

	private static final String _DTD_RESOURCE_NAME_1_1 =
		"com/puppycrawl/tools/checkstyle/suppressions_1_1.dtd";

	private final FilterSet _filterChain = new FilterSet();

}