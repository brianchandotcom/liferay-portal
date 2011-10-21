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

import com.liferay.portlet.social.model.SocialEquitySetting;

import java.io.Serializable;

/**
 * The cache model class for representing SocialEquitySetting in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see SocialEquitySetting
 * @generated
 */
public class SocialEquitySettingCacheModel implements CacheModel<SocialEquitySetting>,
	Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("{equitySettingId=");
		sb.append(equitySettingId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", actionId=");
		sb.append(actionId);
		sb.append(", dailyLimit=");
		sb.append(dailyLimit);
		sb.append(", lifespan=");
		sb.append(lifespan);
		sb.append(", type=");
		sb.append(type);
		sb.append(", uniqueEntry=");
		sb.append(uniqueEntry);
		sb.append(", value=");
		sb.append(value);
		sb.append("}");

		return sb.toString();
	}

	public SocialEquitySetting toEntityModel() {
		SocialEquitySettingImpl socialEquitySettingImpl = new SocialEquitySettingImpl();

		socialEquitySettingImpl.setEquitySettingId(equitySettingId);
		socialEquitySettingImpl.setGroupId(groupId);
		socialEquitySettingImpl.setCompanyId(companyId);
		socialEquitySettingImpl.setClassNameId(classNameId);

		if (actionId == null) {
			socialEquitySettingImpl.setActionId(StringPool.BLANK);
		}
		else {
			socialEquitySettingImpl.setActionId(actionId);
		}

		socialEquitySettingImpl.setDailyLimit(dailyLimit);
		socialEquitySettingImpl.setLifespan(lifespan);
		socialEquitySettingImpl.setType(type);
		socialEquitySettingImpl.setUniqueEntry(uniqueEntry);
		socialEquitySettingImpl.setValue(value);

		socialEquitySettingImpl.resetOriginalValues();

		return socialEquitySettingImpl;
	}

	public long equitySettingId;
	public long groupId;
	public long companyId;
	public long classNameId;
	public String actionId;
	public int dailyLimit;
	public int lifespan;
	public int type;
	public boolean uniqueEntry;
	public int value;
}