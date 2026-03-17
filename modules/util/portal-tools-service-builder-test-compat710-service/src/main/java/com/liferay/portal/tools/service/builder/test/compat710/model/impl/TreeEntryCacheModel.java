/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat710.model.impl;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.tools.service.builder.test.compat710.model.TreeEntry;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing TreeEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class TreeEntryCacheModel
	implements CacheModel<TreeEntry>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof TreeEntryCacheModel)) {
			return false;
		}

		TreeEntryCacheModel treeEntryCacheModel = (TreeEntryCacheModel)object;

		if (treeEntryId == treeEntryCacheModel.treeEntryId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, treeEntryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(11);

		sb.append("{treeEntryId=");
		sb.append(treeEntryId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", parentTreeEntryId=");
		sb.append(parentTreeEntryId);
		sb.append(", leftTreeEntryId=");
		sb.append(leftTreeEntryId);
		sb.append(", rightTreeEntryId=");
		sb.append(rightTreeEntryId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public TreeEntry toEntityModel() {
		TreeEntryImpl treeEntryImpl = new TreeEntryImpl();

		treeEntryImpl.setTreeEntryId(treeEntryId);
		treeEntryImpl.setGroupId(groupId);
		treeEntryImpl.setParentTreeEntryId(parentTreeEntryId);
		treeEntryImpl.setLeftTreeEntryId(leftTreeEntryId);
		treeEntryImpl.setRightTreeEntryId(rightTreeEntryId);

		treeEntryImpl.resetOriginalValues();

		return treeEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		treeEntryId = objectInput.readLong();

		groupId = objectInput.readLong();

		parentTreeEntryId = objectInput.readLong();

		leftTreeEntryId = objectInput.readLong();

		rightTreeEntryId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(treeEntryId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(parentTreeEntryId);

		objectOutput.writeLong(leftTreeEntryId);

		objectOutput.writeLong(rightTreeEntryId);
	}

	public long treeEntryId;
	public long groupId;
	public long parentTreeEntryId;
	public long leftTreeEntryId;
	public long rightTreeEntryId;

}
// SB-Hash:-1241197395