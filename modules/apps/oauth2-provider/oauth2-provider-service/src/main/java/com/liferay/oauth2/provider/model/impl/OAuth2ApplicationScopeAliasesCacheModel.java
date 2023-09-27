/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.model.impl;

import com.liferay.oauth2.provider.model.OAuth2ApplicationScopeAliases;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing OAuth2ApplicationScopeAliases in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class OAuth2ApplicationScopeAliasesCacheModel
	implements CacheModel<OAuth2ApplicationScopeAliases>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof OAuth2ApplicationScopeAliasesCacheModel)) {
			return false;
		}

		OAuth2ApplicationScopeAliasesCacheModel
			oAuth2ApplicationScopeAliasesCacheModel =
				(OAuth2ApplicationScopeAliasesCacheModel)object;

		if (oAuth2ApplicationScopeAliasesId ==
				oAuth2ApplicationScopeAliasesCacheModel.
					oAuth2ApplicationScopeAliasesId) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, oAuth2ApplicationScopeAliasesId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(13);

		sb.append("{oAuth2ApplicationScopeAliasesId=");
		sb.append(oAuth2ApplicationScopeAliasesId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", oAuth2ApplicationId=");
		sb.append(oAuth2ApplicationId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public OAuth2ApplicationScopeAliases toEntityModel() {
		OAuth2ApplicationScopeAliasesImpl oAuth2ApplicationScopeAliasesImpl =
			new OAuth2ApplicationScopeAliasesImpl();

		oAuth2ApplicationScopeAliasesImpl.setOAuth2ApplicationScopeAliasesId(
			oAuth2ApplicationScopeAliasesId);
		oAuth2ApplicationScopeAliasesImpl.setCompanyId(companyId);
		oAuth2ApplicationScopeAliasesImpl.setUserId(userId);

		if (userName == null) {
			oAuth2ApplicationScopeAliasesImpl.setUserName("");
		}
		else {
			oAuth2ApplicationScopeAliasesImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			oAuth2ApplicationScopeAliasesImpl.setCreateDate(null);
		}
		else {
			oAuth2ApplicationScopeAliasesImpl.setCreateDate(
				new Date(createDate));
		}

		oAuth2ApplicationScopeAliasesImpl.setOAuth2ApplicationId(
			oAuth2ApplicationId);

		oAuth2ApplicationScopeAliasesImpl.resetOriginalValues();

		return oAuth2ApplicationScopeAliasesImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		oAuth2ApplicationScopeAliasesId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();

		oAuth2ApplicationId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(oAuth2ApplicationScopeAliasesId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);

		objectOutput.writeLong(oAuth2ApplicationId);
	}

	public long oAuth2ApplicationScopeAliasesId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long oAuth2ApplicationId;

}