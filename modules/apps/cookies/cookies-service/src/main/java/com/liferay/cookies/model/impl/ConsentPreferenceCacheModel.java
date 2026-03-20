/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.cookies.model.impl;

import com.liferay.cookies.model.ConsentPreference;
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
 * The cache model class for representing ConsentPreference in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class ConsentPreferenceCacheModel
	implements CacheModel<ConsentPreference>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ConsentPreferenceCacheModel)) {
			return false;
		}

		ConsentPreferenceCacheModel consentPreferenceCacheModel =
			(ConsentPreferenceCacheModel)object;

		if ((consentPreferenceId ==
				consentPreferenceCacheModel.consentPreferenceId) &&
			(mvccVersion == consentPreferenceCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, consentPreferenceId);

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
		sb.append(", consentPreferenceId=");
		sb.append(consentPreferenceId);
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
	public ConsentPreference toEntityModel() {
		ConsentPreferenceImpl consentPreferenceImpl =
			new ConsentPreferenceImpl();

		consentPreferenceImpl.setMvccVersion(mvccVersion);
		consentPreferenceImpl.setConsentPreferenceId(consentPreferenceId);
		consentPreferenceImpl.setCompanyId(companyId);
		consentPreferenceImpl.setUserId(userId);

		if (userName == null) {
			consentPreferenceImpl.setUserName("");
		}
		else {
			consentPreferenceImpl.setUserName(userName);
		}

		if (domain == null) {
			consentPreferenceImpl.setDomain("");
		}
		else {
			consentPreferenceImpl.setDomain(domain);
		}

		if (expirationDate == Long.MIN_VALUE) {
			consentPreferenceImpl.setExpirationDate(null);
		}
		else {
			consentPreferenceImpl.setExpirationDate(new Date(expirationDate));
		}

		if (name == null) {
			consentPreferenceImpl.setName("");
		}
		else {
			consentPreferenceImpl.setName(name);
		}

		if (value == null) {
			consentPreferenceImpl.setValue("");
		}
		else {
			consentPreferenceImpl.setValue(value);
		}

		consentPreferenceImpl.resetOriginalValues();

		return consentPreferenceImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		consentPreferenceId = objectInput.readLong();

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

		objectOutput.writeLong(consentPreferenceId);

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
	public long consentPreferenceId;
	public long companyId;
	public long userId;
	public String userName;
	public String domain;
	public long expirationDate;
	public String name;
	public String value;

}
// LIFERAY-SERVICE-BUILDER-HASH:2006989661