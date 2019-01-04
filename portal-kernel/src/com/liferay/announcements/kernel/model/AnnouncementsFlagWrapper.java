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

package com.liferay.announcements.kernel.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;

/**
 * <p>
 * This class is a wrapper for {@link AnnouncementsFlag}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AnnouncementsFlag
 * @generated
 */
@ProviderType
public class AnnouncementsFlagWrapper extends BaseModelWrapper<AnnouncementsFlag>
	implements AnnouncementsFlag, ModelWrapper<AnnouncementsFlag> {
	public AnnouncementsFlagWrapper(AnnouncementsFlag announcementsFlag) {
		super(announcementsFlag);
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
	protected AnnouncementsFlagWrapper wrap(AnnouncementsFlag announcementsFlag) {
		return new AnnouncementsFlagWrapper(announcementsFlag);
	}
}