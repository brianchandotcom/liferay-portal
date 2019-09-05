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

package com.liferay.portlet.social.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.social.kernel.model.SocialActivityCounter;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing SocialActivityCounter in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class SocialActivityCounterCacheModel
	implements CacheModel<SocialActivityCounter>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SocialActivityCounterCacheModel)) {
			return false;
		}

		SocialActivityCounterCacheModel socialActivityCounterCacheModel =
			(SocialActivityCounterCacheModel)obj;

		if (activityCounterId ==
				socialActivityCounterCacheModel.activityCounterId) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, activityCounterId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(27);

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
		sb.append(", active=");
		sb.append(active);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public SocialActivityCounter toEntityModel() {
		SocialActivityCounterImpl socialActivityCounterImpl =
			new SocialActivityCounterImpl();

		socialActivityCounterImpl.setActivityCounterId(activityCounterId);
		socialActivityCounterImpl.setGroupId(groupId);
		socialActivityCounterImpl.setCompanyId(companyId);
		socialActivityCounterImpl.setClassNameId(classNameId);
		socialActivityCounterImpl.setClassPK(classPK);

		if (name == null) {
			socialActivityCounterImpl.setName("");
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
		socialActivityCounterImpl.setActive(active);

		socialActivityCounterImpl.resetOriginalValues();

		return socialActivityCounterImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		activityCounterId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();
		name = objectInput.readUTF();

		ownerType = objectInput.readInt();

		currentValue = objectInput.readInt();

		totalValue = objectInput.readInt();

		graceValue = objectInput.readInt();

		startPeriod = objectInput.readInt();

		endPeriod = objectInput.readInt();

		active = objectInput.readBoolean();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(activityCounterId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(classNameId);

		objectOutput.writeLong(classPK);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		objectOutput.writeInt(ownerType);

		objectOutput.writeInt(currentValue);

		objectOutput.writeInt(totalValue);

		objectOutput.writeInt(graceValue);

		objectOutput.writeInt(startPeriod);

		objectOutput.writeInt(endPeriod);

		objectOutput.writeBoolean(active);
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
	public boolean active;

}