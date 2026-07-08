/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order.content.web.internal.template;

import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Shuyang Zhou
 */
public class CommerceOrderHttpHelperTemplateContextContributorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {

		// FeatureFlagManagerUtil serializes the feature flags to JSON when
		// first loaded, so wire the JSON factory before it is touched.

		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());
	}

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testPrepareWithHttpServletRequest() {
		Mockito.when(
			_portal.getCompanyId(_mockHttpServletRequest)
		).thenReturn(
			_COMPANY_ID
		);

		Map<String, Object> contextObjects = new HashMap<>();

		_commerceOrderHttpHelperTemplateContextContributor.prepare(
			contextObjects, _mockHttpServletRequest);

		Assert.assertTrue(
			contextObjects.toString(),
			contextObjects.containsKey("commerceReturnsEnabled"));

		Mockito.verify(
			_portal
		).getCompanyId(
			_mockHttpServletRequest
		);
	}

	@Test
	public void testPrepareWithoutHttpServletRequest() {

		// A notification such as an object action email prepares the template
		// outside of an HTTP request, so EmailNotificationType passes a null
		// request. Portal.getCompanyId(null) throws a NullPointerException
		// because it dereferences the request, so the company must be resolved
		// from CompanyThreadLocal instead.

		Mockito.lenient(
		).when(
			_portal.getCompanyId((HttpServletRequest)null)
		).thenThrow(
			new NullPointerException()
		);

		Map<String, Object> contextObjects = new HashMap<>();

		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setCompanyIdWithSafeCloseable(_COMPANY_ID)) {

			_commerceOrderHttpHelperTemplateContextContributor.prepare(
				contextObjects, null);
		}

		Assert.assertTrue(
			contextObjects.toString(),
			contextObjects.containsKey("commerceReturnsEnabled"));

		Mockito.verify(
			_portal, Mockito.never()
		).getCompanyId(
			(HttpServletRequest)null
		);
	}

	private static final long _COMPANY_ID = RandomTestUtil.randomLong();

	@InjectMocks
	private CommerceOrderHttpHelperTemplateContextContributor
		_commerceOrderHttpHelperTemplateContextContributor;

	private final MockHttpServletRequest _mockHttpServletRequest =
		new MockHttpServletRequest();

	@Mock
	private Portal _portal;

}