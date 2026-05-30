/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.seo.web.internal.helper;

import com.liferay.layout.seo.provider.LayoutSEOMetaRobotsProvider;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.configuration.kernel.util.PortletConfigurationUtil;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Amos Fong
 */
@Component(service = LayoutSEOMetaRobotsHelper.class)
public class LayoutSEOMetaRobotsHelper {

	public List<String> getProviderPortletTitles(Layout layout, Locale locale) {
		if (!FeatureFlagManagerUtil.isEnabled(
				layout.getCompanyId(), "LPD-71164")) {

			return Collections.emptyList();
		}

		List<String> portletTitles = TransformUtil.transform(
			_portletPreferencesLocalService.getPortletPreferencesByPlid(
				layout.getPlid()),
			portletPreferences -> {
				Portlet portlet = _portletLocalService.getPortletById(
					layout.getCompanyId(), portletPreferences.getPortletId());

				if (portlet == null) {
					return null;
				}

				LayoutSEOMetaRobotsProvider layoutSEOMetaRobotsProvider =
					_serviceTrackerMap.getService(portlet.getRootPortletId());

				if (layoutSEOMetaRobotsProvider == null) {
					return null;
				}

				jakarta.portlet.PortletPreferences jxPortletPreferences =
					_portletPreferencesLocalService.fetchPreferences(
						portletPreferences.getCompanyId(),
						portletPreferences.getOwnerId(),
						portletPreferences.getOwnerType(),
						portletPreferences.getPlid(),
						portletPreferences.getPortletId());

				if ((jxPortletPreferences == null) ||
					!layoutSEOMetaRobotsProvider.isProvidesContent(
						jxPortletPreferences)) {

					return null;
				}

				return _getPortletTitle(jxPortletPreferences, locale, portlet);
			});

		return ListUtil.sort(portletTitles);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, LayoutSEOMetaRobotsProvider.class,
			"jakarta.portlet.name");
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private String _getPortletTitle(
		jakarta.portlet.PortletPreferences portletPreferences, Locale locale,
		Portlet portlet) {

		String customPortletTitle = PortletConfigurationUtil.getPortletTitle(
			portlet.getPortletId(), portletPreferences,
			LocaleUtil.toLanguageId(locale));

		if (Validator.isNotNull(customPortletTitle)) {
			return customPortletTitle;
		}

		return _portal.getPortletTitle(portlet, locale);
	}

	@Reference
	private Portal _portal;

	@Reference
	private PortletLocalService _portletLocalService;

	@Reference
	private PortletPreferencesLocalService _portletPreferencesLocalService;

	private ServiceTrackerMap<String, LayoutSEOMetaRobotsProvider>
		_serviceTrackerMap;

}