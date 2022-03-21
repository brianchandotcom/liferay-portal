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

package com.liferay.fragment.util;

import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentCollectionLocalServiceUtil;
import com.liferay.fragment.service.FragmentEntryLinkLocalServiceUtil;
import com.liferay.fragment.service.FragmentEntryLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.segments.service.SegmentsExperienceLocalServiceUtil;

import java.util.Date;

/**
 * @author Pavel Savinov
 */
public class FragmentTestUtil {

	public static FragmentCollection addFragmentCollection(long groupId)
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		return FragmentCollectionLocalServiceUtil.addFragmentCollection(
			TestPropsValues.getUserId(), groupId, RandomTestUtil.randomString(),
			StringPool.BLANK, serviceContext);
	}

	public static FragmentCollection addFragmentCollection(
			long groupId, String name)
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		return FragmentCollectionLocalServiceUtil.addFragmentCollection(
			TestPropsValues.getUserId(), groupId, name, StringPool.BLANK,
			serviceContext);
	}

	public static FragmentCollection addFragmentCollection(
			long groupId, String name, Date createDate)
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		serviceContext.setCreateDate(createDate);
		serviceContext.setModifiedDate(createDate);

		return FragmentCollectionLocalServiceUtil.addFragmentCollection(
			TestPropsValues.getUserId(), groupId, name, StringPool.BLANK,
			serviceContext);
	}

	public static FragmentCollection addFragmentCollection(
			long groupId, String name, String fragmentCollectionKey)
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		return FragmentCollectionLocalServiceUtil.addFragmentCollection(
			TestPropsValues.getUserId(), groupId, fragmentCollectionKey, name,
			StringPool.BLANK, serviceContext);
	}

	public static FragmentEntryLink addFragmentEntryLink(
			FragmentEntry fragmentEntry, long plid)
		throws PortalException {

		long defaultSegmentsExperienceId =
			SegmentsExperienceLocalServiceUtil.fetchDefaultSegmentsExperienceId(
				plid);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				fragmentEntry.getGroupId());

		return FragmentEntryLinkLocalServiceUtil.addFragmentEntryLink(
			TestPropsValues.getUserId(), serviceContext.getScopeGroupId(), 0,
			fragmentEntry.getFragmentEntryId(), defaultSegmentsExperienceId,
			plid, fragmentEntry.getCss(), fragmentEntry.getHtml(),
			fragmentEntry.getJs(), fragmentEntry.getConfiguration(),
			StringPool.BLANK, StringPool.BLANK, 1, StringPool.BLANK,
			serviceContext);
	}

	public static FragmentEntryLink addFragmentEntryLink(
			long groupId, long fragmentEntryId, long plid)
		throws PortalException {

		FragmentEntry fragmentEntry =
			FragmentEntryLocalServiceUtil.getFragmentEntry(fragmentEntryId);

		long defaultSegmentsExperienceId =
			SegmentsExperienceLocalServiceUtil.fetchDefaultSegmentsExperienceId(
				plid);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		return FragmentEntryLinkLocalServiceUtil.addFragmentEntryLink(
			TestPropsValues.getUserId(), groupId, 0,
			fragmentEntry.getFragmentEntryId(), defaultSegmentsExperienceId,
			plid, fragmentEntry.getCss(), fragmentEntry.getHtml(),
			fragmentEntry.getJs(), fragmentEntry.getConfiguration(),
			StringPool.BLANK, StringPool.BLANK, 1, StringPool.BLANK,
			serviceContext);
	}

	public static FragmentEntryLink fetchFragmentEntryLink(
		String uuid, long groupId) {

		return FragmentEntryLinkLocalServiceUtil.
			fetchFragmentEntryLinkByUuidAndGroupId(uuid, groupId);
	}

}