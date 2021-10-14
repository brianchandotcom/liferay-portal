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

package com.liferay.search.experiences.internal.index;

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
import com.liferay.search.experiences.index.FieldMappingInfo;
import com.liferay.search.experiences.index.FieldMappingInfoProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(immediate = true, service = FieldMappingInfoProvider.class)
public class FieldMappingInfoProviderImpl implements FieldMappingInfoProvider {

	@Override
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

	@Override
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
		FieldMappingInfoProviderImpl.class);

	@Reference
	private IndexInformation _indexInformation;

	@Reference
	private IndexNameBuilder _indexNameBuilder;

	@Reference
	private JSONFactory _jsonFactory;

}