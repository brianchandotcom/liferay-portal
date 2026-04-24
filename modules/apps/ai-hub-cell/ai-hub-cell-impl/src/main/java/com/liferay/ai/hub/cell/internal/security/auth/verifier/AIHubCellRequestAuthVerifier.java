/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.cell.internal.security.auth.verifier;

import com.liferay.ai.hub.cell.security.JWTTokenUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.security.auth.AccessControlContext;
import com.liferay.portal.kernel.security.auth.AuthException;
import com.liferay.portal.kernel.security.auth.verifier.AuthVerifier;
import com.liferay.portal.kernel.security.auth.verifier.AuthVerifierResult;
import com.liferay.portal.kernel.security.service.access.policy.ServiceAccessPolicy;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(
	property = "auth.verifier.AIHubCellRequestAuthVerifier.urls.includes=*",
	service = AuthVerifier.class
)
public class AIHubCellRequestAuthVerifier implements AuthVerifier {

	@Override
	public String getAuthType() {
		Class<?> clazz = getClass();

		return clazz.getSimpleName();
	}

	@Override
	public AuthVerifierResult verify(
			AccessControlContext accessControlContext, Properties properties)
		throws AuthException {

		try {
			HttpServletRequest httpServletRequest =
				accessControlContext.getRequest();

			String token = httpServletRequest.getHeader(
				"Liferay-AI-Hub-Cell-On-Behalf-Of");

			if (Validator.isBlank(token)) {
				return new AuthVerifierResult();
			}

			Company company = _companyLocalService.getCompany(
				_portal.getCompanyId(httpServletRequest));

			long userId = JWTTokenUtil.getUserId(
				company.getVirtualHostname(), token);

			if (userId == 0) {
				AuthVerifierResult authVerifierResult =
					new AuthVerifierResult();

				authVerifierResult.setState(
					AuthVerifierResult.State.INVALID_CREDENTIALS);

				return authVerifierResult;
			}

			AuthVerifierResult authVerifierResult = new AuthVerifierResult();

			Map<String, Object> settings = authVerifierResult.getSettings();

			List<String> serviceAccessPolicyNames =
				(List<String>)settings.computeIfAbsent(
					ServiceAccessPolicy.SERVICE_ACCESS_POLICY_NAMES,
					value -> new ArrayList<>());

			serviceAccessPolicyNames.add("AI_HUB_CELL_TOKEN");

			authVerifierResult.setState(AuthVerifierResult.State.SUCCESS);
			authVerifierResult.setUserId(userId);

			return authVerifierResult;
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to verify AI Hub Cell JWT token", exception);
			}

			return new AuthVerifierResult();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AIHubCellRequestAuthVerifier.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private Portal _portal;

}