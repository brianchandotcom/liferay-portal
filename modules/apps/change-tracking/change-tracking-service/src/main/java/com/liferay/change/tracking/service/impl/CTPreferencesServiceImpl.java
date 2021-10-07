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

package com.liferay.change.tracking.service.impl;

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.change.tracking.exception.CTStagingEnabledException;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTPreferences;
import com.liferay.change.tracking.model.CTPreferencesTable;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTPreferencesLocalService;
import com.liferay.change.tracking.service.base.CTPreferencesServiceBaseImpl;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.GroupTable;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.permission.PortletPermission;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(
	property = {
		"json.web.service.context.name=ct",
		"json.web.service.context.path=CTPreferences"
	},
	service = AopService.class
)
public class CTPreferencesServiceImpl extends CTPreferencesServiceBaseImpl {

	@Override
	public CTPreferences checkoutCTCollection(
			long companyId, long userId, long ctCollectionId)
		throws PortalException {

		if (userId == 0) {
			return enablePublications(companyId, true);
		}

		if (ctCollectionId != CTConstants.CT_COLLECTION_ID_PRODUCTION) {
			CTCollection ctCollection =
				_ctCollectionLocalService.fetchCTCollection(ctCollectionId);

			if ((ctCollection == null) ||
				(ctCollection.getStatus() != WorkflowConstants.STATUS_DRAFT)) {

				return null;
			}

			_ctCollectionModelResourcePermission.check(
				getPermissionChecker(), ctCollection, ActionKeys.UPDATE);
		}

		CTPreferences ctPreferences =
			ctPreferencesLocalService.getCTPreferences(companyId, userId);

		long currentCtCollectionId = ctPreferences.getCtCollectionId();

		if (currentCtCollectionId != ctCollectionId) {
			ctPreferences.setCtCollectionId(ctCollectionId);

			if (ctCollectionId == CTConstants.CT_COLLECTION_ID_PRODUCTION) {
				ctPreferences.setPreviousCtCollectionId(currentCtCollectionId);
			}
			else {
				ctPreferences.setPreviousCtCollectionId(
					CTConstants.CT_COLLECTION_ID_PRODUCTION);
			}

			ctPreferences = ctPreferencesPersistence.update(ctPreferences);
		}

		return ctPreferences;
	}

	@Override
	public CTPreferences enablePublications(long companyId, boolean enable)
		throws PortalException {

		_portletPermission.check(
			getPermissionChecker(), CTPortletKeys.PUBLICATIONS,
			ActionKeys.CONFIGURATION);

		if (enable) {
			for (Group group :
					_groupLocalService.<List<Group>>dslQuery(
						DSLQueryFactoryUtil.select(
							GroupTable.INSTANCE
						).from(
							GroupTable.INSTANCE
						).where(
							GroupTable.INSTANCE.companyId.eq(
								companyId
							).and(
								GroupTable.INSTANCE.liveGroupId.neq(
									GroupConstants.DEFAULT_LIVE_GROUP_ID
								).or(
									GroupTable.INSTANCE.typeSettings.like(
										"%staged=true%")
								).withParentheses()
							)
						))) {

				if (group.isStaged() || group.isStagingGroup()) {
					throw new CTStagingEnabledException();
				}
			}

			Role role = _roleLocalService.getRole(
				companyId, RoleConstants.PUBLICATIONS_USER);

			_resourcePermissionLocalService.addResourcePermission(
				companyId, CTPortletKeys.PUBLICATIONS,
				ResourceConstants.SCOPE_COMPANY, String.valueOf(companyId),
				role.getRoleId(), ActionKeys.ACCESS_IN_CONTROL_PANEL);
			_resourcePermissionLocalService.addResourcePermission(
				companyId, CTPortletKeys.PUBLICATIONS,
				ResourceConstants.SCOPE_COMPANY, String.valueOf(companyId),
				role.getRoleId(), ActionKeys.VIEW);

			return _ctPreferencesLocalService.getCTPreferences(companyId, 0);
		}

		for (CTPreferences ctPreferences :
				ctPreferencesPersistence.<List<CTPreferences>>dslQuery(
					DSLQueryFactoryUtil.select(
						CTPreferencesTable.INSTANCE
					).from(
						CTPreferencesTable.INSTANCE
					).where(
						CTPreferencesTable.INSTANCE.companyId.eq(companyId)
					))) {

			ctPreferencesPersistence.remove(ctPreferences);
		}

		return null;
	}

	@Reference
	private CTCollectionLocalService _ctCollectionLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.change.tracking.model.CTCollection)"
	)
	private ModelResourcePermission<CTCollection>
		_ctCollectionModelResourcePermission;

	@Reference
	private CTPreferencesLocalService _ctPreferencesLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private PortletPermission _portletPermission;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserLocalService _userLocalService;

}