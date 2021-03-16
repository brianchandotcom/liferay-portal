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

package com.liferay.portal.search.tuning.blueprints.admin.web.internal.util;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.search.tuning.blueprints.fields.provider.FieldInfo;
import com.liferay.portal.search.tuning.blueprints.fields.provider.FieldMappingInfoProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(immediate = true, service = {})
public class BlueprintsAdminFieldsUtil {

	public static JSONArray getFieldsJSONArray(long companyId) {
		List<FieldInfo> fields =
			_fieldMappingInfoProvider.getFieldInfosDeLocalized(companyId);

		JSONArray jsonArray = _jsonFactory.createJSONArray();

		Stream<FieldInfo> stream = fields.stream();

		stream.filter(
			fieldInfo -> !_isBlacklisted(fieldInfo)
		).forEach(
			fieldInfo -> jsonArray.put(
				JSONUtil.put(
					"language_id_position", fieldInfo.getLanguageIdPosition()
				).put(
					"name", fieldInfo.getName()
				).put(
					"type", fieldInfo.getType()
				))
		);

		return jsonArray;
	}

	@Reference(unbind = "-")
	protected void setFieldMappingInfoProvider(
		FieldMappingInfoProvider fieldMappingInfoProvider) {

		_fieldMappingInfoProvider = fieldMappingInfoProvider;
	}

	@Reference(unbind = "-")
	protected void setJSONFactory(JSONFactory jsonFactory) {
		_jsonFactory = jsonFactory;
	}

	private static boolean _isBlacklisted(FieldInfo fieldInfo) {
		return _blackListedfields.contains(
			fieldInfo.getType() + ":" + fieldInfo.getName());
	}

	private static final List<String> _blackListedfields =
		new ArrayList<String>() {
			{
				add("text:content_length");
				add("text:versionCount");
			}
		};
	private static FieldMappingInfoProvider _fieldMappingInfoProvider;
	private static JSONFactory _jsonFactory;

}