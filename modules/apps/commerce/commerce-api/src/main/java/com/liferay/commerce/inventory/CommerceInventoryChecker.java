/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory;

import com.liferay.portal.kernel.model.BaseModel;

import java.util.List;

/**
 * @author Igor Beslic
 */
public interface CommerceInventoryChecker<T> {

	public List<T> filterByAvailability(List<T> baseModels);

	public boolean isAvailable(BaseModel<T> baseModel);

}