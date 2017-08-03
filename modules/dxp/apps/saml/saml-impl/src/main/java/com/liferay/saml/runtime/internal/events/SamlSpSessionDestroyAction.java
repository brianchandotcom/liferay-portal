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

package com.liferay.saml.runtime.internal.events;

import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.LifecycleAction;
import com.liferay.portal.kernel.events.SessionAction;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.saml.persistence.model.SamlSpSession;
import com.liferay.saml.persistence.service.SamlSpSessionLocalService;
import com.liferay.saml.runtime.configuration.SamlProviderConfigurationHelper;

import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mika Koivisto
 */
@Component(
	immediate = true,
	property = "key=" + PropsKeys.SERVLET_SESSION_DESTROY_EVENTS,
	service = LifecycleAction.class
)
public class SamlSpSessionDestroyAction extends SessionAction {

	@Override
	public void run(HttpSession session) throws ActionException {
		Long userId = (Long)session.getAttribute(WebKeys.USER_ID);

		if (userId == null) {
			return;
		}

		long userCompanyId = 0;

		try {
			userCompanyId = _companyLocalService.getCompanyIdByUserId(userId);
		}
		catch (Exception e) {
		}

		if (userCompanyId == 0) {
			return;
		}

		long companyId = CompanyThreadLocal.getCompanyId();

		CompanyThreadLocal.setCompanyId(userCompanyId);

		try {
			doRun(session);
		}
		finally {
			CompanyThreadLocal.setCompanyId(companyId);
		}
	}

	protected void doRun(HttpSession session) throws ActionException {
		if (!_samlProviderConfigurationHelper.isEnabled() ||
			!_samlProviderConfigurationHelper.isRoleSp()) {

			return;
		}

		try {
			SamlSpSession samlSpSession =
				_samlSpSessionLocalService.getSamlSpSessionByJSessionId(
					session.getId());

			if (_log.isDebugEnabled()) {
				_log.debug(
					"HTTP session " + session.getId() +
						" expiring SAML SP session " +
							samlSpSession.getSamlSpSessionKey());
			}

			_samlSpSessionLocalService.deleteSamlSpSession(
				samlSpSession.getSamlSpSessionId());
		}
		catch (Exception e) {
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SamlSpSessionDestroyAction.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private SamlProviderConfigurationHelper _samlProviderConfigurationHelper;

	@Reference
	private SamlSpSessionLocalService _samlSpSessionLocalService;

}