/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.portlet.shared.search;

import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.processor.PortletRegistry;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ListUtil;
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
 * @author Felipe Lorenz
 * @author Joshua Cords
 */
public class PortletSharedSearchRequestImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetSegmentExperiencePortletIdsWithPortletFragments() {
		Assert.assertEquals(
			Set.of("portletA_INSTANCE_rdna", "portletB_INSTANCE_rdna"),
			ReflectionTestUtil.<Set<String>>invoke(
				_mockPortletSharedSearchRequest(),
				"_getSegmentExperiencePortletIds",
				new Class<?>[] {Layout.class, long.class},
				Mockito.mock(Layout.class), RandomTestUtil.randomLong()));
	}

	private FragmentEntryLink _mockFragmentEntryLink(String portletName) {
		FragmentEntryLink fragmentEntryLink = Mockito.mock(
			FragmentEntryLink.class);

		Mockito.doReturn(
			ListUtil.toList(portletName + "_INSTANCE_rdna")
		).when(
			_portletRegistry
		).getFragmentEntryLinkPortletIds(
			fragmentEntryLink
		);

		return fragmentEntryLink;
	}

	private FragmentEntryLinkLocalService _mockFragmentEntryLinkLocalService() {
		FragmentEntryLinkLocalService fragmentEntryLinkLocalService =
			Mockito.mock(FragmentEntryLinkLocalService.class);

		Mockito.doReturn(
			List.of(
				_mockFragmentEntryLink("portletA"),
				_mockFragmentEntryLink("portletB"))
		).when(
			fragmentEntryLinkLocalService
		).getFragmentEntryLinksBySegmentsExperienceId(
			Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong()
		);

		return fragmentEntryLinkLocalService;
	}

	private PortletSharedSearchRequest _mockPortletSharedSearchRequest() {
		PortletSharedSearchRequest portletSharedSearchRequest =
			new PortletSharedSearchRequestImpl();

		ReflectionTestUtil.setFieldValue(
			portletSharedSearchRequest, "_fragmentEntryLinkLocalService",
			_mockFragmentEntryLinkLocalService());

		ReflectionTestUtil.setFieldValue(
			portletSharedSearchRequest, "_portletRegistry", _portletRegistry);

		return portletSharedSearchRequest;
	}

	private final PortletRegistry _portletRegistry = Mockito.mock(
		PortletRegistry.class);

}