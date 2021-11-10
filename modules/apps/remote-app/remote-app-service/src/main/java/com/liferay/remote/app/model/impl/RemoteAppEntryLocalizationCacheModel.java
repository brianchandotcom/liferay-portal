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

package com.liferay.remote.app.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.remote.app.model.RemoteAppEntryLocalization;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing RemoteAppEntryLocalization in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class RemoteAppEntryLocalizationCacheModel
	implements CacheModel<RemoteAppEntryLocalization>, Externalizable,
			   MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof RemoteAppEntryLocalizationCacheModel)) {
			return false;
		}

		RemoteAppEntryLocalizationCacheModel
			remoteAppEntryLocalizationCacheModel =
				(RemoteAppEntryLocalizationCacheModel)object;

		if ((remoteAppEntryLocalizationId ==
				remoteAppEntryLocalizationCacheModel.
					remoteAppEntryLocalizationId) &&
			(mvccVersion == remoteAppEntryLocalizationCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, remoteAppEntryLocalizationId);

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
		StringBundler sb = new StringBundler(15);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", remoteAppEntryLocalizationId=");
		sb.append(remoteAppEntryLocalizationId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", remoteAppEntryId=");
		sb.append(remoteAppEntryId);
		sb.append(", languageId=");
		sb.append(languageId);
		sb.append(", description=");
		sb.append(description);
		sb.append(", name=");
		sb.append(name);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public RemoteAppEntryLocalization toEntityModel() {
		RemoteAppEntryLocalizationImpl remoteAppEntryLocalizationImpl =
			new RemoteAppEntryLocalizationImpl();

		remoteAppEntryLocalizationImpl.setMvccVersion(mvccVersion);
		remoteAppEntryLocalizationImpl.setRemoteAppEntryLocalizationId(
			remoteAppEntryLocalizationId);
		remoteAppEntryLocalizationImpl.setCompanyId(companyId);
		remoteAppEntryLocalizationImpl.setRemoteAppEntryId(remoteAppEntryId);

		if (languageId == null) {
			remoteAppEntryLocalizationImpl.setLanguageId("");
		}
		else {
			remoteAppEntryLocalizationImpl.setLanguageId(languageId);
		}

		if (description == null) {
			remoteAppEntryLocalizationImpl.setDescription("");
		}
		else {
			remoteAppEntryLocalizationImpl.setDescription(description);
		}

		if (name == null) {
			remoteAppEntryLocalizationImpl.setName("");
		}
		else {
			remoteAppEntryLocalizationImpl.setName(name);
		}

		remoteAppEntryLocalizationImpl.resetOriginalValues();

		return remoteAppEntryLocalizationImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();

		remoteAppEntryLocalizationId = objectInput.readLong();

		companyId = objectInput.readLong();

		remoteAppEntryId = objectInput.readLong();
		languageId = objectInput.readUTF();
		description = (String)objectInput.readObject();
		name = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(remoteAppEntryLocalizationId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(remoteAppEntryId);

		if (languageId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(languageId);
		}

		if (description == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(description);
		}

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}
	}

	public long mvccVersion;
	public long remoteAppEntryLocalizationId;
	public long companyId;
	public long remoteAppEntryId;
	public String languageId;
	public String description;
	public String name;

}