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

package com.liferay.content.dashboard.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author David Arques
 */
@RunWith(Arquillian.class)
@Sync
public class UpdateContentDashboardConfigurationMVCActionCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testProcessAction() throws PortletException {
		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			new MockLiferayPortletActionRequest();

		String[] assetVocabularyIds = {"vocabulary1", "vocabulary2"};

		mockLiferayPortletActionRequest.setParameter(
			"assetVocabularyIds", StringUtil.merge(assetVocabularyIds));

		_mvcActionCommand.processAction(
			mockLiferayPortletActionRequest,
			new MockLiferayPortletActionResponse());

		PortletPreferences portletPreferences =
			mockLiferayPortletActionRequest.getPreferences();

		Assert.assertArrayEquals(
			assetVocabularyIds,
			portletPreferences.getValues("assetVocabularyIds", new String[0]));

		Assert.assertNull(
			SessionMessages.get(
				mockLiferayPortletActionRequest, "emptyAssetVocabularyIds"));
	}

	@Test
	public void testProcessActionWithEmptyAssetVocabularyIdsDuringInitialization()
		throws PortletException {

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			new MockLiferayPortletActionRequest();

		mockLiferayPortletActionRequest.setParameter(
			"assetVocabularyIds", new String[0]);

		_mvcActionCommand.processAction(
			mockLiferayPortletActionRequest,
			new MockLiferayPortletActionResponse());

		PortletPreferences portletPreferences =
			mockLiferayPortletActionRequest.getPreferences();

		Assert.assertArrayEquals(
			new String[0],
			portletPreferences.getValues("assetVocabularyIds", new String[0]));

		Object emptyAssetVocabularyIds = SessionMessages.get(
			mockLiferayPortletActionRequest, "emptyAssetVocabularyIds");

		Assert.assertNotNull(emptyAssetVocabularyIds);
		Assert.assertTrue((Boolean)emptyAssetVocabularyIds);
	}

	@Inject(
		filter = "mvc.command.name=/content_dashboard/update_content_dashboard_configuration",
		type = MVCActionCommand.class
	)
	private MVCActionCommand _mvcActionCommand;

}