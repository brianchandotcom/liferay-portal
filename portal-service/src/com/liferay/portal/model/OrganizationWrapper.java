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

package com.liferay.portal.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Validator;

import com.liferay.portlet.exportimport.lar.StagedModelType;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link Organization}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see Organization
 * @generated
 */
@ProviderType
public class OrganizationWrapper implements Organization,
	ModelWrapper<Organization> {
	public OrganizationWrapper(Organization organization) {
		_organization = organization;
	}

	@Override
	public Class<?> getModelClass() {
		return Organization.class;
	}

	@Override
	public String getModelClassName() {
		return Organization.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("organizationId", getOrganizationId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("parentOrganizationId", getParentOrganizationId());
		attributes.put("treePath", getTreePath());
		attributes.put("name", getName());
		attributes.put("type", getType());
		attributes.put("recursable", getRecursable());
		attributes.put("regionId", getRegionId());
		attributes.put("countryId", getCountryId());
		attributes.put("statusId", getStatusId());
		attributes.put("comments", getComments());
		attributes.put("logoId", getLogoId());

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

		Long organizationId = (Long)attributes.get("organizationId");

		if (organizationId != null) {
			setOrganizationId(organizationId);
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

		Long parentOrganizationId = (Long)attributes.get("parentOrganizationId");

		if (parentOrganizationId != null) {
			setParentOrganizationId(parentOrganizationId);
		}

		String treePath = (String)attributes.get("treePath");

		if (treePath != null) {
			setTreePath(treePath);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String type = (String)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		Boolean recursable = (Boolean)attributes.get("recursable");

		if (recursable != null) {
			setRecursable(recursable);
		}

		Long regionId = (Long)attributes.get("regionId");

		if (regionId != null) {
			setRegionId(regionId);
		}

		Long countryId = (Long)attributes.get("countryId");

		if (countryId != null) {
			setCountryId(countryId);
		}

		Long statusId = (Long)attributes.get("statusId");

		if (statusId != null) {
			setStatusId(statusId);
		}

		String comments = (String)attributes.get("comments");

		if (comments != null) {
			setComments(comments);
		}

		Long logoId = (Long)attributes.get("logoId");

		if (logoId != null) {
			setLogoId(logoId);
		}
	}

	@Override
	public java.lang.String buildTreePath()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _organization.buildTreePath();
	}

	@Override
	public java.lang.Object clone() {
		return new OrganizationWrapper((Organization)_organization.clone());
	}

	@Override
	public int compareTo(com.liferay.portal.model.Organization organization) {
		return _organization.compareTo(organization);
	}

	@Override
	public com.liferay.portal.model.Address getAddress() {
		return _organization.getAddress();
	}

	@Override
	public java.util.List<com.liferay.portal.model.Address> getAddresses() {
		return _organization.getAddresses();
	}

	@Override
	public long[] getAncestorOrganizationIds()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _organization.getAncestorOrganizationIds();
	}

	@Override
	public java.util.List<com.liferay.portal.model.Organization> getAncestors()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _organization.getAncestors();
	}

	@Override
	public java.lang.String[] getChildrenTypes() {
		return _organization.getChildrenTypes();
	}

	/**
	* Returns the comments of this organization.
	*
	* @return the comments of this organization
	*/
	@Override
	public java.lang.String getComments() {
		return _organization.getComments();
	}

	/**
	* Returns the company ID of this organization.
	*
	* @return the company ID of this organization
	*/
	@Override
	public long getCompanyId() {
		return _organization.getCompanyId();
	}

	/**
	* Returns the country ID of this organization.
	*
	* @return the country ID of this organization
	*/
	@Override
	public long getCountryId() {
		return _organization.getCountryId();
	}

	/**
	* Returns the create date of this organization.
	*
	* @return the create date of this organization
	*/
	@Override
	public Date getCreateDate() {
		return _organization.getCreateDate();
	}

	@Override
	public java.util.List<com.liferay.portal.model.Organization> getDescendants() {
		return _organization.getDescendants();
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _organization.getExpandoBridge();
	}

	@Override
	public com.liferay.portal.model.Group getGroup() {
		return _organization.getGroup();
	}

	@Override
	public long getGroupId() {
		return _organization.getGroupId();
	}

	/**
	* Returns the logo ID of this organization.
	*
	* @return the logo ID of this organization
	*/
	@Override
	public long getLogoId() {
		return _organization.getLogoId();
	}

	/**
	* Returns the modified date of this organization.
	*
	* @return the modified date of this organization
	*/
	@Override
	public Date getModifiedDate() {
		return _organization.getModifiedDate();
	}

	/**
	* Returns the mvcc version of this organization.
	*
	* @return the mvcc version of this organization
	*/
	@Override
	public long getMvccVersion() {
		return _organization.getMvccVersion();
	}

	/**
	* Returns the name of this organization.
	*
	* @return the name of this organization
	*/
	@Override
	public java.lang.String getName() {
		return _organization.getName();
	}

	/**
	* Returns the organization ID of this organization.
	*
	* @return the organization ID of this organization
	*/
	@Override
	public long getOrganizationId() {
		return _organization.getOrganizationId();
	}

	@Override
	public com.liferay.portal.model.Organization getParentOrganization()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _organization.getParentOrganization();
	}

	/**
	* Returns the parent organization ID of this organization.
	*
	* @return the parent organization ID of this organization
	*/
	@Override
	public long getParentOrganizationId() {
		return _organization.getParentOrganizationId();
	}

	@Override
	public java.lang.String getParentOrganizationName() {
		return _organization.getParentOrganizationName();
	}

	@Override
	public javax.portlet.PortletPreferences getPreferences() {
		return _organization.getPreferences();
	}

	/**
	* Returns the primary key of this organization.
	*
	* @return the primary key of this organization
	*/
	@Override
	public long getPrimaryKey() {
		return _organization.getPrimaryKey();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _organization.getPrimaryKeyObj();
	}

	@Override
	public int getPrivateLayoutsPageCount() {
		return _organization.getPrivateLayoutsPageCount();
	}

	@Override
	public int getPublicLayoutsPageCount() {
		return _organization.getPublicLayoutsPageCount();
	}

	/**
	* Returns the recursable of this organization.
	*
	* @return the recursable of this organization
	*/
	@Override
	public boolean getRecursable() {
		return _organization.getRecursable();
	}

	/**
	* Returns the region ID of this organization.
	*
	* @return the region ID of this organization
	*/
	@Override
	public long getRegionId() {
		return _organization.getRegionId();
	}

	@Override
	public java.util.Set<java.lang.String> getReminderQueryQuestions(
		java.lang.String languageId) {
		return _organization.getReminderQueryQuestions(languageId);
	}

	@Override
	public java.util.Set<java.lang.String> getReminderQueryQuestions(
		java.util.Locale locale) {
		return _organization.getReminderQueryQuestions(locale);
	}

	/**
	* Returns the status ID of this organization.
	*
	* @return the status ID of this organization
	*/
	@Override
	public long getStatusId() {
		return _organization.getStatusId();
	}

	@Override
	public java.util.List<com.liferay.portal.model.Organization> getSuborganizations() {
		return _organization.getSuborganizations();
	}

	@Override
	public int getSuborganizationsSize() {
		return _organization.getSuborganizationsSize();
	}

	/**
	* Returns the tree path of this organization.
	*
	* @return the tree path of this organization
	*/
	@Override
	public java.lang.String getTreePath() {
		return _organization.getTreePath();
	}

	/**
	* Returns the type of this organization.
	*
	* @return the type of this organization
	*/
	@Override
	public java.lang.String getType() {
		return _organization.getType();
	}

	@Override
	public int getTypeOrder() {
		return _organization.getTypeOrder();
	}

	/**
	* Returns the user ID of this organization.
	*
	* @return the user ID of this organization
	*/
	@Override
	public long getUserId() {
		return _organization.getUserId();
	}

	/**
	* Returns the user name of this organization.
	*
	* @return the user name of this organization
	*/
	@Override
	public java.lang.String getUserName() {
		return _organization.getUserName();
	}

	/**
	* Returns the user uuid of this organization.
	*
	* @return the user uuid of this organization
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _organization.getUserUuid();
	}

	/**
	* Returns the uuid of this organization.
	*
	* @return the uuid of this organization
	*/
	@Override
	public java.lang.String getUuid() {
		return _organization.getUuid();
	}

	@Override
	public boolean hasPrivateLayouts() {
		return _organization.hasPrivateLayouts();
	}

	@Override
	public boolean hasPublicLayouts() {
		return _organization.hasPublicLayouts();
	}

	@Override
	public boolean hasSuborganizations() {
		return _organization.hasSuborganizations();
	}

	@Override
	public int hashCode() {
		return _organization.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _organization.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _organization.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _organization.isNew();
	}

	@Override
	public boolean isParentable() {
		return _organization.isParentable();
	}

	/**
	* Returns <code>true</code> if this organization is recursable.
	*
	* @return <code>true</code> if this organization is recursable; <code>false</code> otherwise
	*/
	@Override
	public boolean isRecursable() {
		return _organization.isRecursable();
	}

	@Override
	public boolean isRoot() {
		return _organization.isRoot();
	}

	@Override
	public void persist() {
		_organization.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_organization.setCachedModel(cachedModel);
	}

	/**
	* Sets the comments of this organization.
	*
	* @param comments the comments of this organization
	*/
	@Override
	public void setComments(java.lang.String comments) {
		_organization.setComments(comments);
	}

	/**
	* Sets the company ID of this organization.
	*
	* @param companyId the company ID of this organization
	*/
	@Override
	public void setCompanyId(long companyId) {
		_organization.setCompanyId(companyId);
	}

	/**
	* Sets the country ID of this organization.
	*
	* @param countryId the country ID of this organization
	*/
	@Override
	public void setCountryId(long countryId) {
		_organization.setCountryId(countryId);
	}

	/**
	* Sets the create date of this organization.
	*
	* @param createDate the create date of this organization
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_organization.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(BaseModel<?> baseModel) {
		_organization.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_organization.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_organization.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the logo ID of this organization.
	*
	* @param logoId the logo ID of this organization
	*/
	@Override
	public void setLogoId(long logoId) {
		_organization.setLogoId(logoId);
	}

	/**
	* Sets the modified date of this organization.
	*
	* @param modifiedDate the modified date of this organization
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_organization.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the mvcc version of this organization.
	*
	* @param mvccVersion the mvcc version of this organization
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		_organization.setMvccVersion(mvccVersion);
	}

	/**
	* Sets the name of this organization.
	*
	* @param name the name of this organization
	*/
	@Override
	public void setName(java.lang.String name) {
		_organization.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_organization.setNew(n);
	}

	/**
	* Sets the organization ID of this organization.
	*
	* @param organizationId the organization ID of this organization
	*/
	@Override
	public void setOrganizationId(long organizationId) {
		_organization.setOrganizationId(organizationId);
	}

	/**
	* Sets the parent organization ID of this organization.
	*
	* @param parentOrganizationId the parent organization ID of this organization
	*/
	@Override
	public void setParentOrganizationId(long parentOrganizationId) {
		_organization.setParentOrganizationId(parentOrganizationId);
	}

	/**
	* Sets the primary key of this organization.
	*
	* @param primaryKey the primary key of this organization
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_organization.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_organization.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets whether this organization is recursable.
	*
	* @param recursable the recursable of this organization
	*/
	@Override
	public void setRecursable(boolean recursable) {
		_organization.setRecursable(recursable);
	}

	/**
	* Sets the region ID of this organization.
	*
	* @param regionId the region ID of this organization
	*/
	@Override
	public void setRegionId(long regionId) {
		_organization.setRegionId(regionId);
	}

	/**
	* Sets the status ID of this organization.
	*
	* @param statusId the status ID of this organization
	*/
	@Override
	public void setStatusId(long statusId) {
		_organization.setStatusId(statusId);
	}

	/**
	* Sets the tree path of this organization.
	*
	* @param treePath the tree path of this organization
	*/
	@Override
	public void setTreePath(java.lang.String treePath) {
		_organization.setTreePath(treePath);
	}

	/**
	* Sets the type of this organization.
	*
	* @param type the type of this organization
	*/
	@Override
	public void setType(java.lang.String type) {
		_organization.setType(type);
	}

	/**
	* Sets the user ID of this organization.
	*
	* @param userId the user ID of this organization
	*/
	@Override
	public void setUserId(long userId) {
		_organization.setUserId(userId);
	}

	/**
	* Sets the user name of this organization.
	*
	* @param userName the user name of this organization
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_organization.setUserName(userName);
	}

	/**
	* Sets the user uuid of this organization.
	*
	* @param userUuid the user uuid of this organization
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_organization.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this organization.
	*
	* @param uuid the uuid of this organization
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_organization.setUuid(uuid);
	}

	@Override
	public CacheModel<com.liferay.portal.model.Organization> toCacheModel() {
		return _organization.toCacheModel();
	}

	@Override
	public com.liferay.portal.model.Organization toEscapedModel() {
		return new OrganizationWrapper(_organization.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _organization.toString();
	}

	@Override
	public com.liferay.portal.model.Organization toUnescapedModel() {
		return new OrganizationWrapper(_organization.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _organization.toXmlString();
	}

	@Override
	public void updateTreePath(java.lang.String treePath) {
		_organization.updateTreePath(treePath);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof OrganizationWrapper)) {
			return false;
		}

		OrganizationWrapper organizationWrapper = (OrganizationWrapper)obj;

		if (Validator.equals(_organization, organizationWrapper._organization)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _organization.getStagedModelType();
	}

	@Override
	public Organization getWrappedModel() {
		return _organization;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _organization.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _organization.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_organization.resetOriginalValues();
	}

	private final Organization _organization;
}