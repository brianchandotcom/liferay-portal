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

package com.liferay.service.access.policy.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import com.liferay.service.access.policy.model.SAPEntry;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing SAPEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see SAPEntry
 * @generated
 */
@ProviderType
public class SAPEntryCacheModel implements CacheModel<SAPEntry>, Externalizable {
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
		StringBundler sb = new StringBundler(23);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", sapEntryId=");
		sb.append(sapEntryId);
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
		sb.append(", name=");
		sb.append(name);
		sb.append(", title=");
		sb.append(title);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public SAPEntry toEntityModel() {
		SAPEntryImpl sapEntryImpl = new SAPEntryImpl();

		if (uuid == null) {
			sapEntryImpl.setUuid(StringPool.BLANK);
		}
		else {
			sapEntryImpl.setUuid(uuid);
		}

		sapEntryImpl.setSapEntryId(sapEntryId);
		sapEntryImpl.setUserId(userId);

		if (userName == null) {
			sapEntryImpl.setUserName(StringPool.BLANK);
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
			sapEntryImpl.setAllowedServiceSignatures(StringPool.BLANK);
		}
		else {
			sapEntryImpl.setAllowedServiceSignatures(allowedServiceSignatures);
		}

		sapEntryImpl.setDefaultSAPEntry(defaultSAPEntry);

		if (name == null) {
			sapEntryImpl.setName(StringPool.BLANK);
		}
		else {
			sapEntryImpl.setName(name);
		}

		if (title == null) {
			sapEntryImpl.setTitle(StringPool.BLANK);
		}
		else {
			sapEntryImpl.setTitle(title);
		}

		sapEntryImpl.setCompanyId(companyId);

		sapEntryImpl.resetOriginalValues();

		return sapEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();
		sapEntryId = objectInput.readLong();
		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		allowedServiceSignatures = objectInput.readUTF();
		defaultSAPEntry = objectInput.readBoolean();
		name = objectInput.readUTF();
		title = objectInput.readUTF();
		companyId = objectInput.readLong();
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

		objectOutput.writeLong(sapEntryId);
		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		if (allowedServiceSignatures == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(allowedServiceSignatures);
		}

		objectOutput.writeBoolean(defaultSAPEntry);

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

		objectOutput.writeLong(companyId);
	}

	public String uuid;
	public long sapEntryId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String allowedServiceSignatures;
	public boolean defaultSAPEntry;
	public String name;
	public String title;
	public long companyId;
}