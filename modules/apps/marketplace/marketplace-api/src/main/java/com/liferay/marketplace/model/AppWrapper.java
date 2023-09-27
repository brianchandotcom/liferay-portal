/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link App}.
 * </p>
 *
 * @author Ryan Park
 * @see App
 * @generated
 */
public class AppWrapper
	extends BaseModelWrapper<App> implements App, ModelWrapper<App> {

	public AppWrapper(App app) {
		super(app);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("appId", getAppId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("remoteAppId", getRemoteAppId());
		attributes.put("title", getTitle());
		attributes.put("description", getDescription());
		attributes.put("category", getCategory());
		attributes.put("iconURL", getIconURL());
		attributes.put("version", getVersion());
		attributes.put("required", isRequired());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long appId = (Long)attributes.get("appId");

		if (appId != null) {
			setAppId(appId);
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

		Long remoteAppId = (Long)attributes.get("remoteAppId");

		if (remoteAppId != null) {
			setRemoteAppId(remoteAppId);
		}

		String title = (String)attributes.get("title");

		if (title != null) {
			setTitle(title);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		String category = (String)attributes.get("category");

		if (category != null) {
			setCategory(category);
		}

		String iconURL = (String)attributes.get("iconURL");

		if (iconURL != null) {
			setIconURL(iconURL);
		}

		String version = (String)attributes.get("version");

		if (version != null) {
			setVersion(version);
		}

		Boolean required = (Boolean)attributes.get("required");

		if (required != null) {
			setRequired(required);
		}
	}

	@Override
	public String[] addContextName(String contextName) {
		return model.addContextName(contextName);
	}

	@Override
	public App cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the app ID of this app.
	 *
	 * @return the app ID of this app
	 */
	@Override
	public long getAppId() {
		return model.getAppId();
	}

	/**
	 * Returns the category of this app.
	 *
	 * @return the category of this app
	 */
	@Override
	public String getCategory() {
		return model.getCategory();
	}

	/**
	 * Returns the company ID of this app.
	 *
	 * @return the company ID of this app
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	@Override
	public String[] getContextNames() {
		return model.getContextNames();
	}

	/**
	 * Returns the create date of this app.
	 *
	 * @return the create date of this app
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the description of this app.
	 *
	 * @return the description of this app
	 */
	@Override
	public String getDescription() {
		return model.getDescription();
	}

	@Override
	public String getFileDir() {
		return model.getFileDir();
	}

	@Override
	public String getFileName() {
		return model.getFileName();
	}

	@Override
	public String getFilePath() {
		return model.getFilePath();
	}

	/**
	 * Returns the icon url of this app.
	 *
	 * @return the icon url of this app
	 */
	@Override
	public String getIconURL() {
		return model.getIconURL();
	}

	/**
	 * Returns the modified date of this app.
	 *
	 * @return the modified date of this app
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the primary key of this app.
	 *
	 * @return the primary key of this app
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the remote app ID of this app.
	 *
	 * @return the remote app ID of this app
	 */
	@Override
	public long getRemoteAppId() {
		return model.getRemoteAppId();
	}

	/**
	 * Returns the required of this app.
	 *
	 * @return the required of this app
	 */
	@Override
	public boolean getRequired() {
		return model.getRequired();
	}

	/**
	 * Returns the title of this app.
	 *
	 * @return the title of this app
	 */
	@Override
	public String getTitle() {
		return model.getTitle();
	}

	/**
	 * Returns the user ID of this app.
	 *
	 * @return the user ID of this app
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this app.
	 *
	 * @return the user name of this app
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this app.
	 *
	 * @return the user uuid of this app
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this app.
	 *
	 * @return the uuid of this app
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns the version of this app.
	 *
	 * @return the version of this app
	 */
	@Override
	public String getVersion() {
		return model.getVersion();
	}

	@Override
	public boolean isDownloaded()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.isDownloaded();
	}

	@Override
	public boolean isInstalled() {
		return model.isInstalled();
	}

	/**
	 * Returns <code>true</code> if this app is required.
	 *
	 * @return <code>true</code> if this app is required; <code>false</code> otherwise
	 */
	@Override
	public boolean isRequired() {
		return model.isRequired();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the app ID of this app.
	 *
	 * @param appId the app ID of this app
	 */
	@Override
	public void setAppId(long appId) {
		model.setAppId(appId);
	}

	/**
	 * Sets the category of this app.
	 *
	 * @param category the category of this app
	 */
	@Override
	public void setCategory(String category) {
		model.setCategory(category);
	}

	/**
	 * Sets the company ID of this app.
	 *
	 * @param companyId the company ID of this app
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this app.
	 *
	 * @param createDate the create date of this app
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the description of this app.
	 *
	 * @param description the description of this app
	 */
	@Override
	public void setDescription(String description) {
		model.setDescription(description);
	}

	/**
	 * Sets the icon url of this app.
	 *
	 * @param iconURL the icon url of this app
	 */
	@Override
	public void setIconURL(String iconURL) {
		model.setIconURL(iconURL);
	}

	/**
	 * Sets the modified date of this app.
	 *
	 * @param modifiedDate the modified date of this app
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the primary key of this app.
	 *
	 * @param primaryKey the primary key of this app
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the remote app ID of this app.
	 *
	 * @param remoteAppId the remote app ID of this app
	 */
	@Override
	public void setRemoteAppId(long remoteAppId) {
		model.setRemoteAppId(remoteAppId);
	}

	/**
	 * Sets whether this app is required.
	 *
	 * @param required the required of this app
	 */
	@Override
	public void setRequired(boolean required) {
		model.setRequired(required);
	}

	/**
	 * Sets the title of this app.
	 *
	 * @param title the title of this app
	 */
	@Override
	public void setTitle(String title) {
		model.setTitle(title);
	}

	/**
	 * Sets the user ID of this app.
	 *
	 * @param userId the user ID of this app
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this app.
	 *
	 * @param userName the user name of this app
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this app.
	 *
	 * @param userUuid the user uuid of this app
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this app.
	 *
	 * @param uuid the uuid of this app
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	/**
	 * Sets the version of this app.
	 *
	 * @param version the version of this app
	 */
	@Override
	public void setVersion(String version) {
		model.setVersion(version);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected AppWrapper wrap(App app) {
		return new AppWrapper(app);
	}

}