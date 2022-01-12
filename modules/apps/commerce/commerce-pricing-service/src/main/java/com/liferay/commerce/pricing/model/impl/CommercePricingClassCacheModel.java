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

package com.liferay.commerce.pricing.model.impl;

import com.liferay.commerce.pricing.model.CommercePricingClass;
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
 * The cache model class for representing CommercePricingClass in entity cache.
 *
 * @author Riccardo Alberti
 * @generated
 */
public class CommercePricingClassCacheModel
	implements CacheModel<CommercePricingClass>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommercePricingClassCacheModel)) {
			return false;
		}

		CommercePricingClassCacheModel commercePricingClassCacheModel =
			(CommercePricingClassCacheModel)object;

		if ((commercePricingClassId ==
				commercePricingClassCacheModel.commercePricingClassId) &&
			(mvccVersion == commercePricingClassCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, commercePricingClassId);

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
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", externalReferenceCode=");
		sb.append(externalReferenceCode);
		sb.append(", commercePricingClassId=");
		sb.append(commercePricingClassId);
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
		sb.append(", title=");
		sb.append(title);
		sb.append(", description=");
		sb.append(description);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommercePricingClass toEntityModel() {
		CommercePricingClassImpl commercePricingClassImpl =
			new CommercePricingClassImpl();

		commercePricingClassImpl.setMvccVersion(mvccVersion);
		commercePricingClassImpl.setCtCollectionId(ctCollectionId);

		if (uuid == null) {
			commercePricingClassImpl.setUuid("");
		}
		else {
			commercePricingClassImpl.setUuid(uuid);
		}

		if (externalReferenceCode == null) {
			commercePricingClassImpl.setExternalReferenceCode("");
		}
		else {
			commercePricingClassImpl.setExternalReferenceCode(
				externalReferenceCode);
		}

		commercePricingClassImpl.setCommercePricingClassId(
			commercePricingClassId);
		commercePricingClassImpl.setCompanyId(companyId);
		commercePricingClassImpl.setUserId(userId);

		if (userName == null) {
			commercePricingClassImpl.setUserName("");
		}
		else {
			commercePricingClassImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commercePricingClassImpl.setCreateDate(null);
		}
		else {
			commercePricingClassImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commercePricingClassImpl.setModifiedDate(null);
		}
		else {
			commercePricingClassImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (title == null) {
			commercePricingClassImpl.setTitle("");
		}
		else {
			commercePricingClassImpl.setTitle(title);
		}

		if (description == null) {
			commercePricingClassImpl.setDescription("");
		}
		else {
			commercePricingClassImpl.setDescription(description);
		}

		if (lastPublishDate == Long.MIN_VALUE) {
			commercePricingClassImpl.setLastPublishDate(null);
		}
		else {
			commercePricingClassImpl.setLastPublishDate(
				new Date(lastPublishDate));
		}

		commercePricingClassImpl.resetOriginalValues();

		return commercePricingClassImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();
		uuid = objectInput.readUTF();
		externalReferenceCode = objectInput.readUTF();

		commercePricingClassId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		title = objectInput.readUTF();
		description = objectInput.readUTF();
		lastPublishDate = objectInput.readLong();
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

		if (externalReferenceCode == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(externalReferenceCode);
		}

		objectOutput.writeLong(commercePricingClassId);

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

		if (title == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(title);
		}

		if (description == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(description);
		}

		objectOutput.writeLong(lastPublishDate);
	}

	public long mvccVersion;
	public long ctCollectionId;
	public String uuid;
	public String externalReferenceCode;
	public long commercePricingClassId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String title;
	public String description;
	public long lastPublishDate;

}