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

package com.liferay.oauth2.provider.shortcut.internal.spi.scope.finder;

import com.liferay.oauth2.provider.scope.spi.application.descriptor.ApplicationDescriptor;
import com.liferay.oauth2.provider.scope.spi.prefix.handler.PrefixHandler;
import com.liferay.oauth2.provider.scope.spi.prefix.handler.PrefixHandlerFactory;
import com.liferay.oauth2.provider.scope.spi.scope.finder.ScopeFinder;
import com.liferay.oauth2.provider.scope.spi.scope.mapper.ScopeMapper;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.security.service.access.policy.model.SAPEntry;
import com.liferay.portal.security.service.access.policy.service.SAPEntryLocalService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.function.Function;

/**
 * @author Shinn Lok
 */
public class OAuth2ProviderShortcutScopeFinder
	implements ApplicationDescriptor, PrefixHandlerFactory, ScopeFinder,
			   ScopeMapper {

	public OAuth2ProviderShortcutScopeFinder(
		SAPEntryLocalService sapEntryLocalService) {

		_sapEntryLocalService = sapEntryLocalService;
	}

	@Override
	public PrefixHandler create(
		Function<String, Object> propertyAccessorFunction) {

		return PrefixHandler.PASS_THROUGH_PREFIX_HANDLER;
	}

	@Override
	public String describeApplication(Locale locale) {
		return GetterUtil.getString(
			ResourceBundleUtil.getString(
				ResourceBundleUtil.getBundle(
					locale, OAuth2ProviderShortcutScopeFinder.class),
				"liferay-json-web-services-analytics-name"),
			"liferay-json-web-services-analytics-name");
	}

	@Override
	public Collection<String> findScopes() {
		Long companyId = CompanyThreadLocal.getCompanyId();

		if (companyId == null) {
			return _scopeAliasesList;
		}

		ArrayList<String> scopes = new ArrayList<>();

		for (String scopeAlias : _scopeAliasesList) {
			String name = "OAUTH2_" + scopeAlias;
			SAPEntry sapEntry = null;

			try {
				sapEntry = _sapEntryLocalService.fetchSAPEntry(companyId, name);
			}
			catch (PortalException pe) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						StringBundler.concat(
							"Scope alias ", name, " is not found: ",
							pe.getMessage()));
				}

				continue;
			}

			if ((sapEntry != null) && sapEntry.isEnabled()) {
				scopes.add(scopeAlias);
			}
		}

		return scopes;
	}

	@Override
	public Set<String> map(String scope) {
		return Collections.singleton(scope);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OAuth2ProviderShortcutScopeFinder.class);

	private static final List<String> _scopeAliasesList = Arrays.asList(
		"analytics.read", "analytics.write");

	private final SAPEntryLocalService _sapEntryLocalService;

}