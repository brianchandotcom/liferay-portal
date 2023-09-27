/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.model.impl;

import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstanceLink;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing DDMDataProviderInstanceLink in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DDMDataProviderInstanceLinkCacheModel
	implements CacheModel<DDMDataProviderInstanceLink>, Externalizable,
			   MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DDMDataProviderInstanceLinkCacheModel)) {
			return false;
		}

		DDMDataProviderInstanceLinkCacheModel
			ddmDataProviderInstanceLinkCacheModel =
				(DDMDataProviderInstanceLinkCacheModel)object;

		if ((dataProviderInstanceLinkId ==
				ddmDataProviderInstanceLinkCacheModel.
					dataProviderInstanceLinkId) &&
			(mvccVersion ==
				ddmDataProviderInstanceLinkCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, dataProviderInstanceLinkId);

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
		StringBundler sb = new StringBundler(13);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", dataProviderInstanceLinkId=");
		sb.append(dataProviderInstanceLinkId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", dataProviderInstanceId=");
		sb.append(dataProviderInstanceId);
		sb.append(", structureId=");
		sb.append(structureId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public DDMDataProviderInstanceLink toEntityModel() {
		DDMDataProviderInstanceLinkImpl ddmDataProviderInstanceLinkImpl =
			new DDMDataProviderInstanceLinkImpl();

		ddmDataProviderInstanceLinkImpl.setMvccVersion(mvccVersion);
		ddmDataProviderInstanceLinkImpl.setCtCollectionId(ctCollectionId);
		ddmDataProviderInstanceLinkImpl.setDataProviderInstanceLinkId(
			dataProviderInstanceLinkId);
		ddmDataProviderInstanceLinkImpl.setCompanyId(companyId);
		ddmDataProviderInstanceLinkImpl.setDataProviderInstanceId(
			dataProviderInstanceId);
		ddmDataProviderInstanceLinkImpl.setStructureId(structureId);

		ddmDataProviderInstanceLinkImpl.resetOriginalValues();

		return ddmDataProviderInstanceLinkImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();

		dataProviderInstanceLinkId = objectInput.readLong();

		companyId = objectInput.readLong();

		dataProviderInstanceId = objectInput.readLong();

		structureId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

		objectOutput.writeLong(dataProviderInstanceLinkId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(dataProviderInstanceId);

		objectOutput.writeLong(structureId);
	}

	public long mvccVersion;
	public long ctCollectionId;
	public long dataProviderInstanceLinkId;
	public long companyId;
	public long dataProviderInstanceId;
	public long structureId;

}