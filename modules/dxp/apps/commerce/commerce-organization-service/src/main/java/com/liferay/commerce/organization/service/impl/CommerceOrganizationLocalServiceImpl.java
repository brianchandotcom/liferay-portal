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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.messaging.proxy.ProxyModeThreadLocal;
import com.liferay.portal.kernel.messaging.proxy.ProxyModeThreadLocalCloseable;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.EmailAddress;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ListTypeConstants;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.OrganizationConstants;
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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.ArrayList;
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

		try (ProxyModeThreadLocalCloseable proxyModeThreadLocalCloseable =
				new ProxyModeThreadLocalCloseable()) {

			ProxyModeThreadLocal.setForceSync(true);

			long[] userIds = new long[emailAddresses.length];

			for (int i = 0; i < emailAddresses.length; i++) {
				String emailAddress = emailAddresses[i];

				User user = userLocalService.fetchUserByEmailAddress(
					companyId, emailAddress);

				if (user == null) {
					user = userLocalService.addUserWithWorkflow(
						serviceContext.getUserId(), companyId, true,
						StringPool.BLANK, StringPool.BLANK, true,
						StringPool.BLANK, emailAddress, 0, StringPool.BLANK,
						locale, emailAddress, StringPool.BLANK, emailAddress, 0,
						0, true, 1, 1, 1970, StringPool.BLANK, null,
						new long[] {organizationId}, null, null, true,
						serviceContext);
				}

				userIds[i] = user.getUserId();
			}

			userLocalService.addOrganizationUsers(organizationId, userIds);
		}
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

		return addressPersistence.create(0);
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

		return emailAddressPersistence.create(0);
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

		return searchOrganizations(
			userId, parentOrganizationId, type, keywords, start, end, sorts);
	}

	@Override
	public void unsetOrganizationUsers(long organizationId, long[] userIds)
		throws PortalException {

		try (ProxyModeThreadLocalCloseable proxyModeThreadLocalCloseable =
				new ProxyModeThreadLocalCloseable()) {

			ProxyModeThreadLocal.setForceSync(true);

			userLocalService.unsetOrganizationUsers(organizationId, userIds);
		}
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

		boolean andSearch = true;

		LinkedHashMap<String, Object> params = new LinkedHashMap<>();

		params.put(Field.TREE_PATH, parentOrganization.getTreePath());

		params.put("organizationTreePaths", treePaths);

		Map<String, Serializable> attributes = new HashMap<>();

		attributes.put("params", params);

		attributes.put(
			"parentOrganizationId",
			String.valueOf(OrganizationConstants.ANY_PARENT_ORGANIZATION_ID));

		if (Validator.isNotNull(type)) {
			attributes.put(Field.TYPE, type);
		}

		if (Validator.isNotNull(keywords)) {
			attributes.put("city", keywords);
			attributes.put("country", keywords);
			attributes.put("name", keywords);
			attributes.put("region", keywords);
			attributes.put("street", keywords);
			attributes.put("zip", keywords);

			andSearch = false;

			searchContext.setKeywords(keywords);
		}

		searchContext.setAndSearch(andSearch);
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

	protected void updateAddress(
			long addressId, String className, long classPK, String street1,
			String street2, String street3, String city, String zip,
			long regionId, long countryId, ServiceContext serviceContext)
		throws PortalException {

		if (addressId > 0) {
			Address address = addressLocalService.getAddress(addressId);

			addressLocalService.updateAddress(
				addressId, street1, street2, street3, city, zip, regionId,
				countryId, address.getTypeId(), address.getMailing(),
				address.getPrimary());
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
				emailAddress.getPrimary());
		}

		emailAddressLocalService.addEmailAddress(
			serviceContext.getUserId(), className, classPK, address, 0L, true,
			serviceContext);
	}

}