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
import com.liferay.oauth2.provider.shortcut.internal.constants.OAuth2ProviderShortcutConstants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.function.Function;

import org.osgi.service.component.annotations.Component;

/**
 * @author Shinn Lok
 */
@Component(
	immediate = true,
	property = {
		"osgi.jaxrs.name=" + OAuth2ProviderShortcutConstants.APPLICATION_NAME,
		"sap.scope.finder=true"
	},
	service = {
		ApplicationDescriptor.class, PrefixHandlerFactory.class,
		ScopeFinder.class, ScopeMapper.class
	}
)
public class OAuth2ProviderShortcutScopeFinder
	implements ApplicationDescriptor, PrefixHandlerFactory, ScopeFinder,
			   ScopeMapper {

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
		return _scopeAliasesList;
	}

	@Override
	public Set<String> map(String scope) {
		return Collections.singleton(scope);
	}

	private static final List<String> _scopeAliasesList = Arrays.asList(
		"analytics.read", "analytics.write");

}