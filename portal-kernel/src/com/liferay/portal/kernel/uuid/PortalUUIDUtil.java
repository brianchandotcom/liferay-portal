/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.uuid;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.security.SecureRandomUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.UUID;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class PortalUUIDUtil {

	public static String fromJsSafeUuid(String jsSafeUuid) {
		return StringUtil.replace(
			jsSafeUuid, StringPool.DOUBLE_UNDERLINE, StringPool.DASH);
	}

	public static String generate() {
		UUID uuid = new UUID(
			SecureRandomUtil.nextLong(), SecureRandomUtil.nextLong());

		return uuid.toString();
	}

	public static String generate(byte[] bytes) {
		UUID uuid = UUID.nameUUIDFromBytes(bytes);

		return uuid.toString();
	}

	public static String toJsSafeUuid(String uuid) {
		return StringUtil.replace(
			uuid, CharPool.DASH, StringPool.DOUBLE_UNDERLINE);
	}

}