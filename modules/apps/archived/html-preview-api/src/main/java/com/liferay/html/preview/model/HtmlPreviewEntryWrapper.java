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

package com.liferay.html.preview.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link HtmlPreviewEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see HtmlPreviewEntry
 * @generated
 */
public class HtmlPreviewEntryWrapper
	extends BaseModelWrapper<HtmlPreviewEntry>
	implements HtmlPreviewEntry, ModelWrapper<HtmlPreviewEntry> {

	public HtmlPreviewEntryWrapper(HtmlPreviewEntry htmlPreviewEntry) {
		super(htmlPreviewEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("htmlPreviewEntryId", getHtmlPreviewEntryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("fileEntryId", getFileEntryId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long htmlPreviewEntryId = (Long)attributes.get("htmlPreviewEntryId");

		if (htmlPreviewEntryId != null) {
			setHtmlPreviewEntryId(htmlPreviewEntryId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
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

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		Long fileEntryId = (Long)attributes.get("fileEntryId");

		if (fileEntryId != null) {
			setFileEntryId(fileEntryId);
		}
	}

	/**
	 * Returns the fully qualified class name of this html preview entry.
	 *
	 * @return the fully qualified class name of this html preview entry
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the class name ID of this html preview entry.
	 *
	 * @return the class name ID of this html preview entry
	 */
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	 * Returns the class pk of this html preview entry.
	 *
	 * @return the class pk of this html preview entry
	 */
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	 * Returns the company ID of this html preview entry.
	 *
	 * @return the company ID of this html preview entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this html preview entry.
	 *
	 * @return the create date of this html preview entry
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the file entry ID of this html preview entry.
	 *
	 * @return the file entry ID of this html preview entry
	 */
	@Override
	public long getFileEntryId() {
		return model.getFileEntryId();
	}

	/**
	 * Returns the group ID of this html preview entry.
	 *
	 * @return the group ID of this html preview entry
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the html preview entry ID of this html preview entry.
	 *
	 * @return the html preview entry ID of this html preview entry
	 */
	@Override
	public long getHtmlPreviewEntryId() {
		return model.getHtmlPreviewEntryId();
	}

	@Override
	public String getImagePreviewURL(
		com.liferay.portal.kernel.theme.ThemeDisplay themeDisplay) {

		return model.getImagePreviewURL(themeDisplay);
	}

	/**
	 * Returns the modified date of this html preview entry.
	 *
	 * @return the modified date of this html preview entry
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the primary key of this html preview entry.
	 *
	 * @return the primary key of this html preview entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this html preview entry.
	 *
	 * @return the user ID of this html preview entry
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this html preview entry.
	 *
	 * @return the user name of this html preview entry
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this html preview entry.
	 *
	 * @return the user uuid of this html preview entry
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	@Override
	public void persist() {
		model.persist();
	}

	@Override
	public void setClassName(String className) {
		model.setClassName(className);
	}

	/**
	 * Sets the class name ID of this html preview entry.
	 *
	 * @param classNameId the class name ID of this html preview entry
	 */
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	 * Sets the class pk of this html preview entry.
	 *
	 * @param classPK the class pk of this html preview entry
	 */
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	 * Sets the company ID of this html preview entry.
	 *
	 * @param companyId the company ID of this html preview entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this html preview entry.
	 *
	 * @param createDate the create date of this html preview entry
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the file entry ID of this html preview entry.
	 *
	 * @param fileEntryId the file entry ID of this html preview entry
	 */
	@Override
	public void setFileEntryId(long fileEntryId) {
		model.setFileEntryId(fileEntryId);
	}

	/**
	 * Sets the group ID of this html preview entry.
	 *
	 * @param groupId the group ID of this html preview entry
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the html preview entry ID of this html preview entry.
	 *
	 * @param htmlPreviewEntryId the html preview entry ID of this html preview entry
	 */
	@Override
	public void setHtmlPreviewEntryId(long htmlPreviewEntryId) {
		model.setHtmlPreviewEntryId(htmlPreviewEntryId);
	}

	/**
	 * Sets the modified date of this html preview entry.
	 *
	 * @param modifiedDate the modified date of this html preview entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the primary key of this html preview entry.
	 *
	 * @param primaryKey the primary key of this html preview entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this html preview entry.
	 *
	 * @param userId the user ID of this html preview entry
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this html preview entry.
	 *
	 * @param userName the user name of this html preview entry
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this html preview entry.
	 *
	 * @param userUuid the user uuid of this html preview entry
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected HtmlPreviewEntryWrapper wrap(HtmlPreviewEntry htmlPreviewEntry) {
		return new HtmlPreviewEntryWrapper(htmlPreviewEntry);
	}

}