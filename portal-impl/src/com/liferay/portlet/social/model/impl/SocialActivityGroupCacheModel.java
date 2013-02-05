/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.CacheModel;

import com.liferay.portlet.social.model.SocialActivityGroup;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing SocialActivityGroup in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see SocialActivityGroup
 * @generated
 */
public class SocialActivityGroupCacheModel implements CacheModel<SocialActivityGroup>,
	Externalizable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(19);

		sb.append("{activityGroupId=");
		sb.append(activityGroupId);
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
		sb.append("}");

		return sb.toString();
	}

	public SocialActivityGroup toEntityModel() {
		SocialActivityGroupImpl socialActivityGroupImpl = new SocialActivityGroupImpl();

		socialActivityGroupImpl.setActivityGroupId(activityGroupId);
		socialActivityGroupImpl.setGroupId(groupId);
		socialActivityGroupImpl.setCompanyId(companyId);
		socialActivityGroupImpl.setUserId(userId);
		socialActivityGroupImpl.setCreateDate(createDate);
		socialActivityGroupImpl.setModifiedDate(modifiedDate);
		socialActivityGroupImpl.setClassNameId(classNameId);
		socialActivityGroupImpl.setClassPK(classPK);
		socialActivityGroupImpl.setType(type);

		socialActivityGroupImpl.resetOriginalValues();

		return socialActivityGroupImpl;
	}

	public void readExternal(ObjectInput objectInput) throws IOException {
		activityGroupId = objectInput.readLong();
		groupId = objectInput.readLong();
		companyId = objectInput.readLong();
		userId = objectInput.readLong();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		classNameId = objectInput.readLong();
		classPK = objectInput.readLong();
		type = objectInput.readInt();
	}

	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(activityGroupId);
		objectOutput.writeLong(groupId);
		objectOutput.writeLong(companyId);
		objectOutput.writeLong(userId);
		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);
		objectOutput.writeLong(classNameId);
		objectOutput.writeLong(classPK);
		objectOutput.writeInt(type);
	}

	public long activityGroupId;
	public long groupId;
	public long companyId;
	public long userId;
	public long createDate;
	public long modifiedDate;
	public long classNameId;
	public long classPK;
	public int type;
}