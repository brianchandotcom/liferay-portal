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

package com.liferay.portal.osgi.web.servlet.context.helper.internal.order;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.osgi.web.servlet.context.helper.definition.WebXMLDefinition;
import com.liferay.portal.osgi.web.servlet.context.helper.order.Order;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;

/**
 * @author Vernon Singleton
 * @author Juan González
 */
public class OrderCircularDependencyException extends Exception {

	public OrderCircularDependencyException(
		Order.Path path, List<WebXMLDefinition> webXMLDefinitions) {

		super(_getMessage(path, webXMLDefinitions));
	}

	private static String _getMessage(
		Order.Path path, List<WebXMLDefinition> webXMLDefinitions) {

		StringBundler sb = new StringBundler();

		sb.append("Circular dependencies detected when traversing ");
		sb.append(path.name());
		sb.append(" declarations:");

		for (WebXMLDefinition webXMLDefinition : webXMLDefinitions) {
			Order order = webXMLDefinition.getOrder();

			EnumMap<Order.Path, String[]> routes = order.getRoutes();

			String[] names = routes.get(path);

			if (names.length != 0) {
				sb.append("\n");
				sb.append(webXMLDefinition.getFragmentName());
				sb.append(" ");
				sb.append(path.name());
				sb.append(": ");
				sb.append(Arrays.asList(names));
			}
		}

		return sb.toString();
	}

}