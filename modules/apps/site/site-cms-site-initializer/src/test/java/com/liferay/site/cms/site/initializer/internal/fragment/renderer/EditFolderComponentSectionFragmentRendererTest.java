/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.fragment.renderer;

import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public class EditFolderComponentSectionFragmentRendererTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		MockitoAnnotations.openMocks(this);

		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(_portal);
	}

	@Test
	public void testGetProps() {
		String redirect = "javascript:alert(1)";

		String escapedRedirect = RandomTestUtil.randomString();

		Mockito.when(
			_portal.escapeRedirect(redirect)
		).thenReturn(
			escapedRedirect
		);

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setParameter("redirect", redirect);

		Map<String, Object> props =
			_editFolderComponentSectionFragmentRenderer.getProps(
				null, mockHttpServletRequest);

		Assert.assertEquals(escapedRedirect, props.get("backURL"));
	}

	private final EditFolderComponentSectionFragmentRenderer
		_editFolderComponentSectionFragmentRenderer =
			new EditFolderComponentSectionFragmentRenderer();

	@Mock
	private Portal _portal;

}