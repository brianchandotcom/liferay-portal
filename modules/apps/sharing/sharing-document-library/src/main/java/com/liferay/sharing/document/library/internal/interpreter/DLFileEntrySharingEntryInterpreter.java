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

package com.liferay.sharing.document.library.internal.interpreter;

import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.sharing.document.library.internal.renderer.DLFileEntrySharingEntryEditRenderer;
import com.liferay.sharing.document.library.internal.renderer.DLFileEntrySharingEntryViewRenderer;
import com.liferay.sharing.interpreter.SharingEntryInterpreter;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.renderer.SharingEntryEditRenderer;
import com.liferay.sharing.renderer.SharingEntryViewRenderer;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.Locale;

/**
 * @author Sergio González
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.document.library.kernel.model.DLFileEntry",
	service = SharingEntryInterpreter.class
)
public class DLFileEntrySharingEntryInterpreter
	implements SharingEntryInterpreter<FileEntry> {

	@Override
	public String getAssetTypeTitle(SharingEntry sharingEntry, Locale locale) {
		return LanguageUtil.get(locale, "document");
	}

	@Override
	public FileEntry getEntry(SharingEntry sharingEntry)
		throws PortalException {

		return _dlAppLocalService.getFileEntry(sharingEntry.getClassPK());
	}

	@Override
	public SharingEntryEditRenderer<FileEntry> getSharingEntryEditRenderer() {
		return _dlFileEntrySharingEntryEditRenderer;
	}

	@Override
	public SharingEntryViewRenderer<FileEntry> getSharingEntryViewRenderer() {
		return _dlFileEntrySharingEntryViewRenderer;
	}

	@Override
	public String getTitle(SharingEntry sharingEntry) {
		try {
			FileEntry fileEntry = getEntry(sharingEntry);

			return fileEntry.getTitle();
		}
		catch (PortalException pe) {
			_log.error(pe, pe);
		}

		return StringPool.BLANK;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SharingEntryInterpreter.class);

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private DLFileEntrySharingEntryEditRenderer
		_dlFileEntrySharingEntryEditRenderer;

	@Reference
	private DLFileEntrySharingEntryViewRenderer
		_dlFileEntrySharingEntryViewRenderer;

}