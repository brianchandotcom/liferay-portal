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

package com.liferay.message.boards.web.internal.util;

import com.liferay.message.boards.web.internal.display.context.MBDisplayContextProvider;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera
 */
@Component(service = {})
public class MBDisplayContextUtil {

	public static MBDisplayContextUtil getMBWebComponentProvider() {
		return _mbDisplayContextUtil;
	}

	public MBDisplayContextProvider getMBDisplayContextProvider() {
		return _mbDisplayContextProvider;
	}

	@Activate
	protected void activate() {
		_mbDisplayContextUtil = this;
	}

	@Deactivate
	protected void deactivate() {
		_mbDisplayContextUtil = null;
	}

	private static MBDisplayContextUtil _mbDisplayContextUtil;

	@Reference
	private MBDisplayContextProvider _mbDisplayContextProvider;

}