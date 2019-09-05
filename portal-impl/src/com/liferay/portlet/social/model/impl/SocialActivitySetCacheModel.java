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
	implements CacheModel<SocialActivitySet>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SocialActivitySetCacheModel)) {
			return false;
		}

		SocialActivitySetCacheModel socialActivitySetCacheModel =
			(SocialActivitySetCacheModel)obj;

		if (activitySetId == socialActivitySetCacheModel.activitySetId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, activitySetId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(23);

		sb.append("{activitySetId=");
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