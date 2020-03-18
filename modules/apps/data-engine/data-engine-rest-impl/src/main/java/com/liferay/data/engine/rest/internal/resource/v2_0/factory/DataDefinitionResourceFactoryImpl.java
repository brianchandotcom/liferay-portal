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

package com.liferay.data.engine.rest.internal.resource.v2_0.factory;

import com.liferay.data.engine.rest.resource.v2_0.DataDefinitionResource;
import com.liferay.portal.kernel.exception.NoSuchUserException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.HttpHeaders;

import org.osgi.service.component.ComponentServiceObjects;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceScope;

/**
 * @author Jeyvison Nascimento
 * @generated
 */
@Component(immediate = true, service = DataDefinitionResource.Factory.class)
@Generated("")
public class DataDefinitionResourceFactoryImpl
	implements DataDefinitionResource.Factory {

	@Override
	public DataDefinitionResource.Builder create() {
		return new DataDefinitionResource.Builder() {

			@Override
			public DataDefinitionResource build() {
				if (_user == null) {
					throw new IllegalArgumentException("User is not set");
				}

				return (DataDefinitionResource)ProxyUtil.newProxyInstance(
					DataDefinitionResource.class.getClassLoader(),
					new Class<?>[] {DataDefinitionResource.class},
					(proxy, method, arguments) -> _invoke(
						method, arguments, _checkPermissions,
						_httpServletRequest, _user));
			}

			@Override
			public DataDefinitionResource.Builder checkPermissions(
				boolean checkPermissions) {

				_checkPermissions = checkPermissions;

				return this;
			}

			@Override
			public DataDefinitionResource.Builder httpServletRequest(
				HttpServletRequest httpServletRequest) {

				_httpServletRequest = httpServletRequest;

				return this;
			}

			@Override
			public DataDefinitionResource.Builder user(User user) {
				_user = user;

				return this;
			}

			private boolean _checkPermissions = true;
			private HttpServletRequest _httpServletRequest;
			private User _user;

		};
	}

	@Activate
	protected void activate() {
		DataDefinitionResource.FactoryHolder.factory = this;
	}

	@Deactivate
	protected void deactivate() {
		DataDefinitionResource.FactoryHolder.factory = null;
	}

	private Object _invoke(
			Method method, Object[] arguments, boolean checkPermissions,
			HttpServletRequest httpServletRequest, User user)
		throws Throwable {

		String name = PrincipalThreadLocal.getName();

		PrincipalThreadLocal.setName(user.getUserId());

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (checkPermissions) {
			PermissionThreadLocal.setPermissionChecker(
				_defaultPermissionCheckerFactory.create(user));
		}
		else {
			PermissionThreadLocal.setPermissionChecker(
				_liberalPermissionCheckerFactory.create(user));
		}

		DataDefinitionResource dataDefinitionResource =
			_componentServiceObjects.getService();

		dataDefinitionResource.setContextAcceptLanguage(
			new AcceptLanguageImpl(httpServletRequest));

		Company company = _companyLocalService.getCompany(user.getCompanyId());

		dataDefinitionResource.setContextCompany(company);

		dataDefinitionResource.setContextHttpServletRequest(httpServletRequest);
		dataDefinitionResource.setContextUser(user);

		try {
			return method.invoke(dataDefinitionResource, arguments);
		}
		catch (InvocationTargetException invocationTargetException) {
			throw invocationTargetException.getTargetException();
		}
		finally {
			_componentServiceObjects.ungetService(dataDefinitionResource);

			PrincipalThreadLocal.setName(name);

			PermissionThreadLocal.setPermissionChecker(permissionChecker);
		}
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<DataDefinitionResource>
		_componentServiceObjects;

	@Reference
	private PermissionCheckerFactory _defaultPermissionCheckerFactory;

	@Reference(target = "(permission.checker.type=liberal)")
	private PermissionCheckerFactory _liberalPermissionCheckerFactory;

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	private class AcceptLanguageImpl implements AcceptLanguage {

		public AcceptLanguageImpl(HttpServletRequest httpServletRequest) {
			_httpServletRequest = httpServletRequest;
		}

		@Override
		public List<Locale> getLocales() {
			String acceptLanguage = _httpServletRequest.getHeader(
				HttpHeaders.ACCEPT_LANGUAGE);

			if (acceptLanguage == null) {
				return Collections.emptyList();
			}

			if (acceptLanguage.equals("zh-Hans-CN")) {
				acceptLanguage = "zh-CN";
			}
			else if (acceptLanguage.equals("zh-Hant-TW")) {
				acceptLanguage = "zh-TW";
			}

			try {
				Company company = _portal.getCompany(_httpServletRequest);

				Set<Locale> companyAvailableLocales =
					_language.getCompanyAvailableLocales(company.getCompanyId());

				List<Locale> locales = Locale.filter(
					Locale.LanguageRange.parse(acceptLanguage),
					companyAvailableLocales);

				if (ListUtil.isEmpty(locales)) {
					throw new ClientErrorException(
						"No locales match the accepted languages: " +
						acceptLanguage,
						422);
				}

				return locales;
			}
			catch (PortalException portalException) {
				throw new InternalServerErrorException(
					"Unable to get locales: " + portalException.getMessage(),
					portalException);
			}
		}

		@Override
		public String getPreferredLanguageId() {
			return LocaleUtil.toLanguageId(getPreferredLocale());
		}

		@Override
		public Locale getPreferredLocale() {
			List<Locale> locales = getLocales();

			if (ListUtil.isNotEmpty(locales)) {
				return locales.get(0);
			}

			try {
				User user = _portal.initUser(_httpServletRequest);

				return user.getLocale();
			}
			catch (NoSuchUserException noSuchUserException) {
				throw new NotFoundException(
					"Unable to get preferred locale from nonexistent user",
					noSuchUserException);
			}
			catch (Exception exception) {
				throw new InternalServerErrorException(
					"Unable to get preferred locale: " + exception.getMessage(),
					exception);
			}
		}

		@Override
		public boolean isAcceptAllLanguages() {
			return GetterUtil.getBoolean(
				_httpServletRequest.getHeader("X-Accept-All-Languages"));
		}

		private final HttpServletRequest _httpServletRequest;

	}

}