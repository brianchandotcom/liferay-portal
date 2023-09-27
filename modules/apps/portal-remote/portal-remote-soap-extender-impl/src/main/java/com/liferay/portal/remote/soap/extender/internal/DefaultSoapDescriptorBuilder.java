/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.remote.soap.extender.internal;

import com.liferay.portal.remote.soap.extender.SoapDescriptorBuilder;

import java.util.Map;

import javax.xml.namespace.QName;

import org.osgi.service.component.annotations.Component;

/**
 * @author Carlos Sierra Andrés
 */
@Component(service = SoapDescriptorBuilder.class)
public class DefaultSoapDescriptorBuilder implements SoapDescriptorBuilder {

	@Override
	public SoapDescriptor buildSoapDescriptor(
		Object service, Map<String, Object> properties) {

		return new DefaultSoapDescriptor(service, properties);
	}

	private static class DefaultSoapDescriptor implements SoapDescriptor {

		public DefaultSoapDescriptor(
			Object service, Map<String, Object> properties) {

			_service = service;
			_properties = properties;
		}

		@Override
		public QName getEndpointName() {
			Object soapEndpointName = _properties.get("soap.endpoint.name");

			if ((soapEndpointName != null) &&
				(soapEndpointName instanceof QName)) {

				return (QName)soapEndpointName;
			}

			return null;
		}

		@Override
		public String getPublicationAddress() {
			Object soapAddress = _properties.get("soap.address");

			if (soapAddress == null) {
				Class<?> clazz = _service.getClass();

				return "/" + clazz.getSimpleName();
			}

			return soapAddress.toString();
		}

		@Override
		public Class<?> getServiceClass() {
			Object soapServiceClass = _properties.get("soap.service.class");

			if ((soapServiceClass != null) &&
				(soapServiceClass instanceof Class<?>)) {

				return (Class<?>)soapServiceClass;
			}

			return null;
		}

		@Override
		public String getWsdlLocation() {
			Object soapWsdlLocation = _properties.get("soap.wsdl.location");

			if (soapWsdlLocation != null) {
				return soapWsdlLocation.toString();
			}

			return null;
		}

		private final Map<String, Object> _properties;
		private final Object _service;

	}

}