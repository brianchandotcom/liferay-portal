/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.model.impl;

import com.liferay.commerce.product.model.CPSOListTypeDefinitionRel;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing CPSOListTypeDefinitionRel in entity cache.
 *
 * @author Marco Leo
 * @generated
 */
public class CPSOListTypeDefinitionRelCacheModel
	implements CacheModel<CPSOListTypeDefinitionRel>, Externalizable,
			   MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CPSOListTypeDefinitionRelCacheModel)) {
			return false;
		}

		CPSOListTypeDefinitionRelCacheModel
			cpsoListTypeDefinitionRelCacheModel =
				(CPSOListTypeDefinitionRelCacheModel)object;

		if ((CPSOListTypeDefinitionRelId ==
				cpsoListTypeDefinitionRelCacheModel.
					CPSOListTypeDefinitionRelId) &&
			(mvccVersion == cpsoListTypeDefinitionRelCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, CPSOListTypeDefinitionRelId);

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
		sb.append(", CPSOListTypeDefinitionRelId=");
		sb.append(CPSOListTypeDefinitionRelId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", CPSpecificationOptionId=");
		sb.append(CPSpecificationOptionId);
		sb.append(", listTypeDefinitionId=");
		sb.append(listTypeDefinitionId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CPSOListTypeDefinitionRel toEntityModel() {
		CPSOListTypeDefinitionRelImpl cpsoListTypeDefinitionRelImpl =
			new CPSOListTypeDefinitionRelImpl();

		cpsoListTypeDefinitionRelImpl.setMvccVersion(mvccVersion);
		cpsoListTypeDefinitionRelImpl.setCtCollectionId(ctCollectionId);
		cpsoListTypeDefinitionRelImpl.setCPSOListTypeDefinitionRelId(
			CPSOListTypeDefinitionRelId);
		cpsoListTypeDefinitionRelImpl.setCompanyId(companyId);
		cpsoListTypeDefinitionRelImpl.setCPSpecificationOptionId(
			CPSpecificationOptionId);
		cpsoListTypeDefinitionRelImpl.setListTypeDefinitionId(
			listTypeDefinitionId);

		cpsoListTypeDefinitionRelImpl.resetOriginalValues();

		return cpsoListTypeDefinitionRelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();

		CPSOListTypeDefinitionRelId = objectInput.readLong();

		companyId = objectInput.readLong();

		CPSpecificationOptionId = objectInput.readLong();

		listTypeDefinitionId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

		objectOutput.writeLong(CPSOListTypeDefinitionRelId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(CPSpecificationOptionId);

		objectOutput.writeLong(listTypeDefinitionId);
	}

	public long mvccVersion;
	public long ctCollectionId;
	public long CPSOListTypeDefinitionRelId;
	public long companyId;
	public long CPSpecificationOptionId;
	public long listTypeDefinitionId;

}