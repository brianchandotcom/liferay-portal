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
 * This class is a wrapper for {@link SocialEquityHistory}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialEquityHistory
 * @generated
 */
public class SocialEquityHistoryWrapper implements SocialEquityHistory,
	ModelWrapper<SocialEquityHistory> {
	public SocialEquityHistoryWrapper(SocialEquityHistory socialEquityHistory) {
		_socialEquityHistory = socialEquityHistory;
	}

	public Class<?> getModelClass() {
		return SocialEquityHistory.class;
	}

	public String getModelClassName() {
		return SocialEquityHistory.class.getName();
	}

	/**
	* Returns the primary key of this social equity history.
	*
	* @return the primary key of this social equity history
	*/
	public long getPrimaryKey() {
		return _socialEquityHistory.getPrimaryKey();
	}

	/**
	* Sets the primary key of this social equity history.
	*
	* @param primaryKey the primary key of this social equity history
	*/
	public void setPrimaryKey(long primaryKey) {
		_socialEquityHistory.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the equity history ID of this social equity history.
	*
	* @return the equity history ID of this social equity history
	*/
	public long getEquityHistoryId() {
		return _socialEquityHistory.getEquityHistoryId();
	}

	/**
	* Sets the equity history ID of this social equity history.
	*
	* @param equityHistoryId the equity history ID of this social equity history
	*/
	public void setEquityHistoryId(long equityHistoryId) {
		_socialEquityHistory.setEquityHistoryId(equityHistoryId);
	}

	/**
	* Returns the group ID of this social equity history.
	*
	* @return the group ID of this social equity history
	*/
	public long getGroupId() {
		return _socialEquityHistory.getGroupId();
	}

	/**
	* Sets the group ID of this social equity history.
	*
	* @param groupId the group ID of this social equity history
	*/
	public void setGroupId(long groupId) {
		_socialEquityHistory.setGroupId(groupId);
	}

	/**
	* Returns the company ID of this social equity history.
	*
	* @return the company ID of this social equity history
	*/
	public long getCompanyId() {
		return _socialEquityHistory.getCompanyId();
	}

	/**
	* Sets the company ID of this social equity history.
	*
	* @param companyId the company ID of this social equity history
	*/
	public void setCompanyId(long companyId) {
		_socialEquityHistory.setCompanyId(companyId);
	}

	/**
	* Returns the user ID of this social equity history.
	*
	* @return the user ID of this social equity history
	*/
	public long getUserId() {
		return _socialEquityHistory.getUserId();
	}

	/**
	* Sets the user ID of this social equity history.
	*
	* @param userId the user ID of this social equity history
	*/
	public void setUserId(long userId) {
		_socialEquityHistory.setUserId(userId);
	}

	/**
	* Returns the user uuid of this social equity history.
	*
	* @return the user uuid of this social equity history
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityHistory.getUserUuid();
	}

	/**
	* Sets the user uuid of this social equity history.
	*
	* @param userUuid the user uuid of this social equity history
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_socialEquityHistory.setUserUuid(userUuid);
	}

	/**
	* Returns the create date of this social equity history.
	*
	* @return the create date of this social equity history
	*/
	public java.util.Date getCreateDate() {
		return _socialEquityHistory.getCreateDate();
	}

	/**
	* Sets the create date of this social equity history.
	*
	* @param createDate the create date of this social equity history
	*/
	public void setCreateDate(java.util.Date createDate) {
		_socialEquityHistory.setCreateDate(createDate);
	}

	/**
	* Returns the personal equity of this social equity history.
	*
	* @return the personal equity of this social equity history
	*/
	public int getPersonalEquity() {
		return _socialEquityHistory.getPersonalEquity();
	}

	/**
	* Sets the personal equity of this social equity history.
	*
	* @param personalEquity the personal equity of this social equity history
	*/
	public void setPersonalEquity(int personalEquity) {
		_socialEquityHistory.setPersonalEquity(personalEquity);
	}

	public boolean isNew() {
		return _socialEquityHistory.isNew();
	}

	public void setNew(boolean n) {
		_socialEquityHistory.setNew(n);
	}

	public boolean isCachedModel() {
		return _socialEquityHistory.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_socialEquityHistory.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _socialEquityHistory.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _socialEquityHistory.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_socialEquityHistory.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _socialEquityHistory.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_socialEquityHistory.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new SocialEquityHistoryWrapper((SocialEquityHistory)_socialEquityHistory.clone());
	}

	public int compareTo(
		com.liferay.portlet.social.model.SocialEquityHistory socialEquityHistory) {
		return _socialEquityHistory.compareTo(socialEquityHistory);
	}

	@Override
	public int hashCode() {
		return _socialEquityHistory.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.portlet.social.model.SocialEquityHistory> toCacheModel() {
		return _socialEquityHistory.toCacheModel();
	}

	public com.liferay.portlet.social.model.SocialEquityHistory toEscapedModel() {
		return new SocialEquityHistoryWrapper(_socialEquityHistory.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _socialEquityHistory.toString();
	}

	public java.lang.String toXmlString() {
		return _socialEquityHistory.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_socialEquityHistory.persist();
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public SocialEquityHistory getWrappedSocialEquityHistory() {
		return _socialEquityHistory;
	}

	public SocialEquityHistory getWrappedModel() {
		return _socialEquityHistory;
	}

	public void resetOriginalValues() {
		_socialEquityHistory.resetOriginalValues();
	}

	private SocialEquityHistory _socialEquityHistory;
}