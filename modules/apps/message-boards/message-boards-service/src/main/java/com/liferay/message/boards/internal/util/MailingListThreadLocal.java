/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.internal.util;

import com.liferay.petra.lang.CentralizedThreadLocal;

/**
 * @author Thiago Moreira
 */
public class MailingListThreadLocal {

	public static boolean isSourceMailingList() {
		return _sourceMailingList.get();
	}

	public static void setSourceMailingList(boolean sourceMailingList) {
		_sourceMailingList.set(sourceMailingList);
	}

	private static final ThreadLocal<Boolean> _sourceMailingList =
		new CentralizedThreadLocal<>(
			MailingListThreadLocal.class + "._sourceMailingList",
			() -> Boolean.FALSE, false);

}