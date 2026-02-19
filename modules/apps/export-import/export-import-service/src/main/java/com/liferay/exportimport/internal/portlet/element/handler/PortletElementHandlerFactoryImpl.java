/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.portlet.element.handler;

import com.liferay.exportimport.portlet.element.handler.PortletElementHandler;
import com.liferay.exportimport.portlet.element.handler.PortletElementHandlerFactory;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.xml.Element;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carlos Correa
 */
@Component(service = PortletElementHandlerFactory.class)
public class PortletElementHandlerFactoryImpl
	implements PortletElementHandlerFactory {

	@Override
	public PortletElementHandler create(Element element) {
		return new PortletElementHandlerImpl(element, _portletLocalService);
	}

	@Reference
	private PortletLocalService _portletLocalService;

}