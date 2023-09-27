/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.model.impl;

import com.liferay.commerce.model.CommerceShippingMethod;
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
 * The cache model class for representing CommerceShippingMethod in entity cache.
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CommerceShippingMethodCacheModel
	implements CacheModel<CommerceShippingMethod>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceShippingMethodCacheModel)) {
			return false;
		}

		CommerceShippingMethodCacheModel commerceShippingMethodCacheModel =
			(CommerceShippingMethodCacheModel)object;

		if ((commerceShippingMethodId ==
				commerceShippingMethodCacheModel.commerceShippingMethodId) &&
			(mvccVersion == commerceShippingMethodCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, commerceShippingMethodId);

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
		sb.append(", commerceShippingMethodId=");
		sb.append(commerceShippingMethodId);
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
		sb.append(", active=");
		sb.append(active);
		sb.append(", engineKey=");
		sb.append(engineKey);
		sb.append(", imageId=");
		sb.append(imageId);
		sb.append(", priority=");
		sb.append(priority);
		sb.append(", trackingURL=");
		sb.append(trackingURL);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceShippingMethod toEntityModel() {
		CommerceShippingMethodImpl commerceShippingMethodImpl =
			new CommerceShippingMethodImpl();

		commerceShippingMethodImpl.setMvccVersion(mvccVersion);
		commerceShippingMethodImpl.setCommerceShippingMethodId(
			commerceShippingMethodId);
		commerceShippingMethodImpl.setGroupId(groupId);
		commerceShippingMethodImpl.setCompanyId(companyId);
		commerceShippingMethodImpl.setUserId(userId);

		if (userName == null) {
			commerceShippingMethodImpl.setUserName("");
		}
		else {
			commerceShippingMethodImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceShippingMethodImpl.setCreateDate(null);
		}
		else {
			commerceShippingMethodImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceShippingMethodImpl.setModifiedDate(null);
		}
		else {
			commerceShippingMethodImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (name == null) {
			commerceShippingMethodImpl.setName("");
		}
		else {
			commerceShippingMethodImpl.setName(name);
		}

		if (description == null) {
			commerceShippingMethodImpl.setDescription("");
		}
		else {
			commerceShippingMethodImpl.setDescription(description);
		}

		commerceShippingMethodImpl.setActive(active);

		if (engineKey == null) {
			commerceShippingMethodImpl.setEngineKey("");
		}
		else {
			commerceShippingMethodImpl.setEngineKey(engineKey);
		}

		commerceShippingMethodImpl.setImageId(imageId);
		commerceShippingMethodImpl.setPriority(priority);

		if (trackingURL == null) {
			commerceShippingMethodImpl.setTrackingURL("");
		}
		else {
			commerceShippingMethodImpl.setTrackingURL(trackingURL);
		}

		commerceShippingMethodImpl.resetOriginalValues();

		return commerceShippingMethodImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		commerceShippingMethodId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		name = objectInput.readUTF();
		description = objectInput.readUTF();

		active = objectInput.readBoolean();
		engineKey = objectInput.readUTF();

		imageId = objectInput.readLong();

		priority = objectInput.readDouble();
		trackingURL = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(commerceShippingMethodId);

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

		objectOutput.writeBoolean(active);

		if (engineKey == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(engineKey);
		}

		objectOutput.writeLong(imageId);

		objectOutput.writeDouble(priority);

		if (trackingURL == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(trackingURL);
		}
	}

	public long mvccVersion;
	public long commerceShippingMethodId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String name;
	public String description;
	public boolean active;
	public String engineKey;
	public long imageId;
	public double priority;
	public String trackingURL;

}