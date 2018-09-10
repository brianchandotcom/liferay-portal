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

import com.liferay.bean.portlet.cdi.extension.internal.BasePublicRenderParameterImpl;

import javax.portlet.annotations.PortletQName;
import javax.portlet.annotations.PublicRenderParameterDefinition;

import javax.xml.namespace.QName;

/**
 * @author Neil Griffin
 */
public class PublicRenderParameterAnnotationImpl
	extends BasePublicRenderParameterImpl {

	public PublicRenderParameterAnnotationImpl(
		PublicRenderParameterDefinition publicRenderParameterDefinition) {

		this(
			publicRenderParameterDefinition.identifier(),
			publicRenderParameterDefinition.qname());
	}

	public PublicRenderParameterAnnotationImpl(
		String id, PortletQName portletQName) {

		setIdentifier(id);
		setQName(
			new QName(portletQName.namespaceURI(), portletQName.localPart()));
	}

	@Override
	public void setName(String name) {

		// The @PublicRenderParameterDefinition annotation does not have the
		// name feature that is available in portlet.xml.

		throw new UnsupportedOperationException();
	}

}