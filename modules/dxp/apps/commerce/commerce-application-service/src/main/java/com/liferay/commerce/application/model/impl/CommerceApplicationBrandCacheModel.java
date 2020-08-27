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

import com.liferay.commerce.application.model.CommerceApplicationBrand;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CommerceApplicationBrand in entity cache.
 *
 * @author Luca Pellizzon
 * @generated
 */
public class CommerceApplicationBrandCacheModel
	implements CacheModel<CommerceApplicationBrand>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceApplicationBrandCacheModel)) {
			return false;
		}

		CommerceApplicationBrandCacheModel commerceApplicationBrandCacheModel =
			(CommerceApplicationBrandCacheModel)object;

		if (commerceApplicationBrandId ==
				commerceApplicationBrandCacheModel.commerceApplicationBrandId) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, commerceApplicationBrandId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(17);

		sb.append("{commerceApplicationBrandId=");
		sb.append(commerceApplicationBrandId);
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
		sb.append(", logoId=");
		sb.append(logoId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceApplicationBrand toEntityModel() {
		CommerceApplicationBrandImpl commerceApplicationBrandImpl =
			new CommerceApplicationBrandImpl();

		commerceApplicationBrandImpl.setCommerceApplicationBrandId(
			commerceApplicationBrandId);
		commerceApplicationBrandImpl.setCompanyId(companyId);
		commerceApplicationBrandImpl.setUserId(userId);

		if (userName == null) {
			commerceApplicationBrandImpl.setUserName("");
		}
		else {
			commerceApplicationBrandImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceApplicationBrandImpl.setCreateDate(null);
		}
		else {
			commerceApplicationBrandImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceApplicationBrandImpl.setModifiedDate(null);
		}
		else {
			commerceApplicationBrandImpl.setModifiedDate(
				new Date(modifiedDate));
		}

		if (name == null) {
			commerceApplicationBrandImpl.setName("");
		}
		else {
			commerceApplicationBrandImpl.setName(name);
		}

		commerceApplicationBrandImpl.setLogoId(logoId);

		commerceApplicationBrandImpl.resetOriginalValues();

		return commerceApplicationBrandImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		commerceApplicationBrandId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		name = objectInput.readUTF();

		logoId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(commerceApplicationBrandId);

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

		objectOutput.writeLong(logoId);
	}

	public long commerceApplicationBrandId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String name;
	public long logoId;

}