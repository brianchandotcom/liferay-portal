/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.admin.web.internal.util;

import com.liferay.layout.page.template.admin.constants.LayoutPageTemplateAdminPortletKeys;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Georgel Pop
 */
public class LayoutPageTemplatePortletUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_setUpPortalUtil();
	}

	@After
	public void tearDown() {
		_layoutPageTemplateCollectionLocalServiceUtilMockedStatic.close();
	}

	@Test
	@TestInfo({"LPD-104842", "LPD-104843"})
	public void testFetchLayoutPageTemplateCollection() {
		_testFetchLayoutPageTemplateCollection(StringPool.BLANK);
		_testFetchLayoutPageTemplateCollection(_PORTLET_NAMESPACE);
		_testFetchLayoutPageTemplateCollectionWithExternalReferenceCode(
			StringPool.BLANK);
		_testFetchLayoutPageTemplateCollectionWithExternalReferenceCode(
			_PORTLET_NAMESPACE);
		_testFetchLayoutPageTemplateCollectionWithOtherGroupId();
		_testFetchLayoutPageTemplateCollectionWithoutParameters();
	}

	private void _setUpPortalUtil() {
		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(Mockito.mock(Portal.class));

		Mockito.when(
			portalUtil.getPortletNamespace(
				LayoutPageTemplateAdminPortletKeys.LAYOUT_PAGE_TEMPLATES)
		).thenReturn(
			_PORTLET_NAMESPACE
		);
	}

	private void _testFetchLayoutPageTemplateCollection(
		String portletNamespace) {

		long groupId = RandomTestUtil.randomLong();
		long layoutPageTemplateCollectionId = RandomTestUtil.randomLong();

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setParameter(
			portletNamespace + "layoutPageTemplateCollectionId",
			String.valueOf(layoutPageTemplateCollectionId));

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			Mockito.mock(LayoutPageTemplateCollection.class);

		Mockito.when(
			layoutPageTemplateCollection.getGroupId()
		).thenReturn(
			groupId
		);

		_layoutPageTemplateCollectionLocalServiceUtilMockedStatic.when(
			() ->
				LayoutPageTemplateCollectionLocalServiceUtil.
					fetchLayoutPageTemplateCollection(
						layoutPageTemplateCollectionId)
		).thenReturn(
			layoutPageTemplateCollection
		);

		Assert.assertSame(
			layoutPageTemplateCollection,
			LayoutPageTemplatePortletUtil.fetchLayoutPageTemplateCollection(
				mockHttpServletRequest, groupId));
	}

	private void
		_testFetchLayoutPageTemplateCollectionWithExternalReferenceCode(
			String portletNamespace) {

		String externalReferenceCode = RandomTestUtil.randomString();
		long groupId = RandomTestUtil.randomLong();

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setParameter(
			portletNamespace +
				"layoutPageTemplateCollectionExternalReferenceCode",
			externalReferenceCode);

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			Mockito.mock(LayoutPageTemplateCollection.class);

		_layoutPageTemplateCollectionLocalServiceUtilMockedStatic.when(
			() ->
				LayoutPageTemplateCollectionLocalServiceUtil.
					fetchLayoutPageTemplateCollectionByExternalReferenceCode(
						externalReferenceCode, groupId)
		).thenReturn(
			layoutPageTemplateCollection
		);

		Assert.assertSame(
			layoutPageTemplateCollection,
			LayoutPageTemplatePortletUtil.fetchLayoutPageTemplateCollection(
				mockHttpServletRequest, groupId));
	}

	private void _testFetchLayoutPageTemplateCollectionWithOtherGroupId() {
		long layoutPageTemplateCollectionId = RandomTestUtil.randomLong();

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setParameter(
			"layoutPageTemplateCollectionId",
			String.valueOf(layoutPageTemplateCollectionId));

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			Mockito.mock(LayoutPageTemplateCollection.class);

		Mockito.when(
			layoutPageTemplateCollection.getGroupId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		_layoutPageTemplateCollectionLocalServiceUtilMockedStatic.when(
			() ->
				LayoutPageTemplateCollectionLocalServiceUtil.
					fetchLayoutPageTemplateCollection(
						layoutPageTemplateCollectionId)
		).thenReturn(
			layoutPageTemplateCollection
		);

		Assert.assertNull(
			LayoutPageTemplatePortletUtil.fetchLayoutPageTemplateCollection(
				mockHttpServletRequest, RandomTestUtil.randomLong()));
	}

	private void _testFetchLayoutPageTemplateCollectionWithoutParameters() {
		Assert.assertNull(
			LayoutPageTemplatePortletUtil.fetchLayoutPageTemplateCollection(
				new MockHttpServletRequest(), RandomTestUtil.randomLong()));
	}

	private static final String _PORTLET_NAMESPACE =
		RandomTestUtil.randomString();

	private final MockedStatic<LayoutPageTemplateCollectionLocalServiceUtil>
		_layoutPageTemplateCollectionLocalServiceUtilMockedStatic =
			Mockito.mockStatic(
				LayoutPageTemplateCollectionLocalServiceUtil.class);

}