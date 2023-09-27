/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link ObjectFolderItem}.
 * </p>
 *
 * @author Marco Leo
 * @see ObjectFolderItem
 * @generated
 */
public class ObjectFolderItemWrapper
	extends BaseModelWrapper<ObjectFolderItem>
	implements ModelWrapper<ObjectFolderItem>, ObjectFolderItem {

	public ObjectFolderItemWrapper(ObjectFolderItem objectFolderItem) {
		super(objectFolderItem);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("objectFolderItemId", getObjectFolderItemId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("objectDefinitionId", getObjectDefinitionId());
		attributes.put("objectFolderId", getObjectFolderId());
		attributes.put("positionX", getPositionX());
		attributes.put("positionY", getPositionY());

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

		Long objectFolderItemId = (Long)attributes.get("objectFolderItemId");

		if (objectFolderItemId != null) {
			setObjectFolderItemId(objectFolderItemId);
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

		Long objectDefinitionId = (Long)attributes.get("objectDefinitionId");

		if (objectDefinitionId != null) {
			setObjectDefinitionId(objectDefinitionId);
		}

		Long objectFolderId = (Long)attributes.get("objectFolderId");

		if (objectFolderId != null) {
			setObjectFolderId(objectFolderId);
		}

		Integer positionX = (Integer)attributes.get("positionX");

		if (positionX != null) {
			setPositionX(positionX);
		}

		Integer positionY = (Integer)attributes.get("positionY");

		if (positionY != null) {
			setPositionY(positionY);
		}
	}

	@Override
	public ObjectFolderItem cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this object folder item.
	 *
	 * @return the company ID of this object folder item
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this object folder item.
	 *
	 * @return the create date of this object folder item
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the modified date of this object folder item.
	 *
	 * @return the modified date of this object folder item
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this object folder item.
	 *
	 * @return the mvcc version of this object folder item
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the object definition ID of this object folder item.
	 *
	 * @return the object definition ID of this object folder item
	 */
	@Override
	public long getObjectDefinitionId() {
		return model.getObjectDefinitionId();
	}

	/**
	 * Returns the object folder ID of this object folder item.
	 *
	 * @return the object folder ID of this object folder item
	 */
	@Override
	public long getObjectFolderId() {
		return model.getObjectFolderId();
	}

	/**
	 * Returns the object folder item ID of this object folder item.
	 *
	 * @return the object folder item ID of this object folder item
	 */
	@Override
	public long getObjectFolderItemId() {
		return model.getObjectFolderItemId();
	}

	/**
	 * Returns the position x of this object folder item.
	 *
	 * @return the position x of this object folder item
	 */
	@Override
	public int getPositionX() {
		return model.getPositionX();
	}

	/**
	 * Returns the position y of this object folder item.
	 *
	 * @return the position y of this object folder item
	 */
	@Override
	public int getPositionY() {
		return model.getPositionY();
	}

	/**
	 * Returns the primary key of this object folder item.
	 *
	 * @return the primary key of this object folder item
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this object folder item.
	 *
	 * @return the user ID of this object folder item
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this object folder item.
	 *
	 * @return the user name of this object folder item
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this object folder item.
	 *
	 * @return the user uuid of this object folder item
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this object folder item.
	 *
	 * @return the uuid of this object folder item
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this object folder item.
	 *
	 * @param companyId the company ID of this object folder item
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this object folder item.
	 *
	 * @param createDate the create date of this object folder item
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the modified date of this object folder item.
	 *
	 * @param modifiedDate the modified date of this object folder item
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this object folder item.
	 *
	 * @param mvccVersion the mvcc version of this object folder item
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the object definition ID of this object folder item.
	 *
	 * @param objectDefinitionId the object definition ID of this object folder item
	 */
	@Override
	public void setObjectDefinitionId(long objectDefinitionId) {
		model.setObjectDefinitionId(objectDefinitionId);
	}

	/**
	 * Sets the object folder ID of this object folder item.
	 *
	 * @param objectFolderId the object folder ID of this object folder item
	 */
	@Override
	public void setObjectFolderId(long objectFolderId) {
		model.setObjectFolderId(objectFolderId);
	}

	/**
	 * Sets the object folder item ID of this object folder item.
	 *
	 * @param objectFolderItemId the object folder item ID of this object folder item
	 */
	@Override
	public void setObjectFolderItemId(long objectFolderItemId) {
		model.setObjectFolderItemId(objectFolderItemId);
	}

	/**
	 * Sets the position x of this object folder item.
	 *
	 * @param positionX the position x of this object folder item
	 */
	@Override
	public void setPositionX(int positionX) {
		model.setPositionX(positionX);
	}

	/**
	 * Sets the position y of this object folder item.
	 *
	 * @param positionY the position y of this object folder item
	 */
	@Override
	public void setPositionY(int positionY) {
		model.setPositionY(positionY);
	}

	/**
	 * Sets the primary key of this object folder item.
	 *
	 * @param primaryKey the primary key of this object folder item
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this object folder item.
	 *
	 * @param userId the user ID of this object folder item
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this object folder item.
	 *
	 * @param userName the user name of this object folder item
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this object folder item.
	 *
	 * @param userUuid the user uuid of this object folder item
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this object folder item.
	 *
	 * @param uuid the uuid of this object folder item
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
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected ObjectFolderItemWrapper wrap(ObjectFolderItem objectFolderItem) {
		return new ObjectFolderItemWrapper(objectFolderItem);
	}

}