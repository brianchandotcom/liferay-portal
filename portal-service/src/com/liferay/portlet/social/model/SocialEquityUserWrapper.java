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
 * This class is a wrapper for {@link SocialEquityUser}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialEquityUser
 * @generated
 */
public class SocialEquityUserWrapper implements SocialEquityUser,
	ModelWrapper<SocialEquityUser> {
	public SocialEquityUserWrapper(SocialEquityUser socialEquityUser) {
		_socialEquityUser = socialEquityUser;
	}

	public Class<?> getModelClass() {
		return SocialEquityUser.class;
	}

	public String getModelClassName() {
		return SocialEquityUser.class.getName();
	}

	/**
	* Returns the primary key of this social equity user.
	*
	* @return the primary key of this social equity user
	*/
	public long getPrimaryKey() {
		return _socialEquityUser.getPrimaryKey();
	}

	/**
	* Sets the primary key of this social equity user.
	*
	* @param primaryKey the primary key of this social equity user
	*/
	public void setPrimaryKey(long primaryKey) {
		_socialEquityUser.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the equity user ID of this social equity user.
	*
	* @return the equity user ID of this social equity user
	*/
	public long getEquityUserId() {
		return _socialEquityUser.getEquityUserId();
	}

	/**
	* Sets the equity user ID of this social equity user.
	*
	* @param equityUserId the equity user ID of this social equity user
	*/
	public void setEquityUserId(long equityUserId) {
		_socialEquityUser.setEquityUserId(equityUserId);
	}

	/**
	* Returns the equity user uuid of this social equity user.
	*
	* @return the equity user uuid of this social equity user
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getEquityUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityUser.getEquityUserUuid();
	}

	/**
	* Sets the equity user uuid of this social equity user.
	*
	* @param equityUserUuid the equity user uuid of this social equity user
	*/
	public void setEquityUserUuid(java.lang.String equityUserUuid) {
		_socialEquityUser.setEquityUserUuid(equityUserUuid);
	}

	/**
	* Returns the group ID of this social equity user.
	*
	* @return the group ID of this social equity user
	*/
	public long getGroupId() {
		return _socialEquityUser.getGroupId();
	}

	/**
	* Sets the group ID of this social equity user.
	*
	* @param groupId the group ID of this social equity user
	*/
	public void setGroupId(long groupId) {
		_socialEquityUser.setGroupId(groupId);
	}

	/**
	* Returns the company ID of this social equity user.
	*
	* @return the company ID of this social equity user
	*/
	public long getCompanyId() {
		return _socialEquityUser.getCompanyId();
	}

	/**
	* Sets the company ID of this social equity user.
	*
	* @param companyId the company ID of this social equity user
	*/
	public void setCompanyId(long companyId) {
		_socialEquityUser.setCompanyId(companyId);
	}

	/**
	* Returns the user ID of this social equity user.
	*
	* @return the user ID of this social equity user
	*/
	public long getUserId() {
		return _socialEquityUser.getUserId();
	}

	/**
	* Sets the user ID of this social equity user.
	*
	* @param userId the user ID of this social equity user
	*/
	public void setUserId(long userId) {
		_socialEquityUser.setUserId(userId);
	}

	/**
	* Returns the user uuid of this social equity user.
	*
	* @return the user uuid of this social equity user
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityUser.getUserUuid();
	}

	/**
	* Sets the user uuid of this social equity user.
	*
	* @param userUuid the user uuid of this social equity user
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_socialEquityUser.setUserUuid(userUuid);
	}

	/**
	* Returns the contribution k of this social equity user.
	*
	* @return the contribution k of this social equity user
	*/
	public double getContributionK() {
		return _socialEquityUser.getContributionK();
	}

	/**
	* Sets the contribution k of this social equity user.
	*
	* @param contributionK the contribution k of this social equity user
	*/
	public void setContributionK(double contributionK) {
		_socialEquityUser.setContributionK(contributionK);
	}

	/**
	* Returns the contribution b of this social equity user.
	*
	* @return the contribution b of this social equity user
	*/
	public double getContributionB() {
		return _socialEquityUser.getContributionB();
	}

	/**
	* Sets the contribution b of this social equity user.
	*
	* @param contributionB the contribution b of this social equity user
	*/
	public void setContributionB(double contributionB) {
		_socialEquityUser.setContributionB(contributionB);
	}

	/**
	* Returns the participation k of this social equity user.
	*
	* @return the participation k of this social equity user
	*/
	public double getParticipationK() {
		return _socialEquityUser.getParticipationK();
	}

	/**
	* Sets the participation k of this social equity user.
	*
	* @param participationK the participation k of this social equity user
	*/
	public void setParticipationK(double participationK) {
		_socialEquityUser.setParticipationK(participationK);
	}

	/**
	* Returns the participation b of this social equity user.
	*
	* @return the participation b of this social equity user
	*/
	public double getParticipationB() {
		return _socialEquityUser.getParticipationB();
	}

	/**
	* Sets the participation b of this social equity user.
	*
	* @param participationB the participation b of this social equity user
	*/
	public void setParticipationB(double participationB) {
		_socialEquityUser.setParticipationB(participationB);
	}

	/**
	* Returns the rank of this social equity user.
	*
	* @return the rank of this social equity user
	*/
	public int getRank() {
		return _socialEquityUser.getRank();
	}

	/**
	* Sets the rank of this social equity user.
	*
	* @param rank the rank of this social equity user
	*/
	public void setRank(int rank) {
		_socialEquityUser.setRank(rank);
	}

	public boolean isNew() {
		return _socialEquityUser.isNew();
	}

	public void setNew(boolean n) {
		_socialEquityUser.setNew(n);
	}

	public boolean isCachedModel() {
		return _socialEquityUser.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_socialEquityUser.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _socialEquityUser.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _socialEquityUser.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_socialEquityUser.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _socialEquityUser.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_socialEquityUser.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new SocialEquityUserWrapper((SocialEquityUser)_socialEquityUser.clone());
	}

	public int compareTo(
		com.liferay.portlet.social.model.SocialEquityUser socialEquityUser) {
		return _socialEquityUser.compareTo(socialEquityUser);
	}

	@Override
	public int hashCode() {
		return _socialEquityUser.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.portlet.social.model.SocialEquityUser> toCacheModel() {
		return _socialEquityUser.toCacheModel();
	}

	public com.liferay.portlet.social.model.SocialEquityUser toEscapedModel() {
		return new SocialEquityUserWrapper(_socialEquityUser.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _socialEquityUser.toString();
	}

	public java.lang.String toXmlString() {
		return _socialEquityUser.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_socialEquityUser.persist();
	}

	public double getContributionEquity() {
		return _socialEquityUser.getContributionEquity();
	}

	public double getParticipationEquity() {
		return _socialEquityUser.getParticipationEquity();
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public SocialEquityUser getWrappedSocialEquityUser() {
		return _socialEquityUser;
	}

	public SocialEquityUser getWrappedModel() {
		return _socialEquityUser;
	}

	public void resetOriginalValues() {
		_socialEquityUser.resetOriginalValues();
	}

	private SocialEquityUser _socialEquityUser;
}