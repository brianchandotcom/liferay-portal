/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.internal.dynamic.data.mapping.util;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.lang.SafeCloseable;

/**
 * @author Jürgen Kappler
 */
public class JournalArticleDDMStructureThreadLocal {

	public static DDMStructure get() {
		return _ddmStructure.get();
	}

	public static void set(DDMStructure ddmStructure) {
		_ddmStructure.set(ddmStructure);
	}

	public static SafeCloseable setDDMStructureWithSafeCloseable(
		DDMStructure ddmStructure) {

		return _ddmStructure.setWithSafeCloseable(ddmStructure);
	}

	private static final CentralizedThreadLocal<DDMStructure> _ddmStructure =
		new CentralizedThreadLocal<>(
			JournalArticleDDMStructureThreadLocal.class + "._ddmStructure");

}