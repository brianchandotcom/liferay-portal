/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.url;

import com.liferay.frontend.data.set.url.FDSAPIURLBuilder;
import com.liferay.frontend.data.set.url.FDSAPIURLBuilderFactory;
import com.liferay.frontend.data.set.url.FDSAPIURLResolverRegistry;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Sanz
 */
@Component(service = FDSAPIURLBuilderFactory.class)
public class FDSAPIURLBuilderFactoryImpl implements FDSAPIURLBuilderFactory {

	@Override
	public FDSAPIURLBuilder create(
		String restApplication, String restEndpoint, String restSchema,
		HttpServletRequest httpServletRequest) {

		return new FDSAPIURLBuilderImpl(
			restApplication, restEndpoint, restSchema,
			_fdsAPIURLResolverRegistry, httpServletRequest);
	}

	@Reference
	private FDSAPIURLResolverRegistry _fdsAPIURLResolverRegistry;

}