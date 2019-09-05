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
import com.liferay.social.kernel.model.SocialActivityAchievement;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing SocialActivityAchievement in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class SocialActivityAchievementCacheModel
	implements CacheModel<SocialActivityAchievement>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SocialActivityAchievementCacheModel)) {
			return false;
		}

		SocialActivityAchievementCacheModel
			socialActivityAchievementCacheModel =
				(SocialActivityAchievementCacheModel)obj;

		if (activityAchievementId ==
				socialActivityAchievementCacheModel.activityAchievementId) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, activityAchievementId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(15);

		sb.append("{activityAchievementId=");
		sb.append(activityAchievementId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", name=");
		sb.append(name);
		sb.append(", firstInGroup=");
		sb.append(firstInGroup);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public SocialActivityAchievement toEntityModel() {
		SocialActivityAchievementImpl socialActivityAchievementImpl =
			new SocialActivityAchievementImpl();

		socialActivityAchievementImpl.setActivityAchievementId(
			activityAchievementId);
		socialActivityAchievementImpl.setGroupId(groupId);
		socialActivityAchievementImpl.setCompanyId(companyId);
		socialActivityAchievementImpl.setUserId(userId);
		socialActivityAchievementImpl.setCreateDate(createDate);

		if (name == null) {
			socialActivityAchievementImpl.setName("");
		}
		else {
			socialActivityAchievementImpl.setName(name);
		}

		socialActivityAchievementImpl.setFirstInGroup(firstInGroup);

		socialActivityAchievementImpl.resetOriginalValues();

		return socialActivityAchievementImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		activityAchievementId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();

		createDate = objectInput.readLong();
		name = objectInput.readUTF();

		firstInGroup = objectInput.readBoolean();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(activityAchievementId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		objectOutput.writeLong(createDate);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		objectOutput.writeBoolean(firstInGroup);
	}

	public long activityAchievementId;
	public long groupId;
	public long companyId;
	public long userId;
	public long createDate;
	public String name;
	public boolean firstInGroup;

}