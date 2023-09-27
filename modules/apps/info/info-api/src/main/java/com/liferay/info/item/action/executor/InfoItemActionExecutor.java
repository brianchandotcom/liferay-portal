/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.item.action.executor;

import com.liferay.info.exception.InfoItemActionExecutionException;
import com.liferay.info.item.InfoItemIdentifier;

/**
 * @author Rubén Pulido
 */
public interface InfoItemActionExecutor<T> {

	public void executeInfoItemAction(
			InfoItemIdentifier infoItemIdentifier, String fieldId)
		throws InfoItemActionExecutionException;

}