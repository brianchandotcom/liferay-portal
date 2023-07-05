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

package com.liferay.oauth2.provider.scope.internal;

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
		accessTokenThreadLocal.remove();
		applicationNameThreadLocal.remove();
		bundleSymbolicNameThreadLocal.remove();
		companyIdThreadLocal.remove();
	}

	@Override
	public void setAccessToken(String accessToken) {
		accessTokenThreadLocal.set(accessToken);
	}

	@Override
	public void setApplicationName(String applicationName) {
		applicationNameThreadLocal.set(applicationName);
	}

	@Override
	public void setBundle(Bundle bundle) {
		String symbolicName = null;

		if (bundle != null) {
			symbolicName = bundle.getSymbolicName();
		}

		bundleSymbolicNameThreadLocal.set(symbolicName);
	}

	@Override
	public void setCompanyId(long companyId) {
		companyIdThreadLocal.set(companyId);
	}

	protected final ThreadLocal<String> accessTokenThreadLocal =
		ThreadLocal.withInitial(() -> StringPool.BLANK);
	protected final ThreadLocal<String> applicationNameThreadLocal =
		ThreadLocal.withInitial(() -> StringPool.BLANK);
	protected final ThreadLocal<String> bundleSymbolicNameThreadLocal =
		ThreadLocal.withInitial(() -> StringPool.BLANK);
	protected final ThreadLocal<Long> companyIdThreadLocal =
		ThreadLocal.withInitial(() -> 0L);

}