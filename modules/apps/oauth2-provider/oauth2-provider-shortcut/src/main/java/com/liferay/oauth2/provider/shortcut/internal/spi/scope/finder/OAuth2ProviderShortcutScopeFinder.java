/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.shortcut.internal.spi.scope.finder;

import com.liferay.oauth2.provider.scope.spi.application.descriptor.ApplicationDescriptor;
import com.liferay.oauth2.provider.scope.spi.prefix.handler.PrefixHandler;
import com.liferay.oauth2.provider.scope.spi.prefix.handler.PrefixHandlerFactory;
import com.liferay.oauth2.provider.scope.spi.scope.finder.ScopeFinder;
import com.liferay.oauth2.provider.scope.spi.scope.mapper.ScopeMapper;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.security.service.access.policy.model.SAPEntry;
import com.liferay.portal.security.service.access.policy.service.SAPEntryLocalService;

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
				"analytics"),
			"analytics");
	}

	@Override
	public Collection<String> findScopes() {
		Long companyId = CompanyThreadLocal.getCompanyId();

		if (companyId == null) {
			return _scopeAliasesList;
		}

		return TransformUtil.transform(
			_scopeAliasesList,
			scopeAlias -> {
				SAPEntry sapEntry = _sapEntryLocalService.fetchSAPEntry(
					companyId, "OAUTH2_" + scopeAlias);

				if ((sapEntry != null) && sapEntry.isEnabled()) {
					return scopeAlias;
				}

				return null;
			});
	}

	@Override
	public Set<String> map(String scope) {
		return Collections.singleton(scope);
	}

	private static final List<String> _scopeAliasesList = Arrays.asList(
		"analytics.read", "analytics.write");

	private final SAPEntryLocalService _sapEntryLocalService;

}