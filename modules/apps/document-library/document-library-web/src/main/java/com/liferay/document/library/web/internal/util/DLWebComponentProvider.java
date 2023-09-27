/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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