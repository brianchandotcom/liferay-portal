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

import com.liferay.friendly.url.model.FriendlyURLTitleLocalization;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing FriendlyURLTitleLocalization in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see FriendlyURLTitleLocalization
 * @generated
 */
@ProviderType
public class FriendlyURLTitleLocalizationCacheModel implements CacheModel<FriendlyURLTitleLocalization>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof FriendlyURLTitleLocalizationCacheModel)) {
			return false;
		}

		FriendlyURLTitleLocalizationCacheModel friendlyURLTitleLocalizationCacheModel =
			(FriendlyURLTitleLocalizationCacheModel)obj;

		if (friendlyURLTitleLocalizationId == friendlyURLTitleLocalizationCacheModel.friendlyURLTitleLocalizationId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, friendlyURLTitleLocalizationId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(13);

		sb.append("{friendlyURLTitleLocalizationId=");
		sb.append(friendlyURLTitleLocalizationId);
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
	public FriendlyURLTitleLocalization toEntityModel() {
		FriendlyURLTitleLocalizationImpl friendlyURLTitleLocalizationImpl = new FriendlyURLTitleLocalizationImpl();

		friendlyURLTitleLocalizationImpl.setFriendlyURLTitleLocalizationId(friendlyURLTitleLocalizationId);
		friendlyURLTitleLocalizationImpl.setGroupId(groupId);
		friendlyURLTitleLocalizationImpl.setCompanyId(companyId);
		friendlyURLTitleLocalizationImpl.setFriendlyURLId(friendlyURLId);

		if (urlTitle == null) {
			friendlyURLTitleLocalizationImpl.setUrlTitle(StringPool.BLANK);
		}
		else {
			friendlyURLTitleLocalizationImpl.setUrlTitle(urlTitle);
		}

		if (languageId == null) {
			friendlyURLTitleLocalizationImpl.setLanguageId(StringPool.BLANK);
		}
		else {
			friendlyURLTitleLocalizationImpl.setLanguageId(languageId);
		}

		friendlyURLTitleLocalizationImpl.resetOriginalValues();

		return friendlyURLTitleLocalizationImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		friendlyURLTitleLocalizationId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		friendlyURLId = objectInput.readLong();
		urlTitle = objectInput.readUTF();
		languageId = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(friendlyURLTitleLocalizationId);

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

	public long friendlyURLTitleLocalizationId;
	public long groupId;
	public long companyId;
	public long friendlyURLId;
	public String urlTitle;
	public String languageId;
}