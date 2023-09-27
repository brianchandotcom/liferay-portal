/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.tools.service.builder.test.model.RenameFinderColumnEntry;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing RenameFinderColumnEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class RenameFinderColumnEntryCacheModel
	implements CacheModel<RenameFinderColumnEntry>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof RenameFinderColumnEntryCacheModel)) {
			return false;
		}

		RenameFinderColumnEntryCacheModel renameFinderColumnEntryCacheModel =
			(RenameFinderColumnEntryCacheModel)object;

		if (renameFinderColumnEntryId ==
				renameFinderColumnEntryCacheModel.renameFinderColumnEntryId) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, renameFinderColumnEntryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(7);

		sb.append("{renameFinderColumnEntryId=");
		sb.append(renameFinderColumnEntryId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", columnToRename=");
		sb.append(columnToRename);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public RenameFinderColumnEntry toEntityModel() {
		RenameFinderColumnEntryImpl renameFinderColumnEntryImpl =
			new RenameFinderColumnEntryImpl();

		renameFinderColumnEntryImpl.setRenameFinderColumnEntryId(
			renameFinderColumnEntryId);
		renameFinderColumnEntryImpl.setGroupId(groupId);

		if (columnToRename == null) {
			renameFinderColumnEntryImpl.setColumnToRename("");
		}
		else {
			renameFinderColumnEntryImpl.setColumnToRename(columnToRename);
		}

		renameFinderColumnEntryImpl.resetOriginalValues();

		return renameFinderColumnEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		renameFinderColumnEntryId = objectInput.readLong();

		groupId = objectInput.readLong();
		columnToRename = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(renameFinderColumnEntryId);

		objectOutput.writeLong(groupId);

		if (columnToRename == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(columnToRename);
		}
	}

	public long renameFinderColumnEntryId;
	public long groupId;
	public String columnToRename;

}