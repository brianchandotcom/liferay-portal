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

package com.liferay.commerce.term.model.impl;

import com.liferay.commerce.term.model.CTermEntryLocalization;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing CTermEntryLocalization in entity cache.
 *
 * @author Luca Pellizzon
 * @generated
 */
public class CTermEntryLocalizationCacheModel
	implements CacheModel<CTermEntryLocalization>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CTermEntryLocalizationCacheModel)) {
			return false;
		}

		CTermEntryLocalizationCacheModel cTermEntryLocalizationCacheModel =
			(CTermEntryLocalizationCacheModel)object;

		if ((cTermEntryLocalizationId ==
				cTermEntryLocalizationCacheModel.cTermEntryLocalizationId) &&
			(mvccVersion == cTermEntryLocalizationCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, cTermEntryLocalizationId);

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
		sb.append(", cTermEntryLocalizationId=");
		sb.append(cTermEntryLocalizationId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", commerceTermEntryId=");
		sb.append(commerceTermEntryId);
		sb.append(", languageId=");
		sb.append(languageId);
		sb.append(", description=");
		sb.append(description);
		sb.append(", label=");
		sb.append(label);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CTermEntryLocalization toEntityModel() {
		CTermEntryLocalizationImpl cTermEntryLocalizationImpl =
			new CTermEntryLocalizationImpl();

		cTermEntryLocalizationImpl.setMvccVersion(mvccVersion);
		cTermEntryLocalizationImpl.setCTermEntryLocalizationId(
			cTermEntryLocalizationId);
		cTermEntryLocalizationImpl.setCompanyId(companyId);
		cTermEntryLocalizationImpl.setCommerceTermEntryId(commerceTermEntryId);

		if (languageId == null) {
			cTermEntryLocalizationImpl.setLanguageId("");
		}
		else {
			cTermEntryLocalizationImpl.setLanguageId(languageId);
		}

		if (description == null) {
			cTermEntryLocalizationImpl.setDescription("");
		}
		else {
			cTermEntryLocalizationImpl.setDescription(description);
		}

		if (label == null) {
			cTermEntryLocalizationImpl.setLabel("");
		}
		else {
			cTermEntryLocalizationImpl.setLabel(label);
		}

		cTermEntryLocalizationImpl.resetOriginalValues();

		return cTermEntryLocalizationImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();

		cTermEntryLocalizationId = objectInput.readLong();

		companyId = objectInput.readLong();

		commerceTermEntryId = objectInput.readLong();
		languageId = objectInput.readUTF();
		description = (String)objectInput.readObject();
		label = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(cTermEntryLocalizationId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(commerceTermEntryId);

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

		if (label == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(label);
		}
	}

	public long mvccVersion;
	public long cTermEntryLocalizationId;
	public long companyId;
	public long commerceTermEntryId;
	public String languageId;
	public String description;
	public String label;

}