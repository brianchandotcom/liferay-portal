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

package com.liferay.change.tracking.rest.internal.resource.v1_0;

import com.liferay.change.tracking.definition.CTDefinitionRegistryUtil;
import com.liferay.change.tracking.engine.CTEngineManager;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.rest.dto.v1_0.Entry;
import com.liferay.change.tracking.rest.resource.v1_0.EntryResource;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.liferay.portal.vulcan.util.TransformUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Máté Thurzó
 * @author Zoltan Csaszi
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/entry.properties",
	scope = ServiceScope.PROTOTYPE, service = EntryResource.class
)
public class EntryResourceImpl extends BaseEntryResourceImpl {

	@Override
	public Page<Entry> getCollectionEntriesPage(
			Long collectionId, String changeTypesFilter,
			String classNameIdsFilter, Boolean collision, String groupIdsFilter,
			Integer status, String userIdsFilter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		CTCollection ctCollection = _ctCollectionLocalService.fetchCTCollection(
			GetterUtil.getLong(collectionId));

		if (ctCollection == null) {
			throw new IllegalArgumentException(
				"Unable to get change tacking collection " + collectionId);
		}

		collision = GetterUtil.getBoolean(collision);

		QueryDefinition<CTEntry> queryDefinition =
			SearchUtil.getQueryDefinition(CTEntry.class, pagination, sorts);

		queryDefinition.setStatus(status);

		return Page.of(
			TransformUtil.transform(
				_ctEngineManager.getCTEntries(
					ctCollection,
					GetterUtil.getLongValues(_getFiltersArray(groupIdsFilter)),
					GetterUtil.getLongValues(_getFiltersArray(userIdsFilter)),
					GetterUtil.getLongValues(_getFiltersArray(classNameIdsFilter)),
					GetterUtil.getIntegerValues(_getFiltersArray(changeTypesFilter)),
					collision, queryDefinition),
				this::_toEntry),
			pagination,
			_ctEngineManager.getCTEntriesCount(
				ctCollection,
				GetterUtil.getLongValues(_getFiltersArray(groupIdsFilter)),
				GetterUtil.getLongValues(_getFiltersArray(userIdsFilter)),
				GetterUtil.getLongValues(_getFiltersArray(classNameIdsFilter)),
				GetterUtil.getIntegerValues(_getFiltersArray(changeTypesFilter)),
				collision, queryDefinition));
	}

	@Override
	public Entry getEntry(Long entryId) throws Exception {
		return _toEntry(_ctEntryLocalService.getCTEntry(entryId));
	}

	private List<Entry> _getEntries(List<CTEntry> ctEntries) {
		Stream<CTEntry> stream = ctEntries.stream();

		return stream.map(
			this::_toEntry
		).collect(
			Collectors.toList()
		);
	}

	private String[] _getFiltersArray(String filterString) {
		if (Validator.isNull(filterString)) {
			return new String[0];
		}

		filterString = filterString.trim();

		return filterString.split(StringPool.COMMA);
	}

	private Entry _toEntry(CTEntry ctEntry) {
		if (ctEntry == null) {
			return null;
		}

		return new Entry() {
			{
				affectedByEntriesCount =
					_ctEntryLocalService.getRelatedOwnerCTEntriesCount(
						ctEntry.getCtEntryId(), new QueryDefinition<>());
				changeType = ctEntry.getChangeType();
				classNameId = ctEntry.getModelClassNameId();
				classPK = ctEntry.getModelClassPK();
				collision = ctEntry.isCollision();
				contentType =
					CTDefinitionRegistryUtil.
						getVersionEntityContentTypeLanguageKey(
							ctEntry.getModelClassNameId());
				dateModified = ctEntry.getModifiedDate();
				entryId = ctEntry.getCtEntryId();
				key = ctEntry.getModelResourcePrimKey();
				siteName = CTDefinitionRegistryUtil.getVersionEntitySiteName(
					ctEntry.getModelClassNameId(), ctEntry.getModelClassPK());
				title = CTDefinitionRegistryUtil.getVersionEntityTitle(
					ctEntry.getModelClassNameId(), ctEntry.getModelClassPK());
				userName = ctEntry.getUserName();
				version = String.valueOf(
					CTDefinitionRegistryUtil.getVersionEntityVersion(
						ctEntry.getModelClassNameId(),
						ctEntry.getModelClassPK()));
			}
		};
	}

	@Reference
	private CTCollectionLocalService _ctCollectionLocalService;

	@Reference
	private CTEngineManager _ctEngineManager;

	@Reference
	private CTEntryLocalService _ctEntryLocalService;

}