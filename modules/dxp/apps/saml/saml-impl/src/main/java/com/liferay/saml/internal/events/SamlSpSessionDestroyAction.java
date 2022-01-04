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

package com.liferay.saml.internal.events;

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
	public void run(HttpSession httpSession) throws ActionException {
		Long userId = (Long)httpSession.getAttribute(WebKeys.USER_ID);

		if (userId == null) {
			return;
		}

		long userCompanyId = 0;

		try {
			userCompanyId = _companyLocalService.getCompanyIdByUserId(userId);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
		}

		if (userCompanyId == 0) {
			return;
		}

		long companyId = CompanyThreadLocal.getCompanyId();

		CompanyThreadLocal.setCompanyId(userCompanyId);

		try {
			_run(httpSession);
		}
		finally {
			CompanyThreadLocal.setCompanyId(companyId);
		}
	}

	private void _run(HttpSession httpSession) throws ActionException {
		if (!_samlProviderConfigurationHelper.isEnabled() ||
			!_samlProviderConfigurationHelper.isRoleSp()) {

			return;
		}

		SamlSpSession samlSpSession =
			_samlSpSessionLocalService.fetchSamlSpSessionByJSessionId(
				httpSession.getId());

		if (samlSpSession == null) {
			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"HTTP session expiring SAML SP session " +
					samlSpSession.getSamlSpSessionKey());
		}

		try {
			_samlSpSessionLocalService.deleteSamlSpSession(
				samlSpSession.getSamlSpSessionId());
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
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