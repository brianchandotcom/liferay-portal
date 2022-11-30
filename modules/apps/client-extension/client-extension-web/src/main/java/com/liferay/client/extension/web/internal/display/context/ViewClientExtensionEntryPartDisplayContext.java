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

package com.liferay.client.extension.web.internal.display.context;

import com.liferay.client.extension.type.CET;
import com.liferay.client.extension.type.annotation.CETProperty;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.CamelCaseUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Iván Zaera Avellón
 */
public class ViewClientExtensionEntryPartDisplayContext<T extends CET> {

	public ViewClientExtensionEntryPartDisplayContext(
		T cet, PortletRequest portletRequest) {

		_cet = cet;
		_portletRequest = portletRequest;
	}

	public T getCET() {
		return _cet;
	}

	public String getLabel(Method method) {
		CETProperty cetProperty = method.getAnnotation(CETProperty.class);

		String label = cetProperty.label();

		if (Validator.isBlank(label)) {
			label = CamelCaseUtil.fromCamelCase(cetProperty.name());
		}

		return LanguageUtil.get(_getHttpServletRequest(), label);
	}

	public Collection<Method> getPropertyMethods() {
		List<Method> methods = new ArrayList<>();

		Class<? extends CET> clazz = _cet.getClass();

		for (Class<?> iface : clazz.getInterfaces()) {
			if ((iface == _CET_CLASS) || !_CET_CLASS.isAssignableFrom(iface)) {
				continue;
			}

			for (Method method : iface.getDeclaredMethods()) {
				if (method.getAnnotation(CETProperty.class) != null) {
					methods.add(method);
				}
			}
		}

		Collections.sort(
			methods, Comparator.comparing(method -> getLabel(method)));

		return methods;
	}

	public <T> T getValue(Method method) {
		try {
			return (T)method.invoke(_cet);
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	private HttpServletRequest _getHttpServletRequest() {
		return PortalUtil.getHttpServletRequest(_portletRequest);
	}

	private static final Class<CET> _CET_CLASS = CET.class;

	private final T _cet;
	private final PortletRequest _portletRequest;

}