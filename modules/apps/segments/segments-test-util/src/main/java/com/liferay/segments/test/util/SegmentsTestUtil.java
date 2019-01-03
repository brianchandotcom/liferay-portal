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

package com.liferay.segments.test.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.segments.constants.SegmentsConstants;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.service.SegmentsEntryLocalServiceUtil;
import com.liferay.segments.service.SegmentsEntryRelLocalServiceUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Eduardo García
 */
public class SegmentsTestUtil {

	public static SegmentsEntry addSegmentsEntry(long groupId)
		throws PortalException {

		return addSegmentsEntry(groupId, RandomTestUtil.randomString());
	}

	public static SegmentsEntry addSegmentsEntry(long groupId, String key)
		throws PortalException {

		return addSegmentsEntry(
			groupId, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), key,
			RandomTestUtil.randomString());
	}

	public static SegmentsEntry addSegmentsEntry(
			long groupId, String className, long classPK)
		throws PortalException {

		SegmentsEntry segmentsEntry = addSegmentsEntry(
			groupId, StringPool.BLANK, className);

		SegmentsEntryRelLocalServiceUtil.addSegmentsEntryRel(
			segmentsEntry.getSegmentsEntryId(),
			PortalUtil.getClassNameId(className), classPK,
			ServiceContextTestUtil.getServiceContext(groupId));

		return segmentsEntry;
	}

	public static SegmentsEntry addSegmentsEntry(
			long groupId, String criteria, String type)
		throws PortalException {

		return addSegmentsEntry(
			groupId, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), criteria,
			RandomTestUtil.randomString(), type);
	}

	public static SegmentsEntry addSegmentsEntry(
			long groupId, final String name, final String description,
			String criteria, String key, String type)
		throws PortalException {

		return addSegmentsEntry(
			name, description, criteria, key, SegmentsConstants.SOURCE_DEFAULT,
			type, ServiceContextTestUtil.getServiceContext(groupId));
	}

	public static SegmentsEntry addSegmentsEntry(ServiceContext serviceContext)
		throws PortalException {

		return addSegmentsEntry(
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			SegmentsConstants.SOURCE_DEFAULT, RandomTestUtil.randomString(),
			serviceContext);
	}

	public static SegmentsEntry addSegmentsEntry(
			final String name, final String description, String criteria,
			String key, String source, String type,
			ServiceContext serviceContext)
		throws PortalException {

		Map<Locale, String> nameMap = new HashMap<>();

		nameMap.put(LocaleUtil.getDefault(), name);

		Map<Locale, String> descriptionMap = new HashMap<>();

		descriptionMap.put(LocaleUtil.getDefault(), description);

		return SegmentsEntryLocalServiceUtil.addSegmentsEntry(
			nameMap, descriptionMap, true, criteria, key, source, type,
			serviceContext);
	}

}