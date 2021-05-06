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

package com.liferay.object.rest.internal.resource.v1_0;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.internal.dto.converter.ObjectEntryDTOConverter;
import com.liferay.object.rest.internal.odata.entity.ObjectEntryEntityModel;
import com.liferay.object.rest.resource.v1_0.ObjectEntryResource;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.persistence.ObjectDefinitionPersistence;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.aggregation.Aggregation;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/object-entry.properties",
	scope = ServiceScope.PROTOTYPE, service = ObjectEntryResource.class
)
public class ObjectEntryResourceImpl extends BaseObjectEntryResourceImpl {

	@Override
	public void deleteObjectEntry(Long objectEntryId) throws Exception {
		_objectEntryLocalService.deleteObjectEntry(objectEntryId);
	}

	@Override
	public EntityModel getEntityModel(
		Map<String, List<String>> multivaluedMap) {

		if (_entityModel == null) {
			_entityModel = new ObjectEntryEntityModel(
				_objectFieldLocalService.getObjectFields(
					_objectDefinition.getObjectDefinitionId()));
		}

		return _entityModel;
	}

	@Override
	public Page<ObjectEntry> getObjectEntriesPage(
			Boolean flatten, String search, Aggregation aggregation,
			Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception {

		return SearchUtil.search(
			new HashMap<>(),
			booleanQuery -> {
				BooleanFilter booleanFilter =
					booleanQuery.getPreBooleanFilter();

				booleanFilter.add(
					new TermFilter(
						"objectDefinitionId",
						String.valueOf(
							_objectDefinition.getObjectDefinitionId())),
					BooleanClauseOccur.MUST);
			},
			filter, com.liferay.object.model.ObjectEntry.class, search,
			pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> {
				searchContext.addVulcanAggregation(aggregation);
				searchContext.setCompanyId(_contextCompany.getCompanyId());
			},
			sorts,
			document -> _toObjectEntry(
				_objectEntryLocalService.getObjectEntry(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))));
	}

	@Override
	public ObjectEntry getObjectEntry(Long objectEntryId) throws Exception {
		return _toObjectEntry(
			_objectEntryLocalService.getObjectEntry(objectEntryId));
	}

	@Override
	public ObjectEntry postObjectEntrySite(Long siteId, ObjectEntry objectEntry)
		throws Exception {

		return _toObjectEntry(
			_objectEntryLocalService.addObjectEntry(
				_contextUser.getUserId(), siteId,
				_objectDefinition.getObjectDefinitionId(),
				(Map)objectEntry.getProperties(), new ServiceContext()));
	}

	@Override
	public ObjectEntry putObjectEntry(
			Long objectEntryId, ObjectEntry objectEntry)
		throws Exception {

		return _toObjectEntry(
			_objectEntryLocalService.updateObjectEntry(
				_contextUser.getUserId(), objectEntryId,
				(Map)objectEntry.getProperties(), new ServiceContext()));
	}

	@Override
	public void setContextCompany(Company contextCompany) {
		_contextCompany = contextCompany;
	}

	public void setContextUser(User contextUser) {
		_contextUser = contextUser;
	}

	@Override
	public void setLanguageId(String languageId) {
		_contextAcceptLanguage = new AcceptLanguage() {

			@Override
			public List<Locale> getLocales() {
				return null;
			}

			@Override
			public String getPreferredLanguageId() {
				return languageId;
			}

			@Override
			public Locale getPreferredLocale() {
				return LocaleUtil.fromLanguageId(languageId);
			}

		};
	}

	private ObjectEntry _toObjectEntry(
		com.liferay.object.model.ObjectEntry objectEntry) {

		return _objectEntryDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				_contextAcceptLanguage.isAcceptAllLanguages(),
				Collections.emptyMap(), null, _contextHttpServletRequest,
				objectEntry.getObjectEntryId(),
				_contextAcceptLanguage.getPreferredLocale(), _contextUriInfo,
				_contextUser),
			objectEntry);
	}

	@Context
	private AcceptLanguage _contextAcceptLanguage;

	@Context
	private Company _contextCompany;

	@Context
	private HttpServletRequest _contextHttpServletRequest;

	@Context
	private UriInfo _contextUriInfo;

	@Context
	private User _contextUser;

	private ObjectEntryEntityModel _entityModel;

	@Context
	private ObjectDefinition _objectDefinition;

	@Reference
	private ObjectDefinitionPersistence _objectDefinitionPersistence;

	@Reference
	private ObjectEntryDTOConverter _objectEntryDTOConverter;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	// Should be the remote service to check permissions

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

}