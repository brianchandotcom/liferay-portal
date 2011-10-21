/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.softwarecatalog.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import com.liferay.portlet.softwarecatalog.model.SCProductVersion;

import java.io.Serializable;

import java.util.Date;

/**
 * The cache model class for representing SCProductVersion in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see SCProductVersion
 * @generated
 */
public class SCProductVersionCacheModel implements CacheModel<SCProductVersion>,
	Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(25);

		sb.append("{productVersionId=");
		sb.append(productVersionId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", productEntryId=");
		sb.append(productEntryId);
		sb.append(", version=");
		sb.append(version);
		sb.append(", changeLog=");
		sb.append(changeLog);
		sb.append(", downloadPageURL=");
		sb.append(downloadPageURL);
		sb.append(", directDownloadURL=");
		sb.append(directDownloadURL);
		sb.append(", repoStoreArtifact=");
		sb.append(repoStoreArtifact);
		sb.append("}");

		return sb.toString();
	}

	public SCProductVersion toEntityModel() {
		SCProductVersionImpl scProductVersionImpl = new SCProductVersionImpl();

		scProductVersionImpl.setProductVersionId(productVersionId);
		scProductVersionImpl.setCompanyId(companyId);
		scProductVersionImpl.setUserId(userId);

		if (userName == null) {
			scProductVersionImpl.setUserName(StringPool.BLANK);
		}
		else {
			scProductVersionImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			scProductVersionImpl.setCreateDate(null);
		}
		else {
			scProductVersionImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			scProductVersionImpl.setModifiedDate(null);
		}
		else {
			scProductVersionImpl.setModifiedDate(new Date(modifiedDate));
		}

		scProductVersionImpl.setProductEntryId(productEntryId);

		if (version == null) {
			scProductVersionImpl.setVersion(StringPool.BLANK);
		}
		else {
			scProductVersionImpl.setVersion(version);
		}

		if (changeLog == null) {
			scProductVersionImpl.setChangeLog(StringPool.BLANK);
		}
		else {
			scProductVersionImpl.setChangeLog(changeLog);
		}

		if (downloadPageURL == null) {
			scProductVersionImpl.setDownloadPageURL(StringPool.BLANK);
		}
		else {
			scProductVersionImpl.setDownloadPageURL(downloadPageURL);
		}

		if (directDownloadURL == null) {
			scProductVersionImpl.setDirectDownloadURL(StringPool.BLANK);
		}
		else {
			scProductVersionImpl.setDirectDownloadURL(directDownloadURL);
		}

		scProductVersionImpl.setRepoStoreArtifact(repoStoreArtifact);

		scProductVersionImpl.resetOriginalValues();

		return scProductVersionImpl;
	}

	public long productVersionId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long productEntryId;
	public String version;
	public String changeLog;
	public String downloadPageURL;
	public String directDownloadURL;
	public boolean repoStoreArtifact;
}