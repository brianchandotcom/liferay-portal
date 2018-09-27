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

package com.liferay.bean.portlet.cdi.extension.internal.annotated;

import com.liferay.bean.portlet.cdi.extension.internal.BaseEventImpl;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.annotations.EventDefinition;
import javax.portlet.annotations.PortletQName;

import javax.xml.namespace.QName;

/**
 * @author Neil Griffin
 */
public class EventAnnotationImpl extends BaseEventImpl {

	public EventAnnotationImpl(EventDefinition eventDefinition) {
		this(eventDefinition.qname(), eventDefinition.payloadType());

		for (PortletQName portletQName : eventDefinition.alias()) {
			_aliasQNames.add(
				new QName(
					portletQName.namespaceURI(), portletQName.localPart()));
		}
	}

	public EventAnnotationImpl(PortletQName portletQName, Class<?> valueType) {
		super(
			new QName(portletQName.namespaceURI(), portletQName.localPart()),
			_getClassName(valueType));
	}

	@Override
	public List<QName> getAliasQNames() {
		return _aliasQNames;
	}

	private static String _getClassName(Class<?> valueType) {
		if (valueType == null) {
			return null;
		}

		return valueType.getName();
	}

	private final List<QName> _aliasQNames = new ArrayList<>();

}