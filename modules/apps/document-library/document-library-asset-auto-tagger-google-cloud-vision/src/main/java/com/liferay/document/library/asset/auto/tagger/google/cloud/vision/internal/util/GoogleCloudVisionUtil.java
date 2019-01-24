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
public class GoogleCloudVisionUtil {

	public static String getAnnotateImagePayload(FileEntry fileEntry)
		throws Exception {

		FileVersion fileVersion = fileEntry.getFileVersion();

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
						Base64.encode(
							FileUtil.getBytes(
								fileVersion.getContentStream(false))))
				)));

		return jsonObject.toString();
	}

}