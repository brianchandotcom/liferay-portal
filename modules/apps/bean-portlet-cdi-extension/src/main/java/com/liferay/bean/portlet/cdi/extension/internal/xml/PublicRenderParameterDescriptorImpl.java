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

package com.liferay.bean.portlet.cdi.extension.internal.xml;

import com.liferay.bean.portlet.cdi.extension.internal.BasePublicRenderParameterImpl;

import javax.xml.namespace.QName;

/**
 * @author Neil Griffin
 */
public class PublicRenderParameterDescriptorImpl
	extends BasePublicRenderParameterImpl {

	public PublicRenderParameterDescriptorImpl(
		String identifier, com.liferay.portal.kernel.xml.QName qName) {

		super(
			identifier,
			new QName(
				qName.getNamespaceURI(), qName.getLocalPart(),
				qName.getNamespacePrefix()));
	}

}