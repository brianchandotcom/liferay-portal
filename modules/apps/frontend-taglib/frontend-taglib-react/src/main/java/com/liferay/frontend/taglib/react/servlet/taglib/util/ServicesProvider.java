/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.react.servlet.taglib.util;

import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.template.react.renderer.ReactRenderer;

/**
 * @author Chema Balsas
 */
public class ServicesProvider {

	public static ReactRenderer getReactRenderer() {
		return _reactRendererSnapshot.get();
	}

	private static final Snapshot<ReactRenderer> _reactRendererSnapshot =
		new Snapshot<>(ServicesProvider.class, ReactRenderer.class);

}