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

package com.liferay.document.library.google.docs.context;

import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portlet.documentlibrary.context.BaseDLFileVersionActionsDisplayContext;
import com.liferay.portlet.documentlibrary.context.DLFileVersionActionsDisplayContext;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Iván Zaera
 */
public class GoogleDocsDLFileVersionActionsDisplayContext
	extends BaseDLFileVersionActionsDisplayContext {

	public GoogleDocsDLFileVersionActionsDisplayContext(
		DLFileVersionActionsDisplayContext parentDLDisplayContext,
		HttpServletRequest request, HttpServletResponse response,
		FileVersion fileVersion) {

		super(_UUID, parentDLDisplayContext, request, response, fileVersion);
	}

	private static final UUID _UUID = UUID.fromString(
		"7B61EA79-83AE-4FFD-A77A-1D47E06EBBE9");

}