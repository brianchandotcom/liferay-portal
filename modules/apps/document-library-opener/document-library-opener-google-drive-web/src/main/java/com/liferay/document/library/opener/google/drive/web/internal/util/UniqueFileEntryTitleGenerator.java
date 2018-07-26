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

package com.liferay.document.library.opener.google.drive.web.internal.util;

import com.liferay.document.library.kernel.exception.FileNameException;
import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;

import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = UniqueFileEntryTitleGenerator.class)
public class UniqueFileEntryTitleGenerator {

	public String getUniqueFileEntryTitle(
			long groupId, long folderId, Locale locale)
		throws PortalException {

		ResourceBundle resourceBundle =
			_resourceBundleLoader.loadResourceBundle(locale);

		for (int i = 1; i < _MAXIMUM_TRIES; i++) {
			String title = _getTitle(resourceBundle, i);

			try {
				_dlAppLocalService.getFileEntry(groupId, folderId, title);
			}
			catch (NoSuchFileEntryException nsfee) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						StringBundler.concat(
							"Unique file entry title ", title,
							" successfully generated in folder ", folderId),
						nsfee);
				}

				return title;
			}
		}

		throw new FileNameException(
			StringBundler.concat(
				"Could not generate an unique file name in Folder ", folderId,
				" after ", _MAXIMUM_TRIES, " tries"));
	}

	private String _getTitle(ResourceBundle resourceBundle, int n) {
		return String.format(
			"%s %d", _language.get(resourceBundle, "untitled"), n);
	}

	private static final int _MAXIMUM_TRIES = 100;

	private static final Log _log = LogFactoryUtil.getLog(
		UniqueFileEntryTitleGenerator.class);

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private Language _language;

	@Reference(
		target = "(bundle.symbolic.name=com.liferay.document.library.opener.google.drive.web)"
	)
	private ResourceBundleLoader _resourceBundleLoader;

}