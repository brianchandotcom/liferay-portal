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

package com.liferay.service.access.control.profile.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import com.liferay.service.access.control.profile.model.SACPEntry;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing SACPEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see SACPEntry
 * @generated
 */
@ProviderType
public class SACPEntryCacheModel implements CacheModel<SACPEntry>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SACPEntryCacheModel)) {
			return false;
		}

		SACPEntryCacheModel sacpEntryCacheModel = (SACPEntryCacheModel)obj;

		if (sacpEntryId == sacpEntryCacheModel.sacpEntryId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, sacpEntryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", sacpEntryId=");
		sb.append(sacpEntryId);
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
		sb.append(", allowedServices=");
		sb.append(allowedServices);
		sb.append(", name=");
		sb.append(name);
		sb.append(", title=");
		sb.append(title);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public SACPEntry toEntityModel() {
		SACPEntryImpl sacpEntryImpl = new SACPEntryImpl();

		if (uuid == null) {
			sacpEntryImpl.setUuid(StringPool.BLANK);
		}
		else {
			sacpEntryImpl.setUuid(uuid);
		}

		sacpEntryImpl.setSacpEntryId(sacpEntryId);
		sacpEntryImpl.setCompanyId(companyId);
		sacpEntryImpl.setUserId(userId);

		if (userName == null) {
			sacpEntryImpl.setUserName(StringPool.BLANK);
		}
		else {
			sacpEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			sacpEntryImpl.setCreateDate(null);
		}
		else {
			sacpEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			sacpEntryImpl.setModifiedDate(null);
		}
		else {
			sacpEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (allowedServices == null) {
			sacpEntryImpl.setAllowedServices(StringPool.BLANK);
		}
		else {
			sacpEntryImpl.setAllowedServices(allowedServices);
		}

		if (name == null) {
			sacpEntryImpl.setName(StringPool.BLANK);
		}
		else {
			sacpEntryImpl.setName(name);
		}

		if (title == null) {
			sacpEntryImpl.setTitle(StringPool.BLANK);
		}
		else {
			sacpEntryImpl.setTitle(title);
		}

		sacpEntryImpl.resetOriginalValues();

		return sacpEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();
		sacpEntryId = objectInput.readLong();
		companyId = objectInput.readLong();
		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		allowedServices = objectInput.readUTF();
		name = objectInput.readUTF();
		title = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(sacpEntryId);
		objectOutput.writeLong(companyId);
		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		if (allowedServices == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(allowedServices);
		}

		if (name == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (title == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(title);
		}
	}

	public String uuid;
	public long sacpEntryId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String allowedServices;
	public String name;
	public String title;
}