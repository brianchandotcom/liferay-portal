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

import com.liferay.portlet.social.model.SocialEquityGroupSetting;

import java.io.Serializable;

/**
 * The cache model class for representing SocialEquityGroupSetting in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see SocialEquityGroupSetting
 * @generated
 */
public class SocialEquityGroupSettingCacheModel implements CacheModel<SocialEquityGroupSetting>,
	Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(13);

		sb.append("{equityGroupSettingId=");
		sb.append(equityGroupSettingId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", type=");
		sb.append(type);
		sb.append(", enabled=");
		sb.append(enabled);
		sb.append("}");

		return sb.toString();
	}

	public SocialEquityGroupSetting toEntityModel() {
		SocialEquityGroupSettingImpl socialEquityGroupSettingImpl = new SocialEquityGroupSettingImpl();

		socialEquityGroupSettingImpl.setEquityGroupSettingId(equityGroupSettingId);
		socialEquityGroupSettingImpl.setGroupId(groupId);
		socialEquityGroupSettingImpl.setCompanyId(companyId);
		socialEquityGroupSettingImpl.setClassNameId(classNameId);
		socialEquityGroupSettingImpl.setType(type);
		socialEquityGroupSettingImpl.setEnabled(enabled);

		socialEquityGroupSettingImpl.resetOriginalValues();

		return socialEquityGroupSettingImpl;
	}

	public long equityGroupSettingId;
	public long groupId;
	public long companyId;
	public long classNameId;
	public int type;
	public boolean enabled;
}