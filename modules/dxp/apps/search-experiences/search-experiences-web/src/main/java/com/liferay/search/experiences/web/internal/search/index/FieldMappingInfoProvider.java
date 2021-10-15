/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.web.internal.search.index;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.index.IndexInformation;
import com.liferay.portal.search.index.IndexNameBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Petteri Karttunen
 */
public class FieldMappingInfoProvider {

	public FieldMappingInfoProvider(
		IndexInformation indexInformation, IndexNameBuilder indexNameBuilder,
		JSONFactory jsonFactory) {

		_indexInformation = indexInformation;
		_indexNameBuilder = indexNameBuilder;
		_jsonFactory = jsonFactory;
	}

	public List<FieldMappingInfo> getFieldMappings(long companyId) {
		JSONObject jsonObject = _getFieldMappingsJSONObject(companyId);

		if (jsonObject == null) {
			return Collections.<FieldMappingInfo>emptyList();
		}

		List<FieldMappingInfo> fieldMappingInfos = new ArrayList<>();

		_addFieldMappingInfos(
			fieldMappingInfos, new HashSet<String>(), jsonObject,
			StringPool.BLANK);

		return fieldMappingInfos;
	}

	public List<FieldMappingInfo> getLocalizedFieldMappings(long companyId) {
		JSONObject jsonObject = _getFieldMappingsJSONObject(companyId);

		if (jsonObject == null) {
			return Collections.<FieldMappingInfo>emptyList();
		}

		List<FieldMappingInfo> fieldMappingInfos = new ArrayList<>();

		for (String fieldName : jsonObject.keySet()) {
			JSONObject fieldJSONObject = jsonObject.getJSONObject(fieldName);

			fieldMappingInfos.add(
				new FieldMappingInfo(
					fieldName, fieldJSONObject.getString("type")));
		}

		return fieldMappingInfos;
	}

	private void _addFieldMappingInfo(
		List<FieldMappingInfo> fieldMappingInfos, String fieldName,
		Set<String> fieldNames, JSONObject jsonObject) {

		int languageIdPosition = -1;

		String languageId = _getLanguageId(fieldName);

		if (!Validator.isBlank(languageId)) {
			languageIdPosition = fieldName.lastIndexOf(languageId);
			fieldName = StringUtil.removeSubstring(fieldName, languageId);
		}

		String fieldNameWithPosition = fieldName + languageIdPosition;

		if (!fieldNames.contains(fieldNameWithPosition)) {
			fieldMappingInfos.add(
				new FieldMappingInfo(
					languageIdPosition, fieldName,
					jsonObject.getString("type")));

			fieldNames.add(fieldNameWithPosition);
		}
	}

	private void _addFieldMappingInfos(
		List<FieldMappingInfo> fieldMappingInfos, Set<String> fieldNames,
		JSONObject jsonObject, String path) {

		for (String fieldName : jsonObject.keySet()) {
			JSONObject fieldJSONObject = jsonObject.getJSONObject(fieldName);

			String fieldPath = fieldName;

			if (!Validator.isBlank(path)) {
				fieldPath = path + "." + fieldName;
			}

			if (Objects.equals(fieldJSONObject.getString("type"), "nested")) {
				_addFieldMappingInfos(
					fieldMappingInfos, fieldNames,
					fieldJSONObject.getJSONObject("properties"), fieldPath);
			}
			else {
				_addFieldMappingInfo(
					fieldMappingInfos, fieldPath, fieldNames, fieldJSONObject);
			}
		}
	}

	private JSONObject _getFieldMappingsJSONObject(long companyId) {
		String indexName = _indexNameBuilder.getIndexName(companyId);

		try {
			return JSONUtil.getValueAsJSONObject(
				_jsonFactory.createJSONObject(
					_indexInformation.getFieldMappings(indexName)),
				"JSONObject/" + indexName, "JSONObject/mappings",
				"JSONObject/properties");
		}
		catch (JSONException jsonException) {
			_log.error(jsonException.getMessage(), jsonException);
		}

		return null;
	}

	private String _getLanguageId(String fieldName) {
		Matcher matcher = _languageIdPattern.matcher(fieldName);

		if (matcher.matches()) {
			return matcher.group(2);
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FieldMappingInfoProvider.class);

	private static final Pattern _languageIdPattern = Pattern.compile(
		"(.*)(_[a-z]{2}_[A-Z]{2})(_.*)?");

	private final IndexInformation _indexInformation;
	private final IndexNameBuilder _indexNameBuilder;
	private final JSONFactory _jsonFactory;

}