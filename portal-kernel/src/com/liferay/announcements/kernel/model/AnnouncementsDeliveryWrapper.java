/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.announcements.kernel.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

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
public class AnnouncementsDeliveryWrapper
	extends BaseModelWrapper<AnnouncementsDelivery>
	implements AnnouncementsDelivery, ModelWrapper<AnnouncementsDelivery> {

	public AnnouncementsDeliveryWrapper(
		AnnouncementsDelivery announcementsDelivery) {

		super(announcementsDelivery);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("deliveryId", getDeliveryId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("type", getType());
		attributes.put("email", isEmail());
		attributes.put("sms", isSms());
		attributes.put("website", isWebsite());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

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
	public AnnouncementsDelivery cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this announcements delivery.
	 *
	 * @return the company ID of this announcements delivery
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the delivery ID of this announcements delivery.
	 *
	 * @return the delivery ID of this announcements delivery
	 */
	@Override
	public long getDeliveryId() {
		return model.getDeliveryId();
	}

	/**
	 * Returns the email of this announcements delivery.
	 *
	 * @return the email of this announcements delivery
	 */
	@Override
	public boolean getEmail() {
		return model.getEmail();
	}

	/**
	 * Returns the mvcc version of this announcements delivery.
	 *
	 * @return the mvcc version of this announcements delivery
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this announcements delivery.
	 *
	 * @return the primary key of this announcements delivery
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the sms of this announcements delivery.
	 *
	 * @return the sms of this announcements delivery
	 */
	@Override
	public boolean getSms() {
		return model.getSms();
	}

	/**
	 * Returns the type of this announcements delivery.
	 *
	 * @return the type of this announcements delivery
	 */
	@Override
	public String getType() {
		return model.getType();
	}

	/**
	 * Returns the user ID of this announcements delivery.
	 *
	 * @return the user ID of this announcements delivery
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user uuid of this announcements delivery.
	 *
	 * @return the user uuid of this announcements delivery
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the website of this announcements delivery.
	 *
	 * @return the website of this announcements delivery
	 */
	@Override
	public boolean getWebsite() {
		return model.getWebsite();
	}

	/**
	 * Returns <code>true</code> if this announcements delivery is email.
	 *
	 * @return <code>true</code> if this announcements delivery is email; <code>false</code> otherwise
	 */
	@Override
	public boolean isEmail() {
		return model.isEmail();
	}

	/**
	 * Returns <code>true</code> if this announcements delivery is sms.
	 *
	 * @return <code>true</code> if this announcements delivery is sms; <code>false</code> otherwise
	 */
	@Override
	public boolean isSms() {
		return model.isSms();
	}

	/**
	 * Returns <code>true</code> if this announcements delivery is website.
	 *
	 * @return <code>true</code> if this announcements delivery is website; <code>false</code> otherwise
	 */
	@Override
	public boolean isWebsite() {
		return model.isWebsite();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this announcements delivery.
	 *
	 * @param companyId the company ID of this announcements delivery
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the delivery ID of this announcements delivery.
	 *
	 * @param deliveryId the delivery ID of this announcements delivery
	 */
	@Override
	public void setDeliveryId(long deliveryId) {
		model.setDeliveryId(deliveryId);
	}

	/**
	 * Sets whether this announcements delivery is email.
	 *
	 * @param email the email of this announcements delivery
	 */
	@Override
	public void setEmail(boolean email) {
		model.setEmail(email);
	}

	/**
	 * Sets the mvcc version of this announcements delivery.
	 *
	 * @param mvccVersion the mvcc version of this announcements delivery
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this announcements delivery.
	 *
	 * @param primaryKey the primary key of this announcements delivery
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets whether this announcements delivery is sms.
	 *
	 * @param sms the sms of this announcements delivery
	 */
	@Override
	public void setSms(boolean sms) {
		model.setSms(sms);
	}

	/**
	 * Sets the type of this announcements delivery.
	 *
	 * @param type the type of this announcements delivery
	 */
	@Override
	public void setType(String type) {
		model.setType(type);
	}

	/**
	 * Sets the user ID of this announcements delivery.
	 *
	 * @param userId the user ID of this announcements delivery
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user uuid of this announcements delivery.
	 *
	 * @param userUuid the user uuid of this announcements delivery
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets whether this announcements delivery is website.
	 *
	 * @param website the website of this announcements delivery
	 */
	@Override
	public void setWebsite(boolean website) {
		model.setWebsite(website);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	protected AnnouncementsDeliveryWrapper wrap(
		AnnouncementsDelivery announcementsDelivery) {

		return new AnnouncementsDeliveryWrapper(announcementsDelivery);
	}

}