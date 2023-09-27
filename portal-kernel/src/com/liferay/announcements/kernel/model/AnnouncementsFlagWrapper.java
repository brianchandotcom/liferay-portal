/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.announcements.kernel.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * <p>
 * This class is a wrapper for {@link AnnouncementsFlag}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AnnouncementsFlag
 * @generated
 */
public class AnnouncementsFlagWrapper
	extends BaseModelWrapper<AnnouncementsFlag>
	implements AnnouncementsFlag, ModelWrapper<AnnouncementsFlag> {

	public AnnouncementsFlagWrapper(AnnouncementsFlag announcementsFlag) {
		super(announcementsFlag);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("flagId", getFlagId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("createDate", getCreateDate());
		attributes.put("entryId", getEntryId());
		attributes.put("value", getValue());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long ctCollectionId = (Long)attributes.get("ctCollectionId");

		if (ctCollectionId != null) {
			setCtCollectionId(ctCollectionId);
		}

		Long flagId = (Long)attributes.get("flagId");

		if (flagId != null) {
			setFlagId(flagId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Long entryId = (Long)attributes.get("entryId");

		if (entryId != null) {
			setEntryId(entryId);
		}

		Integer value = (Integer)attributes.get("value");

		if (value != null) {
			setValue(value);
		}
	}

	@Override
	public AnnouncementsFlag cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this announcements flag.
	 *
	 * @return the company ID of this announcements flag
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this announcements flag.
	 *
	 * @return the create date of this announcements flag
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct collection ID of this announcements flag.
	 *
	 * @return the ct collection ID of this announcements flag
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the entry ID of this announcements flag.
	 *
	 * @return the entry ID of this announcements flag
	 */
	@Override
	public long getEntryId() {
		return model.getEntryId();
	}

	/**
	 * Returns the flag ID of this announcements flag.
	 *
	 * @return the flag ID of this announcements flag
	 */
	@Override
	public long getFlagId() {
		return model.getFlagId();
	}

	/**
	 * Returns the mvcc version of this announcements flag.
	 *
	 * @return the mvcc version of this announcements flag
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this announcements flag.
	 *
	 * @return the primary key of this announcements flag
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this announcements flag.
	 *
	 * @return the user ID of this announcements flag
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user uuid of this announcements flag.
	 *
	 * @return the user uuid of this announcements flag
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the value of this announcements flag.
	 *
	 * @return the value of this announcements flag
	 */
	@Override
	public int getValue() {
		return model.getValue();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this announcements flag.
	 *
	 * @param companyId the company ID of this announcements flag
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this announcements flag.
	 *
	 * @param createDate the create date of this announcements flag
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct collection ID of this announcements flag.
	 *
	 * @param ctCollectionId the ct collection ID of this announcements flag
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the entry ID of this announcements flag.
	 *
	 * @param entryId the entry ID of this announcements flag
	 */
	@Override
	public void setEntryId(long entryId) {
		model.setEntryId(entryId);
	}

	/**
	 * Sets the flag ID of this announcements flag.
	 *
	 * @param flagId the flag ID of this announcements flag
	 */
	@Override
	public void setFlagId(long flagId) {
		model.setFlagId(flagId);
	}

	/**
	 * Sets the mvcc version of this announcements flag.
	 *
	 * @param mvccVersion the mvcc version of this announcements flag
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this announcements flag.
	 *
	 * @param primaryKey the primary key of this announcements flag
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this announcements flag.
	 *
	 * @param userId the user ID of this announcements flag
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user uuid of this announcements flag.
	 *
	 * @param userUuid the user uuid of this announcements flag
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the value of this announcements flag.
	 *
	 * @param value the value of this announcements flag
	 */
	@Override
	public void setValue(int value) {
		model.setValue(value);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	public Map<String, Function<AnnouncementsFlag, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<AnnouncementsFlag, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	protected AnnouncementsFlagWrapper wrap(
		AnnouncementsFlag announcementsFlag) {

		return new AnnouncementsFlagWrapper(announcementsFlag);
	}

}