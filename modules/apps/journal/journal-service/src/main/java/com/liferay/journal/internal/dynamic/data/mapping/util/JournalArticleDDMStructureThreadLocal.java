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
		return _journalArticleDDMStructure.get();
	}

	public static void set(DDMStructure ddmStructure) {
		_journalArticleDDMStructure.set(ddmStructure);
	}

	public static SafeCloseable setJournalArticleDDMStructureWithSafeCloseable(
		DDMStructure ddmStructure) {

		return _journalArticleDDMStructure.setWithSafeCloseable(ddmStructure);
	}

	private static final CentralizedThreadLocal<DDMStructure>
		_journalArticleDDMStructure = new CentralizedThreadLocal<>(
			JournalArticleDDMStructureThreadLocal.class +
				"._journalArticleDDMStructure");

}