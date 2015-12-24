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

package com.liferay.portlet.announcements.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link AnnouncementsDelivery}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AnnouncementsDelivery
 * @generated
 */
@ProviderType
public class AnnouncementsDeliveryWrapper implements AnnouncementsDelivery,
	ModelWrapper<AnnouncementsDelivery> {
	public AnnouncementsDeliveryWrapper(
		AnnouncementsDelivery announcementsDelivery) {
		_announcementsDelivery = announcementsDelivery;
	}

	@Override
	public Class<?> getModelClass() {
		return AnnouncementsDelivery.class;
	}

	@Override
	public String getModelClassName() {
		return AnnouncementsDelivery.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("deliveryId", getDeliveryId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("type", getType());
		attributes.put("email", getEmail());
		attributes.put("sms", getSms());
		attributes.put("website", getWebsite());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long deliveryId = (Long)attributes.get("deliveryId");

		if (deliveryId != null) {
			setDeliveryId(deliveryId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String type = (String)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		Boolean email = (Boolean)attributes.get("email");

		if (email != null) {
			setEmail(email);
		}

		Boolean sms = (Boolean)attributes.get("sms");

		if (sms != null) {
			setSms(sms);
		}

		Boolean website = (Boolean)attributes.get("website");

		if (website != null) {
			setWebsite(website);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new AnnouncementsDeliveryWrapper((AnnouncementsDelivery)_announcementsDelivery.clone());
	}

	@Override
	public int compareTo(
		com.liferay.portlet.announcements.model.AnnouncementsDelivery announcementsDelivery) {
		return _announcementsDelivery.compareTo(announcementsDelivery);
	}

	/**
	* Returns the company ID of this announcements delivery.
	*
	* @return the company ID of this announcements delivery
	*/
	@Override
	public long getCompanyId() {
		return _announcementsDelivery.getCompanyId();
	}

	/**
	* Returns the delivery ID of this announcements delivery.
	*
	* @return the delivery ID of this announcements delivery
	*/
	@Override
	public long getDeliveryId() {
		return _announcementsDelivery.getDeliveryId();
	}

	/**
	* Returns the email of this announcements delivery.
	*
	* @return the email of this announcements delivery
	*/
	@Override
	public boolean getEmail() {
		return _announcementsDelivery.getEmail();
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _announcementsDelivery.getExpandoBridge();
	}

	/**
	* Returns the primary key of this announcements delivery.
	*
	* @return the primary key of this announcements delivery
	*/
	@Override
	public long getPrimaryKey() {
		return _announcementsDelivery.getPrimaryKey();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _announcementsDelivery.getPrimaryKeyObj();
	}

	/**
	* Returns the sms of this announcements delivery.
	*
	* @return the sms of this announcements delivery
	*/
	@Override
	public boolean getSms() {
		return _announcementsDelivery.getSms();
	}

	/**
	* Returns the type of this announcements delivery.
	*
	* @return the type of this announcements delivery
	*/
	@Override
	public java.lang.String getType() {
		return _announcementsDelivery.getType();
	}

	/**
	* Returns the user ID of this announcements delivery.
	*
	* @return the user ID of this announcements delivery
	*/
	@Override
	public long getUserId() {
		return _announcementsDelivery.getUserId();
	}

	/**
	* Returns the user uuid of this announcements delivery.
	*
	* @return the user uuid of this announcements delivery
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _announcementsDelivery.getUserUuid();
	}

	/**
	* Returns the website of this announcements delivery.
	*
	* @return the website of this announcements delivery
	*/
	@Override
	public boolean getWebsite() {
		return _announcementsDelivery.getWebsite();
	}

	@Override
	public int hashCode() {
		return _announcementsDelivery.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _announcementsDelivery.isCachedModel();
	}

	/**
	* Returns <code>true</code> if this announcements delivery is email.
	*
	* @return <code>true</code> if this announcements delivery is email; <code>false</code> otherwise
	*/
	@Override
	public boolean isEmail() {
		return _announcementsDelivery.isEmail();
	}

	@Override
	public boolean isEscapedModel() {
		return _announcementsDelivery.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _announcementsDelivery.isNew();
	}

	/**
	* Returns <code>true</code> if this announcements delivery is sms.
	*
	* @return <code>true</code> if this announcements delivery is sms; <code>false</code> otherwise
	*/
	@Override
	public boolean isSms() {
		return _announcementsDelivery.isSms();
	}

	/**
	* Returns <code>true</code> if this announcements delivery is website.
	*
	* @return <code>true</code> if this announcements delivery is website; <code>false</code> otherwise
	*/
	@Override
	public boolean isWebsite() {
		return _announcementsDelivery.isWebsite();
	}

	@Override
	public void persist() {
		_announcementsDelivery.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_announcementsDelivery.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this announcements delivery.
	*
	* @param companyId the company ID of this announcements delivery
	*/
	@Override
	public void setCompanyId(long companyId) {
		_announcementsDelivery.setCompanyId(companyId);
	}

	/**
	* Sets the delivery ID of this announcements delivery.
	*
	* @param deliveryId the delivery ID of this announcements delivery
	*/
	@Override
	public void setDeliveryId(long deliveryId) {
		_announcementsDelivery.setDeliveryId(deliveryId);
	}

	/**
	* Sets whether this announcements delivery is email.
	*
	* @param email the email of this announcements delivery
	*/
	@Override
	public void setEmail(boolean email) {
		_announcementsDelivery.setEmail(email);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_announcementsDelivery.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_announcementsDelivery.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_announcementsDelivery.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public void setNew(boolean n) {
		_announcementsDelivery.setNew(n);
	}

	/**
	* Sets the primary key of this announcements delivery.
	*
	* @param primaryKey the primary key of this announcements delivery
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_announcementsDelivery.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_announcementsDelivery.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets whether this announcements delivery is sms.
	*
	* @param sms the sms of this announcements delivery
	*/
	@Override
	public void setSms(boolean sms) {
		_announcementsDelivery.setSms(sms);
	}

	/**
	* Sets the type of this announcements delivery.
	*
	* @param type the type of this announcements delivery
	*/
	@Override
	public void setType(java.lang.String type) {
		_announcementsDelivery.setType(type);
	}

	/**
	* Sets the user ID of this announcements delivery.
	*
	* @param userId the user ID of this announcements delivery
	*/
	@Override
	public void setUserId(long userId) {
		_announcementsDelivery.setUserId(userId);
	}

	/**
	* Sets the user uuid of this announcements delivery.
	*
	* @param userUuid the user uuid of this announcements delivery
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_announcementsDelivery.setUserUuid(userUuid);
	}

	/**
	* Sets whether this announcements delivery is website.
	*
	* @param website the website of this announcements delivery
	*/
	@Override
	public void setWebsite(boolean website) {
		_announcementsDelivery.setWebsite(website);
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portlet.announcements.model.AnnouncementsDelivery> toCacheModel() {
		return _announcementsDelivery.toCacheModel();
	}

	@Override
	public com.liferay.portlet.announcements.model.AnnouncementsDelivery toEscapedModel() {
		return new AnnouncementsDeliveryWrapper(_announcementsDelivery.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _announcementsDelivery.toString();
	}

	@Override
	public com.liferay.portlet.announcements.model.AnnouncementsDelivery toUnescapedModel() {
		return new AnnouncementsDeliveryWrapper(_announcementsDelivery.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _announcementsDelivery.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AnnouncementsDeliveryWrapper)) {
			return false;
		}

		AnnouncementsDeliveryWrapper announcementsDeliveryWrapper = (AnnouncementsDeliveryWrapper)obj;

		if (Validator.equals(_announcementsDelivery,
					announcementsDeliveryWrapper._announcementsDelivery)) {
			return true;
		}

		return false;
	}

	@Override
	public AnnouncementsDelivery getWrappedModel() {
		return _announcementsDelivery;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _announcementsDelivery.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _announcementsDelivery.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_announcementsDelivery.resetOriginalValues();
	}

	private final AnnouncementsDelivery _announcementsDelivery;
}