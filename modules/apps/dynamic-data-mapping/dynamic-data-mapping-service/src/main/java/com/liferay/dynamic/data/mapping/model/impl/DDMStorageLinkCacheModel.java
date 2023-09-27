/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.model.impl;

import com.liferay.dynamic.data.mapping.model.DDMStorageLink;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing DDMStorageLink in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DDMStorageLinkCacheModel
	implements CacheModel<DDMStorageLink>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DDMStorageLinkCacheModel)) {
			return false;
		}

		DDMStorageLinkCacheModel ddmStorageLinkCacheModel =
			(DDMStorageLinkCacheModel)object;

		if ((storageLinkId == ddmStorageLinkCacheModel.storageLinkId) &&
			(mvccVersion == ddmStorageLinkCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, storageLinkId);

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
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", storageLinkId=");
		sb.append(storageLinkId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", structureId=");
		sb.append(structureId);
		sb.append(", structureVersionId=");
		sb.append(structureVersionId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public DDMStorageLink toEntityModel() {
		DDMStorageLinkImpl ddmStorageLinkImpl = new DDMStorageLinkImpl();

		ddmStorageLinkImpl.setMvccVersion(mvccVersion);
		ddmStorageLinkImpl.setCtCollectionId(ctCollectionId);

		if (uuid == null) {
			ddmStorageLinkImpl.setUuid("");
		}
		else {
			ddmStorageLinkImpl.setUuid(uuid);
		}

		ddmStorageLinkImpl.setStorageLinkId(storageLinkId);
		ddmStorageLinkImpl.setCompanyId(companyId);
		ddmStorageLinkImpl.setClassNameId(classNameId);
		ddmStorageLinkImpl.setClassPK(classPK);
		ddmStorageLinkImpl.setStructureId(structureId);
		ddmStorageLinkImpl.setStructureVersionId(structureVersionId);

		ddmStorageLinkImpl.resetOriginalValues();

		return ddmStorageLinkImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();
		uuid = objectInput.readUTF();

		storageLinkId = objectInput.readLong();

		companyId = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();

		structureId = objectInput.readLong();

		structureVersionId = objectInput.readLong();
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

		objectOutput.writeLong(storageLinkId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(classNameId);

		objectOutput.writeLong(classPK);

		objectOutput.writeLong(structureId);

		objectOutput.writeLong(structureVersionId);
	}

	public long mvccVersion;
	public long ctCollectionId;
	public String uuid;
	public long storageLinkId;
	public long companyId;
	public long classNameId;
	public long classPK;
	public long structureId;
	public long structureVersionId;

}