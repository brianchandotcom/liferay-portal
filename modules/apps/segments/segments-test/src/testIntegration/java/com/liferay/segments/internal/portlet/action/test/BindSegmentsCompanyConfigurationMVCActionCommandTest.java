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
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.segments.configuration.SegmentsCompanyConfiguration;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

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
		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			new MockLiferayPortletActionRequest();

		MockLiferayPortletActionResponse mockLiferayPortletActionResponse =
			new MockLiferayPortletActionResponse();

		mockLiferayPortletActionRequest.setParameter(
			"roleSegmentationEnabled", "off");

		_mvcActionCommand.processAction(
			mockLiferayPortletActionRequest, mockLiferayPortletActionResponse);

		SegmentsCompanyConfiguration segmentsCompanyConfiguration =
			_configurationProvider.getCompanyConfiguration(
				SegmentsCompanyConfiguration.class,
				TestPropsValues.getCompanyId());

		Assert.assertFalse(
			segmentsCompanyConfiguration.roleSegmentationEnabled());
	}

	@Test
	public void testProcessActionRoleSegmentationEnabled() throws Exception {
		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			new MockLiferayPortletActionRequest();

		MockLiferayPortletActionResponse mockLiferayPortletActionResponse =
			new MockLiferayPortletActionResponse();

		mockLiferayPortletActionRequest.setParameter(
			"roleSegmentationEnabled", "on");

		_mvcActionCommand.processAction(
			mockLiferayPortletActionRequest, mockLiferayPortletActionResponse);

		SegmentsCompanyConfiguration segmentsCompanyConfiguration =
			_configurationProvider.getCompanyConfiguration(
				SegmentsCompanyConfiguration.class,
				TestPropsValues.getCompanyId());

		Assert.assertTrue(
			segmentsCompanyConfiguration.roleSegmentationEnabled());
	}

	@Test
	public void testProcessActionSegmentationDisabled() throws Exception {
		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			new MockLiferayPortletActionRequest();

		MockLiferayPortletActionResponse mockLiferayPortletActionResponse =
			new MockLiferayPortletActionResponse();

		mockLiferayPortletActionRequest.setParameter(
			"segmentationEnabled", "off");

		_mvcActionCommand.processAction(
			mockLiferayPortletActionRequest, mockLiferayPortletActionResponse);

		SegmentsCompanyConfiguration segmentsCompanyConfiguration =
			_configurationProvider.getCompanyConfiguration(
				SegmentsCompanyConfiguration.class,
				TestPropsValues.getCompanyId());

		Assert.assertFalse(segmentsCompanyConfiguration.segmentationEnabled());
	}

	@Test
	public void testProcessActionSegmentationEnabled() throws Exception {
		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			new MockLiferayPortletActionRequest();

		MockLiferayPortletActionResponse mockLiferayPortletActionResponse =
			new MockLiferayPortletActionResponse();

		mockLiferayPortletActionRequest.setParameter(
			"segmentationEnabled", "on");

		_mvcActionCommand.processAction(
			mockLiferayPortletActionRequest, mockLiferayPortletActionResponse);

		SegmentsCompanyConfiguration segmentsCompanyConfiguration =
			_configurationProvider.getCompanyConfiguration(
				SegmentsCompanyConfiguration.class,
				TestPropsValues.getCompanyId());

		Assert.assertTrue(segmentsCompanyConfiguration.segmentationEnabled());
	}

	@Inject
	private ConfigurationProvider _configurationProvider;

	@Inject(
		filter = "mvc.command.name=/instance_settings/bind_segments_company_configuration",
		type = MVCActionCommand.class
	)
	private MVCActionCommand _mvcActionCommand;

}