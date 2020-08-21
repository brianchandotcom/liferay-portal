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

package com.liferay.commerce.application.model.impl;

import com.liferay.commerce.application.model.CommerceApplicationModel;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CommerceApplicationModel in entity cache.
 *
 * @author Luca Pellizzon
 * @generated
 */
public class CommerceApplicationModelCacheModel
	implements CacheModel<CommerceApplicationModel>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceApplicationModelCacheModel)) {
			return false;
		}

		CommerceApplicationModelCacheModel commerceApplicationModelCacheModel =
			(CommerceApplicationModelCacheModel)object;

		if (commerceApplicationModelId ==
				commerceApplicationModelCacheModel.commerceApplicationModelId) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, commerceApplicationModelId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(19);

		sb.append("{commerceApplicationModelId=");
		sb.append(commerceApplicationModelId);
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
		sb.append(", commerceApplicationBrandId=");
		sb.append(commerceApplicationBrandId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", year=");
		sb.append(year);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceApplicationModel toEntityModel() {
		CommerceApplicationModelImpl commerceApplicationModelImpl =
			new CommerceApplicationModelImpl();

		commerceApplicationModelImpl.setCommerceApplicationModelId(
			commerceApplicationModelId);
		commerceApplicationModelImpl.setCompanyId(companyId);
		commerceApplicationModelImpl.setUserId(userId);

		if (userName == null) {
			commerceApplicationModelImpl.setUserName("");
		}
		else {
			commerceApplicationModelImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceApplicationModelImpl.setCreateDate(null);
		}
		else {
			commerceApplicationModelImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceApplicationModelImpl.setModifiedDate(null);
		}
		else {
			commerceApplicationModelImpl.setModifiedDate(
				new Date(modifiedDate));
		}

		commerceApplicationModelImpl.setCommerceApplicationBrandId(
			commerceApplicationBrandId);

		if (name == null) {
			commerceApplicationModelImpl.setName("");
		}
		else {
			commerceApplicationModelImpl.setName(name);
		}

		if (year == null) {
			commerceApplicationModelImpl.setYear("");
		}
		else {
			commerceApplicationModelImpl.setYear(year);
		}

		commerceApplicationModelImpl.resetOriginalValues();

		return commerceApplicationModelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		commerceApplicationModelId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		commerceApplicationBrandId = objectInput.readLong();
		name = objectInput.readUTF();
		year = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(commerceApplicationModelId);

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

		objectOutput.writeLong(commerceApplicationBrandId);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (year == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(year);
		}
	}

	public long commerceApplicationModelId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long commerceApplicationBrandId;
	public String name;
	public String year;

}