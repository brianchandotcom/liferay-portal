/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.model.impl;

import com.liferay.depot.model.DepotEntryPin;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing DepotEntryPin in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DepotEntryPinCacheModel
	implements CacheModel<DepotEntryPin>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DepotEntryPinCacheModel)) {
			return false;
		}

		DepotEntryPinCacheModel depotEntryPinCacheModel =
			(DepotEntryPinCacheModel)object;

		if ((depotEntryPinId == depotEntryPinCacheModel.depotEntryPinId) &&
			(mvccVersion == depotEntryPinCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, depotEntryPinId);

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
		StringBundler sb = new StringBundler(17);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", depotEntryPinId=");
		sb.append(depotEntryPinId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", depotEntryId=");
		sb.append(depotEntryId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public DepotEntryPin toEntityModel() {
		DepotEntryPinImpl depotEntryPinImpl = new DepotEntryPinImpl();

		depotEntryPinImpl.setMvccVersion(mvccVersion);
		depotEntryPinImpl.setCtCollectionId(ctCollectionId);

		if (uuid == null) {
			depotEntryPinImpl.setUuid("");
		}
		else {
			depotEntryPinImpl.setUuid(uuid);
		}

		depotEntryPinImpl.setDepotEntryPinId(depotEntryPinId);
		depotEntryPinImpl.setGroupId(groupId);
		depotEntryPinImpl.setCompanyId(companyId);
		depotEntryPinImpl.setUserId(userId);
		depotEntryPinImpl.setDepotEntryId(depotEntryId);

		depotEntryPinImpl.resetOriginalValues();

		return depotEntryPinImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();
		uuid = objectInput.readUTF();

		depotEntryPinId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();

		depotEntryId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(depotEntryPinId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		objectOutput.writeLong(depotEntryId);
	}

	public long mvccVersion;
	public long ctCollectionId;
	public String uuid;
	public long depotEntryPinId;
	public long groupId;
	public long companyId;
	public long userId;
	public long depotEntryId;

}
// SB-Hash:-1423485036