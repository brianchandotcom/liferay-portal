/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.admin.rest.dto.v1_0.util;

import com.liferay.object.admin.rest.dto.v1_0.ObjectStateFlow;
import com.liferay.object.constants.ObjectFieldSettingConstants;
import com.liferay.object.filter.util.ObjectFilterUtil;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectFieldSetting;
import com.liferay.object.service.ObjectStateFlowLocalServiceUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;

/**
 * @author Feliphe Marinho
 */
public class ObjectFieldSettingUtil {

	public static Object getValue(ObjectFieldSetting objectFieldSetting) {
		if (objectFieldSetting.compareName(
				ObjectFieldSettingConstants.NAME_FILTERS)) {

			return ObjectFilterUtil.getObjectFiltersJSONArray(
				objectFieldSetting.getObjectFilters());
		}
		else if (objectFieldSetting.compareName(
					ObjectFieldSettingConstants.NAME_MAX_FILE_SIZE) ||
				 objectFieldSetting.compareName(
					 ObjectFieldSettingConstants.NAME_MAX_LENGTH)) {

			return GetterUtil.getInteger(objectFieldSetting.getValue());
		}
		else if (objectFieldSetting.compareName(
					ObjectFieldSettingConstants.NAME_SHOW_COUNTER) ||
				 objectFieldSetting.compareName(
					 ObjectFieldSettingConstants.
						 NAME_SHOW_FILES_IN_DOCS_AND_MEDIA) ||
				 objectFieldSetting.compareName(
					 ObjectFieldSettingConstants.NAME_UNIQUE_VALUES)) {

			return GetterUtil.getBoolean(objectFieldSetting.getValue());
		}
		else if (objectFieldSetting.compareName(
					ObjectFieldSettingConstants.NAME_STATE_FLOW)) {

			ObjectStateFlow objectStateFlow =
				ObjectStateFlowUtil.toObjectStateFlow(
					ObjectStateFlowLocalServiceUtil.fetchObjectStateFlow(
						GetterUtil.getLong(objectFieldSetting.getValue())));

			try {
				return JSONFactoryUtil.createJSONObject(
					objectStateFlow.toString());
			}
			catch (JSONException jsonException) {
				_log.error(jsonException);

				return null;
			}
		}

		return objectFieldSetting.getValue();
	}

	public static JSONArray toJSONObject(ObjectField objectField) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		ListUtil.isNotEmptyForEach(
			objectField.getObjectFieldSettings(),
			objectFieldSetting -> jsonArray.put(
				JSONUtil.put(
					"name", objectFieldSetting.getName()
				).put(
					"objectFieldId", objectFieldSetting.getObjectFieldId()
				).put(
					"value", getValue(objectFieldSetting)
				)));

		return jsonArray;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectFieldSettingUtil.class);

}