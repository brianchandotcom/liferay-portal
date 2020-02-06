/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.depot.service.impl;

import com.liferay.depot.constants.DepotActionKeys;
import com.liferay.depot.constants.DepotConstants;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.base.DepotEntryServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.permission.GroupPermission;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = {
		"json.web.service.context.name=depot",
		"json.web.service.context.path=DepotEntry"
	},
	service = AopService.class
)
public class DepotEntryServiceImpl extends DepotEntryServiceBaseImpl {

	@Override
	public DepotEntry addDepotEntry(
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			DepotActionKeys.ADD_DEPOT_ENTRY);

		return depotEntryLocalService.addDepotEntry(
			nameMap, descriptionMap, serviceContext);
	}

	@Override
	public DepotEntry deleteDepotEntry(long depotEntryId)
		throws PortalException {

		_depotEntryModelResourcePermission.check(
			getPermissionChecker(), depotEntryId, ActionKeys.DELETE);

		return depotEntryLocalService.deleteDepotEntry(depotEntryId);
	}

	@Override
	public DepotEntry getDepotEntry(long depotEntryId) throws PortalException {
		_depotEntryModelResourcePermission.check(
			getPermissionChecker(), depotEntryId, ActionKeys.VIEW);

		return depotEntryLocalService.getDepotEntry(depotEntryId);
	}

	@Override
	public List<DepotEntry> getGroupConnectedDepotEntries(
			long groupId, int start, int end)
		throws PortalException {

		if (!_groupPermission.contains(
				getPermissionChecker(), groupId, ActionKeys.VIEW)) {

			return Collections.emptyList();
		}

		return depotEntryLocalService.getGroupConnectedDepotEntries(
			groupId, start, end);
	}

	@Override
	public int getGroupConnectedDepotEntriesCount(long groupId)
		throws PortalException {

		if (!_groupPermission.contains(
				getPermissionChecker(), groupId, ActionKeys.VIEW)) {

			return 0;
		}

		return depotEntryLocalService.getGroupConnectedDepotEntriesCount(
			groupId);
	}

	@Override
	public DepotEntry getGroupDepotEntry(long groupId) throws PortalException {
		DepotEntry depotEntry = depotEntryLocalService.getGroupDepotEntry(
			groupId);

		_depotEntryModelResourcePermission.check(
			getPermissionChecker(), depotEntry, ActionKeys.VIEW);

		return depotEntry;
	}

	@Override
	public DepotEntry updateDepotEntry(
			long depotEntryId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap,
			Map<String, Boolean> depotAppCustomizationMap,
			UnicodeProperties typeSettingsProperties,
			ServiceContext serviceContext)
		throws PortalException {

		_depotEntryModelResourcePermission.check(
			getPermissionChecker(), depotEntryId, ActionKeys.UPDATE);

		return depotEntryLocalService.updateDepotEntry(
			depotEntryId, nameMap, descriptionMap, depotAppCustomizationMap,
			typeSettingsProperties, serviceContext);
	}

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(model.class.name=com.liferay.depot.model.DepotEntry)"
	)
	private volatile ModelResourcePermission<DepotEntry>
		_depotEntryModelResourcePermission;

	@Reference
	private GroupPermission _groupPermission;

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(resource.name=" + DepotConstants.RESOURCE_NAME + ")"
	)
	private volatile PortletResourcePermission _portletResourcePermission;

}