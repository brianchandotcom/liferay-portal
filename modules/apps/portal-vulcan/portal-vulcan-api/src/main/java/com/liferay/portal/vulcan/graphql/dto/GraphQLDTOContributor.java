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

package com.liferay.portal.vulcan.graphql.dto;

import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.aggregation.Aggregation;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Javier de Arcos
 */
@ProviderType
public interface GraphQLDTOContributor<D, R> {

	public R createDTO(D dto, DTOConverterContext dtoConverterContext)
		throws Exception;

	public boolean deleteDTO(long id) throws Exception;

	public long getCompanyId();

	public R getDTO(DTOConverterContext dtoConverterContext, long id)
		throws Exception;

	public Page<R> getDTOs(
			Aggregation aggregation, DTOConverterContext dtoConverterContext,
			Filter filter, Pagination pagination, String search, Sort[] sorts)
		throws Exception;

	public EntityModel getEntityModel();

	public List<GraphQLDTOProperty> getGraphQLDTOProperties();

	public String getIdName();

	public String getResourceName();

	public String getTypeName();

	public boolean hasScope();

	public R updateDTO(D dto, DTOConverterContext dtoConverterContext, long id)
		throws Exception;

}