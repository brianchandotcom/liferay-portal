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

package com.liferay.portal.util;

import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.events.EventsProcessorUtil;
import com.liferay.portal.kernel.cookies.CookiesManagerUtil;
import com.liferay.portal.kernel.cookies.constants.CookiesConstants;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.exception.NoSuchVirtualHostException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.PortletCategory;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.VirtualHost;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.service.VirtualHostLocalServiceUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 * @author Jose Oliver
 * @author Atul Patel
 * @author Mika Koivisto
 */
public class PortalInstances {

	public static long getCompanyId(HttpServletRequest httpServletRequest) {
		try {
			return getCompanyId(httpServletRequest, false);
		}
		catch (NoSuchVirtualHostException noSuchVirtualHostException) {
			_log.error(noSuchVirtualHostException);

			return 0;
		}
	}

	public static long getCompanyId(
			HttpServletRequest httpServletRequest, boolean strict)
		throws NoSuchVirtualHostException {

		if (_log.isDebugEnabled()) {
			_log.debug("Get company id");
		}

		Long companyIdObj = (Long)httpServletRequest.getAttribute(
			WebKeys.COMPANY_ID);

		if (_log.isDebugEnabled()) {
			_log.debug("Company id from request " + companyIdObj);
		}

		if (companyIdObj != null) {
			return companyIdObj.longValue();
		}

		long companyId = _getCompanyIdByVirtualHosts(
			httpServletRequest, strict);

		if (_log.isDebugEnabled()) {
			_log.debug("Company id from host " + companyId);
		}

		if (companyId <= 0) {
			long cookieCompanyId = GetterUtil.getLong(
				CookiesManagerUtil.getCookieValue(
					CookiesConstants.NAME_COMPANY_ID, httpServletRequest,
					false));

			if (cookieCompanyId > 0) {
				try {
					Company cookieCompany =
						CompanyLocalServiceUtil.fetchCompanyById(
							cookieCompanyId);

					if (cookieCompany == null) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								"Company id from cookie " + cookieCompanyId +
									" does not exist");
						}
					}
					else {
						companyId = cookieCompanyId;

						if (_log.isDebugEnabled()) {
							_log.debug("Company id from cookie " + companyId);
						}
					}
				}
				catch (Exception exception) {
					_log.error(exception);
				}
			}
		}

		if (companyId <= 0) {
			companyId = getDefaultCompanyId();

			if (_log.isDebugEnabled()) {
				_log.debug("Default company id " + companyId);
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Set company id " + companyId);
		}

		httpServletRequest.setAttribute(
			WebKeys.COMPANY_ID, Long.valueOf(companyId));

		CompanyThreadLocal.setCompanyId(companyId);

		if (Validator.isNotNull(PropsValues.VIRTUAL_HOSTS_DEFAULT_SITE_NAME) &&
			(httpServletRequest.getAttribute(WebKeys.VIRTUAL_HOST_LAYOUT_SET) ==
				null)) {

			try {
				Group group = GroupLocalServiceUtil.getGroup(
					companyId, PropsValues.VIRTUAL_HOSTS_DEFAULT_SITE_NAME);

				LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
					group.getGroupId(), false);

				TreeMap<String, String> virtualHostnames =
					layoutSet.getVirtualHostnames();

				if (virtualHostnames.isEmpty() ||
					_isCompanyVirtualHostname(
						companyId, httpServletRequest.getServerName())) {

					httpServletRequest.setAttribute(
						WebKeys.VIRTUAL_HOST_LAYOUT_SET, layoutSet);
				}
			}
			catch (Exception exception) {
				_log.error(exception);
			}
		}

		return companyId;
	}

	public static long[] getCompanyIds() {
		return ArrayUtil.toLongArray(_companies.keySet());
	}

	public static long[] getCompanyIdsBySQL() throws SQLException {
		List<Long> companyIds = new ArrayList<>();

		long defaultCompanyId = getDefaultCompanyIdBySQL();

		if (defaultCompanyId != 0) {
			companyIds.add(defaultCompanyId);
		}

		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				_GET_COMPANY_IDS);
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				long companyId = resultSet.getLong("companyId");

				if (companyId != defaultCompanyId) {
					companyIds.add(companyId);
				}
			}
		}

		return ArrayUtil.toArray(companyIds.toArray(new Long[0]));
	}

	public static long getDefaultCompanyId() {
		if (_companies.isEmpty()) {
			try {
				return getDefaultCompanyIdBySQL();
			}
			catch (SQLException sqlException) {
				_log.error(
					"Unable to get the default company ID by SQL",
					sqlException);

				throw new RuntimeException(sqlException);
			}
		}

		for (Map.Entry<Long, String> entry : _companies.entrySet()) {
			if (Objects.equals(
					PropsValues.COMPANY_DEFAULT_WEB_ID, entry.getValue())) {

				return entry.getKey();
			}
		}

		throw new IllegalStateException("Unable to get default company id");
	}

	public static long getDefaultCompanyIdBySQL() throws SQLException {
		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				"select companyId from Company where webId = '" +
					PropsValues.COMPANY_DEFAULT_WEB_ID + "'");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			if (resultSet.next()) {
				return resultSet.getLong(1);
			}
		}

		return 0;
	}

	public static String[] getWebIds() {
		return ArrayUtil.toStringArray(_companies.values());
	}

	public static long initCompany(String webId) {
		return initCompany(webId, false);
	}

	public static long initCompany(String webId, boolean skipCheck) {

		// Begin initializing company

		if (_log.isDebugEnabled()) {
			_log.debug("Begin initializing company with web id " + webId);
		}

		long companyId = 0;

		try {
			Company company = CompanyLocalServiceUtil.getCompanyByWebId(webId);

			companyId = company.getCompanyId();
		}
		catch (Exception exception) {
			_log.error(exception);

			return companyId;
		}

		Long currentThreadCompanyId = CompanyThreadLocal.getCompanyId();

		String currentThreadPrincipalName = PrincipalThreadLocal.getName();

		try {
			CompanyThreadLocal.setCompanyId(companyId);

			if (!skipCheck) {
				try {
					CompanyLocalServiceUtil.checkCompany(webId);
				}
				catch (Exception exception) {
					_log.error(exception);
				}
			}

			String principalName = null;

			long userId = PrincipalThreadLocal.getUserId();

			if (userId > 0) {
				User user = UserLocalServiceUtil.fetchUser(userId);

				if ((user != null) && (user.getCompanyId() == companyId)) {
					principalName = currentThreadPrincipalName;
				}
			}

			PrincipalThreadLocal.setName(principalName);

			// Initialize display

			if (_log.isDebugEnabled()) {
				_log.debug("Initialize display");
			}

			try {
				PortletCategory portletCategory =
					(PortletCategory)WebAppPool.get(
						companyId, WebKeys.PORTLET_CATEGORY);

				if (portletCategory == null) {
					portletCategory = new PortletCategory();
				}

				for (long currentCompanyId : _companies.keySet()) {
					PortletCategory currentPortletCategory =
						(PortletCategory)WebAppPool.get(
							currentCompanyId, WebKeys.PORTLET_CATEGORY);

					if (currentPortletCategory != null) {
						portletCategory.merge(currentPortletCategory);
					}
				}

				WebAppPool.put(
					companyId, WebKeys.PORTLET_CATEGORY, portletCategory);
			}
			catch (Exception exception) {
				_log.error(exception);
			}

			// Process application startup events

			if (_log.isDebugEnabled()) {
				_log.debug("Process application startup events");
			}

			try {
				EventsProcessorUtil.process(
					PropsKeys.APPLICATION_STARTUP_EVENTS,
					PropsValues.APPLICATION_STARTUP_EVENTS,
					new String[] {String.valueOf(companyId)});
			}
			catch (Exception exception) {
				_log.error(exception);
			}

			// End initializing company

			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"End initializing company with web id ", webId,
						" and company id ", companyId));
			}

			_companies.putIfAbsent(companyId, webId);
		}
		finally {
			CompanyThreadLocal.setCompanyId(currentThreadCompanyId);

			PrincipalThreadLocal.setName(currentThreadPrincipalName);
		}

		return companyId;
	}

	public static boolean isAutoLoginIgnoreHost(String host) {
		return _autoLoginIgnoreHosts.contains(host);
	}

	public static boolean isAutoLoginIgnorePath(String path) {
		return _autoLoginIgnorePaths.contains(path);
	}

	public static boolean isCompanyActive(long companyId) {
		try {
			Company company = CompanyLocalServiceUtil.fetchCompanyById(
				companyId);

			if (company != null) {
				return company.isActive();
			}
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		return false;
	}

	public static boolean isCompanyInDeletionProcess(long companyId) {
		return _companyIdsInDeletionProcess.contains(companyId);
	}

	public static boolean isCurrentCompanyInDeletionProcess() {
		return _companyIdsInDeletionProcess.contains(
			CompanyThreadLocal.getCompanyId());
	}

	public static boolean isVirtualHostsIgnoreHost(String host) {
		return _virtualHostsIgnoreHosts.contains(host);
	}

	public static boolean isVirtualHostsIgnorePath(String path) {
		return _virtualHostsIgnorePaths.contains(path);
	}

	public static void removeCompany(long companyId) {
		try {
			EventsProcessorUtil.process(
				PropsKeys.APPLICATION_SHUTDOWN_EVENTS,
				PropsValues.APPLICATION_SHUTDOWN_EVENTS,
				new String[] {String.valueOf(companyId)});
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		_companies.remove(companyId);

		WebAppPool.remove(companyId, WebKeys.PORTLET_CATEGORY);
	}

	public static SafeCloseable setCompanyInDeletionProcess(long companyId) {
		if (_companyIdsInDeletionProcess.contains(companyId)) {
			throw new UnsupportedOperationException(
				companyId + " is already in deletion");
		}

		_companyIdsInDeletionProcess.add(companyId);

		return () -> _companyIdsInDeletionProcess.remove(companyId);
	}

	private static long _getCompanyIdByHost(
		String host, HttpServletRequest httpServletRequest) {

		if (Validator.isNull(host)) {
			return 0;
		}

		try {
			VirtualHost virtualHost =
				VirtualHostLocalServiceUtil.fetchVirtualHost(host);

			if (virtualHost == null) {
				return 0;
			}

			CompanyThreadLocal.setCompanyId(virtualHost.getCompanyId());

			if (virtualHost.getLayoutSetId() != 0) {
				_setAttributes(virtualHost, httpServletRequest);
			}

			return virtualHost.getCompanyId();
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		return 0;
	}

	private static long _getCompanyIdByVirtualHosts(
			HttpServletRequest httpServletRequest, boolean strict)
		throws NoSuchVirtualHostException {

		String host = PortalUtil.getHost(httpServletRequest);

		if (_log.isDebugEnabled()) {
			_log.debug("Host " + host);
		}

		if (isVirtualHostsIgnoreHost(host)) {
			return 0;
		}

		long companyId = _getCompanyIdByHost(host, httpServletRequest);

		if (strict && (companyId == 0)) {
			throw new NoSuchVirtualHostException(host);
		}

		return companyId;
	}

	private static boolean _isCompanyVirtualHostname(
			long companyId, String serverName)
		throws PortalException {

		Company company = CompanyLocalServiceUtil.getCompany(companyId);

		String virtualHostname = company.getVirtualHostname();

		if (Validator.isNull(virtualHostname)) {
			virtualHostname = "localhost";
		}

		if (Objects.equals(virtualHostname, serverName)) {
			return true;
		}

		return false;
	}

	private static void _setAttributes(
			VirtualHost virtualHost, HttpServletRequest httpServletRequest)
		throws PortalException {

		LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
			virtualHost.getLayoutSetId());

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Company ", virtualHost.getCompanyId(),
					" is associated with layout set ",
					virtualHost.getLayoutSetId()));
		}

		httpServletRequest.setAttribute(
			WebKeys.VIRTUAL_HOST_LAYOUT_SET, layoutSet);

		String languageId = virtualHost.getLanguageId();

		if (Validator.isNotNull(languageId) &&
			LanguageUtil.isAvailableLocale(
				layoutSet.getGroupId(), languageId)) {

			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Virtual host ", virtualHost.getHostname(),
						" has default language ", languageId));
			}

			httpServletRequest.setAttribute(
				WebKeys.VIRTUAL_HOST_LANGUAGE_ID, languageId);
		}
	}

	private PortalInstances() {
	}

	private static final String _GET_COMPANY_IDS =
		"select companyId from Company";

	private static final Log _log = LogFactoryUtil.getLog(
		PortalInstances.class);

	private static final Set<String> _autoLoginIgnoreHosts;
	private static final Set<String> _autoLoginIgnorePaths;
	private static final Map<Long, String> _companies;
	private static final List<Long> _companyIdsInDeletionProcess =
		new CopyOnWriteArrayList<>();
	private static final Set<String> _virtualHostsIgnoreHosts;
	private static final Set<String> _virtualHostsIgnorePaths;

	static {
		_companies = new ConcurrentHashMap<>();
		_autoLoginIgnoreHosts = SetUtil.fromArray(
			PropsUtil.getArray(PropsKeys.AUTO_LOGIN_IGNORE_HOSTS));
		_autoLoginIgnorePaths = SetUtil.fromArray(
			PropsUtil.getArray(PropsKeys.AUTO_LOGIN_IGNORE_PATHS));
		_virtualHostsIgnoreHosts = SetUtil.fromArray(
			PropsUtil.getArray(PropsKeys.VIRTUAL_HOSTS_IGNORE_HOSTS));
		_virtualHostsIgnorePaths = SetUtil.fromArray(
			PropsUtil.getArray(PropsKeys.VIRTUAL_HOSTS_IGNORE_PATHS));
	}

}