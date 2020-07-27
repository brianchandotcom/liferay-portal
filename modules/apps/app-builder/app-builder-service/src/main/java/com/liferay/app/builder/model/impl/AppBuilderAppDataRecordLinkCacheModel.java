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

package com.liferay.app.builder.model.impl;

import com.liferay.app.builder.model.AppBuilderAppDataRecordLink;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing AppBuilderAppDataRecordLink in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AppBuilderAppDataRecordLinkCacheModel
	implements CacheModel<AppBuilderAppDataRecordLink>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof AppBuilderAppDataRecordLinkCacheModel)) {
			return false;
		}

		AppBuilderAppDataRecordLinkCacheModel
			appBuilderAppDataRecordLinkCacheModel =
				(AppBuilderAppDataRecordLinkCacheModel)object;

		if (appBuilderAppDataRecordLinkId ==
				appBuilderAppDataRecordLinkCacheModel.
					appBuilderAppDataRecordLinkId) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, appBuilderAppDataRecordLinkId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(13);

		sb.append("{appBuilderAppDataRecordLinkId=");
		sb.append(appBuilderAppDataRecordLinkId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", appBuilderAppId=");
		sb.append(appBuilderAppId);
		sb.append(", appBuilderAppVersionId=");
		sb.append(appBuilderAppVersionId);
		sb.append(", ddlRecordId=");
		sb.append(ddlRecordId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public AppBuilderAppDataRecordLink toEntityModel() {
		AppBuilderAppDataRecordLinkImpl appBuilderAppDataRecordLinkImpl =
			new AppBuilderAppDataRecordLinkImpl();

		appBuilderAppDataRecordLinkImpl.setAppBuilderAppDataRecordLinkId(
			appBuilderAppDataRecordLinkId);
		appBuilderAppDataRecordLinkImpl.setGroupId(groupId);
		appBuilderAppDataRecordLinkImpl.setCompanyId(companyId);
		appBuilderAppDataRecordLinkImpl.setAppBuilderAppId(appBuilderAppId);
		appBuilderAppDataRecordLinkImpl.setAppBuilderAppVersionId(
			appBuilderAppVersionId);
		appBuilderAppDataRecordLinkImpl.setDdlRecordId(ddlRecordId);

		appBuilderAppDataRecordLinkImpl.resetOriginalValues();

		return appBuilderAppDataRecordLinkImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		appBuilderAppDataRecordLinkId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		appBuilderAppId = objectInput.readLong();

		appBuilderAppVersionId = objectInput.readLong();

		ddlRecordId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(appBuilderAppDataRecordLinkId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(appBuilderAppId);

		objectOutput.writeLong(appBuilderAppVersionId);

		objectOutput.writeLong(ddlRecordId);
	}

	public long appBuilderAppDataRecordLinkId;
	public long groupId;
	public long companyId;
	public long appBuilderAppId;
	public long appBuilderAppVersionId;
	public long ddlRecordId;

}