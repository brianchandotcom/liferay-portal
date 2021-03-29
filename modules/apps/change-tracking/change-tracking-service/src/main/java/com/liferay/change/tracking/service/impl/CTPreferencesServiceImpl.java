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
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTPreferences;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.base.CTPreferencesServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author @author Preston Crary
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
			long companyId, long ctCollectionId, long userId)
		throws PortalException {

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

	@Reference
	private CTCollectionLocalService _ctCollectionLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.change.tracking.model.CTCollection)"
	)
	private ModelResourcePermission<CTCollection>
		_ctCollectionModelResourcePermission;

}