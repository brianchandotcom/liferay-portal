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

package com.liferay.content.space.apio.internal.architect.resource.test;

import com.liferay.apio.architect.language.AcceptLanguage;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.resource.CollectionResource;
import com.liferay.apio.architect.test.util.pagination.PaginationRequest;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerTestRule;

import java.lang.reflect.Method;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Julio Camarero
 */
@RunWith(Arquillian.class)
public class ContentSpaceCollectionResourceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerTestRule.INSTANCE);

	@Test
	public void testGetActiveGroup() throws Throwable {
		Group group = GroupTestUtil.addGroup();

		try {
			Company company = _companyLocalService.getCompany(
				group.getCompanyId());

			PageItems<Group> groupPageItems = _getPageItems(
				PaginationRequest.of(10, 1), company, _acceptLanguage);

			List<Group> groups = (List<Group>)groupPageItems.getItems();

			Assert.assertTrue(groups.contains(group));
		}
		catch (Exception e) {
			_groupLocalService.deleteGroup(group);
		}
	}

	@Test
	public void testGetInactiveGroup() throws Throwable {
		Group group = GroupTestUtil.addGroup();

		try {
			_deactivateGroup(group);

			Company company = _companyLocalService.getCompany(
				group.getCompanyId());

			PageItems<Group> groupPageItems = _getPageItems(
				PaginationRequest.of(10, 1), company, _acceptLanguage);

			List<Group> groups = (List<Group>)groupPageItems.getItems();

			Assert.assertFalse(groups.contains(group));
		}
		catch (Exception e) {
			_groupLocalService.deleteGroup(group);
		}
	}

	private Group _deactivateGroup(Group group) throws PortalException {
		return _groupLocalService.updateGroup(
			group.getGroupId(), group.getParentGroupId(), group.getNameMap(),
			group.getDescriptionMap(), group.getType(),
			group.isManualMembership(), group.getMembershipRestriction(),
			group.getFriendlyURL(), group.isInheritContent(), false,
			new ServiceContext());
	}

	private PageItems<Group> _getPageItems(
			Pagination pagination, Company company,
			AcceptLanguage acceptLanguage)
		throws Exception {

		Class<? extends CollectionResource> clazz =
			_collectionResource.getClass();

		Method method = clazz.getDeclaredMethod(
			"_getPageItems", Pagination.class, Company.class,
			AcceptLanguage.class);

		method.setAccessible(true);

		return (PageItems)method.invoke(
			_collectionResource, pagination, company, acceptLanguage);
	}

	private static final AcceptLanguage _acceptLanguage =
		() -> LocaleUtil.getDefault();

	@Inject(
		filter = "component.name=com.liferay.content.space.apio.internal.architect.resource.ContentSpaceCollectionResource"
	)
	private CollectionResource _collectionResource;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private GroupLocalService _groupLocalService;

}