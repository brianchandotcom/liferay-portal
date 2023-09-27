/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.uuid;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class PortalUUIDUtil {

	public static String fromJsSafeUuid(String jsSafeUuid) {
		return _portalUUID.fromJsSafeUuid(jsSafeUuid);
	}

	public static String generate() {
		return _portalUUID.generate();
	}

	public static String generate(byte[] bytes) {
		return _portalUUID.generate(bytes);
	}

	public static PortalUUID getPortalUUID() {
		return _portalUUID;
	}

	public static String toJsSafeUuid(String uuid) {
		return _portalUUID.toJsSafeUuid(uuid);
	}

	public void setPortalUUID(PortalUUID portalUUID) {
		_portalUUID = portalUUID;
	}

	private static PortalUUID _portalUUID;

}