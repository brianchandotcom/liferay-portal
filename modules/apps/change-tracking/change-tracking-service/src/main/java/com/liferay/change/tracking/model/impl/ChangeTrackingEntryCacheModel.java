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

package com.liferay.change.tracking.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.change.tracking.model.ChangeTrackingEntry;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;

import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing ChangeTrackingEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see ChangeTrackingEntry
 * @generated
 */
@ProviderType
public class ChangeTrackingEntryCacheModel implements CacheModel<ChangeTrackingEntry>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ChangeTrackingEntryCacheModel)) {
			return false;
		}

		ChangeTrackingEntryCacheModel changeTrackingEntryCacheModel = (ChangeTrackingEntryCacheModel)obj;

		if (changeTrackingEntryId == changeTrackingEntryCacheModel.changeTrackingEntryId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, changeTrackingEntryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(19);

		sb.append("{changeTrackingEntryId=");
		sb.append(changeTrackingEntryId);
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
		sb.append(", resourcePrimKey=");
		sb.append(resourcePrimKey);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ChangeTrackingEntry toEntityModel() {
		ChangeTrackingEntryImpl changeTrackingEntryImpl = new ChangeTrackingEntryImpl();

		changeTrackingEntryImpl.setChangeTrackingEntryId(changeTrackingEntryId);
		changeTrackingEntryImpl.setCompanyId(companyId);
		changeTrackingEntryImpl.setUserId(userId);

		if (userName == null) {
			changeTrackingEntryImpl.setUserName("");
		}
		else {
			changeTrackingEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			changeTrackingEntryImpl.setCreateDate(null);
		}
		else {
			changeTrackingEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			changeTrackingEntryImpl.setModifiedDate(null);
		}
		else {
			changeTrackingEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		changeTrackingEntryImpl.setClassNameId(classNameId);
		changeTrackingEntryImpl.setClassPK(classPK);
		changeTrackingEntryImpl.setResourcePrimKey(resourcePrimKey);

		changeTrackingEntryImpl.resetOriginalValues();

		return changeTrackingEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		changeTrackingEntryId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();

		resourcePrimKey = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(changeTrackingEntryId);

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

		objectOutput.writeLong(resourcePrimKey);
	}

	public long changeTrackingEntryId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long classNameId;
	public long classPK;
	public long resourcePrimKey;
}