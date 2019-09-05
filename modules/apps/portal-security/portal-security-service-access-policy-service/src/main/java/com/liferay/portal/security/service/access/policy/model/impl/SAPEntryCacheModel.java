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

package com.liferay.portal.security.service.access.policy.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.security.service.access.policy.model.SAPEntry;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing SAPEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class SAPEntryCacheModel
	implements CacheModel<SAPEntry>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SAPEntryCacheModel)) {
			return false;
		}

		SAPEntryCacheModel sapEntryCacheModel = (SAPEntryCacheModel)obj;

		if (sapEntryId == sapEntryCacheModel.sapEntryId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, sapEntryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(25);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", sapEntryId=");
		sb.append(sapEntryId);
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
		sb.append(", allowedServiceSignatures=");
		sb.append(allowedServiceSignatures);
		sb.append(", defaultSAPEntry=");
		sb.append(defaultSAPEntry);
		sb.append(", enabled=");
		sb.append(enabled);
		sb.append(", name=");
		sb.append(name);
		sb.append(", title=");
		sb.append(title);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public SAPEntry toEntityModel() {
		SAPEntryImpl sapEntryImpl = new SAPEntryImpl();

		if (uuid == null) {
			sapEntryImpl.setUuid("");
		}
		else {
			sapEntryImpl.setUuid(uuid);
		}

		sapEntryImpl.setSapEntryId(sapEntryId);
		sapEntryImpl.setCompanyId(companyId);
		sapEntryImpl.setUserId(userId);

		if (userName == null) {
			sapEntryImpl.setUserName("");
		}
		else {
			sapEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			sapEntryImpl.setCreateDate(null);
		}
		else {
			sapEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			sapEntryImpl.setModifiedDate(null);
		}
		else {
			sapEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (allowedServiceSignatures == null) {
			sapEntryImpl.setAllowedServiceSignatures("");
		}
		else {
			sapEntryImpl.setAllowedServiceSignatures(allowedServiceSignatures);
		}

		sapEntryImpl.setDefaultSAPEntry(defaultSAPEntry);
		sapEntryImpl.setEnabled(enabled);

		if (name == null) {
			sapEntryImpl.setName("");
		}
		else {
			sapEntryImpl.setName(name);
		}

		if (title == null) {
			sapEntryImpl.setTitle("");
		}
		else {
			sapEntryImpl.setTitle(title);
		}

		sapEntryImpl.resetOriginalValues();

		return sapEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		sapEntryId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		allowedServiceSignatures = objectInput.readUTF();

		defaultSAPEntry = objectInput.readBoolean();

		enabled = objectInput.readBoolean();
		name = objectInput.readUTF();
		title = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(sapEntryId);

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

		if (allowedServiceSignatures == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(allowedServiceSignatures);
		}

		objectOutput.writeBoolean(defaultSAPEntry);

		objectOutput.writeBoolean(enabled);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (title == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(title);
		}
	}

	public String uuid;
	public long sapEntryId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String allowedServiceSignatures;
	public boolean defaultSAPEntry;
	public boolean enabled;
	public String name;
	public String title;

}