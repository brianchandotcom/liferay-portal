/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.uuid;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.security.SecureRandomUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.uuid.PortalUUID;

import java.util.UUID;

/**
 * @author Brian Wing Shun Chan
 */
public class PortalUUIDImpl implements PortalUUID {

	@Override
	public String fromJsSafeUuid(String jsSafeUuid) {
		return StringUtil.replace(
			jsSafeUuid, StringPool.DOUBLE_UNDERLINE, StringPool.DASH);
	}

	@Override
	public String generate() {
		UUID uuid = new UUID(
			SecureRandomUtil.nextLong(), SecureRandomUtil.nextLong());

		return uuid.toString();
	}

	@Override
	public String generate(byte[] bytes) {
		UUID uuid = UUID.nameUUIDFromBytes(bytes);

		return uuid.toString();
	}

	@Override
	public String toJsSafeUuid(String uuid) {
		return StringUtil.replace(
			uuid, CharPool.DASH, StringPool.DOUBLE_UNDERLINE);
	}

}