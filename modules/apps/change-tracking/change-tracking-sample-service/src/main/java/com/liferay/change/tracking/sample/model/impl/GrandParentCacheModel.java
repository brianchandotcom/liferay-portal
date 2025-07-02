/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.sample.model.impl;

import com.liferay.change.tracking.sample.model.GrandParent;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing GrandParent in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class GrandParentCacheModel
	implements CacheModel<GrandParent>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof GrandParentCacheModel)) {
			return false;
		}

		GrandParentCacheModel grandParentCacheModel =
			(GrandParentCacheModel)object;

		if ((grandParentId == grandParentCacheModel.grandParentId) &&
			(mvccVersion == grandParentCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, grandParentId);

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
		StringBundler sb = new StringBundler(11);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", grandParentId=");
		sb.append(grandParentId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", parentGrandParentId=");
		sb.append(parentGrandParentId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public GrandParent toEntityModel() {
		GrandParentImpl grandParentImpl = new GrandParentImpl();

		grandParentImpl.setMvccVersion(mvccVersion);
		grandParentImpl.setGrandParentId(grandParentId);
		grandParentImpl.setCompanyId(companyId);

		if (name == null) {
			grandParentImpl.setName("");
		}
		else {
			grandParentImpl.setName(name);
		}

		grandParentImpl.setParentGrandParentId(parentGrandParentId);

		grandParentImpl.resetOriginalValues();

		return grandParentImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		grandParentId = objectInput.readLong();

		companyId = objectInput.readLong();
		name = objectInput.readUTF();

		parentGrandParentId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(grandParentId);

		objectOutput.writeLong(companyId);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		objectOutput.writeLong(parentGrandParentId);
	}

	public long mvccVersion;
	public long grandParentId;
	public long companyId;
	public String name;
	public long parentGrandParentId;

}