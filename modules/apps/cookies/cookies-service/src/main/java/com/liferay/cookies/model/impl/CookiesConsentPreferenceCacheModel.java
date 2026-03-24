/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.cookies.model.impl;

import com.liferay.cookies.model.CookiesConsentPreference;
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
 * The cache model class for representing CookiesConsentPreference in entity cache.
 *
 * @author Christopher Kian
 * @generated
 */
public class CookiesConsentPreferenceCacheModel
	implements CacheModel<CookiesConsentPreference>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CookiesConsentPreferenceCacheModel)) {
			return false;
		}

		CookiesConsentPreferenceCacheModel cookiesConsentPreferenceCacheModel =
			(CookiesConsentPreferenceCacheModel)object;

		if ((cookiesConsentPreferenceId ==
				cookiesConsentPreferenceCacheModel.
					cookiesConsentPreferenceId) &&
			(mvccVersion == cookiesConsentPreferenceCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, cookiesConsentPreferenceId);

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
		sb.append(", cookiesConsentPreferenceId=");
		sb.append(cookiesConsentPreferenceId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", domain=");
		sb.append(domain);
		sb.append(", expirationDate=");
		sb.append(expirationDate);
		sb.append(", name=");
		sb.append(name);
		sb.append(", value=");
		sb.append(value);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CookiesConsentPreference toEntityModel() {
		CookiesConsentPreferenceImpl cookiesConsentPreferenceImpl =
			new CookiesConsentPreferenceImpl();

		cookiesConsentPreferenceImpl.setMvccVersion(mvccVersion);
		cookiesConsentPreferenceImpl.setCookiesConsentPreferenceId(
			cookiesConsentPreferenceId);
		cookiesConsentPreferenceImpl.setCompanyId(companyId);
		cookiesConsentPreferenceImpl.setUserId(userId);

		if (userName == null) {
			cookiesConsentPreferenceImpl.setUserName("");
		}
		else {
			cookiesConsentPreferenceImpl.setUserName(userName);
		}

		if (domain == null) {
			cookiesConsentPreferenceImpl.setDomain("");
		}
		else {
			cookiesConsentPreferenceImpl.setDomain(domain);
		}

		if (expirationDate == Long.MIN_VALUE) {
			cookiesConsentPreferenceImpl.setExpirationDate(null);
		}
		else {
			cookiesConsentPreferenceImpl.setExpirationDate(
				new Date(expirationDate));
		}

		if (name == null) {
			cookiesConsentPreferenceImpl.setName("");
		}
		else {
			cookiesConsentPreferenceImpl.setName(name);
		}

		if (value == null) {
			cookiesConsentPreferenceImpl.setValue("");
		}
		else {
			cookiesConsentPreferenceImpl.setValue(value);
		}

		cookiesConsentPreferenceImpl.resetOriginalValues();

		return cookiesConsentPreferenceImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		cookiesConsentPreferenceId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		domain = objectInput.readUTF();
		expirationDate = objectInput.readLong();
		name = objectInput.readUTF();
		value = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(cookiesConsentPreferenceId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(userName);
		}

		if (domain == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(domain);
		}

		objectOutput.writeLong(expirationDate);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (value == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(value);
		}
	}

	public long mvccVersion;
	public long cookiesConsentPreferenceId;
	public long companyId;
	public long userId;
	public String userName;
	public String domain;
	public long expirationDate;
	public String name;
	public String value;

}
// LIFERAY-SERVICE-BUILDER-HASH:1354805450