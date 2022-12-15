/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.headless.commerce.machine.learning.internal.batch.engine.v1_0;

import com.liferay.batch.engine.pagination.Page;
import com.liferay.batch.engine.pagination.Pagination;
import com.liferay.headless.commerce.machine.learning.internal.odata.entity.v1_0.ModifiedDateEntityModel;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
public abstract class BaseBatchEngineTaskItemDelegate<T>
	extends com.liferay.batch.engine.BaseBatchEngineTaskItemDelegate<T> {

	@Override
	public EntityModel getEntityModel(Map<String, List<String>> multivaluedMap)
		throws Exception {

		return new ModifiedDateEntityModel();
	}

	protected Page<T> search(
			DTOConverter<?, T> dtoConverter, String entryClassName,
			Filter filter, Pagination pagination, Sort[] sorts, String search)
		throws Exception {

		com.liferay.portal.vulcan.pagination.Pagination vulcanPagination =
			com.liferay.portal.vulcan.pagination.Pagination.of(
				pagination.getPage(), pagination.getPageSize());

		com.liferay.portal.vulcan.pagination.Page<T> page = SearchUtil.search(
			null, Query::getPreBooleanFilter, filter, entryClassName, search,
			vulcanPagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> {
				searchContext.setAttribute(
					"useSearchResultPermissionFilter", Boolean.FALSE);
				searchContext.setCompanyId(contextCompany.getCompanyId());
				searchContext.setGroupIds(new long[] {0});
				searchContext.setUserId(0);

				if (contextUser.getLocale() != null) {
					searchContext.setLocale(contextUser.getLocale());
				}
			},
			sorts,
			document -> {
				long entryClassPK = GetterUtil.getLong(
					document.get(Field.ENTRY_CLASS_PK));

				return dtoConverter.toDTO(
					new DefaultDTOConverterContext(
						false, null, dtoConverterRegistry, entryClassPK,
						contextUser.getLocale(), null, contextUser));
			});

		return Page.of(
			page.getItems(),
			Pagination.of((int)page.getPage(), (int)page.getPageSize()),
			page.getTotalCount());
	}

	@Reference
	protected DTOConverterRegistry dtoConverterRegistry;

}