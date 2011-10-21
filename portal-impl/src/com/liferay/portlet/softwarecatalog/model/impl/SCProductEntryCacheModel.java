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

import com.liferay.portlet.softwarecatalog.model.SCProductEntry;

import java.io.Serializable;

import java.util.Date;

/**
 * The cache model class for representing SCProductEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see SCProductEntry
 * @generated
 */
public class SCProductEntryCacheModel implements CacheModel<SCProductEntry>,
	Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(33);

		sb.append("{productEntryId=");
		sb.append(productEntryId);
		sb.append(", groupId=");
		sb.append(groupId);
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
		sb.append(", name=");
		sb.append(name);
		sb.append(", type=");
		sb.append(type);
		sb.append(", tags=");
		sb.append(tags);
		sb.append(", shortDescription=");
		sb.append(shortDescription);
		sb.append(", longDescription=");
		sb.append(longDescription);
		sb.append(", pageURL=");
		sb.append(pageURL);
		sb.append(", author=");
		sb.append(author);
		sb.append(", repoGroupId=");
		sb.append(repoGroupId);
		sb.append(", repoArtifactId=");
		sb.append(repoArtifactId);
		sb.append("}");

		return sb.toString();
	}

	public SCProductEntry toEntityModel() {
		SCProductEntryImpl scProductEntryImpl = new SCProductEntryImpl();

		scProductEntryImpl.setProductEntryId(productEntryId);
		scProductEntryImpl.setGroupId(groupId);
		scProductEntryImpl.setCompanyId(companyId);
		scProductEntryImpl.setUserId(userId);

		if (userName == null) {
			scProductEntryImpl.setUserName(StringPool.BLANK);
		}
		else {
			scProductEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			scProductEntryImpl.setCreateDate(null);
		}
		else {
			scProductEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			scProductEntryImpl.setModifiedDate(null);
		}
		else {
			scProductEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (name == null) {
			scProductEntryImpl.setName(StringPool.BLANK);
		}
		else {
			scProductEntryImpl.setName(name);
		}

		if (type == null) {
			scProductEntryImpl.setType(StringPool.BLANK);
		}
		else {
			scProductEntryImpl.setType(type);
		}

		if (tags == null) {
			scProductEntryImpl.setTags(StringPool.BLANK);
		}
		else {
			scProductEntryImpl.setTags(tags);
		}

		if (shortDescription == null) {
			scProductEntryImpl.setShortDescription(StringPool.BLANK);
		}
		else {
			scProductEntryImpl.setShortDescription(shortDescription);
		}

		if (longDescription == null) {
			scProductEntryImpl.setLongDescription(StringPool.BLANK);
		}
		else {
			scProductEntryImpl.setLongDescription(longDescription);
		}

		if (pageURL == null) {
			scProductEntryImpl.setPageURL(StringPool.BLANK);
		}
		else {
			scProductEntryImpl.setPageURL(pageURL);
		}

		if (author == null) {
			scProductEntryImpl.setAuthor(StringPool.BLANK);
		}
		else {
			scProductEntryImpl.setAuthor(author);
		}

		if (repoGroupId == null) {
			scProductEntryImpl.setRepoGroupId(StringPool.BLANK);
		}
		else {
			scProductEntryImpl.setRepoGroupId(repoGroupId);
		}

		if (repoArtifactId == null) {
			scProductEntryImpl.setRepoArtifactId(StringPool.BLANK);
		}
		else {
			scProductEntryImpl.setRepoArtifactId(repoArtifactId);
		}

		scProductEntryImpl.resetOriginalValues();

		return scProductEntryImpl;
	}

	public long productEntryId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String name;
	public String type;
	public String tags;
	public String shortDescription;
	public String longDescription;
	public String pageURL;
	public String author;
	public String repoGroupId;
	public String repoArtifactId;
}