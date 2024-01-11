/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.spi.scope.mapper;

import com.liferay.oauth2.provider.scope.spi.scope.mapper.ScopeMapper;

import java.util.Collections;
import java.util.Set;

import org.osgi.service.component.annotations.Component;

/**
 * @author Joao Victor Alves
 */
@Component(
	property = "osgi.jaxrs.name=Liferay.Analytics.Cloud.REST",
	service = ScopeMapper.class
)
public class OAuth2AuthorizationExpandoScopeMapper implements ScopeMapper {

	@Override
	public Set<String> map(String scope) {
		return Collections.singleton(scope);
	}

}