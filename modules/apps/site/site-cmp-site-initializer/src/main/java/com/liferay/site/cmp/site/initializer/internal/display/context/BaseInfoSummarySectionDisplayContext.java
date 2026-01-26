/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cmp.site.initializer.internal.display.context;

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetTagLocalServiceUtil;
import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectState;
import com.liferay.object.model.ObjectStateFlow;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectStateFlowLocalService;
import com.liferay.object.service.ObjectStateLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.Map;

/**
 * @author Pedro Leite
 */
public abstract class BaseInfoSummarySectionDisplayContext {

	public BaseInfoSummarySectionDisplayContext(
		ListTypeEntryLocalService listTypeEntryLocalService,
		ObjectEntry objectEntry,
		ObjectFieldLocalService objectFieldLocalService,
		ObjectStateFlowLocalService objectStateFlowLocalService,
		ObjectStateLocalService objectStateLocalService,
		ThemeDisplay themeDisplay) {

		_listTypeEntryLocalService = listTypeEntryLocalService;

		this.objectEntry = objectEntry;

		_objectFieldLocalService = objectFieldLocalService;
		_objectStateFlowLocalService = objectStateFlowLocalService;
		_objectStateLocalService = objectStateLocalService;

		this.themeDisplay = themeDisplay;
	}

	public Map<String, Object> getProperties() throws Exception {
		return HashMapBuilder.<String, Object>put(
			"dueDate",
			() -> {
				String dueDate = String.valueOf(getFieldValue("dueDate"));

				if (Validator.isNull(dueDate)) {
					return StringPool.BLANK;
				}

				return DateUtil.getDate(
					DateUtil.parseDate(
						ObjectFieldUtil.getDateTimePattern(dueDate), dueDate,
						themeDisplay.getLocale()),
					"yyyy-MM-dd", themeDisplay.getLocale());
			}
		).put(
			"initialState", getFieldValue("state")
		).put(
			"states",
			() -> {
				JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

				ObjectField objectField =
					_objectFieldLocalService.fetchObjectField(
						objectEntry.getObjectDefinitionId(), "state");

				for (ListTypeEntry listTypeEntry :
						_listTypeEntryLocalService.getListTypeEntries(
							objectField.getListTypeDefinitionId())) {

					jsonArray.put(
						JSONUtil.put(
							"key", listTypeEntry.getKey()
						).put(
							"name",
							listTypeEntry.getName(themeDisplay.getLocale())
						).put(
							"nextStates",
							_getNextStatesJSONArray(listTypeEntry, objectField)
						));
				}

				return jsonArray;
			}
		).put(
			"tags",
			ListUtil.toArray(
				AssetTagLocalServiceUtil.getTags(
					objectEntry.getModelClassName(),
					objectEntry.getObjectEntryId()),
				AssetTag.NAME_ACCESSOR)
		).build();
	}

	protected Object getFieldValue(String fieldName) {
		Map<String, Serializable> values = objectEntry.getValues();

		return values.get(fieldName);
	}

	protected final ObjectEntry objectEntry;
	protected final ThemeDisplay themeDisplay;

	private JSONArray _getNextStatesJSONArray(
		ListTypeEntry currentListTypeEntry, ObjectField objectField) {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		ObjectStateFlow objectStateFlow =
			_objectStateFlowLocalService.fetchObjectFieldObjectStateFlow(
				objectField.getObjectFieldId());

		ObjectState objectState =
			_objectStateLocalService.fetchObjectStateFlowObjectState(
				currentListTypeEntry.getListTypeEntryId(),
				objectStateFlow.getObjectStateFlowId());

		for (ObjectState nextObjectState :
				_objectStateLocalService.getNextObjectStates(
					objectState.getObjectStateId())) {

			ListTypeEntry nextListTypeEntry =
				_listTypeEntryLocalService.fetchListTypeEntry(
					nextObjectState.getListTypeEntryId());

			jsonArray.put(nextListTypeEntry.getKey());
		}

		return jsonArray;
	}

	private final ListTypeEntryLocalService _listTypeEntryLocalService;
	private final ObjectFieldLocalService _objectFieldLocalService;
	private final ObjectStateFlowLocalService _objectStateFlowLocalService;
	private final ObjectStateLocalService _objectStateLocalService;

}