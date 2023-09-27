/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.expando.util;

import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.service.DLFileVersionLocalService;
import com.liferay.expando.kernel.util.ExpandoValueDeleteHandler;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Máté Thurzó
 */
@Component(
	property = "model.class.name=com.liferay.document.library.kernel.model.DLFileEntry",
	service = ExpandoValueDeleteHandler.class
)
public class DLFileEntryExpandoValueDeleteHandler
	implements ExpandoValueDeleteHandler {

	@Override
	public void deletedExpandoValue(long classPK) {
		DLFileVersion fileVersion =
			_dlFileVersionLocalService.fetchDLFileVersion(classPK);

		if (fileVersion == null) {
			return;
		}

		fileVersion.setModifiedDate(new Date());

		_dlFileVersionLocalService.updateDLFileVersion(fileVersion);
	}

	@Reference
	private DLFileVersionLocalService _dlFileVersionLocalService;

}