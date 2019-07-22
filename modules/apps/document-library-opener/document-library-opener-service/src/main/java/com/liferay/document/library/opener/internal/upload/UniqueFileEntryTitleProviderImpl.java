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

package com.liferay.document.library.opener.internal.upload;

import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.opener.upload.UniqueFileEntryTitleProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.upload.UniqueFileNameProvider;

import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = UniqueFileEntryTitleProvider.class)
public class UniqueFileEntryTitleProviderImpl
	implements UniqueFileEntryTitleProvider {

	@Override
	public String provide(long groupId, long folderId, Locale locale)
		throws PortalException {

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			locale, UniqueFileEntryTitleProviderImpl.class);

		return _provide(
			groupId, folderId, _language.get(resourceBundle, "untitled"));
	}

	@Override
	public String provide(long groupId, long folderId, String fileName)
		throws PortalException {

		return _provide(groupId, folderId, fileName);
	}

	private boolean _exists(long groupId, long folderId, String fileName) {
		try {
			_dlAppLocalService.getFileEntry(groupId, folderId, fileName);

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

	private String _provide(long groupId, long folderId, String fileName)
		throws PortalException {

		return _uniqueFileNameProvider.provide(
			fileName,
			generatedFileName -> _exists(groupId, folderId, generatedFileName));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UniqueFileEntryTitleProviderImpl.class);

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private Language _language;

	@Reference
	private UniqueFileNameProvider _uniqueFileNameProvider;

}