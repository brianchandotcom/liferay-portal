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
import com.liferay.data.engine.rest.dto.v1_0.DataListView;
import com.liferay.data.engine.rest.dto.v1_0.DataRecord;
import com.liferay.data.engine.rest.dto.v1_0.DataRecordCollection;
import com.liferay.data.engine.rest.resource.v1_0.DataDefinitionResource;
import com.liferay.data.engine.rest.resource.v1_0.DataLayoutResource;
import com.liferay.data.engine.rest.resource.v1_0.DataListViewResource;
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
import com.liferay.portal.vulcan.graphql.annotation.GraphQLTypeExtension;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.function.BiFunction;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

	public static void setDataListViewResourceComponentServiceObjects(
		ComponentServiceObjects<DataListViewResource>
			dataListViewResourceComponentServiceObjects) {

		_dataListViewResourceComponentServiceObjects =
			dataListViewResourceComponentServiceObjects;
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
	public DataDefinition dataDefinition(
			@GraphQLName("dataDefinitionId") Long dataDefinitionId)
		throws Exception {

		return _applyComponentServiceObjects(
			_dataDefinitionResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataDefinitionResource -> dataDefinitionResource.getDataDefinition(
				dataDefinitionId));
	}

	@GraphQLField
	public DataDefinitionPage dataDefinitions(
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("keywords") String keywords,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
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
	public DataDefinition dataDefinition(
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
	public DataLayoutPage dataDefinitionDataLayouts(
			@GraphQLName("dataDefinitionId") Long dataDefinitionId,
			@GraphQLName("keywords") String keywords,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_dataLayoutResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataLayoutResource -> new DataLayoutPage(
				dataLayoutResource.getDataDefinitionDataLayoutsPage(
					dataDefinitionId, keywords, Pagination.of(page, pageSize),
					_sortsBiFunction.apply(dataLayoutResource, sortsString))));
	}

	@GraphQLField
	public DataLayout dataLayout(@GraphQLName("dataLayoutId") Long dataLayoutId)
		throws Exception {

		return _applyComponentServiceObjects(
			_dataLayoutResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataLayoutResource -> dataLayoutResource.getDataLayout(
				dataLayoutId));
	}

	@GraphQLField
	public DataLayoutPage dataLayout(
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("keywords") String keywords,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_dataLayoutResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataLayoutResource -> new DataLayoutPage(
				dataLayoutResource.getSiteDataLayoutPage(
					siteId, keywords, Pagination.of(page, pageSize),
					_sortsBiFunction.apply(dataLayoutResource, sortsString))));
	}

	@GraphQLField
	public DataLayout dataLayout(
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
	public DataListViewPage dataDefinitionDataListViews(
			@GraphQLName("dataDefinitionId") Long dataDefinitionId,
			@GraphQLName("keywords") String keywords,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_dataListViewResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataListViewResource -> new DataListViewPage(
				dataListViewResource.getDataDefinitionDataListViewsPage(
					dataDefinitionId, keywords, Pagination.of(page, pageSize),
					_sortsBiFunction.apply(
						dataListViewResource, sortsString))));
	}

	@GraphQLField
	public DataRecordPage dataDefinitionDataRecords(
			@GraphQLName("dataDefinitionId") Long dataDefinitionId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_dataRecordResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataRecordResource -> new DataRecordPage(
				dataRecordResource.getDataDefinitionDataRecordsPage(
					dataDefinitionId, Pagination.of(page, pageSize))));
	}

	@GraphQLField
	public DataRecordPage dataRecordCollectionDataRecords(
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
	public String dataRecordCollectionDataRecordExport(
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
	public DataRecord dataRecord(@GraphQLName("dataRecordId") Long dataRecordId)
		throws Exception {

		return _applyComponentServiceObjects(
			_dataRecordResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataRecordResource -> dataRecordResource.getDataRecord(
				dataRecordId));
	}

	@GraphQLField
	public DataRecordCollectionPage dataDefinitionDataRecordCollections(
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
	public DataRecordCollection dataRecordCollection(
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
	public DataRecordCollectionPage dataRecordCollections(
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
	public DataRecordCollection dataRecordCollection(
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

	@GraphQLTypeExtension(DataRecordCollection.class)
	public class GetDataDefinitionTypeExtension {

		public GetDataDefinitionTypeExtension(
			DataRecordCollection dataRecordCollection) {

			_dataRecordCollection = dataRecordCollection;
		}

		@GraphQLField
		public DataDefinition dataDefinition() throws Exception {
			return _applyComponentServiceObjects(
				_dataDefinitionResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				dataDefinitionResource ->
					dataDefinitionResource.getDataDefinition(
						_dataRecordCollection.getDataDefinitionId()));
		}

		private DataRecordCollection _dataRecordCollection;

	}

	@GraphQLTypeExtension(DataDefinition.class)
	public class GetDataDefinitionDataListViewsPageTypeExtension {

		public GetDataDefinitionDataListViewsPageTypeExtension(
			DataDefinition dataDefinition) {

			_dataDefinition = dataDefinition;
		}

		@GraphQLField
		public DataListViewPage dataListViews(
				@GraphQLName("keywords") String keywords,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page,
				@GraphQLName("sort") String sortsString)
			throws Exception {

			return _applyComponentServiceObjects(
				_dataListViewResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				dataListViewResource -> new DataListViewPage(
					dataListViewResource.getDataDefinitionDataListViewsPage(
						_dataDefinition.getId(), keywords,
						Pagination.of(page, pageSize),
						_sortsBiFunction.apply(
							dataListViewResource, sortsString))));
		}

		private DataDefinition _dataDefinition;

	}

	@GraphQLTypeExtension(DataDefinition.class)
	public class GetDataDefinitionDataRecordsPageTypeExtension {

		public GetDataDefinitionDataRecordsPageTypeExtension(
			DataDefinition dataDefinition) {

			_dataDefinition = dataDefinition;
		}

		@GraphQLField
		public DataRecordPage dataRecords(
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page)
			throws Exception {

			return _applyComponentServiceObjects(
				_dataRecordResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				dataRecordResource -> new DataRecordPage(
					dataRecordResource.getDataDefinitionDataRecordsPage(
						_dataDefinition.getId(),
						Pagination.of(page, pageSize))));
		}

		private DataDefinition _dataDefinition;

	}

	@GraphQLTypeExtension(DataDefinition.class)
	public class GetDataDefinitionDataRecordCollectionsPageTypeExtension {

		public GetDataDefinitionDataRecordCollectionsPageTypeExtension(
			DataDefinition dataDefinition) {

			_dataDefinition = dataDefinition;
		}

		@GraphQLField
		public DataRecordCollectionPage dataRecordCollections(
				@GraphQLName("keywords") String keywords,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page)
			throws Exception {

			return _applyComponentServiceObjects(
				_dataRecordCollectionResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				dataRecordCollectionResource -> new DataRecordCollectionPage(
					dataRecordCollectionResource.
						getDataDefinitionDataRecordCollectionsPage(
							_dataDefinition.getId(), keywords,
							Pagination.of(page, pageSize))));
		}

		private DataDefinition _dataDefinition;

	}

	@GraphQLTypeExtension(DataDefinition.class)
	public class GetDataDefinitionDataLayoutsPageTypeExtension {

		public GetDataDefinitionDataLayoutsPageTypeExtension(
			DataDefinition dataDefinition) {

			_dataDefinition = dataDefinition;
		}

		@GraphQLField
		public DataLayoutPage dataLayouts(
				@GraphQLName("keywords") String keywords,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page,
				@GraphQLName("sort") String sortsString)
			throws Exception {

			return _applyComponentServiceObjects(
				_dataLayoutResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				dataLayoutResource -> new DataLayoutPage(
					dataLayoutResource.getDataDefinitionDataLayoutsPage(
						_dataDefinition.getId(), keywords,
						Pagination.of(page, pageSize),
						_sortsBiFunction.apply(
							dataLayoutResource, sortsString))));
		}

		private DataDefinition _dataDefinition;

	}

	@GraphQLTypeExtension(DataRecordCollection.class)
	public class GetDataRecordCollectionDataRecordsPageTypeExtension {

		public GetDataRecordCollectionDataRecordsPageTypeExtension(
			DataRecordCollection dataRecordCollection) {

			_dataRecordCollection = dataRecordCollection;
		}

		@GraphQLField
		public DataRecordPage dataRecords(
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page)
			throws Exception {

			return _applyComponentServiceObjects(
				_dataRecordResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				dataRecordResource -> new DataRecordPage(
					dataRecordResource.getDataRecordCollectionDataRecordsPage(
						_dataRecordCollection.getId(),
						Pagination.of(page, pageSize))));
		}

		private DataRecordCollection _dataRecordCollection;

	}

	@GraphQLTypeExtension(DataRecordCollection.class)
	public class GetDataRecordCollectionDataRecordExportTypeExtension {

		public GetDataRecordCollectionDataRecordExportTypeExtension(
			DataRecordCollection dataRecordCollection) {

			_dataRecordCollection = dataRecordCollection;
		}

		@GraphQLField
		public String dataRecordExport(
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page)
			throws Exception {

			return _applyComponentServiceObjects(
				_dataRecordResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				dataRecordResource ->
					dataRecordResource.getDataRecordCollectionDataRecordExport(
						_dataRecordCollection.getId(),
						Pagination.of(page, pageSize)));
		}

		private DataRecordCollection _dataRecordCollection;

	}

	@GraphQLTypeExtension(DataRecord.class)
	public class GetDataRecordCollectionTypeExtension {

		public GetDataRecordCollectionTypeExtension(DataRecord dataRecord) {
			_dataRecord = dataRecord;
		}

		@GraphQLField
		public DataRecordCollection collection() throws Exception {
			return _applyComponentServiceObjects(
				_dataRecordCollectionResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				dataRecordCollectionResource ->
					dataRecordCollectionResource.getDataRecordCollection(
						_dataRecord.getId()));
		}

		private DataRecord _dataRecord;

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

	@GraphQLName("DataListViewPage")
	public class DataListViewPage {

		public DataListViewPage(Page dataListViewPage) {
			items = dataListViewPage.getItems();
			page = dataListViewPage.getPage();
			pageSize = dataListViewPage.getPageSize();
			totalCount = dataListViewPage.getTotalCount();
		}

		@GraphQLField
		protected java.util.Collection<DataListView> items;

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
		dataDefinitionResource.setContextHttpServletRequest(
			_httpServletRequest);
		dataDefinitionResource.setContextHttpServletResponse(
			_httpServletResponse);
		dataDefinitionResource.setContextUser(_user);
	}

	private void _populateResourceContext(DataLayoutResource dataLayoutResource)
		throws Exception {

		dataLayoutResource.setContextAcceptLanguage(_acceptLanguage);
		dataLayoutResource.setContextCompany(_company);
		dataLayoutResource.setContextHttpServletRequest(_httpServletRequest);
		dataLayoutResource.setContextHttpServletResponse(_httpServletResponse);
		dataLayoutResource.setContextUser(_user);
	}

	private void _populateResourceContext(
			DataListViewResource dataListViewResource)
		throws Exception {

		dataListViewResource.setContextAcceptLanguage(_acceptLanguage);
		dataListViewResource.setContextCompany(_company);
		dataListViewResource.setContextHttpServletRequest(_httpServletRequest);
		dataListViewResource.setContextHttpServletResponse(
			_httpServletResponse);
		dataListViewResource.setContextUser(_user);
	}

	private void _populateResourceContext(DataRecordResource dataRecordResource)
		throws Exception {

		dataRecordResource.setContextAcceptLanguage(_acceptLanguage);
		dataRecordResource.setContextCompany(_company);
		dataRecordResource.setContextHttpServletRequest(_httpServletRequest);
		dataRecordResource.setContextHttpServletResponse(_httpServletResponse);
		dataRecordResource.setContextUser(_user);
	}

	private void _populateResourceContext(
			DataRecordCollectionResource dataRecordCollectionResource)
		throws Exception {

		dataRecordCollectionResource.setContextAcceptLanguage(_acceptLanguage);
		dataRecordCollectionResource.setContextCompany(_company);
		dataRecordCollectionResource.setContextHttpServletRequest(
			_httpServletRequest);
		dataRecordCollectionResource.setContextHttpServletResponse(
			_httpServletResponse);
		dataRecordCollectionResource.setContextUser(_user);
	}

	private static ComponentServiceObjects<DataDefinitionResource>
		_dataDefinitionResourceComponentServiceObjects;
	private static ComponentServiceObjects<DataLayoutResource>
		_dataLayoutResourceComponentServiceObjects;
	private static ComponentServiceObjects<DataListViewResource>
		_dataListViewResourceComponentServiceObjects;
	private static ComponentServiceObjects<DataRecordResource>
		_dataRecordResourceComponentServiceObjects;
	private static ComponentServiceObjects<DataRecordCollectionResource>
		_dataRecordCollectionResourceComponentServiceObjects;

	private AcceptLanguage _acceptLanguage;
	private BiFunction<Object, String, Filter> _filterBiFunction;
	private BiFunction<Object, String, Sort[]> _sortsBiFunction;
	private Company _company;
	private HttpServletRequest _httpServletRequest;
	private HttpServletResponse _httpServletResponse;
	private User _user;

}