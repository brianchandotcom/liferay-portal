/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.social.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
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
	implements CacheModel<SocialRelation>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof SocialRelationCacheModel)) {
			return false;
		}

		SocialRelationCacheModel socialRelationCacheModel =
			(SocialRelationCacheModel)object;

		if ((relationId == socialRelationCacheModel.relationId) &&
			(mvccVersion == socialRelationCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, relationId);

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
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", uuid=");
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

		socialRelationImpl.setMvccVersion(mvccVersion);
		socialRelationImpl.setCtCollectionId(ctCollectionId);

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
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();
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
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

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

	public long mvccVersion;
	public long ctCollectionId;
	public String uuid;
	public long relationId;
	public long companyId;
	public long createDate;
	public long userId1;
	public long userId2;
	public int type;

}