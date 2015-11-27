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

package com.liferay.portal.security.sso.ntlm.servlet.filters;

import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.SingleVMPool;
import com.liferay.portal.kernel.io.BigEndianCodec;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationFactory;
import com.liferay.portal.kernel.security.SecureRandomUtil;
import com.liferay.portal.kernel.servlet.BaseFilter;
import com.liferay.portal.kernel.servlet.BrowserSnifferUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.sso.ntlm.NetlogonConnectionManager;
import com.liferay.portal.security.sso.ntlm.NtlmManager;
import com.liferay.portal.security.sso.ntlm.NtlmUserAccount;
import com.liferay.portal.security.sso.ntlm.constants.NtlmConstants;
import com.liferay.portal.security.sso.ntlm.constants.NtlmWebKeys;
import com.liferay.portal.security.sso.ntlm.module.configuration.NtlmConfiguration;
import com.liferay.portal.util.PortalInstances;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jcifs.Config;

import jcifs.util.Base64;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bruno Farache
 * @author Marcus Schmidke
 * @author Brian Wing Shun Chan
 * @author Wesley Gong
 * @author Marcellus Tavares
 * @author Michael C. Han
 */
@Component(
	configurationPid = "com.liferay.portal.security.sso.ntlm.module.configuration.NtlmConfiguration",
	immediate = true,
	property = {
		"dispatcher=FORWARD", "dispatcher=REQUEST", "servlet-context-name=",
		"servlet-filter-name=SSO Ntlm Filter", "url-pattern=/c/portal/login"
	},
	service = Filter.class
)
public class NtlmFilter extends BaseFilter {

	@Override
	public boolean isFilterEnabled(
		HttpServletRequest request, HttpServletResponse response) {

		try {
			long companyId = PortalInstances.getCompanyId(request);

			NtlmConfiguration ntlmConfiguration =
				_configurationFactory.getConfiguration(
					NtlmConfiguration.class,
					new CompanyServiceSettingsLocator(
						companyId, NtlmConstants.SERVICE_NAME));

			if (BrowserSnifferUtil.isIe(request) &&
				ntlmConfiguration.enabled()) {

				return true;
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return false;
	}

	@Reference(unbind = "-")
	public void setNetlogonConnectionManager(
		NetlogonConnectionManager netlogonConnectionManager) {

		_netlogonConnectionManager = netlogonConnectionManager;
	}

	@Reference(unbind = "-")
	public void setSingleVMPool(SingleVMPool singleVMPool) {
		_portalCache = (PortalCache<String, byte[]>)singleVMPool.getPortalCache(
			NtlmFilter.class.getName());
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		for (Map.Entry<String, Object> entry : properties.entrySet()) {
			String key = entry.getKey();

			if (key.contains("jcifs.")) {
				String value = (String)entry.getValue();

				Config.setProperty(key, value);
			}
		}

		_ntlmManagers.clear();
	}

	@Override
	protected Log getLog() {
		return _log;
	}

	protected NtlmManager getNtlmManager(long companyId) throws Exception {
		NtlmConfiguration ntlmConfiguration =
			_configurationFactory.getConfiguration(
				NtlmConfiguration.class,
				new CompanyServiceSettingsLocator(
					companyId, NtlmConstants.SERVICE_NAME));

		String domain = ntlmConfiguration.domain();
		String domainController = ntlmConfiguration.domainController();
		String domainControllerName = ntlmConfiguration.domainControllerName();
		String serviceAccount = ntlmConfiguration.serviceAccount();
		String servicePassword = ntlmConfiguration.servicePassword();

		NtlmManager ntlmManager = _ntlmManagers.get(companyId);

		if (ntlmManager == null) {
			ntlmManager = new NtlmManager(
				_netlogonConnectionManager, domain, domainController,
				domainControllerName, serviceAccount, servicePassword);

			_ntlmManagers.put(companyId, ntlmManager);
		}
		else {
			if (!Validator.equals(ntlmManager.getDomain(), domain) ||
				!Validator.equals(
					ntlmManager.getDomainController(), domainController) ||
				!Validator.equals(
					ntlmManager.getDomainControllerName(),
					domainControllerName) ||
				!Validator.equals(
					ntlmManager.getServiceAccount(), serviceAccount) ||
				!Validator.equals(
					ntlmManager.getServicePassword(), servicePassword)) {

				ntlmManager.setConfiguration(
					domain, domainController, domainControllerName,
					serviceAccount, servicePassword);
			}
		}

		return ntlmManager;
	}

	protected String getPortalCacheKey(HttpServletRequest request) {
		HttpSession session = request.getSession(false);

		if (session == null) {
			return request.getRemoteAddr();
		}

		return session.getId();
	}

	@Override
	protected void processFilter(
			HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
		throws Exception {

		// Type 1 NTLM requests from browser can (and should) always immediately
		// be replied to with an Type 2 NTLM response, no matter whether we're
		// yet logging in or whether it is much later in the session.

		HttpSession session = request.getSession(false);

		long companyId = PortalInstances.getCompanyId(request);

		String authorization = GetterUtil.getString(
			request.getHeader(HttpHeaders.AUTHORIZATION));

		if (authorization.startsWith("NTLM")) {
			NtlmManager ntlmManager = getNtlmManager(companyId);

			String portalCacheKey = getPortalCacheKey(request);

			byte[] src = Base64.decode(authorization.substring(5));

			if (src[8] == 1) {
				byte[] serverChallenge = new byte[8];

				BigEndianCodec.putLong(
					serverChallenge, 0, SecureRandomUtil.nextLong());

				byte[] challengeMessage = ntlmManager.negotiate(
					src, serverChallenge);

				authorization = Base64.encode(challengeMessage);

				response.setContentLength(0);
				response.setHeader(
					HttpHeaders.WWW_AUTHENTICATE, "NTLM " + authorization);
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

				response.flushBuffer();

				_portalCache.put(portalCacheKey, serverChallenge);

				// Interrupt filter chain, send response. Browser will
				// immediately post a new request.

				return;
			}

			byte[] serverChallenge = _portalCache.get(portalCacheKey);

			if (serverChallenge == null) {
				response.setContentLength(0);
				response.setHeader(HttpHeaders.WWW_AUTHENTICATE, "NTLM");
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

				response.flushBuffer();

				return;
			}

			NtlmUserAccount ntlmUserAccount = null;

			try {
				ntlmUserAccount = ntlmManager.authenticate(
					src, serverChallenge);
			}
			catch (Exception e) {
				if (_log.isErrorEnabled()) {
					_log.error("Unable to perform NTLM authentication", e);
				}
			}
			finally {
				_portalCache.remove(portalCacheKey);
			}

			if (ntlmUserAccount == null) {
				response.setContentLength(0);
				response.setHeader(HttpHeaders.WWW_AUTHENTICATE, "NTLM");
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

				response.flushBuffer();

				return;
			}

			if (_log.isDebugEnabled()) {
				_log.debug("NTLM remote user " + ntlmUserAccount.getUserName());
			}

			request.setAttribute(
				NtlmWebKeys.NTLM_REMOTE_USER, ntlmUserAccount.getUserName());

			if (session != null) {
				session.setAttribute(
					NtlmWebKeys.NTLM_USER_ACCOUNT, ntlmUserAccount);
			}
		}

		String path = request.getPathInfo();

		if ((path != null) && path.endsWith("/login")) {
			NtlmUserAccount ntlmUserAccount = null;

			if (session != null) {
				ntlmUserAccount = (NtlmUserAccount)session.getAttribute(
					NtlmWebKeys.NTLM_USER_ACCOUNT);
			}

			if (ntlmUserAccount == null) {
				response.setContentLength(0);
				response.setHeader(HttpHeaders.WWW_AUTHENTICATE, "NTLM");
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

				response.flushBuffer();

				return;
			}
			else {
				request.setAttribute(
					NtlmWebKeys.NTLM_REMOTE_USER,
					ntlmUserAccount.getUserName());
			}
		}

		processFilter(
			NtlmPostFilter.class.getName(), request, response, filterChain);
	}

	@Reference(unbind = "-")
	protected void setConfigurationFactory(
		ConfigurationFactory configurationFactory) {

		_configurationFactory = configurationFactory;
	}

	private static final Log _log = LogFactoryUtil.getLog(NtlmFilter.class);

	private volatile ConfigurationFactory _configurationFactory;
	private volatile NetlogonConnectionManager _netlogonConnectionManager;
	private final Map<Long, NtlmManager> _ntlmManagers =
		new ConcurrentHashMap<>();
	private PortalCache<String, byte[]> _portalCache;

}