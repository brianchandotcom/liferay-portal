/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.social.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.social.kernel.model.SocialActivityCounter;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing SocialActivityCounter in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class SocialActivityCounterCacheModel
	implements CacheModel<SocialActivityCounter>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof SocialActivityCounterCacheModel)) {
			return false;
		}

		SocialActivityCounterCacheModel socialActivityCounterCacheModel =
			(SocialActivityCounterCacheModel)object;

		if ((activityCounterId ==
				socialActivityCounterCacheModel.activityCounterId) &&
			(mvccVersion == socialActivityCounterCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, activityCounterId);

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
		StringBundler sb = new StringBundler(31);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", activityCounterId=");
		sb.append(activityCounterId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", name=");
		sb.append(name);
		sb.append(", ownerType=");
		sb.append(ownerType);
		sb.append(", currentValue=");
		sb.append(currentValue);
		sb.append(", totalValue=");
		sb.append(totalValue);
		sb.append(", graceValue=");
		sb.append(graceValue);
		sb.append(", startPeriod=");
		sb.append(startPeriod);
		sb.append(", endPeriod=");
		sb.append(endPeriod);
		sb.append(", active=");
		sb.append(active);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public SocialActivityCounter toEntityModel() {
		SocialActivityCounterImpl socialActivityCounterImpl =
			new SocialActivityCounterImpl();

		socialActivityCounterImpl.setMvccVersion(mvccVersion);
		socialActivityCounterImpl.setCtCollectionId(ctCollectionId);
		socialActivityCounterImpl.setActivityCounterId(activityCounterId);
		socialActivityCounterImpl.setGroupId(groupId);
		socialActivityCounterImpl.setCompanyId(companyId);
		socialActivityCounterImpl.setClassNameId(classNameId);
		socialActivityCounterImpl.setClassPK(classPK);

		if (name == null) {
			socialActivityCounterImpl.setName("");
		}
		else {
			socialActivityCounterImpl.setName(name);
		}

		socialActivityCounterImpl.setOwnerType(ownerType);
		socialActivityCounterImpl.setCurrentValue(currentValue);
		socialActivityCounterImpl.setTotalValue(totalValue);
		socialActivityCounterImpl.setGraceValue(graceValue);
		socialActivityCounterImpl.setStartPeriod(startPeriod);
		socialActivityCounterImpl.setEndPeriod(endPeriod);
		socialActivityCounterImpl.setActive(active);

		socialActivityCounterImpl.resetOriginalValues();

		return socialActivityCounterImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();

		activityCounterId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();
		name = objectInput.readUTF();

		ownerType = objectInput.readInt();

		currentValue = objectInput.readInt();

		totalValue = objectInput.readInt();

		graceValue = objectInput.readInt();

		startPeriod = objectInput.readInt();

		endPeriod = objectInput.readInt();

		active = objectInput.readBoolean();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

		objectOutput.writeLong(activityCounterId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(classNameId);

		objectOutput.writeLong(classPK);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		objectOutput.writeInt(ownerType);

		objectOutput.writeInt(currentValue);

		objectOutput.writeInt(totalValue);

		objectOutput.writeInt(graceValue);

		objectOutput.writeInt(startPeriod);

		objectOutput.writeInt(endPeriod);

		objectOutput.writeBoolean(active);
	}

	public long mvccVersion;
	public long ctCollectionId;
	public long activityCounterId;
	public long groupId;
	public long companyId;
	public long classNameId;
	public long classPK;
	public String name;
	public int ownerType;
	public int currentValue;
	public int totalValue;
	public int graceValue;
	public int startPeriod;
	public int endPeriod;
	public boolean active;

}