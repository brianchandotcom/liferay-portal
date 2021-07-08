/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.shop.by.diagram.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link CPDefinitionDiagramSetting}.
 * </p>
 *
 * @author Andrea Sbarra
 * @see CPDefinitionDiagramSetting
 * @generated
 */
public class CPDefinitionDiagramSettingWrapper
	extends BaseModelWrapper<CPDefinitionDiagramSetting>
	implements CPDefinitionDiagramSetting,
			   ModelWrapper<CPDefinitionDiagramSetting> {

	public CPDefinitionDiagramSettingWrapper(
		CPDefinitionDiagramSetting cpDefinitionDiagramSetting) {

		super(cpDefinitionDiagramSetting);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put(
			"CPDefinitionDiagramSettingId", getCPDefinitionDiagramSettingId());
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
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long CPDefinitionDiagramSettingId = (Long)attributes.get(
			"CPDefinitionDiagramSettingId");

		if (CPDefinitionDiagramSettingId != null) {
			setCPDefinitionDiagramSettingId(CPDefinitionDiagramSettingId);
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

	/**
	 * Returns the color of this cp definition diagram setting.
	 *
	 * @return the color of this cp definition diagram setting
	 */
	@Override
	public String getColor() {
		return model.getColor();
	}

	/**
	 * Returns the company ID of this cp definition diagram setting.
	 *
	 * @return the company ID of this cp definition diagram setting
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
	 * Returns the cp attachment file entry ID of this cp definition diagram setting.
	 *
	 * @return the cp attachment file entry ID of this cp definition diagram setting
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
	 * Returns the cp definition diagram setting ID of this cp definition diagram setting.
	 *
	 * @return the cp definition diagram setting ID of this cp definition diagram setting
	 */
	@Override
	public long getCPDefinitionDiagramSettingId() {
		return model.getCPDefinitionDiagramSettingId();
	}

	/**
	 * Returns the cp definition ID of this cp definition diagram setting.
	 *
	 * @return the cp definition ID of this cp definition diagram setting
	 */
	@Override
	public long getCPDefinitionId() {
		return model.getCPDefinitionId();
	}

	/**
	 * Returns the create date of this cp definition diagram setting.
	 *
	 * @return the create date of this cp definition diagram setting
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the modified date of this cp definition diagram setting.
	 *
	 * @return the modified date of this cp definition diagram setting
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the primary key of this cp definition diagram setting.
	 *
	 * @return the primary key of this cp definition diagram setting
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the radius of this cp definition diagram setting.
	 *
	 * @return the radius of this cp definition diagram setting
	 */
	@Override
	public double getRadius() {
		return model.getRadius();
	}

	/**
	 * Returns the type of this cp definition diagram setting.
	 *
	 * @return the type of this cp definition diagram setting
	 */
	@Override
	public String getType() {
		return model.getType();
	}

	/**
	 * Returns the user ID of this cp definition diagram setting.
	 *
	 * @return the user ID of this cp definition diagram setting
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this cp definition diagram setting.
	 *
	 * @return the user name of this cp definition diagram setting
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this cp definition diagram setting.
	 *
	 * @return the user uuid of this cp definition diagram setting
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this cp definition diagram setting.
	 *
	 * @return the uuid of this cp definition diagram setting
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
	 * Sets the color of this cp definition diagram setting.
	 *
	 * @param color the color of this cp definition diagram setting
	 */
	@Override
	public void setColor(String color) {
		model.setColor(color);
	}

	/**
	 * Sets the company ID of this cp definition diagram setting.
	 *
	 * @param companyId the company ID of this cp definition diagram setting
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the cp attachment file entry ID of this cp definition diagram setting.
	 *
	 * @param CPAttachmentFileEntryId the cp attachment file entry ID of this cp definition diagram setting
	 */
	@Override
	public void setCPAttachmentFileEntryId(long CPAttachmentFileEntryId) {
		model.setCPAttachmentFileEntryId(CPAttachmentFileEntryId);
	}

	/**
	 * Sets the cp definition diagram setting ID of this cp definition diagram setting.
	 *
	 * @param CPDefinitionDiagramSettingId the cp definition diagram setting ID of this cp definition diagram setting
	 */
	@Override
	public void setCPDefinitionDiagramSettingId(
		long CPDefinitionDiagramSettingId) {

		model.setCPDefinitionDiagramSettingId(CPDefinitionDiagramSettingId);
	}

	/**
	 * Sets the cp definition ID of this cp definition diagram setting.
	 *
	 * @param CPDefinitionId the cp definition ID of this cp definition diagram setting
	 */
	@Override
	public void setCPDefinitionId(long CPDefinitionId) {
		model.setCPDefinitionId(CPDefinitionId);
	}

	/**
	 * Sets the create date of this cp definition diagram setting.
	 *
	 * @param createDate the create date of this cp definition diagram setting
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the modified date of this cp definition diagram setting.
	 *
	 * @param modifiedDate the modified date of this cp definition diagram setting
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the primary key of this cp definition diagram setting.
	 *
	 * @param primaryKey the primary key of this cp definition diagram setting
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the radius of this cp definition diagram setting.
	 *
	 * @param radius the radius of this cp definition diagram setting
	 */
	@Override
	public void setRadius(double radius) {
		model.setRadius(radius);
	}

	/**
	 * Sets the type of this cp definition diagram setting.
	 *
	 * @param type the type of this cp definition diagram setting
	 */
	@Override
	public void setType(String type) {
		model.setType(type);
	}

	/**
	 * Sets the user ID of this cp definition diagram setting.
	 *
	 * @param userId the user ID of this cp definition diagram setting
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this cp definition diagram setting.
	 *
	 * @param userName the user name of this cp definition diagram setting
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this cp definition diagram setting.
	 *
	 * @param userUuid the user uuid of this cp definition diagram setting
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this cp definition diagram setting.
	 *
	 * @param uuid the uuid of this cp definition diagram setting
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
	protected CPDefinitionDiagramSettingWrapper wrap(
		CPDefinitionDiagramSetting cpDefinitionDiagramSetting) {

		return new CPDefinitionDiagramSettingWrapper(
			cpDefinitionDiagramSetting);
	}

}