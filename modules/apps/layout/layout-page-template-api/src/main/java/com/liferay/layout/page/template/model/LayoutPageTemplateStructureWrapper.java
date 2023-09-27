/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.model;

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
		attributes.put("ctCollectionId", getCtCollectionId());
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
		attributes.put("plid", getPlid());

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

		Long plid = (Long)attributes.get("plid");

		if (plid != null) {
			setPlid(plid);
		}
	}

	@Override
	public LayoutPageTemplateStructure cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
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

	/**
	 * Returns the ct collection ID of this layout page template structure.
	 *
	 * @return the ct collection ID of this layout page template structure
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	@Override
	public String getData(long segmentsExperienceId) {
		return model.getData(segmentsExperienceId);
	}

	@Override
	public String getData(String segmentsExperienceKey) {
		return model.getData(segmentsExperienceKey);
	}

	@Override
	public String getDefaultSegmentsExperienceData() {
		return model.getDefaultSegmentsExperienceData();
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
	 * Returns the plid of this layout page template structure.
	 *
	 * @return the plid of this layout page template structure
	 */
	@Override
	public long getPlid() {
		return model.getPlid();
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

	@Override
	public void persist() {
		model.persist();
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
	 * Sets the ct collection ID of this layout page template structure.
	 *
	 * @param ctCollectionId the ct collection ID of this layout page template structure
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
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
	 * Sets the plid of this layout page template structure.
	 *
	 * @param plid the plid of this layout page template structure
	 */
	@Override
	public void setPlid(long plid) {
		model.setPlid(plid);
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
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	public Map<String, Function<LayoutPageTemplateStructure, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<LayoutPageTemplateStructure, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
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