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

package com.liferay.commerce.product.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.model.CPDisplayLayout;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CPDisplayLayout in entity cache.
 *
 * @author Marco Leo
 * @see CPDisplayLayout
 * @generated
 */
@ProviderType
public class CPDisplayLayoutCacheModel implements CacheModel<CPDisplayLayout>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CPDisplayLayoutCacheModel)) {
			return false;
		}

		CPDisplayLayoutCacheModel cpDisplayLayoutCacheModel = (CPDisplayLayoutCacheModel)obj;

		if (CPDisplayLayoutId == cpDisplayLayoutCacheModel.CPDisplayLayoutId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, CPDisplayLayoutId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(23);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", CPDisplayLayoutId=");
		sb.append(CPDisplayLayoutId);
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
		sb.append(", layoutUuid=");
		sb.append(layoutUuid);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CPDisplayLayout toEntityModel() {
		CPDisplayLayoutImpl cpDisplayLayoutImpl = new CPDisplayLayoutImpl();

		if (uuid == null) {
			cpDisplayLayoutImpl.setUuid("");
		}
		else {
			cpDisplayLayoutImpl.setUuid(uuid);
		}

		cpDisplayLayoutImpl.setCPDisplayLayoutId(CPDisplayLayoutId);
		cpDisplayLayoutImpl.setGroupId(groupId);
		cpDisplayLayoutImpl.setCompanyId(companyId);
		cpDisplayLayoutImpl.setUserId(userId);

		if (userName == null) {
			cpDisplayLayoutImpl.setUserName("");
		}
		else {
			cpDisplayLayoutImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			cpDisplayLayoutImpl.setCreateDate(null);
		}
		else {
			cpDisplayLayoutImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			cpDisplayLayoutImpl.setModifiedDate(null);
		}
		else {
			cpDisplayLayoutImpl.setModifiedDate(new Date(modifiedDate));
		}

		cpDisplayLayoutImpl.setClassNameId(classNameId);
		cpDisplayLayoutImpl.setClassPK(classPK);

		if (layoutUuid == null) {
			cpDisplayLayoutImpl.setLayoutUuid("");
		}
		else {
			cpDisplayLayoutImpl.setLayoutUuid(layoutUuid);
		}

		cpDisplayLayoutImpl.resetOriginalValues();

		return cpDisplayLayoutImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		CPDisplayLayoutId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();
		layoutUuid = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(CPDisplayLayoutId);

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

		if (layoutUuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(layoutUuid);
		}
	}

	public String uuid;
	public long CPDisplayLayoutId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long classNameId;
	public long classPK;
	public String layoutUuid;
}