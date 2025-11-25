/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.portlet.shared.search;

import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.processor.PortletRegistry;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchRequest;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Joshua Cords
 */
public class PortletSharedSearchRequestImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetSegmentExperiencePortletIdsWithPortletFragments() {
		FragmentEntryLinkLocalService fragmentEntryLinkLocalService =
			Mockito.mock(FragmentEntryLinkLocalService.class);

		PortletSharedSearchRequest portletSharedSearchRequest =
			new PortletSharedSearchRequestImpl();

		ReflectionTestUtil.setFieldValue(
			portletSharedSearchRequest, "_fragmentEntryLinkLocalService",
			fragmentEntryLinkLocalService);

		PortletRegistry portletRegistry = Mockito.mock(PortletRegistry.class);

		ReflectionTestUtil.setFieldValue(
			portletSharedSearchRequest, "_portletRegistry", portletRegistry);

		Layout layout = Mockito.mock(Layout.class);

		Mockito.doReturn(
			1L
		).when(
			layout
		).getGroupId();

		Mockito.doReturn(
			2L
		).when(
			layout
		).getPlid();

		String elementId = RandomTestUtil.randomString();
		String portletAlias = StringUtil.toLowerCase(
			RandomTestUtil.randomString());

		FragmentEntryLink fragmentEntryLink1 = _getFragmentEntryLink(
			StringBundler.concat(
				"<div class=\"fragment_1\">", RandomTestUtil.randomString(),
				"<lfr-widget-", portletAlias, " id=\"", elementId, "\">",
				RandomTestUtil.randomString(), "</div>"),
			RandomTestUtil.randomString(), "portletA");

		FragmentEntryLink fragmentEntryLink2 = _getFragmentEntryLink(
			StringBundler.concat(
				"<div class=\"fragment_1\">", RandomTestUtil.randomString(),
				"old-working-values", portletAlias, " id=\"", elementId, "\">",
				RandomTestUtil.randomString(), "</div>"),
			RandomTestUtil.randomString(), "portletB");

		List<FragmentEntryLink> fragmentEntryLinks = List.of(
			fragmentEntryLink1, fragmentEntryLink2);

		Mockito.doReturn(
			fragmentEntryLinks
		).when(
			fragmentEntryLinkLocalService
		).getFragmentEntryLinksBySegmentsExperienceId(
			Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong()
		);

		Mockito.when(
			fragmentEntryLink1.isTypePortlet()
		).thenReturn(
			true
		);

		Mockito.doReturn(
			ListUtil.toList("portletA_INSTANCE_rdna")
		).when(
			portletRegistry
		).getFragmentEntryLinkPortletIds(
			fragmentEntryLink1
		);

		Mockito.doReturn(
			ListUtil.toList("portletB_INSTANCE_rdna")
		).when(
			portletRegistry
		).getFragmentEntryLinkPortletIds(
			fragmentEntryLink2
		);

		Set<String> result = ReflectionTestUtil.invoke(
			portletSharedSearchRequest, "_getSegmentExperiencePortletIds",
			new Class<?>[] {Layout.class, long.class}, layout, 123L);

		Assert.assertEquals(
			Set.of("portletA_INSTANCE_rdna", "portletB_INSTANCE_rdna"), result);
	}

	private FragmentEntryLink _getFragmentEntryLink(
		String html, String namespace, String portletName) {

		FragmentEntryLink fragmentEntryLink = Mockito.mock(
			FragmentEntryLink.class);

		Mockito.when(
			fragmentEntryLink.getEditableValuesJSONObject()
		).thenReturn(
			JSONFactoryUtil.safeCreateJSONObject(
				"{\"instanceId\":\"rdna\",\"portletId\":\"" + portletName +
					"\"}")
		);

		Mockito.when(
			fragmentEntryLink.getHtml()
		).thenReturn(
			html
		);

		Mockito.when(
			fragmentEntryLink.getNamespace()
		).thenReturn(
			namespace
		);

		return fragmentEntryLink;
	}

}