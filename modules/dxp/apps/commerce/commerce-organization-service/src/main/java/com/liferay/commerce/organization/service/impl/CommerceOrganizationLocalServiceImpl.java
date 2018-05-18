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

package com.liferay.commerce.organization.service.impl;

import com.liferay.commerce.organization.constants.CommerceOrganizationConstants;
import com.liferay.commerce.organization.service.base.CommerceOrganizationLocalServiceBaseImpl;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.EmailAddress;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.ListTypeConstants;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Marco Leo
 * @author Andrea Di Giorgi
 * @author Alessio Antonio Rendina
 */
public class CommerceOrganizationLocalServiceImpl
	extends CommerceOrganizationLocalServiceBaseImpl {

	public static final String ORGANIZATION_NAME_SUFFIX = " LFR_ORGANIZATION";

	public static final String ORGANIZATION_STAGING_SUFFIX = " (Staging)";

	@Override
	public Organization addOrganization(
			long parentOrganizationId, String name, String type,
			ServiceContext serviceContext)
		throws PortalException {

		return organizationLocalService.addOrganization(
			serviceContext.getUserId(), parentOrganizationId, name, type, 0, 0,
			ListTypeConstants.ORGANIZATION_STATUS_DEFAULT, StringPool.BLANK,
			false, serviceContext);
	}

	@Override
	public void addOrganizationUsers(
			long organizationId, String[] emailAddresses,
			ServiceContext serviceContext)
		throws PortalException {

		if (emailAddresses.length == 0) {
			return;
		}

		long companyId = serviceContext.getCompanyId();
		Locale locale = serviceContext.getLocale();

		long[] userIds = new long[emailAddresses.length];

		for (int i = 0; i < emailAddresses.length; i++) {
			String emailAddress = emailAddresses[i];

			User user = userLocalService.fetchUserByEmailAddress(
				companyId, emailAddress);

			if (user == null) {
				user = userLocalService.addUserWithWorkflow(
					serviceContext.getUserId(), companyId, true,
					StringPool.BLANK, StringPool.BLANK, true, StringPool.BLANK,
					emailAddress, 0, StringPool.BLANK, locale, emailAddress,
					StringPool.BLANK, emailAddress, 0, 0, true, 1, 1, 1970,
					StringPool.BLANK, null, new long[] {organizationId}, null,
					null, true, serviceContext);
			}

			userIds[i] = user.getUserId();
		}

		userLocalService.addOrganizationUsers(organizationId, userIds);
	}

	@Override
	public void configureB2BSite(long groupId, ServiceContext serviceContext)
		throws PortalException {

		Group group = groupLocalService.getGroup(groupId);

		String groupNameCurrentValue = group.getNameCurrentValue();

		Organization organization = createOrganization(
			groupNameCurrentValue, serviceContext);

		long classPK = organization.getOrganizationId();

		String groupKey = groupNameCurrentValue + ORGANIZATION_NAME_SUFFIX;
		Map<Locale, String> nameMap = group.getNameMap();
		String friendlyURL = group.getFriendlyURL();

		if (group.isStagingGroup()) {
			classPK = group.getLiveGroupId();
			groupKey = groupKey.concat("-staging");

			for (Map.Entry<Locale, String> entry : nameMap.entrySet()) {
				String name = entry.getValue();

				if (Validator.isNull(name)) {
					continue;
				}

				nameMap.put(
					entry.getKey(), name.concat(ORGANIZATION_STAGING_SUFFIX));
			}

			friendlyURL = friendlyURL.concat("-staging");
		}

		group.setParentGroupId(organization.getParentOrganizationId());
		group.setClassName(organization.getModelClassName());
		group.setClassPK(classPK);
		group.setTreePath(group.buildTreePath());
		group.setGroupKey(groupKey);
		group.setNameMap(nameMap);
		group.setType(GroupConstants.TYPE_SITE_PRIVATE);
		group.setManualMembership(false);
		group.setMembershipRestriction(
			GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION);
		group.setFriendlyURL(friendlyURL);
		group.setSite(true);

		groupLocalService.updateGroup(group);
	}

	@Override
	public Organization getAccountOrganization(long organizationId)
		throws PortalException {

		Organization organization = organizationLocalService.getOrganization(
			organizationId);

		while ((organization != null) &&
			   !CommerceOrganizationConstants.TYPE_ACCOUNT.equals(
				   organization.getType())) {

			organization = organization.getParentOrganization();
		}

		return organization;
	}

	@Override
	public Address getOrganizationPrimaryAddress(long organizationId)
		throws PortalException {

		Organization organization = organizationLocalService.getOrganization(
			organizationId);

		List<Address> addresses = organization.getAddresses();

		for (Address address : addresses) {
			if (address.isPrimary()) {
				return address;
			}
		}

		return addressLocalService.createAddress(0);
	}

	@Override
	public EmailAddress getOrganizationPrimaryEmailAddress(long organizationId)
		throws PortalException {

		Organization organization = organizationLocalService.getOrganization(
			organizationId);

		List<EmailAddress> emailAddresses =
			emailAddressLocalService.getEmailAddresses(
				organization.getCompanyId(), Organization.class.getName(),
				organization.getOrganizationId());

		for (EmailAddress emailAddress : emailAddresses) {
			if (emailAddress.isPrimary()) {
				return emailAddress;
			}
		}

		return emailAddressLocalService.createEmailAddress(0);
	}

	@Override
	public boolean hasGroupOrganization(long siteGroupId, long organizationId)
		throws PortalException {

		Organization organization = organizationLocalService.getOrganization(
			organizationId);

		while (organization != null) {
			if (organization.getGroupId() == siteGroupId) {
				return true;
			}

			if (organization.getParentOrganizationId() ==
					OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID) {

				break;
			}

			organization = organization.getParentOrganization();
		}

		return false;
	}

	@Override
	public boolean isB2BOrganization(long organizationId)
		throws PortalException {

		Organization organization = organizationLocalService.getOrganization(
			organizationId);

		if (ArrayUtil.contains(
				CommerceOrganizationConstants.TYPES, organization.getType())) {

			return true;
		}

		return false;
	}

	@Override
	public BaseModelSearchResult<Organization> searchOrganizations(
			long userId, long parentOrganizationId, String type,
			String keywords, int start, int end, Sort[] sorts)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		List<Organization> organizations = new ArrayList<>();

		Organization parentOrganization =
			organizationLocalService.getOrganization(parentOrganizationId);

		Indexer<Organization> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			Organization.class);

		SearchContext searchContext = buildSearchContext(
			user, parentOrganization, type, keywords, start, end, sorts);

		Hits hits = indexer.search(searchContext);

		Document[] documents = hits.getDocs();

		for (Document document : documents) {
			long classPK = GetterUtil.getLong(
				document.get(Field.ORGANIZATION_ID));

			organizations.add(
				organizationLocalService.getOrganization(classPK));
		}

		return new BaseModelSearchResult<>(organizations, hits.getLength());
	}

	@Override
	public BaseModelSearchResult<Organization> searchOrganizationsByGroup(
			long groupId, long userId, String type, String keywords, int start,
			int end, Sort[] sorts)
		throws PortalException {

		Group group = groupLocalService.getGroup(groupId);

		long parentOrganizationId = group.getOrganizationId();

		if (parentOrganizationId == 0) {
			return new BaseModelSearchResult<>(
				Collections.<Organization>emptyList(), 0);
		}

		return searchOrganizations(
			userId, parentOrganizationId, type, keywords, start, end, sorts);
	}

	@Override
	public void unsetOrganizationUsers(long organizationId, long[] userIds)
		throws PortalException {

		userLocalService.unsetOrganizationUsers(organizationId, userIds);
	}

	@Override
	public Organization updateOrganization(
			long organizationId, String name, long emailAddressId,
			String address, long addressId, String street1, String street2,
			String street3, String city, String zip, long regionId,
			long countryId, boolean logo, byte[] logoBytes,
			ServiceContext serviceContext)
		throws PortalException {

		Organization organization = organizationLocalService.getOrganization(
			organizationId);

		updateEmailAddress(
			emailAddressId, Organization.class.getName(), organizationId,
			address, serviceContext);

		updateAddress(
			addressId, Organization.class.getName(), organizationId, street1,
			street2, street3, city, zip, regionId, countryId, serviceContext);

		return organizationLocalService.updateOrganization(
			organization.getCompanyId(), organizationId,
			organization.getParentOrganizationId(), name,
			organization.getType(), regionId, countryId,
			organization.getStatusId(), organization.getComments(), logo,
			logoBytes, false, serviceContext);
	}

	protected SearchContext buildSearchContext(
			User user, Organization parentOrganization, String type,
			String keywords, int start, int end, Sort[] sorts)
		throws PortalException {

		SearchContext searchContext = new SearchContext();

		List<String> treePaths = new ArrayList<>();

		List<Organization> organizations = user.getOrganizations();

		for (Organization organization : organizations) {
			treePaths.add(organization.getTreePath());
		}

		LinkedHashMap<String, Object> params = new LinkedHashMap<>();

		params.put(Field.TREE_PATH, parentOrganization.getTreePath());
		params.put("organizationTreePaths", treePaths);

		if (Validator.isNotNull(type)) {
			params.put(Field.TYPE, type);
		}

		Map<String, Serializable> attributes = new HashMap<>();

		attributes.put("params", params);
		attributes.put(
			"parentOrganizationId",
			String.valueOf(OrganizationConstants.ANY_PARENT_ORGANIZATION_ID));

		if (Validator.isNotNull(keywords)) {
			attributes.put("name", keywords);
			searchContext.setKeywords(keywords);
		}

		searchContext.setAndSearch(false);
		searchContext.setAttributes(attributes);
		searchContext.setCompanyId(parentOrganization.getCompanyId());
		searchContext.setEnd(end);

		if (sorts != null) {
			searchContext.setSorts(sorts);
		}

		searchContext.setStart(start);

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		return searchContext;
	}

	protected Organization createOrganization(
			String name, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());

		name = _getUniqueName(serviceContext.getCompanyId(), name, 0);

		long organizationId = counterLocalService.increment();

		Organization organization = organizationLocalService.createOrganization(
			organizationId);

		if (serviceContext != null) {
			organization.setUuid(serviceContext.getUuid());
		}

		organization.setCompanyId(user.getCompanyId());
		organization.setUserId(user.getUserId());
		organization.setUserName(user.getFullName());
		organization.setParentOrganizationId(
			OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID);
		organization.setTreePath(organization.buildTreePath());
		organization.setName(name);
		organization.setType(OrganizationConstants.TYPE_ORGANIZATION);
		organization.setRecursable(true);
		organization.setStatusId(ListTypeConstants.ORGANIZATION_STATUS_DEFAULT);

		organizationLocalService.addOrganization(organization);

		// Role

		Role role = roleLocalService.getRole(
			organization.getCompanyId(), RoleConstants.ORGANIZATION_OWNER);

		userGroupRoleLocalService.addUserGroupRoles(
			user.getUserId(), serviceContext.getScopeGroupId(),
			new long[] {role.getRoleId()});

		// Resources

		organizationLocalService.addOrganizationResources(
			user.getUserId(), organization);

		// Asset

		organizationLocalService.updateAsset(
			user.getUserId(), organization,
			serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames());

		// Indexer

		Indexer<Organization> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			Organization.class);

		indexer.reindex(organization);

		return organization;
	}

	protected void updateAddress(
			long addressId, String className, long classPK, String street1,
			String street2, String street3, String city, String zip,
			long regionId, long countryId, ServiceContext serviceContext)
		throws PortalException {

		if (addressId > 0) {
			Address address = addressLocalService.getAddress(addressId);

			addressLocalService.updateAddress(
				addressId, street1, street2, street3, city, zip, regionId,
				countryId, address.getTypeId(), address.isMailing(),
				address.isPrimary());
		}

		addressLocalService.addAddress(
			serviceContext.getUserId(), className, classPK, street1, street2,
			street3, city, zip, regionId, countryId, 0L, false, true,
			serviceContext);
	}

	protected void updateEmailAddress(
			long emailAddressId, String className, long classPK, String address,
			ServiceContext serviceContext)
		throws PortalException {

		if (emailAddressId > 0) {
			EmailAddress emailAddress =
				emailAddressLocalService.getEmailAddress(emailAddressId);

			emailAddressLocalService.updateEmailAddress(
				emailAddressId, address, emailAddress.getTypeId(),
				emailAddress.isPrimary());
		}

		emailAddressLocalService.addEmailAddress(
			serviceContext.getUserId(), className, classPK, address, 0L, true,
			serviceContext);
	}

	private String _getUniqueName(long companyId, String name, int count) {
		Organization organization = organizationLocalService.fetchOrganization(
			companyId, name);

		if (organization != null) {
			name = name + StringPool.UNDERLINE + count++;

			name = _getUniqueName(companyId, name, count);
		}

		return name;
	}

}