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

package com.liferay.portal.service.impl;

import com.liferay.portal.DuplicateOrganizationException;
import com.liferay.portal.OrganizationNameException;
import com.liferay.portal.OrganizationParentException;
import com.liferay.portal.OrganizationTypeException;
import com.liferay.portal.RequiredOrganizationException;
import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TreeModelTasksAdapter;
import com.liferay.portal.kernel.util.TreePathUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Country;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.ListTypeConstants;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.OrganizationConstants;
import com.liferay.portal.model.Region;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.SystemEventConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.model.impl.OrganizationImpl;
import com.liferay.portal.security.permission.PermissionCacheUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.base.OrganizationLocalServiceBaseImpl;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.comparator.OrganizationIdComparator;
import com.liferay.portal.util.comparator.OrganizationNameComparator;
import com.liferay.portlet.usersadmin.util.UsersAdminUtil;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Provides the local service for accessing, adding, deleting, and updating
 * organizations.
 *
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 * @author Julio Camarero
 * @author Hugo Huijser
 * @author Juan Fernández
 */
public class OrganizationLocalServiceImpl
	extends OrganizationLocalServiceBaseImpl {

	/**
	 * Adds the organizations to the group.
	 *
	 * @param  groupId the primary key of the group
	 * @param  organizationIds the primary keys of the organizations
	 * @throws PortalException if a group or organization with the primary key
	 *         could not be found
	 */
	@Override
	public void addGroupOrganizations(long groupId, long[] organizationIds)
		throws PortalException {

		groupPersistence.addOrganizations(groupId, organizationIds);

		PermissionCacheUtil.clearCache();
	}

	/**
	 * Adds an organization.
	 *
	 * <p>
	 * This method handles the creation and bookkeeping of the organization
	 * including its resources, metadata, and internal data structures. It is
	 * not necessary to make a subsequent call to {@link
	 * #addOrganizationResources(long, Organization)}.
	 * </p>
	 *
	 * @param  userId the primary key of the creator/owner of the organization
	 * @param  parentOrganizationId the primary key of the organization's parent
	 *         organization
	 * @param  name the organization's name
	 * @param  site whether the organization is to be associated with a main
	 *         site
	 * @return the organization
	 * @throws PortalException if a creator or parent organization with the
	 *         primary key could not be found or if the organization's
	 *         information was invalid
	 */
	@Override
	public Organization addOrganization(
			long userId, long parentOrganizationId, String name, boolean site)
		throws PortalException {

		return addOrganization(
			userId, parentOrganizationId, name,
			OrganizationConstants.TYPE_REGULAR_ORGANIZATION, 0, 0,
			ListTypeConstants.ORGANIZATION_STATUS_DEFAULT, StringPool.BLANK,
			site, null);
	}

	/**
	 * Adds an organization.
	 *
	 * <p>
	 * This method handles the creation and bookkeeping of the organization
	 * including its resources, metadata, and internal data structures. It is
	 * not necessary to make a subsequent call to {@link
	 * #addOrganizationResources(long, Organization)}.
	 * </p>
	 *
	 * @param      userId the primary key of the creator/owner of the
	 *             organization
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
	 * @throws     PortalException if a creator or parent organization with the
	 *             primary key could not be found or if the organization's
	 *             information was invalid
	 * @deprecated As of 6.2.0, replaced by {@link #addOrganization(long, long,
	 *             String, String, long, long, int, String, boolean,
	 *             ServiceContext)}
	 */
	@Deprecated
	@Override
	public Organization addOrganization(
			long userId, long parentOrganizationId, String name, String type,
			boolean recursable, long regionId, long countryId, int statusId,
			String comments, boolean site, ServiceContext serviceContext)
		throws PortalException {

		return addOrganization(
			userId, parentOrganizationId, name, type, regionId, countryId,
			statusId, comments, site, serviceContext);
	}

	/**
	 * Adds an organization.
	 *
	 * <p>
	 * This method handles the creation and bookkeeping of the organization
	 * including its resources, metadata, and internal data structures. It is
	 * not necessary to make a subsequent call to {@link
	 * #addOrganizationResources(long, Organization)}.
	 * </p>
	 *
	 * @param  userId the primary key of the creator/owner of the organization
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
	 * @throws PortalException if a creator or parent organization with the
	 *         primary key could not be found or if the organization's
	 *         information was invalid
	 */
	@Override
	public Organization addOrganization(
			long userId, long parentOrganizationId, String name, String type,
			long regionId, long countryId, int statusId, String comments,
			boolean site, ServiceContext serviceContext)
		throws PortalException {

		// Organization

		User user = userPersistence.findByPrimaryKey(userId);
		parentOrganizationId = getParentOrganizationId(
			user.getCompanyId(), parentOrganizationId);
		Date now = new Date();

		validate(
			user.getCompanyId(), parentOrganizationId, name, type, countryId,
			statusId);

		long organizationId = counterLocalService.increment();

		Organization organization = organizationPersistence.create(
			organizationId);

		if (serviceContext != null) {
			organization.setUuid(serviceContext.getUuid());
		}

		organization.setCompanyId(user.getCompanyId());
		organization.setUserId(user.getUserId());
		organization.setUserName(user.getFullName());

		if (serviceContext != null) {
			organization.setCreateDate(serviceContext.getCreateDate(now));
			organization.setModifiedDate(serviceContext.getModifiedDate(now));
		}
		else {
			organization.setCreateDate(now);
			organization.setModifiedDate(now);
		}

		organization.setParentOrganizationId(parentOrganizationId);
		organization.setTreePath(organization.buildTreePath());
		organization.setName(name);
		organization.setType(type);
		organization.setRecursable(true);
		organization.setRegionId(regionId);
		organization.setCountryId(countryId);
		organization.setStatusId(statusId);
		organization.setComments(comments);
		organization.setExpandoBridgeAttributes(serviceContext);

		organizationPersistence.update(organization);

		// Group

		long parentGroupId = GroupConstants.DEFAULT_PARENT_GROUP_ID;

		if (parentOrganizationId !=
				OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID) {

			Organization parentOrganization =
				organizationPersistence.fetchByPrimaryKey(parentOrganizationId);

			if (parentOrganization != null) {
				Group parentGroup = parentOrganization.getGroup();

				if (site && parentGroup.isSite()) {
					parentGroupId = parentOrganization.getGroupId();
				}
			}
		}

		Group group = groupLocalService.addGroup(
			userId, parentGroupId, Organization.class.getName(), organizationId,
			GroupConstants.DEFAULT_LIVE_GROUP_ID, name, null,
			GroupConstants.TYPE_SITE_PRIVATE, false,
			GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION, null, site, true,
			null);

		// Role

		Role role = roleLocalService.getRole(
			organization.getCompanyId(), RoleConstants.ORGANIZATION_OWNER);

		userGroupRoleLocalService.addUserGroupRoles(
			userId, group.getGroupId(), new long[] {role.getRoleId()});

		// Resources

		addOrganizationResources(userId, organization);

		// Asset

		if (serviceContext != null) {
			updateAsset(
				userId, organization, serviceContext.getAssetCategoryIds(),
				serviceContext.getAssetTagNames());
		}

		// Indexer

		if ((serviceContext == null) || serviceContext.isIndexingEnabled()) {
			Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
				Organization.class);

			indexer.reindex(organization);
		}

		return organization;
	}

	/**
	 * Adds a resource for each type of permission available on the
	 * organization.
	 *
	 * @param  userId the primary key of the creator/owner of the organization
	 * @param  organization the organization
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public void addOrganizationResources(long userId, Organization organization)
		throws PortalException {

		String name = Organization.class.getName();

		resourceLocalService.addResources(
			organization.getCompanyId(), 0, userId, name,
			organization.getOrganizationId(), false, false, false);
	}

	/**
	 * Assigns the password policy to the organizations, removing any other
	 * currently assigned password policies.
	 *
	 * @param passwordPolicyId the primary key of the password policy
	 * @param organizationIds the primary keys of the organizations
	 */
	@Override
	public void addPasswordPolicyOrganizations(
		long passwordPolicyId, long[] organizationIds) {

		passwordPolicyRelLocalService.addPasswordPolicyRels(
			passwordPolicyId, Organization.class.getName(), organizationIds);
	}

	/**
	 * Deletes the organization's logo.
	 *
	 * @param  organizationId the primary key of the organization
	 * @throws PortalException if an organization or parent organization with
	 *         the primary key could not be found or if the organization's logo
	 *         could not be found
	 */
	@Override
	public void deleteLogo(long organizationId) throws PortalException {
		Organization organization = getOrganization(organizationId);

		PortalUtil.updateImageId(organization, false, null, "logoId", 0, 0, 0);
	}

	/**
	 * Deletes the organization. The organization's associated resources and
	 * assets are also deleted.
	 *
	 * @param  organizationId the primary key of the organization
	 * @return the deleted organization
	 * @throws PortalException if an organization with the primary key could not
	 *         be found, if the organization had a workflow in approved status,
	 *         or if the organization was a parent organization
	 */
	@Override
	public Organization deleteOrganization(long organizationId)
		throws PortalException {

		Organization organization = organizationPersistence.findByPrimaryKey(
			organizationId);

		return organizationLocalService.deleteOrganization(organization);
	}

	/**
	 * Deletes the organization. The organization's associated resources and
	 * assets are also deleted.
	 *
	 * @param  organization the organization
	 * @return the deleted organization
	 * @throws PortalException if the organization had a workflow in approved
	 *         status or if the organization was a parent organization
	 */
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public Organization deleteOrganization(Organization organization)
		throws PortalException {

		if ((userLocalService.getOrganizationUsersCount(
				organization.getOrganizationId(),
				WorkflowConstants.STATUS_APPROVED) > 0) ||
			(organizationPersistence.countByC_P(
				organization.getCompanyId(),
				organization.getOrganizationId()) > 0)) {

			throw new RequiredOrganizationException();
		}

		// Asset

		assetEntryLocalService.deleteEntry(
			Organization.class.getName(), organization.getOrganizationId());

		// Addresses

		addressLocalService.deleteAddresses(
			organization.getCompanyId(), Organization.class.getName(),
			organization.getOrganizationId());

		// Email addresses

		emailAddressLocalService.deleteEmailAddresses(
			organization.getCompanyId(), Organization.class.getName(),
			organization.getOrganizationId());

		// Expando

		expandoRowLocalService.deleteRows(organization.getOrganizationId());

		// Password policy relation

		passwordPolicyRelLocalService.deletePasswordPolicyRel(
			Organization.class.getName(), organization.getOrganizationId());

		// Phone

		phoneLocalService.deletePhones(
			organization.getCompanyId(), Organization.class.getName(),
			organization.getOrganizationId());

		// Website

		websiteLocalService.deleteWebsites(
			organization.getCompanyId(), Organization.class.getName(),
			organization.getOrganizationId());

		// Group

		Group group = organization.getGroup();

		if (group.isSite()) {
			group.setSite(false);

			groupPersistence.update(group);
		}

		groupLocalService.deleteGroup(group);

		// Resources

		String name = Organization.class.getName();

		resourceLocalService.deleteResource(
			organization.getCompanyId(), name,
			ResourceConstants.SCOPE_INDIVIDUAL,
			organization.getOrganizationId());

		// Organization

		organizationPersistence.remove(organization);

		// Permission cache

		PermissionCacheUtil.clearCache();

		return organization;
	}

	/**
	 * Returns the organization with the name.
	 *
	 * @param  companyId the primary key of the organization's company
	 * @param  name the organization's name
	 * @return the organization with the name, or <code>null</code> if no
	 *         organization could be found
	 */
	@Override
	public Organization fetchOrganization(long companyId, String name) {
		return organizationPersistence.fetchByC_N(companyId, name);
	}

	@Override
	public List<Organization> getGroupUserOrganizations(
			long groupId, long userId)
		throws PortalException {

		long[] groupOrganizationIds =
			groupPersistence.getOrganizationPrimaryKeys(groupId);

		if (groupOrganizationIds.length == 0) {
			return Collections.emptyList();
		}

		long[] userOrganizationIds = userPersistence.getOrganizationPrimaryKeys(
			userId);

		if (userOrganizationIds.length == 0) {
			return Collections.emptyList();
		}

		Set<Long> organizationIds = SetUtil.intersect(
			groupOrganizationIds, userOrganizationIds);

		if (organizationIds.isEmpty()) {
			return Collections.emptyList();
		}

		List<Organization> organizations = new ArrayList<Organization>(
			organizationIds.size());

		for (Long organizationId : organizationIds) {
			organizations.add(
				organizationPersistence.findByPrimaryKey(organizationId));
		}

		return organizations;
	}

	@Override
	public List<Organization> getNoAssetOrganizations() {
		return organizationFinder.findByNoAssets();
	}

	/**
	 * Returns the organization with the name.
	 *
	 * @param  companyId the primary key of the organization's company
	 * @param  name the organization's name
	 * @return the organization with the name
	 * @throws PortalException if the organization with the name could not be
	 *         found
	 */
	@Override
	public Organization getOrganization(long companyId, String name)
		throws PortalException {

		return organizationPersistence.findByC_N(companyId, name);
	}

	/**
	 * Returns the primary key of the organization with the name.
	 *
	 * @param  companyId the primary key of the organization's company
	 * @param  name the organization's name
	 * @return the primary key of the organization with the name, or
	 *         <code>0</code> if the organization could not be found
	 */
	@Override
	public long getOrganizationId(long companyId, String name) {
		Organization organization = organizationPersistence.fetchByC_N(
			companyId, name);

		if (organization != null) {
			return organization.getOrganizationId();
		}
		else {
			return 0;
		}
	}

	@Override
	public List<Organization> getOrganizations(
			long userId, int start, int end,
			OrderByComparator<Organization> obc)
		throws PortalException {

		User user = userPersistence.findByPrimaryKey(userId);

		List<Organization> organizations = ListUtil.copy(
			userPersistence.getOrganizations(userId));

		Iterator<Organization> iterator = organizations.iterator();

		while (iterator.hasNext()) {
			Organization organization = iterator.next();

			if (organization.getCompanyId() != user.getCompanyId()) {
				iterator.remove();
			}
		}

		if (organizations.isEmpty()) {
			return organizations;
		}

		if (obc == null) {
			obc = new OrganizationNameComparator(true);
		}

		Collections.sort(organizations, obc);

		return ListUtil.subList(organizations, start, end);
	}

	/**
	 * Returns all the organizations belonging to the parent organization.
	 *
	 * @param  companyId the primary key of the organization's company
	 * @param  parentOrganizationId the primary key of the organization's parent
	 *         organization
	 * @return the organizations belonging to the parent organization
	 */
	@Override
	public List<Organization> getOrganizations(
		long companyId, long parentOrganizationId) {

		return getOrganizations(
			companyId, parentOrganizationId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);
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
	 * @param  companyId the primary key of the organization's company
	 * @param  parentOrganizationId the primary key of the organization's parent
	 *         organization
	 * @param  start the lower bound of the range of organizations to return
	 * @param  end the upper bound of the range of organizations to return (not
	 *         inclusive)
	 * @return the range of organizations belonging to the parent organization
	 * @see    com.liferay.portal.service.persistence.OrganizationPersistence#findByC_P(
	 *         long, long, int, int)
	 */
	@Override
	public List<Organization> getOrganizations(
		long companyId, long parentOrganizationId, int start, int end) {

		if (parentOrganizationId ==
				OrganizationConstants.ANY_PARENT_ORGANIZATION_ID) {

			return organizationPersistence.findByCompanyId(
				companyId, start, end);
		}
		else {
			return organizationPersistence.findByC_P(
				companyId, parentOrganizationId, start, end);
		}
	}

	/**
	 * Returns the organizations with the primary keys.
	 *
	 * @param  organizationIds the primary keys of the organizations
	 * @return the organizations with the primary keys
	 * @throws PortalException if any one of the organizations could not be
	 *         found
	 */
	@Override
	public List<Organization> getOrganizations(long[] organizationIds)
		throws PortalException {

		List<Organization> organizations = new ArrayList<Organization>(
			organizationIds.length);

		for (long organizationId : organizationIds) {
			Organization organization = getOrganization(organizationId);

			organizations.add(organization);
		}

		return organizations;
	}

	/**
	 * Returns the number of organizations belonging to the parent organization.
	 *
	 * @param  companyId the primary key of the organization's company
	 * @param  parentOrganizationId the primary key of the organization's parent
	 *         organization
	 * @return the number of organizations belonging to the parent organization
	 */
	@Override
	public int getOrganizationsCount(
		long companyId, long parentOrganizationId) {

		if (parentOrganizationId ==
				OrganizationConstants.ANY_PARENT_ORGANIZATION_ID) {

			return organizationPersistence.countByCompanyId(companyId);
		}
		else {
			return organizationPersistence.countByC_P(
				companyId, parentOrganizationId);
		}
	}

	/**
	 * Returns the parent organizations in order by closest ancestor. The list
	 * starts with the organization itself.
	 *
	 * @param  organizationId the primary key of the organization
	 * @return the parent organizations in order by closest ancestor
	 * @throws PortalException if an organization with the primary key could not
	 *         be found
	 */
	@Override
	public List<Organization> getParentOrganizations(long organizationId)
		throws PortalException {

		if (organizationId ==
				OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID) {

			return new ArrayList<Organization>();
		}

		Organization organization = organizationPersistence.findByPrimaryKey(
			organizationId);

		return organization.getAncestors();
	}

	/**
	 * Returns the suborganizations of the organizations.
	 *
	 * @param  organizations the organizations from which to get
	 *         suborganizations
	 * @return the suborganizations of the organizations
	 */
	@Override
	public List<Organization> getSuborganizations(
		List<Organization> organizations) {

		List<Organization> allSuborganizations = new ArrayList<Organization>();

		for (int i = 0; i < organizations.size(); i++) {
			Organization organization = organizations.get(i);

			List<Organization> suborganizations =
				organizationPersistence.findByC_P(
					organization.getCompanyId(),
					organization.getOrganizationId());

			addSuborganizations(allSuborganizations, suborganizations);
		}

		return allSuborganizations;
	}

	/**
	 * Returns the suborganizations of the organization.
	 *
	 * @param  companyId the primary key of the organization's company
	 * @param  organizationId the primary key of the organization
	 * @return the suborganizations of the organization
	 */
	@Override
	public List<Organization> getSuborganizations(
		long companyId, long organizationId) {

		return organizationPersistence.findByC_P(companyId, organizationId);
	}

	/**
	 * Returns the count of suborganizations of the organization.
	 *
	 * @param  companyId the primary key of the organization's company
	 * @param  organizationId the primary key of the organization
	 * @return the count of suborganizations of the organization
	 */
	@Override
	public int getSuborganizationsCount(long companyId, long organizationId) {
		return organizationPersistence.countByC_P(companyId, organizationId);
	}

	/**
	 * Returns the intersection of <code>allOrganizations</code> and
	 * <code>availableOrganizations</code>.
	 *
	 * @param  allOrganizations the organizations to check for availability
	 * @param  availableOrganizations the available organizations
	 * @return the intersection of <code>allOrganizations</code> and
	 *         <code>availableOrganizations</code>
	 */
	@Override
	public List<Organization> getSubsetOrganizations(
		List<Organization> allOrganizations,
		List<Organization> availableOrganizations) {

		List<Organization> subsetOrganizations = new ArrayList<Organization>();

		for (Organization organization : allOrganizations) {
			if (availableOrganizations.contains(organization)) {
				subsetOrganizations.add(organization);
			}
		}

		return subsetOrganizations;
	}

	/**
	 * Returns all the organization IDs associated with the user. If
	 * <code>includeAdministrative</code> is <code>true</code>, the result
	 * includes those organization IDs that are indirectly associated to the
	 * user because he is an administrator or owner of the organization.
	 *
	 * @param  userId the primary key of the user
	 * @param  includeAdministrative whether to include organizations that are
	 *         indirectly associated to the user because he is an administrator
	 *         or owner of the organization
	 * @return the organization IDs of organizations associated with the user
	 * @throws PortalException if a user with the primary key could not be found
	 *         or if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public long[] getUserOrganizationIds(
			long userId, boolean includeAdministrative)
		throws PortalException {

		if (!includeAdministrative) {
			return userPersistence.getOrganizationPrimaryKeys(userId);
		}

		Set<Long> organizationIds = SetUtil.fromArray(
			userPersistence.getOrganizationPrimaryKeys(userId));

		List<UserGroupRole> userGroupRoles =
			userGroupRoleLocalService.getUserGroupRoles(userId);

		for (UserGroupRole userGroupRole : userGroupRoles) {
			Role role = userGroupRole.getRole();

			String roleName = role.getName();

			if (roleName.equals(RoleConstants.ORGANIZATION_ADMINISTRATOR) ||
				roleName.equals(RoleConstants.ORGANIZATION_OWNER)) {

				Group group = userGroupRole.getGroup();

				organizationIds.add(group.getOrganizationId());
			}
		}

		return ArrayUtil.toLongArray(organizationIds);
	}

	/**
	 * Returns all the organizations associated with the user. If
	 * <code>includeAdministrative</code> is <code>true</code>, the result
	 * includes those organizations that are indirectly associated to the user
	 * because he is an administrator or owner of the organization.
	 *
	 * @param  userId the primary key of the user
	 * @param  includeAdministrative whether to include organizations that are
	 *         indirectly associated to the user because he is an administrator
	 *         or owner of the organization
	 * @return the organizations associated with the user
	 * @throws PortalException if a user with the primary key could not be found
	 */
	@Override
	public List<Organization> getUserOrganizations(
			long userId, boolean includeAdministrative)
		throws PortalException {

		if (!includeAdministrative) {
			return getUserOrganizations(userId);
		}

		Set<Organization> organizations = new HashSet<Organization>(
			getUserOrganizations(userId));

		List<UserGroupRole> userGroupRoles =
			userGroupRoleLocalService.getUserGroupRoles(userId);

		for (UserGroupRole userGroupRole : userGroupRoles) {
			Role role = userGroupRole.getRole();

			String roleName = role.getName();

			if (roleName.equals(RoleConstants.ORGANIZATION_ADMINISTRATOR) ||
				roleName.equals(RoleConstants.ORGANIZATION_OWNER)) {

				Group group = userGroupRole.getGroup();

				Organization organization =
					organizationPersistence.findByPrimaryKey(
						group.getOrganizationId());

				organizations.add(organization);
			}
		}

		return new ArrayList<Organization>(organizations);
	}

	/**
	 * Returns <code>true</code> if the password policy has been assigned to the
	 * organization.
	 *
	 * @param  passwordPolicyId the primary key of the password policy
	 * @param  organizationId the primary key of the organization
	 * @return <code>true</code> if the password policy has been assigned to the
	 *         organization; <code>false</code> otherwise
	 */
	@Override
	public boolean hasPasswordPolicyOrganization(
		long passwordPolicyId, long organizationId) {

		return passwordPolicyRelLocalService.hasPasswordPolicyRel(
			passwordPolicyId, Organization.class.getName(), organizationId);
	}

	/**
	 * Returns <code>true</code> if the user is a member of the organization,
	 * optionally focusing on suborganizations or the specified organization.
	 * This method is usually called to determine if the user has view access to
	 * a resource belonging to the organization.
	 *
	 * <ol>
	 * <li>
	 * If <code>inheritSuborganizations=<code>false</code></code>:
	 * the method checks whether the user belongs to the organization specified
	 * by <code>organizationId</code>. The parameter
	 * <code>includeSpecifiedOrganization</code> is ignored.
	 * </li>
	 * <li>
	 * The parameter <code>includeSpecifiedOrganization</code> is
	 * ignored unless <code>inheritSuborganizations</code> is also
	 * <code>true</code>.
	 * </li>
	 * <li>
	 * If <code>inheritSuborganizations=<code>true</code></code> and
	 * <code>includeSpecifiedOrganization=<code>false</code></code>: the method
	 * checks
	 * whether the user belongs to one of the child organizations of the one
	 * specified by <code>organizationId</code>.
	 * </li>
	 * <li>
	 * If <code>inheritSuborganizations=<code>true</code></code> and
	 * <code>includeSpecifiedOrganization=<code>true</code></code>: the method
	 * checks whether
	 * the user belongs to the organization specified by
	 * <code>organizationId</code> or any of
	 * its child organizations.
	 * </li>
	 * </ol>
	 *
	 * @param  userId the primary key of the organization's user
	 * @param  organizationId the primary key of the organization
	 * @param  inheritSuborganizations if <code>true</code> suborganizations are
	 *         considered in the determination
	 * @param  includeSpecifiedOrganization if <code>true</code> the
	 *         organization specified by <code>organizationId</code> is
	 *         considered in the determination
	 * @return <code>true</code> if the user has access to the organization;
	 *         <code>false</code> otherwise
	 * @throws PortalException if an organization with the primary key could not
	 *         be found
	 * @see    com.liferay.portal.service.persistence.OrganizationFinder
	 */
	@Override
	public boolean hasUserOrganization(
			long userId, long organizationId, boolean inheritSuborganizations,
			boolean includeSpecifiedOrganization)
		throws PortalException {

		if (!inheritSuborganizations) {
			return userPersistence.containsOrganization(userId, organizationId);
		}

		LinkedHashMap<String, Object> params =
			new LinkedHashMap<String, Object>();

		List<Organization> organizationsTree = new ArrayList<Organization>();

		Organization organization = organizationPersistence.findByPrimaryKey(
			organizationId);

		if (!includeSpecifiedOrganization) {
			organizationsTree.add(organization);
		}
		else {
			organizationsTree.add(organization.getParentOrganization());
		}

		params.put("usersOrgsTree", organizationsTree);

		if (userFinder.countByUser(userId, params) > 0) {
			return true;
		}

		return false;
	}

	/**
	 * Rebuilds the organization's tree.
	 *
	 * <p>
	 * Only call this method if the tree has become stale through operations
	 * other than normal CRUD. Under normal circumstances the tree is
	 * automatically rebuilt whenever necessary.
	 * </p>
	 *
	 * @param  companyId the primary key of the organization's company
	 * @throws PortalException if an organization with the primary key could not
	 *         be found
	 */
	@Override
	public void rebuildTree(long companyId) throws PortalException {
		TreePathUtil.rebuildTree(
			companyId, OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID,
			StringPool.SLASH,
			new TreeModelTasksAdapter<Organization>() {

				@Override
				public List<Organization> findTreeModels(
					long previousId, long companyId, long parentPrimaryKey,
					int size) {

					return organizationPersistence.findByO_C_P(
						previousId, companyId, parentPrimaryKey,
						QueryUtil.ALL_POS, size,
						new OrganizationIdComparator(true));
				}

			}
		);
	}

	/**
	 * Returns an ordered range of all the organizations that match the
	 * keywords, using the indexer. It is preferable to use this method instead
	 * of the non-indexed version whenever possible for performance reasons.
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
	 * @param  companyId the primary key of the organization's company
	 * @param  parentOrganizationId the primary key of the organization's parent
	 *         organization
	 * @param  keywords the keywords (space separated), which may occur in the
	 *         organization's name, street, city, zipcode, type, region or
	 *         country (optionally <code>null</code>)
	 * @param  params the finder parameters (optionally <code>null</code>). For
	 *         more information see {@link
	 *         com.liferay.portlet.usersadmin.util.OrganizationIndexer}
	 * @param  start the lower bound of the range of organizations to return
	 * @param  end the upper bound of the range of organizations to return (not
	 *         inclusive)
	 * @param  sort the field and direction by which to sort (optionally
	 *         <code>null</code>)
	 * @return the matching organizations ordered by name
	 * @see    com.liferay.portlet.usersadmin.util.OrganizationIndexer
	 */
	@Override
	public Hits search(
		long companyId, long parentOrganizationId, String keywords,
		LinkedHashMap<String, Object> params, int start, int end, Sort sort) {

		String name = null;
		String type = null;
		String street = null;
		String city = null;
		String zip = null;
		String region = null;
		String country = null;
		boolean andOperator = false;

		if (Validator.isNotNull(keywords)) {
			name = keywords;
			type = keywords;
			street = keywords;
			city = keywords;
			zip = keywords;
			region = keywords;
			country = keywords;
		}
		else {
			andOperator = true;
		}

		if (params != null) {
			params.put("keywords", keywords);
		}

		return search(
			companyId, parentOrganizationId, name, type, street, city, zip,
			region, country, params, andOperator, start, end, sort);
	}

	/**
	 * Returns a name ordered range of all the organizations that match the
	 * keywords, type, region, and country, without using the indexer. It is
	 * preferable to use the indexed version {@link #search(long, long, String,
	 * LinkedHashMap, int, int, Sort)} instead of this method wherever possible
	 * for performance reasons.
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
	 * @param  companyId the primary key of the organization's company
	 * @param  parentOrganizationId the primary key of the organization's parent
	 *         organization
	 * @param  keywords the keywords (space separated), which may occur in the
	 *         organization's name, street, city, or zipcode (optionally
	 *         <code>null</code>)
	 * @param  type the organization's type (optionally <code>null</code>)
	 * @param  regionId the primary key of the organization's region (optionally
	 *         <code>null</code>)
	 * @param  countryId the primary key of the organization's country
	 *         (optionally <code>null</code>)
	 * @param  params the finder params. For more information see {@link
	 *         com.liferay.portal.service.persistence.OrganizationFinder}
	 * @param  start the lower bound of the range of organizations to return
	 * @param  end the upper bound of the range of organizations to return (not
	 *         inclusive)
	 * @return the matching organizations ordered by name
	 * @see    com.liferay.portal.service.persistence.OrganizationFinder
	 */
	@Override
	public List<Organization> search(
		long companyId, long parentOrganizationId, String keywords, String type,
		Long regionId, Long countryId, LinkedHashMap<String, Object> params,
		int start, int end) {

		return search(
			companyId, parentOrganizationId, keywords, type, regionId,
			countryId, params, start, end,
			new OrganizationNameComparator(true));
	}

	/**
	 * Returns an ordered range of all the organizations that match the
	 * keywords, type, region, and country, without using the indexer. It is
	 * preferable to use the indexed version {@link #search(long, long, String,
	 * String, String, String, String, String, String, LinkedHashMap, boolean,
	 * int, int, Sort)} instead of this method wherever possible for performance
	 * reasons.
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
	 * @param  companyId the primary key of the organization's company
	 * @param  parentOrganizationId the primary key of the organization's parent
	 *         organization
	 * @param  keywords the keywords (space separated), which may occur in the
	 *         organization's name, street, city, or zipcode (optionally
	 *         <code>null</code>)
	 * @param  type the organization's type (optionally <code>null</code>)
	 * @param  regionId the primary key of the organization's region (optionally
	 *         <code>null</code>)
	 * @param  countryId the primary key of the organization's country
	 *         (optionally <code>null</code>)
	 * @param  params the finder params. For more information see {@link
	 *         com.liferay.portal.service.persistence.OrganizationFinder}
	 * @param  start the lower bound of the range of organizations to return
	 * @param  end the upper bound of the range of organizations to return (not
	 *         inclusive)
	 * @param  obc the comparator to order the organizations (optionally
	 *         <code>null</code>)
	 * @return the matching organizations ordered by comparator <code>obc</code>
	 * @see    com.liferay.portal.service.persistence.OrganizationFinder
	 */
	@Override
	public List<Organization> search(
		long companyId, long parentOrganizationId, String keywords, String type,
		Long regionId, Long countryId, LinkedHashMap<String, Object> params,
		int start, int end, OrderByComparator<Organization> obc) {

		String parentOrganizationIdComparator = StringPool.EQUAL;

		if (parentOrganizationId ==
				OrganizationConstants.ANY_PARENT_ORGANIZATION_ID) {

			parentOrganizationIdComparator = StringPool.NOT_EQUAL;
		}

		return organizationFinder.findByKeywords(
			companyId, parentOrganizationId, parentOrganizationIdComparator,
			keywords, type, regionId, countryId, params, start, end, obc);
	}

	/**
	 * Returns a name ordered range of all the organizations with the type,
	 * region, and country, and whose name, street, city, and zipcode match the
	 * keywords specified for them, without using the indexer. It is preferable
	 * to use the indexed version {@link #search(long, long, String, String,
	 * String, String, String, String, String, LinkedHashMap, boolean, int, int,
	 * Sort)} instead of this method wherever possible for performance reasons.
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
	 * @param  companyId the primary key of the organization's company
	 * @param  parentOrganizationId the primary key of the organization's parent
	 * @param  name the name keywords (space separated, optionally
	 *         <code>null</code>)
	 * @param  type the organization's type (optionally <code>null</code>)
	 * @param  street the street keywords (optionally <code>null</code>)
	 * @param  city the city keywords (optionally <code>null</code>)
	 * @param  zip the zipcode keywords (optionally <code>null</code>)
	 * @param  regionId the primary key of the organization's region (optionally
	 *         <code>null</code>)
	 * @param  countryId the primary key of the organization's country
	 *         (optionally <code>null</code>)
	 * @param  params the finder parameters (optionally <code>null</code>). For
	 *         more information see {@link
	 *         com.liferay.portal.service.persistence.OrganizationFinder}
	 * @param  andOperator whether every field must match its keywords, or just
	 *         one field. For example, &quot;organizations with the name
	 *         'Employees' and city 'Chicago'&quot; vs &quot;organizations with
	 *         the name 'Employees' or the city 'Chicago'&quot;.
	 * @param  start the lower bound of the range of organizations to return
	 * @param  end the upper bound of the range of organizations to return (not
	 *         inclusive)
	 * @return the matching organizations ordered by name
	 * @see    com.liferay.portal.service.persistence.OrganizationFinder
	 */
	@Override
	public List<Organization> search(
		long companyId, long parentOrganizationId, String name, String type,
		String street, String city, String zip, Long regionId, Long countryId,
		LinkedHashMap<String, Object> params, boolean andOperator, int start,
		int end) {

		return search(
			companyId, parentOrganizationId, name, type, street, city, zip,
			regionId, countryId, params, andOperator, start, end,
			new OrganizationNameComparator(true));
	}

	/**
	 * Returns an ordered range of all the organizations with the type, region,
	 * and country, and whose name, street, city, and zipcode match the keywords
	 * specified for them, without using the indexer. It is preferable to use
	 * the indexed version {@link #search(long, long, String, String, String,
	 * String, String, String, String, LinkedHashMap, boolean, int, int, Sort)}
	 * instead of this method wherever possible for performance reasons.
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
	 * @param  companyId the primary key of the organization's company
	 * @param  parentOrganizationId the primary key of the organization's parent
	 *         organization
	 * @param  name the name keywords (space separated, optionally
	 *         <code>null</code>)
	 * @param  type the organization's type (optionally <code>null</code>)
	 * @param  street the street keywords (optionally <code>null</code>)
	 * @param  city the city keywords (optionally <code>null</code>)
	 * @param  zip the zipcode keywords (optionally <code>null</code>)
	 * @param  regionId the primary key of the organization's region (optionally
	 *         <code>null</code>)
	 * @param  countryId the primary key of the organization's country
	 *         (optionally <code>null</code>)
	 * @param  params the finder parameters (optionally <code>null</code>). For
	 *         more information see {@link
	 *         com.liferay.portal.service.persistence.OrganizationFinder}
	 * @param  andOperator whether every field must match its keywords, or just
	 *         one field. For example, &quot;organizations with the name
	 *         'Employees' and city 'Chicago'&quot; vs &quot;organizations with
	 *         the name 'Employees' or the city 'Chicago'&quot;.
	 * @param  start the lower bound of the range of organizations to return
	 * @param  end the upper bound of the range of organizations to return (not
	 *         inclusive)
	 * @param  obc the comparator to order the organizations (optionally
	 *         <code>null</code>)
	 * @return the matching organizations ordered by comparator <code>obc</code>
	 * @see    com.liferay.portal.service.persistence.OrganizationFinder
	 */
	@Override
	public List<Organization> search(
		long companyId, long parentOrganizationId, String name, String type,
		String street, String city, String zip, Long regionId, Long countryId,
		LinkedHashMap<String, Object> params, boolean andOperator, int start,
		int end, OrderByComparator<Organization> obc) {

		String parentOrganizationIdComparator = StringPool.EQUAL;

		if (parentOrganizationId ==
				OrganizationConstants.ANY_PARENT_ORGANIZATION_ID) {

			parentOrganizationIdComparator = StringPool.NOT_EQUAL;
		}

		return organizationFinder.findByC_PO_N_T_S_C_Z_R_C(
			companyId, parentOrganizationId, parentOrganizationIdComparator,
			name, type, street, city, zip, regionId, countryId, params,
			andOperator, start, end, obc);
	}

	/**
	 * Returns an ordered range of all the organizations whose name, type, or
	 * location fields match the keywords specified for them, using the indexer.
	 * It is preferable to use this method instead of the non-indexed version
	 * whenever possible for performance reasons.
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
	 * @param  companyId the primary key of the organization's company
	 * @param  parentOrganizationId the primary key of the organization's parent
	 *         organization
	 * @param  name the name keywords (space separated, optionally
	 *         <code>null</code>)
	 * @param  type the type keywords (optionally <code>null</code>)
	 * @param  street the street keywords (optionally <code>null</code>)
	 * @param  city the city keywords (optionally <code>null</code>)
	 * @param  zip the zipcode keywords (optionally <code>null</code>)
	 * @param  region the region keywords (optionally <code>null</code>)
	 * @param  country the country keywords (optionally <code>null</code>)
	 * @param  params the finder parameters (optionally <code>null</code>). For
	 *         more information see {@link
	 *         com.liferay.portlet.usersadmin.util.OrganizationIndexer}.
	 * @param  andSearch whether every field must match its keywords or just one
	 *         field
	 * @param  start the lower bound of the range of organizations to return
	 * @param  end the upper bound of the range of organizations to return (not
	 *         inclusive)
	 * @param  sort the field and direction by which to sort (optionally
	 *         <code>null</code>)
	 * @return the matching organizations ordered by <code>sort</code>
	 * @see    com.liferay.portlet.usersadmin.util.OrganizationIndexer
	 */
	@Override
	public Hits search(
		long companyId, long parentOrganizationId, String name, String type,
		String street, String city, String zip, String region, String country,
		LinkedHashMap<String, Object> params, boolean andSearch, int start,
		int end, Sort sort) {

		try {
			Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
				Organization.class);

			SearchContext searchContext = buildSearchContext(
				companyId, parentOrganizationId, name, type, street, city, zip,
				region, country, params, andSearch, start, end, sort);

			return indexer.search(searchContext);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	/**
	 * Returns the number of organizations that match the keywords, type,
	 * region, and country.
	 *
	 * @param  companyId the primary key of the organization's company
	 * @param  parentOrganizationId the primary key of the organization's parent
	 *         organization
	 * @param  keywords the keywords (space separated), which may occur in the
	 *         organization's name, street, city, or zipcode (optionally
	 *         <code>null</code>)
	 * @param  type the organization's type (optionally <code>null</code>)
	 * @param  regionId the primary key of the organization's region (optionally
	 *         <code>null</code>)
	 * @param  countryId the primary key of the organization's country
	 *         (optionally <code>null</code>)
	 * @param  params the finder parameters (optionally <code>null</code>). For
	 *         more information see {@link
	 *         com.liferay.portal.service.persistence.OrganizationFinder}
	 * @return the number of matching organizations
	 * @see    com.liferay.portal.service.persistence.OrganizationFinder
	 */
	@Override
	public int searchCount(
		long companyId, long parentOrganizationId, String keywords, String type,
		Long regionId, Long countryId, LinkedHashMap<String, Object> params) {

		if (!PropsValues.ORGANIZATIONS_INDEXER_ENABLED ||
			!PropsValues.ORGANIZATIONS_SEARCH_WITH_INDEX ||
			isUseCustomSQL(params)) {

			String parentOrganizationIdComparator = StringPool.EQUAL;

			if (parentOrganizationId ==
					OrganizationConstants.ANY_PARENT_ORGANIZATION_ID) {

				parentOrganizationIdComparator = StringPool.NOT_EQUAL;
			}

			return organizationFinder.countByKeywords(
				companyId, parentOrganizationId, parentOrganizationIdComparator,
				keywords, type, regionId, countryId, params);
		}

		try {
			String name = null;
			String street = null;
			String city = null;
			String zip = null;
			boolean andOperator = false;

			if (Validator.isNotNull(keywords)) {
				name = keywords;
				street = keywords;
				city = keywords;
				zip = keywords;
			}
			else {
				andOperator = true;
			}

			if (params != null) {
				params.put("keywords", keywords);
			}

			Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
				Organization.class);

			SearchContext searchContext = buildSearchContext(
				companyId, parentOrganizationId, name, type, street, city, zip,
				regionId, countryId, params, andOperator, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

			Hits hits = indexer.search(searchContext);

			return hits.getLength();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	/**
	 * Returns the number of organizations with the type, region, and country,
	 * and whose name, street, city, and zipcode match the keywords specified
	 * for them.
	 *
	 * @param  companyId the primary key of the organization's company
	 * @param  parentOrganizationId the primary key of the organization's parent
	 *         organization
	 * @param  name the name keywords (space separated, optionally
	 *         <code>null</code>)
	 * @param  type the organization's type (optionally <code>null</code>)
	 * @param  street the street keywords (optionally <code>null</code>)
	 * @param  city the city keywords (optionally <code>null</code>)
	 * @param  zip the zipcode keywords (optionally <code>null</code>)
	 * @param  regionId the primary key of the organization's region (optionally
	 *         <code>null</code>)
	 * @param  countryId the primary key of the organization's country
	 *         (optionally <code>null</code>)
	 * @param  params the finder parameters (optionally <code>null</code>). For
	 *         more information see {@link
	 *         com.liferay.portal.service.persistence.OrganizationFinder}
	 * @param  andOperator whether every field must match its keywords, or just
	 *         one field. For example, &quot;organizations with the name
	 *         'Employees' and city 'Chicago'&quot; vs &quot;organizations with
	 *         the name 'Employees' or the city 'Chicago'&quot;.
	 * @return the number of matching organizations
	 * @see    com.liferay.portal.service.persistence.OrganizationFinder
	 */
	@Override
	public int searchCount(
		long companyId, long parentOrganizationId, String name, String type,
		String street, String city, String zip, Long regionId, Long countryId,
		LinkedHashMap<String, Object> params, boolean andOperator) {

		if (!PropsValues.ORGANIZATIONS_INDEXER_ENABLED ||
			!PropsValues.ORGANIZATIONS_SEARCH_WITH_INDEX ||
			isUseCustomSQL(params)) {

			String parentOrganizationIdComparator = StringPool.EQUAL;

			if (parentOrganizationId ==
					OrganizationConstants.ANY_PARENT_ORGANIZATION_ID) {

				parentOrganizationIdComparator = StringPool.NOT_EQUAL;
			}

			return organizationFinder.countByC_PO_N_T_S_C_Z_R_C(
				companyId, parentOrganizationId, parentOrganizationIdComparator,
				name, type, street, city, zip, regionId, countryId, params,
				andOperator);
		}

		try {
			Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
				Organization.class);

			SearchContext searchContext = buildSearchContext(
				companyId, parentOrganizationId, name, type, street, city, zip,
				regionId, countryId, params, andOperator, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

			Hits hits = indexer.search(searchContext);

			return hits.getLength();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@Override
	public BaseModelSearchResult<Organization> searchOrganizations(
			long companyId, long parentOrganizationId, String keywords,
			LinkedHashMap<String, Object> params, int start, int end, Sort sort)
		throws PortalException {

		String name = null;
		String type = null;
		String street = null;
		String city = null;
		String zip = null;
		String region = null;
		String country = null;
		boolean andOperator = false;

		if (Validator.isNotNull(keywords)) {
			name = keywords;
			type = keywords;
			street = keywords;
			city = keywords;
			zip = keywords;
			region = keywords;
			country = keywords;
		}
		else {
			andOperator = true;
		}

		if (params != null) {
			params.put("keywords", keywords);
		}

		return searchOrganizations(
			companyId, parentOrganizationId, name, type, street, city, zip,
			region, country, params, andOperator, start, end, sort);
	}

	@Override
	public BaseModelSearchResult<Organization> searchOrganizations(
			long companyId, long parentOrganizationId, String name, String type,
			String street, String city, String zip, String region,
			String country, LinkedHashMap<String, Object> params,
			boolean andSearch, int start, int end, Sort sort)
		throws PortalException {

		Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			Organization.class);

		SearchContext searchContext = buildSearchContext(
			companyId, parentOrganizationId, name, type, street, city, zip,
			region, country, params, andSearch, start, end, sort);

		for (int i = 0; i < 10; i++) {
			Hits hits = indexer.search(searchContext);

			List<Organization> organizations = UsersAdminUtil.getOrganizations(
				hits);

			if (organizations != null) {
				return new BaseModelSearchResult<Organization>(
					organizations, hits.getLength());
			}
		}

		throw new SearchException(
			"Unable to fix the search index after 10 attempts");
	}

	/**
	 * Sets the organizations in the group, removing and adding organizations to
	 * the group as necessary.
	 *
	 * @param  groupId the primary key of the group
	 * @param  organizationIds the primary keys of the organizations
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public void setGroupOrganizations(long groupId, long[] organizationIds)
		throws PortalException {

		groupPersistence.setOrganizations(groupId, organizationIds);

		PermissionCacheUtil.clearCache();
	}

	/**
	 * Removes the organizations from the group.
	 *
	 * @param  groupId the primary key of the group
	 * @param  organizationIds the primary keys of the organizations
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public void unsetGroupOrganizations(long groupId, long[] organizationIds)
		throws PortalException {

		groupPersistence.removeOrganizations(groupId, organizationIds);

		PermissionCacheUtil.clearCache();
	}

	/**
	 * Removes the organizations from the password policy.
	 *
	 * @param passwordPolicyId the primary key of the password policy
	 * @param organizationIds the primary keys of the organizations
	 */
	@Override
	public void unsetPasswordPolicyOrganizations(
		long passwordPolicyId, long[] organizationIds) {

		passwordPolicyRelLocalService.deletePasswordPolicyRels(
			passwordPolicyId, Organization.class.getName(), organizationIds);
	}

	/**
	 * Updates the organization's asset with the new asset categories and tag
	 * names, removing and adding asset categories and tag names as necessary.
	 *
	 * @param  userId the primary key of the user
	 * @param  organization the organization
	 * @param  assetCategoryIds the primary keys of the asset categories
	 * @param  assetTagNames the asset tag names
	 * @throws PortalException if a user with the primary key could not be found
	 */
	@Override
	public void updateAsset(
			long userId, Organization organization, long[] assetCategoryIds,
			String[] assetTagNames)
		throws PortalException {

		User user = userPersistence.findByPrimaryKey(userId);

		Company company = companyPersistence.findByPrimaryKey(
			user.getCompanyId());

		Group companyGroup = company.getGroup();

		assetEntryLocalService.updateEntry(
			userId, companyGroup.getGroupId(), null, null,
			Organization.class.getName(), organization.getOrganizationId(),
			organization.getUuid(), 0, assetCategoryIds, assetTagNames, false,
			null, null, null, null, organization.getName(), StringPool.BLANK,
			null, null, null, 0, 0, null, false);
	}

	/**
	 * Updates the organization.
	 *
	 * @param      companyId the primary key of the organization's company
	 * @param      organizationId the primary key of the organization
	 * @param      parentOrganizationId the primary key of organization's parent
	 *             organization
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
	 *             with the primary key could not be found or if the new
	 *             information was invalid
	 * @deprecated As of 6.2.0, replaced by {@link #updateOrganization(long,
	 *             long, long, String, String, long, long, int, String, boolean,
	 *             byte[], boolean, ServiceContext)}
	 */
	@Deprecated
	@Override
	public Organization updateOrganization(
			long companyId, long organizationId, long parentOrganizationId,
			String name, String type, boolean recursable, long regionId,
			long countryId, int statusId, String comments, boolean site,
			ServiceContext serviceContext)
		throws PortalException {

		return updateOrganization(
			companyId, organizationId, parentOrganizationId, name, type,
			regionId, countryId, statusId, comments, true, null, site,
			serviceContext);
	}

	/**
	 * Updates the organization.
	 *
	 * @param  companyId the primary key of the organization's company
	 * @param  organizationId the primary key of the organization
	 * @param  parentOrganizationId the primary key of organization's parent
	 *         organization
	 * @param  name the organization's name
	 * @param  type the organization's type
	 * @param  regionId the primary key of the organization's region
	 * @param  countryId the primary key of the organization's country
	 * @param  statusId the organization's workflow status
	 * @param  comments the comments about the organization
	 * @param  logo whether to update the ogranization's logo
	 * @param  logoBytes the new logo image data
	 * @param  site whether the organization is to be associated with a main
	 *         site
	 * @param  serviceContext the service context to be applied (optionally
	 *         <code>null</code>). Can set asset category IDs and asset tag
	 *         names for the organization, and merge expando bridge attributes
	 *         for the organization.
	 * @return the organization
	 * @throws PortalException if an organization or parent organization with
	 *         the primary key could not be found or if the new information was
	 *         invalid
	 */
	@Override
	public Organization updateOrganization(
			long companyId, long organizationId, long parentOrganizationId,
			String name, String type, long regionId, long countryId,
			int statusId, String comments, boolean logo, byte[] logoBytes,
			boolean site, ServiceContext serviceContext)
		throws PortalException {

		// Organization

		parentOrganizationId = getParentOrganizationId(
			companyId, parentOrganizationId);

		validate(
			companyId, organizationId, parentOrganizationId, name, type,
			countryId, statusId);

		Organization organization = organizationPersistence.findByPrimaryKey(
			organizationId);

		long oldParentOrganizationId = organization.getParentOrganizationId();
		String oldName = organization.getName();

		organization.setModifiedDate(new Date());
		organization.setParentOrganizationId(parentOrganizationId);
		organization.setTreePath(organization.buildTreePath());
		organization.setName(name);
		organization.setType(type);
		organization.setRecursable(true);
		organization.setRegionId(regionId);
		organization.setCountryId(countryId);
		organization.setStatusId(statusId);
		organization.setComments(comments);

		PortalUtil.updateImageId(
			organization, logo, logoBytes, "logoId",
			PrefsPropsUtil.getLong(PropsKeys.USERS_IMAGE_MAX_SIZE),
			PropsValues.USERS_IMAGE_MAX_HEIGHT,
			PropsValues.USERS_IMAGE_MAX_WIDTH);

		organization.setExpandoBridgeAttributes(serviceContext);

		organizationPersistence.update(organization);

		// Group

		Group group = organization.getGroup();

		long parentGroupId = group.getParentGroupId();

		boolean organizationGroup = isOrganizationGroup(
			oldParentOrganizationId, group.getParentGroupId());

		if (organizationGroup) {
			if (parentOrganizationId !=
					OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID) {

				Organization parentOrganization =
					organizationPersistence.fetchByPrimaryKey(
						parentOrganizationId);

				Group parentGroup = parentOrganization.getGroup();

				if (site && parentGroup.isSite()) {
					parentGroupId = parentOrganization.getGroupId();
				}
				else {
					parentGroupId = GroupConstants.DEFAULT_PARENT_GROUP_ID;
				}
			}
			else {
				parentGroupId = GroupConstants.DEFAULT_PARENT_GROUP_ID;
			}
		}

		if (!oldName.equals(name) || organizationGroup) {
			groupLocalService.updateGroup(
				group.getGroupId(), parentGroupId, name, group.getDescription(),
				group.getType(), group.isManualMembership(),
				group.getMembershipRestriction(), group.getFriendlyURL(),
				group.isActive(), null);
		}

		if (group.isSite() != site) {
			groupLocalService.updateSite(group.getGroupId(), site);
		}

		// Asset

		if (serviceContext != null) {
			updateAsset(
				serviceContext.getUserId(), organization,
				serviceContext.getAssetCategoryIds(),
				serviceContext.getAssetTagNames());
		}

		// Indexer

		Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			Organization.class);

		if (oldParentOrganizationId != parentOrganizationId) {
			long[] organizationIds = getReindexOrganizationIds(organization);

			indexer.reindex(organizationIds);
		}
		else {
			indexer.reindex(organization);
		}

		return organization;
	}

	/**
	 * Updates the organization.
	 *
	 * @param      companyId the primary key of the organization's company
	 * @param      organizationId the primary key of the organization
	 * @param      parentOrganizationId the primary key of organization's parent
	 *             organization
	 * @param      name the organization's name
	 * @param      type the organization's type
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
	 *             with the primary key could not be found or if the new
	 *             information was invalid
	 * @deprecated As of 7.0.0, replaced by {@link #updateOrganization(long,
	 *             long, long, String, String, long, long, int, String, boolean,
	 *             boolean, byte[], ServiceContext)}
	 */
	@Deprecated
	@Override
	public Organization updateOrganization(
			long companyId, long organizationId, long parentOrganizationId,
			String name, String type, long regionId, long countryId,
			int statusId, String comments, boolean site,
			ServiceContext serviceContext)
		throws PortalException {

		return updateOrganization(
			companyId, organizationId, parentOrganizationId, name, type,
			regionId, countryId, statusId, comments, site, serviceContext);
	}

	protected void addSuborganizations(
		List<Organization> allSuborganizations,
		List<Organization> organizations) {

		for (Organization organization : organizations) {
			if (!allSuborganizations.contains(organization)) {
				allSuborganizations.add(organization);

				List<Organization> suborganizations =
					organizationPersistence.findByC_P(
						organization.getCompanyId(),
						organization.getOrganizationId());

				addSuborganizations(allSuborganizations, suborganizations);
			}
		}
	}

	protected SearchContext buildSearchContext(
		long companyId, long parentOrganizationId, String name, String type,
		String street, String city, String zip, Long regionId, Long countryId,
		LinkedHashMap<String, Object> params, boolean andSearch, int start,
		int end, Sort sort) {

		String regionCode = null;

		if (regionId != null) {
			Region region = regionService.fetchRegion(regionId);

			regionCode = region.getRegionCode();
		}

		String countryName = null;

		if (countryId != null) {
			Country country = countryService.fetchCountry(countryId);

			countryName = country.getName();
		}

		return buildSearchContext(
			companyId, parentOrganizationId, name, type, street, city, zip,
			regionCode, countryName, params, andSearch, start, end, sort);
	}

	protected SearchContext buildSearchContext(
		long companyId, long parentOrganizationId, String name, String type,
		String street, String city, String zip, String region, String country,
		LinkedHashMap<String, Object> params, boolean andSearch, int start,
		int end, Sort sort) {

		SearchContext searchContext = new SearchContext();

		searchContext.setAndSearch(andSearch);

		Map<String, Serializable> attributes =
			new HashMap<String, Serializable>();

		attributes.put("city", city);
		attributes.put("country", country);
		attributes.put("name", name);
		attributes.put("params", params);
		attributes.put(
			"parentOrganizationId", String.valueOf(parentOrganizationId));
		attributes.put("region", region);
		attributes.put("street", street);
		attributes.put("type", type);
		attributes.put("zip", zip);

		searchContext.setAttributes(attributes);

		searchContext.setCompanyId(companyId);
		searchContext.setEnd(end);

		if (params != null) {
			String keywords = (String)params.remove("keywords");

			if (Validator.isNotNull(keywords)) {
				searchContext.setKeywords(keywords);
			}
		}

		if (sort != null) {
			searchContext.setSorts(sort);
		}

		searchContext.setStart(start);

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		return searchContext;
	}

	protected long getParentOrganizationId(
		long companyId, long parentOrganizationId) {

		if (parentOrganizationId !=
				OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID) {

			// Ensure parent organization exists and belongs to the proper
			// company

			Organization parentOrganization =
				organizationPersistence.fetchByPrimaryKey(parentOrganizationId);

			if ((parentOrganization == null) ||
				(companyId != parentOrganization.getCompanyId())) {

				parentOrganizationId =
					OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID;
			}
		}

		return parentOrganizationId;
	}

	protected long[] getReindexOrganizationIds(Organization organization)
		throws PortalException {

		List<Organization> organizations = organizationPersistence.findByC_T(
			organization.getCompanyId(),
			CustomSQLUtil.keywords(organization.getTreePath())[0],
			QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new OrganizationNameComparator(true));

		long[] organizationIds = new long[organizations.size()];

		for (int i = 0; i < organizations.size(); i++) {
			Organization curOrganization = organizations.get(i);

			curOrganization.setTreePath(curOrganization.buildTreePath());

			organizationPersistence.update(curOrganization);

			organizationIds[i] = curOrganization.getOrganizationId();
		}

		if (!ArrayUtil.contains(
				organizationIds, organization.getOrganizationId())) {

			organizationIds = ArrayUtil.append(
				organizationIds, organization.getOrganizationId());
		}

		return organizationIds;
	}

	protected boolean isOrganizationGroup(long organizationId, long groupId) {
		if ((organizationId ==
				OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID) &&
			(groupId == GroupConstants.DEFAULT_PARENT_GROUP_ID)) {

			return true;
		}

		if (organizationId !=
				OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID) {

			Organization organization =
				organizationPersistence.fetchByPrimaryKey(organizationId);

			if (organization.getGroupId() == groupId) {
				return true;
			}
		}

		return false;
	}

	protected boolean isParentOrganization(
			long parentOrganizationId, long organizationId)
		throws PortalException {

		// Return true if parentOrganizationId is among the parent organizatons
		// of organizationId

		if (organizationId ==
				OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID) {

			return false;
		}

		Organization organization = organizationPersistence.findByPrimaryKey(
			organizationId);

		String treePath = organization.getTreePath();

		if (treePath.contains(
				StringPool.SLASH + parentOrganizationId + StringPool.SLASH)) {

			return true;
		}
		else {
			return false;
		}
	}

	protected boolean isUseCustomSQL(LinkedHashMap<String, Object> params) {
		if (MapUtil.isEmpty(params)) {
			return false;
		}

		return true;
	}

	protected void validate(
			long companyId, long organizationId, long parentOrganizationId,
			String name, String type, long countryId, int statusId)
		throws PortalException {

		if (!ArrayUtil.contains(PropsValues.ORGANIZATIONS_TYPES, type)) {
			throw new OrganizationTypeException(
				"Invalid organization type " + type);
		}

		if (parentOrganizationId ==
				OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID) {

			if (!OrganizationImpl.isRootable(type)) {
				throw new OrganizationParentException(
					"Organization of type " + type + " cannot be a root");
			}
		}
		else {
			Organization parentOrganization =
				organizationPersistence.fetchByPrimaryKey(parentOrganizationId);

			if (parentOrganization == null) {
				throw new OrganizationParentException(
					"Organization " + parentOrganizationId + " doesn't exist");
			}

			String[] childrenTypes = OrganizationImpl.getChildrenTypes(
				parentOrganization.getType());

			if (childrenTypes.length == 0) {
				throw new OrganizationParentException(
					"Organization of type " + type + " cannot have children");
			}

			if ((companyId != parentOrganization.getCompanyId()) ||
				(parentOrganizationId == organizationId)) {

				throw new OrganizationParentException();
			}

			if (!ArrayUtil.contains(childrenTypes, type)) {
				throw new OrganizationParentException(
					"Type " + type + " not allowed as child of " +
						parentOrganization.getType());
			}
		}

		if ((organizationId > 0) &&
			(parentOrganizationId !=
				OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID)) {

			// Prevent circular organizational references

			if (isParentOrganization(organizationId, parentOrganizationId)) {
				throw new OrganizationParentException();
			}
		}

		if (Validator.isNull(name)) {
			throw new OrganizationNameException();
		}
		else {
			Organization organization = organizationPersistence.fetchByC_N(
				companyId, name);

			if ((organization != null) &&
				StringUtil.equalsIgnoreCase(organization.getName(), name)) {

				if ((organizationId <= 0) ||
					(organization.getOrganizationId() != organizationId)) {

					throw new DuplicateOrganizationException(
						"There is another organization named " + name);
				}
			}
		}

		boolean countryRequired = GetterUtil.getBoolean(
			PropsUtil.get(
				PropsKeys.ORGANIZATIONS_COUNTRY_REQUIRED, new Filter(type)));

		if (countryRequired || (countryId > 0)) {
			countryPersistence.findByPrimaryKey(countryId);
		}

		listTypeService.validate(
			statusId, ListTypeConstants.ORGANIZATION_STATUS);
	}

	protected void validate(
			long companyId, long parentOrganizationId, String name, String type,
			long countryId, int statusId)
		throws PortalException {

		validate(
			companyId, 0, parentOrganizationId, name, type, countryId,
			statusId);
	}

}