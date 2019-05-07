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

package com.liferay.portal.kernel.display.context;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Iván Zaera
 */
public abstract class BaseDisplayContext<T extends DisplayContext>
	implements DisplayContext {

	public BaseDisplayContext(
		UUID uuid, T parentDisplayContext,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		_uuid = uuid;

		this.parentDisplayContext = parentDisplayContext;
		request = httpServletRequest;
		response = httpServletResponse;
	}

	@Override
	public UUID getUuid() {
		return _uuid;
	}

	protected final T parentDisplayContext;
	protected final HttpServletRequest request;
	protected final HttpServletResponse response;

	private final UUID _uuid;

}