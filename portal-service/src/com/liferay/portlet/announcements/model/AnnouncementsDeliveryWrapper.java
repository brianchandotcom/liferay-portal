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

package com.liferay.portlet.announcements.model;

import com.liferay.portal.model.ModelWrapper;

/**
 * <p>
 * This class is a wrapper for {@link AnnouncementsDelivery}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AnnouncementsDelivery
 * @generated
 */
public class AnnouncementsDeliveryWrapper implements AnnouncementsDelivery,
	ModelWrapper<AnnouncementsDelivery> {
	public AnnouncementsDeliveryWrapper(
		AnnouncementsDelivery announcementsDelivery) {
		_announcementsDelivery = announcementsDelivery;
	}

	public Class<?> getModelClass() {
		return AnnouncementsDelivery.class;
	}

	public String getModelClassName() {
		return AnnouncementsDelivery.class.getName();
	}

	/**
	* Returns the primary key of this announcements delivery.
	*
	* @return the primary key of this announcements delivery
	*/
	public long getPrimaryKey() {
		return _announcementsDelivery.getPrimaryKey();
	}

	/**
	* Sets the primary key of this announcements delivery.
	*
	* @param primaryKey the primary key of this announcements delivery
	*/
	public void setPrimaryKey(long primaryKey) {
		_announcementsDelivery.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the delivery ID of this announcements delivery.
	*
	* @return the delivery ID of this announcements delivery
	*/
	public long getDeliveryId() {
		return _announcementsDelivery.getDeliveryId();
	}

	/**
	* Sets the delivery ID of this announcements delivery.
	*
	* @param deliveryId the delivery ID of this announcements delivery
	*/
	public void setDeliveryId(long deliveryId) {
		_announcementsDelivery.setDeliveryId(deliveryId);
	}

	/**
	* Returns the company ID of this announcements delivery.
	*
	* @return the company ID of this announcements delivery
	*/
	public long getCompanyId() {
		return _announcementsDelivery.getCompanyId();
	}

	/**
	* Sets the company ID of this announcements delivery.
	*
	* @param companyId the company ID of this announcements delivery
	*/
	public void setCompanyId(long companyId) {
		_announcementsDelivery.setCompanyId(companyId);
	}

	/**
	* Returns the user ID of this announcements delivery.
	*
	* @return the user ID of this announcements delivery
	*/
	public long getUserId() {
		return _announcementsDelivery.getUserId();
	}

	/**
	* Sets the user ID of this announcements delivery.
	*
	* @param userId the user ID of this announcements delivery
	*/
	public void setUserId(long userId) {
		_announcementsDelivery.setUserId(userId);
	}

	/**
	* Returns the user uuid of this announcements delivery.
	*
	* @return the user uuid of this announcements delivery
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _announcementsDelivery.getUserUuid();
	}

	/**
	* Sets the user uuid of this announcements delivery.
	*
	* @param userUuid the user uuid of this announcements delivery
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_announcementsDelivery.setUserUuid(userUuid);
	}

	/**
	* Returns the type of this announcements delivery.
	*
	* @return the type of this announcements delivery
	*/
	public java.lang.String getType() {
		return _announcementsDelivery.getType();
	}

	/**
	* Sets the type of this announcements delivery.
	*
	* @param type the type of this announcements delivery
	*/
	public void setType(java.lang.String type) {
		_announcementsDelivery.setType(type);
	}

	/**
	* Returns the email of this announcements delivery.
	*
	* @return the email of this announcements delivery
	*/
	public boolean getEmail() {
		return _announcementsDelivery.getEmail();
	}

	/**
	* Returns <code>true</code> if this announcements delivery is email.
	*
	* @return <code>true</code> if this announcements delivery is email; <code>false</code> otherwise
	*/
	public boolean isEmail() {
		return _announcementsDelivery.isEmail();
	}

	/**
	* Sets whether this announcements delivery is email.
	*
	* @param email the email of this announcements delivery
	*/
	public void setEmail(boolean email) {
		_announcementsDelivery.setEmail(email);
	}

	/**
	* Returns the sms of this announcements delivery.
	*
	* @return the sms of this announcements delivery
	*/
	public boolean getSms() {
		return _announcementsDelivery.getSms();
	}

	/**
	* Returns <code>true</code> if this announcements delivery is sms.
	*
	* @return <code>true</code> if this announcements delivery is sms; <code>false</code> otherwise
	*/
	public boolean isSms() {
		return _announcementsDelivery.isSms();
	}

	/**
	* Sets whether this announcements delivery is sms.
	*
	* @param sms the sms of this announcements delivery
	*/
	public void setSms(boolean sms) {
		_announcementsDelivery.setSms(sms);
	}

	/**
	* Returns the website of this announcements delivery.
	*
	* @return the website of this announcements delivery
	*/
	public boolean getWebsite() {
		return _announcementsDelivery.getWebsite();
	}

	/**
	* Returns <code>true</code> if this announcements delivery is website.
	*
	* @return <code>true</code> if this announcements delivery is website; <code>false</code> otherwise
	*/
	public boolean isWebsite() {
		return _announcementsDelivery.isWebsite();
	}

	/**
	* Sets whether this announcements delivery is website.
	*
	* @param website the website of this announcements delivery
	*/
	public void setWebsite(boolean website) {
		_announcementsDelivery.setWebsite(website);
	}

	public boolean isNew() {
		return _announcementsDelivery.isNew();
	}

	public void setNew(boolean n) {
		_announcementsDelivery.setNew(n);
	}

	public boolean isCachedModel() {
		return _announcementsDelivery.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_announcementsDelivery.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _announcementsDelivery.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _announcementsDelivery.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_announcementsDelivery.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _announcementsDelivery.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_announcementsDelivery.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new AnnouncementsDeliveryWrapper((AnnouncementsDelivery)_announcementsDelivery.clone());
	}

	public int compareTo(
		com.liferay.portlet.announcements.model.AnnouncementsDelivery announcementsDelivery) {
		return _announcementsDelivery.compareTo(announcementsDelivery);
	}

	@Override
	public int hashCode() {
		return _announcementsDelivery.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.portlet.announcements.model.AnnouncementsDelivery> toCacheModel() {
		return _announcementsDelivery.toCacheModel();
	}

	public com.liferay.portlet.announcements.model.AnnouncementsDelivery toEscapedModel() {
		return new AnnouncementsDeliveryWrapper(_announcementsDelivery.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _announcementsDelivery.toString();
	}

	public java.lang.String toXmlString() {
		return _announcementsDelivery.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_announcementsDelivery.persist();
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public AnnouncementsDelivery getWrappedAnnouncementsDelivery() {
		return _announcementsDelivery;
	}

	public AnnouncementsDelivery getWrappedModel() {
		return _announcementsDelivery;
	}

	public void resetOriginalValues() {
		_announcementsDelivery.resetOriginalValues();
	}

	private AnnouncementsDelivery _announcementsDelivery;
}