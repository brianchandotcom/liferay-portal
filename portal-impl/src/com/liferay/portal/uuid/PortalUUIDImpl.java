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

package com.liferay.portal.uuid;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.security.SecureRandomUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.uuid.PortalUUID;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	public List<String> getUuids(String s) {
		List<String> uuids = new ArrayList<>();

		Matcher matcher = _uuidPattern.matcher(s);

		while (matcher.find()) {
			uuids.add(matcher.group(0));
		}

		return uuids;
	}

	@Override
	public String toJsSafeUuid(String uuid) {
		return StringUtil.replace(
			uuid, CharPool.DASH, StringPool.DOUBLE_UNDERLINE);
	}

	private static final Pattern _uuidPattern = Pattern.compile(
		"[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-" +
			"[a-fA-F0-9]{12}");

}