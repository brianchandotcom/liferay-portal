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

package com.liferay.meris.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.meris.model.MerisSegmentRel;

import com.liferay.petra.string.StringBundler;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing MerisSegmentRel in entity cache.
 *
 * @author Eduardo Garcia
 * @see MerisSegmentRel
 * @generated
 */
@ProviderType
public class MerisSegmentRelCacheModel implements CacheModel<MerisSegmentRel>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof MerisSegmentRelCacheModel)) {
			return false;
		}

		MerisSegmentRelCacheModel merisSegmentRelCacheModel = (MerisSegmentRelCacheModel)obj;

		if (merisSegmentRelId == merisSegmentRelCacheModel.merisSegmentRelId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, merisSegmentRelId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("{merisSegmentRelId=");
		sb.append(merisSegmentRelId);
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
		sb.append(", merisSegmentId=");
		sb.append(merisSegmentId);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public MerisSegmentRel toEntityModel() {
		MerisSegmentRelImpl merisSegmentRelImpl = new MerisSegmentRelImpl();

		merisSegmentRelImpl.setMerisSegmentRelId(merisSegmentRelId);
		merisSegmentRelImpl.setGroupId(groupId);
		merisSegmentRelImpl.setCompanyId(companyId);
		merisSegmentRelImpl.setUserId(userId);

		if (userName == null) {
			merisSegmentRelImpl.setUserName("");
		}
		else {
			merisSegmentRelImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			merisSegmentRelImpl.setCreateDate(null);
		}
		else {
			merisSegmentRelImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			merisSegmentRelImpl.setModifiedDate(null);
		}
		else {
			merisSegmentRelImpl.setModifiedDate(new Date(modifiedDate));
		}

		merisSegmentRelImpl.setMerisSegmentId(merisSegmentId);
		merisSegmentRelImpl.setClassNameId(classNameId);
		merisSegmentRelImpl.setClassPK(classPK);

		merisSegmentRelImpl.resetOriginalValues();

		return merisSegmentRelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		merisSegmentRelId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		merisSegmentId = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(merisSegmentRelId);

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

		objectOutput.writeLong(merisSegmentId);

		objectOutput.writeLong(classNameId);

		objectOutput.writeLong(classPK);
	}

	public long merisSegmentRelId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long merisSegmentId;
	public long classNameId;
	public long classPK;
}