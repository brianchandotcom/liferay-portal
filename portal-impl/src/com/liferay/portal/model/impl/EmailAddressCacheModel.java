/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.EmailAddress;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing EmailAddress in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class EmailAddressCacheModel
	implements CacheModel<EmailAddress>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof EmailAddressCacheModel)) {
			return false;
		}

		EmailAddressCacheModel emailAddressCacheModel =
			(EmailAddressCacheModel)object;

		if ((emailAddressId == emailAddressCacheModel.emailAddressId) &&
			(mvccVersion == emailAddressCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, emailAddressId);

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
		StringBundler sb = new StringBundler(29);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", emailAddressId=");
		sb.append(emailAddressId);
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
		sb.append(", address=");
		sb.append(address);
		sb.append(", listTypeId=");
		sb.append(listTypeId);
		sb.append(", primary=");
		sb.append(primary);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public EmailAddress toEntityModel() {
		EmailAddressImpl emailAddressImpl = new EmailAddressImpl();

		emailAddressImpl.setMvccVersion(mvccVersion);
		emailAddressImpl.setCtCollectionId(ctCollectionId);

		if (uuid == null) {
			emailAddressImpl.setUuid("");
		}
		else {
			emailAddressImpl.setUuid(uuid);
		}

		emailAddressImpl.setEmailAddressId(emailAddressId);
		emailAddressImpl.setCompanyId(companyId);
		emailAddressImpl.setUserId(userId);

		if (userName == null) {
			emailAddressImpl.setUserName("");
		}
		else {
			emailAddressImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			emailAddressImpl.setCreateDate(null);
		}
		else {
			emailAddressImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			emailAddressImpl.setModifiedDate(null);
		}
		else {
			emailAddressImpl.setModifiedDate(new Date(modifiedDate));
		}

		emailAddressImpl.setClassNameId(classNameId);
		emailAddressImpl.setClassPK(classPK);

		if (address == null) {
			emailAddressImpl.setAddress("");
		}
		else {
			emailAddressImpl.setAddress(address);
		}

		emailAddressImpl.setListTypeId(listTypeId);
		emailAddressImpl.setPrimary(primary);

		emailAddressImpl.resetOriginalValues();

		return emailAddressImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();
		uuid = objectInput.readUTF();

		emailAddressId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();
		address = objectInput.readUTF();

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

		objectOutput.writeLong(emailAddressId);

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

		if (address == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(address);
		}

		objectOutput.writeLong(listTypeId);

		objectOutput.writeBoolean(primary);
	}

	public long mvccVersion;
	public long ctCollectionId;
	public String uuid;
	public long emailAddressId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long classNameId;
	public long classPK;
	public String address;
	public long listTypeId;
	public boolean primary;

}