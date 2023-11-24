/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.virtual.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link CPDVirtualSettingFileEntry}.
 * </p>
 *
 * @author Marco Leo
 * @see CPDVirtualSettingFileEntry
 * @generated
 */
public class CPDVirtualSettingFileEntryWrapper
	extends BaseModelWrapper<CPDVirtualSettingFileEntry>
	implements CPDVirtualSettingFileEntry,
			   ModelWrapper<CPDVirtualSettingFileEntry> {

	public CPDVirtualSettingFileEntryWrapper(
		CPDVirtualSettingFileEntry cpdVirtualSettingFileEntry) {

		super(cpdVirtualSettingFileEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put(
			"CPDefinitionVirtualSettingFileEntryId",
			getCPDefinitionVirtualSettingFileEntryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put(
			"CPDefinitionVirtualSettingId", getCPDefinitionVirtualSettingId());
		attributes.put("fileEntryId", getFileEntryId());
		attributes.put("url", getUrl());
		attributes.put("version", getVersion());

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

		Long CPDefinitionVirtualSettingFileEntryId = (Long)attributes.get(
			"CPDefinitionVirtualSettingFileEntryId");

		if (CPDefinitionVirtualSettingFileEntryId != null) {
			setCPDefinitionVirtualSettingFileEntryId(
				CPDefinitionVirtualSettingFileEntryId);
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

		Long CPDefinitionVirtualSettingId = (Long)attributes.get(
			"CPDefinitionVirtualSettingId");

		if (CPDefinitionVirtualSettingId != null) {
			setCPDefinitionVirtualSettingId(CPDefinitionVirtualSettingId);
		}

		Long fileEntryId = (Long)attributes.get("fileEntryId");

		if (fileEntryId != null) {
			setFileEntryId(fileEntryId);
		}

		String url = (String)attributes.get("url");

		if (url != null) {
			setUrl(url);
		}

		String version = (String)attributes.get("version");

		if (version != null) {
			setVersion(version);
		}
	}

	@Override
	public CPDVirtualSettingFileEntry cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this cpd virtual setting file entry.
	 *
	 * @return the company ID of this cpd virtual setting file entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	@Override
	public CPDefinitionVirtualSetting getCPDefinitionVirtualSetting()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getCPDefinitionVirtualSetting();
	}

	/**
	 * Returns the cp definition virtual setting file entry ID of this cpd virtual setting file entry.
	 *
	 * @return the cp definition virtual setting file entry ID of this cpd virtual setting file entry
	 */
	@Override
	public long getCPDefinitionVirtualSettingFileEntryId() {
		return model.getCPDefinitionVirtualSettingFileEntryId();
	}

	/**
	 * Returns the cp definition virtual setting ID of this cpd virtual setting file entry.
	 *
	 * @return the cp definition virtual setting ID of this cpd virtual setting file entry
	 */
	@Override
	public long getCPDefinitionVirtualSettingId() {
		return model.getCPDefinitionVirtualSettingId();
	}

	/**
	 * Returns the create date of this cpd virtual setting file entry.
	 *
	 * @return the create date of this cpd virtual setting file entry
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the file entry ID of this cpd virtual setting file entry.
	 *
	 * @return the file entry ID of this cpd virtual setting file entry
	 */
	@Override
	public long getFileEntryId() {
		return model.getFileEntryId();
	}

	/**
	 * Returns the group ID of this cpd virtual setting file entry.
	 *
	 * @return the group ID of this cpd virtual setting file entry
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the modified date of this cpd virtual setting file entry.
	 *
	 * @return the modified date of this cpd virtual setting file entry
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this cpd virtual setting file entry.
	 *
	 * @return the mvcc version of this cpd virtual setting file entry
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this cpd virtual setting file entry.
	 *
	 * @return the primary key of this cpd virtual setting file entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the url of this cpd virtual setting file entry.
	 *
	 * @return the url of this cpd virtual setting file entry
	 */
	@Override
	public String getUrl() {
		return model.getUrl();
	}

	/**
	 * Returns the user ID of this cpd virtual setting file entry.
	 *
	 * @return the user ID of this cpd virtual setting file entry
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this cpd virtual setting file entry.
	 *
	 * @return the user name of this cpd virtual setting file entry
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this cpd virtual setting file entry.
	 *
	 * @return the user uuid of this cpd virtual setting file entry
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this cpd virtual setting file entry.
	 *
	 * @return the uuid of this cpd virtual setting file entry
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns the version of this cpd virtual setting file entry.
	 *
	 * @return the version of this cpd virtual setting file entry
	 */
	@Override
	public String getVersion() {
		return model.getVersion();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this cpd virtual setting file entry.
	 *
	 * @param companyId the company ID of this cpd virtual setting file entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the cp definition virtual setting file entry ID of this cpd virtual setting file entry.
	 *
	 * @param CPDefinitionVirtualSettingFileEntryId the cp definition virtual setting file entry ID of this cpd virtual setting file entry
	 */
	@Override
	public void setCPDefinitionVirtualSettingFileEntryId(
		long CPDefinitionVirtualSettingFileEntryId) {

		model.setCPDefinitionVirtualSettingFileEntryId(
			CPDefinitionVirtualSettingFileEntryId);
	}

	/**
	 * Sets the cp definition virtual setting ID of this cpd virtual setting file entry.
	 *
	 * @param CPDefinitionVirtualSettingId the cp definition virtual setting ID of this cpd virtual setting file entry
	 */
	@Override
	public void setCPDefinitionVirtualSettingId(
		long CPDefinitionVirtualSettingId) {

		model.setCPDefinitionVirtualSettingId(CPDefinitionVirtualSettingId);
	}

	/**
	 * Sets the create date of this cpd virtual setting file entry.
	 *
	 * @param createDate the create date of this cpd virtual setting file entry
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the file entry ID of this cpd virtual setting file entry.
	 *
	 * @param fileEntryId the file entry ID of this cpd virtual setting file entry
	 */
	@Override
	public void setFileEntryId(long fileEntryId) {
		model.setFileEntryId(fileEntryId);
	}

	/**
	 * Sets the group ID of this cpd virtual setting file entry.
	 *
	 * @param groupId the group ID of this cpd virtual setting file entry
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the modified date of this cpd virtual setting file entry.
	 *
	 * @param modifiedDate the modified date of this cpd virtual setting file entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this cpd virtual setting file entry.
	 *
	 * @param mvccVersion the mvcc version of this cpd virtual setting file entry
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this cpd virtual setting file entry.
	 *
	 * @param primaryKey the primary key of this cpd virtual setting file entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the url of this cpd virtual setting file entry.
	 *
	 * @param url the url of this cpd virtual setting file entry
	 */
	@Override
	public void setUrl(String url) {
		model.setUrl(url);
	}

	/**
	 * Sets the user ID of this cpd virtual setting file entry.
	 *
	 * @param userId the user ID of this cpd virtual setting file entry
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this cpd virtual setting file entry.
	 *
	 * @param userName the user name of this cpd virtual setting file entry
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this cpd virtual setting file entry.
	 *
	 * @param userUuid the user uuid of this cpd virtual setting file entry
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this cpd virtual setting file entry.
	 *
	 * @param uuid the uuid of this cpd virtual setting file entry
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	/**
	 * Sets the version of this cpd virtual setting file entry.
	 *
	 * @param version the version of this cpd virtual setting file entry
	 */
	@Override
	public void setVersion(String version) {
		model.setVersion(version);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected CPDVirtualSettingFileEntryWrapper wrap(
		CPDVirtualSettingFileEntry cpdVirtualSettingFileEntry) {

		return new CPDVirtualSettingFileEntryWrapper(
			cpdVirtualSettingFileEntry);
	}

}