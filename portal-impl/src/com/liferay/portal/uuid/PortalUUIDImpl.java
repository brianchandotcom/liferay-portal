/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.uuid;

import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.uuid.PortalUUID;

import java.util.UUID;

/**
 * @author Brian Wing Shun Chan
 */
@DoPrivileged
public class PortalUUIDImpl implements PortalUUID {

	@Override
	public String fromJsSafeUuid(String jsSafeUuid) {
		return StringUtil.replace(
			jsSafeUuid, StringPool.DOUBLE_UNDERLINE, StringPool.DASH);
	}

	@Override
	public String generate() {
		return UUID.randomUUID().toString();
	}

	@Override
	public String generate(byte[] bytes) {
		return UUID.nameUUIDFromBytes(bytes).toString();
	}

	@Override
	public String toJsSafeUuid(String uuid) {
		return StringUtil.replace(
			uuid, StringPool.DASH, StringPool.DOUBLE_UNDERLINE);
	}

}