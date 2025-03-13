/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.asset.model.impl;

import com.liferay.asset.kernel.model.AssetTagGroupRel;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing AssetTagGroupRel in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AssetTagGroupRelCacheModel
	implements CacheModel<AssetTagGroupRel>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof AssetTagGroupRelCacheModel)) {
			return false;
		}

		AssetTagGroupRelCacheModel assetTagGroupRelCacheModel =
			(AssetTagGroupRelCacheModel)object;

		if ((assetTagGroupRelId ==
				assetTagGroupRelCacheModel.assetTagGroupRelId) &&
			(mvccVersion == assetTagGroupRelCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, assetTagGroupRelId);

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
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", assetTagGroupRelId=");
		sb.append(assetTagGroupRelId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", tagId=");
		sb.append(tagId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public AssetTagGroupRel toEntityModel() {
		AssetTagGroupRelImpl assetTagGroupRelImpl = new AssetTagGroupRelImpl();

		assetTagGroupRelImpl.setMvccVersion(mvccVersion);
		assetTagGroupRelImpl.setCtCollectionId(ctCollectionId);

		if (uuid == null) {
			assetTagGroupRelImpl.setUuid("");
		}
		else {
			assetTagGroupRelImpl.setUuid(uuid);
		}

		assetTagGroupRelImpl.setAssetTagGroupRelId(assetTagGroupRelId);
		assetTagGroupRelImpl.setGroupId(groupId);
		assetTagGroupRelImpl.setCompanyId(companyId);
		assetTagGroupRelImpl.setTagId(tagId);

		assetTagGroupRelImpl.resetOriginalValues();

		return assetTagGroupRelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();
		uuid = objectInput.readUTF();

		assetTagGroupRelId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		tagId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(assetTagGroupRelId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(tagId);
	}

	public long mvccVersion;
	public long ctCollectionId;
	public String uuid;
	public long assetTagGroupRelId;
	public long groupId;
	public long companyId;
	public long tagId;

}