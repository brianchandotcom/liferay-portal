/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.model.listener;

import com.liferay.object.constants.ObjectEntryFolderConstants;
import com.liferay.object.service.ObjectEntryFolderLocalService;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.site.cms.site.initializer.internal.constants.CMSSiteInitializerConstants;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = ModelListener.class)
public class GroupModelListener extends BaseModelListener<Group> {

	@Override
	public void onAfterCreate(Group group) throws ModelListenerException {
		try {
			super.onAfterCreate(group);

			if (!FeatureFlagManagerUtil.isEnabled(
					group.getCompanyId(), "LPD-17809") ||
				!group.isDepot()) {

				return;
			}

			_objectEntryFolderLocalService.addObjectEntryFolder(
				CMSSiteInitializerConstants.
					CONTENT_OBJECT_ENTRY_FOLDER_EXTERNAL_REFERENCE_CODE,
				group.getCreatorUserId(), group.getGroupId(),
				ObjectEntryFolderConstants.
					PARENT_OBJECT_ENTRY_FOLDER_ID_DEFAULT,
				HashMapBuilder.put(
					LocaleUtil.ENGLISH, "Content"
				).build(),
				"Content", ServiceContextThreadLocal.getServiceContext());
			_objectEntryFolderLocalService.addObjectEntryFolder(
				CMSSiteInitializerConstants.
					FILES_OBJECT_ENTRY_FOLDER_EXTERNAL_REFERENCE_CODE,
				group.getCreatorUserId(), group.getGroupId(),
				ObjectEntryFolderConstants.
					PARENT_OBJECT_ENTRY_FOLDER_ID_DEFAULT,
				HashMapBuilder.put(
					LocaleUtil.ENGLISH, "Files"
				).build(),
				"Files", ServiceContextThreadLocal.getServiceContext());
		}
		catch (PortalException portalException) {
			throw new ModelListenerException(portalException);
		}
	}

	@Override
	public void onBeforeRemove(Group group) throws ModelListenerException {
		try {
			super.onBeforeRemove(group);

			if (!FeatureFlagManagerUtil.isEnabled(
					group.getCompanyId(), "LPD-17809") ||
				!group.isDepot()) {

				return;
			}

			_objectEntryFolderLocalService.deleteObjectEntryFolder(
				CMSSiteInitializerConstants.
					CONTENT_OBJECT_ENTRY_FOLDER_EXTERNAL_REFERENCE_CODE,
				group.getCompanyId(), group.getGroupId());
			_objectEntryFolderLocalService.deleteObjectEntryFolder(
				CMSSiteInitializerConstants.
					FILES_OBJECT_ENTRY_FOLDER_EXTERNAL_REFERENCE_CODE,
				group.getCompanyId(), group.getGroupId());
		}
		catch (PortalException portalException) {
			throw new ModelListenerException(portalException);
		}
	}

	@Reference
	private ObjectEntryFolderLocalService _objectEntryFolderLocalService;

}