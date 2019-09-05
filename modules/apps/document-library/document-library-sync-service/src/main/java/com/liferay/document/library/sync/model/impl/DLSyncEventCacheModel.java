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

package com.liferay.document.library.sync.model.impl;

import com.liferay.document.library.sync.model.DLSyncEvent;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing DLSyncEvent in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DLSyncEventCacheModel
	implements CacheModel<DLSyncEvent>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DLSyncEventCacheModel)) {
			return false;
		}

		DLSyncEventCacheModel dlSyncEventCacheModel =
			(DLSyncEventCacheModel)obj;

		if (syncEventId == dlSyncEventCacheModel.syncEventId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, syncEventId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(13);

		sb.append("{syncEventId=");
		sb.append(syncEventId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", modifiedTime=");
		sb.append(modifiedTime);
		sb.append(", event=");
		sb.append(event);
		sb.append(", type=");
		sb.append(type);
		sb.append(", typePK=");
		sb.append(typePK);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public DLSyncEvent toEntityModel() {
		DLSyncEventImpl dlSyncEventImpl = new DLSyncEventImpl();

		dlSyncEventImpl.setSyncEventId(syncEventId);
		dlSyncEventImpl.setCompanyId(companyId);
		dlSyncEventImpl.setModifiedTime(modifiedTime);

		if (event == null) {
			dlSyncEventImpl.setEvent("");
		}
		else {
			dlSyncEventImpl.setEvent(event);
		}

		if (type == null) {
			dlSyncEventImpl.setType("");
		}
		else {
			dlSyncEventImpl.setType(type);
		}

		dlSyncEventImpl.setTypePK(typePK);

		dlSyncEventImpl.resetOriginalValues();

		return dlSyncEventImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		syncEventId = objectInput.readLong();

		companyId = objectInput.readLong();

		modifiedTime = objectInput.readLong();
		event = objectInput.readUTF();
		type = objectInput.readUTF();

		typePK = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(syncEventId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(modifiedTime);

		if (event == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(event);
		}

		if (type == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(type);
		}

		objectOutput.writeLong(typePK);
	}

	public long syncEventId;
	public long companyId;
	public long modifiedTime;
	public String event;
	public String type;
	public long typePK;

}