/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.synonyms.web.internal.application.list;

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.engine.SearchEngineInformation;
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
public class SynonymsPanelAppTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_synonymsPanelApp = new SynonymsPanelApp();

		ReflectionTestUtil.setFieldValue(
			_synonymsPanelApp, "_portletLocalService", _portletLocalService);
		ReflectionTestUtil.setFieldValue(
			_synonymsPanelApp, "searchEngineInformation",
			_searchEngineInformation);
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
			_synonymsPanelApp.isShow(
				Mockito.mock(PermissionChecker.class),
				Mockito.mock(Group.class)));

		Mockito.doReturn(
			true
		).when(
			portlet
		).isActive();

		Assert.assertTrue(
			_synonymsPanelApp.isShow(
				Mockito.mock(PermissionChecker.class),
				Mockito.mock(Group.class)));

		Mockito.doReturn(
			"Solr"
		).when(
			_searchEngineInformation
		).getVendorString();

		Assert.assertFalse(
			_synonymsPanelApp.isShow(
				Mockito.mock(PermissionChecker.class),
				Mockito.mock(Group.class)));
	}

	private final PortletLocalService _portletLocalService = Mockito.mock(
		PortletLocalService.class);
	private final SearchEngineInformation _searchEngineInformation =
		Mockito.mock(SearchEngineInformation.class);
	private SynonymsPanelApp _synonymsPanelApp;

}