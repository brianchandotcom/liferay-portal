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

package com.liferay.change.tracking.web.internal.display.portlet;

import com.liferay.change.tracking.spi.display.BaseCTDisplayRenderer;
import com.liferay.change.tracking.spi.display.CTDisplayRenderer;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.util.CamelCaseUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Truong
 */
@Component(immediate = true, service = CTDisplayRenderer.class)
public class PortletPreferencesCTDisplayRenderer
	extends BaseCTDisplayRenderer<PortletPreferences> {

	@Override
	public Class<PortletPreferences> getModelClass() {
		return PortletPreferences.class;
	}

	@Override
	public String getTitle(
		Locale locale, PortletPreferences portletPreferences) {

		List<String> arguments = new ArrayList<>(2);

		Portlet portlet = _portletLocalService.getPortletById(
			portletPreferences.getCompanyId(),
			portletPreferences.getPortletId());

		try {
			arguments.add(_portal.getPortletTitle(portlet, locale));
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception, exception);
			}

			arguments.add(portlet.getPortletName());
		}

		try {
			Layout layout = _layoutLocalService.getLayout(
				portletPreferences.getPlid());

			arguments.add(layout.getName(locale));
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException, portalException);
			}

			arguments.add("Control Panel");
		}

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			locale, getClass());

		return _language.format(
			resourceBundle, "x-on-x-page", arguments.toArray(new String[0]),
			false);
	}

	@Override
	public String getTypeName(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			locale, getClass());

		return _language.get(resourceBundle, "widget");
	}

	@Override
	public boolean isHideable(PortletPreferences portletPreferences) {
		Portlet portlet = _portletLocalService.getPortletById(
			portletPreferences.getCompanyId(),
			portletPreferences.getPortletId());

		if (portlet.isSystem()) {
			return true;
		}

		try {
			Layout layout = _layoutLocalService.getLayout(
				portletPreferences.getPlid());

			return layout.isSystem();
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException, portalException);
			}
		}

		return true;
	}

	@Override
	protected void buildDisplay(
		DisplayBuilder<PortletPreferences> displayBuilder) {

		PortletPreferences portletPreferences = displayBuilder.getModel();

		displayBuilder.display(
			"name",
			() -> {
				Portlet portlet = _portletLocalService.getPortletById(
					portletPreferences.getCompanyId(),
					portletPreferences.getPortletId());

				try {
					return _portal.getPortletTitle(
						portlet, displayBuilder.getLocale());
				}
				catch (Exception exception) {
					if (_log.isWarnEnabled()) {
						_log.warn(exception, exception);
					}

					return portlet.getPortletName();
				}
			});

		displayBuilder.display(
			"preferences",
			() -> {
				String preferencesXML = portletPreferences.getPreferences();

				javax.portlet.PortletPreferences jxPortletPreferences =
					PortletPreferencesFactoryUtil.fromDefaultXML(
						preferencesXML);

				Map<String, String[]> map = jxPortletPreferences.getMap();

				ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
					displayBuilder.getLocale(), getClass());

				if (map.isEmpty()) {
					return _language.get(
						resourceBundle,
						"this-widget-has-not-configured-any-preferences");
				}

				StringBundler sb = new StringBundler();

				for (Map.Entry<String, String[]> entry : map.entrySet()) {
					sb.append(
						_language.get(
							resourceBundle,
							CamelCaseUtil.fromCamelCase(entry.getKey())));
					sb.append(": ");
					sb.append(StringUtil.merge(entry.getValue()));
					sb.append("\n");
				}

				return sb.toString();
			});
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortletPreferencesCTDisplayRenderer.class);

	@Reference
	private Language _language;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private PortletLocalService _portletLocalService;

}