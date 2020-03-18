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

package com.liferay.redirect.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link RedirectNotFoundEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RedirectNotFoundEntry
 * @generated
 */
public class RedirectNotFoundEntryWrapper
	extends BaseModelWrapper<RedirectNotFoundEntry>
	implements ModelWrapper<RedirectNotFoundEntry>, RedirectNotFoundEntry {

	public RedirectNotFoundEntryWrapper(
		RedirectNotFoundEntry redirectNotFoundEntry) {

		super(redirectNotFoundEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("redirectNotFoundEntryId", getRedirectNotFoundEntryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("hits", getHits());
		attributes.put("url", getUrl());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long redirectNotFoundEntryId = (Long)attributes.get(
			"redirectNotFoundEntryId");

		if (redirectNotFoundEntryId != null) {
			setRedirectNotFoundEntryId(redirectNotFoundEntryId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		Long hits = (Long)attributes.get("hits");

		if (hits != null) {
			setHits(hits);
		}

		String url = (String)attributes.get("url");

		if (url != null) {
			setUrl(url);
		}
	}

	/**
	 * Returns the company ID of this redirect not found entry.
	 *
	 * @return the company ID of this redirect not found entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this redirect not found entry.
	 *
	 * @return the create date of this redirect not found entry
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the group ID of this redirect not found entry.
	 *
	 * @return the group ID of this redirect not found entry
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the hits of this redirect not found entry.
	 *
	 * @return the hits of this redirect not found entry
	 */
	@Override
	public long getHits() {
		return model.getHits();
	}

	/**
	 * Returns the modified date of this redirect not found entry.
	 *
	 * @return the modified date of this redirect not found entry
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this redirect not found entry.
	 *
	 * @return the mvcc version of this redirect not found entry
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this redirect not found entry.
	 *
	 * @return the primary key of this redirect not found entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the redirect not found entry ID of this redirect not found entry.
	 *
	 * @return the redirect not found entry ID of this redirect not found entry
	 */
	@Override
	public long getRedirectNotFoundEntryId() {
		return model.getRedirectNotFoundEntryId();
	}

	/**
	 * Returns the url of this redirect not found entry.
	 *
	 * @return the url of this redirect not found entry
	 */
	@Override
	public String getUrl() {
		return model.getUrl();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this redirect not found entry.
	 *
	 * @param companyId the company ID of this redirect not found entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this redirect not found entry.
	 *
	 * @param createDate the create date of this redirect not found entry
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the group ID of this redirect not found entry.
	 *
	 * @param groupId the group ID of this redirect not found entry
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the hits of this redirect not found entry.
	 *
	 * @param hits the hits of this redirect not found entry
	 */
	@Override
	public void setHits(long hits) {
		model.setHits(hits);
	}

	/**
	 * Sets the modified date of this redirect not found entry.
	 *
	 * @param modifiedDate the modified date of this redirect not found entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this redirect not found entry.
	 *
	 * @param mvccVersion the mvcc version of this redirect not found entry
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this redirect not found entry.
	 *
	 * @param primaryKey the primary key of this redirect not found entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the redirect not found entry ID of this redirect not found entry.
	 *
	 * @param redirectNotFoundEntryId the redirect not found entry ID of this redirect not found entry
	 */
	@Override
	public void setRedirectNotFoundEntryId(long redirectNotFoundEntryId) {
		model.setRedirectNotFoundEntryId(redirectNotFoundEntryId);
	}

	/**
	 * Sets the url of this redirect not found entry.
	 *
	 * @param url the url of this redirect not found entry
	 */
	@Override
	public void setUrl(String url) {
		model.setUrl(url);
	}

	@Override
	protected RedirectNotFoundEntryWrapper wrap(
		RedirectNotFoundEntry redirectNotFoundEntry) {

		return new RedirectNotFoundEntryWrapper(redirectNotFoundEntry);
	}

}