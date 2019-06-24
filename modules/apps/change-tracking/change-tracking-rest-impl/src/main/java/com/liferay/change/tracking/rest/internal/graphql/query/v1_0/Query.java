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

package com.liferay.change.tracking.rest.internal.graphql.query.v1_0;

import com.liferay.change.tracking.rest.dto.v1_0.AffectedEntry;
import com.liferay.change.tracking.rest.dto.v1_0.Collection;
import com.liferay.change.tracking.rest.dto.v1_0.Entry;
import com.liferay.change.tracking.rest.dto.v1_0.Process;
import com.liferay.change.tracking.rest.dto.v1_0.ProcessUser;
import com.liferay.change.tracking.rest.dto.v1_0.Settings;
import com.liferay.change.tracking.rest.resource.v1_0.AffectedEntryResource;
import com.liferay.change.tracking.rest.resource.v1_0.CollectionResource;
import com.liferay.change.tracking.rest.resource.v1_0.EntryResource;
import com.liferay.change.tracking.rest.resource.v1_0.ProcessResource;
import com.liferay.change.tracking.rest.resource.v1_0.ProcessUserResource;
import com.liferay.change.tracking.rest.resource.v1_0.SettingsResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import javax.annotation.Generated;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author Máté Thurzó
 * @generated
 */
@Generated("")
public class Query {

	public static void setAffectedEntryResourceComponentServiceObjects(
		ComponentServiceObjects<AffectedEntryResource>
			affectedEntryResourceComponentServiceObjects) {

		_affectedEntryResourceComponentServiceObjects =
			affectedEntryResourceComponentServiceObjects;
	}

	public static void setCollectionResourceComponentServiceObjects(
		ComponentServiceObjects<CollectionResource>
			collectionResourceComponentServiceObjects) {

		_collectionResourceComponentServiceObjects =
			collectionResourceComponentServiceObjects;
	}

	public static void setEntryResourceComponentServiceObjects(
		ComponentServiceObjects<EntryResource>
			entryResourceComponentServiceObjects) {

		_entryResourceComponentServiceObjects =
			entryResourceComponentServiceObjects;
	}

	public static void setProcessResourceComponentServiceObjects(
		ComponentServiceObjects<ProcessResource>
			processResourceComponentServiceObjects) {

		_processResourceComponentServiceObjects =
			processResourceComponentServiceObjects;
	}

	public static void setProcessUserResourceComponentServiceObjects(
		ComponentServiceObjects<ProcessUserResource>
			processUserResourceComponentServiceObjects) {

		_processUserResourceComponentServiceObjects =
			processUserResourceComponentServiceObjects;
	}

	public static void setSettingsResourceComponentServiceObjects(
		ComponentServiceObjects<SettingsResource>
			settingsResourceComponentServiceObjects) {

		_settingsResourceComponentServiceObjects =
			settingsResourceComponentServiceObjects;
	}

	@GraphQLField
	public java.util.Collection<AffectedEntry> getEntryAffectedEntriesPage(
			@GraphQLName("entryId") Long entryId,
			@GraphQLName("keywords") String keywords,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_affectedEntryResourceComponentServiceObjects,
			this::_populateResourceContext,
			affectedEntryResource -> {
				Page paginationPage =
					affectedEntryResource.getEntryAffectedEntriesPage(
						entryId, keywords, Pagination.of(pageSize, page));

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	public java.util.Collection<Collection> getCollectionsPage(
			@GraphQLName("companyId") Long companyId,
			@GraphQLName("type")
				com.liferay.change.tracking.rest.constant.v1_0.CollectionType
					type,
			@GraphQLName("userId") Long userId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("sorts") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_collectionResourceComponentServiceObjects,
			this::_populateResourceContext,
			collectionResource -> {
				Page paginationPage = collectionResource.getCollectionsPage(
					companyId, type, userId, Pagination.of(pageSize, page),
					sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	public Collection getCollection(
			@GraphQLName("companyId") Long companyId,
			@GraphQLName("collectionId") Long collectionId)
		throws Exception {

		return _applyComponentServiceObjects(
			_collectionResourceComponentServiceObjects,
			this::_populateResourceContext,
			collectionResource -> collectionResource.getCollection(
				companyId, collectionId));
	}

	@GraphQLField
	public java.util.Collection<Entry> getCollectionEntriesPage(
			@GraphQLName("collectionId") Long collectionId,
			@GraphQLName("changeTypesFilter") String changeTypesFilter,
			@GraphQLName("classNameIdsFilter") String classNameIdsFilter,
			@GraphQLName("collision") Boolean collision,
			@GraphQLName("groupIdsFilter") String groupIdsFilter,
			@GraphQLName("status") Integer status,
			@GraphQLName("userIdsFilter") String userIdsFilter,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("sorts") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_entryResourceComponentServiceObjects,
			this::_populateResourceContext,
			entryResource -> {
				Page paginationPage = entryResource.getCollectionEntriesPage(
					collectionId, changeTypesFilter, classNameIdsFilter,
					collision, groupIdsFilter, status, userIdsFilter,
					Pagination.of(pageSize, page), sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	public Entry getEntry(@GraphQLName("entryId") Long entryId)
		throws Exception {

		return _applyComponentServiceObjects(
			_entryResourceComponentServiceObjects,
			this::_populateResourceContext,
			entryResource -> entryResource.getEntry(entryId));
	}

	@GraphQLField
	public java.util.Collection<Process> getProcessesPage(
			@GraphQLName("companyId") Long companyId,
			@GraphQLName("keywords") String keywords,
			@GraphQLName("type")
				com.liferay.change.tracking.rest.constant.v1_0.CollectionType
					type,
			@GraphQLName("userId") Long userId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("sorts") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_processResourceComponentServiceObjects,
			this::_populateResourceContext,
			processResource -> {
				Page paginationPage = processResource.getProcessesPage(
					companyId, keywords, type, userId,
					Pagination.of(pageSize, page), sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	public Process getProcess(@GraphQLName("processId") Long processId)
		throws Exception {

		return _applyComponentServiceObjects(
			_processResourceComponentServiceObjects,
			this::_populateResourceContext,
			processResource -> processResource.getProcess(processId));
	}

	@GraphQLField
	public java.util.Collection<ProcessUser> getProcessUsersPage(
			@GraphQLName("companyId") Long companyId,
			@GraphQLName("keywords") String keywords,
			@GraphQLName("processType")
				com.liferay.change.tracking.rest.constant.v1_0.ProcessType
					processType,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_processUserResourceComponentServiceObjects,
			this::_populateResourceContext,
			processUserResource -> {
				Page paginationPage = processUserResource.getProcessUsersPage(
					companyId, keywords, processType,
					Pagination.of(pageSize, page));

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	public java.util.Collection<Settings> getSettingsPage(
			@GraphQLName("companyId") Long companyId,
			@GraphQLName("userId") Long userId)
		throws Exception {

		return _applyComponentServiceObjects(
			_settingsResourceComponentServiceObjects,
			this::_populateResourceContext,
			settingsResource -> {
				Page paginationPage = settingsResource.getSettingsPage(
					companyId, userId);

				return paginationPage.getItems();
			});
	}

	private <T, R, E1 extends Throwable, E2 extends Throwable> R
			_applyComponentServiceObjects(
				ComponentServiceObjects<T> componentServiceObjects,
				UnsafeConsumer<T, E1> unsafeConsumer,
				UnsafeFunction<T, R, E2> unsafeFunction)
		throws E1, E2 {

		T resource = componentServiceObjects.getService();

		try {
			unsafeConsumer.accept(resource);

			return unsafeFunction.apply(resource);
		}
		finally {
			componentServiceObjects.ungetService(resource);
		}
	}

	private void _populateResourceContext(
			AffectedEntryResource affectedEntryResource)
		throws Exception {

		affectedEntryResource.setContextAcceptLanguage(_acceptLanguage);
		affectedEntryResource.setContextCompany(_company);
	}

	private void _populateResourceContext(CollectionResource collectionResource)
		throws Exception {

		collectionResource.setContextAcceptLanguage(_acceptLanguage);
		collectionResource.setContextCompany(_company);
	}

	private void _populateResourceContext(EntryResource entryResource)
		throws Exception {

		entryResource.setContextAcceptLanguage(_acceptLanguage);
		entryResource.setContextCompany(_company);
	}

	private void _populateResourceContext(ProcessResource processResource)
		throws Exception {

		processResource.setContextAcceptLanguage(_acceptLanguage);
		processResource.setContextCompany(_company);
	}

	private void _populateResourceContext(
			ProcessUserResource processUserResource)
		throws Exception {

		processUserResource.setContextAcceptLanguage(_acceptLanguage);
		processUserResource.setContextCompany(_company);
	}

	private void _populateResourceContext(SettingsResource settingsResource)
		throws Exception {

		settingsResource.setContextAcceptLanguage(_acceptLanguage);
		settingsResource.setContextCompany(_company);
	}

	private static ComponentServiceObjects<AffectedEntryResource>
		_affectedEntryResourceComponentServiceObjects;
	private static ComponentServiceObjects<CollectionResource>
		_collectionResourceComponentServiceObjects;
	private static ComponentServiceObjects<EntryResource>
		_entryResourceComponentServiceObjects;
	private static ComponentServiceObjects<ProcessResource>
		_processResourceComponentServiceObjects;
	private static ComponentServiceObjects<ProcessUserResource>
		_processUserResourceComponentServiceObjects;
	private static ComponentServiceObjects<SettingsResource>
		_settingsResourceComponentServiceObjects;

	private AcceptLanguage _acceptLanguage;
	private Company _company;

}