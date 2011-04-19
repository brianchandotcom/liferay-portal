/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.social.service.impl;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portlet.social.model.SocialActivityLimit;
import com.liferay.portlet.social.service.base.SocialActivityLimitLocalServiceBaseImpl;

/**
 * @author Zsolt Berentey
 */
public class SocialActivityLimitLocalServiceImpl
	extends SocialActivityLimitLocalServiceBaseImpl {

	@Transactional(
			rollbackFor = SystemException.class,
			propagation = Propagation.REQUIRES_NEW)
	public SocialActivityLimit addActivityLimit(
			long companyId, long groupId, long userId, long classNameId,
			long classPK, int activityKey, String statName, int limitPeriod)
		throws SystemException {

		long activityLimitId = counterLocalService.increment();

		SocialActivityLimit activityLimit =
			socialActivityLimitPersistence.create(activityLimitId);

		activityLimit.setCompanyId(companyId);
		activityLimit.setGroupId(groupId);
		activityLimit.setUserId(userId);
		activityLimit.setClassNameId(classNameId);
		activityLimit.setClassPK(classPK);
		activityLimit.setActivityKey(activityKey);
		activityLimit.setStatName(statName);
		activityLimit.setCount(limitPeriod, 0);

		socialActivityLimitPersistence.update(activityLimit, false);

		return activityLimit;
	}

}