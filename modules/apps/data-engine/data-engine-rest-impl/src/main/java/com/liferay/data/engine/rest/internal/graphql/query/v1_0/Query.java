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
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLInvokeDetached;
import graphql.annotations.annotationTypes.GraphQLName;

import java.util.Collection;

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
	@GraphQLInvokeDetached
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
	@GraphQLInvokeDetached
	public Collection<DataDefinition> getSiteDataDefinitionsPage(
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
	@GraphQLInvokeDetached
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
	@GraphQLInvokeDetached
	public Collection<DataLayout> getDataDefinitionDataLayoutsPage(
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
	@GraphQLInvokeDetached
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
	@GraphQLInvokeDetached
	public Collection<DataLayout> getSiteDataLayoutPage(
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
	@GraphQLInvokeDetached
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
	@GraphQLInvokeDetached
	public Collection<DataRecord> getDataDefinitionDataRecordsPage(
			@GraphQLName("dataDefinitionId") Long dataDefinitionId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_dataRecordResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataRecordResource -> {
				Page paginationPage =
					dataRecordResource.getDataDefinitionDataRecordsPage(
						dataDefinitionId, Pagination.of(pageSize, page));

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<DataRecord> getDataRecordCollectionDataRecordsPage(
			@GraphQLName("dataRecordCollectionId") Long dataRecordCollectionId,
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
	@GraphQLInvokeDetached
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
	@GraphQLInvokeDetached
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
	@GraphQLInvokeDetached
	public Collection<DataRecordCollection>
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
	@GraphQLInvokeDetached
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
	@GraphQLInvokeDetached
	public Collection<DataRecordCollection> getSiteDataRecordCollectionsPage(
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

		dataDefinitionResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	private void _populateResourceContext(DataLayoutResource dataLayoutResource)
		throws Exception {

		dataLayoutResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	private void _populateResourceContext(DataRecordResource dataRecordResource)
		throws Exception {

		dataRecordResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	private void _populateResourceContext(
			DataRecordCollectionResource dataRecordCollectionResource)
		throws Exception {

		dataRecordCollectionResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	private static ComponentServiceObjects<DataDefinitionResource>
		_dataDefinitionResourceComponentServiceObjects;
	private static ComponentServiceObjects<DataLayoutResource>
		_dataLayoutResourceComponentServiceObjects;
	private static ComponentServiceObjects<DataRecordResource>
		_dataRecordResourceComponentServiceObjects;
	private static ComponentServiceObjects<DataRecordCollectionResource>
		_dataRecordCollectionResourceComponentServiceObjects;

}