/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.item.creator;

import com.liferay.info.exception.InfoFormException;
import com.liferay.info.item.InfoItemFieldValues;

/**
 * @author Rubén Pulido
 */
public interface InfoItemCreator<T> {

	public T createFromInfoItemFieldValues(
			long groupId, InfoItemFieldValues infoItemFieldValues, int status)
		throws InfoFormException;

}