/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.term.model.impl;

import com.liferay.commerce.term.model.CommerceTermEntryRel;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CommerceTermEntryRel in entity cache.
 *
 * @author Luca Pellizzon
 * @generated
 */
public class CommerceTermEntryRelCacheModel
	implements CacheModel<CommerceTermEntryRel>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceTermEntryRelCacheModel)) {
			return false;
		}

		CommerceTermEntryRelCacheModel commerceTermEntryRelCacheModel =
			(CommerceTermEntryRelCacheModel)object;

		if ((commerceTermEntryRelId ==
				commerceTermEntryRelCacheModel.commerceTermEntryRelId) &&
			(mvccVersion == commerceTermEntryRelCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, commerceTermEntryRelId);

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
		StringBundler sb = new StringBundler(21);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", commerceTermEntryRelId=");
		sb.append(commerceTermEntryRelId);
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
		sb.append(", commerceTermEntryId=");
		sb.append(commerceTermEntryId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceTermEntryRel toEntityModel() {
		CommerceTermEntryRelImpl commerceTermEntryRelImpl =
			new CommerceTermEntryRelImpl();

		commerceTermEntryRelImpl.setMvccVersion(mvccVersion);
		commerceTermEntryRelImpl.setCommerceTermEntryRelId(
			commerceTermEntryRelId);
		commerceTermEntryRelImpl.setCompanyId(companyId);
		commerceTermEntryRelImpl.setUserId(userId);

		if (userName == null) {
			commerceTermEntryRelImpl.setUserName("");
		}
		else {
			commerceTermEntryRelImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceTermEntryRelImpl.setCreateDate(null);
		}
		else {
			commerceTermEntryRelImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceTermEntryRelImpl.setModifiedDate(null);
		}
		else {
			commerceTermEntryRelImpl.setModifiedDate(new Date(modifiedDate));
		}

		commerceTermEntryRelImpl.setClassNameId(classNameId);
		commerceTermEntryRelImpl.setClassPK(classPK);
		commerceTermEntryRelImpl.setCommerceTermEntryId(commerceTermEntryId);

		commerceTermEntryRelImpl.resetOriginalValues();

		return commerceTermEntryRelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		commerceTermEntryRelId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();

		commerceTermEntryId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(commerceTermEntryRelId);

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

		objectOutput.writeLong(commerceTermEntryId);
	}

	public long mvccVersion;
	public long commerceTermEntryRelId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long classNameId;
	public long classPK;
	public long commerceTermEntryId;

}