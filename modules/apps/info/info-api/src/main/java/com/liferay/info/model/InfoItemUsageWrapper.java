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

package com.liferay.info.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link InfoItemUsage}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see InfoItemUsage
 * @generated
 */
public class InfoItemUsageWrapper
	extends BaseModelWrapper<InfoItemUsage>
	implements InfoItemUsage, ModelWrapper<InfoItemUsage> {

	public InfoItemUsageWrapper(InfoItemUsage infoItemUsage) {
		super(infoItemUsage);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("infoItemUsageId", getInfoItemUsageId());
		attributes.put("groupId", getGroupId());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("containerKey", getContainerKey());
		attributes.put("containerType", getContainerType());
		attributes.put("plid", getPlid());
		attributes.put("type", getType());
		attributes.put("lastPublishDate", getLastPublishDate());

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

		Long infoItemUsageId = (Long)attributes.get("infoItemUsageId");

		if (infoItemUsageId != null) {
			setInfoItemUsageId(infoItemUsageId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
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

		String containerKey = (String)attributes.get("containerKey");

		if (containerKey != null) {
			setContainerKey(containerKey);
		}

		Long containerType = (Long)attributes.get("containerType");

		if (containerType != null) {
			setContainerType(containerType);
		}

		Long plid = (Long)attributes.get("plid");

		if (plid != null) {
			setPlid(plid);
		}

		Integer type = (Integer)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}
	}

	/**
	 * Returns the fully qualified class name of this info item usage.
	 *
	 * @return the fully qualified class name of this info item usage
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the class name ID of this info item usage.
	 *
	 * @return the class name ID of this info item usage
	 */
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	 * Returns the class pk of this info item usage.
	 *
	 * @return the class pk of this info item usage
	 */
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	 * Returns the container key of this info item usage.
	 *
	 * @return the container key of this info item usage
	 */
	@Override
	public String getContainerKey() {
		return model.getContainerKey();
	}

	/**
	 * Returns the container type of this info item usage.
	 *
	 * @return the container type of this info item usage
	 */
	@Override
	public long getContainerType() {
		return model.getContainerType();
	}

	/**
	 * Returns the create date of this info item usage.
	 *
	 * @return the create date of this info item usage
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the group ID of this info item usage.
	 *
	 * @return the group ID of this info item usage
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the info item usage ID of this info item usage.
	 *
	 * @return the info item usage ID of this info item usage
	 */
	@Override
	public long getInfoItemUsageId() {
		return model.getInfoItemUsageId();
	}

	/**
	 * Returns the last publish date of this info item usage.
	 *
	 * @return the last publish date of this info item usage
	 */
	@Override
	public Date getLastPublishDate() {
		return model.getLastPublishDate();
	}

	/**
	 * Returns the modified date of this info item usage.
	 *
	 * @return the modified date of this info item usage
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this info item usage.
	 *
	 * @return the mvcc version of this info item usage
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the plid of this info item usage.
	 *
	 * @return the plid of this info item usage
	 */
	@Override
	public long getPlid() {
		return model.getPlid();
	}

	/**
	 * Returns the primary key of this info item usage.
	 *
	 * @return the primary key of this info item usage
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the type of this info item usage.
	 *
	 * @return the type of this info item usage
	 */
	@Override
	public int getType() {
		return model.getType();
	}

	/**
	 * Returns the uuid of this info item usage.
	 *
	 * @return the uuid of this info item usage
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a info item usage model instance should use the <code>InfoItemUsage</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	@Override
	public void setClassName(String className) {
		model.setClassName(className);
	}

	/**
	 * Sets the class name ID of this info item usage.
	 *
	 * @param classNameId the class name ID of this info item usage
	 */
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	 * Sets the class pk of this info item usage.
	 *
	 * @param classPK the class pk of this info item usage
	 */
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	 * Sets the container key of this info item usage.
	 *
	 * @param containerKey the container key of this info item usage
	 */
	@Override
	public void setContainerKey(String containerKey) {
		model.setContainerKey(containerKey);
	}

	/**
	 * Sets the container type of this info item usage.
	 *
	 * @param containerType the container type of this info item usage
	 */
	@Override
	public void setContainerType(long containerType) {
		model.setContainerType(containerType);
	}

	/**
	 * Sets the create date of this info item usage.
	 *
	 * @param createDate the create date of this info item usage
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the group ID of this info item usage.
	 *
	 * @param groupId the group ID of this info item usage
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the info item usage ID of this info item usage.
	 *
	 * @param infoItemUsageId the info item usage ID of this info item usage
	 */
	@Override
	public void setInfoItemUsageId(long infoItemUsageId) {
		model.setInfoItemUsageId(infoItemUsageId);
	}

	/**
	 * Sets the last publish date of this info item usage.
	 *
	 * @param lastPublishDate the last publish date of this info item usage
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		model.setLastPublishDate(lastPublishDate);
	}

	/**
	 * Sets the modified date of this info item usage.
	 *
	 * @param modifiedDate the modified date of this info item usage
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this info item usage.
	 *
	 * @param mvccVersion the mvcc version of this info item usage
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the plid of this info item usage.
	 *
	 * @param plid the plid of this info item usage
	 */
	@Override
	public void setPlid(long plid) {
		model.setPlid(plid);
	}

	/**
	 * Sets the primary key of this info item usage.
	 *
	 * @param primaryKey the primary key of this info item usage
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the type of this info item usage.
	 *
	 * @param type the type of this info item usage
	 */
	@Override
	public void setType(int type) {
		model.setType(type);
	}

	/**
	 * Sets the uuid of this info item usage.
	 *
	 * @param uuid the uuid of this info item usage
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	protected InfoItemUsageWrapper wrap(InfoItemUsage infoItemUsage) {
		return new InfoItemUsageWrapper(infoItemUsage);
	}

}