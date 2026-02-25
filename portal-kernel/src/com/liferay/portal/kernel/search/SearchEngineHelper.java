/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.search;

import java.util.concurrent.ExecutorService;

/**
 * @author Michael C. Han
 */
public interface SearchEngineHelper {

	public ExecutorService getDocumentsConsumerExecutorService();

	public ExecutorService getDocumentsProducerExecutorService();

	public String[] getEntryClassNames();

	public SearchEngine getSearchEngine();

	public void initialize(long companyId);

	public void removeCompany(long companyId);

}