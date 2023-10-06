/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.kernel.model.PermissionPropagation;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing PermissionPropagation in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class PermissionPropagationCacheModel
	implements CacheModel<PermissionPropagation>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof PermissionPropagationCacheModel)) {
			return false;
		}

		PermissionPropagationCacheModel permissionPropagationCacheModel =
			(PermissionPropagationCacheModel)object;

		if ((permissionPropagationId ==
				permissionPropagationCacheModel.permissionPropagationId) &&
			(mvccVersion == permissionPropagationCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, permissionPropagationId);

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
		sb.append(", permissionPropagationId=");
		sb.append(permissionPropagationId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", propagate=");
		sb.append(propagate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public PermissionPropagation toEntityModel() {
		PermissionPropagationImpl permissionPropagationImpl =
			new PermissionPropagationImpl();

		permissionPropagationImpl.setMvccVersion(mvccVersion);
		permissionPropagationImpl.setCtCollectionId(ctCollectionId);
		permissionPropagationImpl.setPermissionPropagationId(
			permissionPropagationId);
		permissionPropagationImpl.setGroupId(groupId);
		permissionPropagationImpl.setCompanyId(companyId);
		permissionPropagationImpl.setClassNameId(classNameId);
		permissionPropagationImpl.setClassPK(classPK);
		permissionPropagationImpl.setPropagate(propagate);

		permissionPropagationImpl.resetOriginalValues();

		return permissionPropagationImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();

		permissionPropagationId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();

		propagate = objectInput.readBoolean();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

		objectOutput.writeLong(permissionPropagationId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(classNameId);

		objectOutput.writeLong(classPK);

		objectOutput.writeBoolean(propagate);
	}

	public long mvccVersion;
	public long ctCollectionId;
	public long permissionPropagationId;
	public long groupId;
	public long companyId;
	public long classNameId;
	public long classPK;
	public boolean propagate;

}