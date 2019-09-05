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
import com.liferay.social.kernel.model.SocialActivityLimit;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing SocialActivityLimit in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class SocialActivityLimitCacheModel
	implements CacheModel<SocialActivityLimit>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SocialActivityLimitCacheModel)) {
			return false;
		}

		SocialActivityLimitCacheModel socialActivityLimitCacheModel =
			(SocialActivityLimitCacheModel)obj;

		if (activityLimitId == socialActivityLimitCacheModel.activityLimitId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, activityLimitId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(19);

		sb.append("{activityLimitId=");
		sb.append(activityLimitId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", activityType=");
		sb.append(activityType);
		sb.append(", activityCounterName=");
		sb.append(activityCounterName);
		sb.append(", value=");
		sb.append(value);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public SocialActivityLimit toEntityModel() {
		SocialActivityLimitImpl socialActivityLimitImpl =
			new SocialActivityLimitImpl();

		socialActivityLimitImpl.setActivityLimitId(activityLimitId);
		socialActivityLimitImpl.setGroupId(groupId);
		socialActivityLimitImpl.setCompanyId(companyId);
		socialActivityLimitImpl.setUserId(userId);
		socialActivityLimitImpl.setClassNameId(classNameId);
		socialActivityLimitImpl.setClassPK(classPK);
		socialActivityLimitImpl.setActivityType(activityType);

		if (activityCounterName == null) {
			socialActivityLimitImpl.setActivityCounterName("");
		}
		else {
			socialActivityLimitImpl.setActivityCounterName(activityCounterName);
		}

		if (value == null) {
			socialActivityLimitImpl.setValue("");
		}
		else {
			socialActivityLimitImpl.setValue(value);
		}

		socialActivityLimitImpl.resetOriginalValues();

		return socialActivityLimitImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		activityLimitId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();

		activityType = objectInput.readInt();
		activityCounterName = objectInput.readUTF();
		value = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(activityLimitId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		objectOutput.writeLong(classNameId);

		objectOutput.writeLong(classPK);

		objectOutput.writeInt(activityType);

		if (activityCounterName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(activityCounterName);
		}

		if (value == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(value);
		}
	}

	public long activityLimitId;
	public long groupId;
	public long companyId;
	public long userId;
	public long classNameId;
	public long classPK;
	public int activityType;
	public String activityCounterName;
	public String value;

}