/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.asset.model.impl;

import com.liferay.asset.kernel.model.AssetVocabularyGroupRel;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing AssetVocabularyGroupRel in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AssetVocabularyGroupRelCacheModel
	implements CacheModel<AssetVocabularyGroupRel>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof AssetVocabularyGroupRelCacheModel)) {
			return false;
		}

		AssetVocabularyGroupRelCacheModel assetVocabularyGroupRelCacheModel =
			(AssetVocabularyGroupRelCacheModel)object;

		if ((assetVocabularyGroupRelId ==
				assetVocabularyGroupRelCacheModel.assetVocabularyGroupRelId) &&
			(mvccVersion == assetVocabularyGroupRelCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, assetVocabularyGroupRelId);

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
		sb.append(", assetVocabularyGroupRelId=");
		sb.append(assetVocabularyGroupRelId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", vocabularyId=");
		sb.append(vocabularyId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public AssetVocabularyGroupRel toEntityModel() {
		AssetVocabularyGroupRelImpl assetVocabularyGroupRelImpl =
			new AssetVocabularyGroupRelImpl();

		assetVocabularyGroupRelImpl.setMvccVersion(mvccVersion);
		assetVocabularyGroupRelImpl.setCtCollectionId(ctCollectionId);

		if (uuid == null) {
			assetVocabularyGroupRelImpl.setUuid("");
		}
		else {
			assetVocabularyGroupRelImpl.setUuid(uuid);
		}

		assetVocabularyGroupRelImpl.setAssetVocabularyGroupRelId(
			assetVocabularyGroupRelId);
		assetVocabularyGroupRelImpl.setGroupId(groupId);
		assetVocabularyGroupRelImpl.setCompanyId(companyId);
		assetVocabularyGroupRelImpl.setVocabularyId(vocabularyId);

		assetVocabularyGroupRelImpl.resetOriginalValues();

		return assetVocabularyGroupRelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();
		uuid = objectInput.readUTF();

		assetVocabularyGroupRelId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		vocabularyId = objectInput.readLong();
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

		objectOutput.writeLong(assetVocabularyGroupRelId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(vocabularyId);
	}

	public long mvccVersion;
	public long ctCollectionId;
	public String uuid;
	public long assetVocabularyGroupRelId;
	public long groupId;
	public long companyId;
	public long vocabularyId;

}
// LIFERAY-SERVICE-BUILDER-HASH:282957432