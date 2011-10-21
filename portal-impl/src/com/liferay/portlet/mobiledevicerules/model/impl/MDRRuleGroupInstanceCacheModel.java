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

import com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance;

import java.io.Serializable;

import java.util.Date;

/**
 * The cache model class for representing MDRRuleGroupInstance in entity cache.
 *
 * @author Edward C. Han
 * @see MDRRuleGroupInstance
 * @generated
 */
public class MDRRuleGroupInstanceCacheModel implements CacheModel<MDRRuleGroupInstance>,
	Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(25);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", ruleGroupInstanceId=");
		sb.append(ruleGroupInstanceId);
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
		sb.append(", ruleGroupId=");
		sb.append(ruleGroupId);
		sb.append(", priority=");
		sb.append(priority);
		sb.append("}");

		return sb.toString();
	}

	public MDRRuleGroupInstance toEntityModel() {
		MDRRuleGroupInstanceImpl mdrRuleGroupInstanceImpl = new MDRRuleGroupInstanceImpl();

		if (uuid == null) {
			mdrRuleGroupInstanceImpl.setUuid(StringPool.BLANK);
		}
		else {
			mdrRuleGroupInstanceImpl.setUuid(uuid);
		}

		mdrRuleGroupInstanceImpl.setRuleGroupInstanceId(ruleGroupInstanceId);
		mdrRuleGroupInstanceImpl.setGroupId(groupId);
		mdrRuleGroupInstanceImpl.setCompanyId(companyId);
		mdrRuleGroupInstanceImpl.setUserId(userId);

		if (userName == null) {
			mdrRuleGroupInstanceImpl.setUserName(StringPool.BLANK);
		}
		else {
			mdrRuleGroupInstanceImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			mdrRuleGroupInstanceImpl.setCreateDate(null);
		}
		else {
			mdrRuleGroupInstanceImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			mdrRuleGroupInstanceImpl.setModifiedDate(null);
		}
		else {
			mdrRuleGroupInstanceImpl.setModifiedDate(new Date(modifiedDate));
		}

		mdrRuleGroupInstanceImpl.setClassNameId(classNameId);
		mdrRuleGroupInstanceImpl.setClassPK(classPK);
		mdrRuleGroupInstanceImpl.setRuleGroupId(ruleGroupId);
		mdrRuleGroupInstanceImpl.setPriority(priority);

		mdrRuleGroupInstanceImpl.resetOriginalValues();

		return mdrRuleGroupInstanceImpl;
	}

	public String uuid;
	public long ruleGroupInstanceId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long classNameId;
	public long classPK;
	public long ruleGroupId;
	public int priority;
}