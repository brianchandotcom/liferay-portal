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

package com.liferay.layout.reports.web.internal.configuration.admin.display.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.configuration.admin.display.ConfigurationVisibilityController;
import com.liferay.layout.reports.web.internal.util.LayoutReportsTestUtil;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Cristina González
 */
@RunWith(Arquillian.class)
public class
	LayoutReportsGooglePageSpeedGroupConfigurationVisibilityControllerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(), 0);
	}

	@Test
	public void testIsVisibleWithCompanyScopeAndDisabledCompanyConfiguration()
		throws Exception {

		LayoutReportsTestUtil.
			withLayoutReportsGooglePageSpeedCompanyConfiguration(
				_group.getCompanyId(), false,
				() -> Assert.assertFalse(
					_configurationVisibilityController.isVisible(
						ExtendedObjectClassDefinition.Scope.COMPANY,
						_group.getCompanyId())));
	}

	@Test
	public void testIsVisibleWithCompanyScopeAndEnabledCompanyConfiguration()
		throws Exception {

		LayoutReportsTestUtil.
			withLayoutReportsGooglePageSpeedCompanyConfiguration(
				_group.getCompanyId(), true,
				() -> Assert.assertTrue(
					_configurationVisibilityController.isVisible(
						ExtendedObjectClassDefinition.Scope.COMPANY,
						_group.getCompanyId())));
	}

	@Test
	public void testIsVisibleWithSystemScopeAndDisabledCompanyConfiguration()
		throws Exception {

		LayoutReportsTestUtil.
			withLayoutReportsGooglePageSpeedCompanyConfiguration(
				TestPropsValues.getCompanyId(), false,
				() -> Assert.assertFalse(
					_configurationVisibilityController.isVisible(
						ExtendedObjectClassDefinition.Scope.SYSTEM, null)));
	}

	@Test
	public void testIsVisibleWithSystemScopeAndEnabledCompanyConfiguration()
		throws Exception {

		LayoutReportsTestUtil.
			withLayoutReportsGooglePageSpeedCompanyConfiguration(
				TestPropsValues.getCompanyId(), true,
				() -> Assert.assertTrue(
					_configurationVisibilityController.isVisible(
						ExtendedObjectClassDefinition.Scope.SYSTEM, null)));
	}

	@Inject(
		filter = "component.name=com.liferay.layout.reports.web.internal.configuration.admin.display.LayoutReportsGooglePageSpeedGroupConfigurationVisibilityController"
	)
	private ConfigurationVisibilityController
		_configurationVisibilityController;

	@DeleteAfterTestRun
	private Group _group;

}