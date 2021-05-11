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

/**
 * @author Javier de Arcos
 */
public interface GraphQLDTOContributor<D, R> {

	public R createDTO(D dto, DTOConverterContext dtoConverterContext)
		throws Exception;

	public boolean deleteDTO(long id) throws Exception;

	public R getDTO(long id, DTOConverterContext dtoConverterContext)
		throws Exception;

	public Page<R> getDTOs(
			Aggregation aggregation, Filter filter, Pagination pagination,
			String search, Sort[] sorts,
			DTOConverterContext dtoConverterContext)
		throws Exception;

	public EntityModel getEntityModel();

	public String getIdName();

	public List<GraphQLDTOProperty> getProperties();

	public String getResourceName();

	public R updateDTO(long id, D dto, DTOConverterContext dtoConverterContext)
		throws Exception;

}