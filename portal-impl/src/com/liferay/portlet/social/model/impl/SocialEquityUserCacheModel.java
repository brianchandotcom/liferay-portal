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

package com.liferay.portlet.social.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.CacheModel;

import com.liferay.portlet.social.model.SocialEquityUser;

import java.io.Serializable;

/**
 * The cache model class for representing SocialEquityUser in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see SocialEquityUser
 * @generated
 */
public class SocialEquityUserCacheModel implements CacheModel<SocialEquityUser>,
	Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(19);

		sb.append("{equityUserId=");
		sb.append(equityUserId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", contributionK=");
		sb.append(contributionK);
		sb.append(", contributionB=");
		sb.append(contributionB);
		sb.append(", participationK=");
		sb.append(participationK);
		sb.append(", participationB=");
		sb.append(participationB);
		sb.append(", rank=");
		sb.append(rank);
		sb.append("}");

		return sb.toString();
	}

	public SocialEquityUser toEntityModel() {
		SocialEquityUserImpl socialEquityUserImpl = new SocialEquityUserImpl();

		socialEquityUserImpl.setEquityUserId(equityUserId);
		socialEquityUserImpl.setGroupId(groupId);
		socialEquityUserImpl.setCompanyId(companyId);
		socialEquityUserImpl.setUserId(userId);
		socialEquityUserImpl.setContributionK(contributionK);
		socialEquityUserImpl.setContributionB(contributionB);
		socialEquityUserImpl.setParticipationK(participationK);
		socialEquityUserImpl.setParticipationB(participationB);
		socialEquityUserImpl.setRank(rank);

		socialEquityUserImpl.resetOriginalValues();

		return socialEquityUserImpl;
	}

	public long equityUserId;
	public long groupId;
	public long companyId;
	public long userId;
	public double contributionK;
	public double contributionB;
	public double participationK;
	public double participationB;
	public int rank;
}