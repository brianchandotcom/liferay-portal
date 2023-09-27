/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.social.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.social.kernel.model.SocialActivity;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing SocialActivity in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class SocialActivityCacheModel
	implements CacheModel<SocialActivity>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof SocialActivityCacheModel)) {
			return false;
		}

		SocialActivityCacheModel socialActivityCacheModel =
			(SocialActivityCacheModel)object;

		if ((activityId == socialActivityCacheModel.activityId) &&
			(mvccVersion == socialActivityCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, activityId);

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
		StringBundler sb = new StringBundler(33);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", activityId=");
		sb.append(activityId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", activitySetId=");
		sb.append(activitySetId);
		sb.append(", mirrorActivityId=");
		sb.append(mirrorActivityId);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", parentClassNameId=");
		sb.append(parentClassNameId);
		sb.append(", parentClassPK=");
		sb.append(parentClassPK);
		sb.append(", type=");
		sb.append(type);
		sb.append(", extraData=");
		sb.append(extraData);
		sb.append(", receiverUserId=");
		sb.append(receiverUserId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public SocialActivity toEntityModel() {
		SocialActivityImpl socialActivityImpl = new SocialActivityImpl();

		socialActivityImpl.setMvccVersion(mvccVersion);
		socialActivityImpl.setCtCollectionId(ctCollectionId);
		socialActivityImpl.setActivityId(activityId);
		socialActivityImpl.setGroupId(groupId);
		socialActivityImpl.setCompanyId(companyId);
		socialActivityImpl.setUserId(userId);
		socialActivityImpl.setCreateDate(createDate);
		socialActivityImpl.setActivitySetId(activitySetId);
		socialActivityImpl.setMirrorActivityId(mirrorActivityId);
		socialActivityImpl.setClassNameId(classNameId);
		socialActivityImpl.setClassPK(classPK);
		socialActivityImpl.setParentClassNameId(parentClassNameId);
		socialActivityImpl.setParentClassPK(parentClassPK);
		socialActivityImpl.setType(type);

		if (extraData == null) {
			socialActivityImpl.setExtraData("");
		}
		else {
			socialActivityImpl.setExtraData(extraData);
		}

		socialActivityImpl.setReceiverUserId(receiverUserId);

		socialActivityImpl.resetOriginalValues();

		return socialActivityImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();

		activityId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();

		createDate = objectInput.readLong();

		activitySetId = objectInput.readLong();

		mirrorActivityId = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();

		parentClassNameId = objectInput.readLong();

		parentClassPK = objectInput.readLong();

		type = objectInput.readInt();
		extraData = objectInput.readUTF();

		receiverUserId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

		objectOutput.writeLong(activityId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		objectOutput.writeLong(createDate);

		objectOutput.writeLong(activitySetId);

		objectOutput.writeLong(mirrorActivityId);

		objectOutput.writeLong(classNameId);

		objectOutput.writeLong(classPK);

		objectOutput.writeLong(parentClassNameId);

		objectOutput.writeLong(parentClassPK);

		objectOutput.writeInt(type);

		if (extraData == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(extraData);
		}

		objectOutput.writeLong(receiverUserId);
	}

	public long mvccVersion;
	public long ctCollectionId;
	public long activityId;
	public long groupId;
	public long companyId;
	public long userId;
	public long createDate;
	public long activitySetId;
	public long mirrorActivityId;
	public long classNameId;
	public long classPK;
	public long parentClassNameId;
	public long parentClassPK;
	public int type;
	public String extraData;
	public long receiverUserId;

}