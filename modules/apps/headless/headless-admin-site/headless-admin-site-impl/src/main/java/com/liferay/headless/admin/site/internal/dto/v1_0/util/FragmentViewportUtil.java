/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.dto.v1_0.util;

import com.liferay.headless.admin.site.dto.v1_0.FragmentViewport;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.ArrayUtil;

/**
 * @author Mikel Lorza
 */
public class FragmentViewportUtil {

	public static JSONObject toFragmentViewportsJSONObject(
		FragmentViewport[] fragmentViewports) {

		if (ArrayUtil.isEmpty(fragmentViewports)) {
			return null;
		}

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		for (FragmentViewport fragmentViewport : fragmentViewports) {
			String customCSS = fragmentViewport.getCustomCSS();

			if ((customCSS == null) || customCSS.isEmpty()) {
				continue;
			}

			jsonObject.put(
				fragmentViewport.getId(), JSONUtil.put("customCSS", customCSS));
		}

		return jsonObject;
	}

}