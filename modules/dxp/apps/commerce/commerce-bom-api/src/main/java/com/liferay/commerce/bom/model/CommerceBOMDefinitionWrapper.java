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

package com.liferay.commerce.bom.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link CommerceBOMDefinition}.
 * </p>
 *
 * @author Luca Pellizzon
 * @see CommerceBOMDefinition
 * @generated
 */
public class CommerceBOMDefinitionWrapper
	extends BaseModelWrapper<CommerceBOMDefinition>
	implements CommerceBOMDefinition, ModelWrapper<CommerceBOMDefinition> {

	public CommerceBOMDefinitionWrapper(
		CommerceBOMDefinition commerceBOMDefinition) {

		super(commerceBOMDefinition);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("commerceBOMDefinitionId", getCommerceBOMDefinitionId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("commerceBOMFolderId", getCommerceBOMFolderId());
		attributes.put("CPAttachmentFileEntryId", getCPAttachmentFileEntryId());
		attributes.put("name", getName());
		attributes.put("friendlyUrl", getFriendlyUrl());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long commerceBOMDefinitionId = (Long)attributes.get(
			"commerceBOMDefinitionId");

		if (commerceBOMDefinitionId != null) {
			setCommerceBOMDefinitionId(commerceBOMDefinitionId);
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

		Long commerceBOMFolderId = (Long)attributes.get("commerceBOMFolderId");

		if (commerceBOMFolderId != null) {
			setCommerceBOMFolderId(commerceBOMFolderId);
		}

		Long CPAttachmentFileEntryId = (Long)attributes.get(
			"CPAttachmentFileEntryId");

		if (CPAttachmentFileEntryId != null) {
			setCPAttachmentFileEntryId(CPAttachmentFileEntryId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String friendlyUrl = (String)attributes.get("friendlyUrl");

		if (friendlyUrl != null) {
			setFriendlyUrl(friendlyUrl);
		}
	}

	@Override
	public CommerceBOMFolder fetchCommerceBOMFolder() {
		return model.fetchCommerceBOMFolder();
	}

	@Override
	public com.liferay.commerce.product.model.CPAttachmentFileEntry
		fetchCPAttachmentFileEntry() {

		return model.fetchCPAttachmentFileEntry();
	}

	/**
	 * Returns the commerce bom definition ID of this commerce bom definition.
	 *
	 * @return the commerce bom definition ID of this commerce bom definition
	 */
	@Override
	public long getCommerceBOMDefinitionId() {
		return model.getCommerceBOMDefinitionId();
	}

	/**
	 * Returns the commerce bom folder ID of this commerce bom definition.
	 *
	 * @return the commerce bom folder ID of this commerce bom definition
	 */
	@Override
	public long getCommerceBOMFolderId() {
		return model.getCommerceBOMFolderId();
	}

	/**
	 * Returns the company ID of this commerce bom definition.
	 *
	 * @return the company ID of this commerce bom definition
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the cp attachment file entry ID of this commerce bom definition.
	 *
	 * @return the cp attachment file entry ID of this commerce bom definition
	 */
	@Override
	public long getCPAttachmentFileEntryId() {
		return model.getCPAttachmentFileEntryId();
	}

	/**
	 * Returns the create date of this commerce bom definition.
	 *
	 * @return the create date of this commerce bom definition
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the friendly url of this commerce bom definition.
	 *
	 * @return the friendly url of this commerce bom definition
	 */
	@Override
	public String getFriendlyUrl() {
		return model.getFriendlyUrl();
	}

	/**
	 * Returns the modified date of this commerce bom definition.
	 *
	 * @return the modified date of this commerce bom definition
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the name of this commerce bom definition.
	 *
	 * @return the name of this commerce bom definition
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the primary key of this commerce bom definition.
	 *
	 * @return the primary key of this commerce bom definition
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this commerce bom definition.
	 *
	 * @return the user ID of this commerce bom definition
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this commerce bom definition.
	 *
	 * @return the user name of this commerce bom definition
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce bom definition.
	 *
	 * @return the user uuid of this commerce bom definition
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
	 * Sets the commerce bom definition ID of this commerce bom definition.
	 *
	 * @param commerceBOMDefinitionId the commerce bom definition ID of this commerce bom definition
	 */
	@Override
	public void setCommerceBOMDefinitionId(long commerceBOMDefinitionId) {
		model.setCommerceBOMDefinitionId(commerceBOMDefinitionId);
	}

	/**
	 * Sets the commerce bom folder ID of this commerce bom definition.
	 *
	 * @param commerceBOMFolderId the commerce bom folder ID of this commerce bom definition
	 */
	@Override
	public void setCommerceBOMFolderId(long commerceBOMFolderId) {
		model.setCommerceBOMFolderId(commerceBOMFolderId);
	}

	/**
	 * Sets the company ID of this commerce bom definition.
	 *
	 * @param companyId the company ID of this commerce bom definition
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the cp attachment file entry ID of this commerce bom definition.
	 *
	 * @param CPAttachmentFileEntryId the cp attachment file entry ID of this commerce bom definition
	 */
	@Override
	public void setCPAttachmentFileEntryId(long CPAttachmentFileEntryId) {
		model.setCPAttachmentFileEntryId(CPAttachmentFileEntryId);
	}

	/**
	 * Sets the create date of this commerce bom definition.
	 *
	 * @param createDate the create date of this commerce bom definition
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the friendly url of this commerce bom definition.
	 *
	 * @param friendlyUrl the friendly url of this commerce bom definition
	 */
	@Override
	public void setFriendlyUrl(String friendlyUrl) {
		model.setFriendlyUrl(friendlyUrl);
	}

	/**
	 * Sets the modified date of this commerce bom definition.
	 *
	 * @param modifiedDate the modified date of this commerce bom definition
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the name of this commerce bom definition.
	 *
	 * @param name the name of this commerce bom definition
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the primary key of this commerce bom definition.
	 *
	 * @param primaryKey the primary key of this commerce bom definition
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this commerce bom definition.
	 *
	 * @param userId the user ID of this commerce bom definition
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce bom definition.
	 *
	 * @param userName the user name of this commerce bom definition
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce bom definition.
	 *
	 * @param userUuid the user uuid of this commerce bom definition
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected CommerceBOMDefinitionWrapper wrap(
		CommerceBOMDefinition commerceBOMDefinition) {

		return new CommerceBOMDefinitionWrapper(commerceBOMDefinition);
	}

}