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

	public List<Map<String, String>> getQuickActions() throws Exception {
		List<Map<String, String>> quickActions = new ArrayList<>();

		List<ObjectDefinition> objectDefinitions =
			_objectDefinitionService.getCMSObjectDefinitions(
				_themeDisplay.getCompanyId(),
				new String[] {
					ObjectFolderConstants.
						EXTERNAL_REFERENCE_CODE_CONTENT_STRUCTURES
				});
		Group group = _getDepotEntryGroup();

		for (int i = 0; i < objectDefinitions.size(); i++) {
			ObjectDefinition objectDefinition = objectDefinitions.get(i);

			quickActions.add(
				_createQuickAction(_ICONS[i], group, objectDefinition));
		}

		quickActions.add(
			_createQuickAction(
				_ICONS[_ICONS.length - 2], group,
				_objectDefinitionService.
					getObjectDefinitionByExternalReferenceCode(
						"L_BASIC_DOCUMENT", _themeDisplay.getCompanyId())));

		quickActions.add(
			HashMapBuilder.put(
				"href",
				PortalUtil.getLayoutFullURL(
					LayoutLocalServiceUtil.getLayoutByFriendlyURL(
						_themeDisplay.getScopeGroupId(), false,
						"/categorization/new-vocabulary"),
					_themeDisplay)
			).put(
				"icon", _ICONS[_ICONS.length - 1]
			).put(
				"label",
				LanguageUtil.get(_themeDisplay.getLocale(), "vocabulary")
			).build());

		return quickActions;
	}

	private Map<String, String> _createQuickAction(
		String icon, Group group, ObjectDefinition objectDefinition) {

		return HashMapBuilder.put(
			"href",
			StringBundler.concat(
				_themeDisplay.getPortalURL(), _themeDisplay.getPathMain(),
				GroupConstants.CMS_FRIENDLY_URL,
				"/add_structured_content_item?groupId=", group.getGroupId(),
				"&name=", group.getName(_themeDisplay.getLocale()),
				"&objectDefinitionId=",
				objectDefinition.getObjectDefinitionId(),
				"&objectEntryFolderExternalReferenceCode=",
				_getObjectEntryFolderExternalReferenceCode(objectDefinition),
				"&plid=", _themeDisplay.getPlid(), "&redirect=",
				_themeDisplay.getURLCurrent())
		).put(
			"icon", icon
		).put(
			"label", objectDefinition.getLabel(_themeDisplay.getLocale())
		).build();
	}

	private Group _getDepotEntryGroup() {
		List<Long> groupIds = TransformUtil.transform(
			_depotEntryLocalService.getDepotEntries(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS),
			DepotEntry::getGroupId);

		return _groupLocalService.fetchGroup(groupIds.get(0));
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

	private static final String[] _ICONS = {
		"forms", "blogs", "wiki", "documents-and-media", "vocabulary"
	};

	private final DepotEntryLocalService _depotEntryLocalService;
	private final GroupLocalService _groupLocalService;
	private final ObjectDefinitionService _objectDefinitionService;
	private final ThemeDisplay _themeDisplay;

}