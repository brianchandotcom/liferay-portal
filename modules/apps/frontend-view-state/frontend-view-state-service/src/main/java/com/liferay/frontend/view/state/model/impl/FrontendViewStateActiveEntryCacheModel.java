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

package com.liferay.frontend.view.state.model.impl;

import com.liferay.frontend.view.state.model.FrontendViewStateActiveEntry;
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
 * The cache model class for representing FrontendViewStateActiveEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class FrontendViewStateActiveEntryCacheModel
	implements CacheModel<FrontendViewStateActiveEntry>, Externalizable,
			   MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof FrontendViewStateActiveEntryCacheModel)) {
			return false;
		}

		FrontendViewStateActiveEntryCacheModel
			frontendViewStateActiveEntryCacheModel =
				(FrontendViewStateActiveEntryCacheModel)object;

		if ((frontendViewStateActiveEntryId ==
				frontendViewStateActiveEntryCacheModel.
					frontendViewStateActiveEntryId) &&
			(mvccVersion ==
				frontendViewStateActiveEntryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, frontendViewStateActiveEntryId);

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
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", frontendViewStateActiveEntryId=");
		sb.append(frontendViewStateActiveEntryId);
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
		sb.append(", datasetDisplayId=");
		sb.append(datasetDisplayId);
		sb.append(", frontendViewStateEntryId=");
		sb.append(frontendViewStateEntryId);
		sb.append(", plid=");
		sb.append(plid);
		sb.append(", portletId=");
		sb.append(portletId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public FrontendViewStateActiveEntry toEntityModel() {
		FrontendViewStateActiveEntryImpl frontendViewStateActiveEntryImpl =
			new FrontendViewStateActiveEntryImpl();

		frontendViewStateActiveEntryImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			frontendViewStateActiveEntryImpl.setUuid("");
		}
		else {
			frontendViewStateActiveEntryImpl.setUuid(uuid);
		}

		frontendViewStateActiveEntryImpl.setFrontendViewStateActiveEntryId(
			frontendViewStateActiveEntryId);
		frontendViewStateActiveEntryImpl.setCompanyId(companyId);
		frontendViewStateActiveEntryImpl.setUserId(userId);

		if (userName == null) {
			frontendViewStateActiveEntryImpl.setUserName("");
		}
		else {
			frontendViewStateActiveEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			frontendViewStateActiveEntryImpl.setCreateDate(null);
		}
		else {
			frontendViewStateActiveEntryImpl.setCreateDate(
				new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			frontendViewStateActiveEntryImpl.setModifiedDate(null);
		}
		else {
			frontendViewStateActiveEntryImpl.setModifiedDate(
				new Date(modifiedDate));
		}

		if (datasetDisplayId == null) {
			frontendViewStateActiveEntryImpl.setDatasetDisplayId("");
		}
		else {
			frontendViewStateActiveEntryImpl.setDatasetDisplayId(
				datasetDisplayId);
		}

		frontendViewStateActiveEntryImpl.setFrontendViewStateEntryId(
			frontendViewStateEntryId);
		frontendViewStateActiveEntryImpl.setPlid(plid);

		if (portletId == null) {
			frontendViewStateActiveEntryImpl.setPortletId("");
		}
		else {
			frontendViewStateActiveEntryImpl.setPortletId(portletId);
		}

		frontendViewStateActiveEntryImpl.resetOriginalValues();

		return frontendViewStateActiveEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		frontendViewStateActiveEntryId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		datasetDisplayId = objectInput.readUTF();

		frontendViewStateEntryId = objectInput.readLong();

		plid = objectInput.readLong();
		portletId = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(frontendViewStateActiveEntryId);

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

		if (datasetDisplayId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(datasetDisplayId);
		}

		objectOutput.writeLong(frontendViewStateEntryId);

		objectOutput.writeLong(plid);

		if (portletId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(portletId);
		}
	}

	public long mvccVersion;
	public String uuid;
	public long frontendViewStateActiveEntryId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String datasetDisplayId;
	public long frontendViewStateEntryId;
	public long plid;
	public String portletId;

}