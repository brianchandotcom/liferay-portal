/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.web.internal.configuration.admin.display;

import com.liferay.configuration.admin.constants.ConfigurationAdminPortletKeys;
import com.liferay.configuration.admin.display.ConfigurationFormRenderer;
import com.liferay.fragment.configuration.FragmentServiceConfiguration;
import com.liferay.fragment.web.internal.display.context.FragmentServiceConfigurationDisplayContext;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import jakarta.portlet.PortletRequest;
import jakarta.portlet.PortletResponse;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Javier Moral
 */
@Component(service = ConfigurationFormRenderer.class)
public class FragmentServiceConfigurationFormRenderer
	implements ConfigurationFormRenderer {

	@Override
	public String getPid() {
		return FragmentServiceConfiguration.class.getName();
	}

	@Override
	public Map<String, Object> getRequestParameters(
		HttpServletRequest httpServletRequest) {

		return HashMapBuilder.<String, Object>put(
			"propagateChanges",
			ParamUtil.getBoolean(httpServletRequest, "propagateChanges")
		).put(
			"propagateContributedFragmentChanges",
			ParamUtil.getBoolean(
				httpServletRequest, "propagateContributedFragmentChanges")
		).build();
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		try {
			RequestDispatcher requestDispatcher =
				_servletContext.getRequestDispatcher(
					"/fragment_service_configuration.jsp");

			String portletId = _portal.getPortletId(
				(PortletRequest)httpServletRequest.getAttribute(
					JavaConstants.JAKARTA_PORTLET_REQUEST));

			String scope =
				ExtendedObjectClassDefinition.Scope.SYSTEM.getValue();

			if (portletId.equals(
					ConfigurationAdminPortletKeys.INSTANCE_SETTINGS)) {

				scope = ExtendedObjectClassDefinition.Scope.COMPANY.getValue();
			}

			httpServletRequest.setAttribute(
				FragmentServiceConfigurationDisplayContext.class.getName(),
				new FragmentServiceConfigurationDisplayContext(
					_configurationProvider, httpServletRequest,
					_portal.getLiferayPortletResponse(
						(PortletResponse)httpServletRequest.getAttribute(
							JavaConstants.JAKARTA_PORTLET_RESPONSE)),
					scope,
					(ThemeDisplay)httpServletRequest.getAttribute(
						WebKeys.THEME_DISPLAY)));

			requestDispatcher.include(httpServletRequest, httpServletResponse);
		}
		catch (Exception exception) {
			throw new IOException(
				"Unable to render /fragment_service_configuration.jsp",
				exception);
		}
	}

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.fragment.web)",
		unbind = "-"
	)
	private ServletContext _servletContext;

}