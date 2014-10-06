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

package com.liferay.typeclasses;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carlos Sierra Andrés
 */
@Component(
	immediate = true,
	service = Object.class,
	property = {
		"osgi.command.function=printService",
		"osgi.command.scope=liferayService"
	}
)
public class Receiver {

	private LiferayService _liferayService;

	public void printService() {
		System.out.println(_liferayService);
	}

	@Reference(target = "")
	public void setServiceOne(LiferayService liferayService) {
		_liferayService = liferayService;

		System.out.println(_liferayService);
	}
}
