/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat700.model.impl;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.tools.service.builder.test.compat700.model.WhereClauseEntry;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing WhereClauseEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class WhereClauseEntryCacheModel
	implements CacheModel<WhereClauseEntry>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof WhereClauseEntryCacheModel)) {
			return false;
		}

		WhereClauseEntryCacheModel whereClauseEntryCacheModel =
			(WhereClauseEntryCacheModel)object;

		if (whereClauseEntryId ==
				whereClauseEntryCacheModel.whereClauseEntryId) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, whereClauseEntryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(7);

		sb.append("{whereClauseEntryId=");
		sb.append(whereClauseEntryId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", nickname=");
		sb.append(nickname);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public WhereClauseEntry toEntityModel() {
		WhereClauseEntryImpl whereClauseEntryImpl = new WhereClauseEntryImpl();

		whereClauseEntryImpl.setWhereClauseEntryId(whereClauseEntryId);

		if (name == null) {
			whereClauseEntryImpl.setName("");
		}
		else {
			whereClauseEntryImpl.setName(name);
		}

		if (nickname == null) {
			whereClauseEntryImpl.setNickname("");
		}
		else {
			whereClauseEntryImpl.setNickname(nickname);
		}

		whereClauseEntryImpl.resetOriginalValues();

		return whereClauseEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		whereClauseEntryId = objectInput.readLong();
		name = objectInput.readUTF();
		nickname = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(whereClauseEntryId);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (nickname == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(nickname);
		}
	}

	public long whereClauseEntryId;
	public String name;
	public String nickname;

}
// SB-Hash:591449606