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

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
	implements CommerceBOMDefinition, ModelWrapper<CommerceBOMDefinition> {

	public CommerceBOMDefinitionWrapper(
		CommerceBOMDefinition commerceBOMDefinition) {

		_commerceBOMDefinition = commerceBOMDefinition;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceBOMDefinition.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceBOMDefinition.class.getName();
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
	public Object clone() {
		return new CommerceBOMDefinitionWrapper(
			(CommerceBOMDefinition)_commerceBOMDefinition.clone());
	}

	@Override
	public int compareTo(CommerceBOMDefinition commerceBOMDefinition) {
		return _commerceBOMDefinition.compareTo(commerceBOMDefinition);
	}

	@Override
	public CommerceBOMFolder fetchCommerceBOMFolder() {
		return _commerceBOMDefinition.fetchCommerceBOMFolder();
	}

	@Override
	public com.liferay.commerce.product.model.CPAttachmentFileEntry
		fetchCPAttachmentFileEntry() {

		return _commerceBOMDefinition.fetchCPAttachmentFileEntry();
	}

	/**
	 * Returns the commerce bom definition ID of this commerce bom definition.
	 *
	 * @return the commerce bom definition ID of this commerce bom definition
	 */
	@Override
	public long getCommerceBOMDefinitionId() {
		return _commerceBOMDefinition.getCommerceBOMDefinitionId();
	}

	/**
	 * Returns the commerce bom folder ID of this commerce bom definition.
	 *
	 * @return the commerce bom folder ID of this commerce bom definition
	 */
	@Override
	public long getCommerceBOMFolderId() {
		return _commerceBOMDefinition.getCommerceBOMFolderId();
	}

	/**
	 * Returns the company ID of this commerce bom definition.
	 *
	 * @return the company ID of this commerce bom definition
	 */
	@Override
	public long getCompanyId() {
		return _commerceBOMDefinition.getCompanyId();
	}

	/**
	 * Returns the cp attachment file entry ID of this commerce bom definition.
	 *
	 * @return the cp attachment file entry ID of this commerce bom definition
	 */
	@Override
	public long getCPAttachmentFileEntryId() {
		return _commerceBOMDefinition.getCPAttachmentFileEntryId();
	}

	/**
	 * Returns the create date of this commerce bom definition.
	 *
	 * @return the create date of this commerce bom definition
	 */
	@Override
	public Date getCreateDate() {
		return _commerceBOMDefinition.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceBOMDefinition.getExpandoBridge();
	}

	/**
	 * Returns the friendly url of this commerce bom definition.
	 *
	 * @return the friendly url of this commerce bom definition
	 */
	@Override
	public String getFriendlyUrl() {
		return _commerceBOMDefinition.getFriendlyUrl();
	}

	/**
	 * Returns the modified date of this commerce bom definition.
	 *
	 * @return the modified date of this commerce bom definition
	 */
	@Override
	public Date getModifiedDate() {
		return _commerceBOMDefinition.getModifiedDate();
	}

	/**
	 * Returns the name of this commerce bom definition.
	 *
	 * @return the name of this commerce bom definition
	 */
	@Override
	public String getName() {
		return _commerceBOMDefinition.getName();
	}

	/**
	 * Returns the primary key of this commerce bom definition.
	 *
	 * @return the primary key of this commerce bom definition
	 */
	@Override
	public long getPrimaryKey() {
		return _commerceBOMDefinition.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceBOMDefinition.getPrimaryKeyObj();
	}

	/**
	 * Returns the user ID of this commerce bom definition.
	 *
	 * @return the user ID of this commerce bom definition
	 */
	@Override
	public long getUserId() {
		return _commerceBOMDefinition.getUserId();
	}

	/**
	 * Returns the user name of this commerce bom definition.
	 *
	 * @return the user name of this commerce bom definition
	 */
	@Override
	public String getUserName() {
		return _commerceBOMDefinition.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce bom definition.
	 *
	 * @return the user uuid of this commerce bom definition
	 */
	@Override
	public String getUserUuid() {
		return _commerceBOMDefinition.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _commerceBOMDefinition.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _commerceBOMDefinition.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceBOMDefinition.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commerceBOMDefinition.isNew();
	}

	@Override
	public void persist() {
		_commerceBOMDefinition.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceBOMDefinition.setCachedModel(cachedModel);
	}

	/**
	 * Sets the commerce bom definition ID of this commerce bom definition.
	 *
	 * @param commerceBOMDefinitionId the commerce bom definition ID of this commerce bom definition
	 */
	@Override
	public void setCommerceBOMDefinitionId(long commerceBOMDefinitionId) {
		_commerceBOMDefinition.setCommerceBOMDefinitionId(
			commerceBOMDefinitionId);
	}

	/**
	 * Sets the commerce bom folder ID of this commerce bom definition.
	 *
	 * @param commerceBOMFolderId the commerce bom folder ID of this commerce bom definition
	 */
	@Override
	public void setCommerceBOMFolderId(long commerceBOMFolderId) {
		_commerceBOMDefinition.setCommerceBOMFolderId(commerceBOMFolderId);
	}

	/**
	 * Sets the company ID of this commerce bom definition.
	 *
	 * @param companyId the company ID of this commerce bom definition
	 */
	@Override
	public void setCompanyId(long companyId) {
		_commerceBOMDefinition.setCompanyId(companyId);
	}

	/**
	 * Sets the cp attachment file entry ID of this commerce bom definition.
	 *
	 * @param CPAttachmentFileEntryId the cp attachment file entry ID of this commerce bom definition
	 */
	@Override
	public void setCPAttachmentFileEntryId(long CPAttachmentFileEntryId) {
		_commerceBOMDefinition.setCPAttachmentFileEntryId(
			CPAttachmentFileEntryId);
	}

	/**
	 * Sets the create date of this commerce bom definition.
	 *
	 * @param createDate the create date of this commerce bom definition
	 */
	@Override
	public void setCreateDate(Date createDate) {
		_commerceBOMDefinition.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_commerceBOMDefinition.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceBOMDefinition.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceBOMDefinition.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	 * Sets the friendly url of this commerce bom definition.
	 *
	 * @param friendlyUrl the friendly url of this commerce bom definition
	 */
	@Override
	public void setFriendlyUrl(String friendlyUrl) {
		_commerceBOMDefinition.setFriendlyUrl(friendlyUrl);
	}

	/**
	 * Sets the modified date of this commerce bom definition.
	 *
	 * @param modifiedDate the modified date of this commerce bom definition
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceBOMDefinition.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the name of this commerce bom definition.
	 *
	 * @param name the name of this commerce bom definition
	 */
	@Override
	public void setName(String name) {
		_commerceBOMDefinition.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_commerceBOMDefinition.setNew(n);
	}

	/**
	 * Sets the primary key of this commerce bom definition.
	 *
	 * @param primaryKey the primary key of this commerce bom definition
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceBOMDefinition.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceBOMDefinition.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	 * Sets the user ID of this commerce bom definition.
	 *
	 * @param userId the user ID of this commerce bom definition
	 */
	@Override
	public void setUserId(long userId) {
		_commerceBOMDefinition.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce bom definition.
	 *
	 * @param userName the user name of this commerce bom definition
	 */
	@Override
	public void setUserName(String userName) {
		_commerceBOMDefinition.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce bom definition.
	 *
	 * @param userUuid the user uuid of this commerce bom definition
	 */
	@Override
	public void setUserUuid(String userUuid) {
		_commerceBOMDefinition.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CommerceBOMDefinition>
		toCacheModel() {

		return _commerceBOMDefinition.toCacheModel();
	}

	@Override
	public CommerceBOMDefinition toEscapedModel() {
		return new CommerceBOMDefinitionWrapper(
			_commerceBOMDefinition.toEscapedModel());
	}

	@Override
	public String toString() {
		return _commerceBOMDefinition.toString();
	}

	@Override
	public CommerceBOMDefinition toUnescapedModel() {
		return new CommerceBOMDefinitionWrapper(
			_commerceBOMDefinition.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _commerceBOMDefinition.toXmlString();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceBOMDefinitionWrapper)) {
			return false;
		}

		CommerceBOMDefinitionWrapper commerceBOMDefinitionWrapper =
			(CommerceBOMDefinitionWrapper)object;

		if (Objects.equals(
				_commerceBOMDefinition,
				commerceBOMDefinitionWrapper._commerceBOMDefinition)) {

			return true;
		}

		return false;
	}

	@Override
	public CommerceBOMDefinition getWrappedModel() {
		return _commerceBOMDefinition;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceBOMDefinition.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceBOMDefinition.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceBOMDefinition.resetOriginalValues();
	}

	private final CommerceBOMDefinition _commerceBOMDefinition;

}