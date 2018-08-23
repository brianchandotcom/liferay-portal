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

import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.upload.UniqueFileNameProvider;

import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = UniqueFileEntryTitleProvider.class)
public class UniqueFileEntryTitleProvider {

	public String provide(long groupId, long folderId, Locale locale)
		throws PortalException {

		ResourceBundle resourceBundle =
			_resourceBundleLoader.loadResourceBundle(locale);

		return _uniqueFileNameProvider.provide(
			_language.get(resourceBundle, "untitled"),
			curFileName -> _exists(groupId, folderId, curFileName));
	}

	private boolean _exists(long groupId, long folderId, String curFileName) {
		try {
			_dlAppLocalService.getFileEntry(groupId, folderId, curFileName);

			return true;
		}
		catch (NoSuchFileEntryException nsfee) {
			if (_log.isDebugEnabled()) {
				_log.debug(nsfee, nsfee);
			}
		}
		catch (PortalException pe) {
			throw new SystemException(pe);
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UniqueFileEntryTitleProvider.class);

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private Language _language;

	@Reference(
		target = "(bundle.symbolic.name=com.liferay.document.library.opener.google.drive.web)"
	)
	private ResourceBundleLoader _resourceBundleLoader;

	@Reference
	private UniqueFileNameProvider _uniqueFileNameProvider;

}