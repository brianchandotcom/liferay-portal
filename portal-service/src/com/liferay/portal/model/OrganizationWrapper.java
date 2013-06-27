/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.Validator;

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

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
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

		Integer statusId = (Integer)attributes.get("statusId");

		if (statusId != null) {
			setStatusId(statusId);
		}

		String comments = (String)attributes.get("comments");

		if (comments != null) {
			setComments(comments);
		}
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

	/**
	* Sets the primary key of this organization.
	*
	* @param primaryKey the primary key of this organization
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_organization.setPrimaryKey(primaryKey);
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

	/**
	* Sets the uuid of this organization.
	*
	* @param uuid the uuid of this organization
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_organization.setUuid(uuid);
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
	* Returns the company ID of this organization.
	*
	* @return the company ID of this organization
	*/
	@Override
	public long getCompanyId() {
		return _organization.getCompanyId();
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
	* Returns the user ID of this organization.
	*
	* @return the user ID of this organization
	*/
	@Override
	public long getUserId() {
		return _organization.getUserId();
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
	* Returns the user uuid of this organization.
	*
	* @return the user uuid of this organization
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _organization.getUserUuid();
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
	* Returns the user name of this organization.
	*
	* @return the user name of this organization
	*/
	@Override
	public java.lang.String getUserName() {
		return _organization.getUserName();
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
	* Returns the create date of this organization.
	*
	* @return the create date of this organization
	*/
	@Override
	public java.util.Date getCreateDate() {
		return _organization.getCreateDate();
	}

	/**
	* Sets the create date of this organization.
	*
	* @param createDate the create date of this organization
	*/
	@Override
	public void setCreateDate(java.util.Date createDate) {
		_organization.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this organization.
	*
	* @return the modified date of this organization
	*/
	@Override
	public java.util.Date getModifiedDate() {
		return _organization.getModifiedDate();
	}

	/**
	* Sets the modified date of this organization.
	*
	* @param modifiedDate the modified date of this organization
	*/
	@Override
	public void setModifiedDate(java.util.Date modifiedDate) {
		_organization.setModifiedDate(modifiedDate);
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
	* Returns the tree path of this organization.
	*
	* @return the tree path of this organization
	*/
	@Override
	public java.lang.String getTreePath() {
		return _organization.getTreePath();
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
	* Returns the name of this organization.
	*
	* @return the name of this organization
	*/
	@Override
	public java.lang.String getName() {
		return _organization.getName();
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

	/**
	* Returns the type of this organization.
	*
	* @return the type of this organization
	*/
	@Override
	public java.lang.String getType() {
		return _organization.getType();
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
	* Returns the recursable of this organization.
	*
	* @return the recursable of this organization
	*/
	@Override
	public boolean getRecursable() {
		return _organization.getRecursable();
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
	* Returns the region ID of this organization.
	*
	* @return the region ID of this organization
	*/
	@Override
	public long getRegionId() {
		return _organization.getRegionId();
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
	* Returns the country ID of this organization.
	*
	* @return the country ID of this organization
	*/
	@Override
	public long getCountryId() {
		return _organization.getCountryId();
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
	* Returns the status ID of this organization.
	*
	* @return the status ID of this organization
	*/
	@Override
	public int getStatusId() {
		return _organization.getStatusId();
	}

	/**
	* Sets the status ID of this organization.
	*
	* @param statusId the status ID of this organization
	*/
	@Override
	public void setStatusId(int statusId) {
		_organization.setStatusId(statusId);
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
	* Sets the comments of this organization.
	*
	* @param comments the comments of this organization
	*/
	@Override
	public void setComments(java.lang.String comments) {
		_organization.setComments(comments);
	}

	@Override
	public boolean isNew() {
		return _organization.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_organization.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _organization.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_organization.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _organization.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _organization.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_organization.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _organization.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
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

	@Override
	public java.lang.Object clone() {
		return new OrganizationWrapper((Organization)_organization.clone());
	}

	@Override
	public int compareTo(com.liferay.portal.model.Organization organization) {
		return _organization.compareTo(organization);
	}

	@Override
	public int hashCode() {
		return _organization.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portal.model.Organization> toCacheModel() {
		return _organization.toCacheModel();
	}

	@Override
	public com.liferay.portal.model.Organization toEscapedModel() {
		return new OrganizationWrapper(_organization.toEscapedModel());
	}

	@Override
	public com.liferay.portal.model.Organization toUnescapedModel() {
		return new OrganizationWrapper(_organization.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _organization.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _organization.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_organization.persist();
	}

	@Override
	public java.lang.String buildTreePath()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _organization.buildTreePath();
	}

	@Override
	public com.liferay.portal.model.Address getAddress() {
		return _organization.getAddress();
	}

	@Override
	public java.util.List<com.liferay.portal.model.Address> getAddresses()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _organization.getAddresses();
	}

	@Override
	public java.util.List<com.liferay.portal.model.Organization> getAncestors()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _organization.getAncestors();
	}

	@Override
	public java.lang.String[] getChildrenTypes() {
		return _organization.getChildrenTypes();
	}

	@Override
	public java.util.List<com.liferay.portal.model.Organization> getDescendants()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _organization.getDescendants();
	}

	@Override
	public com.liferay.portal.model.Group getGroup() {
		return _organization.getGroup();
	}

	@Override
	public long getGroupId() {
		return _organization.getGroupId();
	}

	@Override
	public long getLogoId() {
		return _organization.getLogoId();
	}

	@Override
	public com.liferay.portal.model.Organization getParentOrganization()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _organization.getParentOrganization();
	}

	@Override
	public javax.portlet.PortletPreferences getPreferences()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _organization.getPreferences();
	}

	@Override
	public int getPrivateLayoutsPageCount() {
		return _organization.getPrivateLayoutsPageCount();
	}

	@Override
	public int getPublicLayoutsPageCount() {
		return _organization.getPublicLayoutsPageCount();
	}

	@Override
	public java.util.Set<java.lang.String> getReminderQueryQuestions(
		java.util.Locale locale)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _organization.getReminderQueryQuestions(locale);
	}

	@Override
	public java.util.Set<java.lang.String> getReminderQueryQuestions(
		java.lang.String languageId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _organization.getReminderQueryQuestions(languageId);
	}

	@Override
	public java.util.List<com.liferay.portal.model.Organization> getSuborganizations()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _organization.getSuborganizations();
	}

	@Override
	public int getSuborganizationsSize()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _organization.getSuborganizationsSize();
	}

	@Override
	public int getTypeOrder() {
		return _organization.getTypeOrder();
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
	public boolean hasSuborganizations()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _organization.hasSuborganizations();
	}

	@Override
	public boolean isParentable() {
		return _organization.isParentable();
	}

	@Override
	public boolean isRoot() {
		return _organization.isRoot();
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

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public Organization getWrappedOrganization() {
		return _organization;
	}

	@Override
	public Organization getWrappedModel() {
		return _organization;
	}

	@Override
	public void resetOriginalValues() {
		_organization.resetOriginalValues();
	}

	private Organization _organization;
}