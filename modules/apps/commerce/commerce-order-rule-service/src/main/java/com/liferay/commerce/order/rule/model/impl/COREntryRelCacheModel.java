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

package com.liferay.commerce.order.rule.model.impl;

import com.liferay.commerce.order.rule.model.COREntryRel;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing COREntryRel in entity cache.
 *
 * @author Luca Pellizzon
 * @generated
 */
public class COREntryRelCacheModel
	implements CacheModel<COREntryRel>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof COREntryRelCacheModel)) {
			return false;
		}

		COREntryRelCacheModel corEntryRelCacheModel =
			(COREntryRelCacheModel)object;

		if (COREntryRelId == corEntryRelCacheModel.COREntryRelId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, COREntryRelId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(19);

		sb.append("{COREntryRelId=");
		sb.append(COREntryRelId);
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
		sb.append(", COREntryId=");
		sb.append(COREntryId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public COREntryRel toEntityModel() {
		COREntryRelImpl corEntryRelImpl = new COREntryRelImpl();

		corEntryRelImpl.setCOREntryRelId(COREntryRelId);
		corEntryRelImpl.setCompanyId(companyId);
		corEntryRelImpl.setUserId(userId);

		if (userName == null) {
			corEntryRelImpl.setUserName("");
		}
		else {
			corEntryRelImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			corEntryRelImpl.setCreateDate(null);
		}
		else {
			corEntryRelImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			corEntryRelImpl.setModifiedDate(null);
		}
		else {
			corEntryRelImpl.setModifiedDate(new Date(modifiedDate));
		}

		corEntryRelImpl.setClassNameId(classNameId);
		corEntryRelImpl.setClassPK(classPK);
		corEntryRelImpl.setCOREntryId(COREntryId);

		corEntryRelImpl.resetOriginalValues();

		return corEntryRelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		COREntryRelId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();

		COREntryId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(COREntryRelId);

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

		objectOutput.writeLong(COREntryId);
	}

	public long COREntryRelId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long classNameId;
	public long classPK;
	public long COREntryId;

}