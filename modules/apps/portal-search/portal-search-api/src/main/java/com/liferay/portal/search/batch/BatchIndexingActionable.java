/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.batch;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.search.Document;

import java.util.function.Consumer;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author André de Oliveira
 */
@ProviderType
public interface BatchIndexingActionable {

	public void addDocuments(Document... documents);

	public void performActions();

	public void setAddCriteriaMethod(Consumer<DynamicQuery> consumer);

	public void setCompanyId(long companyId);

	public void setInterval(int interval);

	public <T> void setPerformActionMethod(Consumer<T> consumer);

}