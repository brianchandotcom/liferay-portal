/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.asset.auto.tagger.google.cloud.vision.internal.util;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.FileUtil;

/**
 * @author Alejandro Tardín
 */
public class GCloudVisionUtil {

	public static String getAnnotateImagePayload(FileEntry fileEntry)
		throws Exception {

		JSONObject jsonObject = JSONUtil.put(
			"requests",
			JSONUtil.put(
				JSONUtil.put(
					"features",
					JSONUtil.put(JSONUtil.put("type", "LABEL_DETECTION"))
				).put(
					"image",
					JSONUtil.put(
						"content",
						() -> {
							FileVersion fileVersion =
								fileEntry.getFileVersion();

							return Base64.encode(
								FileUtil.getBytes(
									fileVersion.getContentStream(false)));
						})
				)));

		return jsonObject.toString();
	}

}