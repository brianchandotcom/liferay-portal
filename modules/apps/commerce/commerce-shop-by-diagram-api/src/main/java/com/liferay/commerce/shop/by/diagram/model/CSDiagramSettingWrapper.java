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

package com.liferay.commerce.shop.by.diagram.model;

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
 * This class is a wrapper for {@link CSDiagramSetting}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CSDiagramSetting
 * @generated
 */
public class CSDiagramSettingWrapper
	extends BaseModelWrapper<CSDiagramSetting>
	implements CSDiagramSetting, ModelWrapper<CSDiagramSetting> {

	public CSDiagramSettingWrapper(CSDiagramSetting csDiagramSetting) {
		super(csDiagramSetting);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("uuid", getUuid());
		attributes.put("CSDiagramSettingId", getCSDiagramSettingId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("CPAttachmentFileEntryId", getCPAttachmentFileEntryId());
		attributes.put("CPDefinitionId", getCPDefinitionId());
		attributes.put("color", getColor());
		attributes.put("radius", getRadius());
		attributes.put("type", getType());

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

		Long CSDiagramSettingId = (Long)attributes.get("CSDiagramSettingId");

		if (CSDiagramSettingId != null) {
			setCSDiagramSettingId(CSDiagramSettingId);
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

		Long CPAttachmentFileEntryId = (Long)attributes.get(
			"CPAttachmentFileEntryId");

		if (CPAttachmentFileEntryId != null) {
			setCPAttachmentFileEntryId(CPAttachmentFileEntryId);
		}

		Long CPDefinitionId = (Long)attributes.get("CPDefinitionId");

		if (CPDefinitionId != null) {
			setCPDefinitionId(CPDefinitionId);
		}

		String color = (String)attributes.get("color");

		if (color != null) {
			setColor(color);
		}

		Double radius = (Double)attributes.get("radius");

		if (radius != null) {
			setRadius(radius);
		}

		String type = (String)attributes.get("type");

		if (type != null) {
			setType(type);
		}
	}

	@Override
	public CSDiagramSetting cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the color of this cs diagram setting.
	 *
	 * @return the color of this cs diagram setting
	 */
	@Override
	public String getColor() {
		return model.getColor();
	}

	/**
	 * Returns the company ID of this cs diagram setting.
	 *
	 * @return the company ID of this cs diagram setting
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	@Override
	public com.liferay.commerce.product.model.CPAttachmentFileEntry
			getCPAttachmentFileEntry()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getCPAttachmentFileEntry();
	}

	/**
	 * Returns the cp attachment file entry ID of this cs diagram setting.
	 *
	 * @return the cp attachment file entry ID of this cs diagram setting
	 */
	@Override
	public long getCPAttachmentFileEntryId() {
		return model.getCPAttachmentFileEntryId();
	}

	@Override
	public com.liferay.commerce.product.model.CPDefinition getCPDefinition()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getCPDefinition();
	}

	/**
	 * Returns the cp definition ID of this cs diagram setting.
	 *
	 * @return the cp definition ID of this cs diagram setting
	 */
	@Override
	public long getCPDefinitionId() {
		return model.getCPDefinitionId();
	}

	/**
	 * Returns the create date of this cs diagram setting.
	 *
	 * @return the create date of this cs diagram setting
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the cs diagram setting ID of this cs diagram setting.
	 *
	 * @return the cs diagram setting ID of this cs diagram setting
	 */
	@Override
	public long getCSDiagramSettingId() {
		return model.getCSDiagramSettingId();
	}

	/**
	 * Returns the ct collection ID of this cs diagram setting.
	 *
	 * @return the ct collection ID of this cs diagram setting
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the modified date of this cs diagram setting.
	 *
	 * @return the modified date of this cs diagram setting
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this cs diagram setting.
	 *
	 * @return the mvcc version of this cs diagram setting
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this cs diagram setting.
	 *
	 * @return the primary key of this cs diagram setting
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the radius of this cs diagram setting.
	 *
	 * @return the radius of this cs diagram setting
	 */
	@Override
	public double getRadius() {
		return model.getRadius();
	}

	/**
	 * Returns the type of this cs diagram setting.
	 *
	 * @return the type of this cs diagram setting
	 */
	@Override
	public String getType() {
		return model.getType();
	}

	/**
	 * Returns the user ID of this cs diagram setting.
	 *
	 * @return the user ID of this cs diagram setting
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this cs diagram setting.
	 *
	 * @return the user name of this cs diagram setting
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this cs diagram setting.
	 *
	 * @return the user uuid of this cs diagram setting
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this cs diagram setting.
	 *
	 * @return the uuid of this cs diagram setting
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
	 * Sets the color of this cs diagram setting.
	 *
	 * @param color the color of this cs diagram setting
	 */
	@Override
	public void setColor(String color) {
		model.setColor(color);
	}

	/**
	 * Sets the company ID of this cs diagram setting.
	 *
	 * @param companyId the company ID of this cs diagram setting
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the cp attachment file entry ID of this cs diagram setting.
	 *
	 * @param CPAttachmentFileEntryId the cp attachment file entry ID of this cs diagram setting
	 */
	@Override
	public void setCPAttachmentFileEntryId(long CPAttachmentFileEntryId) {
		model.setCPAttachmentFileEntryId(CPAttachmentFileEntryId);
	}

	/**
	 * Sets the cp definition ID of this cs diagram setting.
	 *
	 * @param CPDefinitionId the cp definition ID of this cs diagram setting
	 */
	@Override
	public void setCPDefinitionId(long CPDefinitionId) {
		model.setCPDefinitionId(CPDefinitionId);
	}

	/**
	 * Sets the create date of this cs diagram setting.
	 *
	 * @param createDate the create date of this cs diagram setting
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the cs diagram setting ID of this cs diagram setting.
	 *
	 * @param CSDiagramSettingId the cs diagram setting ID of this cs diagram setting
	 */
	@Override
	public void setCSDiagramSettingId(long CSDiagramSettingId) {
		model.setCSDiagramSettingId(CSDiagramSettingId);
	}

	/**
	 * Sets the ct collection ID of this cs diagram setting.
	 *
	 * @param ctCollectionId the ct collection ID of this cs diagram setting
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the modified date of this cs diagram setting.
	 *
	 * @param modifiedDate the modified date of this cs diagram setting
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this cs diagram setting.
	 *
	 * @param mvccVersion the mvcc version of this cs diagram setting
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this cs diagram setting.
	 *
	 * @param primaryKey the primary key of this cs diagram setting
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the radius of this cs diagram setting.
	 *
	 * @param radius the radius of this cs diagram setting
	 */
	@Override
	public void setRadius(double radius) {
		model.setRadius(radius);
	}

	/**
	 * Sets the type of this cs diagram setting.
	 *
	 * @param type the type of this cs diagram setting
	 */
	@Override
	public void setType(String type) {
		model.setType(type);
	}

	/**
	 * Sets the user ID of this cs diagram setting.
	 *
	 * @param userId the user ID of this cs diagram setting
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this cs diagram setting.
	 *
	 * @param userName the user name of this cs diagram setting
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this cs diagram setting.
	 *
	 * @param userUuid the user uuid of this cs diagram setting
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this cs diagram setting.
	 *
	 * @param uuid the uuid of this cs diagram setting
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	public Map<String, Function<CSDiagramSetting, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<CSDiagramSetting, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected CSDiagramSettingWrapper wrap(CSDiagramSetting csDiagramSetting) {
		return new CSDiagramSettingWrapper(csDiagramSetting);
	}

}