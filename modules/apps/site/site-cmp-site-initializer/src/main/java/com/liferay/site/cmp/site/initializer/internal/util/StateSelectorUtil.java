/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cmp.site.initializer.internal.util;

import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeEntryLocalServiceUtil;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectState;
import com.liferay.object.model.ObjectStateFlow;
import com.liferay.object.service.ObjectFieldLocalServiceUtil;
import com.liferay.object.service.ObjectStateFlowLocalServiceUtil;
import com.liferay.object.service.ObjectStateLocalServiceUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;

/**
 * @author Carolina Barbosa
 */
public class StateSelectorUtil {

	public static JSONArray getStatesJSONArray(
		ObjectEntry objectEntry, ThemeDisplay themeDisplay) {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		ObjectField objectField = ObjectFieldLocalServiceUtil.fetchObjectField(
			objectEntry.getObjectDefinitionId(), "state");

		for (ListTypeEntry listTypeEntry :
				ListTypeEntryLocalServiceUtil.getListTypeEntries(
					objectField.getListTypeDefinitionId())) {

			jsonArray.put(
				JSONUtil.put(
					"key", listTypeEntry.getKey()
				).put(
					"name", listTypeEntry.getName(themeDisplay.getLocale())
				).put(
					"nextStates",
					() -> {
						JSONArray nextStatesJSONArray =
							JSONFactoryUtil.createJSONArray();

						ObjectStateFlow objectStateFlow =
							ObjectStateFlowLocalServiceUtil.
								fetchObjectFieldObjectStateFlow(
									objectField.getObjectFieldId());

						ObjectState objectState =
							ObjectStateLocalServiceUtil.
								fetchObjectStateFlowObjectState(
									listTypeEntry.getListTypeEntryId(),
									objectStateFlow.getObjectStateFlowId());

						for (ObjectState nextObjectState :
								ObjectStateLocalServiceUtil.getNextObjectStates(
									objectState.getObjectStateId())) {

							ListTypeEntry nextListTypeEntry =
								ListTypeEntryLocalServiceUtil.
									fetchListTypeEntry(
										nextObjectState.getListTypeEntryId());

							nextStatesJSONArray.put(nextListTypeEntry.getKey());
						}

						return nextStatesJSONArray;
					}
				));
		}

		return jsonArray;
	}

}