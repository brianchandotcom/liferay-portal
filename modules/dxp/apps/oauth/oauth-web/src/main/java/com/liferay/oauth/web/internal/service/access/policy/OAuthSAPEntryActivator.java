/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.oauth.web.internal.service.access.policy;

import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.AggregateResourceBundleLoader;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.language.LanguageResources;
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
 * @author Tomas Polesovsky
 */
@Component(immediate = true)
public class OAuthSAPEntryActivator {

	public static final Object[][] SAP_ENTRY_OBJECT_ARRAYS = {
		{"OAUTH_READ", "#fetch*\n#get*\n#has*\n#is*\n#search*", false},
		{"OAUTH_WRITE", "*", false}
	};

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceRegistration = bundleContext.registerService(
			PortalInstanceLifecycleListener.class,
			new PolicyPortalInstanceLifecycleListener(), null);
	}

	protected void addSAPEntry(long companyId) throws PortalException {
		for (Object[] sapEntryObjectArray : SAP_ENTRY_OBJECT_ARRAYS) {
			String name = String.valueOf(sapEntryObjectArray[0]);
			String allowedServiceSignatures = String.valueOf(
				sapEntryObjectArray[1]);
			boolean defaultSAPEntry = GetterUtil.getBoolean(
				sapEntryObjectArray[2]);

			SAPEntry sapEntry = _sapEntryLocalService.fetchSAPEntry(
				companyId, name);

			if (sapEntry != null) {
				continue;
			}

			ResourceBundleLoader resourceBundleLoader =
				new AggregateResourceBundleLoader(
					ResourceBundleUtil.getResourceBundleLoader(
						"content.Language",
						OAuthSAPEntryActivator.class.getClassLoader()),
					LanguageResources.RESOURCE_BUNDLE_LOADER);

			Map<Locale, String> titleMap =
				ResourceBundleUtil.getLocalizationMap(
					resourceBundleLoader,
					"service-access-policy-entry-default-title-" + name);

			_sapEntryLocalService.addSAPEntry(
				_userLocalService.getDefaultUserId(companyId),
				allowedServiceSignatures, defaultSAPEntry, true, name, titleMap,
				new ServiceContext());
		}
	}

	@Deactivate
	protected void deactivate() {
		if (_serviceRegistration != null) {
			_serviceRegistration.unregister();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OAuthSAPEntryActivator.class);

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
				addSAPEntry(company.getCompanyId());
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