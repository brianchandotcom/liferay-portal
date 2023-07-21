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