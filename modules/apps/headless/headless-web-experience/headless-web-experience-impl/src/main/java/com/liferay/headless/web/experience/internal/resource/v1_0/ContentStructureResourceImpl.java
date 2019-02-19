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

package com.liferay.headless.web.experience.internal.resource.v1_0;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureService;
import com.liferay.headless.web.experience.dto.v1_0.ContentStructure;
import com.liferay.headless.web.experience.internal.dto.v1_0.util.ContentStructureUtil;
import com.liferay.headless.web.experience.internal.odata.entity.v1_0.ContentStructureEntityModel;
import com.liferay.headless.web.experience.resource.v1_0.ContentStructureResource;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.SearchResultPermissionFilterFactory;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/content-structure.properties",
	scope = ServiceScope.PROTOTYPE,
	service = {ContentStructureResource.class, EntityModelResource.class}
)
public class ContentStructureResourceImpl
	extends BaseContentStructureResourceImpl implements EntityModelResource {

	@Override
	public Page<ContentStructure> getContentSpaceContentStructuresPage(
			Long contentSpaceId, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		List<DDMStructure> ddmStructures = new ArrayList<>();

		Hits hits = SearchUtil.getHits(
			filter, _indexerRegistry.nullSafeGetIndexer(DDMStructure.class),
			pagination,
			booleanQuery -> {
			},
			queryConfig -> {
				queryConfig.setSelectedFieldNames(Field.ENTRY_CLASS_PK);
			},
			searchContext -> {
				searchContext.setAttribute(
					"searchPermissionContext", StringPool.BLANK);
				searchContext.setCompanyId(contextCompany.getCompanyId());
				searchContext.setGroupIds(new long[] {contentSpaceId});
			},
			_searchResultPermissionFilterFactory, sorts);

		for (Document document : hits.getDocs()) {
			DDMStructure ddmStructure = _ddmStructureService.getStructure(
				GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)));

			ddmStructures.add(ddmStructure);
		}

		return Page.of(
			transform(ddmStructures, this::_toContentStructure), pagination,
			ddmStructures.size());
	}

	@Override
	public ContentStructure getContentStructure(Long contentStructureId)
		throws Exception {

		return _toContentStructure(
			_ddmStructureService.getStructure(contentStructureId));
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap) {
		return _entityModel;
	}

	private ContentStructure _toContentStructure(DDMStructure ddmStructure)
		throws Exception {

		return ContentStructureUtil.toContentStructure(
			ddmStructure, contextAcceptLanguage.getPreferredLocale(), _portal,
			_userLocalService);
	}

	private static final EntityModel _entityModel =
		new ContentStructureEntityModel();

	@Reference
	private DDMStructureService _ddmStructureService;

	@Reference
	private IndexerRegistry _indexerRegistry;

	@Reference
	private Portal _portal;

	@Reference
	private SearchResultPermissionFilterFactory
		_searchResultPermissionFilterFactory;

	@Reference
	private UserLocalService _userLocalService;

}