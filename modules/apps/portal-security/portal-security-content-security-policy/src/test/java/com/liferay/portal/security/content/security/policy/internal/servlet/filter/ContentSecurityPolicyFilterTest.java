/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.content.security.policy.internal.servlet.filter;

import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.service.permission.LayoutPermission;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
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

		_portal = Mockito.mock(Portal.class);

		Mockito.when(
			_portal.getPathFriendlyURLPublic()
		).thenReturn(
			"/web"
		);

		Mockito.when(
			_portal.getPathFriendlyURLPrivateGroup()
		).thenReturn(
			"/group"
		);

		Mockito.when(
			_portal.getPathFriendlyURLPrivateUser()
		).thenReturn(
			"/user"
		);

		_layoutPermission = Mockito.mock(LayoutPermission.class);
		_permissionCheckerFactory = Mockito.mock(
			PermissionCheckerFactory.class);

		_contentSecurityPolicyFilter = new ContentSecurityPolicyFilter();

		ReflectionTestUtil.setFieldValue(
			_contentSecurityPolicyFilter, "_layoutPermission",
			_layoutPermission);
		ReflectionTestUtil.setFieldValue(
			_contentSecurityPolicyFilter, "_permissionCheckerFactory",
			_permissionCheckerFactory);
		ReflectionTestUtil.setFieldValue(
			_contentSecurityPolicyFilter, "_portal", _portal);
	}

	@Test
	public void testIsExcludedLayoutEditMode() throws Exception {

		// Not edit mode

		_testIsExcludedLayoutEditMode(
			false, RandomTestUtil.randomString(), Mockito.mock(Layout.class),
			true, true);

		// Edit mode, layout the user can update

		_testIsExcludedLayoutEditMode(
			true, Constants.EDIT, Mockito.mock(Layout.class), true, true);

		// Edit mode, layout the user cannot update

		_testIsExcludedLayoutEditMode(
			false, Constants.EDIT, Mockito.mock(Layout.class), false, true);

		// Edit mode, no authenticated user

		_testIsExcludedLayoutEditMode(
			false, Constants.EDIT, Mockito.mock(Layout.class), true, false);

		// Edit mode, unresolvable friendly URL (defer to forward dispatch)

		_testIsExcludedLayoutEditModeUnresolved(
			true, "/" + RandomTestUtil.randomString());

		// Edit mode, friendly URL format but no matching layout (enforce)

		_testIsExcludedLayoutEditModeUnresolved(false, "/web/guest/home");
	}

	@Test
	public void testIsExcludedURIPath() {
		_testIsExcludedURIPath(false, null, "/c/portal/layout");
		_testIsExcludedURIPath(true, "/group/guest/home", "/c/portal/layout");
		_testIsExcludedURIPath(true, null, "/group/guest/home");
	}

	private void _testIsExcludedLayoutEditMode(
			boolean expected, String layoutMode, Layout layout,
			boolean hasUpdatePermission, boolean hasUser)
		throws Exception {

		HttpServletRequest httpServletRequest = Mockito.mock(
			HttpServletRequest.class);

		Mockito.when(
			httpServletRequest.getParameter("p_l_mode")
		).thenReturn(
			layoutMode
		);

		Mockito.when(
			httpServletRequest.getAttribute(WebKeys.LAYOUT)
		).thenReturn(
			layout
		);

		User user = null;

		if (hasUser) {
			user = Mockito.mock(User.class);
		}

		Mockito.when(
			_portal.getUser(httpServletRequest)
		).thenReturn(
			user
		);

		PermissionChecker permissionChecker = Mockito.mock(
			PermissionChecker.class);

		if (user != null) {
			Mockito.when(
				_permissionCheckerFactory.create(user)
			).thenReturn(
				permissionChecker
			);
		}

		Mockito.when(
			_layoutPermission.containsLayoutUpdatePermission(
				permissionChecker, layout)
		).thenReturn(
			hasUpdatePermission
		);

		Assert.assertEquals(
			expected,
			ReflectionTestUtil.invoke(
				_contentSecurityPolicyFilter, "_isExcludedLayoutEditMode",
				new Class<?>[] {HttpServletRequest.class}, httpServletRequest));
	}

	private void _testIsExcludedLayoutEditModeUnresolved(
			boolean expected, String requestURI)
		throws Exception {

		HttpServletRequest httpServletRequest = Mockito.mock(
			HttpServletRequest.class);

		Mockito.when(
			httpServletRequest.getParameter("p_l_mode")
		).thenReturn(
			Constants.EDIT
		);

		Mockito.when(
			httpServletRequest.getRequestURI()
		).thenReturn(
			requestURI
		);

		Assert.assertEquals(
			expected,
			ReflectionTestUtil.invoke(
				_contentSecurityPolicyFilter, "_isExcludedLayoutEditMode",
				new Class<?>[] {HttpServletRequest.class}, httpServletRequest));
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

	private ContentSecurityPolicyConfiguration
		_contentSecurityPolicyConfiguration;
	private ContentSecurityPolicyFilter _contentSecurityPolicyFilter;
	private LayoutPermission _layoutPermission;
	private PermissionCheckerFactory _permissionCheckerFactory;
	private Portal _portal;

}