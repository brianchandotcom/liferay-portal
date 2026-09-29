/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.machine.learning.internal.batch.engine.v1_0;

import com.liferay.batch.engine.pagination.Page;
import com.liferay.batch.engine.pagination.Pagination;
import com.liferay.headless.commerce.machine.learning.internal.odata.entity.v1_0.ModifiedDateEntityModel;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.io.Serializable;

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

	@Override
	public final Page<T> read(
			Filter filter, Pagination pagination, Sort[] sorts,
			Map<String, Serializable> parameters, String search)
		throws Exception {

		_checkPermission();

		return doRead(filter, pagination, sorts, parameters, search);
	}

	protected abstract Page<T> doRead(
			Filter filter, Pagination pagination, Sort[] sorts,
			Map<String, Serializable> parameters, String search)
		throws Exception;

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

	@Reference
	protected RoleLocalService roleLocalService;

	private void _checkPermission() throws Exception {
		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (permissionChecker == null) {
			throw new PrincipalException(
				"Unable to read commerce machine learning entities without a " +
					"permission checker");
		}

		long companyId = contextCompany.getCompanyId();

		if (permissionChecker.isCompanyAdmin(companyId) ||
			roleLocalService.hasUserRole(
				permissionChecker.getUserId(), companyId,
				RoleConstants.ANALYTICS_ADMINISTRATOR, true)) {

			return;
		}

		throw new PrincipalException.MustHavePermission(
			permissionChecker.getUserId(), Company.class.getName(), companyId,
			ActionKeys.VIEW);
	}

}