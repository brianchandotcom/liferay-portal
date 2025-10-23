/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.sso.openid.connect.persistence.service.impl;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.sso.openid.connect.persistence.exception.DuplicateOpenIdConnectUserException;
import com.liferay.portal.security.sso.openid.connect.persistence.exception.OpenIdConnectUserIssuerException;
import com.liferay.portal.security.sso.openid.connect.persistence.exception.OpenIdConnectUserSubjectException;
import com.liferay.portal.security.sso.openid.connect.persistence.model.OpenIdConnectUser;
import com.liferay.portal.security.sso.openid.connect.persistence.service.base.OpenIdConnectUserLocalServiceBaseImpl;

import java.util.Date;

import org.osgi.service.component.annotations.Component;

/**
 * @author Arthur Chan
 */
@Component(
	property = "model.class.name=com.liferay.portal.security.sso.openid.connect.persistence.model.OpenIdConnectUser",
	service = AopService.class
)
public class OpenIdConnectUserLocalServiceImpl
	extends OpenIdConnectUserLocalServiceBaseImpl {

	@Override
	public OpenIdConnectUser addOpenIdConnectUser(
			long companyId, long userId, String issuer, String subject)
		throws PortalException {

		OpenIdConnectUser openIdConnectUser =
			openIdConnectUserPersistence.fetchByC_I_S(
				companyId, issuer, subject);

		if (openIdConnectUser != null) {
			throw new DuplicateOpenIdConnectUserException();
		}

		long openIdConnectUserId = counterLocalService.increment();

		openIdConnectUser = openIdConnectUserPersistence.create(
			openIdConnectUserId);

		openIdConnectUser.setCompanyId(companyId);
		openIdConnectUser.setUserId(userId);
		openIdConnectUser.setCreateDate(new Date());

		if (Validator.isNull(issuer)) {
			throw new OpenIdConnectUserIssuerException("Issuer is null");
		}

		openIdConnectUser.setIssuer(issuer);

		if (Validator.isNull(subject)) {
			throw new OpenIdConnectUserSubjectException("Subject is null");
		}

		openIdConnectUser.setSubject(subject);

		return openIdConnectUserPersistence.update(openIdConnectUser);
	}

	@Override
	public OpenIdConnectUser fetchOpenIdConnectUser(
		long companyId, String issuer, String subject) {

		return openIdConnectUserPersistence.fetchByC_I_S(
			companyId, issuer, subject);
	}

}