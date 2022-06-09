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

package com.liferay.source.formatter.util;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.check.util.SourceUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaClassParser;
import com.liferay.source.formatter.parser.ParseException;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author Peter Shin
 */
public class CETUtil {

	public static String getJSONContent(List<String> fileNames)
		throws IOException, ParseException {

		StringBuilder sb = new StringBuilder();

		sb.append("[");

		List<CET> cets = _getCETs(fileNames);

		for (CET cet : cets) {
			sb.append("\n\t{\n");

			sb.append("\t\t\"description\": \"");
			sb.append(cet.getDescription());
			sb.append("\",\n");

			sb.append("\t\t\"name\": \"");
			sb.append(cet.getName());
			sb.append("\",\n");

			sb.append("\t\t\"properties\": [\n");

			List<CETProperty> cetProperties = cet.getCETProperties();

			for (CETProperty cetProperty : cetProperties) {
				sb.append("\t\t\t{\n");

				String defaultValue = cetProperty.getDefaultValue();

				if (defaultValue != null) {
					sb.append("\t\t\t\t\"default\": \"");
					sb.append(defaultValue);
					sb.append("\",\n");
				}

				String name = cetProperty.getName();

				if (name != null) {
					sb.append("\t\t\t\t\"name\": \"");
					sb.append(name);
					sb.append("\",\n");
				}

				String type = cetProperty.getType();

				if (type != null) {
					sb.append("\t\t\t\t\"type\": \"");
					sb.append(type);
					sb.append("\"\n");
				}

				sb.append("\t\t\t},\n");
			}

			if (!cetProperties.isEmpty()) {
				sb.setLength(sb.length() - 2);

				sb.append("\n");
			}

			sb.append("\t\t]\n");

			sb.append("\t},");
		}

		if (!cets.isEmpty()) {
			sb.setLength(sb.length() - 1);

			sb.append("\n");
		}

		sb.append("]");

		return sb.toString();
	}

	private static CET _getCET(
			JavaClass javaClass, List<CETProperty> defaultCETProperties)
		throws IOException {

		String cetDescription = null;
		String cetName = null;

		List<CETProperty> cetProperties = new ArrayList<>(defaultCETProperties);

		List<String> annotationsBlocks = SourceUtil.getAnnotationsBlocks(
			javaClass.getContent());

		for (String annotationsBlock : annotationsBlocks) {
			List<String> annotations = SourceUtil.splitAnnotations(
				annotationsBlock, SourceUtil.getIndent(annotationsBlock));

			for (String annotation : annotations) {
				annotation = annotation.trim();

				if (annotation.startsWith("@CETProperty")) {
					String defaultValue = null;
					String name = null;
					String type = null;

					int x = annotation.indexOf("defaultValue = \"");

					if (x != -1) {
						defaultValue = annotation.substring(
							x + 16, annotation.indexOf("\"", x + 16));
					}

					x = annotation.indexOf("name = \"");

					if (x != -1) {
						name = annotation.substring(
							x + 8, annotation.indexOf("\"", x + 8));
					}

					x = annotation.indexOf("type = \"");

					if (x != -1) {
						type = annotation.substring(
							x + 8, annotation.indexOf("\"", x + 8));
					}

					cetProperties.add(
						new CETProperty(name, type, defaultValue));
				}
				else if (annotation.startsWith("@CETType")) {
					int x = annotation.indexOf("description = \"");

					if (x != -1) {
						cetDescription = annotation.substring(
							x + 15, annotation.indexOf("\"", x + 15));
					}

					x = annotation.indexOf("name = \"");

					if (x != -1) {
						cetName = annotation.substring(
							x + 8, annotation.indexOf("\"", x + 8));
					}
				}
			}
		}

		return new CET(cetName, cetDescription, cetProperties);
	}

	private static List<CET> _getCETs(List<String> fileNames)
		throws IOException, ParseException {

		JavaClass baseCETJavaClass = null;
		List<JavaClass> cetJavaClasses = new ArrayList<>();

		for (String fileName : fileNames) {
			String normalizedFileName = StringUtil.replace(
				fileName, CharPool.BACK_SLASH, CharPool.SLASH);

			String absolutePath = SourceUtil.getAbsolutePath(
				normalizedFileName);

			String fileContent = FileUtil.read(new File(absolutePath), false);

			JavaClass javaClass = JavaClassParser.parseJavaClass(
				normalizedFileName, fileContent);

			if (Objects.equals(javaClass.getName(), "CET")) {
				baseCETJavaClass = javaClass;
			}
			else {
				cetJavaClasses.add(javaClass);
			}
		}

		List<CETProperty> defaultCETProperties = new ArrayList<>();

		if (baseCETJavaClass != null) {
			CET cet = _getCET(baseCETJavaClass, Collections.emptyList());

			defaultCETProperties.addAll(cet.getCETProperties());
		}

		List<CET> cets = new ArrayList<>();

		for (JavaClass cetJavaClass : cetJavaClasses) {
			cets.add(_getCET(cetJavaClass, defaultCETProperties));
		}

		return cets;
	}

	private static class CET {

		public CET(
			String name, String description, List<CETProperty> cetProperties) {

			_name = name;
			_description = description;
			_cetProperties = cetProperties;
		}

		public List<CETProperty> getCETProperties() {
			return _cetProperties;
		}

		public String getDescription() {
			return _description;
		}

		public String getName() {
			return _name;
		}

		private final List<CETProperty> _cetProperties;
		private final String _description;
		private final String _name;

	}

	private static class CETProperty {

		public CETProperty(String name, String type, String defaultValue) {
			_name = name;
			_type = type;
			_defaultValue = defaultValue;
		}

		public String getDefaultValue() {
			return _defaultValue;
		}

		public String getName() {
			return _name;
		}

		public String getType() {
			return _type;
		}

		private final String _defaultValue;
		private final String _name;
		private final String _type;

	}

}