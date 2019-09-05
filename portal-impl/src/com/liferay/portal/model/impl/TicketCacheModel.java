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

package com.liferay.portal.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.kernel.model.Ticket;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing Ticket in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class TicketCacheModel
	implements CacheModel<Ticket>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof TicketCacheModel)) {
			return false;
		}

		TicketCacheModel ticketCacheModel = (TicketCacheModel)obj;

		if ((ticketId == ticketCacheModel.ticketId) &&
			(mvccVersion == ticketCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, ticketId);

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
		StringBundler sb = new StringBundler(21);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ticketId=");
		sb.append(ticketId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", key=");
		sb.append(key);
		sb.append(", type=");
		sb.append(type);
		sb.append(", extraInfo=");
		sb.append(extraInfo);
		sb.append(", expirationDate=");
		sb.append(expirationDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public Ticket toEntityModel() {
		TicketImpl ticketImpl = new TicketImpl();

		ticketImpl.setMvccVersion(mvccVersion);
		ticketImpl.setTicketId(ticketId);
		ticketImpl.setCompanyId(companyId);

		if (createDate == Long.MIN_VALUE) {
			ticketImpl.setCreateDate(null);
		}
		else {
			ticketImpl.setCreateDate(new Date(createDate));
		}

		ticketImpl.setClassNameId(classNameId);
		ticketImpl.setClassPK(classPK);

		if (key == null) {
			ticketImpl.setKey("");
		}
		else {
			ticketImpl.setKey(key);
		}

		ticketImpl.setType(type);

		if (extraInfo == null) {
			ticketImpl.setExtraInfo("");
		}
		else {
			ticketImpl.setExtraInfo(extraInfo);
		}

		if (expirationDate == Long.MIN_VALUE) {
			ticketImpl.setExpirationDate(null);
		}
		else {
			ticketImpl.setExpirationDate(new Date(expirationDate));
		}

		ticketImpl.resetOriginalValues();

		return ticketImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ticketId = objectInput.readLong();

		companyId = objectInput.readLong();
		createDate = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();
		key = objectInput.readUTF();

		type = objectInput.readInt();
		extraInfo = objectInput.readUTF();
		expirationDate = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ticketId);

		objectOutput.writeLong(companyId);
		objectOutput.writeLong(createDate);

		objectOutput.writeLong(classNameId);

		objectOutput.writeLong(classPK);

		if (key == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(key);
		}

		objectOutput.writeInt(type);

		if (extraInfo == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(extraInfo);
		}

		objectOutput.writeLong(expirationDate);
	}

	public long mvccVersion;
	public long ticketId;
	public long companyId;
	public long createDate;
	public long classNameId;
	public long classPK;
	public String key;
	public int type;
	public String extraInfo;
	public long expirationDate;

}