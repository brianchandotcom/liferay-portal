/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.template;

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.util.PortalUtil;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Juan Fernández
 */
public class PortletDisplayTemplateHandlerRegistryImpl implements
	PortletDisplayTemplateHandlerRegistry {

	public PortletDisplayTemplateHandler getPortletDisplayTemplateHandler(
		long classNameId) {

		return _portletDisplayTemplateHandlers.get(
			PortalUtil.getClassName(classNameId));
	}

	public PortletDisplayTemplateHandler getPortletDisplayTemplateHandler(
		String className) {

		return _portletDisplayTemplateHandlers.get(className);
	}

	public List<PortletDisplayTemplateHandler>
		getPortletDisplayTemplateHandlers() {

		return ListUtil.fromMapValues(_portletDisplayTemplateHandlers);
	}

	public void register(
		PortletDisplayTemplateHandler portletDisplayTemplateHandler) {

		_portletDisplayTemplateHandlers.put(
			portletDisplayTemplateHandler.getClassName(),
			portletDisplayTemplateHandler);
	}

	public void unregister(
		PortletDisplayTemplateHandler portletDisplayTemplateHandler) {

		_portletDisplayTemplateHandlers.remove(
			portletDisplayTemplateHandler.getClassName());
	}

	private Map<String, PortletDisplayTemplateHandler>
		_portletDisplayTemplateHandlers =
			new TreeMap<String, PortletDisplayTemplateHandler>();

}