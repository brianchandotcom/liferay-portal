/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link Organization}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       Organization
 * @generated
 */
public class OrganizationWrapper implements Organization,
	ModelWrapper<Organization> {
	public OrganizationWrapper(Organization organization) {
		_organization = organization;
	}

	public Class<?> getModelClass() {
		return Organization.class;
	}

	public String getModelClassName() {
		return Organization.class.getName();
	}

	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("organizationId", getOrganizationId());
		attributes.put("companyId", getCompanyId());
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

	public void setModelAttributes(Map<String, Object> attributes) {
		Long organizationId = (Long)attributes.get("organizationId");

		if (organizationId != null) {
			setOrganizationId(organizationId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
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
	public long getPrimaryKey() {
		return _organization.getPrimaryKey();
	}

	/**
	* Sets the primary key of this organization.
	*
	* @param primaryKey the primary key of this organization
	*/
	public void setPrimaryKey(long primaryKey) {
		_organization.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the organization ID of this organization.
	*
	* @return the organization ID of this organization
	*/
	public long getOrganizationId() {
		return _organization.getOrganizationId();
	}

	/**
	* Sets the organization ID of this organization.
	*
	* @param organizationId the organization ID of this organization
	*/
	public void setOrganizationId(long organizationId) {
		_organization.setOrganizationId(organizationId);
	}

	/**
	* Returns the company ID of this organization.
	*
	* @return the company ID of this organization
	*/
	public long getCompanyId() {
		return _organization.getCompanyId();
	}

	/**
	* Sets the company ID of this organization.
	*
	* @param companyId the company ID of this organization
	*/
	public void setCompanyId(long companyId) {
		_organization.setCompanyId(companyId);
	}

	/**
	* Returns the parent organization ID of this organization.
	*
	* @return the parent organization ID of this organization
	*/
	public long getParentOrganizationId() {
		return _organization.getParentOrganizationId();
	}

	/**
	* Sets the parent organization ID of this organization.
	*
	* @param parentOrganizationId the parent organization ID of this organization
	*/
	public void setParentOrganizationId(long parentOrganizationId) {
		_organization.setParentOrganizationId(parentOrganizationId);
	}

	/**
	* Returns the tree path of this organization.
	*
	* @return the tree path of this organization
	*/
	public java.lang.String getTreePath() {
		return _organization.getTreePath();
	}

	/**
	* Sets the tree path of this organization.
	*
	* @param treePath the tree path of this organization
	*/
	public void setTreePath(java.lang.String treePath) {
		_organization.setTreePath(treePath);
	}

	/**
	* Returns the name of this organization.
	*
	* @return the name of this organization
	*/
	public java.lang.String getName() {
		return _organization.getName();
	}

	/**
	* Sets the name of this organization.
	*
	* @param name the name of this organization
	*/
	public void setName(java.lang.String name) {
		_organization.setName(name);
	}

	/**
	* Returns the type of this organization.
	*
	* @return the type of this organization
	*/
	public java.lang.String getType() {
		return _organization.getType();
	}

	/**
	* Sets the type of this organization.
	*
	* @param type the type of this organization
	*/
	public void setType(java.lang.String type) {
		_organization.setType(type);
	}

	/**
	* Returns the recursable of this organization.
	*
	* @return the recursable of this organization
	*/
	public boolean getRecursable() {
		return _organization.getRecursable();
	}

	/**
	* Returns <code>true</code> if this organization is recursable.
	*
	* @return <code>true</code> if this organization is recursable; <code>false</code> otherwise
	*/
	public boolean isRecursable() {
		return _organization.isRecursable();
	}

	/**
	* Sets whether this organization is recursable.
	*
	* @param recursable the recursable of this organization
	*/
	public void setRecursable(boolean recursable) {
		_organization.setRecursable(recursable);
	}

	/**
	* Returns the region ID of this organization.
	*
	* @return the region ID of this organization
	*/
	public long getRegionId() {
		return _organization.getRegionId();
	}

	/**
	* Sets the region ID of this organization.
	*
	* @param regionId the region ID of this organization
	*/
	public void setRegionId(long regionId) {
		_organization.setRegionId(regionId);
	}

	/**
	* Returns the country ID of this organization.
	*
	* @return the country ID of this organization
	*/
	public long getCountryId() {
		return _organization.getCountryId();
	}

	/**
	* Sets the country ID of this organization.
	*
	* @param countryId the country ID of this organization
	*/
	public void setCountryId(long countryId) {
		_organization.setCountryId(countryId);
	}

	/**
	* Returns the status ID of this organization.
	*
	* @return the status ID of this organization
	*/
	public int getStatusId() {
		return _organization.getStatusId();
	}

	/**
	* Sets the status ID of this organization.
	*
	* @param statusId the status ID of this organization
	*/
	public void setStatusId(int statusId) {
		_organization.setStatusId(statusId);
	}

	/**
	* Returns the comments of this organization.
	*
	* @return the comments of this organization
	*/
	public java.lang.String getComments() {
		return _organization.getComments();
	}

	/**
	* Sets the comments of this organization.
	*
	* @param comments the comments of this organization
	*/
	public void setComments(java.lang.String comments) {
		_organization.setComments(comments);
	}

	public boolean isNew() {
		return _organization.isNew();
	}

	public void setNew(boolean n) {
		_organization.setNew(n);
	}

	public boolean isCachedModel() {
		return _organization.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_organization.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _organization.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _organization.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_organization.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _organization.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_organization.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new OrganizationWrapper((Organization)_organization.clone());
	}

	public int compareTo(com.liferay.portal.model.Organization organization) {
		return _organization.compareTo(organization);
	}

	@Override
	public int hashCode() {
		return _organization.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.portal.model.Organization> toCacheModel() {
		return _organization.toCacheModel();
	}

	public com.liferay.portal.model.Organization toEscapedModel() {
		return new OrganizationWrapper(_organization.toEscapedModel());
	}

	public com.liferay.portal.model.Organization toUnescapedModel() {
		return new OrganizationWrapper(_organization.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _organization.toString();
	}

	public java.lang.String toXmlString() {
		return _organization.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_organization.persist();
	}

	public java.lang.String buildTreePath()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _organization.buildTreePath();
	}

	public com.liferay.portal.model.Address getAddress() {
		return _organization.getAddress();
	}

	public java.util.List<com.liferay.portal.model.Address> getAddresses()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _organization.getAddresses();
	}

	public java.util.List<com.liferay.portal.model.Organization> getAncestors()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _organization.getAncestors();
	}

	public java.lang.String[] getChildrenTypes() {
		return _organization.getChildrenTypes();
	}

	public java.util.List<com.liferay.portal.model.Organization> getDescendants()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _organization.getDescendants();
	}

	public com.liferay.portal.model.Group getGroup() {
		return _organization.getGroup();
	}

	public long getGroupId() {
		return _organization.getGroupId();
	}

	public long getLogoId() {
		return _organization.getLogoId();
	}

	public com.liferay.portal.model.Organization getParentOrganization()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _organization.getParentOrganization();
	}

	public javax.portlet.PortletPreferences getPreferences()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _organization.getPreferences();
	}

	public int getPrivateLayoutsPageCount() {
		return _organization.getPrivateLayoutsPageCount();
	}

	public int getPublicLayoutsPageCount() {
		return _organization.getPublicLayoutsPageCount();
	}

	public java.util.Set<java.lang.String> getReminderQueryQuestions(
		java.util.Locale locale)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _organization.getReminderQueryQuestions(locale);
	}

	public java.util.Set<java.lang.String> getReminderQueryQuestions(
		java.lang.String languageId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _organization.getReminderQueryQuestions(languageId);
	}

	public java.util.List<com.liferay.portal.model.Organization> getSuborganizations()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _organization.getSuborganizations();
	}

	public int getSuborganizationsSize()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _organization.getSuborganizationsSize();
	}

	public int getTypeOrder() {
		return _organization.getTypeOrder();
	}

	public boolean hasPrivateLayouts() {
		return _organization.hasPrivateLayouts();
	}

	public boolean hasPublicLayouts() {
		return _organization.hasPublicLayouts();
	}

	public boolean hasSuborganizations()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _organization.hasSuborganizations();
	}

	public boolean isParentable() {
		return _organization.isParentable();
	}

	public boolean isRoot() {
		return _organization.isRoot();
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public Organization getWrappedOrganization() {
		return _organization;
	}

	public Organization getWrappedModel() {
		return _organization;
	}

	public void resetOriginalValues() {
		_organization.resetOriginalValues();
	}

	private Organization _organization;
}