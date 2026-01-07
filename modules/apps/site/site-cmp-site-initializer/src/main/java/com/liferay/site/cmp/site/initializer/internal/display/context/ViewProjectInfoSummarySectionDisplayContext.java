/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cmp.site.initializer.internal.display.context;

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetTagLocalServiceUtil;
import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeEntryLocalServiceUtil;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectFieldLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.Collections;
import java.util.Map;

/**
 * @author Igor Franca
 */
public class ViewProjectInfoSummarySectionDisplayContext {

	public ViewProjectInfoSummarySectionDisplayContext(
		ObjectEntry objectEntry, ThemeDisplay themeDisplay) {

		_objectEntry = objectEntry;
		_themeDisplay = themeDisplay;
	}

	public Map<String, Object> getProperties() throws Exception {
		return HashMapBuilder.<String, Object>put(
			"dueDate",
			() -> {
				String dueDate = String.valueOf(_getFieldValue("dueDate"));

				if (Validator.isNull(dueDate)) {
					return StringPool.BLANK;
				}

				return DateUtil.getDate(
					DateUtil.parseDate(
						ObjectFieldUtil.getDateTimePattern(dueDate), dueDate,
						_themeDisplay.getLocale()),
					"yyyy-MM-dd", _themeDisplay.getLocale());
			}
		).put(
			"initialState", _getFieldValue("state")
		).put(
			"manager",
			_getUserInfoMap(
				GetterUtil.getLong(
					_getFieldValue("r_userToCMPProjectManager_userId")))
		).put(
			"projectId", _objectEntry.getObjectEntryId()
		).put(
			"sponsor",
			_getUserInfoMap(
				GetterUtil.getLong(
					_getFieldValue("r_userToCMPProjectSponsor_userId")))
		).put(
			"states",
			() -> {
				JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

				ObjectField objectField =
					ObjectFieldLocalServiceUtil.fetchObjectField(
						_objectEntry.getObjectDefinitionId(), "state");

				for (ListTypeEntry listTypeEntry :
						ListTypeEntryLocalServiceUtil.getListTypeEntries(
							objectField.getListTypeDefinitionId())) {

					jsonArray.put(
						JSONUtil.put(
							"key", listTypeEntry.getKey()
						).put(
							"name",
							listTypeEntry.getName(_themeDisplay.getLocale())
						));
				}

				return jsonArray;
			}
		).put(
			"tags",
			ListUtil.toArray(
				AssetTagLocalServiceUtil.getTags(
					_objectEntry.getModelClassName(),
					_objectEntry.getObjectEntryId()),
				AssetTag.NAME_ACCESSOR)
		).build();
	}

	private Object _getFieldValue(String fieldName) {
		Map<String, Serializable> values = _objectEntry.getValues();

		return values.get(fieldName);
	}

	private Map<String, String> _getUserInfoMap(long userId) throws Exception {
		User user = UserLocalServiceUtil.fetchUser(userId);

		if (user == null) {
			return Collections.emptyMap();
		}

		return HashMapBuilder.put(
			"image", user.getPortraitURL(_themeDisplay)
		).put(
			"name", user.getFullName()
		).build();
	}

	private final ObjectEntry _objectEntry;
	private final ThemeDisplay _themeDisplay;

}