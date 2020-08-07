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

package com.liferay.commerce.product.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link CPFriendlyURLEntry}.
 * </p>
 *
 * @author Marco Leo
 * @see CPFriendlyURLEntry
 * @deprecated As of Athanasius (7.3.x), replaced by {@link
 com.liferay.friendly.url.model.impl.FriendlyURLEntryImpl}
 * @generated
 */
@Deprecated
public class CPFriendlyURLEntryWrapper
	extends BaseModelWrapper<CPFriendlyURLEntry>
	implements CPFriendlyURLEntry, ModelWrapper<CPFriendlyURLEntry> {

	public CPFriendlyURLEntryWrapper(CPFriendlyURLEntry cpFriendlyURLEntry) {
		super(cpFriendlyURLEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("CPFriendlyURLEntryId", getCPFriendlyURLEntryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("languageId", getLanguageId());
		attributes.put("urlTitle", getUrlTitle());
		attributes.put("main", isMain());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long CPFriendlyURLEntryId = (Long)attributes.get(
			"CPFriendlyURLEntryId");

		if (CPFriendlyURLEntryId != null) {
			setCPFriendlyURLEntryId(CPFriendlyURLEntryId);
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

		String languageId = (String)attributes.get("languageId");

		if (languageId != null) {
			setLanguageId(languageId);
		}

		String urlTitle = (String)attributes.get("urlTitle");

		if (urlTitle != null) {
			setUrlTitle(urlTitle);
		}

		Boolean main = (Boolean)attributes.get("main");

		if (main != null) {
			setMain(main);
		}
	}

	/**
	 * Returns the fully qualified class name of this cp friendly url entry.
	 *
	 * @return the fully qualified class name of this cp friendly url entry
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the class name ID of this cp friendly url entry.
	 *
	 * @return the class name ID of this cp friendly url entry
	 */
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	 * Returns the class pk of this cp friendly url entry.
	 *
	 * @return the class pk of this cp friendly url entry
	 */
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	 * Returns the company ID of this cp friendly url entry.
	 *
	 * @return the company ID of this cp friendly url entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the cp friendly url entry ID of this cp friendly url entry.
	 *
	 * @return the cp friendly url entry ID of this cp friendly url entry
	 */
	@Override
	public long getCPFriendlyURLEntryId() {
		return model.getCPFriendlyURLEntryId();
	}

	/**
	 * Returns the create date of this cp friendly url entry.
	 *
	 * @return the create date of this cp friendly url entry
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the group ID of this cp friendly url entry.
	 *
	 * @return the group ID of this cp friendly url entry
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the language ID of this cp friendly url entry.
	 *
	 * @return the language ID of this cp friendly url entry
	 */
	@Override
	public String getLanguageId() {
		return model.getLanguageId();
	}

	@Override
	public java.util.Locale getLocale() {
		return model.getLocale();
	}

	/**
	 * Returns the main of this cp friendly url entry.
	 *
	 * @return the main of this cp friendly url entry
	 */
	@Override
	public boolean getMain() {
		return model.getMain();
	}

	/**
	 * Returns the modified date of this cp friendly url entry.
	 *
	 * @return the modified date of this cp friendly url entry
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this cp friendly url entry.
	 *
	 * @return the mvcc version of this cp friendly url entry
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this cp friendly url entry.
	 *
	 * @return the primary key of this cp friendly url entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the url title of this cp friendly url entry.
	 *
	 * @return the url title of this cp friendly url entry
	 */
	@Override
	public String getUrlTitle() {
		return model.getUrlTitle();
	}

	/**
	 * Returns the user ID of this cp friendly url entry.
	 *
	 * @return the user ID of this cp friendly url entry
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this cp friendly url entry.
	 *
	 * @return the user name of this cp friendly url entry
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this cp friendly url entry.
	 *
	 * @return the user uuid of this cp friendly url entry
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this cp friendly url entry.
	 *
	 * @return the uuid of this cp friendly url entry
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns <code>true</code> if this cp friendly url entry is main.
	 *
	 * @return <code>true</code> if this cp friendly url entry is main; <code>false</code> otherwise
	 */
	@Override
	public boolean isMain() {
		return model.isMain();
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
	 * Sets the class name ID of this cp friendly url entry.
	 *
	 * @param classNameId the class name ID of this cp friendly url entry
	 */
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	 * Sets the class pk of this cp friendly url entry.
	 *
	 * @param classPK the class pk of this cp friendly url entry
	 */
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	 * Sets the company ID of this cp friendly url entry.
	 *
	 * @param companyId the company ID of this cp friendly url entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the cp friendly url entry ID of this cp friendly url entry.
	 *
	 * @param CPFriendlyURLEntryId the cp friendly url entry ID of this cp friendly url entry
	 */
	@Override
	public void setCPFriendlyURLEntryId(long CPFriendlyURLEntryId) {
		model.setCPFriendlyURLEntryId(CPFriendlyURLEntryId);
	}

	/**
	 * Sets the create date of this cp friendly url entry.
	 *
	 * @param createDate the create date of this cp friendly url entry
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the group ID of this cp friendly url entry.
	 *
	 * @param groupId the group ID of this cp friendly url entry
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the language ID of this cp friendly url entry.
	 *
	 * @param languageId the language ID of this cp friendly url entry
	 */
	@Override
	public void setLanguageId(String languageId) {
		model.setLanguageId(languageId);
	}

	/**
	 * Sets whether this cp friendly url entry is main.
	 *
	 * @param main the main of this cp friendly url entry
	 */
	@Override
	public void setMain(boolean main) {
		model.setMain(main);
	}

	/**
	 * Sets the modified date of this cp friendly url entry.
	 *
	 * @param modifiedDate the modified date of this cp friendly url entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this cp friendly url entry.
	 *
	 * @param mvccVersion the mvcc version of this cp friendly url entry
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this cp friendly url entry.
	 *
	 * @param primaryKey the primary key of this cp friendly url entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the url title of this cp friendly url entry.
	 *
	 * @param urlTitle the url title of this cp friendly url entry
	 */
	@Override
	public void setUrlTitle(String urlTitle) {
		model.setUrlTitle(urlTitle);
	}

	/**
	 * Sets the user ID of this cp friendly url entry.
	 *
	 * @param userId the user ID of this cp friendly url entry
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this cp friendly url entry.
	 *
	 * @param userName the user name of this cp friendly url entry
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this cp friendly url entry.
	 *
	 * @param userUuid the user uuid of this cp friendly url entry
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this cp friendly url entry.
	 *
	 * @param uuid the uuid of this cp friendly url entry
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected CPFriendlyURLEntryWrapper wrap(
		CPFriendlyURLEntry cpFriendlyURLEntry) {

		return new CPFriendlyURLEntryWrapper(cpFriendlyURLEntry);
	}

}