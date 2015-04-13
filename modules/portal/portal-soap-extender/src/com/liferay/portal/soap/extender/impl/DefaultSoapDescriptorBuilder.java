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

package com.liferay.portal.soap.extender.impl;

import com.liferay.portal.soap.extender.api.SoapDescriptorBuilder;
import com.liferay.portal.soap.extender.api.SoapDescriptorBuilder.SoapDescriptor;

import java.util.Map;

import javax.xml.namespace.QName;

import org.osgi.service.component.annotations.Component;

/**
 * @author Carlos Sierra Andrés
 */
@Component
public class DefaultSoapDescriptorBuilder implements SoapDescriptorBuilder {

	@Override
	public SoapDescriptor buildDescriptor(
		final Map<String, Object> properties, final Object service) {

		return new SoapDescriptor() {
			@Override
			public String getPublicationAddress() {
				Object soapAddress = properties.get("soap.address");

				if (soapAddress == null) {
					Class<?> clazz = service.getClass();

					return "/" + clazz.getSimpleName();
				}

				return soapAddress.toString();
			}

			@Override
			public QName getEndpointName() {
				Object soapEndpointName = properties.get(
					"soap.endpoint.name");

				if ((soapEndpointName != null) &&
					soapEndpointName instanceof QName) {

					QName endpointName = (QName)soapEndpointName;

					return endpointName;
				}

				return null;
			}

			@Override
			public Class<?> getServiceClass() {
				Object soapServiceClass = properties.get(
					"soap.service.class");

				if ((soapServiceClass != null) &&
					soapServiceClass instanceof Class<?>) {

					return (Class<?>)soapServiceClass;
				}

				return null;
			}

			@Override
			public String getWsdlLocation() {
				Object soapWsdlLocation = properties.get(
					"soap.wsdl.location");

				if (soapWsdlLocation != null) {
					return soapWsdlLocation.toString();
				}

				return null;
			}

		};
	}

}