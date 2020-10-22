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

package com.liferay.layout.page.template.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryService;
import com.liferay.layout.page.template.service.test.util.DisplayPageTemplateTestUtil;
import com.liferay.portal.kernel.exception.NoSuchClassNameException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Kyle Miho
 */
@RunWith(Arquillian.class)
public class DisplayPageTemplateServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testAddDisplayPageTemplate() throws PortalException {
		String name = RandomTestUtil.randomString();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		LayoutPageTemplateEntry displayPageTemplate =
			_layoutPageTemplateEntryService.addLayoutPageTemplateEntry(
				_group.getGroupId(), 0, name,
				LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE,
				WorkflowConstants.STATUS_DRAFT, serviceContext);

		LayoutPageTemplateEntry persistedDisplayPageTemplate =
			_layoutPageTemplateEntryService.fetchLayoutPageTemplateEntry(
				displayPageTemplate.getLayoutPageTemplateEntryId());

		Assert.assertEquals(name, persistedDisplayPageTemplate.getName());
	}

	@Test(expected = NoSuchClassNameException.class)
	public void testAddDisplayPageWithInvalidClassNameId()
		throws PortalException {

		_createDisplayPageEntry(0, RandomTestUtil.randomLong());
	}

	@Test
	public void testDeleteDisplayPageTemplate() throws PortalException {
		LayoutPageTemplateEntry displayPageTemplate =
			DisplayPageTemplateTestUtil.addDisplayPageTemplate(
				_group.getGroupId());

		_layoutPageTemplateEntryService.deleteLayoutPageTemplateEntry(
			displayPageTemplate.getLayoutPageTemplateEntryId());

		Assert.assertNull(
			_layoutPageTemplateEntryService.fetchLayoutPageTemplateEntry(
				displayPageTemplate.getLayoutPageTemplateEntryId()));
	}

	private LayoutPageTemplateEntry _createDisplayPageEntry(
			long classNameId, long classTypeId)
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		return _layoutPageTemplateEntryService.addLayoutPageTemplateEntry(
			_group.getGroupId(), 0, RandomTestUtil.randomString(),
			WorkflowConstants.STATUS_DRAFT, classNameId, classTypeId,
			serviceContext);
	}

	@Inject
	private static LayoutPageTemplateEntryService
		_layoutPageTemplateEntryService;

	@DeleteAfterTestRun
	private Group _group;

}