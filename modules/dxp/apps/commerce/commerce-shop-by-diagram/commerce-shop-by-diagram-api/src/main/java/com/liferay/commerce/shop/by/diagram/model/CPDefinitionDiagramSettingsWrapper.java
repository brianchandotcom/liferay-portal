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

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link CPDefinitionDiagramSettings}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CPDefinitionDiagramSettings
 * @generated
 */
public class CPDefinitionDiagramSettingsWrapper
	extends BaseModelWrapper<CPDefinitionDiagramSettings>
	implements CPDefinitionDiagramSettings,
			   ModelWrapper<CPDefinitionDiagramSettings> {

	public CPDefinitionDiagramSettingsWrapper(
		CPDefinitionDiagramSettings cpDefinitionDiagramSettings) {

		super(cpDefinitionDiagramSettings);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put(
			"CPDefinitionDiagramSettingsId",
			getCPDefinitionDiagramSettingsId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("CPDefinitionId", getCPDefinitionId());
		attributes.put("assetCategoryId", getAssetCategoryId());
		attributes.put("name", getName());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long CPDefinitionDiagramSettingsId = (Long)attributes.get(
			"CPDefinitionDiagramSettingsId");

		if (CPDefinitionDiagramSettingsId != null) {
			setCPDefinitionDiagramSettingsId(CPDefinitionDiagramSettingsId);
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

		Long CPDefinitionId = (Long)attributes.get("CPDefinitionId");

		if (CPDefinitionId != null) {
			setCPDefinitionId(CPDefinitionId);
		}

		Long assetCategoryId = (Long)attributes.get("assetCategoryId");

		if (assetCategoryId != null) {
			setAssetCategoryId(assetCategoryId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}
	}

	@Override
	public com.liferay.asset.kernel.model.AssetCategory fetchAssetCategory() {
		return model.fetchAssetCategory();
	}

	@Override
	public com.liferay.commerce.product.model.CPAttachmentFileEntry
			fetchCPAttachmentFileEntry()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.fetchCPAttachmentFileEntry();
	}

	/**
	 * Returns the asset category ID of this cp definition diagram settings.
	 *
	 * @return the asset category ID of this cp definition diagram settings
	 */
	@Override
	public long getAssetCategoryId() {
		return model.getAssetCategoryId();
	}

	/**
	 * Returns the company ID of this cp definition diagram settings.
	 *
	 * @return the company ID of this cp definition diagram settings
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	@Override
	public com.liferay.commerce.product.model.CPDefinition getCPDefinition()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getCPDefinition();
	}

	/**
	 * Returns the cp definition diagram settings ID of this cp definition diagram settings.
	 *
	 * @return the cp definition diagram settings ID of this cp definition diagram settings
	 */
	@Override
	public long getCPDefinitionDiagramSettingsId() {
		return model.getCPDefinitionDiagramSettingsId();
	}

	/**
	 * Returns the cp definition ID of this cp definition diagram settings.
	 *
	 * @return the cp definition ID of this cp definition diagram settings
	 */
	@Override
	public long getCPDefinitionId() {
		return model.getCPDefinitionId();
	}

	/**
	 * Returns the create date of this cp definition diagram settings.
	 *
	 * @return the create date of this cp definition diagram settings
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the modified date of this cp definition diagram settings.
	 *
	 * @return the modified date of this cp definition diagram settings
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the name of this cp definition diagram settings.
	 *
	 * @return the name of this cp definition diagram settings
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the primary key of this cp definition diagram settings.
	 *
	 * @return the primary key of this cp definition diagram settings
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this cp definition diagram settings.
	 *
	 * @return the user ID of this cp definition diagram settings
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this cp definition diagram settings.
	 *
	 * @return the user name of this cp definition diagram settings
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this cp definition diagram settings.
	 *
	 * @return the user uuid of this cp definition diagram settings
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the asset category ID of this cp definition diagram settings.
	 *
	 * @param assetCategoryId the asset category ID of this cp definition diagram settings
	 */
	@Override
	public void setAssetCategoryId(long assetCategoryId) {
		model.setAssetCategoryId(assetCategoryId);
	}

	/**
	 * Sets the company ID of this cp definition diagram settings.
	 *
	 * @param companyId the company ID of this cp definition diagram settings
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the cp definition diagram settings ID of this cp definition diagram settings.
	 *
	 * @param CPDefinitionDiagramSettingsId the cp definition diagram settings ID of this cp definition diagram settings
	 */
	@Override
	public void setCPDefinitionDiagramSettingsId(
		long CPDefinitionDiagramSettingsId) {

		model.setCPDefinitionDiagramSettingsId(CPDefinitionDiagramSettingsId);
	}

	/**
	 * Sets the cp definition ID of this cp definition diagram settings.
	 *
	 * @param CPDefinitionId the cp definition ID of this cp definition diagram settings
	 */
	@Override
	public void setCPDefinitionId(long CPDefinitionId) {
		model.setCPDefinitionId(CPDefinitionId);
	}

	/**
	 * Sets the create date of this cp definition diagram settings.
	 *
	 * @param createDate the create date of this cp definition diagram settings
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the modified date of this cp definition diagram settings.
	 *
	 * @param modifiedDate the modified date of this cp definition diagram settings
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the name of this cp definition diagram settings.
	 *
	 * @param name the name of this cp definition diagram settings
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the primary key of this cp definition diagram settings.
	 *
	 * @param primaryKey the primary key of this cp definition diagram settings
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this cp definition diagram settings.
	 *
	 * @param userId the user ID of this cp definition diagram settings
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this cp definition diagram settings.
	 *
	 * @param userName the user name of this cp definition diagram settings
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this cp definition diagram settings.
	 *
	 * @param userUuid the user uuid of this cp definition diagram settings
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected CPDefinitionDiagramSettingsWrapper wrap(
		CPDefinitionDiagramSettings cpDefinitionDiagramSettings) {

		return new CPDefinitionDiagramSettingsWrapper(
			cpDefinitionDiagramSettings);
	}

}