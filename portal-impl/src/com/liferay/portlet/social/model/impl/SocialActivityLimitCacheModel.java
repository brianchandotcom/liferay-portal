/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.social.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.social.kernel.model.SocialActivityLimit;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing SocialActivityLimit in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class SocialActivityLimitCacheModel
	implements CacheModel<SocialActivityLimit>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof SocialActivityLimitCacheModel)) {
			return false;
		}

		SocialActivityLimitCacheModel socialActivityLimitCacheModel =
			(SocialActivityLimitCacheModel)object;

		if ((activityLimitId ==
				socialActivityLimitCacheModel.activityLimitId) &&
			(mvccVersion == socialActivityLimitCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, activityLimitId);

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
		StringBundler sb = new StringBundler(23);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", activityLimitId=");
		sb.append(activityLimitId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", activityType=");
		sb.append(activityType);
		sb.append(", activityCounterName=");
		sb.append(activityCounterName);
		sb.append(", value=");
		sb.append(value);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public SocialActivityLimit toEntityModel() {
		SocialActivityLimitImpl socialActivityLimitImpl =
			new SocialActivityLimitImpl();

		socialActivityLimitImpl.setMvccVersion(mvccVersion);
		socialActivityLimitImpl.setCtCollectionId(ctCollectionId);
		socialActivityLimitImpl.setActivityLimitId(activityLimitId);
		socialActivityLimitImpl.setGroupId(groupId);
		socialActivityLimitImpl.setCompanyId(companyId);
		socialActivityLimitImpl.setUserId(userId);
		socialActivityLimitImpl.setClassNameId(classNameId);
		socialActivityLimitImpl.setClassPK(classPK);
		socialActivityLimitImpl.setActivityType(activityType);

		if (activityCounterName == null) {
			socialActivityLimitImpl.setActivityCounterName("");
		}
		else {
			socialActivityLimitImpl.setActivityCounterName(activityCounterName);
		}

		if (value == null) {
			socialActivityLimitImpl.setValue("");
		}
		else {
			socialActivityLimitImpl.setValue(value);
		}

		socialActivityLimitImpl.resetOriginalValues();

		return socialActivityLimitImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();

		activityLimitId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();

		activityType = objectInput.readInt();
		activityCounterName = objectInput.readUTF();
		value = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

		objectOutput.writeLong(activityLimitId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		objectOutput.writeLong(classNameId);

		objectOutput.writeLong(classPK);

		objectOutput.writeInt(activityType);

		if (activityCounterName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(activityCounterName);
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
	public long activityLimitId;
	public long groupId;
	public long companyId;
	public long userId;
	public long classNameId;
	public long classPK;
	public int activityType;
	public String activityCounterName;
	public String value;

}