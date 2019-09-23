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

package com.liferay.site.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link SiteFriendlyURL}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SiteFriendlyURL
 * @generated
 */
public class SiteFriendlyURLWrapper
	extends BaseModelWrapper<SiteFriendlyURL>
	implements ModelWrapper<SiteFriendlyURL>, SiteFriendlyURL {

	public SiteFriendlyURLWrapper(SiteFriendlyURL siteFriendlyURL) {
		super(siteFriendlyURL);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("siteFriendlyURLId", getSiteFriendlyURLId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("groupId", getGroupId());
		attributes.put("friendlyURL", getFriendlyURL());
		attributes.put("languageId", getLanguageId());
		attributes.put("lastPublishDate", getLastPublishDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long siteFriendlyURLId = (Long)attributes.get("siteFriendlyURLId");

		if (siteFriendlyURLId != null) {
			setSiteFriendlyURLId(siteFriendlyURLId);
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

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		String friendlyURL = (String)attributes.get("friendlyURL");

		if (friendlyURL != null) {
			setFriendlyURL(friendlyURL);
		}

		String languageId = (String)attributes.get("languageId");

		if (languageId != null) {
			setLanguageId(languageId);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}
	}

	/**
	 * Returns the company ID of this site friendly url.
	 *
	 * @return the company ID of this site friendly url
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this site friendly url.
	 *
	 * @return the create date of this site friendly url
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the friendly url of this site friendly url.
	 *
	 * @return the friendly url of this site friendly url
	 */
	@Override
	public String getFriendlyURL() {
		return model.getFriendlyURL();
	}

	/**
	 * Returns the group ID of this site friendly url.
	 *
	 * @return the group ID of this site friendly url
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the language ID of this site friendly url.
	 *
	 * @return the language ID of this site friendly url
	 */
	@Override
	public String getLanguageId() {
		return model.getLanguageId();
	}

	/**
	 * Returns the last publish date of this site friendly url.
	 *
	 * @return the last publish date of this site friendly url
	 */
	@Override
	public Date getLastPublishDate() {
		return model.getLastPublishDate();
	}

	/**
	 * Returns the modified date of this site friendly url.
	 *
	 * @return the modified date of this site friendly url
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this site friendly url.
	 *
	 * @return the mvcc version of this site friendly url
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this site friendly url.
	 *
	 * @return the primary key of this site friendly url
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the site friendly url ID of this site friendly url.
	 *
	 * @return the site friendly url ID of this site friendly url
	 */
	@Override
	public long getSiteFriendlyURLId() {
		return model.getSiteFriendlyURLId();
	}

	/**
	 * Returns the user ID of this site friendly url.
	 *
	 * @return the user ID of this site friendly url
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this site friendly url.
	 *
	 * @return the user name of this site friendly url
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this site friendly url.
	 *
	 * @return the user uuid of this site friendly url
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this site friendly url.
	 *
	 * @return the uuid of this site friendly url
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a site friendly url model instance should use the <code>SiteFriendlyURL</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this site friendly url.
	 *
	 * @param companyId the company ID of this site friendly url
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this site friendly url.
	 *
	 * @param createDate the create date of this site friendly url
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the friendly url of this site friendly url.
	 *
	 * @param friendlyURL the friendly url of this site friendly url
	 */
	@Override
	public void setFriendlyURL(String friendlyURL) {
		model.setFriendlyURL(friendlyURL);
	}

	/**
	 * Sets the group ID of this site friendly url.
	 *
	 * @param groupId the group ID of this site friendly url
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the language ID of this site friendly url.
	 *
	 * @param languageId the language ID of this site friendly url
	 */
	@Override
	public void setLanguageId(String languageId) {
		model.setLanguageId(languageId);
	}

	/**
	 * Sets the last publish date of this site friendly url.
	 *
	 * @param lastPublishDate the last publish date of this site friendly url
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		model.setLastPublishDate(lastPublishDate);
	}

	/**
	 * Sets the modified date of this site friendly url.
	 *
	 * @param modifiedDate the modified date of this site friendly url
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this site friendly url.
	 *
	 * @param mvccVersion the mvcc version of this site friendly url
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this site friendly url.
	 *
	 * @param primaryKey the primary key of this site friendly url
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the site friendly url ID of this site friendly url.
	 *
	 * @param siteFriendlyURLId the site friendly url ID of this site friendly url
	 */
	@Override
	public void setSiteFriendlyURLId(long siteFriendlyURLId) {
		model.setSiteFriendlyURLId(siteFriendlyURLId);
	}

	/**
	 * Sets the user ID of this site friendly url.
	 *
	 * @param userId the user ID of this site friendly url
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this site friendly url.
	 *
	 * @param userName the user name of this site friendly url
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this site friendly url.
	 *
	 * @param userUuid the user uuid of this site friendly url
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this site friendly url.
	 *
	 * @param uuid the uuid of this site friendly url
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected SiteFriendlyURLWrapper wrap(SiteFriendlyURL siteFriendlyURL) {
		return new SiteFriendlyURLWrapper(siteFriendlyURL);
	}

}