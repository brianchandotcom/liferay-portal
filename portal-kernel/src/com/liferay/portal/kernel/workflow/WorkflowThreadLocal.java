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

package com.liferay.portal.kernel.workflow;

import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.petra.lang.CentralizedThreadLocal;

/**
 * @author Jorge Ferrer
 */
public class WorkflowThreadLocal {

	public static boolean isEnabled() {
		if (ExportImportThreadLocal.isImportInProcess()) {
			return false;
		}

		return _enabled.get();
	}

	public static void setEnabled(boolean enabled) {
		_enabled.set(enabled);
	}

	private static final ThreadLocal<Boolean> _enabled =
		new CentralizedThreadLocal<>(
			WorkflowThreadLocal.class + "._enabled", () -> Boolean.TRUE);

}