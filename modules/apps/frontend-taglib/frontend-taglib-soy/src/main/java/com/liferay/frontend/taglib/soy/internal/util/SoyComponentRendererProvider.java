/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.soy.internal.util;

import com.liferay.frontend.taglib.soy.internal.template.SoyComponentRenderer;
import com.liferay.osgi.util.service.Snapshot;

/**
 * @author Iván Zaera Avellón
 */
public class SoyComponentRendererProvider {

	public static SoyComponentRenderer getSoyComponentRenderer() {
		return _soyComponentRendererSnapshot.get();
	}

	private static final Snapshot<SoyComponentRenderer>
		_soyComponentRendererSnapshot = new Snapshot<>(
			SoyComponentRendererProvider.class, SoyComponentRenderer.class);

}