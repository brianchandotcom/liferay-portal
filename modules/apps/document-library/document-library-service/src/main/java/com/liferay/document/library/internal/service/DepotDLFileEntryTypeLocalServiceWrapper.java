/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.service;

import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalServiceWrapper;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = ServiceWrapper.class)
public class DepotDLFileEntryTypeLocalServiceWrapper
	extends DLFileEntryTypeLocalServiceWrapper {

	@Override
	public List<DLFileEntryType> getFolderFileEntryTypes(
			long[] groupIds, long folderId, boolean inherited)
		throws PortalException {

		return super.getFolderFileEntryTypes(
			ArrayUtil.append(
				groupIds, _getGroupConnectedDepotGroupIds(groupIds[0])),
			folderId, inherited);
	}

	private long[] _getGroupConnectedDepotGroupIds(long groupId)
		throws PortalException {

		return ListUtil.toLongArray(
			_depotEntryLocalService.getGroupConnectedDepotEntries(
				groupId, true, QueryUtil.ALL_POS, QueryUtil.ALL_POS),
			DepotEntry::getGroupId);
	}

	@Reference
	private DepotEntryLocalService _depotEntryLocalService;

}