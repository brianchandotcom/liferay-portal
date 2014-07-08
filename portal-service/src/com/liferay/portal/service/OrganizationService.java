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

package com.liferay.portal.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.security.ac.AccessControlled;

/**
 * Provides the remote service interface for Organization. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see OrganizationServiceUtil
 * @see com.liferay.portal.service.base.OrganizationServiceBaseImpl
 * @see com.liferay.portal.service.impl.OrganizationServiceImpl
 * @generated
 */
@ProviderType
@AccessControlled
@JSONWebService
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface OrganizationService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link OrganizationServiceUtil} to access the organization remote service. Add custom service methods to {@link com.liferay.portal.service.impl.OrganizationServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier();

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier);

	/**
	* Adds the organizations to the group.
	*
	* @param groupId the primary key of the group
	* @param organizationIds the primary keys of the organizations
	* @throws PortalException if a group or organization with the primary key
	could not be found or if the user did not have permission to
	assign group members
	*/
	public void addGroupOrganizations(long groupId, long[] organizationIds)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Adds an organization with additional parameters.
	*
	* <p>
	* This method handles the creation and bookkeeping of the organization
	* including its resources, metadata, and internal data structures.
	* </p>
	*
	* @param parentOrganizationId the primary key of the organization's
	parent organization
	* @param name the organization's name
	* @param type the organization's type
	* @param recursable whether the permissions of the organization are to
	be inherited by its suborganizations
	* @param regionId the primary key of the organization's region
	* @param countryId the primary key of the organization's country
	* @param statusId the organization's workflow status
	* @param comments the comments about the organization
	* @param site whether the organization is to be associated with a main
	site
	* @param addresses the organization's addresses
	* @param emailAddresses the organization's email addresses
	* @param orgLabors the organization's hours of operation
	* @param phones the organization's phone numbers
	* @param websites the organization's websites
	* @param serviceContext the service context to be applied (optionally
	<code>null</code>). Can set asset category IDs, asset tag
	names, and expando bridge attributes for the organization.
	* @return the organization
	* @throws PortalException if a parent organization with the primary key
	could not be found, if the organization's information was
	invalid, or if the user did not have permission to add the
	organization
	* @deprecated As of 6.2.0, replaced by {@link #addOrganization(long,
	String, String, long, long, int, String, boolean,
	java.util.List, java.util.List, java.util.List,
	java.util.List, java.util.List, ServiceContext)}
	*/
	@Deprecated
	public com.liferay.portal.model.Organization addOrganization(
		long parentOrganizationId, java.lang.String name,
		java.lang.String type, boolean recursable, long regionId,
		long countryId, int statusId, java.lang.String comments, boolean site,
		java.util.List<com.liferay.portal.model.Address> addresses,
		java.util.List<com.liferay.portal.model.EmailAddress> emailAddresses,
		java.util.List<com.liferay.portal.model.OrgLabor> orgLabors,
		java.util.List<com.liferay.portal.model.Phone> phones,
		java.util.List<com.liferay.portal.model.Website> websites,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Adds an organization.
	*
	* <p>
	* This method handles the creation and bookkeeping of the organization
	* including its resources, metadata, and internal data structures.
	* </p>
	*
	* @param parentOrganizationId the primary key of the organization's
	parent organization
	* @param name the organization's name
	* @param type the organization's type
	* @param recursable whether the permissions of the organization are to
	be inherited by its suborganizations
	* @param regionId the primary key of the organization's region
	* @param countryId the primary key of the organization's country
	* @param statusId the organization's workflow status
	* @param comments the comments about the organization
	* @param site whether the organization is to be associated with a main
	site
	* @param serviceContext the service context to be applied (optionally
	<code>null</code>). Can set asset category IDs, asset tag
	names, and expando bridge attributes for the organization.
	* @return the organization
	* @throws PortalException if the parent organization with the primary
	key could not be found, if the organization information was
	invalid, or if the user did not have permission to add the
	organization
	* @deprecated As of 6.2.0, replaced by {@link #addOrganization(long,
	String, String, long, long, int, String, boolean,
	ServiceContext)}
	*/
	@Deprecated
	public com.liferay.portal.model.Organization addOrganization(
		long parentOrganizationId, java.lang.String name,
		java.lang.String type, boolean recursable, long regionId,
		long countryId, int statusId, java.lang.String comments, boolean site,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Adds an organization with additional parameters.
	*
	* <p>
	* This method handles the creation and bookkeeping of the organization
	* including its resources, metadata, and internal data structures.
	* </p>
	*
	* @param parentOrganizationId the primary key of the organization's parent
	organization
	* @param name the organization's name
	* @param type the organization's type
	* @param regionId the primary key of the organization's region
	* @param countryId the primary key of the organization's country
	* @param statusId the organization's workflow status
	* @param comments the comments about the organization
	* @param site whether the organization is to be associated with a main
	site
	* @param addresses the organization's addresses
	* @param emailAddresses the organization's email addresses
	* @param orgLabors the organization's hours of operation
	* @param phones the organization's phone numbers
	* @param websites the organization's websites
	* @param serviceContext the service context to be applied (optionally
	<code>null</code>). Can set asset category IDs, asset tag names,
	and expando bridge attributes for the organization.
	* @return the organization
	* @throws PortalException if a parent organization with the primary key
	could not be found, if the organization's information was
	invalid, or if the user did not have permission to add the
	organization
	*/
	public com.liferay.portal.model.Organization addOrganization(
		long parentOrganizationId, java.lang.String name,
		java.lang.String type, long regionId, long countryId, int statusId,
		java.lang.String comments, boolean site,
		java.util.List<com.liferay.portal.model.Address> addresses,
		java.util.List<com.liferay.portal.model.EmailAddress> emailAddresses,
		java.util.List<com.liferay.portal.model.OrgLabor> orgLabors,
		java.util.List<com.liferay.portal.model.Phone> phones,
		java.util.List<com.liferay.portal.model.Website> websites,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Adds an organization.
	*
	* <p>
	* This method handles the creation and bookkeeping of the organization
	* including its resources, metadata, and internal data structures.
	* </p>
	*
	* @param parentOrganizationId the primary key of the organization's parent
	organization
	* @param name the organization's name
	* @param type the organization's type
	* @param regionId the primary key of the organization's region
	* @param countryId the primary key of the organization's country
	* @param statusId the organization's workflow status
	* @param comments the comments about the organization
	* @param site whether the organization is to be associated with a main
	site
	* @param serviceContext the service context to be applied (optionally
	<code>null</code>). Can set asset category IDs, asset tag names,
	and expando bridge attributes for the organization.
	* @return the organization
	* @throws PortalException if the parent organization with the primary key
	could not be found, if the organization information was invalid,
	or if the user did not have permission to add the organization
	*/
	public com.liferay.portal.model.Organization addOrganization(
		long parentOrganizationId, java.lang.String name,
		java.lang.String type, long regionId, long countryId, int statusId,
		java.lang.String comments, boolean site,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Assigns the password policy to the organizations, removing any other
	* currently assigned password policies.
	*
	* @param passwordPolicyId the primary key of the password policy
	* @param organizationIds the primary keys of the organizations
	* @throws PortalException if the user did not have permission to update the
	password policy
	*/
	public void addPasswordPolicyOrganizations(long passwordPolicyId,
		long[] organizationIds)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Deletes the organization's logo.
	*
	* @param organizationId the primary key of the organization
	* @throws PortalException if an organization with the primary key could not
	be found, if the organization's logo could not be found, or if
	the user did not have permission to update the organization
	*/
	public void deleteLogo(long organizationId)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Deletes the organization. The organization's associated resources and
	* assets are also deleted.
	*
	* @param organizationId the primary key of the organization
	* @throws PortalException if an organization with the primary key could not
	be found, if the user did not have permission to delete the
	organization, if the organization had a workflow in approved
	status, or if the organization was a parent organization
	*/
	public void deleteOrganization(long organizationId)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Returns all the organizations which the user has permission to manage.
	*
	* @param actionId the permitted action
	* @param max the maximum number of the organizations to be considered
	* @return the organizations which the user has permission to manage
	* @throws PortalException if a portal exception occurred
	* @deprecated As of 6.2.0, replaced by {@link #getOrganizations(long, long,
	int, int)}
	*/
	@Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.Organization> getManageableOrganizations(
		java.lang.String actionId, int max)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Returns the organization with the primary key.
	*
	* @param organizationId the primary key of the organization
	* @return the organization with the primary key
	* @throws PortalException if an organization with the primary key could not
	be found or if the user did not have permission to view the
	organization
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.Organization getOrganization(
		long organizationId)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Returns the primary key of the organization with the name.
	*
	* @param companyId the primary key of the organization's company
	* @param name the organization's name
	* @return the primary key of the organization with the name, or
	<code>0</code> if the organization could not be found
	* @throws PortalException if the user did not have permission to view the
	organization
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long getOrganizationId(long companyId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Returns all the organizations belonging to the parent organization.
	*
	* @param companyId the primary key of the organizations' company
	* @param parentOrganizationId the primary key of the organizations' parent
	organization
	* @return the organizations belonging to the parent organization
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.Organization> getOrganizations(
		long companyId, long parentOrganizationId);

	/**
	* Returns a range of all the organizations belonging to the parent
	* organization.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end -
	* start</code> instances. <code>start</code> and <code>end</code> are not
	* primary keys, they are indexes in the result set. Thus, <code>0</code>
	* refers to the first result in the set. Setting both <code>start</code>
	* and <code>end</code> to {@link
	* com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param companyId the primary key of the organizations' company
	* @param parentOrganizationId the primary key of the organizations' parent
	organization
	* @param start the lower bound of the range of organizations to return
	* @param end the upper bound of the range of organizations to return (not
	inclusive)
	* @return the range of organizations belonging to the parent organization
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.Organization> getOrganizations(
		long companyId, long parentOrganizationId, int start, int end);

	/**
	* Returns the number of organizations belonging to the parent organization.
	*
	* @param companyId the primary key of the organizations' company
	* @param parentOrganizationId the primary key of the organizations' parent
	organization
	* @return the number of organizations belonging to the parent organization
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getOrganizationsCount(long companyId, long parentOrganizationId);

	/**
	* Returns all the organizations associated with the user.
	*
	* @param userId the primary key of the user
	* @return the organizations associated with the user
	* @throws PortalException if a user with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.Organization> getUserOrganizations(
		long userId) throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Sets the organizations in the group, removing and adding organizations to
	* the group as necessary.
	*
	* @param groupId the primary key of the group
	* @param organizationIds the primary keys of the organizations
	* @throws PortalException if a group or organization with the primary key
	could not be found or if the user did not have permission to
	assign group members
	*/
	public void setGroupOrganizations(long groupId, long[] organizationIds)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Removes the organizations from the group.
	*
	* @param groupId the primary key of the group
	* @param organizationIds the primary keys of the organizations
	* @throws PortalException if a group or organization with the primary key
	could not be found or if the user did not have permission to
	assign group members
	*/
	public void unsetGroupOrganizations(long groupId, long[] organizationIds)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Removes the organizations from the password policy.
	*
	* @param passwordPolicyId the primary key of the password policy
	* @param organizationIds the primary keys of the organizations
	* @throws PortalException if a password policy or organization with the
	primary key could not be found, or if the user did not have
	permission to update the password policy
	*/
	public void unsetPasswordPolicyOrganizations(long passwordPolicyId,
		long[] organizationIds)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Updates the organization with additional parameters.
	*
	* @param organizationId the primary key of the organization
	* @param parentOrganizationId the primary key of the organization's
	parent organization
	* @param name the organization's name
	* @param type the organization's type
	* @param recursable whether the permissions of the organization are to
	be inherited by its suborganizations
	* @param regionId the primary key of the organization's region
	* @param countryId the primary key of the organization's country
	* @param statusId the organization's workflow status
	* @param comments the comments about the organization
	* @param site whether the organization is to be associated with a main
	site
	* @param addresses the organization's addresses
	* @param emailAddresses the organization's email addresses
	* @param orgLabors the organization's hours of operation
	* @param phones the organization's phone numbers
	* @param websites the organization's websites
	* @param serviceContext the service context to be applied (optionally
	<code>null</code>). Can set asset category IDs and asset tag
	names for the organization, and merge expando bridge
	attributes for the organization.
	* @return the organization
	* @throws PortalException if an organization or parent organization
	with the primary key could not be found, if the user did not
	have permission to update the organization information, or if
	the new information was invalid
	* @deprecated As of 6.2.0, replaced by {@link #updateOrganization(long,
	long, String, String, long, long, int, String, boolean,
	java.util.List, java.util.List, java.util.List,
	java.util.List, java.util.List, boolean, byte[],
	ServiceContext)}
	*/
	@Deprecated
	public com.liferay.portal.model.Organization updateOrganization(
		long organizationId, long parentOrganizationId, java.lang.String name,
		java.lang.String type, boolean recursable, long regionId,
		long countryId, int statusId, java.lang.String comments, boolean site,
		java.util.List<com.liferay.portal.model.Address> addresses,
		java.util.List<com.liferay.portal.model.EmailAddress> emailAddresses,
		java.util.List<com.liferay.portal.model.OrgLabor> orgLabors,
		java.util.List<com.liferay.portal.model.Phone> phones,
		java.util.List<com.liferay.portal.model.Website> websites,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Updates the organization.
	*
	* @param organizationId the primary key of the organization
	* @param parentOrganizationId the primary key of the organization's
	parent organization
	* @param name the organization's name
	* @param type the organization's type
	* @param recursable whether permissions of the organization are to be
	inherited by its suborganizations
	* @param regionId the primary key of the organization's region
	* @param countryId the primary key of the organization's country
	* @param statusId the organization's workflow status
	* @param comments the comments about the organization
	* @param site whether the organization is to be associated with a main
	site
	* @param serviceContext the service context to be applied (optionally
	<code>null</code>). Can set asset category IDs and asset tag
	names for the organization, and merge expando bridge
	attributes for the organization.
	* @return the organization
	* @throws PortalException if an organization or parent organization
	with the primary key could not be found, if the user did not
	have permission to update the organization, or if the new
	information was invalid
	* @deprecated As of 6.2.0, replaced by {@link #updateOrganization(long,
	long, String, String, long, long, int, String, boolean,
	ServiceContext)}
	*/
	@Deprecated
	public com.liferay.portal.model.Organization updateOrganization(
		long organizationId, long parentOrganizationId, java.lang.String name,
		java.lang.String type, boolean recursable, long regionId,
		long countryId, int statusId, java.lang.String comments, boolean site,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Updates the organization with additional parameters.
	*
	* @param organizationId the primary key of the organization
	* @param parentOrganizationId the primary key of the organization's parent
	organization
	* @param name the organization's name
	* @param type the organization's type
	* @param regionId the primary key of the organization's region
	* @param countryId the primary key of the organization's country
	* @param statusId the organization's workflow status
	* @param comments the comments about the organization
	* @param logo whether to update the ogranization's logo
	* @param logoBytes the new logo image data
	* @param site whether the organization is to be associated with a main
	site
	* @param addresses the organization's addresses
	* @param emailAddresses the organization's email addresses
	* @param orgLabors the organization's hours of operation
	* @param phones the organization's phone numbers
	* @param websites the organization's websites
	* @param serviceContext the service context to be applied (optionally
	<code>null</code>). Can set asset category IDs and asset tag
	names for the organization, and merge expando bridge attributes
	for the organization.
	* @return the organization
	* @throws PortalException if an organization or parent organization with
	the primary key could not be found, if the user did not have
	permission to update the organization information, or if the new
	information was invalid
	*/
	public com.liferay.portal.model.Organization updateOrganization(
		long organizationId, long parentOrganizationId, java.lang.String name,
		java.lang.String type, long regionId, long countryId, int statusId,
		java.lang.String comments, boolean logo, byte[] logoBytes,
		boolean site,
		java.util.List<com.liferay.portal.model.Address> addresses,
		java.util.List<com.liferay.portal.model.EmailAddress> emailAddresses,
		java.util.List<com.liferay.portal.model.OrgLabor> orgLabors,
		java.util.List<com.liferay.portal.model.Phone> phones,
		java.util.List<com.liferay.portal.model.Website> websites,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Updates the organization with additional parameters.
	*
	* @param organizationId the primary key of the organization
	* @param parentOrganizationId the primary key of the organization's
	parent organization
	* @param name the organization's name
	* @param type the organization's type
	* @param regionId the primary key of the organization's region
	* @param countryId the primary key of the organization's country
	* @param statusId the organization's workflow status
	* @param comments the comments about the organization
	* @param site whether the organization is to be associated with a main
	site
	* @param addresses the organization's addresses
	* @param emailAddresses the organization's email addresses
	* @param orgLabors the organization's hours of operation
	* @param phones the organization's phone numbers
	* @param websites the organization's websites
	* @param serviceContext the service context to be applied (optionally
	<code>null</code>). Can set asset category IDs and asset tag
	names for the organization, and merge expando bridge
	attributes for the organization.
	* @return the organization
	* @throws PortalException if an organization or parent organization
	with the primary key could not be found, if the user did not
	have permission to update the organization information, or if
	the new information was invalid
	* @deprecated As of 7.0.0, replaced by {@link #updateOrganization(long,
	long, String, String, long, long, int, String, boolean,
	java.util.List, java.util.List, java.util.List,
	java.util.List, java.util.List, boolean, byte[],
	ServiceContext)}
	*/
	@Deprecated
	public com.liferay.portal.model.Organization updateOrganization(
		long organizationId, long parentOrganizationId, java.lang.String name,
		java.lang.String type, long regionId, long countryId, int statusId,
		java.lang.String comments, boolean site,
		java.util.List<com.liferay.portal.model.Address> addresses,
		java.util.List<com.liferay.portal.model.EmailAddress> emailAddresses,
		java.util.List<com.liferay.portal.model.OrgLabor> orgLabors,
		java.util.List<com.liferay.portal.model.Phone> phones,
		java.util.List<com.liferay.portal.model.Website> websites,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Updates the organization.
	*
	* @param organizationId the primary key of the organization
	* @param parentOrganizationId the primary key of the organization's parent
	organization
	* @param name the organization's name
	* @param type the organization's type
	* @param regionId the primary key of the organization's region
	* @param countryId the primary key of the organization's country
	* @param statusId the organization's workflow status
	* @param comments the comments about the organization
	* @param site whether the organization is to be associated with a main
	site
	* @param serviceContext the service context to be applied (optionally
	<code>null</code>). Can set asset category IDs and asset tag
	names for the organization, and merge expando bridge attributes
	for the organization.
	* @return the organization
	* @throws PortalException if an organization or parent organization with
	the primary key could not be found, if the user did not have
	permission to update the organization, or if the new information
	was invalid
	*/
	public com.liferay.portal.model.Organization updateOrganization(
		long organizationId, long parentOrganizationId, java.lang.String name,
		java.lang.String type, long regionId, long countryId, int statusId,
		java.lang.String comments, boolean site,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException;
}