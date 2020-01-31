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

package com.liferay.change.tracking.display;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.change.tracking.CTModel;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Samuel Trong Tran
 */
public interface CTDisplayRenderer<T extends CTModel<T>> {

	public String getEditURL(HttpServletRequest httpServletRequest, T ctModel)
		throws Exception;

	public Class<T> getModelClass();

	public String getTypeName(Locale locale);

	public default String getTypeName(Locale locale, T ctModel)
		throws PortalException {

		return getTypeName(locale);
	}

	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, T ctModel)
		throws Exception;

}