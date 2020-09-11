/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.saml.saas.internal.portlet.filter;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.saml.constants.SamlPortletKeys;
import com.liferay.saml.constants.SamlProviderConfigurationKeys;
import com.liferay.saml.runtime.configuration.SamlProviderConfiguration;
import com.liferay.saml.runtime.configuration.SamlProviderConfigurationHelper;
import com.liferay.saml.runtime.credential.KeyStoreManager;
import com.liferay.saml.saas.internal.configuration.SamlSaasConfiguration;

import java.io.IOException;

import java.util.Map;
import java.util.Objects;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.filter.FilterChain;
import javax.portlet.filter.FilterConfig;
import javax.portlet.filter.PortletFilter;
import javax.portlet.filter.RenderFilter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marta Medio
 */
@Component(
	configurationPid = "com.liferay.saml.runtime.configuration.SamlKeyStoreManagerConfiguration",
	immediate = true,
	property = "javax.portlet.name=" + SamlPortletKeys.SAML_ADMIN, service = {}
)
public class SamlAdminRenderFilter implements RenderFilter {

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(
			RenderRequest renderRequest, RenderResponse renderResponse,
			FilterChain chain)
		throws IOException, PortletException {

		chain.doFilter(renderRequest, renderResponse);

		String mvcRenderCommandName = ParamUtil.getString(
			renderRequest, "mvcRenderCommandName", null);
		String tabs1 = ParamUtil.getString(renderRequest, "tabs1", "general");

		if (((mvcRenderCommandName != null) &&
			 !Objects.equals(mvcRenderCommandName, "/admin")) ||
			!Objects.equals(tabs1, "general")) {

			return;
		}

		try {
			SamlSaasConfiguration samlSaasConfiguration =
				ConfigurationProviderUtil.getCompanyConfiguration(
					SamlSaasConfiguration.class,
					_portal.getCompanyId(renderRequest));

			if (samlSaasConfiguration.productionEnvironment() ||
				Validator.isBlank(samlSaasConfiguration.preSharedKey()) ||
				Validator.isBlank(
					samlSaasConfiguration.targetInstanceImportURL())) {

				return;
			}
		}
		catch (ConfigurationException configurationException) {
			_log.error(
				"Unable to get SaaS instance configuration",
				configurationException);

			return;
		}

		SamlProviderConfiguration samlProviderConfiguration =
			_samlProviderConfigurationHelper.getSamlProviderConfiguration();

		if (!Objects.equals(
				SamlProviderConfigurationKeys.SAML_ROLE_SP,
				samlProviderConfiguration.role())) {

			return;
		}

		RequestDispatcher requestDispatcher =
			_servletContext.getRequestDispatcher("/export.jsp");

		try {
			requestDispatcher.include(
				_portal.getHttpServletRequest(renderRequest),
				_portal.getHttpServletResponse(renderResponse));
		}
		catch (Exception exception) {
			throw new PortletException(
				"Unable to include export.jsp", exception);
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws PortletException {
	}

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		if (Objects.equals(
				_keyStoreManagerServiceReference.getProperty("component.name"),
				_DL_KEYSTORE_MANAGER_CLASS_NAME)) {

			_serviceRegistration = bundleContext.registerService(
				PortletFilter.class, this, new HashMapDictionary<>(properties));
		}
	}

	@Deactivate
	protected void deactivate() {
		if (_serviceRegistration != null) {
			_serviceRegistration.unregister();
		}
	}

	private static final String _DL_KEYSTORE_MANAGER_CLASS_NAME =
		"com.liferay.saml.opensaml.integration.internal.credential." +
			"DLKeyStoreManagerImpl";

	private static final Log _log = LogFactoryUtil.getLog(
		SamlAdminRenderFilter.class);

	@Reference(name = "KeyStoreManager")
	private ServiceReference<KeyStoreManager> _keyStoreManagerServiceReference;

	@Reference
	private Portal _portal;

	@Reference
	private SamlProviderConfigurationHelper _samlProviderConfigurationHelper;

	private ServiceRegistration<?> _serviceRegistration;

	@Reference(target = "(osgi.web.symbolicname=com.liferay.saml.saas)")
	private ServletContext _servletContext;

}