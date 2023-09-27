/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof OAuth2ScopeGrantCacheModel)) {
			return false;
		}

		OAuth2ScopeGrantCacheModel oAuth2ScopeGrantCacheModel =
			(OAuth2ScopeGrantCacheModel)object;

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
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		oAuth2ScopeGrantId = objectInput.readLong();

		companyId = objectInput.readLong();

		oAuth2ApplicationScopeAliasesId = objectInput.readLong();
		applicationName = objectInput.readUTF();
		bundleSymbolicName = objectInput.readUTF();
		scope = objectInput.readUTF();
		scopeAliases = (String)objectInput.readObject();
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
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(scopeAliases);
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