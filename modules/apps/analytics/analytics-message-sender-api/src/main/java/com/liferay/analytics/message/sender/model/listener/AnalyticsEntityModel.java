/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.message.sender.model.listener;

import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.User;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Rachael Koestartyo
 */
@ProviderType
public interface AnalyticsEntityModel<T extends BaseModel<T>> {

	public void addAnalyticsMessage(
		String eventType, List<String> includeAttributeNames, T model);

	public List<String> getAttributeNames(long companyId);

	public long[] getMembershipIds(User user) throws Exception;

	public T getModel(long id) throws Exception;

	public String getModelClassName();

	public boolean isExcluded(T model);

	public void syncAll(long companyId) throws Exception;

}