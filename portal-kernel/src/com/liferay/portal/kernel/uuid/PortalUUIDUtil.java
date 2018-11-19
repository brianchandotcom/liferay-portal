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

package com.liferay.portal.kernel.uuid;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class PortalUUIDUtil {

	public static String fromJsSafeUuid(String jsSafeUuid) {
		return getPortalUUID().fromJsSafeUuid(jsSafeUuid);
	}

	public static String generate() {
		return getPortalUUID().generate();
	}

	public static String generate(byte[] bytes) {
		return getPortalUUID().generate(bytes);
	}

	public static PortalUUID getPortalUUID() {
		return _portalUUID;
	}

	public static String toJsSafeUuid(String uuid) {
		return getPortalUUID().toJsSafeUuid(uuid);
	}

	public void setPortalUUID(PortalUUID portalUUID) {
		_portalUUID = portalUUID;
	}

	private static PortalUUID _portalUUID;

}