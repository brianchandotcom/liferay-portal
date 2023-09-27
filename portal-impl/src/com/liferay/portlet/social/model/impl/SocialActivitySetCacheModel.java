/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.social.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.social.kernel.model.SocialActivitySet;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing SocialActivitySet in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class SocialActivitySetCacheModel
	implements CacheModel<SocialActivitySet>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof SocialActivitySetCacheModel)) {
			return false;
		}

		SocialActivitySetCacheModel socialActivitySetCacheModel =
			(SocialActivitySetCacheModel)object;

		if ((activitySetId == socialActivitySetCacheModel.activitySetId) &&
			(mvccVersion == socialActivitySetCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, activitySetId);

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
		sb.append(", activitySetId=");
		sb.append(activitySetId);
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
		sb.append(", activityCount=");
		sb.append(activityCount);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public SocialActivitySet toEntityModel() {
		SocialActivitySetImpl socialActivitySetImpl =
			new SocialActivitySetImpl();

		socialActivitySetImpl.setMvccVersion(mvccVersion);
		socialActivitySetImpl.setCtCollectionId(ctCollectionId);
		socialActivitySetImpl.setActivitySetId(activitySetId);
		socialActivitySetImpl.setGroupId(groupId);
		socialActivitySetImpl.setCompanyId(companyId);
		socialActivitySetImpl.setUserId(userId);
		socialActivitySetImpl.setCreateDate(createDate);
		socialActivitySetImpl.setModifiedDate(modifiedDate);
		socialActivitySetImpl.setClassNameId(classNameId);
		socialActivitySetImpl.setClassPK(classPK);
		socialActivitySetImpl.setType(type);

		if (extraData == null) {
			socialActivitySetImpl.setExtraData("");
		}
		else {
			socialActivitySetImpl.setExtraData(extraData);
		}

		socialActivitySetImpl.setActivityCount(activityCount);

		socialActivitySetImpl.resetOriginalValues();

		return socialActivitySetImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();

		activitySetId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();

		createDate = objectInput.readLong();

		modifiedDate = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();

		type = objectInput.readInt();
		extraData = objectInput.readUTF();

		activityCount = objectInput.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

		objectOutput.writeLong(activitySetId);

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

		objectOutput.writeInt(activityCount);
	}

	public long mvccVersion;
	public long ctCollectionId;
	public long activitySetId;
	public long groupId;
	public long companyId;
	public long userId;
	public long createDate;
	public long modifiedDate;
	public long classNameId;
	public long classPK;
	public int type;
	public String extraData;
	public int activityCount;

}