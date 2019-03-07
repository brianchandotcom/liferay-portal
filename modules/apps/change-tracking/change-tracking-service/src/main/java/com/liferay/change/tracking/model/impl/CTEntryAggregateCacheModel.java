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

import com.liferay.change.tracking.model.CTEntryAggregate;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CTEntryAggregate in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class CTEntryAggregateCacheModel
	implements CacheModel<CTEntryAggregate>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CTEntryAggregateCacheModel)) {
			return false;
		}

		CTEntryAggregateCacheModel ctEntryAggregateCacheModel =
			(CTEntryAggregateCacheModel)obj;

		if (ctEntryAggregateId ==
				ctEntryAggregateCacheModel.ctEntryAggregateId) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, ctEntryAggregateId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(15);

		sb.append("{ctEntryAggregateId=");
		sb.append(ctEntryAggregateId);
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
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CTEntryAggregate toEntityModel() {
		CTEntryAggregateImpl ctEntryAggregateImpl = new CTEntryAggregateImpl();

		ctEntryAggregateImpl.setCtEntryAggregateId(ctEntryAggregateId);
		ctEntryAggregateImpl.setCompanyId(companyId);
		ctEntryAggregateImpl.setUserId(userId);

		if (userName == null) {
			ctEntryAggregateImpl.setUserName("");
		}
		else {
			ctEntryAggregateImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			ctEntryAggregateImpl.setCreateDate(null);
		}
		else {
			ctEntryAggregateImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			ctEntryAggregateImpl.setModifiedDate(null);
		}
		else {
			ctEntryAggregateImpl.setModifiedDate(new Date(modifiedDate));
		}

		ctEntryAggregateImpl.setOwnerCTEntryId(ownerCTEntryId);

		ctEntryAggregateImpl.resetOriginalValues();

		return ctEntryAggregateImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		ctEntryAggregateId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		ownerCTEntryId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(ctEntryAggregateId);

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
	}

	public long ctEntryAggregateId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long ownerCTEntryId;

}