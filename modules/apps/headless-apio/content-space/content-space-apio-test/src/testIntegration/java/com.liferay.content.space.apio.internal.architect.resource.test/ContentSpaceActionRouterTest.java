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

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.router.ActionRouter;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.content.space.apio.architect.model.ContentSpace;
import com.liferay.portal.apio.test.util.PaginationRequest;
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
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Julio Camarero
 */
@RunWith(Arquillian.class)
public class ContentSpaceActionRouterTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerTestRule.INSTANCE);

	@Test
	public void testGetContentSpace() throws Throwable {
		Group group = GroupTestUtil.addGroup();

		try {
			ContentSpace contentSpace = getContentSpace(group.getGroupId());

			Assert.assertNotNull(contentSpace);

			Locale locale = LocaleUtil.fromLanguageId(
				group.getDefaultLanguageId());

			Assert.assertEquals(
				group.getName(locale), contentSpace.getName(locale));

			Assert.assertEquals(
				group.getDescription(locale),
				contentSpace.getDescription(locale));
		}
		finally {
			_groupLocalService.deleteGroup(group);
		}
	}

	@Test
	public void testGetPageItemsWithActiveGroup() throws Throwable {
		Group group = GroupTestUtil.addGroup();

		try {
			Company company = _companyLocalService.getCompany(
				group.getCompanyId());

			PageItems<ContentSpace> contentSpacePageItems = getPageItems(
				PaginationRequest.of(50, 1), company);

			ContentSpace contentSpace = _findContentSpace(
				(List<ContentSpace>)
					contentSpacePageItems.getItems(),
				group.getGroupId());

			Assert.assertNotNull(contentSpace);
		}
		finally {
			_groupLocalService.deleteGroup(group);
		}
	}

	@Test
	public void testGetPageItemsWithInactiveGroup() throws Throwable {
		Group group = GroupTestUtil.addGroup();

		try {
			_deactivateGroup(group);

			Company company = _companyLocalService.getCompany(
				group.getCompanyId());

			PageItems<ContentSpace> contentSpacePageItems = getPageItems(
				PaginationRequest.of(50, 1), company);

			ContentSpace contentSpace = _findContentSpace(
				(List<ContentSpace>)
					contentSpacePageItems.getItems(),
				group.getGroupId());

			Assert.assertNull(contentSpace);
		}
		finally {
			_groupLocalService.deleteGroup(group);
		}
	}

	protected ContentSpace getContentSpace(long contentSpaceId)
		throws Exception {

		Class<? extends ActionRouter> clazz = _actionRouter.getClass();

		Method method = clazz.getDeclaredMethod("getContentSpace", long.class);

		method.setAccessible(true);

		return (ContentSpace)method.invoke(_actionRouter, contentSpaceId);
	}

	protected PageItems<ContentSpace> getPageItems(
			Pagination pagination, Company company)
		throws Exception {

		Class<? extends ActionRouter> clazz = _actionRouter.getClass();

		Method method = clazz.getDeclaredMethod(
			"getPageItems", Pagination.class, Company.class);

		method.setAccessible(true);

		return (PageItems)method.invoke(_actionRouter, pagination, company);
	}

	private Group _deactivateGroup(Group group) throws PortalException {
		return _groupLocalService.updateGroup(
			group.getGroupId(), group.getParentGroupId(), group.getNameMap(),
			group.getDescriptionMap(), group.getType(),
			group.isManualMembership(), group.getMembershipRestriction(),
			group.getFriendlyURL(), group.isInheritContent(), false,
			new ServiceContext());
	}

	private ContentSpace _findContentSpace(
		List<ContentSpace> contentSpaces, long groupId) {

		Stream<ContentSpace> stream = contentSpaces.stream();

		return stream.filter(
			contentSpace -> Objects.equals(contentSpace.getId(), groupId)
		).findAny(
		).orElse(
			null
		);
	}

	@Inject(
		filter = "component.name=com.liferay.content.space.apio.internal.architect.resource.ContentSpaceActionRouter"
	)
	private ActionRouter _actionRouter;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private GroupLocalService _groupLocalService;

}