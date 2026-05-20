/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.osgi.web.portlet.container.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.test.util.ContentLayoutTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.application.type.ApplicationType;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.PortletQName;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.osgi.web.portlet.container.test.util.PortletContainerTestUtil;

import jakarta.portlet.PortletRequest;
import jakarta.portlet.RenderRequest;
import jakarta.portlet.RenderResponse;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Raymond Augé
 */
@RunWith(Arquillian.class)
public class PublicRenderParameterTest extends BasePortletContainerTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_publicRenderParameterName = "test_" + RandomTestUtil.randomString();
		_publicRenderParameterValue = RandomTestUtil.randomString();
	}

	@Test
	@TestInfo("LPD-86498")
	public void testWithContentLayoutDirectURL() throws Exception {
		Layout contentLayout = LayoutTestUtil.addTypeContentLayout(group);

		Layout draftLayout = contentLayout.fetchDraftLayout();

		_testPublicRenderParameter(
			() -> {
				ContentLayoutTestUtil.addPortletToLayout(
					draftLayout, TEST_PORTLET_ID);

				ContentLayoutTestUtil.publishLayout(draftLayout, contentLayout);
			},
			Collections.emptyMap(),
			() -> StringBundler.concat(
				"http://localhost:", PortalUtil.getPortalServerPort(false),
				"/web", group.getFriendlyURL(),
				contentLayout.getFriendlyURL(LocaleUtil.getDefault()),
				"?p_r_p_", _publicRenderParameterName, "=",
				_publicRenderParameterValue));
	}

	@Test
	@TestInfo("LPD-86498")
	public void testWithContentLayoutRenderURL() throws Exception {
		Layout contentLayout = LayoutTestUtil.addTypeContentLayout(group);

		Layout draftLayout = contentLayout.fetchDraftLayout();

		_testPublicRenderParameter(
			() -> {
				ContentLayoutTestUtil.addPortletToLayout(
					draftLayout, TEST_PORTLET_ID);

				ContentLayoutTestUtil.publishLayout(draftLayout, contentLayout);
			},
			Collections.emptyMap(),
			() -> PortletURLBuilder.create(
				PortletURLFactoryUtil.create(
					PortletContainerTestUtil.getHttpServletRequest(
						group, contentLayout),
					TEST_PORTLET_ID, contentLayout.getPlid(),
					PortletRequest.RENDER_PHASE)
			).setParameter(
				_publicRenderParameterName, _publicRenderParameterValue
			).buildString());
	}

	@Test
	public void testWithModuleLayoutTypeController() throws Exception {
		_testPublicRenderParameter(
			() -> {
				layout = LayoutTestUtil.addTypeFullPageApplicationLayout(
					group.getGroupId());

				UnicodeProperties typeSettingsUnicodeProperties =
					layout.getTypeSettingsProperties();

				typeSettingsUnicodeProperties.setProperty(
					"fullPageApplicationPortlet", TEST_PORTLET_ID);

				LayoutLocalServiceUtil.updateLayout(layout);
			},
			Collections.singletonMap(
				"com.liferay.portlet.application-type",
				new String[] {
					ApplicationType.FULL_PAGE_APPLICATION.toString(),
					ApplicationType.WIDGET.toString()
				}),
			() -> PortletURLBuilder.create(
				PortletURLFactoryUtil.create(
					PortletContainerTestUtil.getHttpServletRequest(
						group, layout),
					TEST_PORTLET_ID, layout.getPlid(),
					PortletRequest.RENDER_PHASE)
			).setParameter(
				_publicRenderParameterName, _publicRenderParameterValue
			).buildString());
	}

	@Test
	public void testWithPortalLayoutTypeController() throws Exception {
		_testPublicRenderParameter(
			() -> LayoutTestUtil.addPortletToLayout(layout, TEST_PORTLET_ID),
			Collections.emptyMap(),
			() -> PortletURLBuilder.create(
				PortletURLFactoryUtil.create(
					PortletContainerTestUtil.getHttpServletRequest(
						group, layout),
					TEST_PORTLET_ID, layout.getPlid(),
					PortletRequest.RENDER_PHASE)
			).setParameter(
				_publicRenderParameterName, _publicRenderParameterValue
			).buildString());
	}

	private void _testPublicRenderParameter(
			UnsafeRunnable<Exception> layoutSetUpUnsafeRunnable,
			Map<String, Object> portletProperties,
			UnsafeSupplier<String, Exception> portletURLStringUnsafeSupplier)
		throws Exception {

		final AtomicBoolean success = new AtomicBoolean(false);

		testPortlet = new TestPortlet() {

			@Override
			public void render(
					RenderRequest renderRequest, RenderResponse renderResponse)
				throws IOException {

				PrintWriter printWriter = renderResponse.getWriter();

				String value = renderRequest.getParameter(
					_publicRenderParameterName);

				if (_publicRenderParameterValue.equals(value)) {
					success.set(true);
				}

				printWriter.write(value);
			}

		};

		setUpPortlet(
			testPortlet,
			HashMapDictionaryBuilder.<String, Object>put(
				"jakarta.portlet.supported-public-render-parameter",
				_publicRenderParameterName
			).put(
				"jakarta.portlet.version", "3.0"
			).putAll(
				portletProperties
			).build(),
			TEST_PORTLET_ID, false);

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			TestPropsValues.getCompanyId(), TEST_PORTLET_ID);

		Assert.assertFalse(portlet.isUndeployedPortlet());

		layoutSetUpUnsafeRunnable.run();

		String portletURLString = portletURLStringUnsafeSupplier.get();

		Assert.assertTrue(
			portletURLString,
			portletURLString.contains(
				PortletQName.PUBLIC_RENDER_PARAMETER_NAMESPACE));

		PortletContainerTestUtil.Response response =
			PortletContainerTestUtil.request(portletURLString);

		Assert.assertEquals(200, response.getCode());

		Assert.assertTrue(success.get());
	}

	private String _publicRenderParameterName;
	private String _publicRenderParameterValue;

}