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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import com.liferay.portlet.social.model.SocialEquityLog;

import java.io.Serializable;

/**
 * The cache model class for representing SocialEquityLog in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see SocialEquityLog
 * @generated
 */
public class SocialEquityLogCacheModel implements CacheModel<SocialEquityLog>,
	Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(25);

		sb.append("{equityLogId=");
		sb.append(equityLogId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", assetEntryId=");
		sb.append(assetEntryId);
		sb.append(", actionId=");
		sb.append(actionId);
		sb.append(", actionDate=");
		sb.append(actionDate);
		sb.append(", active=");
		sb.append(active);
		sb.append(", expiration=");
		sb.append(expiration);
		sb.append(", type=");
		sb.append(type);
		sb.append(", value=");
		sb.append(value);
		sb.append(", extraData=");
		sb.append(extraData);
		sb.append("}");

		return sb.toString();
	}

	public SocialEquityLog toEntityModel() {
		SocialEquityLogImpl socialEquityLogImpl = new SocialEquityLogImpl();

		socialEquityLogImpl.setEquityLogId(equityLogId);
		socialEquityLogImpl.setGroupId(groupId);
		socialEquityLogImpl.setCompanyId(companyId);
		socialEquityLogImpl.setUserId(userId);
		socialEquityLogImpl.setAssetEntryId(assetEntryId);

		if (actionId == null) {
			socialEquityLogImpl.setActionId(StringPool.BLANK);
		}
		else {
			socialEquityLogImpl.setActionId(actionId);
		}

		socialEquityLogImpl.setActionDate(actionDate);
		socialEquityLogImpl.setActive(active);
		socialEquityLogImpl.setExpiration(expiration);
		socialEquityLogImpl.setType(type);
		socialEquityLogImpl.setValue(value);

		if (extraData == null) {
			socialEquityLogImpl.setExtraData(StringPool.BLANK);
		}
		else {
			socialEquityLogImpl.setExtraData(extraData);
		}

		socialEquityLogImpl.resetOriginalValues();

		return socialEquityLogImpl;
	}

	public long equityLogId;
	public long groupId;
	public long companyId;
	public long userId;
	public long assetEntryId;
	public String actionId;
	public int actionDate;
	public boolean active;
	public int expiration;
	public int type;
	public int value;
	public String extraData;
}