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
import com.liferay.portal.tools.service.builder.test.model.UADPartialEntry;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing UADPartialEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class UADPartialEntryCacheModel
	implements CacheModel<UADPartialEntry>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof UADPartialEntryCacheModel)) {
			return false;
		}

		UADPartialEntryCacheModel uadPartialEntryCacheModel =
			(UADPartialEntryCacheModel)obj;

		if (uadPartialEntryId == uadPartialEntryCacheModel.uadPartialEntryId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, uadPartialEntryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(9);

		sb.append("{uadPartialEntryId=");
		sb.append(uadPartialEntryId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", message=");
		sb.append(message);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public UADPartialEntry toEntityModel() {
		UADPartialEntryImpl uadPartialEntryImpl = new UADPartialEntryImpl();

		uadPartialEntryImpl.setUadPartialEntryId(uadPartialEntryId);
		uadPartialEntryImpl.setUserId(userId);

		if (userName == null) {
			uadPartialEntryImpl.setUserName("");
		}
		else {
			uadPartialEntryImpl.setUserName(userName);
		}

		if (message == null) {
			uadPartialEntryImpl.setMessage("");
		}
		else {
			uadPartialEntryImpl.setMessage(message);
		}

		uadPartialEntryImpl.resetOriginalValues();

		return uadPartialEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uadPartialEntryId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		message = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(uadPartialEntryId);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(userName);
		}

		if (message == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(message);
		}
	}

	public long uadPartialEntryId;
	public long userId;
	public String userName;
	public String message;

}