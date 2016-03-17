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

package com.liferay.document.library.asset;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portlet.asset.util.AssetEntryValidator;
import com.liferay.portlet.asset.util.BaseAssetEntryValidator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true,
	property = {
		"model.class.name=com.liferay.document.library.kernel.model.DLFileEntry"
	},
	service = AssetEntryValidator.class
)
public class FileEntryAssetValidator extends BaseAssetEntryValidator {

	@Override
	public void validate(
			long groupId, String className, long classTypePK,
			long[] categoryIds, String[] entryNames)
		throws PortalException {

		DLFileEntry dlFileEntry = _dlFileEntryLocalService.fetchDLFileEntry(
			classTypePK);

		if ((dlFileEntry == null) ||
			(dlFileEntry.getRepositoryId() != groupId)) {

			return;
		}

		super.validate(
			groupId, className, classTypePK, categoryIds, entryNames);
	}

	@Reference(unbind = "-")
	protected void setDLFileEntryLocalService(
		DLFileEntryLocalService dlFileEntryLocalService) {

		_dlFileEntryLocalService = dlFileEntryLocalService;
	}

	private DLFileEntryLocalService _dlFileEntryLocalService;

}