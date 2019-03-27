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

package com.liferay.document.library.web.internal.util;

import com.liferay.document.library.display.context.DLDisplayContextProvider;
import com.liferay.document.library.web.internal.display.context.DLAdminDisplayContextProvider;
import com.liferay.document.library.web.internal.display.context.IGDisplayContextProvider;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera
 */
@Component(immediate = true, service = {})
public class DLWebComponentProvider {

	public static DLWebComponentProvider getDLWebComponentProvider() {
		return _dlWebComponentProvider;
	}

	public DLAdminDisplayContextProvider getDLAdminDisplayContextProvider() {
		return _dlAdminDisplayContextProvider;
	}

	public DLDisplayContextProvider getDLDisplayContextProvider() {
		return _dlDisplayContextProvider;
	}

	public IGDisplayContextProvider getIGDisplayContextProvider() {
		return _igDisplayContextProvider;
	}

	@Activate
	protected void activate() {
		_dlWebComponentProvider = this;
	}

	@Deactivate
	protected void deactivate() {
		_dlWebComponentProvider = null;
	}

	@Reference(unbind = "-")
	protected void setDLDisplayContextProvider(
		DLDisplayContextProvider dlDisplayContextProvider) {

		_dlDisplayContextProvider = dlDisplayContextProvider;
	}

	@Reference(unbind = "-")
	protected void setIGDisplayContextProvider(
		IGDisplayContextProvider igDisplayContextProvider) {

		_igDisplayContextProvider = igDisplayContextProvider;
	}

	private static DLWebComponentProvider _dlWebComponentProvider;

	@Reference
	private DLAdminDisplayContextProvider _dlAdminDisplayContextProvider;

	private DLDisplayContextProvider _dlDisplayContextProvider;
	private IGDisplayContextProvider _igDisplayContextProvider;

}