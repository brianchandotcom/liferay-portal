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
 * This class is a wrapper for {@link FragmentComposition}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FragmentComposition
 * @generated
 */
public class FragmentCompositionWrapper
	extends BaseModelWrapper<FragmentComposition>
	implements FragmentComposition, ModelWrapper<FragmentComposition> {

	public FragmentCompositionWrapper(FragmentComposition fragmentComposition) {
		super(fragmentComposition);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("uuid", getUuid());
		attributes.put("fragmentCompositionId", getFragmentCompositionId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("fragmentCollectionId", getFragmentCollectionId());
		attributes.put("fragmentCompositionKey", getFragmentCompositionKey());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("data", getData());
		attributes.put("previewFileEntryId", getPreviewFileEntryId());
		attributes.put("lastPublishDate", getLastPublishDate());
		attributes.put("status", getStatus());
		attributes.put("statusByUserId", getStatusByUserId());
		attributes.put("statusByUserName", getStatusByUserName());
		attributes.put("statusDate", getStatusDate());

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

		Long fragmentCompositionId = (Long)attributes.get(
			"fragmentCompositionId");

		if (fragmentCompositionId != null) {
			setFragmentCompositionId(fragmentCompositionId);
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

		Long fragmentCollectionId = (Long)attributes.get(
			"fragmentCollectionId");

		if (fragmentCollectionId != null) {
			setFragmentCollectionId(fragmentCollectionId);
		}

		String fragmentCompositionKey = (String)attributes.get(
			"fragmentCompositionKey");

		if (fragmentCompositionKey != null) {
			setFragmentCompositionKey(fragmentCompositionKey);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		String data = (String)attributes.get("data");

		if (data != null) {
			setData(data);
		}

		Long previewFileEntryId = (Long)attributes.get("previewFileEntryId");

		if (previewFileEntryId != null) {
			setPreviewFileEntryId(previewFileEntryId);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}

		Integer status = (Integer)attributes.get("status");

		if (status != null) {
			setStatus(status);
		}

		Long statusByUserId = (Long)attributes.get("statusByUserId");

		if (statusByUserId != null) {
			setStatusByUserId(statusByUserId);
		}

		String statusByUserName = (String)attributes.get("statusByUserName");

		if (statusByUserName != null) {
			setStatusByUserName(statusByUserName);
		}

		Date statusDate = (Date)attributes.get("statusDate");

		if (statusDate != null) {
			setStatusDate(statusDate);
		}
	}

	/**
	 * Returns the company ID of this fragment composition.
	 *
	 * @return the company ID of this fragment composition
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this fragment composition.
	 *
	 * @return the create date of this fragment composition
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct collection ID of this fragment composition.
	 *
	 * @return the ct collection ID of this fragment composition
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the data of this fragment composition.
	 *
	 * @return the data of this fragment composition
	 */
	@Override
	public String getData() {
		return model.getData();
	}

	@Override
	public com.liferay.portal.kernel.json.JSONObject getDataJSONObject()
		throws Exception {

		return model.getDataJSONObject();
	}

	/**
	 * Returns the description of this fragment composition.
	 *
	 * @return the description of this fragment composition
	 */
	@Override
	public String getDescription() {
		return model.getDescription();
	}

	/**
	 * Returns the fragment collection ID of this fragment composition.
	 *
	 * @return the fragment collection ID of this fragment composition
	 */
	@Override
	public long getFragmentCollectionId() {
		return model.getFragmentCollectionId();
	}

	/**
	 * Returns the fragment composition ID of this fragment composition.
	 *
	 * @return the fragment composition ID of this fragment composition
	 */
	@Override
	public long getFragmentCompositionId() {
		return model.getFragmentCompositionId();
	}

	/**
	 * Returns the fragment composition key of this fragment composition.
	 *
	 * @return the fragment composition key of this fragment composition
	 */
	@Override
	public String getFragmentCompositionKey() {
		return model.getFragmentCompositionKey();
	}

	/**
	 * Returns the group ID of this fragment composition.
	 *
	 * @return the group ID of this fragment composition
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	@Override
	public String getIcon() {
		return model.getIcon();
	}

	@Override
	public String getImagePreviewURL(
		com.liferay.portal.kernel.theme.ThemeDisplay themeDisplay) {

		return model.getImagePreviewURL(themeDisplay);
	}

	/**
	 * Returns the last publish date of this fragment composition.
	 *
	 * @return the last publish date of this fragment composition
	 */
	@Override
	public Date getLastPublishDate() {
		return model.getLastPublishDate();
	}

	/**
	 * Returns the modified date of this fragment composition.
	 *
	 * @return the modified date of this fragment composition
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this fragment composition.
	 *
	 * @return the mvcc version of this fragment composition
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this fragment composition.
	 *
	 * @return the name of this fragment composition
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the preview file entry ID of this fragment composition.
	 *
	 * @return the preview file entry ID of this fragment composition
	 */
	@Override
	public long getPreviewFileEntryId() {
		return model.getPreviewFileEntryId();
	}

	/**
	 * Returns the primary key of this fragment composition.
	 *
	 * @return the primary key of this fragment composition
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the status of this fragment composition.
	 *
	 * @return the status of this fragment composition
	 */
	@Override
	public int getStatus() {
		return model.getStatus();
	}

	/**
	 * Returns the status by user ID of this fragment composition.
	 *
	 * @return the status by user ID of this fragment composition
	 */
	@Override
	public long getStatusByUserId() {
		return model.getStatusByUserId();
	}

	/**
	 * Returns the status by user name of this fragment composition.
	 *
	 * @return the status by user name of this fragment composition
	 */
	@Override
	public String getStatusByUserName() {
		return model.getStatusByUserName();
	}

	/**
	 * Returns the status by user uuid of this fragment composition.
	 *
	 * @return the status by user uuid of this fragment composition
	 */
	@Override
	public String getStatusByUserUuid() {
		return model.getStatusByUserUuid();
	}

	/**
	 * Returns the status date of this fragment composition.
	 *
	 * @return the status date of this fragment composition
	 */
	@Override
	public Date getStatusDate() {
		return model.getStatusDate();
	}

	/**
	 * Returns the user ID of this fragment composition.
	 *
	 * @return the user ID of this fragment composition
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this fragment composition.
	 *
	 * @return the user name of this fragment composition
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this fragment composition.
	 *
	 * @return the user uuid of this fragment composition
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this fragment composition.
	 *
	 * @return the uuid of this fragment composition
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns <code>true</code> if this fragment composition is approved.
	 *
	 * @return <code>true</code> if this fragment composition is approved; <code>false</code> otherwise
	 */
	@Override
	public boolean isApproved() {
		return model.isApproved();
	}

	/**
	 * Returns <code>true</code> if this fragment composition is denied.
	 *
	 * @return <code>true</code> if this fragment composition is denied; <code>false</code> otherwise
	 */
	@Override
	public boolean isDenied() {
		return model.isDenied();
	}

	/**
	 * Returns <code>true</code> if this fragment composition is a draft.
	 *
	 * @return <code>true</code> if this fragment composition is a draft; <code>false</code> otherwise
	 */
	@Override
	public boolean isDraft() {
		return model.isDraft();
	}

	/**
	 * Returns <code>true</code> if this fragment composition is expired.
	 *
	 * @return <code>true</code> if this fragment composition is expired; <code>false</code> otherwise
	 */
	@Override
	public boolean isExpired() {
		return model.isExpired();
	}

	/**
	 * Returns <code>true</code> if this fragment composition is inactive.
	 *
	 * @return <code>true</code> if this fragment composition is inactive; <code>false</code> otherwise
	 */
	@Override
	public boolean isInactive() {
		return model.isInactive();
	}

	/**
	 * Returns <code>true</code> if this fragment composition is incomplete.
	 *
	 * @return <code>true</code> if this fragment composition is incomplete; <code>false</code> otherwise
	 */
	@Override
	public boolean isIncomplete() {
		return model.isIncomplete();
	}

	/**
	 * Returns <code>true</code> if this fragment composition is pending.
	 *
	 * @return <code>true</code> if this fragment composition is pending; <code>false</code> otherwise
	 */
	@Override
	public boolean isPending() {
		return model.isPending();
	}

	/**
	 * Returns <code>true</code> if this fragment composition is scheduled.
	 *
	 * @return <code>true</code> if this fragment composition is scheduled; <code>false</code> otherwise
	 */
	@Override
	public boolean isScheduled() {
		return model.isScheduled();
	}

	@Override
	public void persist() {
		model.persist();
	}

	@Override
	public void populateZipWriter(
			com.liferay.portal.kernel.zip.ZipWriter zipWriter, String path)
		throws Exception {

		model.populateZipWriter(zipWriter, path);
	}

	/**
	 * Sets the company ID of this fragment composition.
	 *
	 * @param companyId the company ID of this fragment composition
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this fragment composition.
	 *
	 * @param createDate the create date of this fragment composition
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct collection ID of this fragment composition.
	 *
	 * @param ctCollectionId the ct collection ID of this fragment composition
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the data of this fragment composition.
	 *
	 * @param data the data of this fragment composition
	 */
	@Override
	public void setData(String data) {
		model.setData(data);
	}

	/**
	 * Sets the description of this fragment composition.
	 *
	 * @param description the description of this fragment composition
	 */
	@Override
	public void setDescription(String description) {
		model.setDescription(description);
	}

	/**
	 * Sets the fragment collection ID of this fragment composition.
	 *
	 * @param fragmentCollectionId the fragment collection ID of this fragment composition
	 */
	@Override
	public void setFragmentCollectionId(long fragmentCollectionId) {
		model.setFragmentCollectionId(fragmentCollectionId);
	}

	/**
	 * Sets the fragment composition ID of this fragment composition.
	 *
	 * @param fragmentCompositionId the fragment composition ID of this fragment composition
	 */
	@Override
	public void setFragmentCompositionId(long fragmentCompositionId) {
		model.setFragmentCompositionId(fragmentCompositionId);
	}

	/**
	 * Sets the fragment composition key of this fragment composition.
	 *
	 * @param fragmentCompositionKey the fragment composition key of this fragment composition
	 */
	@Override
	public void setFragmentCompositionKey(String fragmentCompositionKey) {
		model.setFragmentCompositionKey(fragmentCompositionKey);
	}

	/**
	 * Sets the group ID of this fragment composition.
	 *
	 * @param groupId the group ID of this fragment composition
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	@Override
	public void setIcon(String icon) {
		model.setIcon(icon);
	}

	@Override
	public void setImagePreviewURL(String imagePreviewURL) {
		model.setImagePreviewURL(imagePreviewURL);
	}

	/**
	 * Sets the last publish date of this fragment composition.
	 *
	 * @param lastPublishDate the last publish date of this fragment composition
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		model.setLastPublishDate(lastPublishDate);
	}

	/**
	 * Sets the modified date of this fragment composition.
	 *
	 * @param modifiedDate the modified date of this fragment composition
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this fragment composition.
	 *
	 * @param mvccVersion the mvcc version of this fragment composition
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this fragment composition.
	 *
	 * @param name the name of this fragment composition
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the preview file entry ID of this fragment composition.
	 *
	 * @param previewFileEntryId the preview file entry ID of this fragment composition
	 */
	@Override
	public void setPreviewFileEntryId(long previewFileEntryId) {
		model.setPreviewFileEntryId(previewFileEntryId);
	}

	/**
	 * Sets the primary key of this fragment composition.
	 *
	 * @param primaryKey the primary key of this fragment composition
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the status of this fragment composition.
	 *
	 * @param status the status of this fragment composition
	 */
	@Override
	public void setStatus(int status) {
		model.setStatus(status);
	}

	/**
	 * Sets the status by user ID of this fragment composition.
	 *
	 * @param statusByUserId the status by user ID of this fragment composition
	 */
	@Override
	public void setStatusByUserId(long statusByUserId) {
		model.setStatusByUserId(statusByUserId);
	}

	/**
	 * Sets the status by user name of this fragment composition.
	 *
	 * @param statusByUserName the status by user name of this fragment composition
	 */
	@Override
	public void setStatusByUserName(String statusByUserName) {
		model.setStatusByUserName(statusByUserName);
	}

	/**
	 * Sets the status by user uuid of this fragment composition.
	 *
	 * @param statusByUserUuid the status by user uuid of this fragment composition
	 */
	@Override
	public void setStatusByUserUuid(String statusByUserUuid) {
		model.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	 * Sets the status date of this fragment composition.
	 *
	 * @param statusDate the status date of this fragment composition
	 */
	@Override
	public void setStatusDate(Date statusDate) {
		model.setStatusDate(statusDate);
	}

	/**
	 * Sets the user ID of this fragment composition.
	 *
	 * @param userId the user ID of this fragment composition
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this fragment composition.
	 *
	 * @param userName the user name of this fragment composition
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this fragment composition.
	 *
	 * @param userUuid the user uuid of this fragment composition
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this fragment composition.
	 *
	 * @param uuid the uuid of this fragment composition
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	public Map<String, Function<FragmentComposition, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<FragmentComposition, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected FragmentCompositionWrapper wrap(
		FragmentComposition fragmentComposition) {

		return new FragmentCompositionWrapper(fragmentComposition);
	}

}