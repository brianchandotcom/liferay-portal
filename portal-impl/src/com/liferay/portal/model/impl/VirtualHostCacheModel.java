/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.kernel.model.VirtualHost;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing VirtualHost in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class VirtualHostCacheModel
	implements CacheModel<VirtualHost>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof VirtualHostCacheModel)) {
			return false;
		}

		VirtualHostCacheModel virtualHostCacheModel =
			(VirtualHostCacheModel)object;

		if ((virtualHostId == virtualHostCacheModel.virtualHostId) &&
			(mvccVersion == virtualHostCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, virtualHostId);

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
		sb.append(", virtualHostId=");
		sb.append(virtualHostId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", layoutSetId=");
		sb.append(layoutSetId);
		sb.append(", hostname=");
		sb.append(hostname);
		sb.append(", defaultVirtualHost=");
		sb.append(defaultVirtualHost);
		sb.append(", languageId=");
		sb.append(languageId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public VirtualHost toEntityModel() {
		VirtualHostImpl virtualHostImpl = new VirtualHostImpl();

		virtualHostImpl.setMvccVersion(mvccVersion);
		virtualHostImpl.setCtCollectionId(ctCollectionId);
		virtualHostImpl.setVirtualHostId(virtualHostId);
		virtualHostImpl.setCompanyId(companyId);
		virtualHostImpl.setLayoutSetId(layoutSetId);

		if (hostname == null) {
			virtualHostImpl.setHostname("");
		}
		else {
			virtualHostImpl.setHostname(hostname);
		}

		virtualHostImpl.setDefaultVirtualHost(defaultVirtualHost);

		if (languageId == null) {
			virtualHostImpl.setLanguageId("");
		}
		else {
			virtualHostImpl.setLanguageId(languageId);
		}

		virtualHostImpl.resetOriginalValues();

		return virtualHostImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();

		virtualHostId = objectInput.readLong();

		companyId = objectInput.readLong();

		layoutSetId = objectInput.readLong();
		hostname = objectInput.readUTF();

		defaultVirtualHost = objectInput.readBoolean();
		languageId = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

		objectOutput.writeLong(virtualHostId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(layoutSetId);

		if (hostname == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(hostname);
		}

		objectOutput.writeBoolean(defaultVirtualHost);

		if (languageId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(languageId);
		}
	}

	public long mvccVersion;
	public long ctCollectionId;
	public long virtualHostId;
	public long companyId;
	public long layoutSetId;
	public String hostname;
	public boolean defaultVirtualHost;
	public String languageId;

}