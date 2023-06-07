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

import com.liferay.change.tracking.model.CTRemote;
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
 * The cache model class for representing CTRemote in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class CTRemoteCacheModel
	implements CacheModel<CTRemote>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CTRemoteCacheModel)) {
			return false;
		}

		CTRemoteCacheModel ctRemoteCacheModel = (CTRemoteCacheModel)object;

		if ((ctRemoteId == ctRemoteCacheModel.ctRemoteId) &&
			(mvccVersion == ctRemoteCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, ctRemoteId);

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
		StringBundler sb = new StringBundler(19);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctRemoteId=");
		sb.append(ctRemoteId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append(", url=");
		sb.append(url);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CTRemote toEntityModel() {
		CTRemoteImpl ctRemoteImpl = new CTRemoteImpl();

		ctRemoteImpl.setMvccVersion(mvccVersion);
		ctRemoteImpl.setCtRemoteId(ctRemoteId);
		ctRemoteImpl.setCompanyId(companyId);
		ctRemoteImpl.setUserId(userId);

		if (createDate == Long.MIN_VALUE) {
			ctRemoteImpl.setCreateDate(null);
		}
		else {
			ctRemoteImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			ctRemoteImpl.setModifiedDate(null);
		}
		else {
			ctRemoteImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (name == null) {
			ctRemoteImpl.setName("");
		}
		else {
			ctRemoteImpl.setName(name);
		}

		if (description == null) {
			ctRemoteImpl.setDescription("");
		}
		else {
			ctRemoteImpl.setDescription(description);
		}

		if (url == null) {
			ctRemoteImpl.setUrl("");
		}
		else {
			ctRemoteImpl.setUrl(url);
		}

		ctRemoteImpl.resetOriginalValues();

		return ctRemoteImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctRemoteId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		name = objectInput.readUTF();
		description = objectInput.readUTF();
		url = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctRemoteId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);
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

		if (url == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(url);
		}
	}

	public long mvccVersion;
	public long ctRemoteId;
	public long companyId;
	public long userId;
	public long createDate;
	public long modifiedDate;
	public String name;
	public String description;
	public String url;

}