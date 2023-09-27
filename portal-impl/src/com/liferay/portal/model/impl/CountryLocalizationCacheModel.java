/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.CountryLocalization;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing CountryLocalization in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class CountryLocalizationCacheModel
	implements CacheModel<CountryLocalization>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CountryLocalizationCacheModel)) {
			return false;
		}

		CountryLocalizationCacheModel countryLocalizationCacheModel =
			(CountryLocalizationCacheModel)object;

		if ((countryLocalizationId ==
				countryLocalizationCacheModel.countryLocalizationId) &&
			(mvccVersion == countryLocalizationCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, countryLocalizationId);

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
		sb.append(", countryLocalizationId=");
		sb.append(countryLocalizationId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", countryId=");
		sb.append(countryId);
		sb.append(", languageId=");
		sb.append(languageId);
		sb.append(", title=");
		sb.append(title);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CountryLocalization toEntityModel() {
		CountryLocalizationImpl countryLocalizationImpl =
			new CountryLocalizationImpl();

		countryLocalizationImpl.setMvccVersion(mvccVersion);
		countryLocalizationImpl.setCountryLocalizationId(countryLocalizationId);
		countryLocalizationImpl.setCompanyId(companyId);
		countryLocalizationImpl.setCountryId(countryId);

		if (languageId == null) {
			countryLocalizationImpl.setLanguageId("");
		}
		else {
			countryLocalizationImpl.setLanguageId(languageId);
		}

		if (title == null) {
			countryLocalizationImpl.setTitle("");
		}
		else {
			countryLocalizationImpl.setTitle(title);
		}

		countryLocalizationImpl.resetOriginalValues();

		return countryLocalizationImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		countryLocalizationId = objectInput.readLong();

		companyId = objectInput.readLong();

		countryId = objectInput.readLong();
		languageId = objectInput.readUTF();
		title = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(countryLocalizationId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(countryId);

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
	}

	public long mvccVersion;
	public long countryLocalizationId;
	public long companyId;
	public long countryId;
	public String languageId;
	public String title;

}