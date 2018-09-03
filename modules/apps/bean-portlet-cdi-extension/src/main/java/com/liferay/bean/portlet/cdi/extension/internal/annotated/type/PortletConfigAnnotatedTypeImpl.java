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

package com.liferay.bean.portlet.cdi.extension.internal.annotated.type;

import java.lang.reflect.Type;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.enterprise.inject.spi.AnnotatedType;

import javax.portlet.PortletConfig;

/**
 * Decorates an annotated type by effectively removing the "implements
 * PortletConfig" from the declared types. This allows the PortletConfig
 * producer to be the only candidate for injection.
 *
 * @author Neil Griffin
 */
public class PortletConfigAnnotatedTypeImpl<X> extends AnnotatedTypeWrapper<X> {

	public PortletConfigAnnotatedTypeImpl(AnnotatedType<X> annotatedType) {
		super(annotatedType);

		_types = new LinkedHashSet<>(annotatedType.getTypeClosure());

		_types.remove(PortletConfig.class);
	}

	@Override
	public Set<Type> getTypeClosure() {
		return _types;
	}

	private final Set<Type> _types;

}