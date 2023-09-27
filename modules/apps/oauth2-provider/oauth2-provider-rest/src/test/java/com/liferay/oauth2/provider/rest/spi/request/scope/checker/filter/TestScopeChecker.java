/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.rest.spi.request.scope.checker.filter;

import com.liferay.oauth2.provider.scope.ScopeChecker;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author Carlos Sierra Andrés
 */
public class TestScopeChecker implements ScopeChecker {

	public TestScopeChecker(String... allowedScopes) {
		_allowedScopes = Arrays.asList(allowedScopes);
	}

	@Override
	public boolean checkScope(String scope) {
		return _allowedScopes.contains(scope);
	}

	private final Collection<String> _allowedScopes;

}