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

package com.liferay.portal.kernel.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

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
public class OrganizationWrapper
	extends BaseModelWrapper<Organization>
	implements ModelWrapper<Organization>, Organization {

	public OrganizationWrapper(Organization organization) {
		super(organization);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("externalReferenceCode", getExternalReferenceCode());
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
		attributes.put("recursable", isRecursable());
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

		String externalReferenceCode = (String)attributes.get(
			"externalReferenceCode");

		if (externalReferenceCode != null) {
			setExternalReferenceCode(externalReferenceCode);
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

		Long parentOrganizationId = (Long)attributes.get(
			"parentOrganizationId");

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
	public String buildTreePath()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.buildTreePath();
	}

	@Override
	public Address getAddress() {
		return model.getAddress();
	}

	@Override
	public java.util.List<Address> getAddresses() {
		return model.getAddresses();
	}

	@Override
	public long[] getAncestorOrganizationIds()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getAncestorOrganizationIds();
	}

	@Override
	public java.util.List<Organization> getAncestors()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getAncestors();
	}

	@Override
	public String[] getChildrenTypes() {
		return model.getChildrenTypes();
	}

	/**
	 * Returns the comments of this organization.
	 *
	 * @return the comments of this organization
	 */
	@Override
	public String getComments() {
		return model.getComments();
	}

	/**
	 * Returns the company ID of this organization.
	 *
	 * @return the company ID of this organization
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the country ID of this organization.
	 *
	 * @return the country ID of this organization
	 */
	@Override
	public long getCountryId() {
		return model.getCountryId();
	}

	/**
	 * Returns the create date of this organization.
	 *
	 * @return the create date of this organization
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	@Override
	public java.util.List<Organization> getDescendants() {
		return model.getDescendants();
	}

	/**
	 * Returns the external reference code of this organization.
	 *
	 * @return the external reference code of this organization
	 */
	@Override
	public String getExternalReferenceCode() {
		return model.getExternalReferenceCode();
	}

	@Override
	public Group getGroup() {
		return model.getGroup();
	}

	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the logo ID of this organization.
	 *
	 * @return the logo ID of this organization
	 */
	@Override
	public long getLogoId() {
		return model.getLogoId();
	}

	/**
	 * Returns the modified date of this organization.
	 *
	 * @return the modified date of this organization
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this organization.
	 *
	 * @return the mvcc version of this organization
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this organization.
	 *
	 * @return the name of this organization
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the organization ID of this organization.
	 *
	 * @return the organization ID of this organization
	 */
	@Override
	public long getOrganizationId() {
		return model.getOrganizationId();
	}

	@Override
	public Organization getParentOrganization()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getParentOrganization();
	}

	/**
	 * Returns the parent organization ID of this organization.
	 *
	 * @return the parent organization ID of this organization
	 */
	@Override
	public long getParentOrganizationId() {
		return model.getParentOrganizationId();
	}

	@Override
	public String getParentOrganizationName() {
		return model.getParentOrganizationName();
	}

	@Override
	public javax.portlet.PortletPreferences getPreferences() {
		return model.getPreferences();
	}

	/**
	 * Returns the primary key of this organization.
	 *
	 * @return the primary key of this organization
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	@Override
	public int getPrivateLayoutsPageCount() {
		return model.getPrivateLayoutsPageCount();
	}

	@Override
	public int getPublicLayoutsPageCount() {
		return model.getPublicLayoutsPageCount();
	}

	/**
	 * Returns the recursable of this organization.
	 *
	 * @return the recursable of this organization
	 */
	@Override
	public boolean getRecursable() {
		return model.getRecursable();
	}

	/**
	 * Returns the region ID of this organization.
	 *
	 * @return the region ID of this organization
	 */
	@Override
	public long getRegionId() {
		return model.getRegionId();
	}

	@Override
	public java.util.Set<String> getReminderQueryQuestions(
		java.util.Locale locale) {

		return model.getReminderQueryQuestions(locale);
	}

	@Override
	public java.util.Set<String> getReminderQueryQuestions(String languageId) {
		return model.getReminderQueryQuestions(languageId);
	}

	/**
	 * Returns the status ID of this organization.
	 *
	 * @return the status ID of this organization
	 */
	@Override
	public long getStatusId() {
		return model.getStatusId();
	}

	@Override
	public java.util.List<Organization> getSuborganizations() {
		return model.getSuborganizations();
	}

	@Override
	public int getSuborganizationsSize() {
		return model.getSuborganizationsSize();
	}

	/**
	 * Returns the tree path of this organization.
	 *
	 * @return the tree path of this organization
	 */
	@Override
	public String getTreePath() {
		return model.getTreePath();
	}

	/**
	 * Returns the type of this organization.
	 *
	 * @return the type of this organization
	 */
	@Override
	public String getType() {
		return model.getType();
	}

	@Override
	public int getTypeOrder() {
		return model.getTypeOrder();
	}

	/**
	 * Returns the user ID of this organization.
	 *
	 * @return the user ID of this organization
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this organization.
	 *
	 * @return the user name of this organization
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this organization.
	 *
	 * @return the user uuid of this organization
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this organization.
	 *
	 * @return the uuid of this organization
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	@Override
	public boolean hasPrivateLayouts() {
		return model.hasPrivateLayouts();
	}

	@Override
	public boolean hasPublicLayouts() {
		return model.hasPublicLayouts();
	}

	@Override
	public boolean hasSuborganizations() {
		return model.hasSuborganizations();
	}

	@Override
	public boolean isParentable() {
		return model.isParentable();
	}

	/**
	 * Returns <code>true</code> if this organization is recursable.
	 *
	 * @return <code>true</code> if this organization is recursable; <code>false</code> otherwise
	 */
	@Override
	public boolean isRecursable() {
		return model.isRecursable();
	}

	@Override
	public boolean isRoot() {
		return model.isRoot();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a organization model instance should use the <code>Organization</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the comments of this organization.
	 *
	 * @param comments the comments of this organization
	 */
	@Override
	public void setComments(String comments) {
		model.setComments(comments);
	}

	/**
	 * Sets the company ID of this organization.
	 *
	 * @param companyId the company ID of this organization
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the country ID of this organization.
	 *
	 * @param countryId the country ID of this organization
	 */
	@Override
	public void setCountryId(long countryId) {
		model.setCountryId(countryId);
	}

	/**
	 * Sets the create date of this organization.
	 *
	 * @param createDate the create date of this organization
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the external reference code of this organization.
	 *
	 * @param externalReferenceCode the external reference code of this organization
	 */
	@Override
	public void setExternalReferenceCode(String externalReferenceCode) {
		model.setExternalReferenceCode(externalReferenceCode);
	}

	/**
	 * Sets the logo ID of this organization.
	 *
	 * @param logoId the logo ID of this organization
	 */
	@Override
	public void setLogoId(long logoId) {
		model.setLogoId(logoId);
	}

	/**
	 * Sets the modified date of this organization.
	 *
	 * @param modifiedDate the modified date of this organization
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this organization.
	 *
	 * @param mvccVersion the mvcc version of this organization
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this organization.
	 *
	 * @param name the name of this organization
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the organization ID of this organization.
	 *
	 * @param organizationId the organization ID of this organization
	 */
	@Override
	public void setOrganizationId(long organizationId) {
		model.setOrganizationId(organizationId);
	}

	/**
	 * Sets the parent organization ID of this organization.
	 *
	 * @param parentOrganizationId the parent organization ID of this organization
	 */
	@Override
	public void setParentOrganizationId(long parentOrganizationId) {
		model.setParentOrganizationId(parentOrganizationId);
	}

	/**
	 * Sets the primary key of this organization.
	 *
	 * @param primaryKey the primary key of this organization
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets whether this organization is recursable.
	 *
	 * @param recursable the recursable of this organization
	 */
	@Override
	public void setRecursable(boolean recursable) {
		model.setRecursable(recursable);
	}

	/**
	 * Sets the region ID of this organization.
	 *
	 * @param regionId the region ID of this organization
	 */
	@Override
	public void setRegionId(long regionId) {
		model.setRegionId(regionId);
	}

	/**
	 * Sets the status ID of this organization.
	 *
	 * @param statusId the status ID of this organization
	 */
	@Override
	public void setStatusId(long statusId) {
		model.setStatusId(statusId);
	}

	/**
	 * Sets the tree path of this organization.
	 *
	 * @param treePath the tree path of this organization
	 */
	@Override
	public void setTreePath(String treePath) {
		model.setTreePath(treePath);
	}

	/**
	 * Sets the type of this organization.
	 *
	 * @param type the type of this organization
	 */
	@Override
	public void setType(String type) {
		model.setType(type);
	}

	/**
	 * Sets the user ID of this organization.
	 *
	 * @param userId the user ID of this organization
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this organization.
	 *
	 * @param userName the user name of this organization
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this organization.
	 *
	 * @param userUuid the user uuid of this organization
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this organization.
	 *
	 * @param uuid the uuid of this organization
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	public void updateTreePath(String treePath) {
		model.updateTreePath(treePath);
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected OrganizationWrapper wrap(Organization organization) {
		return new OrganizationWrapper(organization);
	}

}