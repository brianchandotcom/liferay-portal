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

import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.service.FragmentCollectionLocalServiceUtil;
import com.liferay.fragment.service.FragmentEntryLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Date;

/**
 * @author Kyle Miho
 */
public class FragmentEntryTestUtil {

	public static FragmentEntry addFragmentEntry(long fragmentCollectionId)
		throws PortalException {

		return addFragmentEntry(
			fragmentCollectionId, RandomTestUtil.randomString());
	}

	public static FragmentEntry addFragmentEntry(
			long fragmentCollectionId, String name)
		throws PortalException {

		FragmentCollection fragmentCollection =
			FragmentCollectionLocalServiceUtil.getFragmentCollection(
				fragmentCollectionId);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				fragmentCollection.getGroupId());

		return FragmentEntryLocalServiceUtil.addFragmentEntry(
			TestPropsValues.getUserId(), serviceContext.getScopeGroupId(),
			fragmentCollectionId, StringPool.BLANK, name, StringPool.BLANK,
			"<div>TEST</div>", StringPool.BLANK, false, StringPool.BLANK, null,
			0, FragmentConstants.TYPE_COMPONENT,
			WorkflowConstants.STATUS_APPROVED, serviceContext);
	}

	public static FragmentEntry addFragmentEntry(
			long fragmentCollectionId, String name, Date createDate)
		throws PortalException {

		FragmentCollection fragmentCollection =
			FragmentCollectionLocalServiceUtil.getFragmentCollection(
				fragmentCollectionId);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				fragmentCollection.getGroupId());

		serviceContext.setCreateDate(createDate);
		serviceContext.setModifiedDate(createDate);

		return FragmentEntryLocalServiceUtil.addFragmentEntry(
			TestPropsValues.getUserId(), fragmentCollection.getGroupId(),
			fragmentCollectionId, StringPool.BLANK, name, StringPool.BLANK,
			"<div></div>", StringPool.BLANK, false, StringPool.BLANK, null, 0,
			FragmentConstants.TYPE_COMPONENT, WorkflowConstants.STATUS_APPROVED,
			serviceContext);
	}

	public static FragmentEntry addFragmentEntryByStatus(
			long fragmentCollectionId, int status)
		throws PortalException {

		return addFragmentEntryByStatus(
			fragmentCollectionId, RandomTestUtil.randomString(), status);
	}

	public static FragmentEntry addFragmentEntryByStatus(
			long fragmentCollectionId, String name, int status)
		throws PortalException {

		FragmentCollection fragmentCollection =
			FragmentCollectionLocalServiceUtil.getFragmentCollection(
				fragmentCollectionId);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				fragmentCollection.getGroupId());

		return FragmentEntryLocalServiceUtil.addFragmentEntry(
			TestPropsValues.getUserId(), serviceContext.getScopeGroupId(),
			fragmentCollectionId, StringPool.BLANK, name, StringPool.BLANK,
			"<div></div>", StringPool.BLANK, false, StringPool.BLANK, null, 0,
			FragmentConstants.TYPE_COMPONENT, status, serviceContext);
	}

	public static FragmentEntry addFragmentEntryByStatus(
			long fragmentCollectionId, String name, int status, Date createDate)
		throws PortalException {

		FragmentCollection fragmentCollection =
			FragmentCollectionLocalServiceUtil.getFragmentCollection(
				fragmentCollectionId);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				fragmentCollection.getGroupId());

		serviceContext.setCreateDate(createDate);
		serviceContext.setModifiedDate(createDate);

		return FragmentEntryLocalServiceUtil.addFragmentEntry(
			TestPropsValues.getUserId(), serviceContext.getScopeGroupId(),
			fragmentCollectionId, StringPool.BLANK, name, StringPool.BLANK,
			"<div></div>", StringPool.BLANK, false, StringPool.BLANK, null, 0,
			FragmentConstants.TYPE_COMPONENT, status, serviceContext);
	}

	public static FragmentEntry addFragmentEntryByType(
			long fragmentCollectionId, int type)
		throws PortalException {

		return addFragmentEntryByType(
			fragmentCollectionId, RandomTestUtil.randomString(), type);
	}

	public static FragmentEntry addFragmentEntryByType(
			long fragmentCollectionId, String name, int type)
		throws PortalException {

		FragmentCollection fragmentCollection =
			FragmentCollectionLocalServiceUtil.getFragmentCollection(
				fragmentCollectionId);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				fragmentCollection.getGroupId());

		return FragmentEntryLocalServiceUtil.addFragmentEntry(
			TestPropsValues.getUserId(), serviceContext.getScopeGroupId(),
			fragmentCollection.getFragmentCollectionId(),
			StringUtil.randomString(), name, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), false,
			"{fieldSets: []}", null, 0, type, WorkflowConstants.STATUS_APPROVED,
			serviceContext);
	}

	public static FragmentEntry addFragmentEntryByType(
			long fragmentCollectionId, String name, int type, Date createDate)
		throws PortalException {

		FragmentCollection fragmentCollection =
			FragmentCollectionLocalServiceUtil.getFragmentCollection(
				fragmentCollectionId);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				fragmentCollection.getGroupId());

		return FragmentEntryLocalServiceUtil.addFragmentEntry(
			TestPropsValues.getUserId(), serviceContext.getScopeGroupId(),
			fragmentCollection.getFragmentCollectionId(),
			StringUtil.randomString(), name, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), false,
			"{fieldSets: []}", null, 0, type, WorkflowConstants.STATUS_APPROVED,
			serviceContext);
	}

}