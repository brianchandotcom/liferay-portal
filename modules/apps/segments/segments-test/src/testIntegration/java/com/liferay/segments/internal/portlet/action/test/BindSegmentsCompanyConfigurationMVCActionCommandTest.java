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

package com.liferay.segments.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.segments.configuration.SegmentsCompanyConfiguration;

import java.io.IOException;

import java.util.Dictionary;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Yurena Cabrera
 */
@RunWith(Arquillian.class)
@Sync
public class BindSegmentsCompanyConfigurationMVCActionCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_company = CompanyTestUtil.addCompany();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_companyLocalService.deleteCompany(_company);
	}

	@Before
	public void setUp() throws Exception {
		_configurationProvider.deleteCompanyConfiguration(
			SegmentsCompanyConfiguration.class, _company.getCompanyId());
	}

	@Test
	public void testProcessActionRoleSegmentationDisabled() throws Exception {
		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			_getMockLiferayPortletActionRequest();

		MockLiferayPortletActionResponse mockLiferayPortletActionResponse =
			new MockLiferayPortletActionResponse();

		mockLiferayPortletActionRequest.setParameter(
			"roleSegmentationEnabled", "off");

		_mvcActionCommand.processAction(
			mockLiferayPortletActionRequest, mockLiferayPortletActionResponse);

		Configuration configuration = _getSegmentsCompanyConfiguration(
			_company.getCompanyId());

		Dictionary<String, Object> properties = configuration.getProperties();

		Assert.assertFalse(
			GetterUtil.getBoolean(properties.get("roleSegmentationEnabled")));
	}

	@Test
	public void testProcessActionRoleSegmentationEnabled() throws Exception {
		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			_getMockLiferayPortletActionRequest();

		MockLiferayPortletActionResponse mockLiferayPortletActionResponse =
			new MockLiferayPortletActionResponse();

		mockLiferayPortletActionRequest.setParameter(
			"roleSegmentationEnabled", "on");

		_mvcActionCommand.processAction(
			mockLiferayPortletActionRequest, mockLiferayPortletActionResponse);

		Configuration configuration = _getSegmentsCompanyConfiguration(
			_company.getCompanyId());

		Dictionary<String, Object> properties = configuration.getProperties();

		Assert.assertTrue(
			GetterUtil.getBoolean(properties.get("roleSegmentationEnabled")));
	}

	@Test
	public void testProcessActionSegmentationDisabled() throws Exception {
		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			_getMockLiferayPortletActionRequest();

		MockLiferayPortletActionResponse mockLiferayPortletActionResponse =
			new MockLiferayPortletActionResponse();

		mockLiferayPortletActionRequest.setParameter(
			"segmentationEnabled", "off");

		_mvcActionCommand.processAction(
			mockLiferayPortletActionRequest, mockLiferayPortletActionResponse);

		Configuration configuration = _getSegmentsCompanyConfiguration(
			_company.getCompanyId());

		Dictionary<String, Object> properties = configuration.getProperties();

		Assert.assertFalse(
			GetterUtil.getBoolean(properties.get("segmentationEnabled")));
	}

	@Test
	public void testProcessActionSegmentationEnabled() throws Exception {
		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			_getMockLiferayPortletActionRequest();

		MockLiferayPortletActionResponse mockLiferayPortletActionResponse =
			new MockLiferayPortletActionResponse();

		mockLiferayPortletActionRequest.setParameter(
			"segmentationEnabled", "on");

		_mvcActionCommand.processAction(
			mockLiferayPortletActionRequest, mockLiferayPortletActionResponse);

		Configuration configuration = _getSegmentsCompanyConfiguration(
			_company.getCompanyId());

		Dictionary<String, Object> properties = configuration.getProperties();

		Assert.assertTrue(
			GetterUtil.getBoolean(properties.get("segmentationEnabled")));
	}

	private MockLiferayPortletActionRequest
		_getMockLiferayPortletActionRequest() {

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			new MockLiferayPortletActionRequest();

		mockLiferayPortletActionRequest.setAttribute(
			WebKeys.COMPANY_ID, _company.getCompanyId());

		return mockLiferayPortletActionRequest;
	}

	private Configuration _getSegmentsCompanyConfiguration(long companyId)
		throws Exception {

		try {
			String filterString = StringBundler.concat(
				"(&(", ConfigurationAdmin.SERVICE_FACTORYPID, StringPool.EQUAL,
				SegmentsCompanyConfiguration.class.getName(), ".scoped",
				")(companyId=", companyId, "))");

			Configuration[] configuration =
				_configurationAdmin.listConfigurations(filterString);

			if (configuration != null) {
				return configuration[0];
			}

			return null;
		}
		catch (InvalidSyntaxException | IOException exception) {
			throw new ConfigurationException(exception);
		}
	}

	private static Company _company;

	@Inject
	private static CompanyLocalService _companyLocalService;

	@Inject
	private ConfigurationAdmin _configurationAdmin;

	@Inject
	private ConfigurationProvider _configurationProvider;

	@Inject(
		filter = "mvc.command.name=/instance_settings/bind_segments_company_configuration",
		type = MVCActionCommand.class
	)
	private MVCActionCommand _mvcActionCommand;

}