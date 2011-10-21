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

package com.liferay.portlet.social.model;

import com.liferay.portal.model.ModelWrapper;

/**
 * <p>
 * This class is a wrapper for {@link SocialEquityLog}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialEquityLog
 * @generated
 */
public class SocialEquityLogWrapper implements SocialEquityLog,
	ModelWrapper<SocialEquityLog> {
	public SocialEquityLogWrapper(SocialEquityLog socialEquityLog) {
		_socialEquityLog = socialEquityLog;
	}

	public Class<?> getModelClass() {
		return SocialEquityLog.class;
	}

	public String getModelClassName() {
		return SocialEquityLog.class.getName();
	}

	/**
	* Returns the primary key of this social equity log.
	*
	* @return the primary key of this social equity log
	*/
	public long getPrimaryKey() {
		return _socialEquityLog.getPrimaryKey();
	}

	/**
	* Sets the primary key of this social equity log.
	*
	* @param primaryKey the primary key of this social equity log
	*/
	public void setPrimaryKey(long primaryKey) {
		_socialEquityLog.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the equity log ID of this social equity log.
	*
	* @return the equity log ID of this social equity log
	*/
	public long getEquityLogId() {
		return _socialEquityLog.getEquityLogId();
	}

	/**
	* Sets the equity log ID of this social equity log.
	*
	* @param equityLogId the equity log ID of this social equity log
	*/
	public void setEquityLogId(long equityLogId) {
		_socialEquityLog.setEquityLogId(equityLogId);
	}

	/**
	* Returns the group ID of this social equity log.
	*
	* @return the group ID of this social equity log
	*/
	public long getGroupId() {
		return _socialEquityLog.getGroupId();
	}

	/**
	* Sets the group ID of this social equity log.
	*
	* @param groupId the group ID of this social equity log
	*/
	public void setGroupId(long groupId) {
		_socialEquityLog.setGroupId(groupId);
	}

	/**
	* Returns the company ID of this social equity log.
	*
	* @return the company ID of this social equity log
	*/
	public long getCompanyId() {
		return _socialEquityLog.getCompanyId();
	}

	/**
	* Sets the company ID of this social equity log.
	*
	* @param companyId the company ID of this social equity log
	*/
	public void setCompanyId(long companyId) {
		_socialEquityLog.setCompanyId(companyId);
	}

	/**
	* Returns the user ID of this social equity log.
	*
	* @return the user ID of this social equity log
	*/
	public long getUserId() {
		return _socialEquityLog.getUserId();
	}

	/**
	* Sets the user ID of this social equity log.
	*
	* @param userId the user ID of this social equity log
	*/
	public void setUserId(long userId) {
		_socialEquityLog.setUserId(userId);
	}

	/**
	* Returns the user uuid of this social equity log.
	*
	* @return the user uuid of this social equity log
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityLog.getUserUuid();
	}

	/**
	* Sets the user uuid of this social equity log.
	*
	* @param userUuid the user uuid of this social equity log
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_socialEquityLog.setUserUuid(userUuid);
	}

	/**
	* Returns the asset entry ID of this social equity log.
	*
	* @return the asset entry ID of this social equity log
	*/
	public long getAssetEntryId() {
		return _socialEquityLog.getAssetEntryId();
	}

	/**
	* Sets the asset entry ID of this social equity log.
	*
	* @param assetEntryId the asset entry ID of this social equity log
	*/
	public void setAssetEntryId(long assetEntryId) {
		_socialEquityLog.setAssetEntryId(assetEntryId);
	}

	/**
	* Returns the action ID of this social equity log.
	*
	* @return the action ID of this social equity log
	*/
	public java.lang.String getActionId() {
		return _socialEquityLog.getActionId();
	}

	/**
	* Sets the action ID of this social equity log.
	*
	* @param actionId the action ID of this social equity log
	*/
	public void setActionId(java.lang.String actionId) {
		_socialEquityLog.setActionId(actionId);
	}

	/**
	* Returns the action date of this social equity log.
	*
	* @return the action date of this social equity log
	*/
	public int getActionDate() {
		return _socialEquityLog.getActionDate();
	}

	/**
	* Sets the action date of this social equity log.
	*
	* @param actionDate the action date of this social equity log
	*/
	public void setActionDate(int actionDate) {
		_socialEquityLog.setActionDate(actionDate);
	}

	/**
	* Returns the active of this social equity log.
	*
	* @return the active of this social equity log
	*/
	public boolean getActive() {
		return _socialEquityLog.getActive();
	}

	/**
	* Returns <code>true</code> if this social equity log is active.
	*
	* @return <code>true</code> if this social equity log is active; <code>false</code> otherwise
	*/
	public boolean isActive() {
		return _socialEquityLog.isActive();
	}

	/**
	* Sets whether this social equity log is active.
	*
	* @param active the active of this social equity log
	*/
	public void setActive(boolean active) {
		_socialEquityLog.setActive(active);
	}

	/**
	* Returns the expiration of this social equity log.
	*
	* @return the expiration of this social equity log
	*/
	public int getExpiration() {
		return _socialEquityLog.getExpiration();
	}

	/**
	* Sets the expiration of this social equity log.
	*
	* @param expiration the expiration of this social equity log
	*/
	public void setExpiration(int expiration) {
		_socialEquityLog.setExpiration(expiration);
	}

	/**
	* Returns the type of this social equity log.
	*
	* @return the type of this social equity log
	*/
	public int getType() {
		return _socialEquityLog.getType();
	}

	/**
	* Sets the type of this social equity log.
	*
	* @param type the type of this social equity log
	*/
	public void setType(int type) {
		_socialEquityLog.setType(type);
	}

	/**
	* Returns the value of this social equity log.
	*
	* @return the value of this social equity log
	*/
	public int getValue() {
		return _socialEquityLog.getValue();
	}

	/**
	* Sets the value of this social equity log.
	*
	* @param value the value of this social equity log
	*/
	public void setValue(int value) {
		_socialEquityLog.setValue(value);
	}

	/**
	* Returns the extra data of this social equity log.
	*
	* @return the extra data of this social equity log
	*/
	public java.lang.String getExtraData() {
		return _socialEquityLog.getExtraData();
	}

	/**
	* Sets the extra data of this social equity log.
	*
	* @param extraData the extra data of this social equity log
	*/
	public void setExtraData(java.lang.String extraData) {
		_socialEquityLog.setExtraData(extraData);
	}

	public boolean isNew() {
		return _socialEquityLog.isNew();
	}

	public void setNew(boolean n) {
		_socialEquityLog.setNew(n);
	}

	public boolean isCachedModel() {
		return _socialEquityLog.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_socialEquityLog.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _socialEquityLog.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _socialEquityLog.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_socialEquityLog.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _socialEquityLog.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_socialEquityLog.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new SocialEquityLogWrapper((SocialEquityLog)_socialEquityLog.clone());
	}

	public int compareTo(
		com.liferay.portlet.social.model.SocialEquityLog socialEquityLog) {
		return _socialEquityLog.compareTo(socialEquityLog);
	}

	@Override
	public int hashCode() {
		return _socialEquityLog.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.portlet.social.model.SocialEquityLog> toCacheModel() {
		return _socialEquityLog.toCacheModel();
	}

	public com.liferay.portlet.social.model.SocialEquityLog toEscapedModel() {
		return new SocialEquityLogWrapper(_socialEquityLog.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _socialEquityLog.toString();
	}

	public java.lang.String toXmlString() {
		return _socialEquityLog.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_socialEquityLog.persist();
	}

	public int getLifespan() {
		return _socialEquityLog.getLifespan();
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public SocialEquityLog getWrappedSocialEquityLog() {
		return _socialEquityLog;
	}

	public SocialEquityLog getWrappedModel() {
		return _socialEquityLog;
	}

	public void resetOriginalValues() {
		_socialEquityLog.resetOriginalValues();
	}

	private SocialEquityLog _socialEquityLog;
}