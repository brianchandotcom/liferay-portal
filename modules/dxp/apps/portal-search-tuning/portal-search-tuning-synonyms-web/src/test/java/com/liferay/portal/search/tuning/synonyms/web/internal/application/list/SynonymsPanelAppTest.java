/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.search.tuning.synonyms.web.internal.application.list;

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
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
	}

	private final PortletLocalService _portletLocalService = Mockito.mock(
		PortletLocalService.class);
	private SynonymsPanelApp _synonymsPanelApp;

}