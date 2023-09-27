/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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