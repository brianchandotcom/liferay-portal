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

import com.liferay.friendly.url.model.FriendlyURLEntryLocalization;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing FriendlyURLEntryLocalization in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class FriendlyURLEntryLocalizationCacheModel
	implements CacheModel<FriendlyURLEntryLocalization>, Externalizable,
			   MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof FriendlyURLEntryLocalizationCacheModel)) {
			return false;
		}

		FriendlyURLEntryLocalizationCacheModel
			friendlyURLEntryLocalizationCacheModel =
				(FriendlyURLEntryLocalizationCacheModel)obj;

		if ((friendlyURLEntryLocalizationId ==
				friendlyURLEntryLocalizationCacheModel.
					friendlyURLEntryLocalizationId) &&
			(mvccVersion ==
				friendlyURLEntryLocalizationCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, friendlyURLEntryLocalizationId);

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
		sb.append(", friendlyURLEntryLocalizationId=");
		sb.append(friendlyURLEntryLocalizationId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", friendlyURLEntryId=");
		sb.append(friendlyURLEntryId);
		sb.append(", languageId=");
		sb.append(languageId);
		sb.append(", urlTitle=");
		sb.append(urlTitle);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public FriendlyURLEntryLocalization toEntityModel() {
		FriendlyURLEntryLocalizationImpl friendlyURLEntryLocalizationImpl =
			new FriendlyURLEntryLocalizationImpl();

		friendlyURLEntryLocalizationImpl.setMvccVersion(mvccVersion);
		friendlyURLEntryLocalizationImpl.setFriendlyURLEntryLocalizationId(
			friendlyURLEntryLocalizationId);
		friendlyURLEntryLocalizationImpl.setCompanyId(companyId);
		friendlyURLEntryLocalizationImpl.setFriendlyURLEntryId(
			friendlyURLEntryId);

		if (languageId == null) {
			friendlyURLEntryLocalizationImpl.setLanguageId("");
		}
		else {
			friendlyURLEntryLocalizationImpl.setLanguageId(languageId);
		}

		if (urlTitle == null) {
			friendlyURLEntryLocalizationImpl.setUrlTitle("");
		}
		else {
			friendlyURLEntryLocalizationImpl.setUrlTitle(urlTitle);
		}

		friendlyURLEntryLocalizationImpl.setGroupId(groupId);
		friendlyURLEntryLocalizationImpl.setClassNameId(classNameId);
		friendlyURLEntryLocalizationImpl.setClassPK(classPK);

		friendlyURLEntryLocalizationImpl.resetOriginalValues();

		return friendlyURLEntryLocalizationImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		friendlyURLEntryLocalizationId = objectInput.readLong();

		companyId = objectInput.readLong();

		friendlyURLEntryId = objectInput.readLong();
		languageId = objectInput.readUTF();
		urlTitle = objectInput.readUTF();

		groupId = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(friendlyURLEntryLocalizationId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(friendlyURLEntryId);

		if (languageId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(languageId);
		}

		if (urlTitle == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(urlTitle);
		}

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(classNameId);

		objectOutput.writeLong(classPK);
	}

	public long mvccVersion;
	public long friendlyURLEntryLocalizationId;
	public long companyId;
	public long friendlyURLEntryId;
	public String languageId;
	public String urlTitle;
	public long groupId;
	public long classNameId;
	public long classPK;

}