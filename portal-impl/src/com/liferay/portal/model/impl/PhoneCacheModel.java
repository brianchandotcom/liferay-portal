/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.Phone;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing Phone in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see Phone
 * @generated
 */
public class PhoneCacheModel implements CacheModel<Phone>, Externalizable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(29);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", phoneId=");
		sb.append(phoneId);
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
		sb.append(", number=");
		sb.append(number);
		sb.append(", extension=");
		sb.append(extension);
		sb.append(", typeId=");
		sb.append(typeId);
		sb.append(", primary=");
		sb.append(primary);
		sb.append(", mvccVersion=");
		sb.append(mvccVersion);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public Phone toEntityModel() {
		PhoneImpl phoneImpl = new PhoneImpl();

		if (uuid == null) {
			phoneImpl.setUuid(StringPool.BLANK);
		}
		else {
			phoneImpl.setUuid(uuid);
		}

		phoneImpl.setPhoneId(phoneId);
		phoneImpl.setCompanyId(companyId);
		phoneImpl.setUserId(userId);

		if (userName == null) {
			phoneImpl.setUserName(StringPool.BLANK);
		}
		else {
			phoneImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			phoneImpl.setCreateDate(null);
		}
		else {
			phoneImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			phoneImpl.setModifiedDate(null);
		}
		else {
			phoneImpl.setModifiedDate(new Date(modifiedDate));
		}

		phoneImpl.setClassNameId(classNameId);
		phoneImpl.setClassPK(classPK);

		if (number == null) {
			phoneImpl.setNumber(StringPool.BLANK);
		}
		else {
			phoneImpl.setNumber(number);
		}

		if (extension == null) {
			phoneImpl.setExtension(StringPool.BLANK);
		}
		else {
			phoneImpl.setExtension(extension);
		}

		phoneImpl.setTypeId(typeId);
		phoneImpl.setPrimary(primary);
		phoneImpl.setMvccVersion(mvccVersion);

		phoneImpl.resetOriginalValues();

		return phoneImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();
		phoneId = objectInput.readLong();
		companyId = objectInput.readLong();
		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		classNameId = objectInput.readLong();
		classPK = objectInput.readLong();
		number = objectInput.readUTF();
		extension = objectInput.readUTF();
		typeId = objectInput.readInt();
		primary = objectInput.readBoolean();
		mvccVersion = objectInput.readLong();
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

		objectOutput.writeLong(phoneId);
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
		objectOutput.writeLong(classNameId);
		objectOutput.writeLong(classPK);

		if (number == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(number);
		}

		if (extension == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(extension);
		}

		objectOutput.writeInt(typeId);
		objectOutput.writeBoolean(primary);
		objectOutput.writeLong(mvccVersion);
	}

	public String uuid;
	public long phoneId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long classNameId;
	public long classPK;
	public String number;
	public String extension;
	public int typeId;
	public boolean primary;
	public long mvccVersion;
}