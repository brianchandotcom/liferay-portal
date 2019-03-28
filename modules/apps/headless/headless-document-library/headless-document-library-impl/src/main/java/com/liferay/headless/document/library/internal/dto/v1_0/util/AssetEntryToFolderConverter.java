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

package com.liferay.headless.document.library.internal.dto.v1_0.util;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.headless.common.spi.osgi.AssetEntryToDTOConverter;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rubén Pulido
 * @author Víctor Galán
 */
@Component(
	property = "asset.entry.to.dto.converter.class.name=com.liferay.portal.kernel.repository.model.Folder",
	service = AssetEntryToDTOConverter.class
)
public class AssetEntryToFolderConverter implements AssetEntryToDTOConverter {

	@Override
	public Object toDTO(
		AssetEntry assetEntry, AcceptLanguage acceptLanguage, UriInfo uriInfo) {

		try {
			return FolderUtil.toFolder(
				_dlAppService.getFolder(assetEntry.getClassPK()), _dlAppService,
				_portal, _userLocalService);
		}
		catch (Exception e) {
			throw new NotFoundException(e);
		}
	}

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}