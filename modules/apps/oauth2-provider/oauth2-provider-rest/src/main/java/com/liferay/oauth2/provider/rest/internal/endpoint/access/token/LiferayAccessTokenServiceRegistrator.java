/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.rest.internal.endpoint.access.token;

import com.liferay.oauth2.provider.rest.internal.endpoint.liferay.LiferayOAuthDataProvider;
import com.liferay.petra.concurrent.DCLSingleton;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.cxf.rs.security.oauth2.provider.AccessTokenGrantHandler;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceFactory;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.FieldOption;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Tomas Polesovsky
 */
@Component(
	property = {
		"block.unsecure.requests=true", "can.support.public.clients=true",
		"enabled=true"
	},
	service = {}
)
public class LiferayAccessTokenServiceRegistrator {

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		if (!MapUtil.getBoolean(properties, "enabled", true)) {
			return;
		}

		_serviceRegistration = bundleContext.registerService(
			Object.class,
			(Object)new ServiceFactory<Object>() {

				@Override
				public Object getService(
					Bundle bundle,
					ServiceRegistration<Object> serviceRegistration) {

					return _liferayAccessTokenServiceDCLSingleton.getSingleton(
						() -> {
							LiferayAccessTokenService
								liferayAccessTokenService =
									new LiferayAccessTokenService();

							liferayAccessTokenService.setBlockUnsecureRequests(
								MapUtil.getBoolean(
									properties, "block.unsecure.requests",
									true));
							liferayAccessTokenService.
								setCanSupportPublicClients(
									MapUtil.getBoolean(
										properties, "allow.public.clients",
										true));
							liferayAccessTokenService.setDataProvider(
								_liferayOAuthDataProvider);
							liferayAccessTokenService.setGrantHandlers(
								_accessTokenGrantHandlers);

							return liferayAccessTokenService;
						});
				}

				@Override
				public void ungetService(
					Bundle bundle,
					ServiceRegistration<Object> serviceRegistration,
					Object service) {
				}

			},
			HashMapDictionaryBuilder.<String, Object>put(
				"osgi.jaxrs.application.select",
				"(osgi.jaxrs.name=Liferay.OAuth2.Application)"
			).put(
				"osgi.jaxrs.name", "Liferay.Access.Token.Service."
			).put(
				"osgi.jaxrs.resource", true
			).build());
	}

	@Deactivate
	protected void deactivate() {
		if (_serviceRegistration != null) {
			_serviceRegistration.unregister();

			_serviceRegistration = null;
		}
	}

	@Reference(
		cardinality = ReferenceCardinality.AT_LEAST_ONE,
		fieldOption = FieldOption.UPDATE, policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	private volatile List<AccessTokenGrantHandler> _accessTokenGrantHandlers =
		new CopyOnWriteArrayList<>();

	private final DCLSingleton<LiferayAccessTokenService>
		_liferayAccessTokenServiceDCLSingleton = new DCLSingleton<>();

	@Reference
	private LiferayOAuthDataProvider _liferayOAuthDataProvider;

	private volatile ServiceRegistration<Object> _serviceRegistration;

}