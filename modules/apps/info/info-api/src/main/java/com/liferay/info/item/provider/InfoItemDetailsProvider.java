/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.item.provider;

import com.liferay.info.item.InfoItemClassDetails;
import com.liferay.info.item.InfoItemDetails;

/**
 * @author Jorge Ferrer
 */
public interface InfoItemDetailsProvider<T> {

	public InfoItemClassDetails getInfoItemClassDetails();

	public InfoItemDetails getInfoItemDetails(T t);

}