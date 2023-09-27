/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.taglib.internal.frontend.js.loader.modules.extender.npm;

import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.osgi.util.service.Snapshot;

/**
 * @author Chema Balsas
 */
public class NPMResolverProvider {

	public static NPMResolver getNPMResolver() {
		return _npmResolverSnapshot.get();
	}

	private static final Snapshot<NPMResolver> _npmResolverSnapshot =
		new Snapshot<>(NPMResolverProvider.class, NPMResolver.class);

}