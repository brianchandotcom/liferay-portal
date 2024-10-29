/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.tools.service.builder.test.model.IndexEntry;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing IndexEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class IndexEntryCacheModel
	implements CacheModel<IndexEntry>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof IndexEntryCacheModel)) {
			return false;
		}

		IndexEntryCacheModel indexEntryCacheModel =
			(IndexEntryCacheModel)object;

		if ((indexEntryId == indexEntryCacheModel.indexEntryId) &&
			(mvccVersion == indexEntryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, indexEntryId);

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
		StringBundler sb = new StringBundler(19);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", externalReferenceCode=");
		sb.append(externalReferenceCode);
		sb.append(", indexEntryId=");
		sb.append(indexEntryId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", ownerId=");
		sb.append(ownerId);
		sb.append(", ownerType=");
		sb.append(ownerType);
		sb.append(", plid=");
		sb.append(plid);
		sb.append(", portletId=");
		sb.append(portletId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public IndexEntry toEntityModel() {
		IndexEntryImpl indexEntryImpl = new IndexEntryImpl();

		indexEntryImpl.setMvccVersion(mvccVersion);
		indexEntryImpl.setCtCollectionId(ctCollectionId);

		if (externalReferenceCode == null) {
			indexEntryImpl.setExternalReferenceCode("");
		}
		else {
			indexEntryImpl.setExternalReferenceCode(externalReferenceCode);
		}

		indexEntryImpl.setIndexEntryId(indexEntryId);
		indexEntryImpl.setCompanyId(companyId);
		indexEntryImpl.setOwnerId(ownerId);
		indexEntryImpl.setOwnerType(ownerType);
		indexEntryImpl.setPlid(plid);

		if (portletId == null) {
			indexEntryImpl.setPortletId("");
		}
		else {
			indexEntryImpl.setPortletId(portletId);
		}

		indexEntryImpl.resetOriginalValues();

		return indexEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();
		externalReferenceCode = objectInput.readUTF();

		indexEntryId = objectInput.readLong();

		companyId = objectInput.readLong();

		ownerId = objectInput.readLong();

		ownerType = objectInput.readInt();

		plid = objectInput.readLong();
		portletId = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

		if (externalReferenceCode == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(externalReferenceCode);
		}

		objectOutput.writeLong(indexEntryId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(ownerId);

		objectOutput.writeInt(ownerType);

		objectOutput.writeLong(plid);

		if (portletId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(portletId);
		}
	}

	public long mvccVersion;
	public long ctCollectionId;
	public String externalReferenceCode;
	public long indexEntryId;
	public long companyId;
	public long ownerId;
	public int ownerType;
	public long plid;
	public String portletId;

}