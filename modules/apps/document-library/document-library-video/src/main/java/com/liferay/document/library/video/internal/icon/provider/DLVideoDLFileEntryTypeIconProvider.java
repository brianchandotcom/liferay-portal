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