/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

import com.liferay.petra.lang.CentralizedThreadLocal;

/**
 * @author Eudaldo Alonso
 */
public class RenderLayoutContentThreadLocal {

	public static boolean isRenderLayoutContent() {
		return _renderLayoutContent.get();
	}

	public static void setRenderLayoutContent(boolean enabled) {
		_renderLayoutContent.set(enabled);
	}

	private static final ThreadLocal<Boolean> _renderLayoutContent =
		new CentralizedThreadLocal<>(
			RenderLayoutContentThreadLocal.class + "._renderLayoutContent",
			() -> Boolean.FALSE);

}