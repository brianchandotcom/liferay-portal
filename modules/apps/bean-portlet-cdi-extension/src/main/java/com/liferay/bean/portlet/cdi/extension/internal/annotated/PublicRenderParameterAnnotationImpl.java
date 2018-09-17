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

		super(
			publicRenderParameterDefinition.identifier(),
			new QName(
				_getNamespaceURI(publicRenderParameterDefinition),
				_getLocalPart(publicRenderParameterDefinition)));
	}

	private static final String _getLocalPart(
		PublicRenderParameterDefinition publicRenderParameterDefinition) {

		PortletQName portletQName = publicRenderParameterDefinition.qname();

		return portletQName.localPart();
	}

	private static final String _getNamespaceURI(
		PublicRenderParameterDefinition publicRenderParameterDefinition) {

		PortletQName portletQName = publicRenderParameterDefinition.qname();

		return portletQName.namespaceURI();
	}

}