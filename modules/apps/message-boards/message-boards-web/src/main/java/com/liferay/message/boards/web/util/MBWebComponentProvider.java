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

package com.liferay.message.boards.web.util;

import com.liferay.message.boards.web.display.context.MBDisplayContextProvider;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera
 */
@Component(immediate = true)
public class MBWebComponentProvider {

	public static MBWebComponentProvider getMBWebComponentProvider() {
		return _mbWebComponentProvider;
	}

	public MBDisplayContextProvider getMBDisplayContextProvider() {
		return _mbDisplayContextProvider;
	}

	@Reference(unbind = "-")
	public void setMBDisplayContextProvider(
		MBDisplayContextProvider mbDisplayContextProvider) {

		_mbDisplayContextProvider = mbDisplayContextProvider;
	}

	@Activate
	protected void activate() {
		_mbWebComponentProvider = this;
	}

	@Deactivate
	protected void deactivate() {
		_mbWebComponentProvider = null;
	}

	private static MBWebComponentProvider _mbWebComponentProvider;

	private volatile MBDisplayContextProvider _mbDisplayContextProvider;

}