/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace.store.web.internal.util;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.license.util.LicenseManagerUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.LicenseUtil;

import java.io.File;

import java.util.Arrays;

/**
 * @author Joan Kim
 */
public class MarketplaceLicenseUtil {

	public static String getOrder(String productEntryName) throws Exception {
		String response = LicenseUtil.sendRequest(
			JSONUtil.put(
				"cmd", "GET_ORDER"
			).put(
				"hostName", LicenseManagerUtil.getHostName()
			).put(
				"ipAddresses",
				StringUtil.merge(LicenseManagerUtil.getIpAddresses())
			).put(
				"macAddresses",
				StringUtil.merge(LicenseManagerUtil.getMacAddresses())
			).put(
				"productEntryName", productEntryName
			).put(
				"serverId", Arrays.toString(getServerIdBytes())
			).toString());

		JSONObject responseJSONObject = JSONFactoryUtil.createJSONObject(
			response);

		return responseJSONObject.getString("orderUuid");
	}

	public static byte[] getServerIdBytes() throws Exception {
		File serverIdFile = new File(_LICENSE_SERVER_ID_FILE_NAME);

		if (!serverIdFile.exists()) {
			return new byte[0];
		}

		return FileUtil.getBytes(serverIdFile);
	}

	public static void registerOrder(String orderUuid, String productEntryName)
		throws Exception {

		LicenseUtil.registerOrder(orderUuid, productEntryName, 0);
	}

	private static final String _LICENSE_SERVER_ID_FILE_NAME =
		PropsUtil.get(PropsKeys.LIFERAY_HOME) + "/data/license/server/serverId";

}