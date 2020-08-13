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

package com.liferay.portal.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.OrganizationServiceUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>OrganizationServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.portal.kernel.model.OrganizationSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.portal.kernel.model.Organization</code>, that is translated to a
 * <code>com.liferay.portal.kernel.model.OrganizationSoap</code>. Methods that SOAP
 * cannot safely wire are skipped.
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
 * @author Brian Wing Shun Chan
 * @see OrganizationServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class OrganizationServiceSoap {

	/**
	 * Adds the organizations to the group.
	 *
	 * @param groupId the primary key of the group
	 * @param organizationIds the primary keys of the organizations
	 */
	public static void addGroupOrganizations(
			long groupId, long[] organizationIds)
		throws RemoteException {

		try {
			OrganizationServiceUtil.addGroupOrganizations(
				groupId, organizationIds);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
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
	 */
	public static com.liferay.portal.kernel.model.OrganizationSoap
			addOrganization(
				long parentOrganizationId, String name, String type,
				long regionId, long countryId, long statusId, String comments,
				boolean site,
				com.liferay.portal.kernel.model.AddressSoap[] addresses,
				com.liferay.portal.kernel.model.EmailAddressSoap[]
					emailAddresses,
				com.liferay.portal.kernel.model.OrgLaborSoap[] orgLabors,
				com.liferay.portal.kernel.model.PhoneSoap[] phones,
				com.liferay.portal.kernel.model.WebsiteSoap[] websites,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.model.Organization returnValue =
				OrganizationServiceUtil.addOrganization(
					parentOrganizationId, name, type, regionId, countryId,
					statusId, comments, site,
					com.liferay.portal.model.impl.AddressModelImpl.toModels(
						addresses),
					com.liferay.portal.model.impl.EmailAddressModelImpl.
						toModels(emailAddresses),
					com.liferay.portal.model.impl.OrgLaborModelImpl.toModels(
						orgLabors),
					com.liferay.portal.model.impl.PhoneModelImpl.toModels(
						phones),
					com.liferay.portal.model.impl.WebsiteModelImpl.toModels(
						websites),
					serviceContext);

			return com.liferay.portal.kernel.model.OrganizationSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
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
	 */
	public static com.liferay.portal.kernel.model.OrganizationSoap
			addOrganization(
				long parentOrganizationId, String name, String type,
				long regionId, long countryId, long statusId, String comments,
				boolean site,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.model.Organization returnValue =
				OrganizationServiceUtil.addOrganization(
					parentOrganizationId, name, type, regionId, countryId,
					statusId, comments, site, serviceContext);

			return com.liferay.portal.kernel.model.OrganizationSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Assigns the password policy to the organizations, removing any other
	 * currently assigned password policies.
	 *
	 * @param passwordPolicyId the primary key of the password policy
	 * @param organizationIds the primary keys of the organizations
	 */
	public static void addPasswordPolicyOrganizations(
			long passwordPolicyId, long[] organizationIds)
		throws RemoteException {

		try {
			OrganizationServiceUtil.addPasswordPolicyOrganizations(
				passwordPolicyId, organizationIds);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Deletes the organization's logo.
	 *
	 * @param organizationId the primary key of the organization
	 */
	public static void deleteLogo(long organizationId) throws RemoteException {
		try {
			OrganizationServiceUtil.deleteLogo(organizationId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Deletes the organization. The organization's associated resources and
	 * assets are also deleted.
	 *
	 * @param organizationId the primary key of the organization
	 */
	public static void deleteOrganization(long organizationId)
		throws RemoteException {

		try {
			OrganizationServiceUtil.deleteOrganization(organizationId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns the organization with the primary key.
	 *
	 * @param organizationId the primary key of the organization
	 * @return the organization with the primary key, or <code>null</code> if an
	 organization with the primary key could not be found or if the
	 user did not have permission to view the organization
	 */
	public static com.liferay.portal.kernel.model.OrganizationSoap
			fetchOrganization(long organizationId)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.model.Organization returnValue =
				OrganizationServiceUtil.fetchOrganization(organizationId);

			return com.liferay.portal.kernel.model.OrganizationSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.portal.kernel.model.OrganizationSoap[]
			getGtOrganizations(
				long gtOrganizationId, long companyId,
				long parentOrganizationId, int size)
		throws RemoteException {

		try {
			java.util.List<com.liferay.portal.kernel.model.Organization>
				returnValue = OrganizationServiceUtil.getGtOrganizations(
					gtOrganizationId, companyId, parentOrganizationId, size);

			return com.liferay.portal.kernel.model.OrganizationSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns the organization with the primary key.
	 *
	 * @param organizationId the primary key of the organization
	 * @return the organization with the primary key
	 */
	public static com.liferay.portal.kernel.model.OrganizationSoap
			getOrganization(long organizationId)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.model.Organization returnValue =
				OrganizationServiceUtil.getOrganization(organizationId);

			return com.liferay.portal.kernel.model.OrganizationSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns the primary key of the organization with the name.
	 *
	 * @param companyId the primary key of the organization's company
	 * @param name the organization's name
	 * @return the primary key of the organization with the name, or
	 <code>0</code> if the organization could not be found
	 */
	public static long getOrganizationId(long companyId, String name)
		throws RemoteException {

		try {
			long returnValue = OrganizationServiceUtil.getOrganizationId(
				companyId, name);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns all the organizations belonging to the parent organization.
	 *
	 * @param companyId the primary key of the organizations' company
	 * @param parentOrganizationId the primary key of the organizations' parent
	 organization
	 * @return the organizations belonging to the parent organization
	 */
	public static com.liferay.portal.kernel.model.OrganizationSoap[]
			getOrganizations(long companyId, long parentOrganizationId)
		throws RemoteException {

		try {
			java.util.List<com.liferay.portal.kernel.model.Organization>
				returnValue = OrganizationServiceUtil.getOrganizations(
					companyId, parentOrganizationId);

			return com.liferay.portal.kernel.model.OrganizationSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
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
	 */
	public static com.liferay.portal.kernel.model.OrganizationSoap[]
			getOrganizations(
				long companyId, long parentOrganizationId, int start, int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.portal.kernel.model.Organization>
				returnValue = OrganizationServiceUtil.getOrganizations(
					companyId, parentOrganizationId, start, end);

			return com.liferay.portal.kernel.model.OrganizationSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.portal.kernel.model.OrganizationSoap[]
			getOrganizations(
				long companyId, long parentOrganizationId, String name,
				int start, int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.portal.kernel.model.Organization>
				returnValue = OrganizationServiceUtil.getOrganizations(
					companyId, parentOrganizationId, name, start, end);

			return com.liferay.portal.kernel.model.OrganizationSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns the number of organizations belonging to the parent organization.
	 *
	 * @param companyId the primary key of the organizations' company
	 * @param parentOrganizationId the primary key of the organizations' parent
	 organization
	 * @return the number of organizations belonging to the parent organization
	 */
	public static int getOrganizationsCount(
			long companyId, long parentOrganizationId)
		throws RemoteException {

		try {
			int returnValue = OrganizationServiceUtil.getOrganizationsCount(
				companyId, parentOrganizationId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getOrganizationsCount(
			long companyId, long parentOrganizationId, String name)
		throws RemoteException {

		try {
			int returnValue = OrganizationServiceUtil.getOrganizationsCount(
				companyId, parentOrganizationId, name);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns all the organizations with which the user is explicitly
	 * associated.
	 *
	 * <p>
	 * A user is considered to be <i>explicitly</i> associated with an
	 * organization if his account is individually created within the
	 * organization or if the user is later added as a member.
	 * </p>
	 *
	 * @param userId the primary key of the user
	 * @return the organizations with which the user is explicitly associated
	 */
	public static com.liferay.portal.kernel.model.OrganizationSoap[]
			getUserOrganizations(long userId)
		throws RemoteException {

		try {
			java.util.List<com.liferay.portal.kernel.model.Organization>
				returnValue = OrganizationServiceUtil.getUserOrganizations(
					userId);

			return com.liferay.portal.kernel.model.OrganizationSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Sets the organizations in the group, removing and adding organizations to
	 * the group as necessary.
	 *
	 * @param groupId the primary key of the group
	 * @param organizationIds the primary keys of the organizations
	 */
	public static void setGroupOrganizations(
			long groupId, long[] organizationIds)
		throws RemoteException {

		try {
			OrganizationServiceUtil.setGroupOrganizations(
				groupId, organizationIds);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Removes the organizations from the group.
	 *
	 * @param groupId the primary key of the group
	 * @param organizationIds the primary keys of the organizations
	 */
	public static void unsetGroupOrganizations(
			long groupId, long[] organizationIds)
		throws RemoteException {

		try {
			OrganizationServiceUtil.unsetGroupOrganizations(
				groupId, organizationIds);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Removes the organizations from the password policy.
	 *
	 * @param passwordPolicyId the primary key of the password policy
	 * @param organizationIds the primary keys of the organizations
	 */
	public static void unsetPasswordPolicyOrganizations(
			long passwordPolicyId, long[] organizationIds)
		throws RemoteException {

		try {
			OrganizationServiceUtil.unsetPasswordPolicyOrganizations(
				passwordPolicyId, organizationIds);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
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
	 * @param hasLogo if the organization has a custom logo
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
	 */
	public static com.liferay.portal.kernel.model.OrganizationSoap
			updateOrganization(
				long organizationId, long parentOrganizationId, String name,
				String type, long regionId, long countryId, long statusId,
				String comments, boolean hasLogo, byte[] logoBytes,
				boolean site,
				com.liferay.portal.kernel.model.AddressSoap[] addresses,
				com.liferay.portal.kernel.model.EmailAddressSoap[]
					emailAddresses,
				com.liferay.portal.kernel.model.OrgLaborSoap[] orgLabors,
				com.liferay.portal.kernel.model.PhoneSoap[] phones,
				com.liferay.portal.kernel.model.WebsiteSoap[] websites,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.model.Organization returnValue =
				OrganizationServiceUtil.updateOrganization(
					organizationId, parentOrganizationId, name, type, regionId,
					countryId, statusId, comments, hasLogo, logoBytes, site,
					com.liferay.portal.model.impl.AddressModelImpl.toModels(
						addresses),
					com.liferay.portal.model.impl.EmailAddressModelImpl.
						toModels(emailAddresses),
					com.liferay.portal.model.impl.OrgLaborModelImpl.toModels(
						orgLabors),
					com.liferay.portal.model.impl.PhoneModelImpl.toModels(
						phones),
					com.liferay.portal.model.impl.WebsiteModelImpl.toModels(
						websites),
					serviceContext);

			return com.liferay.portal.kernel.model.OrganizationSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
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
	 */
	public static com.liferay.portal.kernel.model.OrganizationSoap
			updateOrganization(
				long organizationId, long parentOrganizationId, String name,
				String type, long regionId, long countryId, long statusId,
				String comments, boolean site,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.model.Organization returnValue =
				OrganizationServiceUtil.updateOrganization(
					organizationId, parentOrganizationId, name, type, regionId,
					countryId, statusId, comments, site, serviceContext);

			return com.liferay.portal.kernel.model.OrganizationSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		OrganizationServiceSoap.class);

}