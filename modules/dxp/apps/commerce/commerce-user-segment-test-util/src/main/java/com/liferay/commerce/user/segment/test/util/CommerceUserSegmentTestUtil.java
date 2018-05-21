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

package com.liferay.commerce.user.segment.test.util;

import com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterion;
import com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterionConstants;
import com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry;
import com.liferay.commerce.user.segment.service.CommerceUserSegmentCriterionLocalServiceUtil;
import com.liferay.commerce.user.segment.service.CommerceUserSegmentEntryLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Andrea Di Giorgi
 * @author Luca Pellizzon
 */
public class CommerceUserSegmentTestUtil {

	public static CommerceUserSegmentCriterion addCommerceUserSegmentCriterion(
			CommerceUserSegmentEntry commerceUserSegmentEntry, String type,
			String typeSettings)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				commerceUserSegmentEntry.getGroupId());

		return CommerceUserSegmentCriterionLocalServiceUtil.
			addCommerceUserSegmentCriterion(
				commerceUserSegmentEntry.getCommerceUserSegmentEntryId(), type,
				typeSettings, RandomTestUtil.randomDouble(), serviceContext);
	}

	public static CommerceUserSegmentEntry addCommerceUserSegmentEntry(
			long groupId)
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		User user = UserTestUtil.getAdminUser(serviceContext.getCompanyId());

		serviceContext.setUserId(user.getUserId());

		return CommerceUserSegmentEntryLocalServiceUtil.
			addCommerceUserSegmentEntry(
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomString(), true, false,
				RandomTestUtil.randomDouble(), serviceContext);
	}

	public static CommerceUserSegmentEntry
			addOrganizationCommerceUserSegmentEntry(
				long groupId, long... organizationIds)
		throws Exception {

		CommerceUserSegmentEntry commerceUserSegmentEntry =
			addCommerceUserSegmentEntry(groupId);

		addCommerceUserSegmentCriterion(
			commerceUserSegmentEntry,
			CommerceUserSegmentCriterionConstants.TYPE_ORGANIZATION,
			StringUtil.merge(organizationIds));

		return commerceUserSegmentEntry;
	}

	public static CommerceUserSegmentEntry addRoleCommerceUserSegmentEntry(
			long groupId, long... roleIds)
		throws Exception {

		CommerceUserSegmentEntry commerceUserSegmentEntry =
			addCommerceUserSegmentEntry(groupId);

		addCommerceUserSegmentCriterion(
			commerceUserSegmentEntry,
			CommerceUserSegmentCriterionConstants.TYPE_ROLE,
			StringUtil.merge(roleIds));

		return commerceUserSegmentEntry;
	}

	public static CommerceUserSegmentEntry addSystemCommerceUserSegmentEntry(
			long groupId)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		return CommerceUserSegmentEntryLocalServiceUtil.
			addCommerceUserSegmentEntry(
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomString(), true, true,
				RandomTestUtil.randomDouble(), serviceContext);
	}

	public static CommerceUserSegmentEntry addUserCommerceUserSegmentEntry(
			long groupId, long... userIds)
		throws Exception {

		CommerceUserSegmentEntry commerceUserSegmentEntry =
			addCommerceUserSegmentEntry(groupId);

		addCommerceUserSegmentCriterion(
			commerceUserSegmentEntry,
			CommerceUserSegmentCriterionConstants.TYPE_USER,
			StringUtil.merge(userIds));

		return commerceUserSegmentEntry;
	}

	public static CommerceUserSegmentEntry addUserGroupCommerceUserSegmentEntry(
			long groupId, long... userGroupIds)
		throws Exception {

		CommerceUserSegmentEntry commerceUserSegmentEntry =
			addCommerceUserSegmentEntry(groupId);

		addCommerceUserSegmentCriterion(
			commerceUserSegmentEntry,
			CommerceUserSegmentCriterionConstants.TYPE_USER_GROUP,
			StringUtil.merge(userGroupIds));

		return commerceUserSegmentEntry;
	}

}