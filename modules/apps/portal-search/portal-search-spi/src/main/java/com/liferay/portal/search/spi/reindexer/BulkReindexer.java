/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.spi.reindexer;

import java.util.Collection;

/**
 * @author André de Oliveira
 */
public interface BulkReindexer {

	public void reindex(long companyId, Collection<Long> classPKs);

}