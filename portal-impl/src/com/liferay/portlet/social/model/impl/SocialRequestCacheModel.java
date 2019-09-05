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

package com.liferay.portlet.social.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.social.kernel.model.SocialRequest;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing SocialRequest in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class SocialRequestCacheModel
	implements CacheModel<SocialRequest>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SocialRequestCacheModel)) {
			return false;
		}

		SocialRequestCacheModel socialRequestCacheModel =
			(SocialRequestCacheModel)obj;

		if (requestId == socialRequestCacheModel.requestId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, requestId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(27);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", requestId=");
		sb.append(requestId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", type=");
		sb.append(type);
		sb.append(", extraData=");
		sb.append(extraData);
		sb.append(", receiverUserId=");
		sb.append(receiverUserId);
		sb.append(", status=");
		sb.append(status);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public SocialRequest toEntityModel() {
		SocialRequestImpl socialRequestImpl = new SocialRequestImpl();

		if (uuid == null) {
			socialRequestImpl.setUuid("");
		}
		else {
			socialRequestImpl.setUuid(uuid);
		}

		socialRequestImpl.setRequestId(requestId);
		socialRequestImpl.setGroupId(groupId);
		socialRequestImpl.setCompanyId(companyId);
		socialRequestImpl.setUserId(userId);
		socialRequestImpl.setCreateDate(createDate);
		socialRequestImpl.setModifiedDate(modifiedDate);
		socialRequestImpl.setClassNameId(classNameId);
		socialRequestImpl.setClassPK(classPK);
		socialRequestImpl.setType(type);

		if (extraData == null) {
			socialRequestImpl.setExtraData("");
		}
		else {
			socialRequestImpl.setExtraData(extraData);
		}

		socialRequestImpl.setReceiverUserId(receiverUserId);
		socialRequestImpl.setStatus(status);

		socialRequestImpl.resetOriginalValues();

		return socialRequestImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		requestId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();

		createDate = objectInput.readLong();

		modifiedDate = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();

		type = objectInput.readInt();
		extraData = objectInput.readUTF();

		receiverUserId = objectInput.readLong();

		status = objectInput.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(requestId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		objectOutput.writeLong(createDate);

		objectOutput.writeLong(modifiedDate);

		objectOutput.writeLong(classNameId);

		objectOutput.writeLong(classPK);

		objectOutput.writeInt(type);

		if (extraData == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(extraData);
		}

		objectOutput.writeLong(receiverUserId);

		objectOutput.writeInt(status);
	}

	public String uuid;
	public long requestId;
	public long groupId;
	public long companyId;
	public long userId;
	public long createDate;
	public long modifiedDate;
	public long classNameId;
	public long classPK;
	public int type;
	public String extraData;
	public long receiverUserId;
	public int status;

}