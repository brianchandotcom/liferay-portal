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

package com.liferay.change.tracking.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.change.tracking.model.CTEntryBag;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;

import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CTEntryBag in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class CTEntryBagCacheModel implements CacheModel<CTEntryBag>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CTEntryBagCacheModel)) {
			return false;
		}

		CTEntryBagCacheModel ctEntryBagCacheModel = (CTEntryBagCacheModel)obj;

		if (ctEntryBagId == ctEntryBagCacheModel.ctEntryBagId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, ctEntryBagId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(17);

		sb.append("{ctEntryBagId=");
		sb.append(ctEntryBagId);
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
		sb.append(", ownerCTEntryId=");
		sb.append(ownerCTEntryId);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CTEntryBag toEntityModel() {
		CTEntryBagImpl ctEntryBagImpl = new CTEntryBagImpl();

		ctEntryBagImpl.setCtEntryBagId(ctEntryBagId);
		ctEntryBagImpl.setCompanyId(companyId);
		ctEntryBagImpl.setUserId(userId);

		if (userName == null) {
			ctEntryBagImpl.setUserName("");
		}
		else {
			ctEntryBagImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			ctEntryBagImpl.setCreateDate(null);
		}
		else {
			ctEntryBagImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			ctEntryBagImpl.setModifiedDate(null);
		}
		else {
			ctEntryBagImpl.setModifiedDate(new Date(modifiedDate));
		}

		ctEntryBagImpl.setOwnerCTEntryId(ownerCTEntryId);
		ctEntryBagImpl.setCtCollectionId(ctCollectionId);

		ctEntryBagImpl.resetOriginalValues();

		return ctEntryBagImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		ctEntryBagId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		ownerCTEntryId = objectInput.readLong();

		ctCollectionId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(ctEntryBagId);

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

		objectOutput.writeLong(ownerCTEntryId);

		objectOutput.writeLong(ctCollectionId);
	}

	public long ctEntryBagId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long ownerCTEntryId;
	public long ctCollectionId;
}