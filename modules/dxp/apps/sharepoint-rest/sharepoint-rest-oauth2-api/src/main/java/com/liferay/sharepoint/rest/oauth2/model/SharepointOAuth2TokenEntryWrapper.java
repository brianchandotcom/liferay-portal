/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.sharepoint.rest.oauth2.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link SharepointOAuth2TokenEntry}.
 * </p>
 *
 * @author Adolfo Pérez
 * @see SharepointOAuth2TokenEntry
 * @generated
 */
public class SharepointOAuth2TokenEntryWrapper
	extends BaseModelWrapper<SharepointOAuth2TokenEntry>
	implements ModelWrapper<SharepointOAuth2TokenEntry>,
			   SharepointOAuth2TokenEntry {

	public SharepointOAuth2TokenEntryWrapper(
		SharepointOAuth2TokenEntry sharepointOAuth2TokenEntry) {

		super(sharepointOAuth2TokenEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put(
			"sharepointOAuth2TokenEntryId", getSharepointOAuth2TokenEntryId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("accessToken", getAccessToken());
		attributes.put("configurationPid", getConfigurationPid());
		attributes.put("expirationDate", getExpirationDate());
		attributes.put("refreshToken", getRefreshToken());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long sharepointOAuth2TokenEntryId = (Long)attributes.get(
			"sharepointOAuth2TokenEntryId");

		if (sharepointOAuth2TokenEntryId != null) {
			setSharepointOAuth2TokenEntryId(sharepointOAuth2TokenEntryId);
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

		String accessToken = (String)attributes.get("accessToken");

		if (accessToken != null) {
			setAccessToken(accessToken);
		}

		String configurationPid = (String)attributes.get("configurationPid");

		if (configurationPid != null) {
			setConfigurationPid(configurationPid);
		}

		Date expirationDate = (Date)attributes.get("expirationDate");

		if (expirationDate != null) {
			setExpirationDate(expirationDate);
		}

		String refreshToken = (String)attributes.get("refreshToken");

		if (refreshToken != null) {
			setRefreshToken(refreshToken);
		}
	}

	/**
	 * Returns the access token of this sharepoint o auth2 token entry.
	 *
	 * @return the access token of this sharepoint o auth2 token entry
	 */
	@Override
	public String getAccessToken() {
		return model.getAccessToken();
	}

	/**
	 * Returns the company ID of this sharepoint o auth2 token entry.
	 *
	 * @return the company ID of this sharepoint o auth2 token entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the configuration pid of this sharepoint o auth2 token entry.
	 *
	 * @return the configuration pid of this sharepoint o auth2 token entry
	 */
	@Override
	public String getConfigurationPid() {
		return model.getConfigurationPid();
	}

	/**
	 * Returns the create date of this sharepoint o auth2 token entry.
	 *
	 * @return the create date of this sharepoint o auth2 token entry
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the expiration date of this sharepoint o auth2 token entry.
	 *
	 * @return the expiration date of this sharepoint o auth2 token entry
	 */
	@Override
	public Date getExpirationDate() {
		return model.getExpirationDate();
	}

	/**
	 * Returns the primary key of this sharepoint o auth2 token entry.
	 *
	 * @return the primary key of this sharepoint o auth2 token entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the refresh token of this sharepoint o auth2 token entry.
	 *
	 * @return the refresh token of this sharepoint o auth2 token entry
	 */
	@Override
	public String getRefreshToken() {
		return model.getRefreshToken();
	}

	/**
	 * Returns the sharepoint o auth2 token entry ID of this sharepoint o auth2 token entry.
	 *
	 * @return the sharepoint o auth2 token entry ID of this sharepoint o auth2 token entry
	 */
	@Override
	public long getSharepointOAuth2TokenEntryId() {
		return model.getSharepointOAuth2TokenEntryId();
	}

	/**
	 * Returns the user ID of this sharepoint o auth2 token entry.
	 *
	 * @return the user ID of this sharepoint o auth2 token entry
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this sharepoint o auth2 token entry.
	 *
	 * @return the user name of this sharepoint o auth2 token entry
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this sharepoint o auth2 token entry.
	 *
	 * @return the user uuid of this sharepoint o auth2 token entry
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a sharepoint o auth2 token entry model instance should use the <code>SharepointOAuth2TokenEntry</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the access token of this sharepoint o auth2 token entry.
	 *
	 * @param accessToken the access token of this sharepoint o auth2 token entry
	 */
	@Override
	public void setAccessToken(String accessToken) {
		model.setAccessToken(accessToken);
	}

	/**
	 * Sets the company ID of this sharepoint o auth2 token entry.
	 *
	 * @param companyId the company ID of this sharepoint o auth2 token entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the configuration pid of this sharepoint o auth2 token entry.
	 *
	 * @param configurationPid the configuration pid of this sharepoint o auth2 token entry
	 */
	@Override
	public void setConfigurationPid(String configurationPid) {
		model.setConfigurationPid(configurationPid);
	}

	/**
	 * Sets the create date of this sharepoint o auth2 token entry.
	 *
	 * @param createDate the create date of this sharepoint o auth2 token entry
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the expiration date of this sharepoint o auth2 token entry.
	 *
	 * @param expirationDate the expiration date of this sharepoint o auth2 token entry
	 */
	@Override
	public void setExpirationDate(Date expirationDate) {
		model.setExpirationDate(expirationDate);
	}

	/**
	 * Sets the primary key of this sharepoint o auth2 token entry.
	 *
	 * @param primaryKey the primary key of this sharepoint o auth2 token entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the refresh token of this sharepoint o auth2 token entry.
	 *
	 * @param refreshToken the refresh token of this sharepoint o auth2 token entry
	 */
	@Override
	public void setRefreshToken(String refreshToken) {
		model.setRefreshToken(refreshToken);
	}

	/**
	 * Sets the sharepoint o auth2 token entry ID of this sharepoint o auth2 token entry.
	 *
	 * @param sharepointOAuth2TokenEntryId the sharepoint o auth2 token entry ID of this sharepoint o auth2 token entry
	 */
	@Override
	public void setSharepointOAuth2TokenEntryId(
		long sharepointOAuth2TokenEntryId) {

		model.setSharepointOAuth2TokenEntryId(sharepointOAuth2TokenEntryId);
	}

	/**
	 * Sets the user ID of this sharepoint o auth2 token entry.
	 *
	 * @param userId the user ID of this sharepoint o auth2 token entry
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this sharepoint o auth2 token entry.
	 *
	 * @param userName the user name of this sharepoint o auth2 token entry
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this sharepoint o auth2 token entry.
	 *
	 * @param userUuid the user uuid of this sharepoint o auth2 token entry
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected SharepointOAuth2TokenEntryWrapper wrap(
		SharepointOAuth2TokenEntry sharepointOAuth2TokenEntry) {

		return new SharepointOAuth2TokenEntryWrapper(
			sharepointOAuth2TokenEntry);
	}

}