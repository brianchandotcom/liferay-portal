/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.machine.learning.internal.search.api;

/**
 * @author Marco Leo
 */
public interface CommerceMLIndexer {

	public void createIndex(long companyId);

	public void dropIndex(long companyId);

	public String getIndexName(long companyId);

}