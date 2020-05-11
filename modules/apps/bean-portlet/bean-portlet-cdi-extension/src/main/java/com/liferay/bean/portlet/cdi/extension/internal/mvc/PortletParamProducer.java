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

package com.liferay.bean.portlet.cdi.extension.internal.mvc;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.InjectionPoint;

import javax.inject.Inject;

import javax.portlet.PortletRequest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.CookieParam;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;

/**
 * @author  Neil Griffin
 */
@Dependent
public class PortletParamProducer {

	@Dependent
	@PortletParam
	@Produces
	public Boolean getBooleanParam(
		InjectionPoint injectionPoint, PortletRequest portletRequest) {

		String value = getStringParam(injectionPoint, portletRequest);

		if (value == null) {
			return null;
		}

		Annotated field = injectionPoint.getAnnotated();

		Annotation[] fieldAnnotations = _getFieldAnnotations(field);

		ParamConverter<Boolean> paramConverter = _getParamConverter(
			fieldAnnotations, field.getBaseType(), Boolean.class);

		if (paramConverter != null) {
			try {
				return paramConverter.fromString(value);
			}
			catch (IllegalArgumentException illegalArgumentException) {
				_addBindingError(
					fieldAnnotations, illegalArgumentException.getMessage(),
					value);

				return null;
			}
		}

		if (_log.isWarnEnabled()) {
			_log.warn(
				"Unable to find a ParamConverterProvider for type Boolean");
		}

		return null;
	}

	@Dependent
	@PortletParam
	@Produces
	public Date getDateParam(
		InjectionPoint injectionPoint, PortletRequest portletRequest) {

		String value = getStringParam(injectionPoint, portletRequest);

		if (value == null) {
			return null;
		}

		Annotated field = injectionPoint.getAnnotated();

		Annotation[] fieldAnnotations = _getFieldAnnotations(field);

		ParamConverter<Date> paramConverter = _getParamConverter(
			fieldAnnotations, field.getBaseType(), Date.class);

		if (paramConverter != null) {
			try {
				return paramConverter.fromString(value);
			}
			catch (IllegalArgumentException illegalArgumentException) {
				_addBindingError(
					fieldAnnotations, illegalArgumentException.getMessage(),
					value);

				return null;
			}
		}

		if (_log.isWarnEnabled()) {
			_log.warn("Unable to find a ParamConverterProvider for type Date");
		}

		return null;
	}

	@Dependent
	@PortletParam
	@Produces
	public Double getDoubleParam(
		InjectionPoint injectionPoint, PortletRequest portletRequest) {

		String value = getStringParam(injectionPoint, portletRequest);

		if (value == null) {
			return null;
		}

		Annotated field = injectionPoint.getAnnotated();

		Annotation[] fieldAnnotations = _getFieldAnnotations(field);

		ParamConverter<Double> paramConverter = _getParamConverter(
			fieldAnnotations, field.getBaseType(), Double.class);

		if (paramConverter != null) {
			try {
				return paramConverter.fromString(value);
			}
			catch (IllegalArgumentException illegalArgumentException) {
				_addBindingError(
					fieldAnnotations, illegalArgumentException.getMessage(),
					value);

				return null;
			}
		}

		if (_log.isWarnEnabled()) {
			_log.warn(
				"Unable to find a ParamConverterProvider for type Double");
		}

		return null;
	}

	@Dependent
	@PortletParam
	@Produces
	public Float getFloatParam(
		InjectionPoint injectionPoint, PortletRequest portletRequest) {

		String value = getStringParam(injectionPoint, portletRequest);

		if (value == null) {
			return null;
		}

		Annotated field = injectionPoint.getAnnotated();

		Annotation[] fieldAnnotations = _getFieldAnnotations(field);

		ParamConverter<Float> paramConverter = _getParamConverter(
			fieldAnnotations, field.getBaseType(), Float.class);

		if (paramConverter != null) {
			try {
				return paramConverter.fromString(value);
			}
			catch (IllegalArgumentException illegalArgumentException) {
				_addBindingError(
					fieldAnnotations, illegalArgumentException.getMessage(),
					value);

				return null;
			}
		}

		if (_log.isWarnEnabled()) {
			_log.warn("Unable to find a ParamConverterProvider for type Float");
		}

		return null;
	}

	@Dependent
	@PortletParam
	@Produces
	public Integer getIntegerParam(
		InjectionPoint injectionPoint, PortletRequest portletRequest) {

		String value = getStringParam(injectionPoint, portletRequest);

		if (value == null) {
			return null;
		}

		Annotated field = injectionPoint.getAnnotated();

		Annotation[] fieldAnnotations = _getFieldAnnotations(field);

		ParamConverter<Integer> paramConverter = _getParamConverter(
			fieldAnnotations, field.getBaseType(), Integer.class);

		if (paramConverter != null) {
			try {
				return paramConverter.fromString(value);
			}
			catch (IllegalArgumentException illegalArgumentException) {
				_addBindingError(
					fieldAnnotations, illegalArgumentException.getMessage(),
					value);

				return null;
			}
		}

		if (_log.isWarnEnabled()) {
			_log.warn(
				"Unable to find a ParamConverterProvider for type Integer");
		}

		return null;
	}

	@Dependent
	@PortletParam
	@Produces
	public Long getLongParam(
		InjectionPoint injectionPoint, PortletRequest portletRequest) {

		String value = getStringParam(injectionPoint, portletRequest);

		if (value == null) {
			return null;
		}

		Annotated field = injectionPoint.getAnnotated();

		Annotation[] fieldAnnotations = _getFieldAnnotations(field);

		ParamConverter<Long> paramConverter = _getParamConverter(
			fieldAnnotations, field.getBaseType(), Long.class);

		if (paramConverter != null) {
			try {
				return paramConverter.fromString(value);
			}
			catch (IllegalArgumentException illegalArgumentException) {
				_addBindingError(
					fieldAnnotations, illegalArgumentException.getMessage(),
					value);

				return null;
			}
		}

		if (_log.isWarnEnabled()) {
			_log.warn("Unable to find a ParamConverterProvider for type Long");
		}

		return Long.valueOf(value);
	}

	@Dependent
	@PortletParam
	@Produces
	public String getStringParam(
		InjectionPoint injectionPoint, PortletRequest portletRequest) {

		Annotated field = injectionPoint.getAnnotated();

		String defaultValue = null;

		DefaultValue defaultValueAnnotation = field.getAnnotation(
			DefaultValue.class);

		if (defaultValueAnnotation != null) {
			defaultValue = defaultValueAnnotation.value();
		}

		CookieParam cookieParam = field.getAnnotation(CookieParam.class);

		if (cookieParam != null) {
			Cookie[] cookies = portletRequest.getCookies();

			for (Cookie cookie : cookies) {
				if (Objects.equals(cookieParam.value(), cookie.getName())) {
					String cookieValue = cookie.getValue();

					if (cookieValue == null) {
						if (_log.isDebugEnabled()) {
							_log.debug(
								StringBundler.concat(
									"Injecting the default value=",
									defaultValue, " into a field annotated ",
									"with @CookieParam(\"", cookieParam.value(),
									"\") because the cookie does not have a ",
									"value"));
						}

						return defaultValue;
					}

					return cookieValue;
				}
			}

			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Injecting the default value=", defaultValue,
						" into a field annotated with @CookieParam(\"",
						cookieParam.value(), "\") because there is no cookie ",
						"with that name"));
			}

			return defaultValue;
		}

		FormParam formParam = field.getAnnotation(FormParam.class);

		if (formParam != null) {
			String lifecyclePhase = (String)portletRequest.getAttribute(
				PortletRequest.LIFECYCLE_PHASE);

			ThemeDisplay themeDisplay =
				(ThemeDisplay)portletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			HttpServletRequest httpServletRequest = themeDisplay.getRequest();

			String httpMethod = httpServletRequest.getMethod();

			if (StringUtil.equalsIgnoreCase(httpMethod, "post") &&
				(lifecyclePhase.equals(PortletRequest.ACTION_PHASE) ||
				 lifecyclePhase.equals(PortletRequest.RESOURCE_PHASE))) {

				@SuppressWarnings("deprecation")
				String parameterValue = portletRequest.getParameter(
					formParam.value());

				if (parameterValue == null) {
					if (_log.isDebugEnabled()) {
						_log.debug(
							StringBundler.concat(
								"Injecting the default value=", defaultValue,
								" into a field annotated with @FormParam(\"",
								formParam.value(), "\") because the request ",
								"parameter does not have a value"));
					}

					return defaultValue;
				}

				return parameterValue;
			}

			if (_log.isDebugEnabled()) {
				if (lifecyclePhase.equals(PortletRequest.RENDER_PHASE)) {
					_log.debug(
						StringBundler.concat(
							"Injection into a field annotated with ",
							"@FormParam(\"", formParam.value(), "\") is ",
							"invalid during the RENDER_PHASE"));
				}
				else {
					_log.debug(
						StringBundler.concat(
							"Injection into a field annotated with ",
							"@FormParam(\"", formParam.value(), "\") is ",
							"invalid during the ", lifecyclePhase, " (HTTP ",
							httpMethod, ") request"));
				}
			}

			return null;
		}

		HeaderParam headerParam = field.getAnnotation(HeaderParam.class);

		if (headerParam != null) {
			Enumeration<String> propertyNames =
				portletRequest.getPropertyNames();

			while (propertyNames.hasMoreElements()) {
				String propertyName = propertyNames.nextElement();

				if (Objects.equals(headerParam.value(), propertyName)) {
					String headerValue = portletRequest.getProperty(
						propertyName);

					if (headerValue == null) {
						if (_log.isDebugEnabled()) {
							_log.debug(
								StringBundler.concat(
									"Injecting the default value=",
									defaultValue, " into a field annotated ",
									"with @HeaderParam(\"", headerParam.value(),
									"\") because the header does not have a ",
									"value"));
						}

						return defaultValue;
					}

					return headerValue;
				}
			}

			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Injecting the default value=", defaultValue,
						" into a field annotated with @HeaderParam(\"",
						headerParam.value(),
						"\") because there is no header with that name"));
			}

			return defaultValue;
		}

		QueryParam queryParam = field.getAnnotation(QueryParam.class);

		if (queryParam != null) {
			@SuppressWarnings("deprecation")
			String parameterValue = portletRequest.getParameter(
				queryParam.value());

			if (parameterValue == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						StringBundler.concat(
							"Injecting the default value=", defaultValue,
							" into a field annotated with @QueryParam(\"",
							queryParam.value(),
							"\") because the request parameter does not have ",
							"a value"));
				}

				return defaultValue;
			}

			return parameterValue;
		}

		return defaultValue;
	}

	private void _addBindingError(
		Annotation[] fieldAnnotations, String message, String value) {

		MutableBindingResult mutableBindingResult =
			BeanUtil.getMutableBindingResult(_beanManager);

		if (mutableBindingResult == null) {
			_log.error("Unable to add binding error");
		}
		else {
			mutableBindingResult.addBindingError(
				new BindingErrorImpl(
					message, _getParamName(fieldAnnotations), value));
		}
	}

	private Annotation[] _getFieldAnnotations(Annotated field) {
		Set<Annotation> annotations = field.getAnnotations();

		return annotations.toArray(new Annotation[0]);
	}

	private <T> ParamConverter<T> _getParamConverter(
		Annotation[] annotations, Type baseType, Class<T> rawType) {

		for (ParamConverterProvider paramConverterProvider :
				_paramConverterProviders) {

			ParamConverter<T> paramConverter =
				paramConverterProvider.getConverter(
					rawType, baseType, annotations);

			if (paramConverter != null) {
				return paramConverter;
			}
		}

		return null;
	}

	private String _getParamName(Annotation[] annotations) {
		for (Annotation annotation : annotations) {
			Class<? extends Annotation> annotationClass = annotation.getClass();

			if (CookieParam.class.isAssignableFrom(annotationClass)) {
				CookieParam cookieParam = (CookieParam)annotation;

				return cookieParam.value();
			}

			if (FormParam.class.isAssignableFrom(annotationClass)) {
				FormParam formParam = (FormParam)annotation;

				return formParam.value();
			}

			if (HeaderParam.class.isAssignableFrom(annotationClass)) {
				HeaderParam headerParam = (HeaderParam)annotation;

				return headerParam.value();
			}

			if (QueryParam.class.isAssignableFrom(annotationClass)) {
				QueryParam queryParam = (QueryParam)annotation;

				return queryParam.value();
			}
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortletParamProducer.class);

	@Inject
	private BeanManager _beanManager;

	@Inject
	@ParamConverterProviders
	private List<ParamConverterProvider> _paramConverterProviders;

}