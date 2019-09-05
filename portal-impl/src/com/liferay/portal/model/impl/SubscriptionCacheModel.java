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

package com.liferay.portal.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.kernel.model.Subscription;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing Subscription in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @deprecated
 * @generated
 */
@Deprecated
public class SubscriptionCacheModel
	implements CacheModel<Subscription>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SubscriptionCacheModel)) {
			return false;
		}

		SubscriptionCacheModel subscriptionCacheModel =
			(SubscriptionCacheModel)obj;

		if ((subscriptionId == subscriptionCacheModel.subscriptionId) &&
			(mvccVersion == subscriptionCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, subscriptionId);

		return HashUtil.hash(hashCode, mvccVersion);
	}

	@Override
	public long getMvccVersion() {
		return mvccVersion;
	}

	@Override
	public void setMvccVersion(long mvccVersion) {
		this.mvccVersion = mvccVersion;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(23);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", subscriptionId=");
		sb.append(subscriptionId);
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
		sb.append(", frequency=");
		sb.append(frequency);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public Subscription toEntityModel() {
		SubscriptionImpl subscriptionImpl = new SubscriptionImpl();

		subscriptionImpl.setMvccVersion(mvccVersion);
		subscriptionImpl.setSubscriptionId(subscriptionId);
		subscriptionImpl.setGroupId(groupId);
		subscriptionImpl.setCompanyId(companyId);
		subscriptionImpl.setUserId(userId);

		if (userName == null) {
			subscriptionImpl.setUserName("");
		}
		else {
			subscriptionImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			subscriptionImpl.setCreateDate(null);
		}
		else {
			subscriptionImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			subscriptionImpl.setModifiedDate(null);
		}
		else {
			subscriptionImpl.setModifiedDate(new Date(modifiedDate));
		}

		subscriptionImpl.setClassNameId(classNameId);
		subscriptionImpl.setClassPK(classPK);

		if (frequency == null) {
			subscriptionImpl.setFrequency("");
		}
		else {
			subscriptionImpl.setFrequency(frequency);
		}

		subscriptionImpl.resetOriginalValues();

		return subscriptionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		subscriptionId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();
		frequency = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(subscriptionId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		objectOutput.writeLong(classNameId);

		objectOutput.writeLong(classPK);

		if (frequency == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(frequency);
		}
	}

	public long mvccVersion;
	public long subscriptionId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long classNameId;
	public long classPK;
	public String frequency;

}