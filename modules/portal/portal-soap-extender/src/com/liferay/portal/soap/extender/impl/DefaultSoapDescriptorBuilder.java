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
				Object addressObject = properties.get("soap.address");

				if (addressObject == null) {
					return "/" + service.getClass().getSimpleName();
				}

				return addressObject.toString();
			}

			@Override
			public QName getEndpointName() {
				Object endpointNameObject = properties.get(
					"soap.endpoint.name");

				if ((endpointNameObject != null) &&
					endpointNameObject instanceof QName) {

					QName endpointName = (QName)endpointNameObject;

					return endpointName;
				}

				return null;
			}

			@Override
			public Class<?> getServiceClass() {
				Object serviceClassObject = properties.get(
					"soap.service.class");

				if ((serviceClassObject != null) &&
					serviceClassObject instanceof Class<?>) {

					return (Class<?>)serviceClassObject;
				}

				return null;
			}

			@Override
			public String getWsdlLocation() {
				Object wsdlLocationObject = properties.get(
					"soap.wsdl.location");

				if (wsdlLocationObject != null) {
					return wsdlLocationObject.toString();
				}

				return null;
			}

		};
	}

}