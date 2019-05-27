/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.data.engine.rest.resource.v1_0;

import com.liferay.data.engine.rest.dto.v1_0.DataDefinition;
import com.liferay.data.engine.rest.dto.v1_0.DataDefinitionPermission;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import javax.annotation.Generated;

/**
 * To access this resource, run:
 *
 *     curl -u your@email.com:yourpassword -D - http://localhost:8080/o/data-engine/v1.0
 *
 * @author Jeyvison Nascimento
 * @generated
 */
@Generated("")
public interface DataDefinitionResource {

	public void deleteDataDefinition(Long dataDefinitionId) throws Exception;

	public DataDefinition getDataDefinition(Long dataDefinitionId)
		throws Exception;

	public DataDefinition putDataDefinition(
			Long dataDefinitionId, DataDefinition dataDefinition)
		throws Exception;

	public void postDataDefinitionDataDefinitionPermission(
			Long dataDefinitionId, String operation,
			DataDefinitionPermission dataDefinitionPermission)
		throws Exception;

	public void postSiteDataDefinitionPermission(
			Long siteId, String operation,
			DataDefinitionPermission dataDefinitionPermission)
		throws Exception;

	public Page<DataDefinition> getSiteDataDefinitionsPage(
			Long siteId, String keywords, Pagination pagination)
		throws Exception;

	public DataDefinition postSiteDataDefinition(
			Long siteId, DataDefinition dataDefinition)
		throws Exception;

	public DataDefinition getSiteDataDefinition(
			Long siteId, String dataDefinitionKey)
		throws Exception;

	public void setContextCompany(Company contextCompany);

}