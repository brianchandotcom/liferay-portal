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

package com.liferay.portal.remote.soap.extender;

import java.util.Map;

import javax.xml.namespace.QName;

/**
 * @author Carlos Sierra Andrés
 */
public interface SoapDescriptorBuilder {

	public SoapDescriptor buildSoapDescriptor(
		Object service, Map<String, Object> properties);

	public interface SoapDescriptor {

		public QName getEndpointName();

		public String getPublicationAddress();

		public Class<?> getServiceClass();

		public String getWsdlLocation();

	}

}