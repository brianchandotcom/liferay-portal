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
import com.liferay.search.experiences.index.IndexFieldInfo;
import com.liferay.search.experiences.index.IndexFieldInfoProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(immediate = true, service = IndexFieldInfoProvider.class)
public class IndexFieldInfoProviderImpl implements IndexFieldInfoProvider {

	@Override
	public List<IndexFieldInfo> getIndexFieldInfos(long companyId) {
		JSONObject mappingsJSONObject = _getPropertiesJSONObject(companyId);

		if (mappingsJSONObject == null) {
			return new ArrayList<>();
		}

		List<IndexFieldInfo> indexFieldInfos = new ArrayList<>();

		Set<String> fieldKeySet = mappingsJSONObject.keySet();

		for (String fieldName : fieldKeySet) {
			JSONObject fieldJSONObject = mappingsJSONObject.getJSONObject(
				fieldName);

			indexFieldInfos.add(
				new IndexFieldInfo.IndexFieldInfoBuilder().name(
					fieldName
				).type(
					fieldJSONObject.getString("type")
				).build());
		}

		return indexFieldInfos;
	}

	@Override
	public List<IndexFieldInfo> getIndexFieldInfosWithoutLanguage(
		long companyId) {

		JSONObject propertiesJSONObject = _getPropertiesJSONObject(companyId);

		if (propertiesJSONObject == null) {
			return new ArrayList<>();
		}

		List<String> fieldNames = new ArrayList<>();

		List<IndexFieldInfo> indexFieldInfos = new ArrayList<>();

		_addFields(
			indexFieldInfos, fieldNames, StringPool.BLANK,
			propertiesJSONObject);

		return indexFieldInfos;
	}

	private void _addField(
		List<IndexFieldInfo> indexFieldInfos, List<String> fieldNames,
		String fieldName, JSONObject fieldJSONObject) {

		String languageId = _getLanguageId(fieldName);

		int languageIdPosition = -1;

		if (!Validator.isBlank(languageId)) {
			languageIdPosition = fieldName.lastIndexOf(languageId);

			fieldName = StringUtil.removeSubstring(fieldName, languageId);
		}

		String trackingKey = fieldName.concat(
			String.valueOf(languageIdPosition));

		if (!fieldNames.contains(trackingKey)) {
			indexFieldInfos.add(
				new IndexFieldInfo.IndexFieldInfoBuilder().languageIdPosition(
					languageIdPosition
				).name(
					fieldName
				).type(
					fieldJSONObject.getString("type")
				).build());

			fieldNames.add(trackingKey);
		}
	}

	private void _addFields(
		List<IndexFieldInfo> indexFieldInfos, List<String> fieldNames,
		String parentPath, JSONObject propertiesJSONObject) {

		Set<String> fieldKeySet = propertiesJSONObject.keySet();

		for (String fieldName : fieldKeySet) {
			JSONObject fieldJSONObject = propertiesJSONObject.getJSONObject(
				fieldName);

			String type = fieldJSONObject.getString("type");

			String fieldPath = _getFieldPath(parentPath, fieldName);

			if (type.equals("nested")) {
				_addFields(
					indexFieldInfos, fieldNames, fieldPath,
					fieldJSONObject.getJSONObject("properties"));
			}
			else {
				_addField(
					indexFieldInfos, fieldNames, fieldPath, fieldJSONObject);
			}
		}
	}

	private String _getFieldPath(String path, String fieldName) {
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

	private JSONObject _getPropertiesJSONObject(long companyId) {
		String indexName = _indexNameBuilder.getIndexName(companyId);

		try {
			String fieldMappings = _indexInformation.getFieldMappings(
				indexName);

			return JSONUtil.getValueAsJSONObject(
				_jsonFactory.createJSONObject(fieldMappings),
				"JSONObject/" + indexName, "JSONObject/mappings",
				"JSONObject/properties");
		}
		catch (JSONException jsonException) {
			_log.error(jsonException.getMessage(), jsonException);
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		IndexFieldInfoProviderImpl.class);

	@Reference
	private IndexInformation _indexInformation;

	@Reference
	private IndexNameBuilder _indexNameBuilder;

	@Reference
	private JSONFactory _jsonFactory;

}