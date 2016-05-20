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

package com.liferay.push.notifications.security.service.access.policy;

import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.security.service.access.policy.model.SAPEntry;
import com.liferay.portal.security.service.access.policy.service.SAPEntryLocalService;
import com.liferay.push.notifications.service.PushNotificationsDeviceService;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tomas Polesovsky
 */
@Component(immediate = true)
public class PushNotificationsDefaultPolicy {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceRegistration = bundleContext.registerService(
			PortalInstanceLifecycleListener.class,
			new PolicyPortalInstanceLifecycleListener(), null);
	}

	protected void create(long companyId) throws PortalException {
		SAPEntry sapEntry = _sapEntryLocalService.fetchSAPEntry(
			companyId, _PUSH_NOTIFICATIONS_DEFAULT);

		if (sapEntry != null) {
			return;
		}

		StringBundler sb = new StringBundler(5);

		sb.append(PushNotificationsDeviceService.class.getName());
		sb.append("#addPushNotificationsDevice");
		sb.append(StringPool.NEW_LINE);
		sb.append(PushNotificationsDeviceService.class.getName());
		sb.append("#deletePushNotificationsDevice");

		String allowedServiceSignatures = sb.toString();
		boolean defaultSAPEntry = true;
		boolean enabled = true;

		Map<Locale, String> titleMap = new HashMap<>();
		titleMap.put(LocaleUtil.getDefault(), _PUSH_NOTIFICATIONS_DEFAULT);

		_sapEntryLocalService.addSAPEntry(
			_userLocalService.getDefaultUserId(companyId),
			allowedServiceSignatures, defaultSAPEntry, enabled,
			_PUSH_NOTIFICATIONS_DEFAULT, titleMap, new ServiceContext());
	}

	@Deactivate
	protected void deactivate() {
		if (_serviceRegistration != null) {
			_serviceRegistration.unregister();
		}
	}

	private static final String _PUSH_NOTIFICATIONS_DEFAULT =
		"PUSH_NOTIFICATIONS_DEFAULT";

	private static final Log _log = LogFactoryUtil.getLog(
		PushNotificationsDefaultPolicy.class);

	@Reference(unbind = "-")
	private SAPEntryLocalService _sapEntryLocalService;

	private ServiceRegistration<PortalInstanceLifecycleListener>
		_serviceRegistration;

	@Reference(unbind = "-")
	private UserLocalService _userLocalService;

	private class PolicyPortalInstanceLifecycleListener
		extends BasePortalInstanceLifecycleListener {

		public void portalInstanceRegistered(Company company) throws Exception {
			try {
				create(company.getCompanyId());
			}
			catch (PortalException pe) {
				_log.error(
					"Unable to add PushNotificationsDefaultPolicy " +
						"for company " + company.getCompanyId(),
					pe);
			}
		}

	}

}