/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.remote.soap.extender;

import java.util.Map;

import javax.xml.namespace.QName;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Carlos Sierra Andrés
 */
@ProviderType
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