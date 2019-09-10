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

package com.liferay.layout.page.template.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link LayoutPageTemplateStructure}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPageTemplateStructure
 * @generated
 */
public class LayoutPageTemplateStructureWrapper
	extends BaseModelWrapper<LayoutPageTemplateStructure>
	implements LayoutPageTemplateStructure,
			   ModelWrapper<LayoutPageTemplateStructure> {

	public LayoutPageTemplateStructureWrapper(
		LayoutPageTemplateStructure layoutPageTemplateStructure) {

		super(layoutPageTemplateStructure);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put(
			"layoutPageTemplateStructureId",
			getLayoutPageTemplateStructureId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());

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

		Long layoutPageTemplateStructureId = (Long)attributes.get(
			"layoutPageTemplateStructureId");

		if (layoutPageTemplateStructureId != null) {
			setLayoutPageTemplateStructureId(layoutPageTemplateStructureId);
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
	}

	/**
	 * Returns the fully qualified class name of this layout page template structure.
	 *
	 * @return the fully qualified class name of this layout page template structure
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the class name ID of this layout page template structure.
	 *
	 * @return the class name ID of this layout page template structure
	 */
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	 * Returns the class pk of this layout page template structure.
	 *
	 * @return the class pk of this layout page template structure
	 */
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	 * Returns the company ID of this layout page template structure.
	 *
	 * @return the company ID of this layout page template structure
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this layout page template structure.
	 *
	 * @return the create date of this layout page template structure
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	@Override
	public String getData(long segmentsExperienceId) {
		return model.getData(segmentsExperienceId);
	}

	@Override
	public String getData(long[] segmentsExperienceIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getData(segmentsExperienceIds);
	}

	/**
	 * Returns the group ID of this layout page template structure.
	 *
	 * @return the group ID of this layout page template structure
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the layout page template structure ID of this layout page template structure.
	 *
	 * @return the layout page template structure ID of this layout page template structure
	 */
	@Override
	public long getLayoutPageTemplateStructureId() {
		return model.getLayoutPageTemplateStructureId();
	}

	/**
	 * Returns the modified date of this layout page template structure.
	 *
	 * @return the modified date of this layout page template structure
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this layout page template structure.
	 *
	 * @return the mvcc version of this layout page template structure
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this layout page template structure.
	 *
	 * @return the primary key of this layout page template structure
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this layout page template structure.
	 *
	 * @return the user ID of this layout page template structure
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this layout page template structure.
	 *
	 * @return the user name of this layout page template structure
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this layout page template structure.
	 *
	 * @return the user uuid of this layout page template structure
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this layout page template structure.
	 *
	 * @return the uuid of this layout page template structure
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a layout page template structure model instance should use the <code>LayoutPageTemplateStructure</code> interface instead.
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
	 * Sets the class name ID of this layout page template structure.
	 *
	 * @param classNameId the class name ID of this layout page template structure
	 */
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	 * Sets the class pk of this layout page template structure.
	 *
	 * @param classPK the class pk of this layout page template structure
	 */
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	 * Sets the company ID of this layout page template structure.
	 *
	 * @param companyId the company ID of this layout page template structure
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this layout page template structure.
	 *
	 * @param createDate the create date of this layout page template structure
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the group ID of this layout page template structure.
	 *
	 * @param groupId the group ID of this layout page template structure
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the layout page template structure ID of this layout page template structure.
	 *
	 * @param layoutPageTemplateStructureId the layout page template structure ID of this layout page template structure
	 */
	@Override
	public void setLayoutPageTemplateStructureId(
		long layoutPageTemplateStructureId) {

		model.setLayoutPageTemplateStructureId(layoutPageTemplateStructureId);
	}

	/**
	 * Sets the modified date of this layout page template structure.
	 *
	 * @param modifiedDate the modified date of this layout page template structure
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this layout page template structure.
	 *
	 * @param mvccVersion the mvcc version of this layout page template structure
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this layout page template structure.
	 *
	 * @param primaryKey the primary key of this layout page template structure
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this layout page template structure.
	 *
	 * @param userId the user ID of this layout page template structure
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this layout page template structure.
	 *
	 * @param userName the user name of this layout page template structure
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this layout page template structure.
	 *
	 * @param userUuid the user uuid of this layout page template structure
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this layout page template structure.
	 *
	 * @param uuid the uuid of this layout page template structure
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
	protected LayoutPageTemplateStructureWrapper wrap(
		LayoutPageTemplateStructure layoutPageTemplateStructure) {

		return new LayoutPageTemplateStructureWrapper(
			layoutPageTemplateStructure);
	}

}