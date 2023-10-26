/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saved.content.internal.security.permission.resource;

import com.liferay.exportimport.kernel.staging.permission.StagingPermission;
import com.liferay.portal.kernel.security.permission.resource.BaseModelResourcePermissionWrapper;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.StagedModelPermissionLogic;
import com.liferay.saved.content.constants.MySavedContentPortletKeys;
import com.liferay.saved.content.constants.SavedContentConstants;
import com.liferay.saved.content.model.SavedContentEntry;
import com.liferay.saved.content.service.SavedContentEntryLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alicia García
 */
@Component(
	property = "model.class.name=com.liferay.saved.content.model.SavedContentEntry",
	service = ModelResourcePermission.class
)
public class SavedContentEntryModelResourcePermissionWrapper
	extends BaseModelResourcePermissionWrapper<SavedContentEntry> {

	@Override
	protected ModelResourcePermission<SavedContentEntry>
		doGetModelResourcePermission() {

		return ModelResourcePermissionFactory.create(
			SavedContentEntry.class, SavedContentEntry::getSavedContentEntryId,
			_savedContentEntryLocalService::getSavedContentEntry,
			_portletResourcePermission,
			(modelResourcePermission, consumer) -> consumer.accept(
				new StagedModelPermissionLogic<>(
					_stagingPermission,
					MySavedContentPortletKeys.MY_SAVED_CONTENT,
					SavedContentEntry::getSavedContentEntryId)));
	}

	@Reference(
		target = "(resource.name=" + SavedContentConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

	@Reference
	private SavedContentEntryLocalService _savedContentEntryLocalService;

	@Reference
	private StagingPermission _stagingPermission;

}