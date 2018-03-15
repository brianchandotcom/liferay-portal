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

package com.liferay.oauth2.provider.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.oauth2.provider.model.OAuth2ScopeGrant;
import com.liferay.oauth2.provider.service.persistence.OAuth2ScopeGrantPK;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing OAuth2ScopeGrant in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see OAuth2ScopeGrant
 * @generated
 */
@ProviderType
public class OAuth2ScopeGrantCacheModel implements CacheModel<OAuth2ScopeGrant>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof OAuth2ScopeGrantCacheModel)) {
			return false;
		}

		OAuth2ScopeGrantCacheModel oAuth2ScopeGrantCacheModel = (OAuth2ScopeGrantCacheModel)obj;

		if (oAuth2ScopeGrantPK.equals(
					oAuth2ScopeGrantCacheModel.oAuth2ScopeGrantPK)) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, oAuth2ScopeGrantPK);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(13);

		sb.append("{applicationName=");
		sb.append(applicationName);
		sb.append(", bundleSymbolicName=");
		sb.append(bundleSymbolicName);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", oAuth2ScopeName=");
		sb.append(oAuth2ScopeName);
		sb.append(", oAuth2TokenId=");
		sb.append(oAuth2TokenId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public OAuth2ScopeGrant toEntityModel() {
		OAuth2ScopeGrantImpl oAuth2ScopeGrantImpl = new OAuth2ScopeGrantImpl();

		if (applicationName == null) {
			oAuth2ScopeGrantImpl.setApplicationName("");
		}
		else {
			oAuth2ScopeGrantImpl.setApplicationName(applicationName);
		}

		if (bundleSymbolicName == null) {
			oAuth2ScopeGrantImpl.setBundleSymbolicName("");
		}
		else {
			oAuth2ScopeGrantImpl.setBundleSymbolicName(bundleSymbolicName);
		}

		oAuth2ScopeGrantImpl.setCompanyId(companyId);

		if (oAuth2ScopeName == null) {
			oAuth2ScopeGrantImpl.setOAuth2ScopeName("");
		}
		else {
			oAuth2ScopeGrantImpl.setOAuth2ScopeName(oAuth2ScopeName);
		}

		oAuth2ScopeGrantImpl.setOAuth2TokenId(oAuth2TokenId);

		if (createDate == Long.MIN_VALUE) {
			oAuth2ScopeGrantImpl.setCreateDate(null);
		}
		else {
			oAuth2ScopeGrantImpl.setCreateDate(new Date(createDate));
		}

		oAuth2ScopeGrantImpl.resetOriginalValues();

		return oAuth2ScopeGrantImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		applicationName = objectInput.readUTF();
		bundleSymbolicName = objectInput.readUTF();

		companyId = objectInput.readLong();
		oAuth2ScopeName = objectInput.readUTF();

		oAuth2TokenId = objectInput.readLong();
		createDate = objectInput.readLong();

		oAuth2ScopeGrantPK = new OAuth2ScopeGrantPK(applicationName,
				bundleSymbolicName, companyId, oAuth2ScopeName, oAuth2TokenId);
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		if (applicationName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(applicationName);
		}

		if (bundleSymbolicName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(bundleSymbolicName);
		}

		objectOutput.writeLong(companyId);

		if (oAuth2ScopeName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(oAuth2ScopeName);
		}

		objectOutput.writeLong(oAuth2TokenId);
		objectOutput.writeLong(createDate);
	}

	public String applicationName;
	public String bundleSymbolicName;
	public long companyId;
	public String oAuth2ScopeName;
	public long oAuth2TokenId;
	public long createDate;
	public transient OAuth2ScopeGrantPK oAuth2ScopeGrantPK;
}