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

package com.liferay.headless.delivery.internal.resource.v1_0;

import com.liferay.headless.delivery.resource.v1_0.KnowledgeBaseFolderResource;
import com.liferay.headless.delivery.resource.v1_0.KnowledgeBaseFolderResourceInvoker;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.exception.NoSuchUserException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.HttpHeaders;

import org.osgi.service.component.ComponentServiceObjects;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceScope;

/**
 * @author Matthew Tambara
 */
@Component(service = KnowledgeBaseFolderResourceInvoker.class)
public class KnowledgeBaseFolderResourceInvokerImpl
	implements KnowledgeBaseFolderResourceInvoker {

	@Override
	public void invoke(
			UnsafeConsumer<KnowledgeBaseFolderResource, Exception>
				unsafeConsumer,
			HttpServletRequest httpServletRequest)
		throws Exception {

		KnowledgeBaseFolderResource knowledgeBaseFolderResource =
			_knowledgeBaseFolderResourceComponentServiceObjects.getService();

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		PermissionThreadLocal.setPermissionChecker(
			_permissionCheckerFactory.create(
				_userLocalService.getUser(PrincipalThreadLocal.getUserId())));

		_setContext(knowledgeBaseFolderResource, httpServletRequest);

		try {
			unsafeConsumer.accept(knowledgeBaseFolderResource);
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(permissionChecker);

			_knowledgeBaseFolderResourceComponentServiceObjects.ungetService(
				knowledgeBaseFolderResource);
		}
	}

	private void _setContext(
			KnowledgeBaseFolderResource knowledgeBaseFolderResource,
			HttpServletRequest httpServletRequest)
		throws Exception {

		knowledgeBaseFolderResource.setContextCompany(
			_portal.getCompany(httpServletRequest));

		knowledgeBaseFolderResource.setContextUser(
			_portal.getUser(httpServletRequest));

		knowledgeBaseFolderResource.setContextAcceptLanguage(
			new AcceptLanguageImpl(httpServletRequest, _language, _portal));

		knowledgeBaseFolderResource.setContextHttpServletRequest(
			httpServletRequest);
	}

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<KnowledgeBaseFolderResource>
		_knowledgeBaseFolderResourceComponentServiceObjects;

	@Reference
	private Language _language;

	@Reference
	private PermissionCheckerFactory _permissionCheckerFactory;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

	private class AcceptLanguageImpl implements AcceptLanguage {

		public AcceptLanguageImpl(
			HttpServletRequest httpServletRequest, Language language,
			Portal portal) {

			_httpServletRequest = httpServletRequest;
			_language = language;
			_portal = portal;
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
					_language.getCompanyAvailableLocales(
						company.getCompanyId());

				List<Locale> locales = Locale.filter(
					Locale.LanguageRange.parse(acceptLanguage),
					companyAvailableLocales);

				if (ListUtil.isEmpty(locales)) {
					throw new ClientErrorException(
						"No available locale matches the accepted languages: " +
							acceptLanguage,
						422);
				}

				return locales;
			}
			catch (PortalException portalException) {
				throw new InternalServerErrorException(
					"Unable to get preferred locale: " +
						portalException.getMessage(),
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
		private final Language _language;
		private final Portal _portal;

	}

}