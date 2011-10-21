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

import com.liferay.portlet.social.model.SocialActivityCounter;

import java.io.Serializable;

/**
 * The cache model class for representing SocialActivityCounter in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see SocialActivityCounter
 * @generated
 */
public class SocialActivityCounterCacheModel implements CacheModel<SocialActivityCounter>,
	Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(25);

		sb.append("{activityCounterId=");
		sb.append(activityCounterId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", name=");
		sb.append(name);
		sb.append(", ownerType=");
		sb.append(ownerType);
		sb.append(", currentValue=");
		sb.append(currentValue);
		sb.append(", totalValue=");
		sb.append(totalValue);
		sb.append(", graceValue=");
		sb.append(graceValue);
		sb.append(", startPeriod=");
		sb.append(startPeriod);
		sb.append(", endPeriod=");
		sb.append(endPeriod);
		sb.append("}");

		return sb.toString();
	}

	public SocialActivityCounter toEntityModel() {
		SocialActivityCounterImpl socialActivityCounterImpl = new SocialActivityCounterImpl();

		socialActivityCounterImpl.setActivityCounterId(activityCounterId);
		socialActivityCounterImpl.setGroupId(groupId);
		socialActivityCounterImpl.setCompanyId(companyId);
		socialActivityCounterImpl.setClassNameId(classNameId);
		socialActivityCounterImpl.setClassPK(classPK);

		if (name == null) {
			socialActivityCounterImpl.setName(StringPool.BLANK);
		}
		else {
			socialActivityCounterImpl.setName(name);
		}

		socialActivityCounterImpl.setOwnerType(ownerType);
		socialActivityCounterImpl.setCurrentValue(currentValue);
		socialActivityCounterImpl.setTotalValue(totalValue);
		socialActivityCounterImpl.setGraceValue(graceValue);
		socialActivityCounterImpl.setStartPeriod(startPeriod);
		socialActivityCounterImpl.setEndPeriod(endPeriod);

		socialActivityCounterImpl.resetOriginalValues();

		return socialActivityCounterImpl;
	}

	public long activityCounterId;
	public long groupId;
	public long companyId;
	public long classNameId;
	public long classPK;
	public String name;
	public int ownerType;
	public int currentValue;
	public int totalValue;
	public int graceValue;
	public int startPeriod;
	public int endPeriod;
}