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
import com.liferay.portal.json.JSONArrayImpl;
import com.liferay.portal.json.JSONObjectImpl;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.check.util.SourceUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaClassParser;
import com.liferay.source.formatter.parser.ParseException;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Peter Shin
 */
public class CETUtil {

	public static String getJSONContent(List<String> fileNames)
		throws IOException, ParseException {

		List<CET> cets = _getCETs(fileNames);

		JSONArray cetJSONArray = new JSONArrayImpl();

		for (CET cet : cets) {
			JSONObject cetJSONObject = new JSONObjectImpl();

			cetJSONObject.put(
				"description", cet.getDescription()
			).put(
				"name", cet.getName()
			);

			JSONArray cetPropertiesJSONArray = new JSONArrayImpl();

			List<CETProperty> cetProperties = cet.getCETProperties();

			for (CETProperty cetProperty : cetProperties) {
				JSONObject cetPropertiesJSONObject = new JSONObjectImpl();

				String defaultValue = cetProperty.getDefaultValue();

				if (defaultValue != null) {
					cetPropertiesJSONObject.put("default", defaultValue);
				}

				String name = cetProperty.getName();

				if (name != null) {
					cetPropertiesJSONObject.put("name", name);
				}

				String type = cetProperty.getType();

				if (type != null) {
					cetPropertiesJSONObject.put("type", type);
				}

				cetPropertiesJSONArray.put(cetPropertiesJSONObject);
			}

			cetJSONObject.put("properties", cetPropertiesJSONArray);

			cetJSONArray.put(cetJSONObject);
		}

		return JSONUtil.toString(cetJSONArray);
	}

	private static CET _getCET(
			List<CETProperty> defaultCETProperties, JavaClass javaClass)
		throws IOException {

		List<CETProperty> cetProperties = new ArrayList<>(defaultCETProperties);
		String description = null;
		String name = null;

		List<String> annotationsBlocks = SourceUtil.getAnnotationsBlocks(
			javaClass.getContent());

		for (String annotationsBlock : annotationsBlocks) {
			List<String> annotations = SourceUtil.splitAnnotations(
				annotationsBlock, SourceUtil.getIndent(annotationsBlock));

			for (String annotation : annotations) {
				annotation = annotation.trim();

				Map<String, String> annotationMemberValuePair =
					SourceUtil.getAnnotationMemberValuePair(annotation);

				if (annotation.startsWith("@CETProperty")) {
					cetProperties.add(
						new CETProperty(
							annotationMemberValuePair.get("defaultValue"),
							annotationMemberValuePair.get("name"),
							annotationMemberValuePair.get("type")));
				}
				else if (annotation.startsWith("@CETType")) {
					description = annotationMemberValuePair.get("description");
					name = annotationMemberValuePair.get("name");
				}
			}
		}

		return new CET(cetProperties, description, name);
	}

	private static List<CET> _getCETs(List<String> fileNames)
		throws IOException, ParseException {

		JavaClass baseCETJavaClass = null;
		List<JavaClass> cetJavaClasses = new ArrayList<>();

		for (String fileName : new LinkedHashSet<>(fileNames)) {
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
			CET cet = _getCET(Collections.emptyList(), baseCETJavaClass);

			defaultCETProperties.addAll(cet.getCETProperties());
		}

		List<CET> cets = new ArrayList<>();

		for (JavaClass cetJavaClass : cetJavaClasses) {
			CET cet = _getCET(defaultCETProperties, cetJavaClass);

			if (Validator.isNull(cet.getName())) {
				continue;
			}

			cets.add(cet);
		}

		Collections.sort(
			cets,
			new Comparator<CET>() {

				@Override
				public int compare(CET cet1, CET cet2) {
					String name1 = cet1.getName();
					String name2 = cet2.getName();

					return name1.compareTo(name2);
				}

			});

		return cets;
	}

	private static class CET {

		public CET(
			List<CETProperty> cetProperties, String description, String name) {

			_cetProperties = cetProperties;
			_description = description;
			_name = name;
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

		public CETProperty(String defaultValue, String name, String type) {
			_defaultValue = defaultValue;
			_name = name;
			_type = type;
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