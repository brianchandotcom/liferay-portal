/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.model.CommerceTaxMethod;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CommerceTaxMethod in entity cache.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceTaxMethod
 * @generated
 */
@ProviderType
public class CommerceTaxMethodCacheModel implements CacheModel<CommerceTaxMethod>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceTaxMethodCacheModel)) {
			return false;
		}

		CommerceTaxMethodCacheModel commerceTaxMethodCacheModel = (CommerceTaxMethodCacheModel)obj;

		if (commerceTaxMethodId == commerceTaxMethodCacheModel.commerceTaxMethodId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, commerceTaxMethodId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(25);

		sb.append("{commerceTaxMethodId=");
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
		CommerceTaxMethodImpl commerceTaxMethodImpl = new CommerceTaxMethodImpl();

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
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
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