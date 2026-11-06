/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.portlet.bridge.mvc.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletRenderRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceResponse;
import com.liferay.portal.kernel.test.portlet.MockRenderResponse;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import jakarta.portlet.Portlet;
import jakarta.portlet.PortletException;
import jakarta.portlet.PortletRequest;
import jakarta.portlet.PortletResponse;
import jakarta.portlet.RenderRequest;
import jakarta.portlet.RenderResponse;

import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

import java.util.Dictionary;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.After;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Antonio Ortega
 */
@RunWith(Arquillian.class)
public class MVCPortletConfigurationTest {

	@After
	public void tearDown() {
		if (_configurationActionServiceRegistration != null) {
			_configurationActionServiceRegistration.unregister();
		}

		if (_portletServiceRegistration != null) {
			_portletServiceRegistration.unregister();
		}
	}

	@Test
	public void testDoDispatchDoesNotInvokeConfigurationAction()
		throws Exception {

		AtomicBoolean invoked = new AtomicBoolean(false);
		String portletId = "testPortlet" + RandomTestUtil.randomString();

		_registerConfigurationAction(
			new DefaultConfigurationAction() {

				@Override
				public String getJspPath(
					HttpServletRequest httpServletRequest) {

					invoked.set(true);

					return super.getJspPath(httpServletRequest);
				}

			},
			portletId);

		CapturingMVCPortlet mvcPortlet = _registerMVCPortlet(
			HashMapDictionaryBuilder.<String, Object>put(
				"jakarta.portlet.name", portletId
			).build());

		String viewPath = "/" + RandomTestUtil.randomString() + ".jsp";

		MockLiferayPortletRenderRequest mockLiferayPortletRenderRequest =
			_createRenderRequest(viewPath);

		mockLiferayPortletRenderRequest.setAttribute(
			WebKeys.COMPANY_ID, TestPropsValues.getCompanyId());

		mvcPortlet.dispatch(
			mockLiferayPortletRenderRequest, new MockRenderResponse());

		Assert.assertFalse(invoked.get());
		Assert.assertEquals(viewPath, mvcPortlet.getIncludedPath());
	}

	@Test
	public void testDoDispatchDoesNotServeConfigTemplateUnderViewMode()
		throws Exception {

		String configTemplate = "/" + RandomTestUtil.randomString() + ".jsp";
		String viewTemplate = "/" + RandomTestUtil.randomString() + ".jsp";

		CapturingMVCPortlet mvcPortlet = _registerMVCPortlet(
			HashMapDictionaryBuilder.<String, Object>put(
				"jakarta.portlet.init-param.config-template", configTemplate
			).put(
				"jakarta.portlet.init-param.view-template", viewTemplate
			).put(
				"jakarta.portlet.name",
				"testPortlet" + RandomTestUtil.randomString()
			).build());

		mvcPortlet.dispatch(
			_createRenderRequest(configTemplate), new MockRenderResponse());

		Assert.assertEquals(viewTemplate, mvcPortlet.getIncludedPath());
	}

	@Test
	public void testDoDispatchDoesNotServeConfigurationJSPInitParamUnderViewMode()
		throws Exception {

		String configJSP = "/" + RandomTestUtil.randomString() + ".jsp";
		String portletId = "testPortlet" + RandomTestUtil.randomString();
		String viewTemplate = "/" + RandomTestUtil.randomString() + ".jsp";

		_registerConfigurationAction(
			new DefaultConfigurationAction(), portletId);

		CapturingMVCPortlet mvcPortlet = _registerMVCPortlet(
			HashMapDictionaryBuilder.<String, Object>put(
				"jakarta.portlet.init-param.config-jsp", configJSP
			).put(
				"jakarta.portlet.init-param.view-template", viewTemplate
			).put(
				"jakarta.portlet.name", portletId
			).build());

		MockLiferayPortletRenderRequest mockLiferayPortletRenderRequest =
			_createRenderRequest(configJSP);

		mockLiferayPortletRenderRequest.setAttribute(
			WebKeys.COMPANY_ID, TestPropsValues.getCompanyId());

		mvcPortlet.dispatch(
			mockLiferayPortletRenderRequest, new MockRenderResponse());

		Assert.assertEquals(viewTemplate, mvcPortlet.getIncludedPath());
	}

	@Test
	public void testDoDispatchDoesNotServeConfigurationJSPSelectedByRequestParameter()
		throws Exception {

		String configTemplate = "/" + RandomTestUtil.randomString() + ".jsp";
		String siblingConfigurationJSP =
			"/" + RandomTestUtil.randomString() + "/configuration.jsp";
		String viewTemplate = "/" + RandomTestUtil.randomString() + ".jsp";

		CapturingMVCPortlet mvcPortlet = _registerMVCPortlet(
			HashMapDictionaryBuilder.<String, Object>put(
				"jakarta.portlet.init-param.config-template", configTemplate
			).put(
				"jakarta.portlet.init-param.view-template", viewTemplate
			).put(
				"jakarta.portlet.name",
				"testPortlet" + RandomTestUtil.randomString()
			).build());

		MockLiferayPortletRenderRequest mockLiferayPortletRenderRequest =
			_createRenderRequest(siblingConfigurationJSP);

		mockLiferayPortletRenderRequest.setParameter(
			"cmd", RandomTestUtil.randomString());

		mvcPortlet.dispatch(
			mockLiferayPortletRenderRequest, new MockRenderResponse());

		Assert.assertEquals(viewTemplate, mvcPortlet.getIncludedPath());
	}

	@Test
	public void testDoDispatchDoesNotServeMetaInfResourcesConfigTemplateUnderViewMode()
		throws Exception {

		String configTemplate = "/" + RandomTestUtil.randomString() + ".jsp";
		String viewTemplate = "/" + RandomTestUtil.randomString() + ".jsp";

		CapturingMVCPortlet mvcPortlet = _registerMVCPortlet(
			HashMapDictionaryBuilder.<String, Object>put(
				"jakarta.portlet.init-param.config-template", configTemplate
			).put(
				"jakarta.portlet.init-param.view-template", viewTemplate
			).put(
				"jakarta.portlet.name",
				"testPortlet" + RandomTestUtil.randomString()
			).build());

		mvcPortlet.dispatch(
			_createRenderRequest("/META-INF/resources" + configTemplate),
			new MockRenderResponse());

		Assert.assertEquals(viewTemplate, mvcPortlet.getIncludedPath());
	}

	@Test
	public void testDoDispatchDoesNotServeNestedConfigurationJSPUnderViewMode()
		throws Exception {

		String configTemplate = "/" + RandomTestUtil.randomString() + ".jsp";
		String nestedConfigurationJSP = StringBundler.concat(
			"/", RandomTestUtil.randomString(), "/configuration/",
			RandomTestUtil.randomString(), ".jsp");
		String viewTemplate = "/" + RandomTestUtil.randomString() + ".jsp";

		CapturingMVCPortlet mvcPortlet = _registerMVCPortlet(
			HashMapDictionaryBuilder.<String, Object>put(
				"jakarta.portlet.init-param.config-template", configTemplate
			).put(
				"jakarta.portlet.init-param.view-template", viewTemplate
			).put(
				"jakarta.portlet.name",
				"testPortlet" + RandomTestUtil.randomString()
			).build());

		mvcPortlet.dispatch(
			_createRenderRequest(nestedConfigurationJSP),
			new MockRenderResponse());

		Assert.assertEquals(viewTemplate, mvcPortlet.getIncludedPath());
	}

	@Test
	public void testDoDispatchDoesNotServeSiblingConfigurationJSPUnderViewMode()
		throws Exception {

		String configTemplate = "/" + RandomTestUtil.randomString() + ".jsp";
		String siblingConfigurationJSP =
			"/" + RandomTestUtil.randomString() + "/configuration.jsp";
		String viewTemplate = "/" + RandomTestUtil.randomString() + ".jsp";

		CapturingMVCPortlet mvcPortlet = _registerMVCPortlet(
			HashMapDictionaryBuilder.<String, Object>put(
				"jakarta.portlet.init-param.config-template", configTemplate
			).put(
				"jakarta.portlet.init-param.view-template", viewTemplate
			).put(
				"jakarta.portlet.name",
				"testPortlet" + RandomTestUtil.randomString()
			).build());

		mvcPortlet.dispatch(
			_createRenderRequest(siblingConfigurationJSP),
			new MockRenderResponse());

		Assert.assertEquals(viewTemplate, mvcPortlet.getIncludedPath());
	}

	@Test
	public void testServeResourceDoesNotServeConfigTemplateOutsideConfigMode()
		throws Exception {

		String configTemplate = "/" + RandomTestUtil.randomString() + ".jsp";

		CapturingMVCPortlet mvcPortlet = _registerMVCPortlet(
			HashMapDictionaryBuilder.<String, Object>put(
				"jakarta.portlet.init-param.config-template", configTemplate
			).put(
				"jakarta.portlet.name",
				"testPortlet" + RandomTestUtil.randomString()
			).build());

		MockLiferayResourceRequest mockLiferayResourceRequest =
			new MockLiferayResourceRequest();

		mockLiferayResourceRequest.setParameter("mvcPath", configTemplate);

		mvcPortlet.serveResource(
			mockLiferayResourceRequest, new MockLiferayResourceResponse());

		Assert.assertNull(mvcPortlet.getIncludedPath());
	}

	@Test
	public void testServeResourceDoesNotServeNestedConfigurationJSPOutsideConfigMode()
		throws Exception {

		String nestedConfigurationJSP = StringBundler.concat(
			"/", RandomTestUtil.randomString(), "/configuration/",
			RandomTestUtil.randomString(), ".jsp");

		CapturingMVCPortlet mvcPortlet = _registerMVCPortlet(
			HashMapDictionaryBuilder.<String, Object>put(
				"jakarta.portlet.init-param.config-template",
				"/" + RandomTestUtil.randomString() + ".jsp"
			).put(
				"jakarta.portlet.name",
				"testPortlet" + RandomTestUtil.randomString()
			).build());

		MockLiferayResourceRequest mockLiferayResourceRequest =
			new MockLiferayResourceRequest();

		mockLiferayResourceRequest.setParameter(
			"mvcPath", nestedConfigurationJSP);

		mvcPortlet.serveResource(
			mockLiferayResourceRequest, new MockLiferayResourceResponse());

		Assert.assertNull(mvcPortlet.getIncludedPath());
	}

	@Rule
	public final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	private MockLiferayPortletRenderRequest _createRenderRequest(
		String mvcPath) {

		MockLiferayPortletRenderRequest mockLiferayPortletRenderRequest =
			new MockLiferayPortletRenderRequest();

		mockLiferayPortletRenderRequest.setParameter("mvcPath", mvcPath);

		return mockLiferayPortletRenderRequest;
	}

	private void _registerConfigurationAction(
		ConfigurationAction configurationAction, String portletId) {

		_configurationActionServiceRegistration =
			_bundleContext.registerService(
				ConfigurationAction.class, configurationAction,
				HashMapDictionaryBuilder.<String, Object>put(
					"jakarta.portlet.name", portletId
				).build());
	}

	private CapturingMVCPortlet _registerMVCPortlet(
		Dictionary<String, Object> properties) {

		CapturingMVCPortlet mvcPortlet = new CapturingMVCPortlet();

		_portletServiceRegistration = _bundleContext.registerService(
			Portlet.class, mvcPortlet, properties);

		return mvcPortlet;
	}

	private final BundleContext _bundleContext;

	{
		Bundle bundle = FrameworkUtil.getBundle(
			MVCPortletConfigurationTest.class);

		_bundleContext = bundle.getBundleContext();
	}

	private ServiceRegistration<ConfigurationAction>
		_configurationActionServiceRegistration;
	private ServiceRegistration<Portlet> _portletServiceRegistration;

	private static class CapturingMVCPortlet extends MVCPortlet {

		public void dispatch(
				RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {

			doDispatch(renderRequest, renderResponse);
		}

		public String getIncludedPath() {
			return _includedPath;
		}

		@Override
		protected void include(
			String path, PortletRequest portletRequest,
			PortletResponse portletResponse, String lifecycle) {

			_includedPath = path;
		}

		private String _includedPath;

	}

}