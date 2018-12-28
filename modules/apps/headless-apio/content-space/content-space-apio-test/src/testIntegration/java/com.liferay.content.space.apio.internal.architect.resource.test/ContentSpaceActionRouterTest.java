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

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
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

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@After
	public void tearDown() throws Exception {
		_groupLocalService.deleteGroup(_group);
	}

	@Test
	public void testGetContentSpace() throws Throwable {
		ContentSpace contentSpace = getContentSpace(_group.getGroupId());

		Assert.assertNotNull(contentSpace);

		Assert.assertEquals(
			Long.valueOf(_group.getCreatorUserId()),
			contentSpace.getCreatorId());

		Assert.assertEquals(
			Long.valueOf(_group.getGroupId()),
			contentSpace.getDocumentsRepositoryId());

		Assert.assertEquals(_group.getGroupId(), contentSpace.getId());

		Locale locale = LocaleUtil.fromLanguageId(
			_group.getDefaultLanguageId());

		Assert.assertEquals(
			_group.getDescription(locale), contentSpace.getDescription(locale));

		Assert.assertEquals(
			_group.getName(locale), contentSpace.getName(locale));

		List<String> availableLanguages = contentSpace.getAvailableLanguages();

		Assert.assertEquals(
			availableLanguages.toString(), 1, availableLanguages.size());

		Assert.assertEquals(
			LocaleUtil.toW3cLanguageId(_group.getDefaultLanguageId()),
			availableLanguages.get(0));
	}

	@Test
	public void testGetPageItemsWithActiveGroup() throws Throwable {
		Company company = _companyLocalService.getCompany(
			_group.getCompanyId());

		PageItems<ContentSpace> contentSpacePageItems = getPageItems(
			PaginationRequest.of(50, 1), company);

		ContentSpace contentSpace = _findContentSpace(
			(List<ContentSpace>)contentSpacePageItems.getItems(),
			_group.getGroupId());

		Assert.assertNotNull(contentSpace);
	}

	@Test
	public void testGetPageItemsWithInactiveGroup() throws Throwable {
		_deactivateGroup(_group);

		Company company = _companyLocalService.getCompany(
			_group.getCompanyId());

		PageItems<ContentSpace> contentSpacePageItems = getPageItems(
			PaginationRequest.of(50, 1), company);

		ContentSpace contentSpace = _findContentSpace(
			(List<ContentSpace>)contentSpacePageItems.getItems(),
			_group.getGroupId());

		Assert.assertNull(contentSpace);
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

	private Group _group;

	@Inject
	private GroupLocalService _groupLocalService;

}