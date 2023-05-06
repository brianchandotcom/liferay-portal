/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upload.test.util;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.upload.FileItem;
import com.liferay.portal.kernel.upload.UploadServletRequest;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jiefeng Wu
 */
public class UploadTestUtil {

	public static UploadServletRequest createUploadServletRequest(
		HttpServletRequest httpServletRequest,
		Map<String, FileItem[]> fileParameters,
		Map<String, List<String>> regularParameters) {

		UploadServletRequest uploadServletRequest =
			PortalUtil.getUploadServletRequest(httpServletRequest);

		if (fileParameters != null) {
			ReflectionTestUtil.setFieldValue(
				uploadServletRequest, "_fileParameters", fileParameters);
		}

		if (regularParameters != null) {
			ReflectionTestUtil.setFieldValue(
				uploadServletRequest, "_regularParameters", regularParameters);
		}

		ReflectionTestUtil.setFieldValue(
			uploadServletRequest, "_liferayServletRequest", null);

		return uploadServletRequest;
	}

}