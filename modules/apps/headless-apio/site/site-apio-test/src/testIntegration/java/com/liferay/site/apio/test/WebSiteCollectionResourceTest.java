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

package com.liferay.site.apio.test;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.resource.CollectionResource;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.apio.test.util.PaginationRequest;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerTestRule;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.test.LayoutTestUtil;

import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Julio Camarero
 */
@RunWith(Arquillian.class)
public class WebSiteCollectionResourceTest {

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

			PageItems<Group> pageItems = _getPageItems(
				PaginationRequest.of(50, 1), company);

			List<Group> groups = (List<Group>)pageItems.getItems();

			Assert.assertTrue(groups.contains(group));
		}
		finally {
			_groupLocalService.deleteGroup(group);
		}
	}

	@Test
	public void testGetGroup() throws Throwable {
		Group group = GroupTestUtil.addGroup();

		try {
			Group finalGroup = _getGroup(group.getGroupId());

			Assert.assertEquals(group.getName(), finalGroup.getName());
		}
		finally {
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

			PageItems<Group> pageItems = _getPageItems(
				PaginationRequest.of(50, 1), company);

			List<Group> groups = (List<Group>)pageItems.getItems();

			Assert.assertFalse(groups.contains(group));
		}
		finally {
			_groupLocalService.deleteGroup(group);
		}
	}

	@Test
	public void testGetPrivateUrl() throws Throwable {
		Group group = GroupTestUtil.addGroup();

		try {
			LayoutTestUtil.addLayout(
				group.getGroupId(), true,
				new HashMap<Locale, String>() {
					{
						put(LocaleUtil.US, RandomTestUtil.randomString());
						put(LocaleUtil.SPAIN, RandomTestUtil.randomString());
					}
				},
				new HashMap<Locale, String>() {
					{
						put(
							LocaleUtil.US,
							StringPool.SLASH + RandomTestUtil.randomString());
						put(
							LocaleUtil.SPAIN,
							StringPool.SLASH + RandomTestUtil.randomString());
					}
				});

			String privateUrl = _getPrivateURL(group);

			Assert.assertTrue(
				"PrivateURL " + privateUrl,
				privateUrl.endsWith(
					PropsValues.
						LAYOUT_FRIENDLY_URL_PRIVATE_GROUP_SERVLET_MAPPING +
							group.getFriendlyURL()));
		}
		finally {
			_groupLocalService.deleteGroup(group);
		}
	}

	@Test
	public void testGetPublicUrl() throws Throwable {
		Group group = GroupTestUtil.addGroup();

		try {
			LayoutTestUtil.addLayout(
				group.getGroupId(), false,
				new HashMap<Locale, String>() {
					{
						put(LocaleUtil.US, RandomTestUtil.randomString());
					}
				},
				new HashMap<Locale, String>() {
					{
						put(
							LocaleUtil.US,
							StringPool.SLASH + RandomTestUtil.randomString());
					}
				});

			String publicUrl = _getPublicURL(group);

			Assert.assertTrue(
				publicUrl.endsWith(
					PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING +
						group.getFriendlyURL()));
		}
		finally {
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

	private Group _getGroup(long groupId) throws Exception {
		Class<? extends CollectionResource> clazz =
			_collectionResource.getClass();

		Method method = clazz.getDeclaredMethod("_getGroup", long.class);

		method.setAccessible(true);

		return (Group)method.invoke(_collectionResource, groupId);
	}

	private PageItems<Group> _getPageItems(
			Pagination pagination, Company company)
		throws Exception {

		Class<? extends CollectionResource> clazz =
			_collectionResource.getClass();

		Method method = clazz.getDeclaredMethod(
			"_getPageItems", Pagination.class, Company.class);

		method.setAccessible(true);

		return (PageItems)method.invoke(
			_collectionResource, pagination, company);
	}

	private String _getPrivateURL(Group group) throws Exception {
		Class<? extends CollectionResource> clazz =
			_collectionResource.getClass();

		Method method = clazz.getDeclaredMethod("_getPrivateURL", Group.class);

		method.setAccessible(true);

		return (String)method.invoke(_collectionResource, group);
	}

	private String _getPublicURL(Group group) throws Exception {
		Class<? extends CollectionResource> clazz =
			_collectionResource.getClass();

		Method method = clazz.getDeclaredMethod("_getPublicURL", Group.class);

		method.setAccessible(true);

		return (String)method.invoke(_collectionResource, group);
	}

	@Inject(
		filter = "component.name=com.liferay.site.apio.internal.architect.resource.WebSiteCollectionResource"
	)
	private CollectionResource _collectionResource;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private GroupLocalService _groupLocalService;

}