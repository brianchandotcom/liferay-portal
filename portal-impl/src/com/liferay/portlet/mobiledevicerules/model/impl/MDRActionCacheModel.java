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

package com.liferay.portlet.mobiledevicerules.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import com.liferay.portlet.mobiledevicerules.model.MDRAction;

import java.io.Serializable;

import java.util.Date;

/**
 * The cache model class for representing MDRAction in entity cache.
 *
 * @author Edward C. Han
 * @see MDRAction
 * @generated
 */
public class MDRActionCacheModel implements CacheModel<MDRAction>, Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(31);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", actionId=");
		sb.append(actionId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", ruleGroupInstanceId=");
		sb.append(ruleGroupInstanceId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append(", type=");
		sb.append(type);
		sb.append(", typeSettings=");
		sb.append(typeSettings);
		sb.append("}");

		return sb.toString();
	}

	public MDRAction toEntityModel() {
		MDRActionImpl mdrActionImpl = new MDRActionImpl();

		if (uuid == null) {
			mdrActionImpl.setUuid(StringPool.BLANK);
		}
		else {
			mdrActionImpl.setUuid(uuid);
		}

		mdrActionImpl.setActionId(actionId);
		mdrActionImpl.setGroupId(groupId);
		mdrActionImpl.setCompanyId(companyId);
		mdrActionImpl.setUserId(userId);

		if (userName == null) {
			mdrActionImpl.setUserName(StringPool.BLANK);
		}
		else {
			mdrActionImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			mdrActionImpl.setCreateDate(null);
		}
		else {
			mdrActionImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			mdrActionImpl.setModifiedDate(null);
		}
		else {
			mdrActionImpl.setModifiedDate(new Date(modifiedDate));
		}

		mdrActionImpl.setClassNameId(classNameId);
		mdrActionImpl.setClassPK(classPK);
		mdrActionImpl.setRuleGroupInstanceId(ruleGroupInstanceId);

		if (name == null) {
			mdrActionImpl.setName(StringPool.BLANK);
		}
		else {
			mdrActionImpl.setName(name);
		}

		if (description == null) {
			mdrActionImpl.setDescription(StringPool.BLANK);
		}
		else {
			mdrActionImpl.setDescription(description);
		}

		if (type == null) {
			mdrActionImpl.setType(StringPool.BLANK);
		}
		else {
			mdrActionImpl.setType(type);
		}

		if (typeSettings == null) {
			mdrActionImpl.setTypeSettings(StringPool.BLANK);
		}
		else {
			mdrActionImpl.setTypeSettings(typeSettings);
		}

		mdrActionImpl.resetOriginalValues();

		return mdrActionImpl;
	}

	public String uuid;
	public long actionId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long classNameId;
	public long classPK;
	public long ruleGroupInstanceId;
	public String name;
	public String description;
	public String type;
	public String typeSettings;
}