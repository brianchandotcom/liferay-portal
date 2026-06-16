/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.content.security.policy.internal.servlet.filter;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.security.content.security.policy.internal.configuration.ContentSecurityPolicyConfiguration;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import jakarta.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Antonio Ortega
 */
public class ContentSecurityPolicyFilterTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_contentSecurityPolicyConfiguration = Mockito.mock(
			ContentSecurityPolicyConfiguration.class);

		Mockito.when(
			_contentSecurityPolicyConfiguration.excludedPaths()
		).thenReturn(
			new String[] {"/group"}
		);

		_contentSecurityPolicyFilter = new ContentSecurityPolicyFilter();
	}

	@Test
	public void testIsExcludedURIPath() {
		_testIsExcludedURIPath(false, null, "/c/portal/layout");
		_testIsExcludedURIPath(true, "/group/guest/home", "/c/portal/layout");
		_testIsExcludedURIPath(true, null, "/group/guest/home");
	}

	@Test
	public void testIsLayoutEditMode() {
		_testIsLayoutEditMode(false, null, "test");
		_testIsLayoutEditMode(false, "view", "test");
		_testIsLayoutEditMode(false, Constants.EDIT, null);
		_testIsLayoutEditMode(true, Constants.EDIT, "test");
	}

	private void _testIsExcludedURIPath(
		boolean excludedURIPath, String forwardRequestURI, String requestURI) {

		HttpServletRequest httpServletRequest = Mockito.mock(
			HttpServletRequest.class);

		Mockito.when(
			httpServletRequest.getAttribute(
				JavaConstants.JAKARTA_SERVLET_FORWARD_REQUEST_URI)
		).thenReturn(
			forwardRequestURI
		);

		Mockito.when(
			httpServletRequest.getRequestURI()
		).thenReturn(
			requestURI
		);

		Assert.assertEquals(
			excludedURIPath,
			ReflectionTestUtil.invoke(
				_contentSecurityPolicyFilter, "_isExcludedURIPath",
				new Class<?>[] {
					ContentSecurityPolicyConfiguration.class,
					HttpServletRequest.class
				},
				_contentSecurityPolicyConfiguration, httpServletRequest));
	}

	private void _testIsLayoutEditMode(
		boolean layoutEditMode, String layoutMode, String remoteUser) {

		HttpServletRequest httpServletRequest = Mockito.mock(
			HttpServletRequest.class);

		Mockito.when(
			httpServletRequest.getParameter("p_l_mode")
		).thenReturn(
			layoutMode
		);

		Mockito.when(
			httpServletRequest.getRemoteUser()
		).thenReturn(
			remoteUser
		);

		Assert.assertEquals(
			layoutEditMode,
			ReflectionTestUtil.invoke(
				_contentSecurityPolicyFilter, "_isLayoutEditMode",
				new Class<?>[] {HttpServletRequest.class}, httpServletRequest));
	}

	private ContentSecurityPolicyConfiguration
		_contentSecurityPolicyConfiguration;
	private ContentSecurityPolicyFilter _contentSecurityPolicyFilter;

}