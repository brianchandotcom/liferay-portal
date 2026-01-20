/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.service;

import com.liferay.depot.constants.DepotConstants;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalServiceWrapper;
import com.liferay.object.constants.ObjectEntryFolderConstants;
import com.liferay.object.entry.folder.util.ObjectEntryFolderThreadLocal;
import com.liferay.object.service.ObjectEntryFolderLocalService;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.site.cms.site.initializer.internal.util.ObjectEntryFolderUtil;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 * @author Roberto Díaz
 */
@Component(service = ServiceWrapper.class)
public class CMSObjectEntryFolderDepotEntryLocalServiceWrapper
	extends DepotEntryLocalServiceWrapper {

	@Override
	public DepotEntry addDepotEntry(Group group, ServiceContext serviceContext)
		throws PortalException {

		DepotEntry depotEntry = super.addDepotEntry(group, serviceContext);

		if (depotEntry.getType() == DepotConstants.TYPE_SPACE) {
			ObjectEntryFolderUtil.addObjectEntryFolders(
				depotEntry.getGroupId());
		}

		return depotEntry;
	}

	@Override
	public DepotEntry addDepotEntry(
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			int type, ServiceContext serviceContext)
		throws PortalException {

		DepotEntry depotEntry = super.addDepotEntry(
			nameMap, descriptionMap, type, serviceContext);

		if (depotEntry.getType() == DepotConstants.TYPE_SPACE) {
			ObjectEntryFolderUtil.addObjectEntryFolders(
				depotEntry.getGroupId());
		}

		return depotEntry;
	}

	@Override
	public DepotEntry deleteDepotEntry(DepotEntry depotEntry)
		throws PortalException {

		_deleteObjectEntryFolders(depotEntry);

		return super.deleteDepotEntry(depotEntry);
	}

	@Override
	public DepotEntry deleteDepotEntry(long depotEntryId)
		throws PortalException {

		_deleteObjectEntryFolders(getDepotEntry(depotEntryId));

		return super.deleteDepotEntry(depotEntryId);
	}

	private void _deleteObjectEntryFolders(DepotEntry depotEntry)
		throws PortalException {

		if (depotEntry.getType() == DepotConstants.TYPE_SPACE) {
			try (SafeCloseable safeCloseable =
					ObjectEntryFolderThreadLocal.
						setForceDeleteSystemObjectEntryFolderWithSafeCloseable(
							true)) {

				_objectEntryFolderLocalService.
					deleteObjectEntryFolderByExternalReferenceCode(
						ObjectEntryFolderConstants.
							EXTERNAL_REFERENCE_CODE_CONTENTS,
						depotEntry.getGroupId(), depotEntry.getCompanyId());
				_objectEntryFolderLocalService.
					deleteObjectEntryFolderByExternalReferenceCode(
						ObjectEntryFolderConstants.
							EXTERNAL_REFERENCE_CODE_FILES,
						depotEntry.getGroupId(), depotEntry.getCompanyId());
			}
		}
	}

	@Reference
	private ObjectEntryFolderLocalService _objectEntryFolderLocalService;

}