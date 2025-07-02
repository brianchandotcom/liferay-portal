/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.sample.model.impl;

import com.liferay.change.tracking.sample.model.CTSChild;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing CTSChild in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class CTSChildCacheModel
	implements CacheModel<CTSChild>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CTSChildCacheModel)) {
			return false;
		}

		CTSChildCacheModel ctsChildCacheModel = (CTSChildCacheModel)object;

		if ((ctsChildId == ctsChildCacheModel.ctsChildId) &&
			(mvccVersion == ctsChildCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, ctsChildId);

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
		sb.append(", ctsChildId=");
		sb.append(ctsChildId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", ctsGrandParentId=");
		sb.append(ctsGrandParentId);
		sb.append(", ctsParentName=");
		sb.append(ctsParentName);
		sb.append(", parentCTSChildId=");
		sb.append(parentCTSChildId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CTSChild toEntityModel() {
		CTSChildImpl ctsChildImpl = new CTSChildImpl();

		ctsChildImpl.setMvccVersion(mvccVersion);
		ctsChildImpl.setCtCollectionId(ctCollectionId);
		ctsChildImpl.setCtsChildId(ctsChildId);
		ctsChildImpl.setCompanyId(companyId);

		if (name == null) {
			ctsChildImpl.setName("");
		}
		else {
			ctsChildImpl.setName(name);
		}

		ctsChildImpl.setCtsGrandParentId(ctsGrandParentId);

		if (ctsParentName == null) {
			ctsChildImpl.setCtsParentName("");
		}
		else {
			ctsChildImpl.setCtsParentName(ctsParentName);
		}

		ctsChildImpl.setParentCTSChildId(parentCTSChildId);

		ctsChildImpl.resetOriginalValues();

		return ctsChildImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();

		ctsChildId = objectInput.readLong();

		companyId = objectInput.readLong();
		name = objectInput.readUTF();

		ctsGrandParentId = objectInput.readLong();
		ctsParentName = objectInput.readUTF();

		parentCTSChildId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

		objectOutput.writeLong(ctsChildId);

		objectOutput.writeLong(companyId);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		objectOutput.writeLong(ctsGrandParentId);

		if (ctsParentName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(ctsParentName);
		}

		objectOutput.writeLong(parentCTSChildId);
	}

	public long mvccVersion;
	public long ctCollectionId;
	public long ctsChildId;
	public long companyId;
	public String name;
	public long ctsGrandParentId;
	public String ctsParentName;
	public long parentCTSChildId;

}