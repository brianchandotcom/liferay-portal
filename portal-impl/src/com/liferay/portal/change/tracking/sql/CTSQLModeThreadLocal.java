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

package com.liferay.portal.change.tracking.sql;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.lang.SafeClosable;

/**
 * @author Preston Crary
 */
public class CTSQLModeThreadLocal {

	public static CTSQLMode getCTSQLMode() {
		return _ctSQLMode.get();
	}

	public static SafeClosable setCTSQLMode(CTSQLMode ctSQLMode) {
		return _ctSQLMode.setWithSafeClosable(ctSQLMode);
	}

	public static enum CTSQLMode {

		CT_ONLY, DEFAULT,

	}

	private CTSQLModeThreadLocal() {
	}

	private static final CentralizedThreadLocal<CTSQLMode> _ctSQLMode =
		new CentralizedThreadLocal<>(
			CTSQLModeThreadLocal.class + "._ctSQLMode",
			() -> CTSQLMode.DEFAULT);

}