/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.internal.resource.v1_0;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.search.AssetSearcherFactoryUtil;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.depot.util.SiteConnectedGroupGroupProviderUtil;
import com.liferay.headless.delivery.dto.v1_0.AssetEntry;
import com.liferay.headless.delivery.internal.odata.entity.v1_0.AssetEntryEntityModel;
import com.liferay.headless.delivery.resource.v1_0.AssetEntryResource;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.SearchUtil;

import jakarta.ws.rs.core.MultivaluedMap;

import java.util.Arrays;
import java.util.Collections;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Luis Ortiz
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/asset-entry.properties",
	scope = ServiceScope.PROTOTYPE, service = AssetEntryResource.class
)
public class AssetEntryResourceImpl extends BaseAssetEntryResourceImpl {

	@Override
	public Page<AssetEntry> getAssetEntriesPage(
			Long[] groupIds, String search, Boolean showNonindexable,
			Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception {

		AssetEntryQuery assetEntryQuery = new AssetEntryQuery();

		assetEntryQuery.setClassNameIds(
			AssetRendererFactoryRegistryUtil.getClassNameIds(
				contextCompany.getCompanyId()));

		return SearchUtil.search(
			null,
			booleanQuery -> {
			},
			filter,
			AssetSearcherFactoryUtil.createBaseSearcher(assetEntryQuery),
			GetterUtil.getString(search), pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK),
			searchContext -> {
				searchContext.setAttribute(
					Field.STATUS, WorkflowConstants.STATUS_ANY);
				searchContext.setAttribute(
					"showNonindexable",
					GetterUtil.getBoolean(showNonindexable));
				searchContext.setCompanyId(contextCompany.getCompanyId());

				if (ArrayUtil.isNotEmpty(groupIds)) {
					searchContext.setGroupIds(
						SiteConnectedGroupGroupProviderUtil.
							getCurrentAndAncestorSiteAndDepotGroupIds(
								ArrayUtil.toLongArray(
									Arrays.asList(groupIds))));
				}

				searchContext.setUserId(contextUser.getUserId());
			},
			sorts, document -> _toAssetEntry(document));
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap) {
		return _entityModel;
	}

	private AssetEntry _toAssetEntry(Document document) throws Exception {
		com.liferay.asset.kernel.model.AssetEntry serviceBuilderAssetEntry =
			_assetEntryLocalService.fetchEntry(
				GetterUtil.getString(document.get(Field.ENTRY_CLASS_NAME)),
				GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)));

		if (serviceBuilderAssetEntry == null) {
			return null;
		}

		return _assetEntryDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(),
				Collections.emptyMap(), _dtoConverterRegistry,
				serviceBuilderAssetEntry.getEntryId(),
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser),
			serviceBuilderAssetEntry);
	}

	private static final EntityModel _entityModel = new AssetEntryEntityModel();

	@Reference(
		target = "(component.name=com.liferay.headless.delivery.internal.dto.v1_0.converter.AssetEntryDTOConverter)"
	)
	private DTOConverter<com.liferay.asset.kernel.model.AssetEntry, AssetEntry>
		_assetEntryDTOConverter;

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

}