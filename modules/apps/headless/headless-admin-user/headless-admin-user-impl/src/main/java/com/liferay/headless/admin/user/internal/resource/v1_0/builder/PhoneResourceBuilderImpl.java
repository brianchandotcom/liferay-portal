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

package com.liferay.headless.admin.user.internal.resource.v1_0.builder;

import com.liferay.headless.admin.user.resource.v1_0.PhoneResource;
import com.liferay.headless.admin.user.resource.v1_0.builder.PhoneResourceBuilder;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.osgi.service.component.ComponentServiceObjects;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceScope;

/**
 * @author Brian Wing Shun Chan
 */
@Component(service = PhoneResourceBuilder.class)
public class PhoneResourceBuilderImpl implements PhoneResourceBuilder {

	public InitializedPhoneResourceBuilder initialize() {
		return new InitializedPhoneResourceBuilder() {

			public PhoneResource build() {
				Class<?> clazz = getClass();

				return (PhoneResource)ProxyUtil.newProxyInstance(
					clazz.getClassLoader(),
					new Class<?>[] {PhoneResource.class},
					new InvocationHandler() {

						@Override
						public Object invoke(
								Object proxy, Method method, Object[] arguments)
							throws Exception {

							return _invoke(
								method, arguments, _usePermissionChecker,
								_user);
						}

					});
			}

			public InitializedPhoneResourceBuilder usePermissionChecker(
				boolean usePermissionChecker) {

				_usePermissionChecker = usePermissionChecker;

				return this;
			}

			public InitializedPhoneResourceBuilder user(User user) {
				_user = user;

				return this;
			}

			private boolean _usePermissionChecker = true;
			private User _user;

		};
	}

	private Object _invoke(
			Method method, Object[] arguments, boolean usePermissionChecker,
			User user)
		throws Exception {

		String name = PrincipalThreadLocal.getName();

		PrincipalThreadLocal.setName(user.getUserId());

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		PermissionThreadLocal.setPermissionChecker(
			_permissionCheckerFactory.create(user));

		PhoneResource phoneResource = _componentServiceObjects.getService();

		phoneResource.setContextUser(user);

		try {
			return method.invoke(phoneResource, arguments);
		}
		finally {
			PrincipalThreadLocal.setName(name);

			PermissionThreadLocal.setPermissionChecker(permissionChecker);

			_componentServiceObjects.ungetService(phoneResource);
		}
	}

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<PhoneResource> _componentServiceObjects;

	@Reference
	private PermissionCheckerFactory _permissionCheckerFactory;

	@Reference
	private UserLocalService _userLocalService;

}