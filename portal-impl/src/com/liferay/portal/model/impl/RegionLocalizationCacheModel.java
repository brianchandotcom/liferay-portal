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

package com.liferay.portal.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.kernel.model.RegionLocalization;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing RegionLocalization in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class RegionLocalizationCacheModel
	implements CacheModel<RegionLocalization>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof RegionLocalizationCacheModel)) {
			return false;
		}

		RegionLocalizationCacheModel regionLocalizationCacheModel =
			(RegionLocalizationCacheModel)object;

		if ((regionLocalizationId ==
				regionLocalizationCacheModel.regionLocalizationId) &&
			(mvccVersion == regionLocalizationCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, regionLocalizationId);

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
		StringBundler sb = new StringBundler(13);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", regionLocalizationId=");
		sb.append(regionLocalizationId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", regionId=");
		sb.append(regionId);
		sb.append(", languageId=");
		sb.append(languageId);
		sb.append(", title=");
		sb.append(title);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public RegionLocalization toEntityModel() {
		RegionLocalizationImpl regionLocalizationImpl =
			new RegionLocalizationImpl();

		regionLocalizationImpl.setMvccVersion(mvccVersion);
		regionLocalizationImpl.setRegionLocalizationId(regionLocalizationId);
		regionLocalizationImpl.setCompanyId(companyId);
		regionLocalizationImpl.setRegionId(regionId);

		if (languageId == null) {
			regionLocalizationImpl.setLanguageId("");
		}
		else {
			regionLocalizationImpl.setLanguageId(languageId);
		}

		if (title == null) {
			regionLocalizationImpl.setTitle("");
		}
		else {
			regionLocalizationImpl.setTitle(title);
		}

		regionLocalizationImpl.resetOriginalValues();

		return regionLocalizationImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		regionLocalizationId = objectInput.readLong();

		companyId = objectInput.readLong();

		regionId = objectInput.readLong();
		languageId = objectInput.readUTF();
		title = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(regionLocalizationId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(regionId);

		if (languageId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(languageId);
		}

		if (title == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(title);
		}
	}

	public long mvccVersion;
	public long regionLocalizationId;
	public long companyId;
	public long regionId;
	public String languageId;
	public String title;

}