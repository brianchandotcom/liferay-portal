/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.display.context;

import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.object.constants.ObjectEntryFolderConstants;
import com.liferay.object.constants.ObjectFolderConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Christian Dorado
 */
public class ViewHomeQuickActionsDisplayContext {

	public ViewHomeQuickActionsDisplayContext(
		DepotEntryLocalService depotEntryLocalService,
		GroupLocalService groupLocalService,
		ObjectDefinitionService objectDefinitionService,
		ThemeDisplay themeDisplay) {

		_depotEntryLocalService = depotEntryLocalService;
		_groupLocalService = groupLocalService;
		_objectDefinitionService = objectDefinitionService;
		_themeDisplay = themeDisplay;
	}

	public Map<String, Object> getProps() throws Exception {
		return HashMapBuilder.<String, Object>put(
			"quickActions", _getQuickActions()
		).build();
	}

	private Map<String, Object> _createQuickAction(
		JSONArray depotEntriesJSONArray, String icon,
		ObjectDefinition objectDefinition) {

		return HashMapBuilder.<String, Object>put(
			"action", "createAsset"
		).put(
			"assetLibraries", depotEntriesJSONArray
		).put(
			"icon", icon
		).put(
			"redirect",
			StringBundler.concat(
				_themeDisplay.getPortalURL(), _themeDisplay.getPathMain(),
				GroupConstants.CMS_FRIENDLY_URL,
				"/add_structured_content_item?objectDefinitionId=",
				objectDefinition.getObjectDefinitionId(),
				"&objectEntryFolderExternalReferenceCode=",
				_getObjectEntryFolderExternalReferenceCode(objectDefinition),
				"&plid=", _themeDisplay.getPlid(), "&redirect=",
				_themeDisplay.getURLCurrent())
		).put(
			"title", objectDefinition.getLabel(_themeDisplay.getLocale())
		).build();
	}

	private JSONArray _getDepotEntriesJSONArray() {
		return _getDepotEntriesJSONArray(
			TransformUtil.transform(
				_depotEntryLocalService.getDepotEntries(
					QueryUtil.ALL_POS, QueryUtil.ALL_POS),
				DepotEntry::getGroupId));
	}

	private JSONArray _getDepotEntriesJSONArray(List<Long> groupIds) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (Long groupId : groupIds) {
			JSONObject jsonObject = _getJSONObject(groupId);

			if (jsonObject != null) {
				jsonArray.put(jsonObject);
			}
		}

		return jsonArray;
	}

	private JSONObject _getJSONObject(long groupId) {
		Group group = _groupLocalService.fetchGroup(groupId);

		if (group == null) {
			return null;
		}

		return JSONUtil.put(
			"groupId", group.getGroupId()
		).put(
			"name", group.getName(_themeDisplay.getLocale())
		);
	}

	private String _getObjectEntryFolderExternalReferenceCode(
		ObjectDefinition objectDefinition) {

		if (Objects.equals(
				objectDefinition.getObjectFolderExternalReferenceCode(),
				ObjectFolderConstants.
					EXTERNAL_REFERENCE_CODE_CONTENT_STRUCTURES)) {

			return ObjectEntryFolderConstants.EXTERNAL_REFERENCE_CODE_CONTENTS;
		}

		if (Objects.equals(
				objectDefinition.getObjectFolderExternalReferenceCode(),
				ObjectFolderConstants.EXTERNAL_REFERENCE_CODE_FILE_TYPES)) {

			return ObjectEntryFolderConstants.EXTERNAL_REFERENCE_CODE_FILES;
		}

		return null;
	}

	private List<Map<String, Object>> _getQuickActions() throws Exception {
		List<Map<String, Object>> quickActions = new ArrayList<>();

		JSONArray depotEntriesJSONArray = _getDepotEntriesJSONArray();

		List<ObjectDefinition> objectDefinitions =
			_objectDefinitionService.getCMSObjectDefinitions(
				_themeDisplay.getCompanyId(),
				new String[] {
					ObjectFolderConstants.
						EXTERNAL_REFERENCE_CODE_CONTENT_STRUCTURES
				});

		for (int i = 0; i < objectDefinitions.size(); i++) {
			ObjectDefinition objectDefinition = objectDefinitions.get(i);

			quickActions.add(
				_createQuickAction(
					depotEntriesJSONArray, _ICONS[i], objectDefinition));
		}

		quickActions.add(
			_createQuickAction(
				depotEntriesJSONArray, _ICONS[_ICONS.length - 2],
				_objectDefinitionService.
					getObjectDefinitionByExternalReferenceCode(
						"L_BASIC_DOCUMENT", _themeDisplay.getCompanyId())));
		quickActions.add(
			HashMapBuilder.<String, Object>put(
				"action", "createVocabulary"
			).put(
				"icon", _ICONS[_ICONS.length - 1]
			).put(
				"redirect",
				PortalUtil.getLayoutFullURL(
					LayoutLocalServiceUtil.getLayoutByFriendlyURL(
						_themeDisplay.getScopeGroupId(), false,
						"/categorization/new-vocabulary"),
					_themeDisplay)
			).put(
				"title",
				LanguageUtil.get(_themeDisplay.getLocale(), "vocabulary")
			).build());

		return quickActions;
	}

	private static final String[] _ICONS = {
		"forms", "blogs", "wiki", "documents-and-media", "vocabulary"
	};

	private final DepotEntryLocalService _depotEntryLocalService;
	private final GroupLocalService _groupLocalService;
	private final ObjectDefinitionService _objectDefinitionService;
	private final ThemeDisplay _themeDisplay;

}