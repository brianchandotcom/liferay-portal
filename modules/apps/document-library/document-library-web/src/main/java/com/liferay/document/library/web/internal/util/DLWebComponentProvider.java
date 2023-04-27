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
import com.liferay.osgi.util.service.Snapshot;

/**
 * @author Iván Zaera
 */
public class DLWebComponentProvider {

	public static DLAdminDisplayContextProvider
		getDlAdminDisplayContextProvider() {

		return _dlAdminDisplayContextProviderSnapshot.get();
	}

	public static DLDisplayContextProvider getDLDisplayContextProvider() {
		return _dlDisplayContextProviderSnapshot.get();
	}

	public static IGDisplayContextProvider getIGDisplayContextProvider() {
		return _igDisplayContextProviderSnapshot.get();
	}

	private static final Snapshot<DLAdminDisplayContextProvider>
		_dlAdminDisplayContextProviderSnapshot = new Snapshot<>(
			DLWebComponentProvider.class, DLAdminDisplayContextProvider.class);
	private static final Snapshot<DLDisplayContextProvider>
		_dlDisplayContextProviderSnapshot = new Snapshot<>(
			DLWebComponentProvider.class, DLDisplayContextProvider.class);
	private static final Snapshot<IGDisplayContextProvider>
		_igDisplayContextProviderSnapshot = new Snapshot<>(
			DLWebComponentProvider.class, IGDisplayContextProvider.class);

}