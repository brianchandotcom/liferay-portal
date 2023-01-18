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

package com.liferay.layout.content.page.editor.web.internal.fragment.processor;

import com.liferay.fragment.processor.PortletRegistry;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(service = {})
public class PortletRegistryUtil {

	public static PortletRegistry getPortletRegistry() {
		return _portletRegistry;
	}

	@Reference(unbind = "-")
	protected void setPortletRegistry(PortletRegistry portletRegistry) {
		_portletRegistry = portletRegistry;
	}

	private static PortletRegistry _portletRegistry;

}