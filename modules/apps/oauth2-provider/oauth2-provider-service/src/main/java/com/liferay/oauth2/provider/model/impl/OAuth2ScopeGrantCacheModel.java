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

import com.liferay.oauth2.provider.model.OAuth2ScopeGrant;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing OAuth2ScopeGrant in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class OAuth2ScopeGrantCacheModel
	implements CacheModel<OAuth2ScopeGrant>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof OAuth2ScopeGrantCacheModel)) {
			return false;
		}

		OAuth2ScopeGrantCacheModel oAuth2ScopeGrantCacheModel =
			(OAuth2ScopeGrantCacheModel)obj;

		if (oAuth2ScopeGrantId ==
				oAuth2ScopeGrantCacheModel.oAuth2ScopeGrantId) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, oAuth2ScopeGrantId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(15);

		sb.append("{oAuth2ScopeGrantId=");
		sb.append(oAuth2ScopeGrantId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", oAuth2ApplicationScopeAliasesId=");
		sb.append(oAuth2ApplicationScopeAliasesId);
		sb.append(", applicationName=");
		sb.append(applicationName);
		sb.append(", bundleSymbolicName=");
		sb.append(bundleSymbolicName);
		sb.append(", scope=");
		sb.append(scope);
		sb.append(", scopeAliases=");
		sb.append(scopeAliases);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public OAuth2ScopeGrant toEntityModel() {
		OAuth2ScopeGrantImpl oAuth2ScopeGrantImpl = new OAuth2ScopeGrantImpl();

		oAuth2ScopeGrantImpl.setOAuth2ScopeGrantId(oAuth2ScopeGrantId);
		oAuth2ScopeGrantImpl.setCompanyId(companyId);
		oAuth2ScopeGrantImpl.setOAuth2ApplicationScopeAliasesId(
			oAuth2ApplicationScopeAliasesId);

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

		if (scope == null) {
			oAuth2ScopeGrantImpl.setScope("");
		}
		else {
			oAuth2ScopeGrantImpl.setScope(scope);
		}

		if (scopeAliases == null) {
			oAuth2ScopeGrantImpl.setScopeAliases("");
		}
		else {
			oAuth2ScopeGrantImpl.setScopeAliases(scopeAliases);
		}

		oAuth2ScopeGrantImpl.resetOriginalValues();

		return oAuth2ScopeGrantImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		oAuth2ScopeGrantId = objectInput.readLong();

		companyId = objectInput.readLong();

		oAuth2ApplicationScopeAliasesId = objectInput.readLong();
		applicationName = objectInput.readUTF();
		bundleSymbolicName = objectInput.readUTF();
		scope = objectInput.readUTF();
		scopeAliases = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(oAuth2ScopeGrantId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(oAuth2ApplicationScopeAliasesId);

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

		if (scope == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(scope);
		}

		if (scopeAliases == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(scopeAliases);
		}
	}

	public long oAuth2ScopeGrantId;
	public long companyId;
	public long oAuth2ApplicationScopeAliasesId;
	public String applicationName;
	public String bundleSymbolicName;
	public String scope;
	public String scopeAliases;

}