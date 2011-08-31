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

import com.liferay.portlet.social.model.SocialActivityStatsEntry;

/**
 * The cache model class for representing SocialActivityStatsEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see SocialActivityStatsEntry
 * @generated
 */
public class SocialActivityStatsEntryCacheModel implements CacheModel<SocialActivityStatsEntry> {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(25);

		sb.append("{activityStatsEntryId=");
		sb.append(activityStatsEntryId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", classType=");
		sb.append(classType);
		sb.append(", statName=");
		sb.append(statName);
		sb.append(", currentValue=");
		sb.append(currentValue);
		sb.append(", overallValue=");
		sb.append(overallValue);
		sb.append(", graceValue=");
		sb.append(graceValue);
		sb.append(", statPeriodStart=");
		sb.append(statPeriodStart);
		sb.append(", statPeriodEnd=");
		sb.append(statPeriodEnd);
		sb.append("}");

		return sb.toString();
	}

	public SocialActivityStatsEntry toEntityModel() {
		SocialActivityStatsEntryImpl socialActivityStatsEntryImpl = new SocialActivityStatsEntryImpl();

		socialActivityStatsEntryImpl.setActivityStatsEntryId(activityStatsEntryId);
		socialActivityStatsEntryImpl.setGroupId(groupId);
		socialActivityStatsEntryImpl.setCompanyId(companyId);
		socialActivityStatsEntryImpl.setClassNameId(classNameId);
		socialActivityStatsEntryImpl.setClassPK(classPK);
		socialActivityStatsEntryImpl.setClassType(classType);

		if (statName == null) {
			socialActivityStatsEntryImpl.setStatName(StringPool.BLANK);
		}
		else {
			socialActivityStatsEntryImpl.setStatName(statName);
		}

		socialActivityStatsEntryImpl.setCurrentValue(currentValue);
		socialActivityStatsEntryImpl.setOverallValue(overallValue);
		socialActivityStatsEntryImpl.setGraceValue(graceValue);
		socialActivityStatsEntryImpl.setStatPeriodStart(statPeriodStart);
		socialActivityStatsEntryImpl.setStatPeriodEnd(statPeriodEnd);

		socialActivityStatsEntryImpl.resetOriginalValues();

		return socialActivityStatsEntryImpl;
	}

	public long activityStatsEntryId;
	public long groupId;
	public long companyId;
	public long classNameId;
	public long classPK;
	public int classType;
	public String statName;
	public int currentValue;
	public int overallValue;
	public int graceValue;
	public int statPeriodStart;
	public int statPeriodEnd;
}