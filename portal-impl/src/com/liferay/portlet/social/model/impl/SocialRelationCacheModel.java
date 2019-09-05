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
import com.liferay.social.kernel.model.SocialRelation;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing SocialRelation in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class SocialRelationCacheModel
	implements CacheModel<SocialRelation>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SocialRelationCacheModel)) {
			return false;
		}

		SocialRelationCacheModel socialRelationCacheModel =
			(SocialRelationCacheModel)obj;

		if (relationId == socialRelationCacheModel.relationId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, relationId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(15);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", relationId=");
		sb.append(relationId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", userId1=");
		sb.append(userId1);
		sb.append(", userId2=");
		sb.append(userId2);
		sb.append(", type=");
		sb.append(type);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public SocialRelation toEntityModel() {
		SocialRelationImpl socialRelationImpl = new SocialRelationImpl();

		if (uuid == null) {
			socialRelationImpl.setUuid("");
		}
		else {
			socialRelationImpl.setUuid(uuid);
		}

		socialRelationImpl.setRelationId(relationId);
		socialRelationImpl.setCompanyId(companyId);
		socialRelationImpl.setCreateDate(createDate);
		socialRelationImpl.setUserId1(userId1);
		socialRelationImpl.setUserId2(userId2);
		socialRelationImpl.setType(type);

		socialRelationImpl.resetOriginalValues();

		return socialRelationImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		relationId = objectInput.readLong();

		companyId = objectInput.readLong();

		createDate = objectInput.readLong();

		userId1 = objectInput.readLong();

		userId2 = objectInput.readLong();

		type = objectInput.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(relationId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(createDate);

		objectOutput.writeLong(userId1);

		objectOutput.writeLong(userId2);

		objectOutput.writeInt(type);
	}

	public String uuid;
	public long relationId;
	public long companyId;
	public long createDate;
	public long userId1;
	public long userId2;
	public int type;

}