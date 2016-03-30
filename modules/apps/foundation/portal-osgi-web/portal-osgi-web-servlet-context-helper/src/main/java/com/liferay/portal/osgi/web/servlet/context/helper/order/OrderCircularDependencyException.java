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

package com.liferay.portal.osgi.web.servlet.context.helper.order;

import com.liferay.portal.osgi.web.servlet.context.helper.definition.WebXMLDefinition;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;

/**
 * @author Vernon Singleton
 * @author Juan Gonzalez
 *
 */
public class OrderCircularDependencyException extends Exception {

	public OrderCircularDependencyException(
		Order.Path path, List<WebXMLDefinition> facesConfigs) {

		super(createMessage(path, facesConfigs));
	}

	private static String createMessage(
		Order.Path path, List<WebXMLDefinition> webXMLDefinitions) {

		StringBuilder message = new StringBuilder();

		message.append("Circular dependencies detected when traversing '");
		message.append(path.name());
		message.append("' declarations:");

		for (WebXMLDefinition webXMLDefinition : webXMLDefinitions) {
			Order someOrdering = webXMLDefinition.getOrdering();

			EnumMap<Order.Path, String[]> someRoutes = someOrdering.getRoutes();

			String[] someNames = someRoutes.get(path);

			if (someNames.length != 0) {
				message.append(" ");
				message.append(webXMLDefinition.getFragmentName());
				message.append(" ");
				message.append(path.name());
				message.append(": ");
				message.append(Arrays.asList(someNames).toString());
				message.append("\n");
			}
		}

		return message.toString();
	}

}