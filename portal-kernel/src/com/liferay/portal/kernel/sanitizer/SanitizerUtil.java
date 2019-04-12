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

package com.liferay.portal.kernel.sanitizer;

import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerList;

import java.util.Map;

/**
 * @author Zsolt Balogh
 * @author Brian Wing Shun Chan
 */
public class SanitizerUtil {

	public static String sanitize(
			long companyId, long groupId, long userId, String className,
			long classPK, String contentType, String content)
		throws SanitizerException {

		return sanitize(
			companyId, groupId, userId, className, classPK, contentType,
			Sanitizer.MODE_ALL, content, null);
	}

	public static String sanitize(
			long companyId, long groupId, long userId, String className,
			long classPK, String contentType, String mode, String s,
			Map<String, Object> options)
		throws SanitizerException {

		return sanitize(
			companyId, groupId, userId, className, classPK, contentType,
			new String[] {mode}, s, options);
	}

	public static String sanitize(
			long companyId, long groupId, long userId, String className,
			long classPK, String contentType, String[] modes, String content,
			Map<String, Object> options)
		throws SanitizerException {

		for (Sanitizer sanitizer : _sanitizers) {
			content = sanitizer.sanitize(
				companyId, groupId, userId, className, classPK, contentType,
				modes, content, options);
		}

		return content;
	}

	private static final ServiceTrackerList<Sanitizer> _sanitizers =
		ServiceTrackerCollections.openList(Sanitizer.class);

}