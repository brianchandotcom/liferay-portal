/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.cookies.service.impl;

import com.liferay.cookies.model.ConsentPreference;
import com.liferay.cookies.service.base.ConsentPreferenceLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.cookies.model.ConsentPreference",
	service = AopService.class
)
public class ConsentPreferenceLocalServiceImpl
	extends ConsentPreferenceLocalServiceBaseImpl {

	public ConsentPreference addConsentPreference(
			long userId, String domain, Date expirationDate, String name,
			String value)
		throws PortalException {

		ConsentPreference consentPreference =
			consentPreferencePersistence.create(
				counterLocalService.increment(
					ConsentPreference.class.getName()));

		User user = _userLocalService.getUser(userId);

		consentPreference.setUserId(userId);
		consentPreference.setUserName(user.getFullName());

		consentPreference.setDomain(domain);
		consentPreference.setExpirationDate(expirationDate);
		consentPreference.setName(name);
		consentPreference.setValue(value);

		return consentPreferencePersistence.update(consentPreference);
	}

	public void deleteConsentPreference(long userId, String domain, String name)
		throws PortalException {

		consentPreferencePersistence.removeByU_D_N(userId, domain, name);
	}

	public void deleteConsentPreferences(long userId) {
		consentPreferencePersistence.removeByUserId(userId);
	}

	public void deleteConsentPreferences(long userId, String domain) {
		consentPreferencePersistence.removeByU_D(userId, domain);
	}

	public ConsentPreference fetchConsentPreference(
		long userId, String domain, String name) {

		return consentPreferencePersistence.fetchByU_D_N(userId, domain, name);
	}

	public List<ConsentPreference> getConsentPreferences(
		long userId, String domain) {

		return consentPreferencePersistence.findByU_D(userId, domain);
	}

	@Reference
	private UserLocalService _userLocalService;

}