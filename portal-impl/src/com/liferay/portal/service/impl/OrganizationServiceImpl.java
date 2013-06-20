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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.model.Address;
import com.liferay.portal.model.EmailAddress;
import com.liferay.portal.model.OrgLabor;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.OrganizationConstants;
import com.liferay.portal.model.Phone;
import com.liferay.portal.model.User;
import com.liferay.portal.model.Website;
import com.liferay.portal.security.membershippolicy.OrganizationMembershipPolicyUtil;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.base.OrganizationServiceBaseImpl;
import com.liferay.portal.service.permission.GroupPermissionUtil;
import com.liferay.portal.service.permission.OrganizationPermissionUtil;
import com.liferay.portal.service.permission.PasswordPolicyPermissionUtil;
import com.liferay.portal.service.permission.PortalPermissionUtil;
import com.liferay.portal.service.permission.UserPermissionUtil;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetTag;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.usersadmin.util.UsersAdminUtil;

import java.io.Serializable;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Provides the remote service for accessing, adding, deleting, and updating
 * organizations. Its methods include permission checks.
 *
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 * @author Julio Camarero
 */
public class OrganizationServiceImpl extends OrganizationServiceBaseImpl {

	/**
	 * Adds the organizations to the group.
	 *
	 * @param  groupId the primary key of the group
	 * @param  organizationIds the primary keys of the organizations
	 * @throws PortalException if a group or organization with the primary key
	 *         could not be found or if the user did not have permission to
	 *         assign group members
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void addGroupOrganizations(long groupId, long[] organizationIds)
		throws PortalException, SystemException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.ASSIGN_MEMBERS);

		organizationLocalService.addGroupOrganizations(
			groupId, organizationIds);
	}

	/**
	 * Adds an organization with additional parameters.
	 *
	 * <p>
	 * This method handles the creation and bookkeeping of the organization
	 * including its resources, metadata, and internal data structures.
	 * </p>
	 *
	 * @param      parentOrganizationId the primary key of the organization's
	 *             parent organization
	 * @param      name the organization's name
	 * @param      type the organization's type
	 * @param      recursable whether the permissions of the organization are to
	 *             be inherited by its suborganizations
	 * @param      regionId the primary key of the organization's region
	 * @param      countryId the primary key of the organization's country
	 * @param      statusId the organization's workflow status
	 * @param      comments the comments about the organization
	 * @param      site whether the organization is to be associated with a main
	 *             site
	 * @param      addresses the organization's addresses
	 * @param      emailAddresses the organization's email addresses
	 * @param      orgLabors the organization's hours of operation
	 * @param      phones the organization's phone numbers
	 * @param      websites the organization's websites
	 * @param      serviceContext the service context to be applied (optionally
	 *             <code>null</code>). Can set asset category IDs, asset tag
	 *             names, and expando bridge attributes for the organization.
	 * @return     the organization
	 * @throws     PortalException if a parent organization with the primary key
	 *             could not be found, if the organization's information was
	 *             invalid, or if the user did not have permission to add the
	 *             organization
	 * @throws     SystemException if a system exception occurred
	 * @deprecated As of 6.2.0, replaced by {@link #addOrganization(long,
	 *             String, String, long, long, int, String, boolean,
	 *             java.util.List, java.util.List, java.util.List,
	 *             java.util.List, java.util.List, ServiceContext)}
	 */
	@Override
	public Organization addOrganization(
			long parentOrganizationId, String name, String type,
			boolean recursable, long regionId, long countryId, int statusId,
			String comments, boolean site, List<Address> addresses,
			List<EmailAddress> emailAddresses, List<OrgLabor> orgLabors,
			List<Phone> phones, List<Website> websites,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		return addOrganization(
			parentOrganizationId, name, type, regionId, countryId, statusId,
			comments, site, addresses, emailAddresses, orgLabors, phones,
			websites, serviceContext);
	}

	/**
	 * Adds an organization.
	 *
	 * <p>
	 * This method handles the creation and bookkeeping of the organization
	 * including its resources, metadata, and internal data structures.
	 * </p>
	 *
	 * @param      parentOrganizationId the primary key of the organization's
	 *             parent organization
	 * @param      name the organization's name
	 * @param      type the organization's type
	 * @param      recursable whether the permissions of the organization are to
	 *             be inherited by its suborganizations
	 * @param      regionId the primary key of the organization's region
	 * @param      countryId the primary key of the organization's country
	 * @param      statusId the organization's workflow status
	 * @param      comments the comments about the organization
	 * @param      site whether the organization is to be associated with a main
	 *             site
	 * @param      serviceContext the service context to be applied (optionally
	 *             <code>null</code>). Can set asset category IDs, asset tag
	 *             names, and expando bridge attributes for the organization.
	 * @return     the organization
	 * @throws     PortalException if the parent organization with the primary
	 *             key could not be found, if the organization information was
	 *             invalid, or if the user did not have permission to add the
	 *             organization
	 * @throws     SystemException if a system exception occurred
	 * @deprecated As of 6.2.0, replaced by {@link #addOrganization(long,
	 *             String, String, long, long, int, String, boolean,
	 *             ServiceContext)}
	 */
	@Override
	public Organization addOrganization(
			long parentOrganizationId, String name, String type,
			boolean recursable, long regionId, long countryId, int statusId,
			String comments, boolean site, ServiceContext serviceContext)
		throws PortalException, SystemException {

		return addOrganization(
			parentOrganizationId, name, type, regionId, countryId, statusId,
			comments, site, serviceContext);
	}

	/**
	 * Adds an organization with additional parameters.
	 *
	 * <p>
	 * This method handles the creation and bookkeeping of the organization
	 * including its resources, metadata, and internal data structures.
	 * </p>
	 *
	 * @param  parentOrganizationId the primary key of the organization's parent
	 *         organization
	 * @param  name the organization's name
	 * @param  type the organization's type
	 * @param  regionId the primary key of the organization's region
	 * @param  countryId the primary key of the organization's country
	 * @param  statusId the organization's workflow status
	 * @param  comments the comments about the organization
	 * @param  site whether the organization is to be associated with a main
	 *         site
	 * @param  addresses the organization's addresses
	 * @param  emailAddresses the organization's email addresses
	 * @param  orgLabors the organization's hours of operation
	 * @param  phones the organization's phone numbers
	 * @param  websites the organization's websites
	 * @param  serviceContext the service context to be applied (optionally
	 *         <code>null</code>). Can set asset category IDs, asset tag names,
	 *         and expando bridge attributes for the organization.
	 * @return the organization
	 * @throws PortalException if a parent organization with the primary key
	 *         could not be found, if the organization's information was
	 *         invalid, or if the user did not have permission to add the
	 *         organization
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Organization addOrganization(
			long parentOrganizationId, String name, String type, long regionId,
			long countryId, int statusId, String comments, boolean site,
			List<Address> addresses, List<EmailAddress> emailAddresses,
			List<OrgLabor> orgLabors, List<Phone> phones,
			List<Website> websites, ServiceContext serviceContext)
		throws PortalException, SystemException {

		boolean indexingEnabled = serviceContext.isIndexingEnabled();

		serviceContext.setIndexingEnabled(false);

		try {
			Organization organization = addOrganization(
				parentOrganizationId, name, type, regionId, countryId, statusId,
				comments, site, serviceContext);

			UsersAdminUtil.updateAddresses(
				Organization.class.getName(), organization.getOrganizationId(),
				addresses);

			UsersAdminUtil.updateEmailAddresses(
				Organization.class.getName(), organization.getOrganizationId(),
				emailAddresses);

			UsersAdminUtil.updateOrgLabors(
				organization.getOrganizationId(), orgLabors);

			UsersAdminUtil.updatePhones(
				Organization.class.getName(), organization.getOrganizationId(),
				phones);

			UsersAdminUtil.updateWebsites(
				Organization.class.getName(), organization.getOrganizationId(),
				websites);

			if (indexingEnabled) {
				Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
					Organization.class);

				indexer.reindex(organization);
			}

			return organization;
		}
		finally {
			serviceContext.setIndexingEnabled(indexingEnabled);
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
	 * @param  parentOrganizationId the primary key of the organization's parent
	 *         organization
	 * @param  name the organization's name
	 * @param  type the organization's type
	 * @param  regionId the primary key of the organization's region
	 * @param  countryId the primary key of the organization's country
	 * @param  statusId the organization's workflow status
	 * @param  comments the comments about the organization
	 * @param  site whether the organization is to be associated with a main
	 *         site
	 * @param  serviceContext the service context to be applied (optionally
	 *         <code>null</code>). Can set asset category IDs, asset tag names,
	 *         and expando bridge attributes for the organization.
	 * @return the organization
	 * @throws PortalException if the parent organization with the primary key
	 *         could not be found, if the organization information was invalid,
	 *         or if the user did not have permission to add the organization
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Organization addOrganization(
			long parentOrganizationId, String name, String type, long regionId,
			long countryId, int statusId, String comments, boolean site,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		if (parentOrganizationId ==
				OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID) {

			PortalPermissionUtil.check(
				getPermissionChecker(), ActionKeys.ADD_ORGANIZATION);
		}
		else {
			OrganizationPermissionUtil.check(
				getPermissionChecker(), parentOrganizationId,
				ActionKeys.ADD_ORGANIZATION);
		}

		Organization organization = organizationLocalService.addOrganization(
			getUserId(), parentOrganizationId, name, type, regionId, countryId,
			statusId, comments, site, serviceContext);

		OrganizationMembershipPolicyUtil.verifyPolicy(organization);

		return organization;
	}

	/**
	 * Assigns the password policy to the organizations, removing any other
	 * currently assigned password policies.
	 *
	 * @param  passwordPolicyId the primary key of the password policy
	 * @param  organizationIds the primary keys of the organizations
	 * @throws PortalException if the user did not have permission to update the
	 *         password policy
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void addPasswordPolicyOrganizations(
			long passwordPolicyId, long[] organizationIds)
		throws PortalException, SystemException {

		PasswordPolicyPermissionUtil.check(
			getPermissionChecker(), passwordPolicyId, ActionKeys.UPDATE);

		organizationLocalService.addPasswordPolicyOrganizations(
			passwordPolicyId, organizationIds);
	}

	/**
	 * Deletes the logo of the organization.
	 *
	 * @param  organizationId the primary key of the organization
	 * @throws PortalException if an organization with the primary key could not
	 *         be found, if the organization's logo could not be found, or if
	 *         the user did not have permission to update the organization
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void deleteLogo(long organizationId)
		throws PortalException, SystemException {

		OrganizationPermissionUtil.check(
			getPermissionChecker(), organizationId, ActionKeys.UPDATE);

		organizationLocalService.deleteLogo(organizationId);
	}

	/**
	 * Deletes the organization. The organization's associated resources and
	 * assets are also deleted.
	 *
	 * @param  organizationId the primary key of the organization
	 * @throws PortalException if an organization with the primary key could not
	 *         be found, if the user did not have permission to delete the
	 *         organization, if the organization had a workflow in approved
	 *         status, or if the organization was a parent organization
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void deleteOrganization(long organizationId)
		throws PortalException, SystemException {

		OrganizationPermissionUtil.check(
			getPermissionChecker(), organizationId, ActionKeys.DELETE);

		organizationLocalService.deleteOrganization(organizationId);
	}

	/**
	 * Returns all the organizations which the user has permission to manage.
	 *
	 * @param      actionId the permitted action
	 * @param      max the maximum number of the organizations to be considered
	 * @return     the organizations which the user has permission to manage
	 * @throws     PortalException if a portal exception occurred
	 * @throws     SystemException if a system exception occurred
	 * @deprecated As of 6.2.0, replaced by {@link #getOrganizations(long, long,
	 *             int, int)}
	 */
	@Override
	public List<Organization> getManageableOrganizations(
			String actionId, int max)
		throws PortalException, SystemException {

		PermissionChecker permissionChecker = getPermissionChecker();

		if (permissionChecker.isCompanyAdmin()) {
			return organizationLocalService.search(
				permissionChecker.getCompanyId(),
				OrganizationConstants.ANY_PARENT_ORGANIZATION_ID, null, null,
				null, null, null, 0, max);
		}

		LinkedHashMap<String, Object> params =
			new LinkedHashMap<String, Object>();

		List<Organization> userOrganizations =
			organizationLocalService.getUserOrganizations(
				permissionChecker.getUserId());

		params.put("organizationsTree", userOrganizations);

		List<Organization> manageableOrganizations =
			organizationLocalService.search(
				permissionChecker.getCompanyId(),
				OrganizationConstants.ANY_PARENT_ORGANIZATION_ID, null, null,
				null, null, params, 0, max);

		manageableOrganizations = ListUtil.copy(manageableOrganizations);

		Iterator<Organization> itr = manageableOrganizations.iterator();

		while (itr.hasNext()) {
			Organization organization = itr.next();

			if (!OrganizationPermissionUtil.contains(
					permissionChecker, organization, actionId)) {

				itr.remove();
			}
		}

		return manageableOrganizations;
	}

	/**
	 * Returns the organization with the primary key.
	 *
	 * @param  organizationId the primary key of the organization
	 * @return the organization with the primary key
	 * @throws PortalException if an organization with the primary key could not
	 *         be found or if the user did not have permission to view the
	 *         organization
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Organization getOrganization(long organizationId)
		throws PortalException, SystemException {

		OrganizationPermissionUtil.check(
			getPermissionChecker(), organizationId, ActionKeys.VIEW);

		return organizationLocalService.getOrganization(organizationId);
	}

	/**
	 * Returns the primary key of the organization with the name.
	 *
	 * @param  companyId the primary key of the organization's company
	 * @param  name the organization's name
	 * @return the primary key of the organization with the name, or
	 *         <code>0</code> if the organization could not be found
	 * @throws PortalException if the user did not have permission to view the
	 *         organization
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public long getOrganizationId(long companyId, String name)
		throws PortalException, SystemException {

		long organizationId = organizationLocalService.getOrganizationId(
			companyId, name);

		OrganizationPermissionUtil.check(
			getPermissionChecker(), organizationId, ActionKeys.VIEW);

		return organizationId;
	}

	/**
	 * Returns all the organizations belonging to the parent organization.
	 *
	 * @param  companyId the primary key of the organizations' company
	 * @param  parentOrganizationId the primary key of the organizations' parent
	 *         organization
	 * @return the organizations belonging to the parent organization
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Organization> getOrganizations(
			long companyId, long parentOrganizationId)
		throws SystemException {

		return organizationPersistence.filterFindByC_P(
			companyId, parentOrganizationId);
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
	 * @param  companyId the primary key of the organizations' company
	 * @param  parentOrganizationId the primary key of the organizations' parent
	 *         organization
	 * @param  start the lower bound of the range of organizations to return
	 * @param  end the upper bound of the range of organizations to return (not
	 *         inclusive)
	 * @return the range of organizations belonging to the parent organization
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Organization> getOrganizations(
			long companyId, long parentOrganizationId, int start, int end)
		throws SystemException {

		return organizationPersistence.filterFindByC_P(
			companyId, parentOrganizationId, start, end);
	}

	/**
	 * Returns the number of organizations belonging to the parent organization.
	 *
	 * @param  companyId the primary key of the organizations' company
	 * @param  parentOrganizationId the primary key of the organizations' parent
	 *         organization
	 * @return the number of organizations belonging to the parent organization
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int getOrganizationsCount(long companyId, long parentOrganizationId)
		throws SystemException {

		return organizationPersistence.filterCountByC_P(
			companyId, parentOrganizationId);
	}

	/**
	 * Returns all the organizations associated with the user.
	 *
	 * @param  userId the primary key of the user
	 * @return the organizations associated with the user
	 * @throws PortalException if a user with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Organization> getUserOrganizations(long userId)
		throws PortalException, SystemException {

		UserPermissionUtil.check(
			getPermissionChecker(), userId, ActionKeys.VIEW);

		return organizationLocalService.getUserOrganizations(userId);
	}

	/**
	 * Sets the organizations in the group, removing and adding organizations to
	 * the group as necessary.
	 *
	 * @param  groupId the primary key of the group
	 * @param  organizationIds the primary keys of the organizations
	 * @throws PortalException if a group or organization with the primary key
	 *         could not be found or if the user did not have permission to
	 *         assign group members
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void setGroupOrganizations(long groupId, long[] organizationIds)
		throws PortalException, SystemException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.ASSIGN_MEMBERS);

		organizationLocalService.setGroupOrganizations(
			groupId, organizationIds);
	}

	/**
	 * Removes the organizations from the group.
	 *
	 * @param  groupId the primary key of the group
	 * @param  organizationIds the primary keys of the organizations
	 * @throws PortalException if a group or organization with the primary key
	 *         could not be found or if the user did not have permission to
	 *         assign group members
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void unsetGroupOrganizations(long groupId, long[] organizationIds)
		throws PortalException, SystemException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.ASSIGN_MEMBERS);

		organizationLocalService.unsetGroupOrganizations(
			groupId, organizationIds);
	}

	/**
	 * Removes the organizations from the password policy.
	 *
	 * @param  passwordPolicyId the primary key of the password policy
	 * @param  organizationIds the primary keys of the organizations
	 * @throws PortalException if a password policy or organization with the
	 *         primary key could not be found, or if the user did not have
	 *         permission to update the password policy
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void unsetPasswordPolicyOrganizations(
			long passwordPolicyId, long[] organizationIds)
		throws PortalException, SystemException {

		PasswordPolicyPermissionUtil.check(
			getPermissionChecker(), passwordPolicyId, ActionKeys.UPDATE);

		organizationLocalService.unsetPasswordPolicyOrganizations(
			passwordPolicyId, organizationIds);
	}

	/**
	 * Updates the organization with additional parameters.
	 *
	 * @param      organizationId the primary key of the organization
	 * @param      parentOrganizationId the primary key of the organization's
	 *             parent organization
	 * @param      name the organization's name
	 * @param      type the organization's type
	 * @param      recursable whether the permissions of the organization are to
	 *             be inherited by its suborganizations
	 * @param      regionId the primary key of the organization's region
	 * @param      countryId the primary key of the organization's country
	 * @param      statusId the organization's workflow status
	 * @param      comments the comments about the organization
	 * @param      site whether the organization is to be associated with a main
	 *             site
	 * @param      addresses the organization's addresses
	 * @param      emailAddresses the organization's email addresses
	 * @param      orgLabors the organization's hours of operation
	 * @param      phones the organization's phone numbers
	 * @param      websites the organization's websites
	 * @param      serviceContext the service context to be applied (optionally
	 *             <code>null</code>). Can set asset category IDs and asset tag
	 *             names for the organization, and merge expando bridge
	 *             attributes for the organization.
	 * @return     the organization
	 * @throws     PortalException if an organization or parent organization
	 *             with the primary key could not be found, if the user did not
	 *             have permission to update the organization information, or if
	 *             the new information was invalid
	 * @throws     SystemException if a system exception occurred
	 * @deprecated As of 6.2.0, replaced by {@link #updateOrganization(long,
	 *             long, String, String, long, long, int, String, boolean,
	 *             java.util.List, java.util.List, java.util.List,
	 *             java.util.List, java.util.List, ServiceContext)}
	 */
	@Override
	public Organization updateOrganization(
			long organizationId, long parentOrganizationId, String name,
			String type, boolean recursable, long regionId, long countryId,
			int statusId, String comments, boolean site,
			List<Address> addresses, List<EmailAddress> emailAddresses,
			List<OrgLabor> orgLabors, List<Phone> phones,
			List<Website> websites, ServiceContext serviceContext)
		throws PortalException, SystemException {

		return updateOrganization(
			organizationId, parentOrganizationId, name, type, regionId,
			countryId, statusId, comments, site, addresses, emailAddresses,
			orgLabors, phones, websites, serviceContext);
	}

	/**
	 * Updates the organization.
	 *
	 * @param      organizationId the primary key of the organization
	 * @param      parentOrganizationId the primary key of the organization's
	 *             parent organization
	 * @param      name the organization's name
	 * @param      type the organization's type
	 * @param      recursable whether permissions of the organization are to be
	 *             inherited by its suborganizations
	 * @param      regionId the primary key of the organization's region
	 * @param      countryId the primary key of the organization's country
	 * @param      statusId the organization's workflow status
	 * @param      comments the comments about the organization
	 * @param      site whether the organization is to be associated with a main
	 *             site
	 * @param      serviceContext the service context to be applied (optionally
	 *             <code>null</code>). Can set asset category IDs and asset tag
	 *             names for the organization, and merge expando bridge
	 *             attributes for the organization.
	 * @return     the organization
	 * @throws     PortalException if an organization or parent organization
	 *             with the primary key could not be found, if the user did not
	 *             have permission to update the organization, or if the new
	 *             information was invalid
	 * @throws     SystemException if a system exception occurred
	 * @deprecated As of 6.2.0, replaced by {@link #updateOrganization(long,
	 *             long, String, String, long, long, int, String, boolean,
	 *             ServiceContext)}
	 */
	@Override
	public Organization updateOrganization(
			long organizationId, long parentOrganizationId, String name,
			String type, boolean recursable, long regionId, long countryId,
			int statusId, String comments, boolean site,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		return updateOrganization(
			organizationId, parentOrganizationId, name, type, regionId,
			countryId, statusId, comments, site, serviceContext);
	}

	/**
	 * Updates the organization with additional parameters.
	 *
	 * @param  organizationId the primary key of the organization
	 * @param  parentOrganizationId the primary key of the organization's parent
	 *         organization
	 * @param  name the organization's name
	 * @param  type the organization's type
	 * @param  regionId the primary key of the organization's region
	 * @param  countryId the primary key of the organization's country
	 * @param  statusId the organization's workflow status
	 * @param  comments the comments about the organization
	 * @param  site whether the organization is to be associated with a main
	 *         site
	 * @param  addresses the organization's addresses
	 * @param  emailAddresses the organization's email addresses
	 * @param  orgLabors the organization's hours of operation
	 * @param  phones the organization's phone numbers
	 * @param  websites the organization's websites
	 * @param  serviceContext the service context to be applied (optionally
	 *         <code>null</code>). Can set asset category IDs and asset tag
	 *         names for the organization, and merge expando bridge attributes
	 *         for the organization.
	 * @return the organization
	 * @throws PortalException if an organization or parent organization with
	 *         the primary key could not be found, if the user did not have
	 *         permission to update the organization information, or if the new
	 *         information was invalid
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Organization updateOrganization(
			long organizationId, long parentOrganizationId, String name,
			String type, long regionId, long countryId, int statusId,
			String comments, boolean site, List<Address> addresses,
			List<EmailAddress> emailAddresses, List<OrgLabor> orgLabors,
			List<Phone> phones, List<Website> websites,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		Organization organization = organizationPersistence.findByPrimaryKey(
			organizationId);

		OrganizationPermissionUtil.check(
			getPermissionChecker(), organization, ActionKeys.UPDATE);

		if (organization.getParentOrganizationId() != parentOrganizationId) {
			if (parentOrganizationId ==
					OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID) {

				PortalPermissionUtil.check(
					getPermissionChecker(), ActionKeys.ADD_ORGANIZATION);
			}
			else {
				OrganizationPermissionUtil.check(
					getPermissionChecker(), parentOrganizationId,
					ActionKeys.ADD_ORGANIZATION);
			}
		}

		if (addresses != null) {
			UsersAdminUtil.updateAddresses(
				Organization.class.getName(), organizationId, addresses);
		}

		if (emailAddresses != null) {
			UsersAdminUtil.updateEmailAddresses(
				Organization.class.getName(), organizationId, emailAddresses);
		}

		if (orgLabors != null) {
			UsersAdminUtil.updateOrgLabors(organizationId, orgLabors);
		}

		if (phones != null) {
			UsersAdminUtil.updatePhones(
				Organization.class.getName(), organizationId, phones);
		}

		if (websites != null) {
			UsersAdminUtil.updateWebsites(
				Organization.class.getName(), organizationId, websites);
		}

		User user = getUser();

		Organization oldOrganization = organization;

		List<AssetCategory> oldAssetCategories =
			assetCategoryLocalService.getCategories(
				Organization.class.getName(), organizationId);

		List<AssetTag> oldAssetTags = assetTagLocalService.getTags(
			Organization.class.getName(), organizationId);

		ExpandoBridge oldExpandoBridge = oldOrganization.getExpandoBridge();

		Map<String, Serializable> oldExpandoAttributes =
			oldExpandoBridge.getAttributes();

		organization = organizationLocalService.updateOrganization(
			user.getCompanyId(), organizationId, parentOrganizationId, name,
			type, regionId, countryId, statusId, comments, site,
			serviceContext);

		OrganizationMembershipPolicyUtil.verifyPolicy(
			organization, oldOrganization, oldAssetCategories, oldAssetTags,
			oldExpandoAttributes);

		return organization;
	}

	/**
	 * Updates the organization.
	 *
	 * @param  organizationId the primary key of the organization
	 * @param  parentOrganizationId the primary key of the organization's parent
	 *         organization
	 * @param  name the organization's name
	 * @param  type the organization's type
	 * @param  regionId the primary key of the organization's region
	 * @param  countryId the primary key of the organization's country
	 * @param  statusId the organization's workflow status
	 * @param  comments the comments about the organization
	 * @param  site whether the organization is to be associated with a main
	 *         site
	 * @param  serviceContext the service context to be applied (optionally
	 *         <code>null</code>). Can set asset category IDs and asset tag
	 *         names for the organization, and merge expando bridge attributes
	 *         for the organization.
	 * @return the organization
	 * @throws PortalException if an organization or parent organization with
	 *         the primary key could not be found, if the user did not have
	 *         permission to update the organization, or if the new information
	 *         was invalid
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Organization updateOrganization(
			long organizationId, long parentOrganizationId, String name,
			String type, long regionId, long countryId, int statusId,
			String comments, boolean site, ServiceContext serviceContext)
		throws PortalException, SystemException {

		return updateOrganization(
			organizationId, parentOrganizationId, name, type, regionId,
			countryId, statusId, comments, site, null, null, null, null, null,
			serviceContext);
	}

}