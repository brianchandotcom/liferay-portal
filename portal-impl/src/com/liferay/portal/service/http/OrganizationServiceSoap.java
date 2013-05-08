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

package com.liferay.portal.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.service.OrganizationServiceUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * {@link com.liferay.portal.service.OrganizationServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.portal.model.OrganizationSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.portal.model.Organization}, that is translated to a
 * {@link com.liferay.portal.model.OrganizationSoap}. Methods that SOAP cannot
 * safely wire are skipped.
 * </p>
 *
 * <p>
 * The benefits of using the SOAP utility is that it is cross platform
 * compatible. SOAP allows different languages like Java, .NET, C++, PHP, and
 * even Perl, to call the generated services. One drawback of SOAP is that it is
 * slow because it needs to serialize all calls into a text format (XML).
 * </p>
 *
 * <p>
 * You can see a list of services at http://localhost:8080/api/axis. Set the
 * property <b>axis.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       OrganizationServiceHttp
 * @see       com.liferay.portal.model.OrganizationSoap
 * @see       com.liferay.portal.service.OrganizationServiceUtil
 * @generated
 */
public class OrganizationServiceSoap {
	/**
	* Adds the organizations to the group.
	*
	* @param groupId the primary key of the group
	* @param organizationIds the primary keys of the organizations
	* @throws PortalException if a group or organization with the primary key
	could not be found or if the user did not have permission to
	assign group members
	* @throws SystemException if a system exception occurred
	*/
	public static void addGroupOrganizations(long groupId,
		long[] organizationIds) throws RemoteException {
		try {
			OrganizationServiceUtil.addGroupOrganizations(groupId,
				organizationIds);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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
	* @throws SystemException if a system exception occurred
	* @deprecated As of 6.2.0, replaced by {@link #addOrganization(long,
	String, String, long, long, int, String, boolean,
	java.util.List, java.util.List, java.util.List,
	java.util.List, java.util.List, ServiceContext)}
	*/
	public static com.liferay.portal.model.OrganizationSoap addOrganization(
		long parentOrganizationId, java.lang.String name,
		java.lang.String type, boolean recursable, long regionId,
		long countryId, int statusId, java.lang.String comments, boolean site,
		com.liferay.portal.model.AddressSoap[] addresses,
		com.liferay.portal.model.EmailAddressSoap[] emailAddresses,
		com.liferay.portal.model.OrgLaborSoap[] orgLabors,
		com.liferay.portal.model.PhoneSoap[] phones,
		com.liferay.portal.model.WebsiteSoap[] websites,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portal.model.Organization returnValue = OrganizationServiceUtil.addOrganization(parentOrganizationId,
					name, type, recursable, regionId, countryId, statusId,
					comments, site,
					com.liferay.portal.model.impl.AddressModelImpl.toModels(
						addresses),
					com.liferay.portal.model.impl.EmailAddressModelImpl.toModels(
						emailAddresses),
					com.liferay.portal.model.impl.OrgLaborModelImpl.toModels(
						orgLabors),
					com.liferay.portal.model.impl.PhoneModelImpl.toModels(
						phones),
					com.liferay.portal.model.impl.WebsiteModelImpl.toModels(
						websites), serviceContext);

			return com.liferay.portal.model.OrganizationSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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
	* @throws SystemException if a system exception occurred
	* @deprecated As of 6.2.0, replaced by {@link #addOrganization(long,
	String, String, long, long, int, String, boolean,
	ServiceContext)}
	*/
	public static com.liferay.portal.model.OrganizationSoap addOrganization(
		long parentOrganizationId, java.lang.String name,
		java.lang.String type, boolean recursable, long regionId,
		long countryId, int statusId, java.lang.String comments, boolean site,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portal.model.Organization returnValue = OrganizationServiceUtil.addOrganization(parentOrganizationId,
					name, type, recursable, regionId, countryId, statusId,
					comments, site, serviceContext);

			return com.liferay.portal.model.OrganizationSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.OrganizationSoap addOrganization(
		long parentOrganizationId, java.lang.String name,
		java.lang.String type, long regionId, long countryId, int statusId,
		java.lang.String comments, boolean site,
		com.liferay.portal.model.AddressSoap[] addresses,
		com.liferay.portal.model.EmailAddressSoap[] emailAddresses,
		com.liferay.portal.model.OrgLaborSoap[] orgLabors,
		com.liferay.portal.model.PhoneSoap[] phones,
		com.liferay.portal.model.WebsiteSoap[] websites,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portal.model.Organization returnValue = OrganizationServiceUtil.addOrganization(parentOrganizationId,
					name, type, regionId, countryId, statusId, comments, site,
					com.liferay.portal.model.impl.AddressModelImpl.toModels(
						addresses),
					com.liferay.portal.model.impl.EmailAddressModelImpl.toModels(
						emailAddresses),
					com.liferay.portal.model.impl.OrgLaborModelImpl.toModels(
						orgLabors),
					com.liferay.portal.model.impl.PhoneModelImpl.toModels(
						phones),
					com.liferay.portal.model.impl.WebsiteModelImpl.toModels(
						websites), serviceContext);

			return com.liferay.portal.model.OrganizationSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.OrganizationSoap addOrganization(
		long parentOrganizationId, java.lang.String name,
		java.lang.String type, long regionId, long countryId, int statusId,
		java.lang.String comments, boolean site,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portal.model.Organization returnValue = OrganizationServiceUtil.addOrganization(parentOrganizationId,
					name, type, regionId, countryId, statusId, comments, site,
					serviceContext);

			return com.liferay.portal.model.OrganizationSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Assigns the password policy to the organizations, removing any other
	* currently assigned password policies.
	*
	* @param passwordPolicyId the primary key of the password policy
	* @param organizationIds the primary keys of the organizations
	* @throws PortalException if the user did not have permission to update the
	password policy
	* @throws SystemException if a system exception occurred
	*/
	public static void addPasswordPolicyOrganizations(long passwordPolicyId,
		long[] organizationIds) throws RemoteException {
		try {
			OrganizationServiceUtil.addPasswordPolicyOrganizations(passwordPolicyId,
				organizationIds);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Deletes the logo of the organization.
	*
	* @param organizationId the primary key of the organization
	* @throws PortalException if an organization with the primary key could not
	be found, if the organization's logo could not be found, or if
	the user did not have permission to update the organization
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteLogo(long organizationId)
		throws RemoteException {
		try {
			OrganizationServiceUtil.deleteLogo(organizationId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Deletes the organization. The organization's associated resources and
	* assets are also deleted.
	*
	* @param organizationId the primary key of the organization
	* @throws PortalException if an organization with the primary key could not
	be found, if the user did not have permission to delete the
	organization, if the organization had a workflow in approved
	status, or if the organization was a parent organization
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteOrganization(long organizationId)
		throws RemoteException {
		try {
			OrganizationServiceUtil.deleteOrganization(organizationId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Returns all the organizations which the user has permission to manage.
	*
	* @param actionId the permitted action
	* @param max the maximum number of the organizations to be considered
	* @return the organizations which the user has permission to manage
	* @throws PortalException if a portal exception occurred
	* @throws SystemException if a system exception occurred
	* @deprecated As of 6.2.0, replaced by {@link #getOrganizations(long, long,
	int, int)}
	*/
	public static com.liferay.portal.model.OrganizationSoap[] getManageableOrganizations(
		java.lang.String actionId, int max) throws RemoteException {
		try {
			java.util.List<com.liferay.portal.model.Organization> returnValue = OrganizationServiceUtil.getManageableOrganizations(actionId,
					max);

			return com.liferay.portal.model.OrganizationSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Returns the organization with the primary key.
	*
	* @param organizationId the primary key of the organization
	* @return the organization with the primary key
	* @throws PortalException if an organization with the primary key could not
	be found or if the user did not have permission to view the
	organization
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.OrganizationSoap getOrganization(
		long organizationId) throws RemoteException {
		try {
			com.liferay.portal.model.Organization returnValue = OrganizationServiceUtil.getOrganization(organizationId);

			return com.liferay.portal.model.OrganizationSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Returns the primary key of the organization with the name.
	*
	* @param companyId the primary key of the organization's company
	* @param name the organization's name
	* @return the primary key of the organization with the name, or
	<code>0</code> if the organization could not be found
	* @throws PortalException if the user did not have permission to view the
	organization
	* @throws SystemException if a system exception occurred
	*/
	public static long getOrganizationId(long companyId, java.lang.String name)
		throws RemoteException {
		try {
			long returnValue = OrganizationServiceUtil.getOrganizationId(companyId,
					name);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Returns all the organizations belonging to the parent organization.
	*
	* @param companyId the primary key of the organizations' company
	* @param parentOrganizationId the primary key of the organizations' parent
	organization
	* @return the organizations belonging to the parent organization
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.OrganizationSoap[] getOrganizations(
		long companyId, long parentOrganizationId) throws RemoteException {
		try {
			java.util.List<com.liferay.portal.model.Organization> returnValue = OrganizationServiceUtil.getOrganizations(companyId,
					parentOrganizationId);

			return com.liferay.portal.model.OrganizationSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.OrganizationSoap[] getOrganizations(
		long companyId, long parentOrganizationId, int start, int end)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portal.model.Organization> returnValue = OrganizationServiceUtil.getOrganizations(companyId,
					parentOrganizationId, start, end);

			return com.liferay.portal.model.OrganizationSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Returns the number of organizations belonging to the parent organization.
	*
	* @param companyId the primary key of the organizations' company
	* @param parentOrganizationId the primary key of the organizations' parent
	organization
	* @return the number of organizations belonging to the parent organization
	* @throws SystemException if a system exception occurred
	*/
	public static int getOrganizationsCount(long companyId,
		long parentOrganizationId) throws RemoteException {
		try {
			int returnValue = OrganizationServiceUtil.getOrganizationsCount(companyId,
					parentOrganizationId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Returns all the organizations associated with the user.
	*
	* @param userId the primary key of the user
	* @return the organizations associated with the user
	* @throws PortalException if a user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.OrganizationSoap[] getUserOrganizations(
		long userId) throws RemoteException {
		try {
			java.util.List<com.liferay.portal.model.Organization> returnValue = OrganizationServiceUtil.getUserOrganizations(userId);

			return com.liferay.portal.model.OrganizationSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Sets the organizations in the group, removing and adding organizations to
	* the group as necessary.
	*
	* @param groupId the primary key of the group
	* @param organizationIds the primary keys of the organizations
	* @throws PortalException if a group or organization with the primary key
	could not be found or if the user did not have permission to
	assign group members
	* @throws SystemException if a system exception occurred
	*/
	public static void setGroupOrganizations(long groupId,
		long[] organizationIds) throws RemoteException {
		try {
			OrganizationServiceUtil.setGroupOrganizations(groupId,
				organizationIds);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Removes the organizations from the group.
	*
	* @param groupId the primary key of the group
	* @param organizationIds the primary keys of the organizations
	* @throws PortalException if a group or organization with the primary key
	could not be found or if the user did not have permission to
	assign group members
	* @throws SystemException if a system exception occurred
	*/
	public static void unsetGroupOrganizations(long groupId,
		long[] organizationIds) throws RemoteException {
		try {
			OrganizationServiceUtil.unsetGroupOrganizations(groupId,
				organizationIds);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Removes the organizations from the password policy.
	*
	* @param passwordPolicyId the primary key of the password policy
	* @param organizationIds the primary keys of the organizations
	* @throws PortalException if a password policy or organization with the
	primary key could not be found, or if the user did not have
	permission to update the password policy
	* @throws SystemException if a system exception occurred
	*/
	public static void unsetPasswordPolicyOrganizations(long passwordPolicyId,
		long[] organizationIds) throws RemoteException {
		try {
			OrganizationServiceUtil.unsetPasswordPolicyOrganizations(passwordPolicyId,
				organizationIds);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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
	* @throws SystemException if a system exception occurred
	* @deprecated As of 6.2.0, replaced by {@link #updateOrganization(long,
	long, String, String, long, long, int, String, boolean,
	java.util.List, java.util.List, java.util.List,
	java.util.List, java.util.List, ServiceContext)}
	*/
	public static com.liferay.portal.model.OrganizationSoap updateOrganization(
		long organizationId, long parentOrganizationId, java.lang.String name,
		java.lang.String type, boolean recursable, long regionId,
		long countryId, int statusId, java.lang.String comments, boolean site,
		com.liferay.portal.model.AddressSoap[] addresses,
		com.liferay.portal.model.EmailAddressSoap[] emailAddresses,
		com.liferay.portal.model.OrgLaborSoap[] orgLabors,
		com.liferay.portal.model.PhoneSoap[] phones,
		com.liferay.portal.model.WebsiteSoap[] websites,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portal.model.Organization returnValue = OrganizationServiceUtil.updateOrganization(organizationId,
					parentOrganizationId, name, type, recursable, regionId,
					countryId, statusId, comments, site,
					com.liferay.portal.model.impl.AddressModelImpl.toModels(
						addresses),
					com.liferay.portal.model.impl.EmailAddressModelImpl.toModels(
						emailAddresses),
					com.liferay.portal.model.impl.OrgLaborModelImpl.toModels(
						orgLabors),
					com.liferay.portal.model.impl.PhoneModelImpl.toModels(
						phones),
					com.liferay.portal.model.impl.WebsiteModelImpl.toModels(
						websites), serviceContext);

			return com.liferay.portal.model.OrganizationSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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
	* @throws SystemException if a system exception occurred
	* @deprecated As of 6.2.0, replaced by {@link #updateOrganization(long,
	long, String, String, long, long, int, String, boolean,
	ServiceContext)}
	*/
	public static com.liferay.portal.model.OrganizationSoap updateOrganization(
		long organizationId, long parentOrganizationId, java.lang.String name,
		java.lang.String type, boolean recursable, long regionId,
		long countryId, int statusId, java.lang.String comments, boolean site,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portal.model.Organization returnValue = OrganizationServiceUtil.updateOrganization(organizationId,
					parentOrganizationId, name, type, recursable, regionId,
					countryId, statusId, comments, site, serviceContext);

			return com.liferay.portal.model.OrganizationSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.OrganizationSoap updateOrganization(
		long organizationId, long parentOrganizationId, java.lang.String name,
		java.lang.String type, long regionId, long countryId, int statusId,
		java.lang.String comments, boolean site,
		com.liferay.portal.model.AddressSoap[] addresses,
		com.liferay.portal.model.EmailAddressSoap[] emailAddresses,
		com.liferay.portal.model.OrgLaborSoap[] orgLabors,
		com.liferay.portal.model.PhoneSoap[] phones,
		com.liferay.portal.model.WebsiteSoap[] websites,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portal.model.Organization returnValue = OrganizationServiceUtil.updateOrganization(organizationId,
					parentOrganizationId, name, type, regionId, countryId,
					statusId, comments, site,
					com.liferay.portal.model.impl.AddressModelImpl.toModels(
						addresses),
					com.liferay.portal.model.impl.EmailAddressModelImpl.toModels(
						emailAddresses),
					com.liferay.portal.model.impl.OrgLaborModelImpl.toModels(
						orgLabors),
					com.liferay.portal.model.impl.PhoneModelImpl.toModels(
						phones),
					com.liferay.portal.model.impl.WebsiteModelImpl.toModels(
						websites), serviceContext);

			return com.liferay.portal.model.OrganizationSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.OrganizationSoap updateOrganization(
		long organizationId, long parentOrganizationId, java.lang.String name,
		java.lang.String type, long regionId, long countryId, int statusId,
		java.lang.String comments, boolean site,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portal.model.Organization returnValue = OrganizationServiceUtil.updateOrganization(organizationId,
					parentOrganizationId, name, type, regionId, countryId,
					statusId, comments, site, serviceContext);

			return com.liferay.portal.model.OrganizationSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(OrganizationServiceSoap.class);
}