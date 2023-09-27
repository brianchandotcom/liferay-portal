/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bulk.selection;

import com.liferay.portal.kernel.model.User;

import java.io.Serializable;

import java.util.Map;

/**
 * @author Adolfo Pérez
 */
public interface BulkSelectionAction<T> {

	public void execute(
			User user, BulkSelection<T> bulkSelection,
			Map<String, Serializable> inputMap)
		throws Exception;

}