/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.rest.manager.v1_0;

import com.liferay.ai.hub.rest.dto.v1_0.TaskDefinition;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

/**
 * @author Feliphe Marinho
 * @author João Victor Alves
 */
public interface TaskDefinitionManager {

	public Page<TaskDefinition> getTaskDefinitions(
			long companyId, DTOConverterContext dtoConverterContext,
			String search, Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception;

}