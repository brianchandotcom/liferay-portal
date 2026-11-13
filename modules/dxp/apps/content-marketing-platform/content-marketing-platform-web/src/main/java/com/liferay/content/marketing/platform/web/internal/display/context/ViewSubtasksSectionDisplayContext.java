/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.content.marketing.platform.web.internal.display.context;

import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.content.marketing.platform.web.internal.constants.CMPActionConstants;
import com.liferay.content.marketing.platform.web.internal.constants.CMPSiteInitializerFDSNames;
import com.liferay.content.marketing.platform.web.internal.util.ActionUtil;
import com.liferay.content.marketing.platform.web.internal.util.ObjectEntryUtil;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectEntryService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectStateFlowLocalService;
import com.liferay.object.service.ObjectStateLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.RoleService;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.site.cms.site.initializer.users.provider.CMSUsersProvider;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

/**
 * @author Larissa Ribeiro
 */
public class ViewSubtasksSectionDisplayContext
	extends BaseTasksSectionDisplayContext {

	public ViewSubtasksSectionDisplayContext(
		AssetTagLocalService assetTagLocalService,
		ClassNameLocalService classNameLocalService,
		ObjectDefinition cmpProjectObjectDefinition,
		ObjectDefinition cmpTaskObjectDefinition,
		CMSUsersProvider cmsUsersProvider,
		DepotEntryLocalService depotEntryLocalService,
		HttpServletRequest httpServletRequest,
		ListTypeEntryLocalService listTypeEntryLocalService,
		ObjectEntryLocalService objectEntryLocalService,
		ObjectEntryService objectEntryService,
		ObjectFieldLocalService objectFieldLocalService,
		ObjectStateFlowLocalService objectStateFlowLocalService,
		ObjectStateLocalService objectStateLocalService,
		RoleService roleService) {

		super(
			assetTagLocalService, classNameLocalService,
			cmpProjectObjectDefinition, cmpTaskObjectDefinition,
			cmsUsersProvider, depotEntryLocalService, httpServletRequest,
			listTypeEntryLocalService, objectEntryService,
			objectFieldLocalService, objectStateFlowLocalService,
			objectStateLocalService, roleService);

		_objectEntryLocalService = objectEntryLocalService;

		_objectEntry = ObjectEntryUtil.getObjectEntry(httpServletRequest);
	}

	@Override
	public String getAPIURL() {
		return StringBundler.concat(
			"/o/search/v1.0/search?emptySearch=true&filter=(",
			"objectDefinitionId eq ", objectDefinition.getObjectDefinitionId(),
			" and cmpParentTaskId eq ", _objectEntry.getObjectEntryId(), ")",
			getNestedFieldsAPIURLParameters("cmpProjectToCMPTasks"));
	}

	@Override
	public CreationMenu getCreationMenu() throws Exception {
		if (!hasAddObjectEntryPortletResourcePermission()) {
			return null;
		}

		return CreationMenuBuilder.addPrimaryDropdownItem(
			dropdownItem -> {
				dropdownItem.putData("action", CMPActionConstants.CREATE_TASK);
				dropdownItem.putData(
					"redirect",
					StringBundler.concat(
						ActionUtil.getAddTaskURL(
							_objectEntry.getGroupId(), objectDefinition,
							MapUtil.getLong(
								_objectEntry.getValues(),
								"r_cmpProjectToCMPTasks_c_cmpProjectId"),
							themeDisplay),
						"&parentId=", _objectEntry.getObjectEntryId()));
				dropdownItem.setIcon("forms");
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "new-subtask"));
			}
		).build();
	}

	@Override
	public List<FDSActionDropdownItem> getFDSActionDropdownItems() {
		return getProjectTasksFDSActionDropdownItems(
			objectDefinition.getClassName());
	}

	public Map<String, Object> getSubtasksTitleProps() {
		return HashMapBuilder.<String, Object>put(
			"apiURL", getAPIURL()
		).put(
			"fdsId", CMPSiteInitializerFDSNames.CMP_SUBTASKS
		).build();
	}

	public boolean isVisible() {
		if ((_objectEntry == null) ||
			!FeatureFlagManagerUtil.isEnabled(
				_objectEntry.getCompanyId(), "LPD-107678")) {

			return false;
		}

		ObjectEntry parentCMPTaskObjectEntry =
			_objectEntryLocalService.fetchObjectEntry(
				MapUtil.getLong(
					_objectEntry.getValues(),
					"r_cmpTaskToCMPTasks_c_cmpTaskId"));

		if (parentCMPTaskObjectEntry == null) {
			return true;
		}

		long grandparentCMPTaskObjectEntryId = MapUtil.getLong(
			parentCMPTaskObjectEntry.getValues(),
			"r_cmpTaskToCMPTasks_c_cmpTaskId");

		if (grandparentCMPTaskObjectEntryId == 0) {
			return true;
		}

		return false;
	}

	private final ObjectEntry _objectEntry;
	private final ObjectEntryLocalService _objectEntryLocalService;

}