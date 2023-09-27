/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.message.sender.model;

import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.User;

import java.util.List;

/**
 * @author     Rachael Koestartyo
 * @deprecated As of Athanasius (7.3.x), replaced by {@link
 *             com.liferay.analytics.message.sender.model.listener.EntityModelListener}
 */
@Deprecated
public interface EntityModelListener<T extends BaseModel<T>> {

	public void addAnalyticsMessage(
		String eventType, List<String> includeAttributeNames, T model);

	public List<String> getAttributeNames();

	public long[] getMembershipIds(User user) throws Exception;

	public String getModelClassName();

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #syncAll(long)}
	 */
	@Deprecated
	public default void syncAll() throws Exception {
	}

	public void syncAll(long companyId) throws Exception;

}