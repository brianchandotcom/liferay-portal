/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.test.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.segments.constants.SegmentsEntryConstants;
import com.liferay.segments.constants.SegmentsExperimentConstants;
import com.liferay.segments.criteria.Criteria;
import com.liferay.segments.criteria.CriteriaSerializer;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.model.SegmentsExperiment;
import com.liferay.segments.service.SegmentsEntryLocalServiceUtil;
import com.liferay.segments.service.SegmentsExperienceLocalServiceUtil;
import com.liferay.segments.service.SegmentsExperimentLocalServiceUtil;

/**
 * @author Eduardo García
 */
public class SegmentsTestUtil {

	public static SegmentsEntry addSegmentsEntry(long groupId)
		throws PortalException {

		return addSegmentsEntry(groupId, RandomTestUtil.randomString());
	}

	public static SegmentsEntry addSegmentsEntry(
			long groupId, String segmentsEntryKey)
		throws PortalException {

		return addSegmentsEntry(
			groupId, segmentsEntryKey, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), _EMPTY_CRITERIA_STRING,
			RandomTestUtil.randomString());
	}

	public static SegmentsEntry addSegmentsEntry(
			long groupId, String className, long classPK)
		throws PortalException {

		SegmentsEntry segmentsEntry = addSegmentsEntry(
			groupId, _EMPTY_CRITERIA_STRING, className);

		SegmentsEntryLocalServiceUtil.addSegmentsEntryClassPKs(
			segmentsEntry.getSegmentsEntryId(), new long[] {classPK},
			ServiceContextTestUtil.getServiceContext(groupId));

		return segmentsEntry;
	}

	public static SegmentsEntry addSegmentsEntry(
			long groupId, String criteria, String type)
		throws PortalException {

		return addSegmentsEntry(
			groupId, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			criteria, type);
	}

	public static SegmentsEntry addSegmentsEntry(
			long groupId, String segmentsEntryKey, String name,
			String description, String criteria, String type)
		throws PortalException {

		return addSegmentsEntry(
			segmentsEntryKey, name, description, criteria,
			SegmentsEntryConstants.SOURCE_DEFAULT, type,
			ServiceContextTestUtil.getServiceContext(groupId));
	}

	public static SegmentsEntry addSegmentsEntry(ServiceContext serviceContext)
		throws PortalException {

		return addSegmentsEntry(
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), _EMPTY_CRITERIA_STRING,
			SegmentsEntryConstants.SOURCE_DEFAULT,
			RandomTestUtil.randomString(), serviceContext);
	}

	public static SegmentsEntry addSegmentsEntry(
			String segmentsEntryKey, String name, String description,
			String criteria, String source, String type,
			ServiceContext serviceContext)
		throws PortalException {

		return SegmentsEntryLocalServiceUtil.addSegmentsEntry(
			segmentsEntryKey,
			HashMapBuilder.put(
				LocaleUtil.getDefault(), name
			).build(),
			HashMapBuilder.put(
				LocaleUtil.getDefault(), description
			).build(),
			true, criteria, source, type, serviceContext);
	}

	public static SegmentsExperience addSegmentsExperience(
			long groupId, long plid)
		throws PortalException {

		SegmentsEntry segmentsEntry = addSegmentsEntry(groupId);

		return addSegmentsExperience(
			groupId, segmentsEntry.getSegmentsEntryId(), plid);
	}

	public static SegmentsExperience addSegmentsExperience(
			long groupId, long segmentsEntryId, long plid)
		throws PortalException {

		return addSegmentsExperience(
			segmentsEntryId, plid,
			ServiceContextTestUtil.getServiceContext(groupId));
	}

	public static SegmentsExperience addSegmentsExperience(
			long segmentsEntryId, long plid, ServiceContext serviceContext)
		throws PortalException {

		return SegmentsExperienceLocalServiceUtil.addSegmentsExperience(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(),
			segmentsEntryId, plid, RandomTestUtil.randomLocaleStringMap(), true,
			new UnicodeProperties(true), serviceContext);
	}

	public static SegmentsExperience addSegmentsExperience(
			long plid, ServiceContext serviceContext)
		throws PortalException {

		SegmentsEntry segmentsEntry = addSegmentsEntry(
			serviceContext.getScopeGroupId());

		return addSegmentsExperience(
			segmentsEntry.getSegmentsEntryId(), plid, serviceContext);
	}

	public static SegmentsExperiment addSegmentsExperiment(
			long groupId, long segmentsExperienceId, long plid)
		throws PortalException {

		return SegmentsExperimentLocalServiceUtil.addSegmentsExperiment(
			segmentsExperienceId, plid, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(),
			SegmentsExperimentConstants.Goal.BOUNCE_RATE.getLabel(),
			StringPool.BLANK,
			ServiceContextTestUtil.getServiceContext(groupId));
	}

	private static final String _EMPTY_CRITERIA_STRING =
		CriteriaSerializer.serialize(new Criteria());

}