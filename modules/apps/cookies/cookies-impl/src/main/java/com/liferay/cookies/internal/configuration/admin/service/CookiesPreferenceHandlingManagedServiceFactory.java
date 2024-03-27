/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.cookies.internal.configuration.admin.service;

import com.liferay.cookies.configuration.CookiesPreferenceHandlingConfiguration;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Dictionary;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

import org.osgi.framework.Constants;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedServiceFactory;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rachael Koestartyo
 */
@Component(
	configurationPid = "com.liferay.cookies.configuration.CookiesPreferenceHandlingConfiguration",
	property = Constants.SERVICE_PID + "=com.liferay.cookies.configuration.CookiesPreferenceHandlingConfiguration.scoped",
	service = ManagedServiceFactory.class
)
public class CookiesPreferenceHandlingManagedServiceFactory
	implements ManagedServiceFactory {

	@Override
	public void deleted(String pid) {
		_unmapPid(pid);
	}

	public boolean getCompanyEnabled(long companyId) {
		CookiesPreferenceHandlingConfiguration
			cookiesPreferenceHandlingConfiguration =
				_getCompanyCookiesPreferenceHandlingConfiguration(companyId);

		return cookiesPreferenceHandlingConfiguration.enabled();
	}

	public boolean getCompanyExplicitConsentMode(long companyId) {
		CookiesPreferenceHandlingConfiguration
			cookiesPreferenceHandlingConfiguration =
				_getCompanyCookiesPreferenceHandlingConfiguration(companyId);

		return cookiesPreferenceHandlingConfiguration.explicitConsentMode();
	}

	public boolean getGroupEnabled(long companyId) {
		CookiesPreferenceHandlingConfiguration
			cookiesPreferenceHandlingConfiguration =
				_getGroupCookiesPreferenceHandlingConfiguration(companyId);

		return cookiesPreferenceHandlingConfiguration.enabled();
	}

	public boolean getGroupExplicitConsentMode(long companyId) {
		CookiesPreferenceHandlingConfiguration
			cookiesPreferenceHandlingConfiguration =
				_getGroupCookiesPreferenceHandlingConfiguration(companyId);

		return cookiesPreferenceHandlingConfiguration.explicitConsentMode();
	}

	@Override
	public String getName() {
		return "com.liferay.cookies.configuration." +
			"CookiesPreferenceHandlingConfiguration.scoped";
	}

	public boolean getSystemEnabled() {
		return _systemCookiesPreferenceHandlingConfiguration.enabled();
	}

	public boolean getSystemExplicitConsentMode() {
		return _systemCookiesPreferenceHandlingConfiguration.
			explicitConsentMode();
	}

	@Override
	public void updated(String pid, Dictionary<String, ?> dictionary)
		throws ConfigurationException {

		_unmapPid(pid);

		long companyId = GetterUtil.getLong(
			dictionary.get("companyId"), CompanyConstants.SYSTEM);

		if (companyId != CompanyConstants.SYSTEM) {
			_companyConfigurationBeans.put(
				companyId,
				ConfigurableUtil.createConfigurable(
					CookiesPreferenceHandlingConfiguration.class, dictionary));
			_companyIds.put(pid, companyId);

			return;
		}

		long groupId = GetterUtil.getLong(
			dictionary.get("groupId"), GroupConstants.DEFAULT_PARENT_GROUP_ID);

		if (groupId != GroupConstants.DEFAULT_PARENT_GROUP_ID) {
			_groupConfigurationBeans.put(
				groupId,
				ConfigurableUtil.createConfigurable(
					CookiesPreferenceHandlingConfiguration.class, dictionary));
			_groupIds.put(pid, groupId);
		}
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_systemCookiesPreferenceHandlingConfiguration =
			ConfigurableUtil.createConfigurable(
				CookiesPreferenceHandlingConfiguration.class, properties);
	}

	private CookiesPreferenceHandlingConfiguration
		_getCompanyCookiesPreferenceHandlingConfiguration(long companyId) {

		return _getCookiesPreferenceHandlingConfiguration(
			companyId, _companyConfigurationBeans,
			() -> _systemCookiesPreferenceHandlingConfiguration);
	}

	private CookiesPreferenceHandlingConfiguration
		_getCookiesPreferenceHandlingConfiguration(
			long key,
			Map<Long, CookiesPreferenceHandlingConfiguration>
				configurationBeans,
			Supplier<CookiesPreferenceHandlingConfiguration> supplier) {

		if (configurationBeans.containsKey(key)) {
			return configurationBeans.get(key);
		}

		return supplier.get();
	}

	private CookiesPreferenceHandlingConfiguration
		_getGroupCookiesPreferenceHandlingConfiguration(long groupId) {

		return _getCookiesPreferenceHandlingConfiguration(
			groupId, _groupConfigurationBeans,
			() -> {
				Group group = _groupLocalService.fetchGroup(groupId);

				long companyId = CompanyThreadLocal.getCompanyId();

				if (group != null) {
					companyId = group.getCompanyId();
				}

				return _getCompanyCookiesPreferenceHandlingConfiguration(
					companyId);
			});
	}

	private void _unmapPid(String pid) {
		if (_companyIds.containsKey(pid)) {
			long companyId = _companyIds.remove(pid);

			_companyConfigurationBeans.remove(companyId);

			_groupConfigurationBeans.clear();
			_groupIds.clear();
		}
		else if (_groupIds.containsKey(pid)) {
			long groupId = _groupIds.remove(pid);

			_groupConfigurationBeans.remove(groupId);
		}
	}

	private final Map<Long, CookiesPreferenceHandlingConfiguration>
		_companyConfigurationBeans = new ConcurrentHashMap<>();
	private final Map<String, Long> _companyIds = new ConcurrentHashMap<>();
	private final Map<Long, CookiesPreferenceHandlingConfiguration>
		_groupConfigurationBeans = new ConcurrentHashMap<>();
	private final Map<String, Long> _groupIds = new ConcurrentHashMap<>();

	@Reference
	private GroupLocalService _groupLocalService;

	private volatile CookiesPreferenceHandlingConfiguration
		_systemCookiesPreferenceHandlingConfiguration;

}