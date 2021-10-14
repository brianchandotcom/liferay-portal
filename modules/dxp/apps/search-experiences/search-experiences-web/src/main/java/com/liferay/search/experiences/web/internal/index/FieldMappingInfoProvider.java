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

package com.liferay.search.experiences.web.internal.index;

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
import java.util.List;
import java.util.Set;

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

	public List<FieldMappingInfo> getMappings(long companyId) {
		JSONObject jsonObject = _getFieldMappingsJSONObject(companyId);

		if (jsonObject == null) {
			return Collections.<FieldMappingInfo>emptyList();
		}

		List<FieldMappingInfo> fieldMappingInfos = new ArrayList<>();

		Set<String> keySet = jsonObject.keySet();

		keySet.forEach(
			fieldName -> {
				JSONObject fieldJSONObject = jsonObject.getJSONObject(
					fieldName);

				fieldMappingInfos.add(
					new FieldMappingInfo(
						fieldName, fieldJSONObject.getString("type")));
			});

		return fieldMappingInfos;
	}

	public List<FieldMappingInfo> getMappingsWithoutLanguageID(long companyId) {
		JSONObject jsonObject = _getFieldMappingsJSONObject(companyId);

		if (jsonObject == null) {
			return Collections.<FieldMappingInfo>emptyList();
		}

		List<FieldMappingInfo> fieldMappingInfos = new ArrayList<>();

		_addFields(
			fieldMappingInfos, new ArrayList<String>(), jsonObject,
			StringPool.BLANK);

		return fieldMappingInfos;
	}

	public class FieldMappingInfo {

		public FieldMappingInfo(
			int languageIdPosition, String name, String type) {

			_languageIdPosition = languageIdPosition;
			_name = name;
			_type = type;
		}

		public FieldMappingInfo(String name, String type) {
			_name = name;
			_type = type;
		}

		public int getLanguageIdPosition() {
			return _languageIdPosition;
		}

		public String getName() {
			return _name;
		}

		public String getType() {
			return _type;
		}

		private int _languageIdPosition;
		private final String _name;
		private final String _type;

	}

	private void _addField(
		List<FieldMappingInfo> fieldInfos, String fieldName,
		List<String> fieldNames, JSONObject jsonObject) {

		String languageId = _getLanguageId(fieldName);

		int languageIdPosition = -1;

		if (!Validator.isBlank(languageId)) {
			languageIdPosition = fieldName.lastIndexOf(languageId);

			fieldName = StringUtil.removeSubstring(fieldName, languageId);
		}

		String fieldNameWithPos = fieldName.concat(
			String.valueOf(languageIdPosition));

		if (!fieldNames.contains(fieldNameWithPos)) {
			fieldInfos.add(
				new FieldMappingInfo(
					languageIdPosition, fieldName,
					jsonObject.getString("type")));

			fieldNames.add(fieldNameWithPos);
		}
	}

	private void _addFields(
		List<FieldMappingInfo> fieldInfos, List<String> fieldNames,
		JSONObject jsonObject, String parentPath) {

		Set<String> keySet = jsonObject.keySet();

		keySet.forEach(
			fieldName -> {
				JSONObject fieldJSONObject = jsonObject.getJSONObject(
					fieldName);

				String type = fieldJSONObject.getString("type");

				String fieldNameWithPath = _getFieldNameWithPath(
					fieldName, parentPath);

				if (type.equals("nested")) {
					_addFields(
						fieldInfos, fieldNames,
						fieldJSONObject.getJSONObject("properties"),
						fieldNameWithPath);
				}
				else {
					_addField(
						fieldInfos, fieldNameWithPath, fieldNames,
						fieldJSONObject);
				}
			});
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

	private String _getFieldNameWithPath(String fieldName, String path) {
		if (Validator.isBlank(path)) {
			return fieldName;
		}

		return path + "." + fieldName;
	}

	private String _getLanguageId(String fieldName) {
		String pattern = "(.*)(_[a-z]{2}_[A-Z]{2})(_.*)?";

		if (fieldName.matches(pattern)) {
			return fieldName.replaceFirst(pattern, "$2");
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FieldMappingInfoProvider.class);

	private final IndexInformation _indexInformation;
	private final IndexNameBuilder _indexNameBuilder;
	private final JSONFactory _jsonFactory;

}