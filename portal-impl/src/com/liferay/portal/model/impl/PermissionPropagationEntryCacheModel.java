/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.kernel.model.PermissionPropagationEntry;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing PermissionPropagationEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class PermissionPropagationEntryCacheModel
	implements CacheModel<PermissionPropagationEntry>, Externalizable,
			   MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof PermissionPropagationEntryCacheModel)) {
			return false;
		}

		PermissionPropagationEntryCacheModel
			permissionPropagationEntryCacheModel =
				(PermissionPropagationEntryCacheModel)object;

		if ((permissionPropagationEntryId ==
				permissionPropagationEntryCacheModel.
					permissionPropagationEntryId) &&
			(mvccVersion == permissionPropagationEntryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, permissionPropagationEntryId);

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
		sb.append(", permissionPropagationEntryId=");
		sb.append(permissionPropagationEntryId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", propagation=");
		sb.append(propagation);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public PermissionPropagationEntry toEntityModel() {
		PermissionPropagationEntryImpl permissionPropagationEntryImpl =
			new PermissionPropagationEntryImpl();

		permissionPropagationEntryImpl.setMvccVersion(mvccVersion);
		permissionPropagationEntryImpl.setCtCollectionId(ctCollectionId);
		permissionPropagationEntryImpl.setPermissionPropagationEntryId(
			permissionPropagationEntryId);
		permissionPropagationEntryImpl.setGroupId(groupId);
		permissionPropagationEntryImpl.setCompanyId(companyId);
		permissionPropagationEntryImpl.setClassNameId(classNameId);
		permissionPropagationEntryImpl.setClassPK(classPK);
		permissionPropagationEntryImpl.setPropagation(propagation);

		permissionPropagationEntryImpl.resetOriginalValues();

		return permissionPropagationEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();

		permissionPropagationEntryId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();

		propagation = objectInput.readBoolean();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

		objectOutput.writeLong(permissionPropagationEntryId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(classNameId);

		objectOutput.writeLong(classPK);

		objectOutput.writeBoolean(propagation);
	}

	public long mvccVersion;
	public long ctCollectionId;
	public long permissionPropagationEntryId;
	public long groupId;
	public long companyId;
	public long classNameId;
	public long classPK;
	public boolean propagation;

}