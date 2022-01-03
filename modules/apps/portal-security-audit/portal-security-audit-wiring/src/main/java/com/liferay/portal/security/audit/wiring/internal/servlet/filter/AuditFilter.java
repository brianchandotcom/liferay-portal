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

package com.liferay.portal.security.audit.wiring.internal.servlet.filter;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.audit.AuditRequestThreadLocal;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogContext;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.BaseFilter;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.TryFilter;
import com.liferay.portal.kernel.util.DigesterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.security.audit.wiring.internal.configuration.AuditLogContextConfiguration;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 * @author Arthur Chan
 */
@Component(
	configurationPid = "com.liferay.portal.security.audit.wiring.internal.configuration.AuditLogContextConfiguration",
	enabled = false, immediate = true,
	property = {
		"after-filter=Session Max Allowed Filter", "servlet-context-name=",
		"servlet-filter-name=Audit Filter", "url-pattern=/*",
		"url-regex-ignore-pattern=^/html/.+\\.(css|gif|html|ico|jpg|js|png)(\\?.*)?$"
	},
	service = Filter.class
)
public class AuditFilter extends BaseFilter implements TryFilter {

	@Override
	public Object doFilterTry(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		AuditRequestThreadLocal auditRequestThreadLocal =
			AuditRequestThreadLocal.getAuditThreadLocal();

		auditRequestThreadLocal.setClientHost(
			httpServletRequest.getRemoteHost());

		String remoteAddr = _getRemoteAddr(httpServletRequest);

		auditRequestThreadLocal.setClientIP(remoteAddr);

		auditRequestThreadLocal.setQueryString(
			httpServletRequest.getQueryString());

		HttpSession httpSession = httpServletRequest.getSession();

		Long userId = (Long)httpSession.getAttribute(WebKeys.USER_ID);

		if (userId != null) {
			auditRequestThreadLocal.setRealUserId(userId.longValue());
		}

		StringBuffer sb = httpServletRequest.getRequestURL();

		auditRequestThreadLocal.setRequestURL(sb.toString());

		auditRequestThreadLocal.setServerName(
			httpServletRequest.getServerName());
		auditRequestThreadLocal.setServerPort(
			httpServletRequest.getServerPort());
		auditRequestThreadLocal.setSessionID(httpSession.getId());

		if (!_auditLogContextConfiguration.enabled()) {
			return null;
		}

		String xRequestId = null;

		if (_auditLogContextConfiguration.useIncomingXRequestId()) {
			xRequestId = httpServletRequest.getHeader(HttpHeaders.X_REQUEST_ID);
		}

		if (!_isValidXRequestId(xRequestId)) {
			xRequestId = PortalUUIDUtil.generate();
		}

		httpServletResponse.setHeader(HttpHeaders.X_REQUEST_ID, xRequestId);

		_auditLogContext.setContext(
			remoteAddr, _portal.getCompanyId(httpServletRequest),
			httpSession.getId(), httpServletRequest.getServerName(), userId,
			xRequestId);

		return null;
	}

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		_auditLogContext = new AuditLogContext();

		_serviceRegistration = bundleContext.registerService(
			LogContext.class, _auditLogContext, new HashMapDictionary());

		_auditLogContextConfiguration = ConfigurableUtil.createConfigurable(
			AuditLogContextConfiguration.class, properties);
	}

	@Deactivate
	protected void deactivate() {
		if (_serviceRegistration != null) {
			_serviceRegistration.unregister();
		}
	}

	@Override
	protected Log getLog() {
		return _log;
	}

	private String _getRemoteAddr(HttpServletRequest httpServletRequest) {
		String remoteAddr = httpServletRequest.getHeader(
			HttpHeaders.X_FORWARDED_FOR);

		if (remoteAddr != null) {
			return remoteAddr;
		}

		return httpServletRequest.getRemoteAddr();
	}

	private boolean _isValidXRequestId(String xRequestId) {
		if (Validator.isBlank(xRequestId)) {
			if (_log.isDebugEnabled()) {
				_log.debug("Incoming X-Request-Id is empty");
			}

			return false;
		}

		if ((xRequestId.length() < 20) || (xRequestId.length() > 200)) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Incoming X-Request-Id has an invalid length: " +
						xRequestId.length());
			}

			return false;
		}

		for (int i = 0; i < xRequestId.length(); ++i) {
			if ((xRequestId.charAt(i) < 32) || (xRequestId.charAt(i) > 126)) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Incoming X-Request-Id contains invalid characters");
				}

				return false;
			}
		}

		return true;
	}

	private static final String _MESSAGE_DIGEST_ALGORITHM = "SHA-256";

	private static final Log _log = LogFactoryUtil.getLog(AuditFilter.class);

	private AuditLogContext _auditLogContext;
	private AuditLogContextConfiguration _auditLogContextConfiguration;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private Portal _portal;

	private ServiceRegistration<LogContext> _serviceRegistration;

	@Reference
	private UserLocalService _userLocalService;

	private class AuditLogContext implements LogContext {

		public AuditLogContext() {
			_contexts = new CentralizedThreadLocal<>(
				AuditLogContext.class + "._contexts",
				HashMap<String, String>::new);
		}

		@Override
		public Map<String, String> getContext() {
			return _contexts.get();
		}

		@Override
		public String getName() {
			return AuditLogContext.class.getSimpleName();
		}

		public void setContext(
			String clientIP, long companyId, String sessionId,
			String serverName, Long userId, String xRequestId) {

			_contexts.set(
				HashMapBuilder.put(
					"clientIP", clientIP
				).put(
					"companyId", String.valueOf(companyId)
				).put(
					"emailAddress",
					() -> {
						if (userId != null) {
							User user = _userLocalService.fetchUser(userId);

							if (user != null) {
								return DigesterUtil.digest(
									_MESSAGE_DIGEST_ALGORITHM,
									user.getEmailAddress());
							}
						}

						return "";
					}
				).put(
					"serverName", serverName
				).put(
					"sessionId",
					DigesterUtil.digest(_MESSAGE_DIGEST_ALGORITHM, sessionId)
				).put(
					"userId", (userId != null) ? String.valueOf(userId) : ""
				).put(
					"virtualHostName",
					() -> {
						if (companyId >= 0) {
							Company company = _companyLocalService.fetchCompany(
								companyId);

							if (company != null) {
								return company.getVirtualHostname();
							}
						}

						return "";
					}
				).put(
					"xRequestId", xRequestId
				).build());
		}

		private final ThreadLocal<Map<String, String>> _contexts;

	}

}