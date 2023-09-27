/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.scope.internal.liferay;

import com.liferay.oauth2.provider.scope.liferay.ScopeContext;
import com.liferay.petra.string.StringPool;

import org.osgi.framework.Bundle;
import org.osgi.service.component.annotations.Component;

/**
 * @author Carlos Sierra Andrés
 */
@Component(service = ScopeContext.class)
public class ThreadLocalScopeContext implements ScopeContext {

	@Override
	public void clear() {
		_accessTokenThreadLocal.remove();
		_applicationNameThreadLocal.remove();
		_bundleSymbolicNameThreadLocal.remove();
		_companyIdThreadLocal.remove();
	}

	@Override
	public String getAccessToken() {
		return _accessTokenThreadLocal.get();
	}

	@Override
	public String getApplicationName() {
		return _applicationNameThreadLocal.get();
	}

	@Override
	public String getBundleSymbolicName() {
		return _bundleSymbolicNameThreadLocal.get();
	}

	@Override
	public Long getCompanyId() {
		return _companyIdThreadLocal.get();
	}

	@Override
	public void setAccessToken(String accessToken) {
		_accessTokenThreadLocal.set(accessToken);
	}

	@Override
	public void setApplicationName(String applicationName) {
		_applicationNameThreadLocal.set(applicationName);
	}

	@Override
	public void setBundle(Bundle bundle) {
		String symbolicName = null;

		if (bundle != null) {
			symbolicName = bundle.getSymbolicName();
		}

		_bundleSymbolicNameThreadLocal.set(symbolicName);
	}

	@Override
	public void setCompanyId(long companyId) {
		_companyIdThreadLocal.set(companyId);
	}

	private final ThreadLocal<String> _accessTokenThreadLocal =
		ThreadLocal.withInitial(() -> StringPool.BLANK);
	private final ThreadLocal<String> _applicationNameThreadLocal =
		ThreadLocal.withInitial(() -> StringPool.BLANK);
	private final ThreadLocal<String> _bundleSymbolicNameThreadLocal =
		ThreadLocal.withInitial(() -> StringPool.BLANK);
	private final ThreadLocal<Long> _companyIdThreadLocal =
		ThreadLocal.withInitial(() -> 0L);

}