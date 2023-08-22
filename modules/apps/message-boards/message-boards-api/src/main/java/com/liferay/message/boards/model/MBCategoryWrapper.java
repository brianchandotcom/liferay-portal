/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.model;

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
 * This class is a wrapper for {@link MBCategory}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MBCategory
 * @generated
 */
public class MBCategoryWrapper
	extends BaseModelWrapper<MBCategory>
	implements MBCategory, ModelWrapper<MBCategory> {

	public MBCategoryWrapper(MBCategory mbCategory) {
		super(mbCategory);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("uuid", getUuid());
		attributes.put("categoryId", getCategoryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("parentCategoryId", getParentCategoryId());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("displayStyle", getDisplayStyle());
		attributes.put("friendlyURL", getFriendlyURL());
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

		Long categoryId = (Long)attributes.get("categoryId");

		if (categoryId != null) {
			setCategoryId(categoryId);
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

		Long parentCategoryId = (Long)attributes.get("parentCategoryId");

		if (parentCategoryId != null) {
			setParentCategoryId(parentCategoryId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		String displayStyle = (String)attributes.get("displayStyle");

		if (displayStyle != null) {
			setDisplayStyle(displayStyle);
		}

		String friendlyURL = (String)attributes.get("friendlyURL");

		if (friendlyURL != null) {
			setFriendlyURL(friendlyURL);
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

	@Override
	public MBCategory cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	@Override
	public java.util.List<Long> getAncestorCategoryIds()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getAncestorCategoryIds();
	}

	@Override
	public java.util.List<MBCategory> getAncestors()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getAncestors();
	}

	/**
	 * Returns the category ID of this message boards category.
	 *
	 * @return the category ID of this message boards category
	 */
	@Override
	public long getCategoryId() {
		return model.getCategoryId();
	}

	/**
	 * Returns the company ID of this message boards category.
	 *
	 * @return the company ID of this message boards category
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the container model ID of this message boards category.
	 *
	 * @return the container model ID of this message boards category
	 */
	@Override
	public long getContainerModelId() {
		return model.getContainerModelId();
	}

	/**
	 * Returns the container name of this message boards category.
	 *
	 * @return the container name of this message boards category
	 */
	@Override
	public String getContainerModelName() {
		return model.getContainerModelName();
	}

	/**
	 * Returns the create date of this message boards category.
	 *
	 * @return the create date of this message boards category
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct collection ID of this message boards category.
	 *
	 * @return the ct collection ID of this message boards category
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the description of this message boards category.
	 *
	 * @return the description of this message boards category
	 */
	@Override
	public String getDescription() {
		return model.getDescription();
	}

	/**
	 * Returns the display style of this message boards category.
	 *
	 * @return the display style of this message boards category
	 */
	@Override
	public String getDisplayStyle() {
		return model.getDisplayStyle();
	}

	/**
	 * Returns the friendly url of this message boards category.
	 *
	 * @return the friendly url of this message boards category
	 */
	@Override
	public String getFriendlyURL() {
		return model.getFriendlyURL();
	}

	/**
	 * Returns the group ID of this message boards category.
	 *
	 * @return the group ID of this message boards category
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the last publish date of this message boards category.
	 *
	 * @return the last publish date of this message boards category
	 */
	@Override
	public Date getLastPublishDate() {
		return model.getLastPublishDate();
	}

	@Override
	public int getMessageCount() {
		return model.getMessageCount();
	}

	/**
	 * Returns the modified date of this message boards category.
	 *
	 * @return the modified date of this message boards category
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this message boards category.
	 *
	 * @return the mvcc version of this message boards category
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this message boards category.
	 *
	 * @return the name of this message boards category
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	@Override
	public MBCategory getParentCategory()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getParentCategory();
	}

	/**
	 * Returns the parent category ID of this message boards category.
	 *
	 * @return the parent category ID of this message boards category
	 */
	@Override
	public long getParentCategoryId() {
		return model.getParentCategoryId();
	}

	/**
	 * Returns the parent container model ID of this message boards category.
	 *
	 * @return the parent container model ID of this message boards category
	 */
	@Override
	public long getParentContainerModelId() {
		return model.getParentContainerModelId();
	}

	/**
	 * Returns the primary key of this message boards category.
	 *
	 * @return the primary key of this message boards category
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the status of this message boards category.
	 *
	 * @return the status of this message boards category
	 */
	@Override
	public int getStatus() {
		return model.getStatus();
	}

	/**
	 * Returns the status by user ID of this message boards category.
	 *
	 * @return the status by user ID of this message boards category
	 */
	@Override
	public long getStatusByUserId() {
		return model.getStatusByUserId();
	}

	/**
	 * Returns the status by user name of this message boards category.
	 *
	 * @return the status by user name of this message boards category
	 */
	@Override
	public String getStatusByUserName() {
		return model.getStatusByUserName();
	}

	/**
	 * Returns the status by user uuid of this message boards category.
	 *
	 * @return the status by user uuid of this message boards category
	 */
	@Override
	public String getStatusByUserUuid() {
		return model.getStatusByUserUuid();
	}

	/**
	 * Returns the status date of this message boards category.
	 *
	 * @return the status date of this message boards category
	 */
	@Override
	public Date getStatusDate() {
		return model.getStatusDate();
	}

	@Override
	public int getThreadCount() {
		return model.getThreadCount();
	}

	/**
	 * Returns the class primary key of the trash entry for this message boards category.
	 *
	 * @return the class primary key of the trash entry for this message boards category
	 */
	@Override
	public long getTrashEntryClassPK() {
		return model.getTrashEntryClassPK();
	}

	/**
	 * Returns the user ID of this message boards category.
	 *
	 * @return the user ID of this message boards category
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this message boards category.
	 *
	 * @return the user name of this message boards category
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this message boards category.
	 *
	 * @return the user uuid of this message boards category
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this message boards category.
	 *
	 * @return the uuid of this message boards category
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns <code>true</code> if this message boards category is approved.
	 *
	 * @return <code>true</code> if this message boards category is approved; <code>false</code> otherwise
	 */
	@Override
	public boolean isApproved() {
		return model.isApproved();
	}

	/**
	 * Returns <code>true</code> if this message boards category is denied.
	 *
	 * @return <code>true</code> if this message boards category is denied; <code>false</code> otherwise
	 */
	@Override
	public boolean isDenied() {
		return model.isDenied();
	}

	/**
	 * Returns <code>true</code> if this message boards category is a draft.
	 *
	 * @return <code>true</code> if this message boards category is a draft; <code>false</code> otherwise
	 */
	@Override
	public boolean isDraft() {
		return model.isDraft();
	}

	/**
	 * Returns <code>true</code> if this message boards category is expired.
	 *
	 * @return <code>true</code> if this message boards category is expired; <code>false</code> otherwise
	 */
	@Override
	public boolean isExpired() {
		return model.isExpired();
	}

	/**
	 * Returns <code>true</code> if this message boards category is inactive.
	 *
	 * @return <code>true</code> if this message boards category is inactive; <code>false</code> otherwise
	 */
	@Override
	public boolean isInactive() {
		return model.isInactive();
	}

	/**
	 * Returns <code>true</code> if this message boards category is incomplete.
	 *
	 * @return <code>true</code> if this message boards category is incomplete; <code>false</code> otherwise
	 */
	@Override
	public boolean isIncomplete() {
		return model.isIncomplete();
	}

	/**
	 * Returns <code>true</code> if this message boards category is in the Recycle Bin.
	 *
	 * @return <code>true</code> if this message boards category is in the Recycle Bin; <code>false</code> otherwise
	 */
	@Override
	public boolean isInTrash() {
		return model.isInTrash();
	}

	/**
	 * Returns <code>true</code> if this message boards category is pending.
	 *
	 * @return <code>true</code> if this message boards category is pending; <code>false</code> otherwise
	 */
	@Override
	public boolean isPending() {
		return model.isPending();
	}

	@Override
	public boolean isRoot() {
		return model.isRoot();
	}

	/**
	 * Returns <code>true</code> if this message boards category is scheduled.
	 *
	 * @return <code>true</code> if this message boards category is scheduled; <code>false</code> otherwise
	 */
	@Override
	public boolean isScheduled() {
		return model.isScheduled();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the category ID of this message boards category.
	 *
	 * @param categoryId the category ID of this message boards category
	 */
	@Override
	public void setCategoryId(long categoryId) {
		model.setCategoryId(categoryId);
	}

	/**
	 * Sets the company ID of this message boards category.
	 *
	 * @param companyId the company ID of this message boards category
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the container model ID of this message boards category.
	 *
	 * @param containerModelId the container model ID of this message boards category
	 */
	@Override
	public void setContainerModelId(long containerModelId) {
		model.setContainerModelId(containerModelId);
	}

	/**
	 * Sets the create date of this message boards category.
	 *
	 * @param createDate the create date of this message boards category
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct collection ID of this message boards category.
	 *
	 * @param ctCollectionId the ct collection ID of this message boards category
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the description of this message boards category.
	 *
	 * @param description the description of this message boards category
	 */
	@Override
	public void setDescription(String description) {
		model.setDescription(description);
	}

	/**
	 * Sets the display style of this message boards category.
	 *
	 * @param displayStyle the display style of this message boards category
	 */
	@Override
	public void setDisplayStyle(String displayStyle) {
		model.setDisplayStyle(displayStyle);
	}

	/**
	 * Sets the friendly url of this message boards category.
	 *
	 * @param friendlyURL the friendly url of this message boards category
	 */
	@Override
	public void setFriendlyURL(String friendlyURL) {
		model.setFriendlyURL(friendlyURL);
	}

	/**
	 * Sets the group ID of this message boards category.
	 *
	 * @param groupId the group ID of this message boards category
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the last publish date of this message boards category.
	 *
	 * @param lastPublishDate the last publish date of this message boards category
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		model.setLastPublishDate(lastPublishDate);
	}

	/**
	 * Sets the modified date of this message boards category.
	 *
	 * @param modifiedDate the modified date of this message boards category
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this message boards category.
	 *
	 * @param mvccVersion the mvcc version of this message boards category
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this message boards category.
	 *
	 * @param name the name of this message boards category
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the parent category ID of this message boards category.
	 *
	 * @param parentCategoryId the parent category ID of this message boards category
	 */
	@Override
	public void setParentCategoryId(long parentCategoryId) {
		model.setParentCategoryId(parentCategoryId);
	}

	/**
	 * Sets the parent container model ID of this message boards category.
	 *
	 * @param parentContainerModelId the parent container model ID of this message boards category
	 */
	@Override
	public void setParentContainerModelId(long parentContainerModelId) {
		model.setParentContainerModelId(parentContainerModelId);
	}

	/**
	 * Sets the primary key of this message boards category.
	 *
	 * @param primaryKey the primary key of this message boards category
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the status of this message boards category.
	 *
	 * @param status the status of this message boards category
	 */
	@Override
	public void setStatus(int status) {
		model.setStatus(status);
	}

	/**
	 * Sets the status by user ID of this message boards category.
	 *
	 * @param statusByUserId the status by user ID of this message boards category
	 */
	@Override
	public void setStatusByUserId(long statusByUserId) {
		model.setStatusByUserId(statusByUserId);
	}

	/**
	 * Sets the status by user name of this message boards category.
	 *
	 * @param statusByUserName the status by user name of this message boards category
	 */
	@Override
	public void setStatusByUserName(String statusByUserName) {
		model.setStatusByUserName(statusByUserName);
	}

	/**
	 * Sets the status by user uuid of this message boards category.
	 *
	 * @param statusByUserUuid the status by user uuid of this message boards category
	 */
	@Override
	public void setStatusByUserUuid(String statusByUserUuid) {
		model.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	 * Sets the status date of this message boards category.
	 *
	 * @param statusDate the status date of this message boards category
	 */
	@Override
	public void setStatusDate(Date statusDate) {
		model.setStatusDate(statusDate);
	}

	/**
	 * Sets the user ID of this message boards category.
	 *
	 * @param userId the user ID of this message boards category
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this message boards category.
	 *
	 * @param userName the user name of this message boards category
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this message boards category.
	 *
	 * @param userUuid the user uuid of this message boards category
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this message boards category.
	 *
	 * @param uuid the uuid of this message boards category
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	public Map<String, Function<MBCategory, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<MBCategory, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected MBCategoryWrapper wrap(MBCategory mbCategory) {
		return new MBCategoryWrapper(mbCategory);
	}

}