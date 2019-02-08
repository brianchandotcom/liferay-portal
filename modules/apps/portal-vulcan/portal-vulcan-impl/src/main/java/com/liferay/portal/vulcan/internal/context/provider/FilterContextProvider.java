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

package com.liferay.portal.vulcan.internal.context.provider;

import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.filter.ExpressionConvert;
import com.liferay.portal.odata.filter.FilterParser;
import com.liferay.portal.vulcan.context.AcceptLanguage;
import com.liferay.portal.vulcan.internal.context.AcceptLanguageImpl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.ServerErrorException;
import javax.ws.rs.ext.Provider;

import org.apache.cxf.jaxrs.ext.ContextProvider;
import org.apache.cxf.message.Message;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = {
		JaxrsWhiteboardConstants.JAX_RS_APPLICATION_SELECT + "=(osgi.jaxrs.extension.select=\\(osgi.jaxrs.name=Liferay.Vulcan.FilterContextProvider\\))",
		JaxrsWhiteboardConstants.JAX_RS_EXTENSION + "=true",
		JaxrsWhiteboardConstants.JAX_RS_NAME + "=Liferay.Vulcan.FilterContextProvider"
	},
	scope = ServiceScope.PROTOTYPE, service = ContextProvider.class
)
@Provider
public class FilterContextProvider implements ContextProvider<Filter> {

	@Override
	public Filter createContext(Message message) {
		try {
			HttpServletRequest httpServletRequest =
				ContextProviderUtil.getHttpServletRequest(message);

			String filterString = ParamUtil.getString(
				httpServletRequest, "filter");

			if (Validator.isNull(filterString)) {
				return null;
			}

			String oDataEntityModelName = _getODataEntityModelName(message);

			System.out.println(
				"## oDataEntityModelName " + oDataEntityModelName);

			if (oDataEntityModelName == null) {
				return null;
			}

			FilterParser filterParser = _getFilterParser(oDataEntityModelName);

			System.out.println("## filterParser " + filterParser);

			com.liferay.portal.odata.filter.Filter filter =
				new com.liferay.portal.odata.filter.Filter(
					filterParser.parse(filterString));

			AcceptLanguage acceptLanguage = new AcceptLanguageImpl(
				httpServletRequest, _portal);

			EntityModel entityModel = _getEntityModel(oDataEntityModelName);

			if (entityModel == null) {
				return null;
			}

			Filter filter1 = _expressionConvert.convert(
				filter.getExpression(), acceptLanguage.getPreferredLocale(),
				entityModel);

			System.out.println("## Converted filter " + filter1);

			return filter1;
		}
		catch (Exception e) {
			throw new ServerErrorException(500, e);
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	private EntityModel _getEntityModel(String oDataEntityModelName)
		throws Exception {

		Collection<ServiceReference<EntityModel>> serviceReferences =
			_bundleContext.getServiceReferences(
				EntityModel.class,
				"(entity.model.name=" + oDataEntityModelName + ")");

		if (serviceReferences.isEmpty()) {
			return null;
		}

		Iterator<ServiceReference<EntityModel>> iterator =
			serviceReferences.iterator();

		ServiceReference<EntityModel> serviceReference = iterator.next();

		return _bundleContext.getService(serviceReference);
	}

	private FilterParser _getFilterParser(String oDataEntityModelName)
		throws Exception {

		Collection<ServiceReference<FilterParser>> serviceReferences =
			_bundleContext.getServiceReferences(
				FilterParser.class,
				"(entity.model.name=" + oDataEntityModelName + ")");

		if (serviceReferences.isEmpty()) {
			return null;
		}

		Iterator<ServiceReference<FilterParser>> iterator =
			serviceReferences.iterator();

		ServiceReference<FilterParser> serviceReference = iterator.next();

		return _bundleContext.getService(serviceReference);
	}

	private String _getODataEntityModelName(Message message) throws Exception {
		Method method = (Method)message.get("org.apache.cxf.resource.method");

		if (method == null) {
			return null;
		}

		Class<?> clazz = method.getDeclaringClass();

		Field field = clazz.getField("ODATA_ENTITY_MODEL_NAME");

		if (field == null) {
			return null;
		}

		return (String)field.get(null);
	}

	private BundleContext _bundleContext;

	@Reference(
		target = "(result.class.name=com.liferay.portal.kernel.search.filter.Filter)"
	)
	private ExpressionConvert<Filter> _expressionConvert;

	@Reference
	private Portal _portal;

}