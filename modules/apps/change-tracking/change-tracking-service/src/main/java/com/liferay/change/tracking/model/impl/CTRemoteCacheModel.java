/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		StringBundler sb = new StringBundler(23);

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
		sb.append(", clientId=");
		sb.append(clientId);
		sb.append(", clientSecret=");
		sb.append(clientSecret);
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

		if (clientId == null) {
			ctRemoteImpl.setClientId("");
		}
		else {
			ctRemoteImpl.setClientId(clientId);
		}

		if (clientSecret == null) {
			ctRemoteImpl.setClientSecret("");
		}
		else {
			ctRemoteImpl.setClientSecret(clientSecret);
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
		clientId = objectInput.readUTF();
		clientSecret = objectInput.readUTF();
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

		if (clientId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(clientId);
		}

		if (clientSecret == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(clientSecret);
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
	public String clientId;
	public String clientSecret;

}