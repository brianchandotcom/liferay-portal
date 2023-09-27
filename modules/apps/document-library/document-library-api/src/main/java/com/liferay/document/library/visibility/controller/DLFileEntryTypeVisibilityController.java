/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.visibility.controller;

import com.liferay.document.library.kernel.model.DLFileEntryType;

/**
 * @author Adolfo Pérez
 */
public interface DLFileEntryTypeVisibilityController {

	public boolean isVisible(long userId, DLFileEntryType dlFileEntryType);

}