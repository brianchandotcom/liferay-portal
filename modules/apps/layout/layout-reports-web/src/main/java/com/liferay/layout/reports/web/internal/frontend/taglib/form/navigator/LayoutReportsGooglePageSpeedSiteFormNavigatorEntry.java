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

package com.liferay.layout.reports.web.internal.frontend.taglib.form.navigator;

import com.liferay.frontend.taglib.form.navigator.BaseJSPFormNavigatorEntry;
import com.liferay.frontend.taglib.form.navigator.FormNavigatorEntry;
import com.liferay.frontend.taglib.form.navigator.constants.FormNavigatorConstants;
import com.liferay.layout.reports.web.internal.configuration.LayoutReportsGooglePageSpeedCompanyConfiguration;
import com.liferay.layout.reports.web.internal.configuration.LayoutReportsGooglePageSpeedConfiguration;
import com.liferay.layout.reports.web.internal.display.context.LayoutReportsGooglePageSpeedDisplayContext;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.portlet.PortletRequest;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(
	configurationPid = "com.liferay.layout.reports.web.internal.configuration.LayoutReportsGooglePageSpeedConfiguration",
	property = "form.navigator.entry.order:Integer=20",
	service = FormNavigatorEntry.class
)
public class LayoutReportsGooglePageSpeedSiteFormNavigatorEntry
	extends BaseJSPFormNavigatorEntry<Group> {

	@Override
	public String getCategoryKey() {
		return FormNavigatorConstants.CATEGORY_KEY_SITES_GENERAL;
	}

	@Override
	public String getFormNavigatorId() {
		return FormNavigatorConstants.FORM_NAVIGATOR_ID_SITES;
	}

	@Override
	public String getKey() {
		return "google-page-speed";
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(
			_getResourceBundle(locale), "google-page-speed");
	}

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		PortletRequest portletRequest =
			(PortletRequest)httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);

		long companyId = _portal.getCompanyId(portletRequest);

		httpServletRequest.setAttribute(
			LayoutReportsGooglePageSpeedDisplayContext.class.getName(),
			new LayoutReportsGooglePageSpeedDisplayContext(
				_getApiKey(companyId), _isEnabled(companyId), portletRequest));

		super.include(httpServletRequest, httpServletResponse);
	}

	@Override
	public boolean isVisible(User user, Group group) {
		if ((group == null) || group.isCompany()) {
			return false;
		}

		if (!_isEnabled(group.getCompanyId())) {
			return false;
		}

		return true;
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.layout.reports.web)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_layoutReportsGooglePageSpeedConfiguration =
			ConfigurableUtil.createConfigurable(
				LayoutReportsGooglePageSpeedConfiguration.class, properties);
	}

	@Override
	protected String getJspPath() {
		return "/site/google_page_speed_settings.jsp";
	}

	private String _getApiKey(long companyId) {
		try {
			LayoutReportsGooglePageSpeedCompanyConfiguration
				layoutReportsGooglePageSpeedCompanyConfiguration =
					_configurationProvider.getCompanyConfiguration(
						LayoutReportsGooglePageSpeedCompanyConfiguration.class,
						companyId);

			if (Validator.isNotNull(
					layoutReportsGooglePageSpeedCompanyConfiguration.
						apiKey())) {

				return layoutReportsGooglePageSpeedCompanyConfiguration.
					apiKey();
			}

			return _layoutReportsGooglePageSpeedConfiguration.apiKey();
		}
		catch (ConfigurationException configurationException) {
			_log.error(configurationException, configurationException);

			return StringPool.BLANK;
		}
	}

	private ResourceBundle _getResourceBundle(Locale locale) {
		return ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());
	}

	private boolean _isEnabled(long companyId) {
		if (!_layoutReportsGooglePageSpeedConfiguration.enabled()) {
			return false;
		}

		try {
			LayoutReportsGooglePageSpeedCompanyConfiguration
				layoutReportsGooglePageSpeedCompanyConfiguration =
					_configurationProvider.getCompanyConfiguration(
						LayoutReportsGooglePageSpeedCompanyConfiguration.class,
						companyId);

			if (!layoutReportsGooglePageSpeedCompanyConfiguration.enabled()) {
				return false;
			}

			return true;
		}
		catch (ConfigurationException configurationException) {
			_log.error(configurationException, configurationException);

			return false;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutReportsGooglePageSpeedSiteFormNavigatorEntry.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private ConfigurationProvider _configurationProvider;

	private LayoutReportsGooglePageSpeedConfiguration
		_layoutReportsGooglePageSpeedConfiguration;

	@Reference
	private Portal _portal;

}