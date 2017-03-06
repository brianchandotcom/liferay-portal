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

import com.liferay.friendly.url.model.FriendlyURLEntryLocalization;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing FriendlyURLEntryLocalization in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see FriendlyURLEntryLocalization
 * @generated
 */
@ProviderType
public class FriendlyURLEntryLocalizationCacheModel implements CacheModel<FriendlyURLEntryLocalization>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof FriendlyURLEntryLocalizationCacheModel)) {
			return false;
		}

		FriendlyURLEntryLocalizationCacheModel friendlyURLEntryLocalizationCacheModel =
			(FriendlyURLEntryLocalizationCacheModel)obj;

		if (friendlyURLEntryLocalizationId == friendlyURLEntryLocalizationCacheModel.friendlyURLEntryLocalizationId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, friendlyURLEntryLocalizationId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(13);

		sb.append("{friendlyURLEntryLocalizationId=");
		sb.append(friendlyURLEntryLocalizationId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", friendlyURLEntryId=");
		sb.append(friendlyURLEntryId);
		sb.append(", urlTitle=");
		sb.append(urlTitle);
		sb.append(", languageId=");
		sb.append(languageId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public FriendlyURLEntryLocalization toEntityModel() {
		FriendlyURLEntryLocalizationImpl friendlyURLEntryLocalizationImpl = new FriendlyURLEntryLocalizationImpl();

		friendlyURLEntryLocalizationImpl.setFriendlyURLEntryLocalizationId(friendlyURLEntryLocalizationId);
		friendlyURLEntryLocalizationImpl.setGroupId(groupId);
		friendlyURLEntryLocalizationImpl.setCompanyId(companyId);
		friendlyURLEntryLocalizationImpl.setFriendlyURLEntryId(friendlyURLEntryId);

		if (urlTitle == null) {
			friendlyURLEntryLocalizationImpl.setUrlTitle(StringPool.BLANK);
		}
		else {
			friendlyURLEntryLocalizationImpl.setUrlTitle(urlTitle);
		}

		if (languageId == null) {
			friendlyURLEntryLocalizationImpl.setLanguageId(StringPool.BLANK);
		}
		else {
			friendlyURLEntryLocalizationImpl.setLanguageId(languageId);
		}

		friendlyURLEntryLocalizationImpl.resetOriginalValues();

		return friendlyURLEntryLocalizationImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		friendlyURLEntryLocalizationId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		friendlyURLEntryId = objectInput.readLong();
		urlTitle = objectInput.readUTF();
		languageId = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(friendlyURLEntryLocalizationId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(friendlyURLEntryId);

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

	public long friendlyURLEntryLocalizationId;
	public long groupId;
	public long companyId;
	public long friendlyURLEntryId;
	public String urlTitle;
	public String languageId;
}