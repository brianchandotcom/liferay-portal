/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.internal.processor;

import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.processor.PortletRegistry;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
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
	public void testGetFragmentEntryLinkPortletIdsAliasPortletName() {
		String portletAlias = StringUtil.toLowerCase(
			RandomTestUtil.randomString());
		String portletName = RandomTestUtil.randomString();

		_portletRegistry.registerAlias(portletAlias, portletName);

		Assert.assertEquals(
			portletName, _portletRegistry.getPortletName(portletAlias));

		String elementId = RandomTestUtil.randomString();
		String namespace = RandomTestUtil.randomString();

		_assertGetFragmentEntryLinkPortletIds(
			_getFragmentEntryLink(
				StringBundler.concat(
					"<div class=\"fragment_1\">", RandomTestUtil.randomString(),
					"<lfr-widget-", portletAlias, " id=\"", elementId, "\">",
					RandomTestUtil.randomString(), "</div>"),
				namespace),
			PortletIdCodec.encode(
				PortletIdCodec.decodePortletName(portletName),
				PortletIdCodec.decodeUserId(portletName),
				namespace + elementId));
	}

	@Test
	public void testGetFragmentEntryLinkPortletIdsAliasPortletNameUnregistered() {
		_assertGetFragmentEntryLinkPortletIds(
			_getFragmentEntryLink(
				StringBundler.concat(
					"<div class=\"fragment_1\">", RandomTestUtil.randomString(),
					"<lfr-widget-", RandomTestUtil.randomString(), " id=\"",
					RandomTestUtil.randomString(), "\">",
					RandomTestUtil.randomString(), "</div>"),
				RandomTestUtil.randomString()));
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
			).toString(),
			"<div class=\"fragment_1\"></div>", RandomTestUtil.randomString());

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

	private FragmentEntryLink _getFragmentEntryLink(
		String html, String namespace) {

		return _getFragmentEntryLink("{}", html, namespace);
	}

	private FragmentEntryLink _getFragmentEntryLink(
		String editableValues, String html, String namespace) {

		FragmentEntryLink fragmentEntryLink = Mockito.mock(
			FragmentEntryLink.class);

		Mockito.when(
			fragmentEntryLink.getEditableValues()
		).thenReturn(
			editableValues
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

	private PortletLocalService _portletLocalService;
	private PortletRegistry _portletRegistry;

}