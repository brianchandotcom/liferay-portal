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

package com.liferay.sharing.web.internal.display.context.util;

import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.Portal;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Joao Victor Alves
 */
public abstract class BaseSharingItemFactory {

	public String getLabel(String key, HttpServletRequest httpServletRequest) {
		return language.get(portal.getLocale(httpServletRequest), key);
	}

	public String getManageCollaboratorsLabel(
		HttpServletRequest httpServletRequest) {

		return getLabel("manage-collaborators", httpServletRequest);
	}

	public String getSharingLabel(HttpServletRequest httpServletRequest) {
		return getLabel("share", httpServletRequest);
	}

	@Reference
	protected Language language;

	@Reference
	protected Portal portal;

}