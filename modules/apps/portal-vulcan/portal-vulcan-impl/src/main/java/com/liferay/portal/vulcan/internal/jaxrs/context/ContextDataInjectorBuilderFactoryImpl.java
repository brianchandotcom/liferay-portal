/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.internal.jaxrs.context;

import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.jaxrs.context.ContextDataInjector;
import com.liferay.portal.vulcan.jaxrs.context.ContextDataInjectorBuilder;
import com.liferay.portal.vulcan.jaxrs.context.ContextDataInjectorBuilderFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.annotations.Component;

/**
 * @author Carlos Correa
 */
@Component(service = ContextDataInjectorBuilderFactory.class)
public class ContextDataInjectorBuilderFactoryImpl
	implements ContextDataInjectorBuilderFactory {

	@Override
	public ContextDataInjectorBuilder builder() {
		return new ContextDataInjectorBuilder() {

			@Override
			public ContextDataInjectorBuilder acceptLanguage(
				AcceptLanguage acceptLanguage) {

				_acceptLanguage = acceptLanguage;

				return this;
			}

			@Override
			public ContextDataInjector build() {
				return instance -> _setInstanceFields(
					instance.getClass(), instance);
			}

			@Override
			public ContextDataInjectorBuilder company(Company company) {
				_company = company;

				return this;
			}

			@Override
			public ContextDataInjectorBuilder httpServletRequest(
				HttpServletRequest httpServletRequest) {

				_httpServletRequest = httpServletRequest;

				return this;
			}

			@Override
			public ContextDataInjectorBuilder httpServletResponse(
				HttpServletResponse httpServletResponse) {

				_httpServletResponse = httpServletResponse;

				return this;
			}

			@Override
			public ContextDataInjectorBuilder uriInfo(UriInfo uriInfo) {
				_uriInfo = uriInfo;

				return this;
			}

			@Override
			public ContextDataInjectorBuilder user(User user) {
				_user = user;

				return this;
			}

			@Override
			public ContextDataInjectorBuilder
				vulcanBatchEngineExportTaskResource(
					VulcanBatchEngineExportTaskResource
						vulcanBatchEngineExportTaskResource) {

				_vulcanBatchEngineExportTaskResource =
					vulcanBatchEngineExportTaskResource;

				return this;
			}

			@Override
			public ContextDataInjectorBuilder
				vulcanBatchEngineImportTaskResource(
					VulcanBatchEngineImportTaskResource
						vulcanBatchEngineImportTaskResource) {

				_vulcanBatchEngineImportTaskResource =
					vulcanBatchEngineImportTaskResource;

				return this;
			}

			private Object _setInstanceFields(Class<?> clazz, Object instance)
				throws Exception {

				if (clazz == Object.class) {
					return instance;
				}

				for (Field field : clazz.getDeclaredFields()) {
					if (Modifier.isFinal(field.getModifiers()) ||
						Modifier.isStatic(field.getModifiers())) {

						continue;
					}

					Class<?> fieldClass = field.getType();

					if (fieldClass.isAssignableFrom(AcceptLanguage.class)) {
						if (_acceptLanguage != null) {
							field.setAccessible(true);

							field.set(instance, _acceptLanguage);
						}
					}
					else if (fieldClass.isAssignableFrom(Company.class)) {
						if (_company != null) {
							field.setAccessible(true);

							field.set(instance, _company);
						}
					}
					else if (fieldClass.isAssignableFrom(
								HttpServletRequest.class)) {

						if (_httpServletRequest != null) {
							field.setAccessible(true);

							field.set(instance, _httpServletRequest);
						}
					}
					else if (fieldClass.isAssignableFrom(
								HttpServletResponse.class)) {

						if (_httpServletResponse != null) {
							field.setAccessible(true);

							field.set(instance, _httpServletResponse);
						}
					}
					else if (fieldClass.isAssignableFrom(UriInfo.class)) {
						if (_uriInfo != null) {
							field.setAccessible(true);

							field.set(instance, _uriInfo);
						}
					}
					else if (fieldClass.isAssignableFrom(User.class)) {
						if (_user != null) {
							field.setAccessible(true);

							field.set(instance, _user);
						}
					}
				}

				return _setInstanceFields(clazz.getSuperclass(), instance);
			}

			private AcceptLanguage _acceptLanguage;
			private Company _company;
			private HttpServletRequest _httpServletRequest;
			private HttpServletResponse _httpServletResponse;
			private UriInfo _uriInfo;
			private User _user;

		};
	}

}