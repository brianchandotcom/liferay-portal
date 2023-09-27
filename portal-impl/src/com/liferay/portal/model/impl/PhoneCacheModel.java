/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.kernel.model.Phone;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing Phone in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class PhoneCacheModel
	implements CacheModel<Phone>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof PhoneCacheModel)) {
			return false;
		}

		PhoneCacheModel phoneCacheModel = (PhoneCacheModel)object;

		if ((phoneId == phoneCacheModel.phoneId) &&
			(mvccVersion == phoneCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, phoneId);

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
		StringBundler sb = new StringBundler(31);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", uuid=");
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
		sb.append(", listTypeId=");
		sb.append(listTypeId);
		sb.append(", primary=");
		sb.append(primary);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public Phone toEntityModel() {
		PhoneImpl phoneImpl = new PhoneImpl();

		phoneImpl.setMvccVersion(mvccVersion);
		phoneImpl.setCtCollectionId(ctCollectionId);

		if (uuid == null) {
			phoneImpl.setUuid("");
		}
		else {
			phoneImpl.setUuid(uuid);
		}

		phoneImpl.setPhoneId(phoneId);
		phoneImpl.setCompanyId(companyId);
		phoneImpl.setUserId(userId);

		if (userName == null) {
			phoneImpl.setUserName("");
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
			phoneImpl.setNumber("");
		}
		else {
			phoneImpl.setNumber(number);
		}

		if (extension == null) {
			phoneImpl.setExtension("");
		}
		else {
			phoneImpl.setExtension(extension);
		}

		phoneImpl.setListTypeId(listTypeId);
		phoneImpl.setPrimary(primary);

		phoneImpl.resetOriginalValues();

		return phoneImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();
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

		listTypeId = objectInput.readLong();

		primary = objectInput.readBoolean();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(phoneId);

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

		if (number == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(number);
		}

		if (extension == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(extension);
		}

		objectOutput.writeLong(listTypeId);

		objectOutput.writeBoolean(primary);
	}

	public long mvccVersion;
	public long ctCollectionId;
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
	public long listTypeId;
	public boolean primary;

}