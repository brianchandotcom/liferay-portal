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

import com.liferay.portlet.social.model.SocialEquityHistory;

import java.io.Serializable;

import java.util.Date;

/**
 * The cache model class for representing SocialEquityHistory in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see SocialEquityHistory
 * @generated
 */
public class SocialEquityHistoryCacheModel implements CacheModel<SocialEquityHistory>,
	Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(13);

		sb.append("{equityHistoryId=");
		sb.append(equityHistoryId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", personalEquity=");
		sb.append(personalEquity);
		sb.append("}");

		return sb.toString();
	}

	public SocialEquityHistory toEntityModel() {
		SocialEquityHistoryImpl socialEquityHistoryImpl = new SocialEquityHistoryImpl();

		socialEquityHistoryImpl.setEquityHistoryId(equityHistoryId);
		socialEquityHistoryImpl.setGroupId(groupId);
		socialEquityHistoryImpl.setCompanyId(companyId);
		socialEquityHistoryImpl.setUserId(userId);

		if (createDate == Long.MIN_VALUE) {
			socialEquityHistoryImpl.setCreateDate(null);
		}
		else {
			socialEquityHistoryImpl.setCreateDate(new Date(createDate));
		}

		socialEquityHistoryImpl.setPersonalEquity(personalEquity);

		socialEquityHistoryImpl.resetOriginalValues();

		return socialEquityHistoryImpl;
	}

	public long equityHistoryId;
	public long groupId;
	public long companyId;
	public long userId;
	public long createDate;
	public int personalEquity;
}