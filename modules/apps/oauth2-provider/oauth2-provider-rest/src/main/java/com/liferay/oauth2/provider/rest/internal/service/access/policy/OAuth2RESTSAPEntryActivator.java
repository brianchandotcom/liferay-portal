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

package com.liferay.oauth2.provider.rest.internal.service.access.policy;

import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleLoaderUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.security.service.access.policy.model.SAPEntry;
import com.liferay.portal.security.service.access.policy.service.SAPEntryLocalService;

import java.util.Locale;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carlos Sierra Andrés
 * @author Tomas Polesovsky
 */
@Component(immediate = true, service = {})
public class OAuth2RESTSAPEntryActivator {

	public void addSAPEntries(long companyId) throws PortalException {
		for (String[] sapEntryObjectArray : _SAP_ENTRY_OBJECT_ARRAYS) {
			String name = sapEntryObjectArray[0];

			SAPEntry sapEntry = _sapEntryLocalService.fetchSAPEntry(
				companyId, name);

			if (sapEntry != null) {
				continue;
			}

			String allowedServiceSignatures = sapEntryObjectArray[1];

			ResourceBundleLoader resourceBundleLoader =
				ResourceBundleLoaderUtil.
					getResourceBundleLoaderByBundleSymbolicName(
						"com.liferay.oauth2.provider.rest");

			Map<Locale, String> map = ResourceBundleUtil.getLocalizationMap(
				resourceBundleLoader, name);

			_sapEntryLocalService.addSAPEntry(
				_userLocalService.getDefaultUserId(companyId),
				allowedServiceSignatures, false, true, name, map,
				new ServiceContext());
		}
	}

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		boolean createSapEntriesOnStartup = MapUtil.getBoolean(
			properties, "oauth2.create.oauth2.sap.entries.on.startup", true);

		if (!createSapEntriesOnStartup) {
			return;
		}

		_serviceRegistration = bundleContext.registerService(
			PortalInstanceLifecycleListener.class,
			new PolicyPortalInstanceLifecycleListener(), null);
	}

	@Deactivate
	protected void deactivate() {
		if (_serviceRegistration != null) {
			_serviceRegistration.unregister();
		}
	}

	private static final String[][] _SAP_ENTRY_OBJECT_ARRAYS = {
		{"AUTHORIZED_OAUTH2_SAP", "*"}
	};

	private static final Log _log = LogFactoryUtil.getLog(
		OAuth2RESTSAPEntryActivator.class);

	@Reference
	private SAPEntryLocalService _sapEntryLocalService;

	private ServiceRegistration<PortalInstanceLifecycleListener>
		_serviceRegistration;

	@Reference
	private UserLocalService _userLocalService;

	private class PolicyPortalInstanceLifecycleListener
		extends BasePortalInstanceLifecycleListener {

		public void portalInstanceRegistered(Company company) throws Exception {
			try {
				addSAPEntries(company.getCompanyId());
			}
			catch (PortalException pe) {
				_log.error(
					"Unable to add service access policy entry for company " +
						company.getCompanyId(),
					pe);
			}
		}

	}

}