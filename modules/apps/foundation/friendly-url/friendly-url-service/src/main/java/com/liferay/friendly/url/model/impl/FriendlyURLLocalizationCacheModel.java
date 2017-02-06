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

package com.liferay.friendly.url.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.friendly.url.model.FriendlyURLLocalization;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing FriendlyURLLocalization in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see FriendlyURLLocalization
 * @generated
 */
@ProviderType
public class FriendlyURLLocalizationCacheModel implements CacheModel<FriendlyURLLocalization>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof FriendlyURLLocalizationCacheModel)) {
			return false;
		}

		FriendlyURLLocalizationCacheModel friendlyURLLocalizationCacheModel = (FriendlyURLLocalizationCacheModel)obj;

		if (friendlyURLLocalizationId == friendlyURLLocalizationCacheModel.friendlyURLLocalizationId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, friendlyURLLocalizationId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(13);

		sb.append("{friendlyURLLocalizationId=");
		sb.append(friendlyURLLocalizationId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", friendlyURLId=");
		sb.append(friendlyURLId);
		sb.append(", urlTitle=");
		sb.append(urlTitle);
		sb.append(", languageId=");
		sb.append(languageId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public FriendlyURLLocalization toEntityModel() {
		FriendlyURLLocalizationImpl friendlyURLLocalizationImpl = new FriendlyURLLocalizationImpl();

		friendlyURLLocalizationImpl.setFriendlyURLLocalizationId(friendlyURLLocalizationId);
		friendlyURLLocalizationImpl.setGroupId(groupId);
		friendlyURLLocalizationImpl.setCompanyId(companyId);
		friendlyURLLocalizationImpl.setFriendlyURLId(friendlyURLId);

		if (urlTitle == null) {
			friendlyURLLocalizationImpl.setUrlTitle(StringPool.BLANK);
		}
		else {
			friendlyURLLocalizationImpl.setUrlTitle(urlTitle);
		}

		if (languageId == null) {
			friendlyURLLocalizationImpl.setLanguageId(StringPool.BLANK);
		}
		else {
			friendlyURLLocalizationImpl.setLanguageId(languageId);
		}

		friendlyURLLocalizationImpl.resetOriginalValues();

		return friendlyURLLocalizationImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		friendlyURLLocalizationId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		friendlyURLId = objectInput.readLong();
		urlTitle = objectInput.readUTF();
		languageId = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(friendlyURLLocalizationId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(friendlyURLId);

		if (urlTitle == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(urlTitle);
		}

		if (languageId == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(languageId);
		}
	}

	public long friendlyURLLocalizationId;
	public long groupId;
	public long companyId;
	public long friendlyURLId;
	public String urlTitle;
	public String languageId;
}