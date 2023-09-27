/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharing.interpreter;

import com.liferay.sharing.model.SharingEntry;

/**
 * @author Alejandro Tardín
 */
public interface SharingEntryInterpreterProvider {

	public SharingEntryInterpreter getSharingEntryInterpreter(
		SharingEntry sharingEntry);

}