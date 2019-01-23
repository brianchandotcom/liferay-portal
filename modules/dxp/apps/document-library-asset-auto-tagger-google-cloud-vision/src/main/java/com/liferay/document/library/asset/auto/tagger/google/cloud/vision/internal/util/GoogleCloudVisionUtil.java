/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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