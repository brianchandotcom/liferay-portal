/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.internal.processor;

import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.processor.PortletRegistry;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Lourdes Fernández Besada
 */
public class PortletRegistryImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_portletRegistry = new PortletRegistryImpl();

		ReflectionTestUtil.setFieldValue(
			_portletRegistry, "_jsonFactory", new JSONFactoryImpl());

		_portletLocalService = Mockito.mock(PortletLocalService.class);

		ReflectionTestUtil.setFieldValue(
			_portletRegistry, "_portletLocalService", _portletLocalService);
	}

	@Test
	public void testGetFragmentEntryLinkPortletIdsTypePortlet() {
		String portletId = RandomTestUtil.randomString();
		String instanceId = RandomTestUtil.randomString();

		FragmentEntryLink fragmentEntryLink = _getFragmentEntryLink(
			JSONUtil.put(
				"instanceId", instanceId
			).put(
				"portletId", portletId
			).toString());

		Mockito.when(
			fragmentEntryLink.isTypePortlet()
		).thenReturn(
			true
		);

		_assertGetFragmentEntryLinkPortletIds(
			fragmentEntryLink, PortletIdCodec.encode(portletId, instanceId));
	}

	private void _assertGetFragmentEntryLinkPortletIds(
		FragmentEntryLink fragmentEntryLink, String... portletIds) {

		List<String> fragmentEntryLinkPortletIds =
			_portletRegistry.getFragmentEntryLinkPortletIds(fragmentEntryLink);

		Assert.assertEquals(
			fragmentEntryLinkPortletIds.toString(), portletIds.length,
			fragmentEntryLinkPortletIds.size());

		for (int i = 0; i < fragmentEntryLinkPortletIds.size(); i++) {
			Assert.assertEquals(
				portletIds[i], fragmentEntryLinkPortletIds.get(i));
		}
	}

	private FragmentEntryLink _getFragmentEntryLink(String editableValues) {
		FragmentEntryLink fragmentEntryLink = Mockito.mock(
			FragmentEntryLink.class);

		Mockito.when(
			fragmentEntryLink.getEditableValues()
		).thenReturn(
			editableValues
		);

		return fragmentEntryLink;
	}

	private PortletLocalService _portletLocalService;
	private PortletRegistry _portletRegistry;

}