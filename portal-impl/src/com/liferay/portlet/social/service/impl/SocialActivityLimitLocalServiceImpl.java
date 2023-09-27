/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.social.service.impl;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.persistence.UserPersistence;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portlet.social.service.base.SocialActivityLimitLocalServiceBaseImpl;
import com.liferay.social.kernel.model.SocialActivityLimit;

/**
 * @author Zsolt Berentey
 */
public class SocialActivityLimitLocalServiceImpl
	extends SocialActivityLimitLocalServiceBaseImpl {

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public SocialActivityLimit addActivityLimit(
			long userId, long groupId, long classNameId, long classPK,
			int activityType, String activityCounterName, int limitPeriod)
		throws PortalException {

		SocialActivityLimit activityLimit =
			socialActivityLimitPersistence.fetchByG_U_C_C_A_A(
				groupId, userId, classNameId, classPK, activityType,
				activityCounterName, false);

		if (activityLimit != null) {
			return activityLimit;
		}

		User user = _userPersistence.findByPrimaryKey(userId);

		long activityLimitId = counterLocalService.increment();

		activityLimit = socialActivityLimitPersistence.create(activityLimitId);

		activityLimit.setGroupId(groupId);
		activityLimit.setCompanyId(user.getCompanyId());
		activityLimit.setUserId(userId);
		activityLimit.setClassNameId(classNameId);
		activityLimit.setClassPK(classPK);
		activityLimit.setActivityType(activityType);
		activityLimit.setActivityCounterName(activityCounterName);
		activityLimit.setCount(limitPeriod, 0);

		return socialActivityLimitPersistence.update(activityLimit);
	}

	@Override
	public SocialActivityLimit fetchActivityLimit(
		long groupId, long userId, long classNameId, long classPK,
		int activityType, String activityCounterName) {

		return socialActivityLimitPersistence.fetchByG_U_C_C_A_A(
			groupId, userId, classNameId, classPK, activityType,
			activityCounterName);
	}

	@BeanReference(type = UserPersistence.class)
	private UserPersistence _userPersistence;

}