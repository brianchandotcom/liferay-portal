/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.tools.service.builder.test.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.tools.service.builder.test.model.FinderWhereClauseEntry;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing FinderWhereClauseEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class FinderWhereClauseEntryCacheModel
	implements CacheModel<FinderWhereClauseEntry>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof FinderWhereClauseEntryCacheModel)) {
			return false;
		}

		FinderWhereClauseEntryCacheModel finderWhereClauseEntryCacheModel =
			(FinderWhereClauseEntryCacheModel)object;

		if (finderWhereClauseEntryId ==
				finderWhereClauseEntryCacheModel.finderWhereClauseEntryId) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, finderWhereClauseEntryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(7);

		sb.append("{finderWhereClauseEntryId=");
		sb.append(finderWhereClauseEntryId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", nickname=");
		sb.append(nickname);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public FinderWhereClauseEntry toEntityModel() {
		FinderWhereClauseEntryImpl finderWhereClauseEntryImpl =
			new FinderWhereClauseEntryImpl();

		finderWhereClauseEntryImpl.setFinderWhereClauseEntryId(
			finderWhereClauseEntryId);

		if (name == null) {
			finderWhereClauseEntryImpl.setName("");
		}
		else {
			finderWhereClauseEntryImpl.setName(name);
		}

		if (nickname == null) {
			finderWhereClauseEntryImpl.setNickname("");
		}
		else {
			finderWhereClauseEntryImpl.setNickname(nickname);
		}

		finderWhereClauseEntryImpl.resetOriginalValues();

		return finderWhereClauseEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		finderWhereClauseEntryId = objectInput.readLong();
		name = objectInput.readUTF();
		nickname = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(finderWhereClauseEntryId);

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

	public long finderWhereClauseEntryId;
	public String name;
	public String nickname;

}