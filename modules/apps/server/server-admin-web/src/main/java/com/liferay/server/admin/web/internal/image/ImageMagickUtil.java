/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.server.admin.web.internal.image;

import com.liferay.image.ImageMagick;
import com.liferay.osgi.util.service.Snapshot;

import java.util.Properties;

/**
 * The ImageMagick utility class.
 *
 * @author Alexander Chow
 */
public class ImageMagickUtil {

	public static String getGlobalSearchPath() throws Exception {
		ImageMagick imageMagick = _imageMagickSnapshot.get();

		return imageMagick.getGlobalSearchPath();
	}

	public static Properties getResourceLimitsProperties() throws Exception {
		ImageMagick imageMagick = _imageMagickSnapshot.get();

		return imageMagick.getResourceLimitsProperties();
	}

	public static boolean isEnabled() {
		ImageMagick imageMagick = _imageMagickSnapshot.get();

		return imageMagick.isEnabled();
	}

	private static final Snapshot<ImageMagick> _imageMagickSnapshot =
		new Snapshot<>(ImageMagickUtil.class, ImageMagick.class);

}