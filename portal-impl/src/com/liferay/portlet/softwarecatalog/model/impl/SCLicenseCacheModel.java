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

package com.liferay.portlet.softwarecatalog.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import com.liferay.portlet.softwarecatalog.model.SCLicense;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing SCLicense in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see SCLicense
 * @generated
 */
@ProviderType
public class SCLicenseCacheModel implements CacheModel<SCLicense>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SCLicenseCacheModel)) {
			return false;
		}

		SCLicenseCacheModel scLicenseCacheModel = (SCLicenseCacheModel)obj;

		if (licenseId == scLicenseCacheModel.licenseId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, licenseId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(15);

		sb.append("{licenseId=");
		sb.append(licenseId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", url=");
		sb.append(url);
		sb.append(", openSource=");
		sb.append(openSource);
		sb.append(", active=");
		sb.append(active);
		sb.append(", recommended=");
		sb.append(recommended);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public SCLicense toEntityModel() {
		SCLicenseImpl scLicenseImpl = new SCLicenseImpl();

		scLicenseImpl.setLicenseId(licenseId);

		if (name == null) {
			scLicenseImpl.setName(StringPool.BLANK);
		}
		else {
			scLicenseImpl.setName(name);
		}

		if (url == null) {
			scLicenseImpl.setUrl(StringPool.BLANK);
		}
		else {
			scLicenseImpl.setUrl(url);
		}

		scLicenseImpl.setOpenSource(openSource);
		scLicenseImpl.setActive(active);
		scLicenseImpl.setRecommended(recommended);
		scLicenseImpl.setCompanyId(companyId);

		scLicenseImpl.resetOriginalValues();

		return scLicenseImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		licenseId = objectInput.readLong();
		name = objectInput.readUTF();
		url = objectInput.readUTF();
		openSource = objectInput.readBoolean();
		active = objectInput.readBoolean();
		recommended = objectInput.readBoolean();
		companyId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(licenseId);

		if (name == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (url == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(url);
		}

		objectOutput.writeBoolean(openSource);
		objectOutput.writeBoolean(active);
		objectOutput.writeBoolean(recommended);
		objectOutput.writeLong(companyId);
	}

	public long licenseId;
	public String name;
	public String url;
	public boolean openSource;
	public boolean active;
	public boolean recommended;
	public long companyId;
}