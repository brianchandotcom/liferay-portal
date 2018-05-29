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

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link SharepointOAuth2TokenEntry}.
 * </p>
 *
 * @author Adolfo Pérez
 * @see SharepointOAuth2TokenEntry
 * @generated
 */
@ProviderType
public class SharepointOAuth2TokenEntryWrapper
	implements SharepointOAuth2TokenEntry,
		ModelWrapper<SharepointOAuth2TokenEntry> {
	public SharepointOAuth2TokenEntryWrapper(
		SharepointOAuth2TokenEntry sharepointOAuth2TokenEntry) {
		_sharepointOAuth2TokenEntry = sharepointOAuth2TokenEntry;
	}

	@Override
	public Class<?> getModelClass() {
		return SharepointOAuth2TokenEntry.class;
	}

	@Override
	public String getModelClassName() {
		return SharepointOAuth2TokenEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("sharepointOAuth2TokenEntryId",
			getSharepointOAuth2TokenEntryId());
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

	@Override
	public Object clone() {
		return new SharepointOAuth2TokenEntryWrapper((SharepointOAuth2TokenEntry)_sharepointOAuth2TokenEntry.clone());
	}

	@Override
	public int compareTo(SharepointOAuth2TokenEntry sharepointOAuth2TokenEntry) {
		return _sharepointOAuth2TokenEntry.compareTo(sharepointOAuth2TokenEntry);
	}

	/**
	* Returns the access token of this sharepoint o auth2 token entry.
	*
	* @return the access token of this sharepoint o auth2 token entry
	*/
	@Override
	public String getAccessToken() {
		return _sharepointOAuth2TokenEntry.getAccessToken();
	}

	/**
	* Returns the configuration pid of this sharepoint o auth2 token entry.
	*
	* @return the configuration pid of this sharepoint o auth2 token entry
	*/
	@Override
	public String getConfigurationPid() {
		return _sharepointOAuth2TokenEntry.getConfigurationPid();
	}

	/**
	* Returns the create date of this sharepoint o auth2 token entry.
	*
	* @return the create date of this sharepoint o auth2 token entry
	*/
	@Override
	public Date getCreateDate() {
		return _sharepointOAuth2TokenEntry.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _sharepointOAuth2TokenEntry.getExpandoBridge();
	}

	/**
	* Returns the expiration date of this sharepoint o auth2 token entry.
	*
	* @return the expiration date of this sharepoint o auth2 token entry
	*/
	@Override
	public Date getExpirationDate() {
		return _sharepointOAuth2TokenEntry.getExpirationDate();
	}

	/**
	* Returns the primary key of this sharepoint o auth2 token entry.
	*
	* @return the primary key of this sharepoint o auth2 token entry
	*/
	@Override
	public long getPrimaryKey() {
		return _sharepointOAuth2TokenEntry.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _sharepointOAuth2TokenEntry.getPrimaryKeyObj();
	}

	/**
	* Returns the refresh token of this sharepoint o auth2 token entry.
	*
	* @return the refresh token of this sharepoint o auth2 token entry
	*/
	@Override
	public String getRefreshToken() {
		return _sharepointOAuth2TokenEntry.getRefreshToken();
	}

	/**
	* Returns the sharepoint o auth2 token entry ID of this sharepoint o auth2 token entry.
	*
	* @return the sharepoint o auth2 token entry ID of this sharepoint o auth2 token entry
	*/
	@Override
	public long getSharepointOAuth2TokenEntryId() {
		return _sharepointOAuth2TokenEntry.getSharepointOAuth2TokenEntryId();
	}

	/**
	* Returns the user ID of this sharepoint o auth2 token entry.
	*
	* @return the user ID of this sharepoint o auth2 token entry
	*/
	@Override
	public long getUserId() {
		return _sharepointOAuth2TokenEntry.getUserId();
	}

	/**
	* Returns the user name of this sharepoint o auth2 token entry.
	*
	* @return the user name of this sharepoint o auth2 token entry
	*/
	@Override
	public String getUserName() {
		return _sharepointOAuth2TokenEntry.getUserName();
	}

	/**
	* Returns the user uuid of this sharepoint o auth2 token entry.
	*
	* @return the user uuid of this sharepoint o auth2 token entry
	*/
	@Override
	public String getUserUuid() {
		return _sharepointOAuth2TokenEntry.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _sharepointOAuth2TokenEntry.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _sharepointOAuth2TokenEntry.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _sharepointOAuth2TokenEntry.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _sharepointOAuth2TokenEntry.isNew();
	}

	@Override
	public void persist() {
		_sharepointOAuth2TokenEntry.persist();
	}

	/**
	* Sets the access token of this sharepoint o auth2 token entry.
	*
	* @param accessToken the access token of this sharepoint o auth2 token entry
	*/
	@Override
	public void setAccessToken(String accessToken) {
		_sharepointOAuth2TokenEntry.setAccessToken(accessToken);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_sharepointOAuth2TokenEntry.setCachedModel(cachedModel);
	}

	/**
	* Sets the configuration pid of this sharepoint o auth2 token entry.
	*
	* @param configurationPid the configuration pid of this sharepoint o auth2 token entry
	*/
	@Override
	public void setConfigurationPid(String configurationPid) {
		_sharepointOAuth2TokenEntry.setConfigurationPid(configurationPid);
	}

	/**
	* Sets the create date of this sharepoint o auth2 token entry.
	*
	* @param createDate the create date of this sharepoint o auth2 token entry
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_sharepointOAuth2TokenEntry.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_sharepointOAuth2TokenEntry.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_sharepointOAuth2TokenEntry.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_sharepointOAuth2TokenEntry.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the expiration date of this sharepoint o auth2 token entry.
	*
	* @param expirationDate the expiration date of this sharepoint o auth2 token entry
	*/
	@Override
	public void setExpirationDate(Date expirationDate) {
		_sharepointOAuth2TokenEntry.setExpirationDate(expirationDate);
	}

	@Override
	public void setNew(boolean n) {
		_sharepointOAuth2TokenEntry.setNew(n);
	}

	/**
	* Sets the primary key of this sharepoint o auth2 token entry.
	*
	* @param primaryKey the primary key of this sharepoint o auth2 token entry
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_sharepointOAuth2TokenEntry.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_sharepointOAuth2TokenEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the refresh token of this sharepoint o auth2 token entry.
	*
	* @param refreshToken the refresh token of this sharepoint o auth2 token entry
	*/
	@Override
	public void setRefreshToken(String refreshToken) {
		_sharepointOAuth2TokenEntry.setRefreshToken(refreshToken);
	}

	/**
	* Sets the sharepoint o auth2 token entry ID of this sharepoint o auth2 token entry.
	*
	* @param sharepointOAuth2TokenEntryId the sharepoint o auth2 token entry ID of this sharepoint o auth2 token entry
	*/
	@Override
	public void setSharepointOAuth2TokenEntryId(
		long sharepointOAuth2TokenEntryId) {
		_sharepointOAuth2TokenEntry.setSharepointOAuth2TokenEntryId(sharepointOAuth2TokenEntryId);
	}

	/**
	* Sets the user ID of this sharepoint o auth2 token entry.
	*
	* @param userId the user ID of this sharepoint o auth2 token entry
	*/
	@Override
	public void setUserId(long userId) {
		_sharepointOAuth2TokenEntry.setUserId(userId);
	}

	/**
	* Sets the user name of this sharepoint o auth2 token entry.
	*
	* @param userName the user name of this sharepoint o auth2 token entry
	*/
	@Override
	public void setUserName(String userName) {
		_sharepointOAuth2TokenEntry.setUserName(userName);
	}

	/**
	* Sets the user uuid of this sharepoint o auth2 token entry.
	*
	* @param userUuid the user uuid of this sharepoint o auth2 token entry
	*/
	@Override
	public void setUserUuid(String userUuid) {
		_sharepointOAuth2TokenEntry.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<SharepointOAuth2TokenEntry> toCacheModel() {
		return _sharepointOAuth2TokenEntry.toCacheModel();
	}

	@Override
	public SharepointOAuth2TokenEntry toEscapedModel() {
		return new SharepointOAuth2TokenEntryWrapper(_sharepointOAuth2TokenEntry.toEscapedModel());
	}

	@Override
	public String toString() {
		return _sharepointOAuth2TokenEntry.toString();
	}

	@Override
	public SharepointOAuth2TokenEntry toUnescapedModel() {
		return new SharepointOAuth2TokenEntryWrapper(_sharepointOAuth2TokenEntry.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _sharepointOAuth2TokenEntry.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SharepointOAuth2TokenEntryWrapper)) {
			return false;
		}

		SharepointOAuth2TokenEntryWrapper sharepointOAuth2TokenEntryWrapper = (SharepointOAuth2TokenEntryWrapper)obj;

		if (Objects.equals(_sharepointOAuth2TokenEntry,
					sharepointOAuth2TokenEntryWrapper._sharepointOAuth2TokenEntry)) {
			return true;
		}

		return false;
	}

	@Override
	public SharepointOAuth2TokenEntry getWrappedModel() {
		return _sharepointOAuth2TokenEntry;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _sharepointOAuth2TokenEntry.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _sharepointOAuth2TokenEntry.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_sharepointOAuth2TokenEntry.resetOriginalValues();
	}

	private final SharepointOAuth2TokenEntry _sharepointOAuth2TokenEntry;
}