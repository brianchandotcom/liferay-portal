/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.model.impl;

import com.liferay.change.tracking.model.CTSchemaVersion;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;

import java.util.Map;

/**
 * The cache model class for representing CTSchemaVersion in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class CTSchemaVersionCacheModel
	implements CacheModel<CTSchemaVersion>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CTSchemaVersionCacheModel)) {
			return false;
		}

		CTSchemaVersionCacheModel ctSchemaVersionCacheModel =
			(CTSchemaVersionCacheModel)object;

		if ((schemaVersionId == ctSchemaVersionCacheModel.schemaVersionId) &&
			(mvccVersion == ctSchemaVersionCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, schemaVersionId);

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
		sb.append(", schemaVersionId=");
		sb.append(schemaVersionId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", schemaContext=");
		sb.append(schemaContext);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CTSchemaVersion toEntityModel() {
		CTSchemaVersionImpl ctSchemaVersionImpl = new CTSchemaVersionImpl();

		ctSchemaVersionImpl.setMvccVersion(mvccVersion);
		ctSchemaVersionImpl.setSchemaVersionId(schemaVersionId);
		ctSchemaVersionImpl.setCompanyId(companyId);
		ctSchemaVersionImpl.setSchemaContext(schemaContext);

		ctSchemaVersionImpl.resetOriginalValues();

		return ctSchemaVersionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();

		schemaVersionId = objectInput.readLong();

		companyId = objectInput.readLong();
		schemaContext = (Map<String, Serializable>)objectInput.readObject();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(schemaVersionId);

		objectOutput.writeLong(companyId);
		objectOutput.writeObject(schemaContext);
	}

	public long mvccVersion;
	public long schemaVersionId;
	public long companyId;
	public Map<String, Serializable> schemaContext;

}