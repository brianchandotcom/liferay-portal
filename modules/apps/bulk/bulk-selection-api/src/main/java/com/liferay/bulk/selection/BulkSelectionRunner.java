/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bulk.selection;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;

import java.io.Serializable;

import java.util.Map;

/**
 * @author Alejandro Tardín
 */
public interface BulkSelectionRunner {

	public boolean isBusy(User user);

	public <T> void run(
			User user, BulkSelection<T> bulkSelection,
			BulkSelectionAction<T> bulkSelectionAction,
			Map<String, Serializable> inputMap)
		throws PortalException;

}