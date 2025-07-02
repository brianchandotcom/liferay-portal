/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.sample.model.impl;

import com.liferay.change.tracking.sample.model.Child;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing Child in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class ChildCacheModel
	implements CacheModel<Child>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ChildCacheModel)) {
			return false;
		}

		ChildCacheModel childCacheModel = (ChildCacheModel)object;

		if ((childId == childCacheModel.childId) &&
			(mvccVersion == childCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, childId);

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
		sb.append(", childId=");
		sb.append(childId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", grandParentId=");
		sb.append(grandParentId);
		sb.append(", parentChildId=");
		sb.append(parentChildId);
		sb.append(", parentName=");
		sb.append(parentName);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public Child toEntityModel() {
		ChildImpl childImpl = new ChildImpl();

		childImpl.setMvccVersion(mvccVersion);
		childImpl.setCtCollectionId(ctCollectionId);
		childImpl.setChildId(childId);
		childImpl.setCompanyId(companyId);

		if (name == null) {
			childImpl.setName("");
		}
		else {
			childImpl.setName(name);
		}

		childImpl.setGrandParentId(grandParentId);
		childImpl.setParentChildId(parentChildId);

		if (parentName == null) {
			childImpl.setParentName("");
		}
		else {
			childImpl.setParentName(parentName);
		}

		childImpl.resetOriginalValues();

		return childImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();

		childId = objectInput.readLong();

		companyId = objectInput.readLong();
		name = objectInput.readUTF();

		grandParentId = objectInput.readLong();

		parentChildId = objectInput.readLong();
		parentName = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

		objectOutput.writeLong(childId);

		objectOutput.writeLong(companyId);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		objectOutput.writeLong(grandParentId);

		objectOutput.writeLong(parentChildId);

		if (parentName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(parentName);
		}
	}

	public long mvccVersion;
	public long ctCollectionId;
	public long childId;
	public long companyId;
	public String name;
	public long grandParentId;
	public long parentChildId;
	public String parentName;

}