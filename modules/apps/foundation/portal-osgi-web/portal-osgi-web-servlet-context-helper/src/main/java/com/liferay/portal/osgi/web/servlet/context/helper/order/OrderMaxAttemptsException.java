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

/**
 * @author Vernon Singleton
 * @author Juan Gonzalez
 *
 */
public class OrderMaxAttemptsException extends Exception {

	public OrderMaxAttemptsException(int limit) {
		super(
			"Too many attempts needed to sort the faces-config files." +
				"Exceeded the limit of " + limit + " passes.");
	}

}