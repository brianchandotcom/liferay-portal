/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.social.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
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
	implements CacheModel<SocialActivitySetting>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof SocialActivitySettingCacheModel)) {
			return false;
		}

		SocialActivitySettingCacheModel socialActivitySettingCacheModel =
			(SocialActivitySettingCacheModel)object;

		if ((activitySettingId ==
				socialActivitySettingCacheModel.activitySettingId) &&
			(mvccVersion == socialActivitySettingCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, activitySettingId);

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
		sb.append(", activitySettingId=");
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

		socialActivitySettingImpl.setMvccVersion(mvccVersion);
		socialActivitySettingImpl.setCtCollectionId(ctCollectionId);
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
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();

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
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

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

	public long mvccVersion;
	public long ctCollectionId;
	public long activitySettingId;
	public long groupId;
	public long companyId;
	public long classNameId;
	public int activityType;
	public String name;
	public String value;

}