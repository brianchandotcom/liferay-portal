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

package com.liferay.commerce.tax.model.impl;

import com.liferay.commerce.tax.model.CommerceTaxMethod;
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
 * The cache model class for representing CommerceTaxMethod in entity cache.
 *
 * @author Marco Leo
 * @generated
 */
public class CommerceTaxMethodCacheModel
	implements CacheModel<CommerceTaxMethod>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceTaxMethodCacheModel)) {
			return false;
		}

		CommerceTaxMethodCacheModel commerceTaxMethodCacheModel =
			(CommerceTaxMethodCacheModel)object;

		if ((commerceTaxMethodId ==
				commerceTaxMethodCacheModel.commerceTaxMethodId) &&
			(mvccVersion == commerceTaxMethodCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, commerceTaxMethodId);

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
		StringBundler sb = new StringBundler(27);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", commerceTaxMethodId=");
		sb.append(commerceTaxMethodId);
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
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append(", engineKey=");
		sb.append(engineKey);
		sb.append(", percentage=");
		sb.append(percentage);
		sb.append(", active=");
		sb.append(active);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceTaxMethod toEntityModel() {
		CommerceTaxMethodImpl commerceTaxMethodImpl =
			new CommerceTaxMethodImpl();

		commerceTaxMethodImpl.setMvccVersion(mvccVersion);
		commerceTaxMethodImpl.setCommerceTaxMethodId(commerceTaxMethodId);
		commerceTaxMethodImpl.setGroupId(groupId);
		commerceTaxMethodImpl.setCompanyId(companyId);
		commerceTaxMethodImpl.setUserId(userId);

		if (userName == null) {
			commerceTaxMethodImpl.setUserName("");
		}
		else {
			commerceTaxMethodImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceTaxMethodImpl.setCreateDate(null);
		}
		else {
			commerceTaxMethodImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceTaxMethodImpl.setModifiedDate(null);
		}
		else {
			commerceTaxMethodImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (name == null) {
			commerceTaxMethodImpl.setName("");
		}
		else {
			commerceTaxMethodImpl.setName(name);
		}

		if (description == null) {
			commerceTaxMethodImpl.setDescription("");
		}
		else {
			commerceTaxMethodImpl.setDescription(description);
		}

		if (engineKey == null) {
			commerceTaxMethodImpl.setEngineKey("");
		}
		else {
			commerceTaxMethodImpl.setEngineKey(engineKey);
		}

		commerceTaxMethodImpl.setPercentage(percentage);
		commerceTaxMethodImpl.setActive(active);

		commerceTaxMethodImpl.resetOriginalValues();

		return commerceTaxMethodImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		commerceTaxMethodId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		name = objectInput.readUTF();
		description = objectInput.readUTF();
		engineKey = objectInput.readUTF();

		percentage = objectInput.readBoolean();

		active = objectInput.readBoolean();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(commerceTaxMethodId);

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

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (description == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(description);
		}

		if (engineKey == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(engineKey);
		}

		objectOutput.writeBoolean(percentage);

		objectOutput.writeBoolean(active);
	}

	public long mvccVersion;
	public long commerceTaxMethodId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String name;
	public String description;
	public String engineKey;
	public boolean percentage;
	public boolean active;

}