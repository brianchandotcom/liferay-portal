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
 * This class is a wrapper for {@link LayoutPageTemplateStructureRel}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPageTemplateStructureRel
 * @generated
 */
public class LayoutPageTemplateStructureRelWrapper
	extends BaseModelWrapper<LayoutPageTemplateStructureRel>
	implements LayoutPageTemplateStructureRel,
			   ModelWrapper<LayoutPageTemplateStructureRel> {

	public LayoutPageTemplateStructureRelWrapper(
		LayoutPageTemplateStructureRel layoutPageTemplateStructureRel) {

		super(layoutPageTemplateStructureRel);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put(
			"layoutPageTemplateStructureRelId",
			getLayoutPageTemplateStructureRelId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put(
			"layoutPageTemplateStructureId",
			getLayoutPageTemplateStructureId());
		attributes.put("segmentsExperienceId", getSegmentsExperienceId());
		attributes.put("data", getData());

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

		Long layoutPageTemplateStructureRelId = (Long)attributes.get(
			"layoutPageTemplateStructureRelId");

		if (layoutPageTemplateStructureRelId != null) {
			setLayoutPageTemplateStructureRelId(
				layoutPageTemplateStructureRelId);
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

		Long layoutPageTemplateStructureId = (Long)attributes.get(
			"layoutPageTemplateStructureId");

		if (layoutPageTemplateStructureId != null) {
			setLayoutPageTemplateStructureId(layoutPageTemplateStructureId);
		}

		Long segmentsExperienceId = (Long)attributes.get(
			"segmentsExperienceId");

		if (segmentsExperienceId != null) {
			setSegmentsExperienceId(segmentsExperienceId);
		}

		String data = (String)attributes.get("data");

		if (data != null) {
			setData(data);
		}
	}

	/**
	 * Returns the company ID of this layout page template structure rel.
	 *
	 * @return the company ID of this layout page template structure rel
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this layout page template structure rel.
	 *
	 * @return the create date of this layout page template structure rel
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the data of this layout page template structure rel.
	 *
	 * @return the data of this layout page template structure rel
	 */
	@Override
	public String getData() {
		return model.getData();
	}

	/**
	 * Returns the group ID of this layout page template structure rel.
	 *
	 * @return the group ID of this layout page template structure rel
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the layout page template structure ID of this layout page template structure rel.
	 *
	 * @return the layout page template structure ID of this layout page template structure rel
	 */
	@Override
	public long getLayoutPageTemplateStructureId() {
		return model.getLayoutPageTemplateStructureId();
	}

	/**
	 * Returns the layout page template structure rel ID of this layout page template structure rel.
	 *
	 * @return the layout page template structure rel ID of this layout page template structure rel
	 */
	@Override
	public long getLayoutPageTemplateStructureRelId() {
		return model.getLayoutPageTemplateStructureRelId();
	}

	/**
	 * Returns the modified date of this layout page template structure rel.
	 *
	 * @return the modified date of this layout page template structure rel
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this layout page template structure rel.
	 *
	 * @return the mvcc version of this layout page template structure rel
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this layout page template structure rel.
	 *
	 * @return the primary key of this layout page template structure rel
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the segments experience ID of this layout page template structure rel.
	 *
	 * @return the segments experience ID of this layout page template structure rel
	 */
	@Override
	public long getSegmentsExperienceId() {
		return model.getSegmentsExperienceId();
	}

	/**
	 * Returns the user ID of this layout page template structure rel.
	 *
	 * @return the user ID of this layout page template structure rel
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this layout page template structure rel.
	 *
	 * @return the user name of this layout page template structure rel
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this layout page template structure rel.
	 *
	 * @return the user uuid of this layout page template structure rel
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this layout page template structure rel.
	 *
	 * @return the uuid of this layout page template structure rel
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a layout page template structure rel model instance should use the <code>LayoutPageTemplateStructureRel</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this layout page template structure rel.
	 *
	 * @param companyId the company ID of this layout page template structure rel
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this layout page template structure rel.
	 *
	 * @param createDate the create date of this layout page template structure rel
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the data of this layout page template structure rel.
	 *
	 * @param data the data of this layout page template structure rel
	 */
	@Override
	public void setData(String data) {
		model.setData(data);
	}

	/**
	 * Sets the group ID of this layout page template structure rel.
	 *
	 * @param groupId the group ID of this layout page template structure rel
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the layout page template structure ID of this layout page template structure rel.
	 *
	 * @param layoutPageTemplateStructureId the layout page template structure ID of this layout page template structure rel
	 */
	@Override
	public void setLayoutPageTemplateStructureId(
		long layoutPageTemplateStructureId) {

		model.setLayoutPageTemplateStructureId(layoutPageTemplateStructureId);
	}

	/**
	 * Sets the layout page template structure rel ID of this layout page template structure rel.
	 *
	 * @param layoutPageTemplateStructureRelId the layout page template structure rel ID of this layout page template structure rel
	 */
	@Override
	public void setLayoutPageTemplateStructureRelId(
		long layoutPageTemplateStructureRelId) {

		model.setLayoutPageTemplateStructureRelId(
			layoutPageTemplateStructureRelId);
	}

	/**
	 * Sets the modified date of this layout page template structure rel.
	 *
	 * @param modifiedDate the modified date of this layout page template structure rel
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this layout page template structure rel.
	 *
	 * @param mvccVersion the mvcc version of this layout page template structure rel
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this layout page template structure rel.
	 *
	 * @param primaryKey the primary key of this layout page template structure rel
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the segments experience ID of this layout page template structure rel.
	 *
	 * @param segmentsExperienceId the segments experience ID of this layout page template structure rel
	 */
	@Override
	public void setSegmentsExperienceId(long segmentsExperienceId) {
		model.setSegmentsExperienceId(segmentsExperienceId);
	}

	/**
	 * Sets the user ID of this layout page template structure rel.
	 *
	 * @param userId the user ID of this layout page template structure rel
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this layout page template structure rel.
	 *
	 * @param userName the user name of this layout page template structure rel
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this layout page template structure rel.
	 *
	 * @param userUuid the user uuid of this layout page template structure rel
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this layout page template structure rel.
	 *
	 * @param uuid the uuid of this layout page template structure rel
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
	protected LayoutPageTemplateStructureRelWrapper wrap(
		LayoutPageTemplateStructureRel layoutPageTemplateStructureRel) {

		return new LayoutPageTemplateStructureRelWrapper(
			layoutPageTemplateStructureRel);
	}

}