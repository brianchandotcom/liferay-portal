/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat730.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.tools.service.builder.test.compat730.model.MVCCEntry;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing MVCCEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class MVCCEntryCacheModel
	implements CacheModel<MVCCEntry>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof MVCCEntryCacheModel)) {
			return false;
		}

		MVCCEntryCacheModel mvccEntryCacheModel = (MVCCEntryCacheModel)object;

		if ((mvccEntryId == mvccEntryCacheModel.mvccEntryId) &&
			(mvccVersion == mvccEntryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, mvccEntryId);

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
		StringBundler sb = new StringBundler(9);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", mvccEntryId=");
		sb.append(mvccEntryId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", name=");
		sb.append(name);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public MVCCEntry toEntityModel() {
		MVCCEntryImpl mvccEntryImpl = new MVCCEntryImpl();

		mvccEntryImpl.setMvccVersion(mvccVersion);
		mvccEntryImpl.setMvccEntryId(mvccEntryId);
		mvccEntryImpl.setCompanyId(companyId);

		if (name == null) {
			mvccEntryImpl.setName("");
		}
		else {
			mvccEntryImpl.setName(name);
		}

		mvccEntryImpl.resetOriginalValues();

		return mvccEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		mvccEntryId = objectInput.readLong();

		companyId = objectInput.readLong();
		name = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(mvccEntryId);

		objectOutput.writeLong(companyId);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}
	}

	public long mvccVersion;
	public long mvccEntryId;
	public long companyId;
	public String name;

}
// SB-Hash:-1170130435