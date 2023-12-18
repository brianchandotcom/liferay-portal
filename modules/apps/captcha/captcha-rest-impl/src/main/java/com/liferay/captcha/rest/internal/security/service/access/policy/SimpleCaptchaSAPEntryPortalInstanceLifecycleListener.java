/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.captcha.rest.internal.security.service.access.policy;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.language.LanguageResources;
import com.liferay.portal.security.service.access.policy.model.SAPEntry;
import com.liferay.portal.security.service.access.policy.service.SAPEntryLocalService;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Quan Huynh
 */
@Component(service = PortalInstanceLifecycleListener.class)
public class SimpleCaptchaSAPEntryPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
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

	private void _addSAPEntry(long companyId) throws PortalException {
		SAPEntry sapEntry = _sapEntryLocalService.fetchSAPEntry(
			companyId, "CAPTCHA_DEFAULT");

		if (sapEntry != null) {
			return;
		}

		Map<Locale, String> map = ResourceBundleUtil.getLocalizationMap(
			LanguageResources.PORTAL_RESOURCE_BUNDLE_LOADER,
			"service-access-policy-entry-default-captcha-title");

		_sapEntryLocalService.addSAPEntry(
			_userLocalService.getGuestUserId(companyId),
			StringBundler.concat(
				"com.liferay.captcha.rest.internal.resource.v1_0.",
				"SimpleCaptchaResourceImpl#getSimpleCaptcha\n",
				"com.liferay.captcha.rest.internal.resource.v1_0.",
				"SimpleCaptchaResourceImpl#postSimpleCaptcha"),
			true, true, "CAPTCHA_DEFAULT", map, new ServiceContext());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SimpleCaptchaSAPEntryPortalInstanceLifecycleListener.class);

	@Reference
	private SAPEntryLocalService _sapEntryLocalService;

	@Reference
	private UserLocalService _userLocalService;

}