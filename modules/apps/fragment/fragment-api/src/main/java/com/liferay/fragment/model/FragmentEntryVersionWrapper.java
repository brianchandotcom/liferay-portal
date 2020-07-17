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

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * <p>
 * This class is a wrapper for {@link FragmentEntryVersion}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FragmentEntryVersion
 * @generated
 */
public class FragmentEntryVersionWrapper
	extends BaseModelWrapper<FragmentEntryVersion>
	implements FragmentEntryVersion, ModelWrapper<FragmentEntryVersion> {

	public FragmentEntryVersionWrapper(
		FragmentEntryVersion fragmentEntryVersion) {

		super(fragmentEntryVersion);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("fragmentEntryVersionId", getFragmentEntryVersionId());
		attributes.put("version", getVersion());
		attributes.put("uuid", getUuid());
		attributes.put("fragmentEntryId", getFragmentEntryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("fragmentCollectionId", getFragmentCollectionId());
		attributes.put("fragmentEntryKey", getFragmentEntryKey());
		attributes.put("name", getName());
		attributes.put("css", getCss());
		attributes.put("html", getHtml());
		attributes.put("js", getJs());
		attributes.put("cacheable", isCacheable());
		attributes.put("configuration", getConfiguration());
		attributes.put("previewFileEntryId", getPreviewFileEntryId());
		attributes.put("readOnly", isReadOnly());
		attributes.put("type", getType());
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

		Long fragmentEntryVersionId = (Long)attributes.get(
			"fragmentEntryVersionId");

		if (fragmentEntryVersionId != null) {
			setFragmentEntryVersionId(fragmentEntryVersionId);
		}

		Integer version = (Integer)attributes.get("version");

		if (version != null) {
			setVersion(version);
		}

		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long fragmentEntryId = (Long)attributes.get("fragmentEntryId");

		if (fragmentEntryId != null) {
			setFragmentEntryId(fragmentEntryId);
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

		String fragmentEntryKey = (String)attributes.get("fragmentEntryKey");

		if (fragmentEntryKey != null) {
			setFragmentEntryKey(fragmentEntryKey);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String css = (String)attributes.get("css");

		if (css != null) {
			setCss(css);
		}

		String html = (String)attributes.get("html");

		if (html != null) {
			setHtml(html);
		}

		String js = (String)attributes.get("js");

		if (js != null) {
			setJs(js);
		}

		Boolean cacheable = (Boolean)attributes.get("cacheable");

		if (cacheable != null) {
			setCacheable(cacheable);
		}

		String configuration = (String)attributes.get("configuration");

		if (configuration != null) {
			setConfiguration(configuration);
		}

		Long previewFileEntryId = (Long)attributes.get("previewFileEntryId");

		if (previewFileEntryId != null) {
			setPreviewFileEntryId(previewFileEntryId);
		}

		Boolean readOnly = (Boolean)attributes.get("readOnly");

		if (readOnly != null) {
			setReadOnly(readOnly);
		}

		Integer type = (Integer)attributes.get("type");

		if (type != null) {
			setType(type);
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
	 * Returns the cacheable of this fragment entry version.
	 *
	 * @return the cacheable of this fragment entry version
	 */
	@Override
	public boolean getCacheable() {
		return model.getCacheable();
	}

	/**
	 * Returns the company ID of this fragment entry version.
	 *
	 * @return the company ID of this fragment entry version
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the configuration of this fragment entry version.
	 *
	 * @return the configuration of this fragment entry version
	 */
	@Override
	public String getConfiguration() {
		return model.getConfiguration();
	}

	/**
	 * Returns the create date of this fragment entry version.
	 *
	 * @return the create date of this fragment entry version
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the css of this fragment entry version.
	 *
	 * @return the css of this fragment entry version
	 */
	@Override
	public String getCss() {
		return model.getCss();
	}

	/**
	 * Returns the ct collection ID of this fragment entry version.
	 *
	 * @return the ct collection ID of this fragment entry version
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the fragment collection ID of this fragment entry version.
	 *
	 * @return the fragment collection ID of this fragment entry version
	 */
	@Override
	public long getFragmentCollectionId() {
		return model.getFragmentCollectionId();
	}

	/**
	 * Returns the fragment entry ID of this fragment entry version.
	 *
	 * @return the fragment entry ID of this fragment entry version
	 */
	@Override
	public long getFragmentEntryId() {
		return model.getFragmentEntryId();
	}

	/**
	 * Returns the fragment entry key of this fragment entry version.
	 *
	 * @return the fragment entry key of this fragment entry version
	 */
	@Override
	public String getFragmentEntryKey() {
		return model.getFragmentEntryKey();
	}

	/**
	 * Returns the fragment entry version ID of this fragment entry version.
	 *
	 * @return the fragment entry version ID of this fragment entry version
	 */
	@Override
	public long getFragmentEntryVersionId() {
		return model.getFragmentEntryVersionId();
	}

	/**
	 * Returns the group ID of this fragment entry version.
	 *
	 * @return the group ID of this fragment entry version
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the html of this fragment entry version.
	 *
	 * @return the html of this fragment entry version
	 */
	@Override
	public String getHtml() {
		return model.getHtml();
	}

	/**
	 * Returns the js of this fragment entry version.
	 *
	 * @return the js of this fragment entry version
	 */
	@Override
	public String getJs() {
		return model.getJs();
	}

	/**
	 * Returns the last publish date of this fragment entry version.
	 *
	 * @return the last publish date of this fragment entry version
	 */
	@Override
	public Date getLastPublishDate() {
		return model.getLastPublishDate();
	}

	/**
	 * Returns the modified date of this fragment entry version.
	 *
	 * @return the modified date of this fragment entry version
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this fragment entry version.
	 *
	 * @return the mvcc version of this fragment entry version
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this fragment entry version.
	 *
	 * @return the name of this fragment entry version
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the preview file entry ID of this fragment entry version.
	 *
	 * @return the preview file entry ID of this fragment entry version
	 */
	@Override
	public long getPreviewFileEntryId() {
		return model.getPreviewFileEntryId();
	}

	/**
	 * Returns the primary key of this fragment entry version.
	 *
	 * @return the primary key of this fragment entry version
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the read only of this fragment entry version.
	 *
	 * @return the read only of this fragment entry version
	 */
	@Override
	public boolean getReadOnly() {
		return model.getReadOnly();
	}

	/**
	 * Returns the status of this fragment entry version.
	 *
	 * @return the status of this fragment entry version
	 */
	@Override
	public int getStatus() {
		return model.getStatus();
	}

	/**
	 * Returns the status by user ID of this fragment entry version.
	 *
	 * @return the status by user ID of this fragment entry version
	 */
	@Override
	public long getStatusByUserId() {
		return model.getStatusByUserId();
	}

	/**
	 * Returns the status by user name of this fragment entry version.
	 *
	 * @return the status by user name of this fragment entry version
	 */
	@Override
	public String getStatusByUserName() {
		return model.getStatusByUserName();
	}

	/**
	 * Returns the status by user uuid of this fragment entry version.
	 *
	 * @return the status by user uuid of this fragment entry version
	 */
	@Override
	public String getStatusByUserUuid() {
		return model.getStatusByUserUuid();
	}

	/**
	 * Returns the status date of this fragment entry version.
	 *
	 * @return the status date of this fragment entry version
	 */
	@Override
	public Date getStatusDate() {
		return model.getStatusDate();
	}

	/**
	 * Returns the type of this fragment entry version.
	 *
	 * @return the type of this fragment entry version
	 */
	@Override
	public int getType() {
		return model.getType();
	}

	/**
	 * Returns the user ID of this fragment entry version.
	 *
	 * @return the user ID of this fragment entry version
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this fragment entry version.
	 *
	 * @return the user name of this fragment entry version
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this fragment entry version.
	 *
	 * @return the user uuid of this fragment entry version
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this fragment entry version.
	 *
	 * @return the uuid of this fragment entry version
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns the version of this fragment entry version.
	 *
	 * @return the version of this fragment entry version
	 */
	@Override
	public int getVersion() {
		return model.getVersion();
	}

	/**
	 * Returns <code>true</code> if this fragment entry version is approved.
	 *
	 * @return <code>true</code> if this fragment entry version is approved; <code>false</code> otherwise
	 */
	@Override
	public boolean isApproved() {
		return model.isApproved();
	}

	/**
	 * Returns <code>true</code> if this fragment entry version is cacheable.
	 *
	 * @return <code>true</code> if this fragment entry version is cacheable; <code>false</code> otherwise
	 */
	@Override
	public boolean isCacheable() {
		return model.isCacheable();
	}

	/**
	 * Returns <code>true</code> if this fragment entry version is denied.
	 *
	 * @return <code>true</code> if this fragment entry version is denied; <code>false</code> otherwise
	 */
	@Override
	public boolean isDenied() {
		return model.isDenied();
	}

	/**
	 * Returns <code>true</code> if this fragment entry version is a draft.
	 *
	 * @return <code>true</code> if this fragment entry version is a draft; <code>false</code> otherwise
	 */
	@Override
	public boolean isDraft() {
		return model.isDraft();
	}

	/**
	 * Returns <code>true</code> if this fragment entry version is expired.
	 *
	 * @return <code>true</code> if this fragment entry version is expired; <code>false</code> otherwise
	 */
	@Override
	public boolean isExpired() {
		return model.isExpired();
	}

	/**
	 * Returns <code>true</code> if this fragment entry version is inactive.
	 *
	 * @return <code>true</code> if this fragment entry version is inactive; <code>false</code> otherwise
	 */
	@Override
	public boolean isInactive() {
		return model.isInactive();
	}

	/**
	 * Returns <code>true</code> if this fragment entry version is incomplete.
	 *
	 * @return <code>true</code> if this fragment entry version is incomplete; <code>false</code> otherwise
	 */
	@Override
	public boolean isIncomplete() {
		return model.isIncomplete();
	}

	/**
	 * Returns <code>true</code> if this fragment entry version is pending.
	 *
	 * @return <code>true</code> if this fragment entry version is pending; <code>false</code> otherwise
	 */
	@Override
	public boolean isPending() {
		return model.isPending();
	}

	/**
	 * Returns <code>true</code> if this fragment entry version is read only.
	 *
	 * @return <code>true</code> if this fragment entry version is read only; <code>false</code> otherwise
	 */
	@Override
	public boolean isReadOnly() {
		return model.isReadOnly();
	}

	/**
	 * Returns <code>true</code> if this fragment entry version is scheduled.
	 *
	 * @return <code>true</code> if this fragment entry version is scheduled; <code>false</code> otherwise
	 */
	@Override
	public boolean isScheduled() {
		return model.isScheduled();
	}

	/**
	 * Sets whether this fragment entry version is cacheable.
	 *
	 * @param cacheable the cacheable of this fragment entry version
	 */
	@Override
	public void setCacheable(boolean cacheable) {
		model.setCacheable(cacheable);
	}

	/**
	 * Sets the company ID of this fragment entry version.
	 *
	 * @param companyId the company ID of this fragment entry version
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the configuration of this fragment entry version.
	 *
	 * @param configuration the configuration of this fragment entry version
	 */
	@Override
	public void setConfiguration(String configuration) {
		model.setConfiguration(configuration);
	}

	/**
	 * Sets the create date of this fragment entry version.
	 *
	 * @param createDate the create date of this fragment entry version
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the css of this fragment entry version.
	 *
	 * @param css the css of this fragment entry version
	 */
	@Override
	public void setCss(String css) {
		model.setCss(css);
	}

	/**
	 * Sets the ct collection ID of this fragment entry version.
	 *
	 * @param ctCollectionId the ct collection ID of this fragment entry version
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the fragment collection ID of this fragment entry version.
	 *
	 * @param fragmentCollectionId the fragment collection ID of this fragment entry version
	 */
	@Override
	public void setFragmentCollectionId(long fragmentCollectionId) {
		model.setFragmentCollectionId(fragmentCollectionId);
	}

	/**
	 * Sets the fragment entry ID of this fragment entry version.
	 *
	 * @param fragmentEntryId the fragment entry ID of this fragment entry version
	 */
	@Override
	public void setFragmentEntryId(long fragmentEntryId) {
		model.setFragmentEntryId(fragmentEntryId);
	}

	/**
	 * Sets the fragment entry key of this fragment entry version.
	 *
	 * @param fragmentEntryKey the fragment entry key of this fragment entry version
	 */
	@Override
	public void setFragmentEntryKey(String fragmentEntryKey) {
		model.setFragmentEntryKey(fragmentEntryKey);
	}

	/**
	 * Sets the fragment entry version ID of this fragment entry version.
	 *
	 * @param fragmentEntryVersionId the fragment entry version ID of this fragment entry version
	 */
	@Override
	public void setFragmentEntryVersionId(long fragmentEntryVersionId) {
		model.setFragmentEntryVersionId(fragmentEntryVersionId);
	}

	/**
	 * Sets the group ID of this fragment entry version.
	 *
	 * @param groupId the group ID of this fragment entry version
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the html of this fragment entry version.
	 *
	 * @param html the html of this fragment entry version
	 */
	@Override
	public void setHtml(String html) {
		model.setHtml(html);
	}

	/**
	 * Sets the js of this fragment entry version.
	 *
	 * @param js the js of this fragment entry version
	 */
	@Override
	public void setJs(String js) {
		model.setJs(js);
	}

	/**
	 * Sets the last publish date of this fragment entry version.
	 *
	 * @param lastPublishDate the last publish date of this fragment entry version
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		model.setLastPublishDate(lastPublishDate);
	}

	/**
	 * Sets the modified date of this fragment entry version.
	 *
	 * @param modifiedDate the modified date of this fragment entry version
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this fragment entry version.
	 *
	 * @param mvccVersion the mvcc version of this fragment entry version
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this fragment entry version.
	 *
	 * @param name the name of this fragment entry version
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the preview file entry ID of this fragment entry version.
	 *
	 * @param previewFileEntryId the preview file entry ID of this fragment entry version
	 */
	@Override
	public void setPreviewFileEntryId(long previewFileEntryId) {
		model.setPreviewFileEntryId(previewFileEntryId);
	}

	/**
	 * Sets the primary key of this fragment entry version.
	 *
	 * @param primaryKey the primary key of this fragment entry version
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets whether this fragment entry version is read only.
	 *
	 * @param readOnly the read only of this fragment entry version
	 */
	@Override
	public void setReadOnly(boolean readOnly) {
		model.setReadOnly(readOnly);
	}

	/**
	 * Sets the status of this fragment entry version.
	 *
	 * @param status the status of this fragment entry version
	 */
	@Override
	public void setStatus(int status) {
		model.setStatus(status);
	}

	/**
	 * Sets the status by user ID of this fragment entry version.
	 *
	 * @param statusByUserId the status by user ID of this fragment entry version
	 */
	@Override
	public void setStatusByUserId(long statusByUserId) {
		model.setStatusByUserId(statusByUserId);
	}

	/**
	 * Sets the status by user name of this fragment entry version.
	 *
	 * @param statusByUserName the status by user name of this fragment entry version
	 */
	@Override
	public void setStatusByUserName(String statusByUserName) {
		model.setStatusByUserName(statusByUserName);
	}

	/**
	 * Sets the status by user uuid of this fragment entry version.
	 *
	 * @param statusByUserUuid the status by user uuid of this fragment entry version
	 */
	@Override
	public void setStatusByUserUuid(String statusByUserUuid) {
		model.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	 * Sets the status date of this fragment entry version.
	 *
	 * @param statusDate the status date of this fragment entry version
	 */
	@Override
	public void setStatusDate(Date statusDate) {
		model.setStatusDate(statusDate);
	}

	/**
	 * Sets the type of this fragment entry version.
	 *
	 * @param type the type of this fragment entry version
	 */
	@Override
	public void setType(int type) {
		model.setType(type);
	}

	/**
	 * Sets the user ID of this fragment entry version.
	 *
	 * @param userId the user ID of this fragment entry version
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this fragment entry version.
	 *
	 * @param userName the user name of this fragment entry version
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this fragment entry version.
	 *
	 * @param userUuid the user uuid of this fragment entry version
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this fragment entry version.
	 *
	 * @param uuid the uuid of this fragment entry version
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	/**
	 * Sets the version of this fragment entry version.
	 *
	 * @param version the version of this fragment entry version
	 */
	@Override
	public void setVersion(int version) {
		model.setVersion(version);
	}

	@Override
	public Map<String, Function<FragmentEntryVersion, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<FragmentEntryVersion, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	public long getVersionedModelId() {
		return model.getVersionedModelId();
	}

	@Override
	public void setVersionedModelId(long id) {
		model.setVersionedModelId(id);
	}

	@Override
	public void populateVersionedModel(FragmentEntry fragmentEntry) {
		model.populateVersionedModel(fragmentEntry);
	}

	@Override
	public FragmentEntry toVersionedModel() {
		return model.toVersionedModel();
	}

	@Override
	protected FragmentEntryVersionWrapper wrap(
		FragmentEntryVersion fragmentEntryVersion) {

		return new FragmentEntryVersionWrapper(fragmentEntryVersion);
	}

}