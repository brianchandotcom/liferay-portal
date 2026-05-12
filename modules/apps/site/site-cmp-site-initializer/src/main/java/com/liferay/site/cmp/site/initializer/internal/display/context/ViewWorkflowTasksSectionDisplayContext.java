/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cmp.site.initializer.internal.display.context;

import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.frontend.data.set.model.FDSActionDropdownItemBuilder;
import com.liferay.frontend.data.set.model.FDSActionDropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectEntryService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectStateFlowLocalService;
import com.liferay.object.service.ObjectStateLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.RoleService;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Collections;
import java.util.List;

/**
 * @author Fábio Alves
 */
public class ViewWorkflowTasksSectionDisplayContext
	extends BaseTasksSectionDisplayContext {

	public ViewWorkflowTasksSectionDisplayContext(
		AssetTagLocalService assetTagLocalService,
		ClassNameLocalService classNameLocalService,
		DepotEntryLocalService depotEntryLocalService,
		HttpServletRequest httpServletRequest,
		ListTypeEntryLocalService listTypeEntryLocalService,
		ObjectEntryService objectEntryService,
		ObjectFieldLocalService objectFieldLocalService,
		ObjectStateFlowLocalService objectStateFlowLocalService,
		ObjectStateLocalService objectStateLocalService,
		ObjectDefinition projectObjectDefinition, RoleService roleService,
		ObjectDefinition taskObjectDefinition) {

		super(
			assetTagLocalService, classNameLocalService, depotEntryLocalService,
			httpServletRequest, listTypeEntryLocalService, objectEntryService,
			objectFieldLocalService, objectStateFlowLocalService,
			objectStateLocalService, projectObjectDefinition, roleService,
			taskObjectDefinition);
	}

	@Override
	public String getAPIURL() {
		StringBundler sb = new StringBundler(5);

		sb.append("/o/search/v1.0/search?emptySearch=true&entryClassNames=");
		sb.append(KaleoTaskInstanceToken.class.getName());
		sb.append("&filter=keywords/any(k:startswith(k, '");
		sb.append(objectDefinition.getExternalReferenceCode());
		sb.append("'))&nestedFields=embedded");

		return sb.toString();
	}

	@Override
	public List<DropdownItem> getBulkActionDropdownItems() {
		return Collections.emptyList();
	}

	@Override
	public List<FDSActionDropdownItem> getFDSActionDropdownItems() {
		return FDSActionDropdownItemList.of(
			getWorkflowTransitionsGroupFDSActionDropdownItem(),
			FDSActionDropdownItemBuilder.setFDSActionDropdownItems(
				getWorkflowTasksFDSActionDropdownItems()
			).setSeparator(
				true
			).setType(
				"group"
			).build(
				"other-actions"
			));
	}

}