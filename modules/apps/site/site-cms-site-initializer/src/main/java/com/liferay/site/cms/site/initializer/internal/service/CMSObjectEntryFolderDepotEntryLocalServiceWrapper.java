/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.service;

import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalServiceWrapper;
import com.liferay.object.constants.ObjectEntryFolderConstants;
import com.liferay.object.service.ObjectEntryFolderLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(service = ServiceWrapper.class)
public class CMSObjectEntryFolderDepotEntryLocalServiceWrapper
	extends DepotEntryLocalServiceWrapper {

	@Override
	public DepotEntry addDepotEntry(Group group, ServiceContext serviceContext)
		throws PortalException {

		DepotEntry depotEntry = super.addDepotEntry(group, serviceContext);

		_addObjectEntryFolders(depotEntry);

		return depotEntry;
	}

	@Override
	public DepotEntry addDepotEntry(
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			int type, ServiceContext serviceContext)
		throws PortalException {

		DepotEntry depotEntry = super.addDepotEntry(
			nameMap, descriptionMap, type, serviceContext);

		_addObjectEntryFolders(depotEntry);

		return depotEntry;
	}

	private void _addObjectEntryFolders(DepotEntry depotEntry)
		throws PortalException {

		if (!FeatureFlagManagerUtil.isEnabled(
				depotEntry.getCompanyId(), "LPD-17564")) {

			return;
		}

		Group group = depotEntry.getGroup();

		_objectEntryFolderLocalService.addObjectEntryFolder(
			ObjectEntryFolderConstants.EXTERNAL_REFERENCE_CODE_CONTENTS,
			group.getGroupId(), group.getCreatorUserId(),
			ObjectEntryFolderConstants.PARENT_OBJECT_ENTRY_FOLDER_ID_DEFAULT,
			"",
			HashMapBuilder.put(
				LocaleUtil.ENGLISH, "Contents"
			).build(),
			"Contents", ServiceContextThreadLocal.getServiceContext());
		_objectEntryFolderLocalService.addObjectEntryFolder(
			ObjectEntryFolderConstants.EXTERNAL_REFERENCE_CODE_FILES,
			group.getGroupId(), group.getCreatorUserId(),
			ObjectEntryFolderConstants.PARENT_OBJECT_ENTRY_FOLDER_ID_DEFAULT,
			"",
			HashMapBuilder.put(
				LocaleUtil.ENGLISH, "Files"
			).build(),
			"Files", ServiceContextThreadLocal.getServiceContext());
	}

	@Reference
	private ObjectEntryFolderLocalService _objectEntryFolderLocalService;

}