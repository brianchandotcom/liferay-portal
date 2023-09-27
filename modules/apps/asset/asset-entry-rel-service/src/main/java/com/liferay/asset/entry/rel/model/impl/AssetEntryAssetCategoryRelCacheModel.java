/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.entry.rel.model.impl;

import com.liferay.asset.entry.rel.model.AssetEntryAssetCategoryRel;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing AssetEntryAssetCategoryRel in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AssetEntryAssetCategoryRelCacheModel
	implements CacheModel<AssetEntryAssetCategoryRel>, Externalizable,
			   MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof AssetEntryAssetCategoryRelCacheModel)) {
			return false;
		}

		AssetEntryAssetCategoryRelCacheModel
			assetEntryAssetCategoryRelCacheModel =
				(AssetEntryAssetCategoryRelCacheModel)object;

		if ((assetEntryAssetCategoryRelId ==
				assetEntryAssetCategoryRelCacheModel.
					assetEntryAssetCategoryRelId) &&
			(mvccVersion == assetEntryAssetCategoryRelCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, assetEntryAssetCategoryRelId);

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
		sb.append(", assetEntryAssetCategoryRelId=");
		sb.append(assetEntryAssetCategoryRelId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", assetEntryId=");
		sb.append(assetEntryId);
		sb.append(", assetCategoryId=");
		sb.append(assetCategoryId);
		sb.append(", priority=");
		sb.append(priority);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public AssetEntryAssetCategoryRel toEntityModel() {
		AssetEntryAssetCategoryRelImpl assetEntryAssetCategoryRelImpl =
			new AssetEntryAssetCategoryRelImpl();

		assetEntryAssetCategoryRelImpl.setMvccVersion(mvccVersion);
		assetEntryAssetCategoryRelImpl.setCtCollectionId(ctCollectionId);
		assetEntryAssetCategoryRelImpl.setAssetEntryAssetCategoryRelId(
			assetEntryAssetCategoryRelId);
		assetEntryAssetCategoryRelImpl.setCompanyId(companyId);
		assetEntryAssetCategoryRelImpl.setAssetEntryId(assetEntryId);
		assetEntryAssetCategoryRelImpl.setAssetCategoryId(assetCategoryId);
		assetEntryAssetCategoryRelImpl.setPriority(priority);

		assetEntryAssetCategoryRelImpl.resetOriginalValues();

		return assetEntryAssetCategoryRelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();

		assetEntryAssetCategoryRelId = objectInput.readLong();

		companyId = objectInput.readLong();

		assetEntryId = objectInput.readLong();

		assetCategoryId = objectInput.readLong();

		priority = objectInput.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

		objectOutput.writeLong(assetEntryAssetCategoryRelId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(assetEntryId);

		objectOutput.writeLong(assetCategoryId);

		objectOutput.writeInt(priority);
	}

	public long mvccVersion;
	public long ctCollectionId;
	public long assetEntryAssetCategoryRelId;
	public long companyId;
	public long assetEntryId;
	public long assetCategoryId;
	public int priority;

}