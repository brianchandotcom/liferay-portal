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

package com.liferay.bean.portlet.cdi.extension.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.HeaderRequest;
import javax.portlet.HeaderResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.annotations.ActionMethod;
import javax.portlet.annotations.DestroyMethod;
import javax.portlet.annotations.EventMethod;
import javax.portlet.annotations.HeaderMethod;
import javax.portlet.annotations.InitMethod;
import javax.portlet.annotations.RenderMethod;
import javax.portlet.annotations.ServeResourceMethod;

/**
 * @author Neil Griffin
 */
public class MethodSignature {

	public static final MethodSignature ACTION = new MethodSignature(
		ActionMethod.class,
		new Class<?>[] {ActionRequest.class, ActionResponse.class}, false);

	public static final MethodSignature DESTROY = new MethodSignature(
		DestroyMethod.class, new Class<?>[0], false);

	public static final MethodSignature EVENT = new MethodSignature(
		EventMethod.class,
		new Class<?>[] {EventRequest.class, EventResponse.class}, false);

	public static final MethodSignature HEADER = new MethodSignature(
		HeaderMethod.class,
		new Class<?>[] {HeaderRequest.class, HeaderResponse.class}, true);

	public static final MethodSignature INIT = new MethodSignature(
		InitMethod.class, new Class<?>[] {PortletConfig.class}, false);

	public static final MethodSignature RENDER = new MethodSignature(
		RenderMethod.class,
		new Class<?>[] {RenderRequest.class, RenderResponse.class}, true);

	public static final MethodSignature SERVE_RESOURCE = new MethodSignature(
		ServeResourceMethod.class,
		new Class<?>[] {ResourceRequest.class, ResourceResponse.class}, true);

	public boolean isMatch(Method method) {
		Class<?> returnType = method.getReturnType();

		Class<?>[] parameterTypes = method.getParameterTypes();

		if (returnType.equals(Void.TYPE) && _isAssignableFrom(parameterTypes)) {
			return true;
		}
		else if (_variant && returnType.equals(Void.TYPE) &&
				 (parameterTypes.length == 0)) {

			return true;
		}
		else if (_variant && returnType.equals(String.class) &&
				 (parameterTypes.length == 0)) {

			return true;
		}

		Class<?> declaringClass = method.getDeclaringClass();

		_log.error(
			StringBundler.concat(
				"Method ", declaringClass.getName(), ".", method.getName(),
				" does not have a valid signature for @",
				_annotation.getSimpleName()));

		return false;
	}

	private MethodSignature(
		Class<? extends Annotation> annotation, Class<?>[] parameterTypes,
		boolean variant) {

		_annotation = annotation;
		_parameterTypes = parameterTypes;
		_variant = variant;
	}

	private boolean _isAssignableFrom(Class<?>[] parameterTypes) {
		if (parameterTypes.length == _parameterTypes.length) {
			for (int i = 1; i < parameterTypes.length; i++) {
				if (!parameterTypes[i].isAssignableFrom(_parameterTypes[i])) {
					return false;
				}
			}

			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MethodSignature.class);

	private final Class<? extends Annotation> _annotation;
	private final Class<?>[] _parameterTypes;
	private final boolean _variant;

}