/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.user.segment.test.util;

import com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterion;
import com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry;
import com.liferay.commerce.user.segment.service.CommerceUserSegmentCriterionLocalServiceUtil;
import com.liferay.commerce.user.segment.service.CommerceUserSegmentEntryLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceUserSegmentTestUtil {

	public static CommerceUserSegmentCriterion addCommerceUserSegmentCriterion(
			long groupId, long commerceUserSegmentEntryId, String type,
			String typeSettings)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		return CommerceUserSegmentCriterionLocalServiceUtil.
			addCommerceUserSegmentCriterion(
				commerceUserSegmentEntryId, type, typeSettings,
				RandomTestUtil.randomDouble(), serviceContext);
	}

	public static CommerceUserSegmentEntry addCommerceUserSegmentEntry(
			long groupId, boolean system)
		throws PortalException {

		return addCommerceUserSegmentEntry(
			groupId, system, RandomTestUtil.randomBoolean(), -1, null);
	}

	public static CommerceUserSegmentEntry addCommerceUserSegmentEntry(
			long groupId, boolean active, boolean system, long userId,
			ServiceContext serviceContext)
		throws PortalException {

		if (serviceContext == null) {
			serviceContext = ServiceContextTestUtil.getServiceContext(groupId);
		}

		if (userId > 0) {
			serviceContext.setUserId(userId);
		}

		return CommerceUserSegmentEntryLocalServiceUtil.
			addCommerceUserSegmentEntry(
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomString(), active, system,
				RandomTestUtil.randomDouble(), serviceContext);
	}

}