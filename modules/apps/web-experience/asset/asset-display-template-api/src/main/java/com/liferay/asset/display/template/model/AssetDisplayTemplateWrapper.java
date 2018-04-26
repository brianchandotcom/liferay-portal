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

package com.liferay.asset.display.template.model;

import aQute.bnd.annotation.ProviderType;

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
 * This class is a wrapper for {@link AssetDisplayTemplate}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetDisplayTemplate
 * @generated
 */
@ProviderType
public class AssetDisplayTemplateWrapper implements AssetDisplayTemplate,
	ModelWrapper<AssetDisplayTemplate> {
	public AssetDisplayTemplateWrapper(
		AssetDisplayTemplate assetDisplayTemplate) {
		_assetDisplayTemplate = assetDisplayTemplate;
	}

	@Override
	public Class<?> getModelClass() {
		return AssetDisplayTemplate.class;
	}

	@Override
	public String getModelClassName() {
		return AssetDisplayTemplate.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("assetDisplayTemplateId", getAssetDisplayTemplateId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("name", getName());
		attributes.put("classNameId", getClassNameId());
		attributes.put("DDMTemplateId", getDDMTemplateId());
		attributes.put("main", isMain());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long assetDisplayTemplateId = (Long)attributes.get(
				"assetDisplayTemplateId");

		if (assetDisplayTemplateId != null) {
			setAssetDisplayTemplateId(assetDisplayTemplateId);
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

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long DDMTemplateId = (Long)attributes.get("DDMTemplateId");

		if (DDMTemplateId != null) {
			setDDMTemplateId(DDMTemplateId);
		}

		Boolean main = (Boolean)attributes.get("main");

		if (main != null) {
			setMain(main);
		}
	}

	@Override
	public Object clone() {
		return new AssetDisplayTemplateWrapper((AssetDisplayTemplate)_assetDisplayTemplate.clone());
	}

	@Override
	public int compareTo(AssetDisplayTemplate assetDisplayTemplate) {
		return _assetDisplayTemplate.compareTo(assetDisplayTemplate);
	}

	/**
	* Returns the asset display template ID of this asset display template.
	*
	* @return the asset display template ID of this asset display template
	*/
	@Override
	public long getAssetDisplayTemplateId() {
		return _assetDisplayTemplate.getAssetDisplayTemplateId();
	}

	@Override
	public String getAssetTypeName(java.util.Locale locale)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetDisplayTemplate.getAssetTypeName(locale);
	}

	/**
	* Returns the fully qualified class name of this asset display template.
	*
	* @return the fully qualified class name of this asset display template
	*/
	@Override
	public String getClassName() {
		return _assetDisplayTemplate.getClassName();
	}

	/**
	* Returns the class name ID of this asset display template.
	*
	* @return the class name ID of this asset display template
	*/
	@Override
	public long getClassNameId() {
		return _assetDisplayTemplate.getClassNameId();
	}

	/**
	* Returns the company ID of this asset display template.
	*
	* @return the company ID of this asset display template
	*/
	@Override
	public long getCompanyId() {
		return _assetDisplayTemplate.getCompanyId();
	}

	/**
	* Returns the create date of this asset display template.
	*
	* @return the create date of this asset display template
	*/
	@Override
	public Date getCreateDate() {
		return _assetDisplayTemplate.getCreateDate();
	}

	/**
	* Returns the ddm template ID of this asset display template.
	*
	* @return the ddm template ID of this asset display template
	*/
	@Override
	public long getDDMTemplateId() {
		return _assetDisplayTemplate.getDDMTemplateId();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _assetDisplayTemplate.getExpandoBridge();
	}

	/**
	* Returns the group ID of this asset display template.
	*
	* @return the group ID of this asset display template
	*/
	@Override
	public long getGroupId() {
		return _assetDisplayTemplate.getGroupId();
	}

	/**
	* Returns the main of this asset display template.
	*
	* @return the main of this asset display template
	*/
	@Override
	public boolean getMain() {
		return _assetDisplayTemplate.getMain();
	}

	/**
	* Returns the modified date of this asset display template.
	*
	* @return the modified date of this asset display template
	*/
	@Override
	public Date getModifiedDate() {
		return _assetDisplayTemplate.getModifiedDate();
	}

	/**
	* Returns the name of this asset display template.
	*
	* @return the name of this asset display template
	*/
	@Override
	public String getName() {
		return _assetDisplayTemplate.getName();
	}

	/**
	* Returns the primary key of this asset display template.
	*
	* @return the primary key of this asset display template
	*/
	@Override
	public long getPrimaryKey() {
		return _assetDisplayTemplate.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _assetDisplayTemplate.getPrimaryKeyObj();
	}

	/**
	* Returns the user ID of this asset display template.
	*
	* @return the user ID of this asset display template
	*/
	@Override
	public long getUserId() {
		return _assetDisplayTemplate.getUserId();
	}

	/**
	* Returns the user name of this asset display template.
	*
	* @return the user name of this asset display template
	*/
	@Override
	public String getUserName() {
		return _assetDisplayTemplate.getUserName();
	}

	/**
	* Returns the user uuid of this asset display template.
	*
	* @return the user uuid of this asset display template
	*/
	@Override
	public String getUserUuid() {
		return _assetDisplayTemplate.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _assetDisplayTemplate.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _assetDisplayTemplate.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _assetDisplayTemplate.isEscapedModel();
	}

	/**
	* Returns <code>true</code> if this asset display template is main.
	*
	* @return <code>true</code> if this asset display template is main; <code>false</code> otherwise
	*/
	@Override
	public boolean isMain() {
		return _assetDisplayTemplate.isMain();
	}

	@Override
	public boolean isNew() {
		return _assetDisplayTemplate.isNew();
	}

	@Override
	public void persist() {
		_assetDisplayTemplate.persist();
	}

	/**
	* Sets the asset display template ID of this asset display template.
	*
	* @param assetDisplayTemplateId the asset display template ID of this asset display template
	*/
	@Override
	public void setAssetDisplayTemplateId(long assetDisplayTemplateId) {
		_assetDisplayTemplate.setAssetDisplayTemplateId(assetDisplayTemplateId);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_assetDisplayTemplate.setCachedModel(cachedModel);
	}

	@Override
	public void setClassName(String className) {
		_assetDisplayTemplate.setClassName(className);
	}

	/**
	* Sets the class name ID of this asset display template.
	*
	* @param classNameId the class name ID of this asset display template
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_assetDisplayTemplate.setClassNameId(classNameId);
	}

	/**
	* Sets the company ID of this asset display template.
	*
	* @param companyId the company ID of this asset display template
	*/
	@Override
	public void setCompanyId(long companyId) {
		_assetDisplayTemplate.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this asset display template.
	*
	* @param createDate the create date of this asset display template
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_assetDisplayTemplate.setCreateDate(createDate);
	}

	/**
	* Sets the ddm template ID of this asset display template.
	*
	* @param DDMTemplateId the ddm template ID of this asset display template
	*/
	@Override
	public void setDDMTemplateId(long DDMTemplateId) {
		_assetDisplayTemplate.setDDMTemplateId(DDMTemplateId);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_assetDisplayTemplate.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_assetDisplayTemplate.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_assetDisplayTemplate.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this asset display template.
	*
	* @param groupId the group ID of this asset display template
	*/
	@Override
	public void setGroupId(long groupId) {
		_assetDisplayTemplate.setGroupId(groupId);
	}

	/**
	* Sets whether this asset display template is main.
	*
	* @param main the main of this asset display template
	*/
	@Override
	public void setMain(boolean main) {
		_assetDisplayTemplate.setMain(main);
	}

	/**
	* Sets the modified date of this asset display template.
	*
	* @param modifiedDate the modified date of this asset display template
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_assetDisplayTemplate.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this asset display template.
	*
	* @param name the name of this asset display template
	*/
	@Override
	public void setName(String name) {
		_assetDisplayTemplate.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_assetDisplayTemplate.setNew(n);
	}

	/**
	* Sets the primary key of this asset display template.
	*
	* @param primaryKey the primary key of this asset display template
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_assetDisplayTemplate.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_assetDisplayTemplate.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the user ID of this asset display template.
	*
	* @param userId the user ID of this asset display template
	*/
	@Override
	public void setUserId(long userId) {
		_assetDisplayTemplate.setUserId(userId);
	}

	/**
	* Sets the user name of this asset display template.
	*
	* @param userName the user name of this asset display template
	*/
	@Override
	public void setUserName(String userName) {
		_assetDisplayTemplate.setUserName(userName);
	}

	/**
	* Sets the user uuid of this asset display template.
	*
	* @param userUuid the user uuid of this asset display template
	*/
	@Override
	public void setUserUuid(String userUuid) {
		_assetDisplayTemplate.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<AssetDisplayTemplate> toCacheModel() {
		return _assetDisplayTemplate.toCacheModel();
	}

	@Override
	public AssetDisplayTemplate toEscapedModel() {
		return new AssetDisplayTemplateWrapper(_assetDisplayTemplate.toEscapedModel());
	}

	@Override
	public String toString() {
		return _assetDisplayTemplate.toString();
	}

	@Override
	public AssetDisplayTemplate toUnescapedModel() {
		return new AssetDisplayTemplateWrapper(_assetDisplayTemplate.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _assetDisplayTemplate.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AssetDisplayTemplateWrapper)) {
			return false;
		}

		AssetDisplayTemplateWrapper assetDisplayTemplateWrapper = (AssetDisplayTemplateWrapper)obj;

		if (Objects.equals(_assetDisplayTemplate,
					assetDisplayTemplateWrapper._assetDisplayTemplate)) {
			return true;
		}

		return false;
	}

	@Override
	public AssetDisplayTemplate getWrappedModel() {
		return _assetDisplayTemplate;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _assetDisplayTemplate.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _assetDisplayTemplate.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_assetDisplayTemplate.resetOriginalValues();
	}

	private final AssetDisplayTemplate _assetDisplayTemplate;
}