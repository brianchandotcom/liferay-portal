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

import com.liferay.portlet.social.model.SocialActivitySetting;

import java.io.Serializable;

/**
 * The cache model class for representing SocialActivitySetting in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see SocialActivitySetting
 * @generated
 */
public class SocialActivitySettingCacheModel implements CacheModel<SocialActivitySetting>,
	Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(15);

		sb.append("{activitySettingId=");
		sb.append(activitySettingId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", activityType=");
		sb.append(activityType);
		sb.append(", name=");
		sb.append(name);
		sb.append(", value=");
		sb.append(value);
		sb.append("}");

		return sb.toString();
	}

	public SocialActivitySetting toEntityModel() {
		SocialActivitySettingImpl socialActivitySettingImpl = new SocialActivitySettingImpl();

		socialActivitySettingImpl.setActivitySettingId(activitySettingId);
		socialActivitySettingImpl.setGroupId(groupId);
		socialActivitySettingImpl.setCompanyId(companyId);
		socialActivitySettingImpl.setClassNameId(classNameId);
		socialActivitySettingImpl.setActivityType(activityType);

		if (name == null) {
			socialActivitySettingImpl.setName(StringPool.BLANK);
		}
		else {
			socialActivitySettingImpl.setName(name);
		}

		if (value == null) {
			socialActivitySettingImpl.setValue(StringPool.BLANK);
		}
		else {
			socialActivitySettingImpl.setValue(value);
		}

		socialActivitySettingImpl.resetOriginalValues();

		return socialActivitySettingImpl;
	}

	public long activitySettingId;
	public long groupId;
	public long companyId;
	public long classNameId;
	public int activityType;
	public String name;
	public String value;
}