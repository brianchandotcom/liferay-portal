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

import com.liferay.commerce.application.model.CommerceApplicationModelCProductRel;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CommerceApplicationModelCProductRel in entity cache.
 *
 * @author Luca Pellizzon
 * @generated
 */
public class CommerceApplicationModelCProductRelCacheModel
	implements CacheModel<CommerceApplicationModelCProductRel>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof
				CommerceApplicationModelCProductRelCacheModel)) {

			return false;
		}

		CommerceApplicationModelCProductRelCacheModel
			commerceApplicationModelCProductRelCacheModel =
				(CommerceApplicationModelCProductRelCacheModel)object;

		if (commerceApplicationModelCProductRelId ==
				commerceApplicationModelCProductRelCacheModel.
					commerceApplicationModelCProductRelId) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, commerceApplicationModelCProductRelId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(17);

		sb.append("{commerceApplicationModelCProductRelId=");
		sb.append(commerceApplicationModelCProductRelId);
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
		sb.append(", commerceApplicationModelId=");
		sb.append(commerceApplicationModelId);
		sb.append(", CProductId=");
		sb.append(CProductId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceApplicationModelCProductRel toEntityModel() {
		CommerceApplicationModelCProductRelImpl
			commerceApplicationModelCProductRelImpl =
				new CommerceApplicationModelCProductRelImpl();

		commerceApplicationModelCProductRelImpl.
			setCommerceApplicationModelCProductRelId(
				commerceApplicationModelCProductRelId);
		commerceApplicationModelCProductRelImpl.setCompanyId(companyId);
		commerceApplicationModelCProductRelImpl.setUserId(userId);

		if (userName == null) {
			commerceApplicationModelCProductRelImpl.setUserName("");
		}
		else {
			commerceApplicationModelCProductRelImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceApplicationModelCProductRelImpl.setCreateDate(null);
		}
		else {
			commerceApplicationModelCProductRelImpl.setCreateDate(
				new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceApplicationModelCProductRelImpl.setModifiedDate(null);
		}
		else {
			commerceApplicationModelCProductRelImpl.setModifiedDate(
				new Date(modifiedDate));
		}

		commerceApplicationModelCProductRelImpl.setCommerceApplicationModelId(
			commerceApplicationModelId);
		commerceApplicationModelCProductRelImpl.setCProductId(CProductId);

		commerceApplicationModelCProductRelImpl.resetOriginalValues();

		return commerceApplicationModelCProductRelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		commerceApplicationModelCProductRelId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		commerceApplicationModelId = objectInput.readLong();

		CProductId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(commerceApplicationModelCProductRelId);

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

		objectOutput.writeLong(commerceApplicationModelId);

		objectOutput.writeLong(CProductId);
	}

	public long commerceApplicationModelCProductRelId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long commerceApplicationModelId;
	public long CProductId;

}