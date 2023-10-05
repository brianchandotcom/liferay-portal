/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.batch.exportimport.internal.engine;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountEntryTable;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.analytics.batch.exportimport.internal.dto.v1_0.converter.constants.DTOConverterConstants;
import com.liferay.analytics.batch.exportimport.internal.odata.entity.AccountEntryAnalyticsDXPEntityEntityModel;
import com.liferay.analytics.dxp.entity.rest.dto.v1_0.DXPEntity;
import com.liferay.batch.engine.BatchEngineTaskItemDelegate;
import com.liferay.batch.engine.pagination.Page;
import com.liferay.batch.engine.pagination.Pagination;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcos Martins
 */
@Component(
	property = "batch.engine.task.item.delegate.name=account-entry-analytics-dxp-entities",
	service = BatchEngineTaskItemDelegate.class
)
public class AccountEntryAnalyticsDXPEntityBatchEngineTaskItemDelegate
	extends BaseAnalyticsDXPEntityBatchEngineTaskItemDelegate<DXPEntity> {

	@Override
	public EntityModel getEntityModel(Map<String, List<String>> multivaluedMap)
		throws Exception {

		return _entityModel;
	}

	@Override
	public Page<DXPEntity> read(
			Filter filter, Pagination pagination, Sort[] sorts,
			Map<String, Serializable> parameters, String search)
		throws Exception {

		return Page.of(
			TransformUtil.transform(
				_accountEntryLocalService.<List<AccountEntry>>dslQuery(
					_createSelectDSLQuery(
						contextCompany.getCompanyId(), filter, pagination)),
				accountEntry -> _dxpEntityDTOConverter.toDTO(accountEntry)),
			Pagination.of(pagination.getPage(), pagination.getPageSize()),
			_accountEntryLocalService.dslQuery(
				_createCountDSLQuery(contextCompany.getCompanyId(), filter)));
	}

	private DSLQuery _createCountDSLQuery(long companyId, Filter filter) {
		return DSLQueryFactoryUtil.count(
		).from(
			AccountEntryTable.INSTANCE
		).where(
			buildPredicate(
				AccountEntryTable.INSTANCE, companyId,
				AccountEntryTable.INSTANCE.companyId.isNotNull(), filter)
		);
	}

	private DSLQuery _createSelectDSLQuery(
		long companyId, Filter filter, Pagination pagination) {

		return DSLQueryFactoryUtil.select(
		).from(
			AccountEntryTable.INSTANCE
		).where(
			buildPredicate(
				AccountEntryTable.INSTANCE, companyId,
				AccountEntryTable.INSTANCE.companyId.isNotNull(), filter)
		).limit(
			pagination.getPage() * pagination.getPageSize(),
			(pagination.getPage() + 1) * pagination.getPageSize()
		);
	}

	private static final EntityModel _entityModel =
		new AccountEntryAnalyticsDXPEntityEntityModel();

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference(target = DTOConverterConstants.DXP_ENTITY_DTO_CONVERTER)
	private DTOConverter<BaseModel<?>, DXPEntity> _dxpEntityDTOConverter;

}