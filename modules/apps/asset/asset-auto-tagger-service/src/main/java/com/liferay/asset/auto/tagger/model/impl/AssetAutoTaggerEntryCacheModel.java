/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.auto.tagger.model.impl;

import com.liferay.asset.auto.tagger.model.AssetAutoTaggerEntry;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing AssetAutoTaggerEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AssetAutoTaggerEntryCacheModel
	implements CacheModel<AssetAutoTaggerEntry>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof AssetAutoTaggerEntryCacheModel)) {
			return false;
		}

		AssetAutoTaggerEntryCacheModel assetAutoTaggerEntryCacheModel =
			(AssetAutoTaggerEntryCacheModel)object;

		if ((assetAutoTaggerEntryId ==
				assetAutoTaggerEntryCacheModel.assetAutoTaggerEntryId) &&
			(mvccVersion == assetAutoTaggerEntryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, assetAutoTaggerEntryId);

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
		sb.append(", assetAutoTaggerEntryId=");
		sb.append(assetAutoTaggerEntryId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", assetEntryId=");
		sb.append(assetEntryId);
		sb.append(", assetTagId=");
		sb.append(assetTagId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public AssetAutoTaggerEntry toEntityModel() {
		AssetAutoTaggerEntryImpl assetAutoTaggerEntryImpl =
			new AssetAutoTaggerEntryImpl();

		assetAutoTaggerEntryImpl.setMvccVersion(mvccVersion);
		assetAutoTaggerEntryImpl.setCtCollectionId(ctCollectionId);
		assetAutoTaggerEntryImpl.setAssetAutoTaggerEntryId(
			assetAutoTaggerEntryId);
		assetAutoTaggerEntryImpl.setGroupId(groupId);
		assetAutoTaggerEntryImpl.setCompanyId(companyId);

		if (createDate == Long.MIN_VALUE) {
			assetAutoTaggerEntryImpl.setCreateDate(null);
		}
		else {
			assetAutoTaggerEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			assetAutoTaggerEntryImpl.setModifiedDate(null);
		}
		else {
			assetAutoTaggerEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		assetAutoTaggerEntryImpl.setAssetEntryId(assetEntryId);
		assetAutoTaggerEntryImpl.setAssetTagId(assetTagId);

		assetAutoTaggerEntryImpl.resetOriginalValues();

		return assetAutoTaggerEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();

		assetAutoTaggerEntryId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		assetEntryId = objectInput.readLong();

		assetTagId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

		objectOutput.writeLong(assetAutoTaggerEntryId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);
		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		objectOutput.writeLong(assetEntryId);

		objectOutput.writeLong(assetTagId);
	}

	public long mvccVersion;
	public long ctCollectionId;
	public long assetAutoTaggerEntryId;
	public long groupId;
	public long companyId;
	public long createDate;
	public long modifiedDate;
	public long assetEntryId;
	public long assetTagId;

}