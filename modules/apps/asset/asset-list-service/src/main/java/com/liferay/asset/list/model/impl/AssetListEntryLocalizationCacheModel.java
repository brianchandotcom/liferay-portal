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

package com.liferay.asset.list.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.asset.list.model.AssetListEntryLocalization;

import com.liferay.petra.string.StringBundler;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.kernel.util.HashUtil;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing AssetListEntryLocalization in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see AssetListEntryLocalization
 * @generated
 */
@ProviderType
public class AssetListEntryLocalizationCacheModel implements CacheModel<AssetListEntryLocalization>,
	Externalizable, MVCCModel {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AssetListEntryLocalizationCacheModel)) {
			return false;
		}

		AssetListEntryLocalizationCacheModel assetListEntryLocalizationCacheModel =
			(AssetListEntryLocalizationCacheModel)obj;

		if ((assetListEntryLocalizationId == assetListEntryLocalizationCacheModel.assetListEntryLocalizationId) &&
				(mvccVersion == assetListEntryLocalizationCacheModel.mvccVersion)) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, assetListEntryLocalizationId);

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
		sb.append(", assetListEntryLocalizationId=");
		sb.append(assetListEntryLocalizationId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", assetListEntryId=");
		sb.append(assetListEntryId);
		sb.append(", languageId=");
		sb.append(languageId);
		sb.append(", title=");
		sb.append(title);
		sb.append(", description=");
		sb.append(description);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public AssetListEntryLocalization toEntityModel() {
		AssetListEntryLocalizationImpl assetListEntryLocalizationImpl = new AssetListEntryLocalizationImpl();

		assetListEntryLocalizationImpl.setMvccVersion(mvccVersion);
		assetListEntryLocalizationImpl.setAssetListEntryLocalizationId(assetListEntryLocalizationId);
		assetListEntryLocalizationImpl.setCompanyId(companyId);
		assetListEntryLocalizationImpl.setAssetListEntryId(assetListEntryId);

		if (languageId == null) {
			assetListEntryLocalizationImpl.setLanguageId("");
		}
		else {
			assetListEntryLocalizationImpl.setLanguageId(languageId);
		}

		if (title == null) {
			assetListEntryLocalizationImpl.setTitle("");
		}
		else {
			assetListEntryLocalizationImpl.setTitle(title);
		}

		if (description == null) {
			assetListEntryLocalizationImpl.setDescription("");
		}
		else {
			assetListEntryLocalizationImpl.setDescription(description);
		}

		assetListEntryLocalizationImpl.setGroupId(groupId);

		assetListEntryLocalizationImpl.resetOriginalValues();

		return assetListEntryLocalizationImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		assetListEntryLocalizationId = objectInput.readLong();

		companyId = objectInput.readLong();

		assetListEntryId = objectInput.readLong();
		languageId = objectInput.readUTF();
		title = objectInput.readUTF();
		description = objectInput.readUTF();

		groupId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(assetListEntryLocalizationId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(assetListEntryId);

		if (languageId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(languageId);
		}

		if (title == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(title);
		}

		if (description == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(description);
		}

		objectOutput.writeLong(groupId);
	}

	public long mvccVersion;
	public long assetListEntryLocalizationId;
	public long companyId;
	public long assetListEntryId;
	public String languageId;
	public String title;
	public String description;
	public long groupId;
}