/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.web.internal.display.context;

import com.liferay.fragment.configuration.FragmentServiceConfiguration;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.service.PortalPreferencesLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PortletKeys;

import jakarta.portlet.PortletPreferences;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Objects;

/**
 * @author Eudaldo Alonso
 */
public class FragmentServiceConfigurationDisplayContext {

	public FragmentServiceConfigurationDisplayContext(
		ConfigurationProvider configurationProvider,
		HttpServletRequest httpServletRequest,
		LiferayPortletResponse liferayPortletResponse, String scope,
		ThemeDisplay themeDisplay) {

		_configurationProvider = configurationProvider;
		_httpServletRequest = httpServletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_scope = scope;
		_themeDisplay = themeDisplay;
	}

	public String getPropagateContributedFragmentEntriesChangesURL() {
		return PortletURLBuilder.createActionURL(
			_liferayPortletResponse
		).setActionName(
			"/instance_settings/propagate_contributed_fragment_entries_changes"
		).setRedirect(
			PortalUtil.getCurrentURL(_httpServletRequest)
		).setParameter(
			"scope", _scope
		).setParameter(
			"scopePK", _getScopePK()
		).buildString();
	}

	public boolean isAlreadyPropagateContributedFragmentChanges() {
		PortletPreferences portletPreferences =
			PortalPreferencesLocalServiceUtil.getPreferences(
				_themeDisplay.getCompanyId(),
				PortletKeys.PREFS_OWNER_TYPE_COMPANY);

		return GetterUtil.getBoolean(
			portletPreferences.getValue(
				"alreadyPropagateContributedFragmentChanges", null));
	}

	public boolean isPropagateChangesEnabled() throws ConfigurationException {
		return _getFragmentServiceConfiguration().propagateChanges();
	}

	public boolean isPropagateContributedFragmentChangesEnabled()
		throws ConfigurationException {

		return _getFragmentServiceConfiguration().
			propagateContributedFragmentChanges();
	}

	private FragmentServiceConfiguration _getFragmentServiceConfiguration()
		throws ConfigurationException {

		if (_fragmentServiceConfiguration != null) {
			return _fragmentServiceConfiguration;
		}

		if (Objects.equals(
				_scope,
				ExtendedObjectClassDefinition.Scope.COMPANY.getValue())) {

			_fragmentServiceConfiguration =
				_configurationProvider.getCompanyConfiguration(
					FragmentServiceConfiguration.class,
					_themeDisplay.getCompanyId());
		}
		else {
			_fragmentServiceConfiguration =
				_configurationProvider.getSystemConfiguration(
					FragmentServiceConfiguration.class);
		}

		return _fragmentServiceConfiguration;
	}

	private long _getScopePK() {
		if (Objects.equals(
				_scope,
				ExtendedObjectClassDefinition.Scope.COMPANY.getValue())) {

			return _themeDisplay.getCompanyId();
		}

		return 0L;
	}

	private final ConfigurationProvider _configurationProvider;
	private FragmentServiceConfiguration _fragmentServiceConfiguration;
	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final String _scope;
	private final ThemeDisplay _themeDisplay;

}