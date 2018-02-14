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

package com.liferay.wsrp.util;

import javax.xml.namespace.QName;

import oasis.names.tc.wsrp.v2.intf.WSRP_v2_Markup_PortType;
import oasis.names.tc.wsrp.v2.intf.WSRP_v2_PortletManagement_PortType;
import oasis.names.tc.wsrp.v2.intf.WSRP_v2_Registration_PortType;
import oasis.names.tc.wsrp.v2.types.PortletDescription;
import oasis.names.tc.wsrp.v2.types.PropertyDescription;
import oasis.names.tc.wsrp.v2.types.RegistrationContext;
import oasis.names.tc.wsrp.v2.types.ServiceDescription;

/**
 * @author Matthew Tambara
 */
public interface WSRPConsumerManager {

	public String getDisplayName(PortletDescription portletDescription);

	public QName getEventQName(QName qName);

	public WSRP_v2_Markup_PortType getMarkupService() throws Exception;

	public PortletDescription getPortletDescription(String portletHandle);

	public WSRP_v2_PortletManagement_PortType getPortletManagementService();

	public PropertyDescription getPropertyDescription(String name);

	public PropertyDescription[] getPropertyDescriptions();

	public WSRP_v2_Registration_PortType getRegistrationService();

	public ServiceDescription getServiceDescription();

	public String getWsdl();

	public void updateServiceDescription(
			RegistrationContext registrationContext)
		throws Exception;

}