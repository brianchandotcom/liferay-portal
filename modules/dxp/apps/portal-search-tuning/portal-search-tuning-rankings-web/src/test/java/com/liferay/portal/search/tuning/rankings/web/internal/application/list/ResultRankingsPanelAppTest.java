/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.rankings.web.internal.application.list;

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.engine.SearchEngineInformation;
import com.liferay.portal.search.tuning.rankings.web.internal.constants.ResultRankingsPortletKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Wade Cao
 */
public class ResultRankingsPanelAppTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_resultRankingsPanelApp = new ResultRankingsPanelApp();

		ReflectionTestUtil.setFieldValue(
			_resultRankingsPanelApp, "_portletLocalService",
			_portletLocalService);
		ReflectionTestUtil.setFieldValue(
			_resultRankingsPanelApp, "searchEngineInformation",
			_searchEngineInformation);
	}

	@Test
	public void testGetPortletId() {
		Assert.assertEquals(
			ResultRankingsPortletKeys.RESULT_RANKINGS,
			_resultRankingsPanelApp.getPortletId());
	}

	@Test
	public void testIsShow() throws Exception {
		Portlet portlet = Mockito.mock(Portlet.class);

		Mockito.doReturn(
			false
		).when(
			portlet
		).isActive();

		Mockito.doReturn(
			portlet
		).when(
			_portletLocalService
		).getPortletById(
			Mockito.anyLong(), Mockito.anyString()
		);

		Assert.assertFalse(
			_resultRankingsPanelApp.isShow(
				Mockito.mock(PermissionChecker.class),
				Mockito.mock(Group.class)));

		Mockito.doReturn(
			true
		).when(
			portlet
		).isActive();

		Assert.assertTrue(
			_resultRankingsPanelApp.isShow(
				Mockito.mock(PermissionChecker.class),
				Mockito.mock(Group.class)));

		Mockito.doReturn(
			"Solr"
		).when(
			_searchEngineInformation
		).getVendorString();

		Assert.assertFalse(
			_resultRankingsPanelApp.isShow(
				Mockito.mock(PermissionChecker.class),
				Mockito.mock(Group.class)));
	}

	private final PortletLocalService _portletLocalService = Mockito.mock(
		PortletLocalService.class);
	private ResultRankingsPanelApp _resultRankingsPanelApp;
	private final SearchEngineInformation _searchEngineInformation =
		Mockito.mock(SearchEngineInformation.class);

}