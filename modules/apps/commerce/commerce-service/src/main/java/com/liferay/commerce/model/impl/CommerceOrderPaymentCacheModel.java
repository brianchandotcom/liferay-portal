/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.model.impl;

import com.liferay.commerce.model.CommerceOrderPayment;
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
 * The cache model class for representing CommerceOrderPayment in entity cache.
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CommerceOrderPaymentCacheModel
	implements CacheModel<CommerceOrderPayment>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceOrderPaymentCacheModel)) {
			return false;
		}

		CommerceOrderPaymentCacheModel commerceOrderPaymentCacheModel =
			(CommerceOrderPaymentCacheModel)object;

		if ((commerceOrderPaymentId ==
				commerceOrderPaymentCacheModel.commerceOrderPaymentId) &&
			(mvccVersion == commerceOrderPaymentCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, commerceOrderPaymentId);

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
		StringBundler sb = new StringBundler(25);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", commerceOrderPaymentId=");
		sb.append(commerceOrderPaymentId);
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
		sb.append(", commerceOrderId=");
		sb.append(commerceOrderId);
		sb.append(", commercePaymentMethodKey=");
		sb.append(commercePaymentMethodKey);
		sb.append(", content=");
		sb.append(content);
		sb.append(", status=");
		sb.append(status);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceOrderPayment toEntityModel() {
		CommerceOrderPaymentImpl commerceOrderPaymentImpl =
			new CommerceOrderPaymentImpl();

		commerceOrderPaymentImpl.setMvccVersion(mvccVersion);
		commerceOrderPaymentImpl.setCommerceOrderPaymentId(
			commerceOrderPaymentId);
		commerceOrderPaymentImpl.setGroupId(groupId);
		commerceOrderPaymentImpl.setCompanyId(companyId);
		commerceOrderPaymentImpl.setUserId(userId);

		if (userName == null) {
			commerceOrderPaymentImpl.setUserName("");
		}
		else {
			commerceOrderPaymentImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceOrderPaymentImpl.setCreateDate(null);
		}
		else {
			commerceOrderPaymentImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceOrderPaymentImpl.setModifiedDate(null);
		}
		else {
			commerceOrderPaymentImpl.setModifiedDate(new Date(modifiedDate));
		}

		commerceOrderPaymentImpl.setCommerceOrderId(commerceOrderId);

		if (commercePaymentMethodKey == null) {
			commerceOrderPaymentImpl.setCommercePaymentMethodKey("");
		}
		else {
			commerceOrderPaymentImpl.setCommercePaymentMethodKey(
				commercePaymentMethodKey);
		}

		if (content == null) {
			commerceOrderPaymentImpl.setContent("");
		}
		else {
			commerceOrderPaymentImpl.setContent(content);
		}

		commerceOrderPaymentImpl.setStatus(status);

		commerceOrderPaymentImpl.resetOriginalValues();

		return commerceOrderPaymentImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();

		commerceOrderPaymentId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		commerceOrderId = objectInput.readLong();
		commercePaymentMethodKey = objectInput.readUTF();
		content = (String)objectInput.readObject();

		status = objectInput.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(commerceOrderPaymentId);

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

		objectOutput.writeLong(commerceOrderId);

		if (commercePaymentMethodKey == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(commercePaymentMethodKey);
		}

		if (content == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(content);
		}

		objectOutput.writeInt(status);
	}

	public long mvccVersion;
	public long commerceOrderPaymentId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long commerceOrderId;
	public String commercePaymentMethodKey;
	public String content;
	public int status;

}