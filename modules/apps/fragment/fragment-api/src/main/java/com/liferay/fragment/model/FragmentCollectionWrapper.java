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

package com.liferay.fragment.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * <p>
 * This class is a wrapper for {@link FragmentCollection}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FragmentCollection
 * @generated
 */
public class FragmentCollectionWrapper
	extends BaseModelWrapper<FragmentCollection>
	implements FragmentCollection, ModelWrapper<FragmentCollection> {

	public FragmentCollectionWrapper(FragmentCollection fragmentCollection) {
		super(fragmentCollection);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("uuid", getUuid());
		attributes.put("fragmentCollectionId", getFragmentCollectionId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("fragmentCollectionKey", getFragmentCollectionKey());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("lastPublishDate", getLastPublishDate());

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

		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long fragmentCollectionId = (Long)attributes.get(
			"fragmentCollectionId");

		if (fragmentCollectionId != null) {
			setFragmentCollectionId(fragmentCollectionId);
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

		String fragmentCollectionKey = (String)attributes.get(
			"fragmentCollectionKey");

		if (fragmentCollectionKey != null) {
			setFragmentCollectionKey(fragmentCollectionKey);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}
	}

	/**
	 * Returns the company ID of this fragment collection.
	 *
	 * @return the company ID of this fragment collection
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this fragment collection.
	 *
	 * @return the create date of this fragment collection
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct collection ID of this fragment collection.
	 *
	 * @return the ct collection ID of this fragment collection
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the description of this fragment collection.
	 *
	 * @return the description of this fragment collection
	 */
	@Override
	public String getDescription() {
		return model.getDescription();
	}

	/**
	 * Returns the fragment collection ID of this fragment collection.
	 *
	 * @return the fragment collection ID of this fragment collection
	 */
	@Override
	public long getFragmentCollectionId() {
		return model.getFragmentCollectionId();
	}

	/**
	 * Returns the fragment collection key of this fragment collection.
	 *
	 * @return the fragment collection key of this fragment collection
	 */
	@Override
	public String getFragmentCollectionKey() {
		return model.getFragmentCollectionKey();
	}

	/**
	 * Returns the group ID of this fragment collection.
	 *
	 * @return the group ID of this fragment collection
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the last publish date of this fragment collection.
	 *
	 * @return the last publish date of this fragment collection
	 */
	@Override
	public Date getLastPublishDate() {
		return model.getLastPublishDate();
	}

	/**
	 * Returns the modified date of this fragment collection.
	 *
	 * @return the modified date of this fragment collection
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this fragment collection.
	 *
	 * @return the mvcc version of this fragment collection
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this fragment collection.
	 *
	 * @return the name of this fragment collection
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the primary key of this fragment collection.
	 *
	 * @return the primary key of this fragment collection
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry>
			getResources()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getResources();
	}

	@Override
	public long getResourcesFolderId()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getResourcesFolderId();
	}

	@Override
	public long getResourcesFolderId(boolean createIfAbsent)
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getResourcesFolderId(createIfAbsent);
	}

	/**
	 * Returns the user ID of this fragment collection.
	 *
	 * @return the user ID of this fragment collection
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this fragment collection.
	 *
	 * @return the user name of this fragment collection
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this fragment collection.
	 *
	 * @return the user uuid of this fragment collection
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this fragment collection.
	 *
	 * @return the uuid of this fragment collection
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	@Override
	public boolean hasResources()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.hasResources();
	}

	@Override
	public void persist() {
		model.persist();
	}

	@Override
	public void populateZipWriter(
			com.liferay.portal.kernel.zip.ZipWriter zipWriter)
		throws Exception {

		model.populateZipWriter(zipWriter);
	}

	@Override
	public void populateZipWriter(
			com.liferay.portal.kernel.zip.ZipWriter zipWriter, String path)
		throws Exception {

		model.populateZipWriter(zipWriter, path);
	}

	/**
	 * Sets the company ID of this fragment collection.
	 *
	 * @param companyId the company ID of this fragment collection
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this fragment collection.
	 *
	 * @param createDate the create date of this fragment collection
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct collection ID of this fragment collection.
	 *
	 * @param ctCollectionId the ct collection ID of this fragment collection
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the description of this fragment collection.
	 *
	 * @param description the description of this fragment collection
	 */
	@Override
	public void setDescription(String description) {
		model.setDescription(description);
	}

	/**
	 * Sets the fragment collection ID of this fragment collection.
	 *
	 * @param fragmentCollectionId the fragment collection ID of this fragment collection
	 */
	@Override
	public void setFragmentCollectionId(long fragmentCollectionId) {
		model.setFragmentCollectionId(fragmentCollectionId);
	}

	/**
	 * Sets the fragment collection key of this fragment collection.
	 *
	 * @param fragmentCollectionKey the fragment collection key of this fragment collection
	 */
	@Override
	public void setFragmentCollectionKey(String fragmentCollectionKey) {
		model.setFragmentCollectionKey(fragmentCollectionKey);
	}

	/**
	 * Sets the group ID of this fragment collection.
	 *
	 * @param groupId the group ID of this fragment collection
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the last publish date of this fragment collection.
	 *
	 * @param lastPublishDate the last publish date of this fragment collection
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		model.setLastPublishDate(lastPublishDate);
	}

	/**
	 * Sets the modified date of this fragment collection.
	 *
	 * @param modifiedDate the modified date of this fragment collection
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this fragment collection.
	 *
	 * @param mvccVersion the mvcc version of this fragment collection
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this fragment collection.
	 *
	 * @param name the name of this fragment collection
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the primary key of this fragment collection.
	 *
	 * @param primaryKey the primary key of this fragment collection
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this fragment collection.
	 *
	 * @param userId the user ID of this fragment collection
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this fragment collection.
	 *
	 * @param userName the user name of this fragment collection
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this fragment collection.
	 *
	 * @param userUuid the user uuid of this fragment collection
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this fragment collection.
	 *
	 * @param uuid the uuid of this fragment collection
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	public Map<String, Function<FragmentCollection, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<FragmentCollection, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected FragmentCollectionWrapper wrap(
		FragmentCollection fragmentCollection) {

		return new FragmentCollectionWrapper(fragmentCollection);
	}

}