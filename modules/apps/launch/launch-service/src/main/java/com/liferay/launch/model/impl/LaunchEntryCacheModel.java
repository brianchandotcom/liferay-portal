/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.launch.model.impl;

import com.liferay.launch.model.LaunchEntry;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing LaunchEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class LaunchEntryCacheModel
	implements CacheModel<LaunchEntry>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof LaunchEntryCacheModel)) {
			return false;
		}

		LaunchEntryCacheModel launchEntryCacheModel =
			(LaunchEntryCacheModel)object;

		if ((launchEntryId == launchEntryCacheModel.launchEntryId) &&
			(mvccVersion == launchEntryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, launchEntryId);

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
		StringBundler sb = new StringBundler(25);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", externalReferenceCode=");
		sb.append(externalReferenceCode);
		sb.append(", launchEntryId=");
		sb.append(launchEntryId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", launchSetId=");
		sb.append(launchSetId);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", classVersion=");
		sb.append(classVersion);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public LaunchEntry toEntityModel() {
		LaunchEntryImpl launchEntryImpl = new LaunchEntryImpl();

		launchEntryImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			launchEntryImpl.setUuid("");
		}
		else {
			launchEntryImpl.setUuid(uuid);
		}

		if (externalReferenceCode == null) {
			launchEntryImpl.setExternalReferenceCode("");
		}
		else {
			launchEntryImpl.setExternalReferenceCode(externalReferenceCode);
		}

		launchEntryImpl.setLaunchEntryId(launchEntryId);
		launchEntryImpl.setCompanyId(companyId);
		launchEntryImpl.setUserId(userId);

		if (createDate == Long.MIN_VALUE) {
			launchEntryImpl.setCreateDate(null);
		}
		else {
			launchEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			launchEntryImpl.setModifiedDate(null);
		}
		else {
			launchEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		launchEntryImpl.setLaunchSetId(launchSetId);
		launchEntryImpl.setClassNameId(classNameId);
		launchEntryImpl.setClassPK(classPK);

		if (classVersion == null) {
			launchEntryImpl.setClassVersion("");
		}
		else {
			launchEntryImpl.setClassVersion(classVersion);
		}

		launchEntryImpl.resetOriginalValues();

		return launchEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();
		externalReferenceCode = objectInput.readUTF();

		launchEntryId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		launchSetId = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();
		classVersion = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		if (externalReferenceCode == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(externalReferenceCode);
		}

		objectOutput.writeLong(launchEntryId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);
		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		objectOutput.writeLong(launchSetId);

		objectOutput.writeLong(classNameId);

		objectOutput.writeLong(classPK);

		if (classVersion == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(classVersion);
		}
	}

	public long mvccVersion;
	public String uuid;
	public String externalReferenceCode;
	public long launchEntryId;
	public long companyId;
	public long userId;
	public long createDate;
	public long modifiedDate;
	public long launchSetId;
	public long classNameId;
	public long classPK;
	public String classVersion;

}
// SB-Hash:-451925477