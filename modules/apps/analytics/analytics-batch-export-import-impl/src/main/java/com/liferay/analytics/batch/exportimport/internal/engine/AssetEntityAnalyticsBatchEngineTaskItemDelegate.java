/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.batch.exportimport.internal.engine;

import com.liferay.analytics.dxp.entity.rest.dto.v1_0.AssetEntity;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetEntryTable;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.batch.engine.BatchEngineTaskItemDelegate;
import com.liferay.batch.engine.pagination.Page;
import com.liferay.batch.engine.pagination.Pagination;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManager;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;

import java.io.Serializable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
@Component(
	property = "batch.engine.task.item.delegate.name=asset-entry-analytics-dxp-entities",
	service = BatchEngineTaskItemDelegate.class
)
public class AssetEntityAnalyticsBatchEngineTaskItemDelegate
	extends BaseAnalyticsDXPEntityBatchEngineTaskItemDelegate<AssetEntity> {

	@Override
	public Class<AssetEntity> getItemClass() {
		return AssetEntity.class;
	}

	@Override
	public Page<AssetEntity> read(
			Filter filter, Pagination pagination, Sort[] sorts,
			Map<String, Serializable> parameters, String search)
		throws Exception {

		if (!_featureFlagManager.isEnabled("LRAC-14771")) {
			return Page.of(
				Collections.emptyList(),
				Pagination.of(pagination.getPage(), pagination.getPageSize()),
				0);
		}

		return Page.of(
			TransformUtil.transform(
				_assetEntryLocalService.<List<AssetEntry>>dslQuery(
					_createSelectDSLQuery(
						contextCompany.getCompanyId(), pagination, parameters)),
				assetEntry -> _assetEntityDTOConverter.toDTO(assetEntry)),
			Pagination.of(pagination.getPage(), pagination.getPageSize()),
			_assetEntryLocalService.dslQuery(
				_createCountDSLQuery(
					contextCompany.getCompanyId(), parameters)));
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_classNameIds = TransformUtil.transform(
			_classNames, className -> _portal.getClassNameId(className));
	}

	private Predicate _buildPredicate(
			long companyId, Map<String, Serializable> parameters)
		throws Exception {

		Predicate predicate = buildPredicate(
			AssetEntryTable.INSTANCE, companyId, parameters);

		return predicate.and(
			AssetEntryTable.INSTANCE.classNameId.in(
				_classNameIds.toArray(new Long[0])));
	}

	private DSLQuery _createCountDSLQuery(
			long companyId, Map<String, Serializable> parameters)
		throws Exception {

		return DSLQueryFactoryUtil.count(
		).from(
			AssetEntryTable.INSTANCE
		).where(
			_buildPredicate(companyId, parameters)
		);
	}

	private DSLQuery _createSelectDSLQuery(
			long companyId, Pagination pagination,
			Map<String, Serializable> parameters)
		throws Exception {

		return DSLQueryFactoryUtil.select(
		).from(
			AssetEntryTable.INSTANCE
		).where(
			_buildPredicate(companyId, parameters)
		).limit(
			(pagination.getPage() - 1) * pagination.getPageSize(),
			pagination.getPage() * pagination.getPageSize()
		);
	}

	private static final List<String> _classNames = Arrays.asList(
		"com.liferay.document.library.kernel.model.DLFileEntry",
		"com.liferay.blogs.model.BlogsEntry",
		"com.liferay.journal.model.JournalArticle",
		"com.liferay.knowledge.base.model.KBArticle");

	@Reference(
		target = "(component.name=com.liferay.analytics.batch.exportimport.internal.dto.v1_0.converter.AssetEntityDTOConverter)"
	)
	private DTOConverter<AssetEntry, AssetEntity> _assetEntityDTOConverter;

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	private List<Long> _classNameIds;

	@Reference
	private FeatureFlagManager _featureFlagManager;

	@Reference
	private Portal _portal;

}