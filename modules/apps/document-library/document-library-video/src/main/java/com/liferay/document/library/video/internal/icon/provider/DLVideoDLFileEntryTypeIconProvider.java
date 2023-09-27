/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.video.internal.icon.provider;

import com.liferay.document.library.icon.provider.DLFileEntryTypeIconProvider;
import com.liferay.document.library.video.internal.constants.DLVideoConstants;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = "file.entry.type.key=" + DLVideoConstants.DL_FILE_ENTRY_TYPE_KEY,
	service = DLFileEntryTypeIconProvider.class
)
public class DLVideoDLFileEntryTypeIconProvider
	implements DLFileEntryTypeIconProvider {

	@Override
	public String getIcon() {
		return "video";
	}

}