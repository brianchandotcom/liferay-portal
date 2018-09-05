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

package com.liferay.blogs.internal.exportimport.portlet.preferences.processor.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.blogs.constants.BlogsPortletKeys;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.portlet.preferences.processor.ExportImportPortletPreferencesProcessor;
import com.liferay.exportimport.test.util.ExportImportTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.test.LayoutTestUtil;
import com.liferay.registry.Filter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

import java.util.HashMap;

import javax.portlet.PortletPreferences;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Mate Thurzo
 */
@RunWith(Arquillian.class)
public class BlogsAggregatorExportImportPortletPreferencesProcessorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() {
		Registry registry = RegistryUtil.getRegistry();

		StringBundler sb = new StringBundler(5);

		sb.append("(&(objectClass=");
		sb.append(ExportImportPortletPreferencesProcessor.class.getName());
		sb.append(")(javax.portlet.name=");
		sb.append(BlogsPortletKeys.BLOGS_AGGREGATOR);
		sb.append("))");

		Filter filter = registry.getFilter(sb.toString());

		_serviceTracker = registry.trackServices(filter);

		_serviceTracker.open();
	}

	@AfterClass
	public static void tearDownClass() {
		_serviceTracker.close();
	}

	@Before
	public void setUp() throws Exception {
		ServiceTestUtil.setUser(TestPropsValues.getUser());

		_group = GroupTestUtil.addGroup();

		_layout = LayoutTestUtil.addLayout(_group.getGroupId());

		LayoutTestUtil.addPortletToLayout(
			TestPropsValues.getUserId(), _layout,
			BlogsPortletKeys.BLOGS_AGGREGATOR, "column-1",
			new HashMap<String, String[]>());

		_organization = OrganizationTestUtil.addOrganization();

		_portletDataContextExport =
			ExportImportTestUtil.getExportPortletDataContext(
				_group.getGroupId());

		_portletDataContextExport.setPortletId(
			BlogsPortletKeys.BLOGS_AGGREGATOR);

		_portletDataContextImport =
			ExportImportTestUtil.getImportPortletDataContext(
				_group.getGroupId());

		_portletDataContextImport.setPortletId(
			BlogsPortletKeys.BLOGS_AGGREGATOR);
	}

	@Test
	public void testProcessOrganizationId() throws Exception {
		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.getStrictPortletSetup(
				_layout, BlogsPortletKeys.BLOGS_AGGREGATOR);

		long organizationId = _organization.getOrganizationId();

		portletPreferences.setValue(
			"organizationId", String.valueOf(organizationId));

		portletPreferences.store();

		ExportImportPortletPreferencesProcessor
			blogsAggregatorPortletPreferencesProcessor =
				_serviceTracker.getService();

		PortletPreferences exportedPortletPreferences =
			blogsAggregatorPortletPreferencesProcessor.
				processExportPortletPreferences(
					_portletDataContextExport, portletPreferences);

		String exportedOrganizationId = exportedPortletPreferences.getValue(
			"organizationId", "");

		Assert.assertEquals(_organization.getUuid(), exportedOrganizationId);

		// Update organization to have a different primary key. We will swap
		// to the new one and verify it.

		OrganizationLocalServiceUtil.deleteOrganization(
			_organization.getOrganizationId());

		_organization = OrganizationTestUtil.addOrganization();

		_organization.setUuid(exportedOrganizationId);

		OrganizationLocalServiceUtil.updateOrganization(_organization);

		// Test the import

		PortletPreferences importedPortletPreferences =
			blogsAggregatorPortletPreferencesProcessor.
				processImportPortletPreferences(
					_portletDataContextImport, exportedPortletPreferences);

		String importedOrganizationId = importedPortletPreferences.getValue(
			"organizationId", "");

		Assert.assertEquals(
			_organization.getOrganizationId(),
			GetterUtil.getLong(importedOrganizationId));
	}

	private static ServiceTracker
		<ExportImportPortletPreferencesProcessor,
		 ExportImportPortletPreferencesProcessor> _serviceTracker;

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;

	@DeleteAfterTestRun
	private Organization _organization;

	private PortletDataContext _portletDataContextExport;
	private PortletDataContext _portletDataContextImport;

}