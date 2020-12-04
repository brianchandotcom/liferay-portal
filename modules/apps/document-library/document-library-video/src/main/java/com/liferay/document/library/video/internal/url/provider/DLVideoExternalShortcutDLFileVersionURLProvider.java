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

package com.liferay.document.library.video.internal.url.provider;

import com.liferay.document.library.url.provider.DLFileVersionURLProvider;
import com.liferay.document.library.video.internal.constants.DLVideoConstants;
import com.liferay.document.library.video.internal.helper.DLVideoExternalShortcutMetadataHelper;
import com.liferay.document.library.video.internal.helper.DLVideoExternalShortcutMetadataHelperFactory;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;

import java.util.Arrays;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = DLFileVersionURLProvider.class)
public class DLVideoExternalShortcutDLFileVersionURLProvider
	implements DLFileVersionURLProvider {

	@Override
	public List<Type> getTypes() {
		return Arrays.asList(Type.IMAGE_PREVIEW, Type.THUMBNAIL);
	}

	@Override
	public String getURL(FileVersion fileVersion, ThemeDisplay themeDisplay) {
		DLVideoExternalShortcutMetadataHelper
			dlVideoExternalShortcutMetadataHelper =
				_dlVideoExternalShortcutMetadataHelperFactory.
					getDLVideoExternalShortcutMetadataHelper(fileVersion);

		if (dlVideoExternalShortcutMetadataHelper != null) {
			String thumbnailURL =
				dlVideoExternalShortcutMetadataHelper.getFieldValue(
					DLVideoConstants.DDM_FIELD_NAME_THUMBNAIL_URL);

			if (Validator.isNotNull(thumbnailURL)) {
				return thumbnailURL;
			}
		}

		return null;
	}

	@Reference
	private DLVideoExternalShortcutMetadataHelperFactory
		_dlVideoExternalShortcutMetadataHelperFactory;

}