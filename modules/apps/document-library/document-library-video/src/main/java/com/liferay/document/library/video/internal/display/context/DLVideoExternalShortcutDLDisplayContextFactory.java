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

package com.liferay.document.library.video.internal.display.context;

import com.liferay.document.library.display.context.BaseDLDisplayContextFactory;
import com.liferay.document.library.display.context.DLDisplayContextFactory;
import com.liferay.document.library.display.context.DLEditFileEntryDisplayContext;
import com.liferay.document.library.display.context.DLViewFileVersionDisplayContext;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.video.external.shortcut.DLVideoExternalShortcut;
import com.liferay.document.library.video.external.shortcut.resolver.DLVideoExternalShortcutResolver;
import com.liferay.document.library.video.internal.constants.DLVideoConstants;
import com.liferay.document.library.video.internal.helper.DLVideoExternalShortcutMetadataHelper;
import com.liferay.document.library.video.internal.helper.DLVideoExternalShortcutMetadataHelperFactory;
import com.liferay.dynamic.data.mapping.kernel.DDMStructure;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.repository.model.FileVersion;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera
 * @author Alejandro Tardín
 */
@Component(
	immediate = true, property = "service.ranking:Integer=-100",
	service = DLDisplayContextFactory.class
)
public class DLVideoExternalShortcutDLDisplayContextFactory
	extends BaseDLDisplayContextFactory {

	@Override
	public DLEditFileEntryDisplayContext getDLEditFileEntryDisplayContext(
		DLEditFileEntryDisplayContext parentDLEditFileEntryDisplayContext,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse,
		DLFileEntryType dlFileEntryType) {

		DDMStructure dlVideoExternalShortcutDDMStructure =
			DLVideoExternalShortcutMetadataHelper.
				getDLVideoExternalShortcutDDMStructure(dlFileEntryType);

		if (dlVideoExternalShortcutDDMStructure != null) {
			return new DLVideoExternalShortcutDLEditFileEntryDisplayContext(
				parentDLEditFileEntryDisplayContext, httpServletRequest,
				httpServletResponse, dlFileEntryType, _servletContext);
		}

		return parentDLEditFileEntryDisplayContext;
	}

	@Override
	public DLEditFileEntryDisplayContext getDLEditFileEntryDisplayContext(
		DLEditFileEntryDisplayContext parentDLEditFileEntryDisplayContext,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, FileEntry fileEntry) {

		DLVideoExternalShortcutMetadataHelper
			dlVideoExternalShortcutMetadataHelper =
				_dlVideoExternalShortcutMetadataHelperFactory.
					getDLVideoExternalShortcutMetadataHelper(fileEntry);

		if ((dlVideoExternalShortcutMetadataHelper != null) &&
			dlVideoExternalShortcutMetadataHelper.isExternalShortcut()) {

			return new DLVideoExternalShortcutDLEditFileEntryDisplayContext(
				parentDLEditFileEntryDisplayContext, httpServletRequest,
				httpServletResponse, fileEntry,
				_getDLVideoExternalShortcut(
					dlVideoExternalShortcutMetadataHelper),
				_servletContext);
		}

		return parentDLEditFileEntryDisplayContext;
	}

	@Override
	public DLViewFileVersionDisplayContext getDLViewFileVersionDisplayContext(
		DLViewFileVersionDisplayContext parentDLViewFileVersionDisplayContext,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, FileShortcut fileShortcut) {

		try {
			long fileEntryId = fileShortcut.getToFileEntryId();

			FileEntry fileEntry = _dlAppService.getFileEntry(fileEntryId);

			return getDLViewFileVersionDisplayContext(
				parentDLViewFileVersionDisplayContext, httpServletRequest,
				httpServletResponse, fileEntry.getFileVersion());
		}
		catch (PortalException portalException) {
			throw new SystemException(
				"Unable to build document library view file version display " +
					"context for file shortcut " + fileShortcut.getPrimaryKey(),
				portalException);
		}
	}

	@Override
	public DLViewFileVersionDisplayContext getDLViewFileVersionDisplayContext(
		DLViewFileVersionDisplayContext parentDLViewFileVersionDisplayContext,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, FileVersion fileVersion) {

		DLVideoExternalShortcutMetadataHelper
			dlVideoExternalShortcutMetadataHelper =
				_dlVideoExternalShortcutMetadataHelperFactory.
					getDLVideoExternalShortcutMetadataHelper(fileVersion);

		if ((dlVideoExternalShortcutMetadataHelper != null) &&
			dlVideoExternalShortcutMetadataHelper.isExternalShortcut()) {

			return new DLVideoExternalShortcutDLViewFileVersionDisplayContext(
				parentDLViewFileVersionDisplayContext, httpServletRequest,
				httpServletResponse, fileVersion);
		}

		return parentDLViewFileVersionDisplayContext;
	}

	private DLVideoExternalShortcut _getDLVideoExternalShortcut(
		DLVideoExternalShortcutMetadataHelper
			dlVideoExternalShortcutMetadataHelper) {

		if (dlVideoExternalShortcutMetadataHelper.containsField(
				DLVideoConstants.DDM_FIELD_NAME_URL)) {

			return _dlVideoExternalShortcutResolver.resolve(
				dlVideoExternalShortcutMetadataHelper.getFieldValue(
					DLVideoConstants.DDM_FIELD_NAME_URL));
		}

		return null;
	}

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private DLVideoExternalShortcutMetadataHelperFactory
		_dlVideoExternalShortcutMetadataHelperFactory;

	@Reference
	private DLVideoExternalShortcutResolver _dlVideoExternalShortcutResolver;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.document.library.video)"
	)
	private ServletContext _servletContext;

}