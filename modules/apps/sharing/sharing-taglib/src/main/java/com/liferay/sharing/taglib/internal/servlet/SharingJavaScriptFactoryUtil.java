/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharing.taglib.internal.servlet;

import com.liferay.osgi.util.service.Snapshot;
import com.liferay.sharing.display.context.util.SharingJavaScriptFactory;

/**
 * @author Alejandro Tardín
 */
public class SharingJavaScriptFactoryUtil {

	public static SharingJavaScriptFactory getSharingJavaScriptFactory() {
		return _sharingJavaScriptFactorySnapshot.get();
	}

	private static final Snapshot<SharingJavaScriptFactory>
		_sharingJavaScriptFactorySnapshot = new Snapshot<>(
			SharingJavaScriptFactoryUtil.class, SharingJavaScriptFactory.class);

}