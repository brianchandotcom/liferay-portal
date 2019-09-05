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
import com.liferay.social.kernel.model.SocialActivitySetting;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing SocialActivitySetting in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class SocialActivitySettingCacheModel
	implements CacheModel<SocialActivitySetting>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SocialActivitySettingCacheModel)) {
			return false;
		}

		SocialActivitySettingCacheModel socialActivitySettingCacheModel =
			(SocialActivitySettingCacheModel)obj;

		if (activitySettingId ==
				socialActivitySettingCacheModel.activitySettingId) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, activitySettingId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(15);

		sb.append("{activitySettingId=");
		sb.append(activitySettingId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", activityType=");
		sb.append(activityType);
		sb.append(", name=");
		sb.append(name);
		sb.append(", value=");
		sb.append(value);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public SocialActivitySetting toEntityModel() {
		SocialActivitySettingImpl socialActivitySettingImpl =
			new SocialActivitySettingImpl();

		socialActivitySettingImpl.setActivitySettingId(activitySettingId);
		socialActivitySettingImpl.setGroupId(groupId);
		socialActivitySettingImpl.setCompanyId(companyId);
		socialActivitySettingImpl.setClassNameId(classNameId);
		socialActivitySettingImpl.setActivityType(activityType);

		if (name == null) {
			socialActivitySettingImpl.setName("");
		}
		else {
			socialActivitySettingImpl.setName(name);
		}

		if (value == null) {
			socialActivitySettingImpl.setValue("");
		}
		else {
			socialActivitySettingImpl.setValue(value);
		}

		socialActivitySettingImpl.resetOriginalValues();

		return socialActivitySettingImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		activitySettingId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		classNameId = objectInput.readLong();

		activityType = objectInput.readInt();
		name = objectInput.readUTF();
		value = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(activitySettingId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(classNameId);

		objectOutput.writeInt(activityType);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (value == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(value);
		}
	}

	public long activitySettingId;
	public long groupId;
	public long companyId;
	public long classNameId;
	public int activityType;
	public String name;
	public String value;

}