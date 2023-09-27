/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.search;

/**
 * @author Rafael Praxedes
 */
public interface DDMFormInstanceRecordBatchReindexer {

	public void reindex(long formInstaceId, long companyId);

}