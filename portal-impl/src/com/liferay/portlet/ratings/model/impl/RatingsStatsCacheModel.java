/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.ratings.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.CacheModel;

import com.liferay.portlet.ratings.model.RatingsStats;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing RatingsStats in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see RatingsStats
 * @generated
 */
@ProviderType
public class RatingsStatsCacheModel implements CacheModel<RatingsStats>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof RatingsStatsCacheModel)) {
			return false;
		}

		RatingsStatsCacheModel ratingsStatsCacheModel = (RatingsStatsCacheModel)obj;

		if (statsId == ratingsStatsCacheModel.statsId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, statsId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(15);

		sb.append("{statsId=");
		sb.append(statsId);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", totalEntries=");
		sb.append(totalEntries);
		sb.append(", totalScore=");
		sb.append(totalScore);
		sb.append(", averageScore=");
		sb.append(averageScore);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public RatingsStats toEntityModel() {
		RatingsStatsImpl ratingsStatsImpl = new RatingsStatsImpl();

		ratingsStatsImpl.setStatsId(statsId);
		ratingsStatsImpl.setClassNameId(classNameId);
		ratingsStatsImpl.setClassPK(classPK);
		ratingsStatsImpl.setTotalEntries(totalEntries);
		ratingsStatsImpl.setTotalScore(totalScore);
		ratingsStatsImpl.setAverageScore(averageScore);
		ratingsStatsImpl.setCompanyId(companyId);

		ratingsStatsImpl.resetOriginalValues();

		return ratingsStatsImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		statsId = objectInput.readLong();
		classNameId = objectInput.readLong();
		classPK = objectInput.readLong();
		totalEntries = objectInput.readInt();
		totalScore = objectInput.readDouble();
		averageScore = objectInput.readDouble();
		companyId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(statsId);
		objectOutput.writeLong(classNameId);
		objectOutput.writeLong(classPK);
		objectOutput.writeInt(totalEntries);
		objectOutput.writeDouble(totalScore);
		objectOutput.writeDouble(averageScore);
		objectOutput.writeLong(companyId);
	}

	public long statsId;
	public long classNameId;
	public long classPK;
	public int totalEntries;
	public double totalScore;
	public double averageScore;
	public long companyId;
}