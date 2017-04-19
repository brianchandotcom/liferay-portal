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

package com.liferay.commerce.product.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.model.CommerceProductDefinitionLocalization;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing CommerceProductDefinitionLocalization in entity cache.
 *
 * @author Marco Leo
 * @see CommerceProductDefinitionLocalization
 * @generated
 */
@ProviderType
public class CommerceProductDefinitionLocalizationCacheModel
	implements CacheModel<CommerceProductDefinitionLocalization>,
		Externalizable, MVCCModel {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceProductDefinitionLocalizationCacheModel)) {
			return false;
		}

		CommerceProductDefinitionLocalizationCacheModel commerceProductDefinitionLocalizationCacheModel =
			(CommerceProductDefinitionLocalizationCacheModel)obj;

		if ((commerceProductDefinitionLocalizationId == commerceProductDefinitionLocalizationCacheModel.commerceProductDefinitionLocalizationId) &&
				(mvccVersion == commerceProductDefinitionLocalizationCacheModel.mvccVersion)) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, commerceProductDefinitionLocalizationId);

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
		sb.append(", commerceProductDefinitionLocalizationId=");
		sb.append(commerceProductDefinitionLocalizationId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", commerceProductDefinitionPK=");
		sb.append(commerceProductDefinitionPK);
		sb.append(", languageId=");
		sb.append(languageId);
		sb.append(", title=");
		sb.append(title);
		sb.append(", urlTitle=");
		sb.append(urlTitle);
		sb.append(", description=");
		sb.append(description);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceProductDefinitionLocalization toEntityModel() {
		CommerceProductDefinitionLocalizationImpl commerceProductDefinitionLocalizationImpl =
			new CommerceProductDefinitionLocalizationImpl();

		commerceProductDefinitionLocalizationImpl.setMvccVersion(mvccVersion);
		commerceProductDefinitionLocalizationImpl.setCommerceProductDefinitionLocalizationId(commerceProductDefinitionLocalizationId);
		commerceProductDefinitionLocalizationImpl.setCompanyId(companyId);
		commerceProductDefinitionLocalizationImpl.setCommerceProductDefinitionPK(commerceProductDefinitionPK);

		if (languageId == null) {
			commerceProductDefinitionLocalizationImpl.setLanguageId(StringPool.BLANK);
		}
		else {
			commerceProductDefinitionLocalizationImpl.setLanguageId(languageId);
		}

		if (title == null) {
			commerceProductDefinitionLocalizationImpl.setTitle(StringPool.BLANK);
		}
		else {
			commerceProductDefinitionLocalizationImpl.setTitle(title);
		}

		if (urlTitle == null) {
			commerceProductDefinitionLocalizationImpl.setUrlTitle(StringPool.BLANK);
		}
		else {
			commerceProductDefinitionLocalizationImpl.setUrlTitle(urlTitle);
		}

		if (description == null) {
			commerceProductDefinitionLocalizationImpl.setDescription(StringPool.BLANK);
		}
		else {
			commerceProductDefinitionLocalizationImpl.setDescription(description);
		}

		commerceProductDefinitionLocalizationImpl.resetOriginalValues();

		return commerceProductDefinitionLocalizationImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		commerceProductDefinitionLocalizationId = objectInput.readLong();

		companyId = objectInput.readLong();

		commerceProductDefinitionPK = objectInput.readLong();
		languageId = objectInput.readUTF();
		title = objectInput.readUTF();
		urlTitle = objectInput.readUTF();
		description = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(commerceProductDefinitionLocalizationId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(commerceProductDefinitionPK);

		if (languageId == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(languageId);
		}

		if (title == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(title);
		}

		if (urlTitle == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(urlTitle);
		}

		if (description == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(description);
		}
	}

	public long mvccVersion;
	public long commerceProductDefinitionLocalizationId;
	public long companyId;
	public long commerceProductDefinitionPK;
	public String languageId;
	public String title;
	public String urlTitle;
	public String description;
}