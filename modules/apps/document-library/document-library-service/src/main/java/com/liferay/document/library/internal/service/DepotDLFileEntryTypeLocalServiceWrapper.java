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

package com.liferay.document.library.internal.service;

import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
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

	public DepotDLFileEntryTypeLocalServiceWrapper() {
		super(null);
	}

	public DepotDLFileEntryTypeLocalServiceWrapper(
		DLFileEntryTypeLocalService dlFileEntryTypeLocalService) {

		super(dlFileEntryTypeLocalService);
	}

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