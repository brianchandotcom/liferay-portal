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
import com.liferay.data.engine.rest.dto.v1_0.DataLayoutPage;
import com.liferay.data.engine.rest.dto.v1_0.DataRecord;
import com.liferay.data.engine.rest.dto.v1_0.DataRecordCollection;
import com.liferay.data.engine.rest.resource.v1_0.DataDefinitionResource;
import com.liferay.data.engine.rest.resource.v1_0.DataLayoutResource;
import com.liferay.data.engine.rest.resource.v1_0.DataRecordCollectionResource;
import com.liferay.data.engine.rest.resource.v1_0.DataRecordResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.function.BiFunction;

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
	public DataDefinitionPage getSiteDataDefinitionsPage(
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("keywords") String keywords,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sorts") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_dataDefinitionResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataDefinitionResource -> new DataDefinitionPage(
				dataDefinitionResource.getSiteDataDefinitionsPage(
					siteId, keywords, Pagination.of(page, pageSize),
					_sortsBiFunction.apply(
						dataDefinitionResource, sortsString))));
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
	public DataLayoutPage getDataDefinitionDataLayoutsPage(
			@GraphQLName("dataDefinitionId") Long dataDefinitionId,
			@GraphQLName("keywords") String keywords,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_dataLayoutResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataLayoutResource -> new DataLayoutPage(
				dataLayoutResource.getDataDefinitionDataLayoutsPage(
					dataDefinitionId, keywords,
					Pagination.of(page, pageSize))));
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
	public DataLayoutPage getSiteDataLayoutPage(
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("keywords") String keywords,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_dataLayoutResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataLayoutResource -> new DataLayoutPage(
				dataLayoutResource.getSiteDataLayoutPage(
					siteId, keywords, Pagination.of(page, pageSize))));
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
	public DataRecordPage getDataRecordCollectionDataRecordsPage(
			@GraphQLName("dataRecordCollectionId") Long dataRecordCollectionId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_dataRecordResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataRecordResource -> new DataRecordPage(
				dataRecordResource.getDataRecordCollectionDataRecordsPage(
					dataRecordCollectionId, Pagination.of(page, pageSize))));
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
					dataRecordCollectionId, Pagination.of(page, pageSize)));
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
	public DataRecordCollectionPage getDataDefinitionDataRecordCollectionsPage(
			@GraphQLName("dataDefinitionId") Long dataDefinitionId,
			@GraphQLName("keywords") String keywords,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_dataRecordCollectionResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataRecordCollectionResource -> new DataRecordCollectionPage(
				dataRecordCollectionResource.
					getDataDefinitionDataRecordCollectionsPage(
						dataDefinitionId, keywords,
						Pagination.of(page, pageSize))));
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
	public DataRecordCollectionPage getSiteDataRecordCollectionsPage(
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("keywords") String keywords,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_dataRecordCollectionResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataRecordCollectionResource -> new DataRecordCollectionPage(
				dataRecordCollectionResource.getSiteDataRecordCollectionsPage(
					siteId, keywords, Pagination.of(page, pageSize))));
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

	@GraphQLName("DataDefinitionPage")
	public class DataDefinitionPage {

		public DataDefinitionPage(Page dataDefinitionPage) {
			items = dataDefinitionPage.getItems();
			page = dataDefinitionPage.getPage();
			pageSize = dataDefinitionPage.getPageSize();
			totalCount = dataDefinitionPage.getTotalCount();
		}

		@GraphQLField
		protected java.util.Collection<DataDefinition> items;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("DataLayoutPage")
	public class DataLayoutPage {

		public DataLayoutPage(Page dataLayoutPage) {
			items = dataLayoutPage.getItems();
			page = dataLayoutPage.getPage();
			pageSize = dataLayoutPage.getPageSize();
			totalCount = dataLayoutPage.getTotalCount();
		}

		@GraphQLField
		protected java.util.Collection<DataLayout> items;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("DataRecordPage")
	public class DataRecordPage {

		public DataRecordPage(Page dataRecordPage) {
			items = dataRecordPage.getItems();
			page = dataRecordPage.getPage();
			pageSize = dataRecordPage.getPageSize();
			totalCount = dataRecordPage.getTotalCount();
		}

		@GraphQLField
		protected java.util.Collection<DataRecord> items;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("DataRecordCollectionPage")
	public class DataRecordCollectionPage {

		public DataRecordCollectionPage(Page dataRecordCollectionPage) {
			items = dataRecordCollectionPage.getItems();
			page = dataRecordCollectionPage.getPage();
			pageSize = dataRecordCollectionPage.getPageSize();
			totalCount = dataRecordCollectionPage.getTotalCount();
		}

		@GraphQLField
		protected java.util.Collection<DataRecordCollection> items;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

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
		dataDefinitionResource.setContextUser(_user);
	}

	private void _populateResourceContext(DataLayoutResource dataLayoutResource)
		throws Exception {

		dataLayoutResource.setContextAcceptLanguage(_acceptLanguage);
		dataLayoutResource.setContextCompany(_company);
		dataLayoutResource.setContextUser(_user);
	}

	private void _populateResourceContext(DataRecordResource dataRecordResource)
		throws Exception {

		dataRecordResource.setContextAcceptLanguage(_acceptLanguage);
		dataRecordResource.setContextCompany(_company);
		dataRecordResource.setContextUser(_user);
	}

	private void _populateResourceContext(
			DataRecordCollectionResource dataRecordCollectionResource)
		throws Exception {

		dataRecordCollectionResource.setContextAcceptLanguage(_acceptLanguage);
		dataRecordCollectionResource.setContextCompany(_company);
		dataRecordCollectionResource.setContextUser(_user);
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
	private BiFunction<Object, String, Filter> _filterBiFunction;
	private BiFunction<Object, String, Sort[]> _sortsBiFunction;
	private Company _company;
	private User _user;

}