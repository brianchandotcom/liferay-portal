/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("ignored", isIgnored());
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

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		Boolean ignored = (Boolean)attributes.get("ignored");

		if (ignored != null) {
			setIgnored(ignored);
		}

		String url = (String)attributes.get("url");

		if (url != null) {
			setUrl(url);
		}
	}

	@Override
	public RedirectNotFoundEntry cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
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

	@Override
	public long getHits() {
		return model.getHits();
	}

	/**
	 * Returns the ignored of this redirect not found entry.
	 *
	 * @return the ignored of this redirect not found entry
	 */
	@Override
	public boolean getIgnored() {
		return model.getIgnored();
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

	@Override
	public long getRequestCount() {
		return model.getRequestCount();
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

	/**
	 * Returns the user ID of this redirect not found entry.
	 *
	 * @return the user ID of this redirect not found entry
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this redirect not found entry.
	 *
	 * @return the user name of this redirect not found entry
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this redirect not found entry.
	 *
	 * @return the user uuid of this redirect not found entry
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns <code>true</code> if this redirect not found entry is ignored.
	 *
	 * @return <code>true</code> if this redirect not found entry is ignored; <code>false</code> otherwise
	 */
	@Override
	public boolean isIgnored() {
		return model.isIgnored();
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
	 * Sets whether this redirect not found entry is ignored.
	 *
	 * @param ignored the ignored of this redirect not found entry
	 */
	@Override
	public void setIgnored(boolean ignored) {
		model.setIgnored(ignored);
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

	/**
	 * Sets the user ID of this redirect not found entry.
	 *
	 * @param userId the user ID of this redirect not found entry
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this redirect not found entry.
	 *
	 * @param userName the user name of this redirect not found entry
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this redirect not found entry.
	 *
	 * @param userUuid the user uuid of this redirect not found entry
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	protected RedirectNotFoundEntryWrapper wrap(
		RedirectNotFoundEntry redirectNotFoundEntry) {

		return new RedirectNotFoundEntryWrapper(redirectNotFoundEntry);
	}

}