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

package com.liferay.data.engine.rest.internal.graphql.query.v1_0;

import com.liferay.data.engine.rest.dto.v1_0.DataDefinition;
import com.liferay.data.engine.rest.dto.v1_0.DataLayout;
import com.liferay.data.engine.rest.dto.v1_0.DataRecord;
import com.liferay.data.engine.rest.dto.v1_0.DataRecordCollection;
import com.liferay.data.engine.rest.resource.v1_0.DataDefinitionResource;
import com.liferay.data.engine.rest.resource.v1_0.DataLayoutResource;
import com.liferay.data.engine.rest.resource.v1_0.DataRecordCollectionResource;
import com.liferay.data.engine.rest.resource.v1_0.DataRecordResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import javax.annotation.Generated;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author Jeyvison Nascimento
 * @generated
 */
@Generated("")
public class Query {

	public static void setDataDefinitionResourceComponentServiceObjects(
		ComponentServiceObjects<DataDefinitionResource>
			dataDefinitionResourceComponentServiceObjects) {

		_dataDefinitionResourceComponentServiceObjects =
			dataDefinitionResourceComponentServiceObjects;
	}

	public static void setDataLayoutResourceComponentServiceObjects(
		ComponentServiceObjects<DataLayoutResource>
			dataLayoutResourceComponentServiceObjects) {

		_dataLayoutResourceComponentServiceObjects =
			dataLayoutResourceComponentServiceObjects;
	}

	public static void setDataRecordResourceComponentServiceObjects(
		ComponentServiceObjects<DataRecordResource>
			dataRecordResourceComponentServiceObjects) {

		_dataRecordResourceComponentServiceObjects =
			dataRecordResourceComponentServiceObjects;
	}

	public static void setDataRecordCollectionResourceComponentServiceObjects(
		ComponentServiceObjects<DataRecordCollectionResource>
			dataRecordCollectionResourceComponentServiceObjects) {

		_dataRecordCollectionResourceComponentServiceObjects =
			dataRecordCollectionResourceComponentServiceObjects;
	}

	@GraphQLField
	public DataDefinition getDataDefinition(
			@GraphQLName("dataDefinitionId") Long dataDefinitionId)
		throws Exception {

		return _applyComponentServiceObjects(
			_dataDefinitionResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataDefinitionResource -> dataDefinitionResource.getDataDefinition(
				dataDefinitionId));
	}

	@GraphQLField
	public java.util.Collection<DataDefinition> getSiteDataDefinitionsPage(
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("keywords") String keywords,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_dataDefinitionResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataDefinitionResource -> {
				Page paginationPage =
					dataDefinitionResource.getSiteDataDefinitionsPage(
						siteId, keywords, Pagination.of(pageSize, page));

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	public DataDefinition getSiteDataDefinition(
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("dataDefinitionKey") String dataDefinitionKey)
		throws Exception {

		return _applyComponentServiceObjects(
			_dataDefinitionResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataDefinitionResource ->
				dataDefinitionResource.getSiteDataDefinition(
					siteId, dataDefinitionKey));
	}

	@GraphQLField
	public java.util.Collection<DataLayout> getDataDefinitionDataLayoutsPage(
			@GraphQLName("dataDefinitionId") Long dataDefinitionId,
			@GraphQLName("keywords") String keywords,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_dataLayoutResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataLayoutResource -> {
				Page paginationPage =
					dataLayoutResource.getDataDefinitionDataLayoutsPage(
						dataDefinitionId, keywords,
						Pagination.of(pageSize, page));

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	public DataLayout getDataLayout(
			@GraphQLName("dataLayoutId") Long dataLayoutId)
		throws Exception {

		return _applyComponentServiceObjects(
			_dataLayoutResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataLayoutResource -> dataLayoutResource.getDataLayout(
				dataLayoutId));
	}

	@GraphQLField
	public java.util.Collection<DataLayout> getSiteDataLayoutPage(
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("keywords") String keywords,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_dataLayoutResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataLayoutResource -> {
				Page paginationPage = dataLayoutResource.getSiteDataLayoutPage(
					siteId, keywords, Pagination.of(pageSize, page));

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	public DataLayout getSiteDataLayout(
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("dataLayoutKey") String dataLayoutKey)
		throws Exception {

		return _applyComponentServiceObjects(
			_dataLayoutResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataLayoutResource -> dataLayoutResource.getSiteDataLayout(
				siteId, dataLayoutKey));
	}

	@GraphQLField
	public java.util.Collection<DataRecord>
			getDataRecordCollectionDataRecordsPage(
				@GraphQLName("dataRecordCollectionId") Long
					dataRecordCollectionId,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_dataRecordResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataRecordResource -> {
				Page paginationPage =
					dataRecordResource.getDataRecordCollectionDataRecordsPage(
						dataRecordCollectionId, Pagination.of(pageSize, page));

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	public String getDataRecordCollectionDataRecordExport(
			@GraphQLName("dataRecordCollectionId") Long dataRecordCollectionId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_dataRecordResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataRecordResource ->
				dataRecordResource.getDataRecordCollectionDataRecordExport(
					dataRecordCollectionId, Pagination.of(pageSize, page)));
	}

	@GraphQLField
	public DataRecord getDataRecord(
			@GraphQLName("dataRecordId") Long dataRecordId)
		throws Exception {

		return _applyComponentServiceObjects(
			_dataRecordResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataRecordResource -> dataRecordResource.getDataRecord(
				dataRecordId));
	}

	@GraphQLField
	public java.util.Collection<DataRecordCollection>
			getDataDefinitionDataRecordCollectionsPage(
				@GraphQLName("dataDefinitionId") Long dataDefinitionId,
				@GraphQLName("keywords") String keywords,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_dataRecordCollectionResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataRecordCollectionResource -> {
				Page paginationPage =
					dataRecordCollectionResource.
						getDataDefinitionDataRecordCollectionsPage(
							dataDefinitionId, keywords,
							Pagination.of(pageSize, page));

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	public DataRecordCollection getDataRecordCollection(
			@GraphQLName("dataRecordCollectionId") Long dataRecordCollectionId)
		throws Exception {

		return _applyComponentServiceObjects(
			_dataRecordCollectionResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataRecordCollectionResource ->
				dataRecordCollectionResource.getDataRecordCollection(
					dataRecordCollectionId));
	}

	@GraphQLField
	public java.util.Collection<DataRecordCollection>
			getSiteDataRecordCollectionsPage(
				@GraphQLName("siteId") Long siteId,
				@GraphQLName("keywords") String keywords,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_dataRecordCollectionResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataRecordCollectionResource -> {
				Page paginationPage =
					dataRecordCollectionResource.
						getSiteDataRecordCollectionsPage(
							siteId, keywords, Pagination.of(pageSize, page));

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	public DataRecordCollection getSiteDataRecordCollection(
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("dataRecordCollectionKey") String
				dataRecordCollectionKey)
		throws Exception {

		return _applyComponentServiceObjects(
			_dataRecordCollectionResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataRecordCollectionResource ->
				dataRecordCollectionResource.getSiteDataRecordCollection(
					siteId, dataRecordCollectionKey));
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
			DataDefinitionResource dataDefinitionResource)
		throws Exception {

		dataDefinitionResource.setContextAcceptLanguage(_acceptLanguage);
		dataDefinitionResource.setContextCompany(_company);
	}

	private void _populateResourceContext(DataLayoutResource dataLayoutResource)
		throws Exception {

		dataLayoutResource.setContextAcceptLanguage(_acceptLanguage);
		dataLayoutResource.setContextCompany(_company);
	}

	private void _populateResourceContext(DataRecordResource dataRecordResource)
		throws Exception {

		dataRecordResource.setContextAcceptLanguage(_acceptLanguage);
		dataRecordResource.setContextCompany(_company);
	}

	private void _populateResourceContext(
			DataRecordCollectionResource dataRecordCollectionResource)
		throws Exception {

		dataRecordCollectionResource.setContextAcceptLanguage(_acceptLanguage);
		dataRecordCollectionResource.setContextCompany(_company);
	}

	private static ComponentServiceObjects<DataDefinitionResource>
		_dataDefinitionResourceComponentServiceObjects;
	private static ComponentServiceObjects<DataLayoutResource>
		_dataLayoutResourceComponentServiceObjects;
	private static ComponentServiceObjects<DataRecordResource>
		_dataRecordResourceComponentServiceObjects;
	private static ComponentServiceObjects<DataRecordCollectionResource>
		_dataRecordCollectionResourceComponentServiceObjects;

	private AcceptLanguage _acceptLanguage;
	private Company _company;

}