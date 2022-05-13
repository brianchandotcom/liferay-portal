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

package com.liferay.portal.search.rest.internal.security.service.access.policy;

import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoaderUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
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
 * @author Petteri Karttunen
 */
@Component(immediate = true, service = {})
public class SuggestionsSAPEntryActivator {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceRegistration = bundleContext.registerService(
			PortalInstanceLifecycleListener.class,
			new SuggestionsPortalInstanceLifecycleListener(), null);
	}

	@Deactivate
	protected void deactivate() {
		if (_serviceRegistration != null) {
			_serviceRegistration.unregister();
		}
	}

	private void _addSAPEntry(long companyId) throws Exception {
		String name = _SAP_ENTRY_OBJECT_ARRAY[0];

		SAPEntry sapEntry = _sapEntryLocalService.fetchSAPEntry(
			companyId, name);

		if (sapEntry != null) {
			return;
		}

		Map<Locale, String> map = ResourceBundleUtil.getLocalizationMap(
			ResourceBundleLoaderUtil.getPortalResourceBundleLoader(), name);

		_sapEntryLocalService.addSAPEntry(
			_userLocalService.getDefaultUserId(companyId),
			_SAP_ENTRY_OBJECT_ARRAY[1], true, true, name, map,
			new ServiceContext());
	}

	private static final String[] _SAP_ENTRY_OBJECT_ARRAY = {
		"SEARCH_SUGGESTIONS",
		"com.liferay.portal.search.rest.internal.resource.v1_0." +
			"SuggestionResourceImpl#postSuggestionsPage"
	};

	private static final Log _log = LogFactoryUtil.getLog(
		SuggestionsSAPEntryActivator.class);

	@Reference
	private SAPEntryLocalService _sapEntryLocalService;

	private ServiceRegistration<PortalInstanceLifecycleListener>
		_serviceRegistration;

	@Reference
	private UserLocalService _userLocalService;

	private class SuggestionsPortalInstanceLifecycleListener
		extends BasePortalInstanceLifecycleListener {

		public void portalInstanceRegistered(Company company) throws Exception {
			try {
				_addSAPEntry(company.getCompanyId());
			}
			catch (PortalException portalException) {
				_log.error(
					"Unable to add service access policy entry for company " +
						company.getCompanyId(),
					portalException);
			}
		}

	}

}