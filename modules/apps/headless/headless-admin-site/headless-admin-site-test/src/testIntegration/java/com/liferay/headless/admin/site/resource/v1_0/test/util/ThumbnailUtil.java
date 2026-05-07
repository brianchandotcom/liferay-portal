/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.resource.v1_0.test.util;

import com.liferay.headless.admin.site.client.dto.v1_0.ThumbnailURLReference;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

/**
 * @author Lourdes Fernández Besada
 */
public class ThumbnailUtil {

	public static ThumbnailURLReference getRandomThumbnailURLReference() {
		ThumbnailURLReference thumbnailURLReference =
			new ThumbnailURLReference();

		thumbnailURLReference.setExternalReferenceCode(
			RandomTestUtil::randomString);
		thumbnailURLReference.setUrl(
			() ->
				"http://invalid.example.test/" + RandomTestUtil.randomString());

		return thumbnailURLReference;
	}

}