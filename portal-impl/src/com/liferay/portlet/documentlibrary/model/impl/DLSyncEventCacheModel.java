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

package com.liferay.portlet.documentlibrary.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import com.liferay.portlet.documentlibrary.model.DLSyncEvent;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing DLSyncEvent in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see DLSyncEvent
 * @generated
 */
@ProviderType
public class DLSyncEventCacheModel implements CacheModel<DLSyncEvent>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DLSyncEventCacheModel)) {
			return false;
		}

		DLSyncEventCacheModel dlSyncEventCacheModel = (DLSyncEventCacheModel)obj;

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
		sb.append(", modifiedTime=");
		sb.append(modifiedTime);
		sb.append(", event=");
		sb.append(event);
		sb.append(", type=");
		sb.append(type);
		sb.append(", typePK=");
		sb.append(typePK);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public DLSyncEvent toEntityModel() {
		DLSyncEventImpl dlSyncEventImpl = new DLSyncEventImpl();

		dlSyncEventImpl.setSyncEventId(syncEventId);
		dlSyncEventImpl.setModifiedTime(modifiedTime);

		if (event == null) {
			dlSyncEventImpl.setEvent(StringPool.BLANK);
		}
		else {
			dlSyncEventImpl.setEvent(event);
		}

		if (type == null) {
			dlSyncEventImpl.setType(StringPool.BLANK);
		}
		else {
			dlSyncEventImpl.setType(type);
		}

		dlSyncEventImpl.setTypePK(typePK);
		dlSyncEventImpl.setCompanyId(companyId);

		dlSyncEventImpl.resetOriginalValues();

		return dlSyncEventImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		syncEventId = objectInput.readLong();
		modifiedTime = objectInput.readLong();
		event = objectInput.readUTF();
		type = objectInput.readUTF();
		typePK = objectInput.readLong();
		companyId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(syncEventId);
		objectOutput.writeLong(modifiedTime);

		if (event == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(event);
		}

		if (type == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(type);
		}

		objectOutput.writeLong(typePK);
		objectOutput.writeLong(companyId);
	}

	public long syncEventId;
	public long modifiedTime;
	public String event;
	public String type;
	public long typePK;
	public long companyId;
}