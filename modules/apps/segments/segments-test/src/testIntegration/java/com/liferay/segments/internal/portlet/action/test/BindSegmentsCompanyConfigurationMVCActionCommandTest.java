/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.configuration.test.util.CompanyConfigurationTemporarySwapper;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.segments.configuration.SegmentsCompanyConfiguration;

import java.io.IOException;

import java.util.Dictionary;
import java.util.Hashtable;

import org.junit.Assert;
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

	@Test
	public void testProcessActionRoleSegmentationDisabled() throws Exception {
		try (CompanyConfigurationTemporarySwapper
				companyConfigurationTemporarySwapper =
					new CompanyConfigurationTemporarySwapper(
						TestPropsValues.getCompanyId(),
						SegmentsCompanyConfiguration.class.getName(),
						new Hashtable<>())) {

			MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
				new MockLiferayPortletActionRequest();

			mockLiferayPortletActionRequest.setParameter(
				"roleSegmentationEnabled", "off");

			_mvcActionCommand.processAction(
				mockLiferayPortletActionRequest,
				new MockLiferayPortletActionResponse());

			Configuration configuration = _getSegmentsCompanyConfiguration(
				TestPropsValues.getCompanyId());

			Dictionary<String, Object> properties =
				configuration.getProperties();

			Assert.assertFalse(
				GetterUtil.getBoolean(
					properties.get("roleSegmentationEnabled")));
		}
	}

	@Test
	public void testProcessActionRoleSegmentationEnabled() throws Exception {
		try (CompanyConfigurationTemporarySwapper
				companyConfigurationTemporarySwapper =
					new CompanyConfigurationTemporarySwapper(
						TestPropsValues.getCompanyId(),
						SegmentsCompanyConfiguration.class.getName(),
						new Hashtable<>())) {

			MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
				new MockLiferayPortletActionRequest();

			mockLiferayPortletActionRequest.setParameter(
				"roleSegmentationEnabled", "on");

			_mvcActionCommand.processAction(
				mockLiferayPortletActionRequest,
				new MockLiferayPortletActionResponse());

			Configuration configuration = _getSegmentsCompanyConfiguration(
				TestPropsValues.getCompanyId());

			Dictionary<String, Object> properties =
				configuration.getProperties();

			Assert.assertTrue(
				GetterUtil.getBoolean(
					properties.get("roleSegmentationEnabled")));
		}
	}

	@Test
	public void testProcessActionSegmentationDisabled() throws Exception {
		try (CompanyConfigurationTemporarySwapper
				companyConfigurationTemporarySwapper =
					new CompanyConfigurationTemporarySwapper(
						TestPropsValues.getCompanyId(),
						SegmentsCompanyConfiguration.class.getName(),
						new Hashtable<>())) {

			MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
				new MockLiferayPortletActionRequest();

			mockLiferayPortletActionRequest.setParameter(
				"segmentationEnabled", "off");

			_mvcActionCommand.processAction(
				mockLiferayPortletActionRequest,
				new MockLiferayPortletActionResponse());

			Configuration configuration = _getSegmentsCompanyConfiguration(
				TestPropsValues.getCompanyId());

			Dictionary<String, Object> properties =
				configuration.getProperties();

			Assert.assertFalse(
				GetterUtil.getBoolean(properties.get("segmentationEnabled")));
		}
	}

	@Test
	public void testProcessActionSegmentationEnabled() throws Exception {
		try (CompanyConfigurationTemporarySwapper
				companyConfigurationTemporarySwapper =
					new CompanyConfigurationTemporarySwapper(
						TestPropsValues.getCompanyId(),
						SegmentsCompanyConfiguration.class.getName(),
						new Hashtable<>())) {

			MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
				new MockLiferayPortletActionRequest();

			mockLiferayPortletActionRequest.setParameter(
				"segmentationEnabled", "on");

			_mvcActionCommand.processAction(
				mockLiferayPortletActionRequest,
				new MockLiferayPortletActionResponse());

			Configuration configuration = _getSegmentsCompanyConfiguration(
				TestPropsValues.getCompanyId());

			Dictionary<String, Object> properties =
				configuration.getProperties();

			Assert.assertTrue(
				GetterUtil.getBoolean(properties.get("segmentationEnabled")));
		}
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