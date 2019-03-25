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

package com.liferay.data.engine.rest.internal.graphql.mutation.v1_0;

import com.liferay.data.engine.rest.dto.v1_0.DataDefinition;
import com.liferay.data.engine.rest.dto.v1_0.DataDefinitionPermission;
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

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLInvokeDetached;
import graphql.annotations.annotationTypes.GraphQLName;

import javax.annotation.Generated;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author Jeyvison Nascimento
 * @generated
 */
@Generated("")
public class Mutation {

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
	public DataDefinition postContentSpaceDataDefinition(
			@GraphQLName("content-space-id") Long contentSpaceId,
			@GraphQLName("DataDefinition") DataDefinition dataDefinition)
		throws Exception {

		return _applyComponentServiceObjects(
			_dataDefinitionResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataDefinitionResource ->
				dataDefinitionResource.postContentSpaceDataDefinition(
					contentSpaceId, dataDefinition));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public boolean postDataDefinitionPermission(
			@GraphQLName("data-definition-id") Long dataDefinitionId,
			@GraphQLName("operation") String operation,
			@GraphQLName("DataDefinitionPermission") DataDefinitionPermission
				dataDefinitionPermission)
		throws Exception {

		return _applyComponentServiceObjects(
			_dataDefinitionResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataDefinitionResource ->
				dataDefinitionResource.postDataDefinitionPermission(
					dataDefinitionId, operation, dataDefinitionPermission));
	}

	@GraphQLInvokeDetached
	public void deleteDataDefinition(
			@GraphQLName("data-definition-id") Long dataDefinitionId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_dataDefinitionResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataDefinitionResource ->
				dataDefinitionResource.deleteDataDefinition(dataDefinitionId));
	}

	@GraphQLInvokeDetached
	public DataDefinition putDataDefinition(
			@GraphQLName("data-definition-id") Long dataDefinitionId,
			@GraphQLName("DataDefinition") DataDefinition dataDefinition)
		throws Exception {

		return _applyComponentServiceObjects(
			_dataDefinitionResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataDefinitionResource -> dataDefinitionResource.putDataDefinition(
				dataDefinitionId, dataDefinition));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public DataLayout postDataDefinitionDataLayout(
			@GraphQLName("data-definition-id") Long dataDefinitionId,
			@GraphQLName("DataLayout") DataLayout dataLayout)
		throws Exception {

		return _applyComponentServiceObjects(
			_dataLayoutResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataLayoutResource ->
				dataLayoutResource.postDataDefinitionDataLayout(
					dataDefinitionId, dataLayout));
	}

	@GraphQLInvokeDetached
	public void deleteDataLayout(
			@GraphQLName("data-layout-id") Long dataLayoutId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_dataLayoutResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataLayoutResource -> dataLayoutResource.deleteDataLayout(
				dataLayoutId));
	}

	@GraphQLInvokeDetached
	public DataLayout putDataLayout(
			@GraphQLName("data-layout-id") Long dataLayoutId,
			@GraphQLName("DataLayout") DataLayout dataLayout)
		throws Exception {

		return _applyComponentServiceObjects(
			_dataLayoutResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataLayoutResource -> dataLayoutResource.putDataLayout(
				dataLayoutId, dataLayout));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public DataRecord postDataRecordCollectionDataRecord(
			@GraphQLName("data-record-collection-id") Long
				dataRecordCollectionId,
			@GraphQLName("DataRecord") DataRecord dataRecord)
		throws Exception {

		return _applyComponentServiceObjects(
			_dataRecordResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataRecordResource ->
				dataRecordResource.postDataRecordCollectionDataRecord(
					dataRecordCollectionId, dataRecord));
	}

	@GraphQLInvokeDetached
	public void deleteDataRecord(
			@GraphQLName("data-record-id") Long dataRecordId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_dataRecordResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataRecordResource -> dataRecordResource.deleteDataRecord(
				dataRecordId));
	}

	@GraphQLInvokeDetached
	public DataRecord putDataRecord(
			@GraphQLName("data-record-id") Long dataRecordId,
			@GraphQLName("DataRecord") DataRecord dataRecord)
		throws Exception {

		return _applyComponentServiceObjects(
			_dataRecordResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataRecordResource -> dataRecordResource.putDataRecord(
				dataRecordId, dataRecord));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public DataRecordCollection postDataDefinitionDataRecordCollection(
			@GraphQLName("data-definition-id") Long dataDefinitionId,
			@GraphQLName("DataRecordCollection") DataRecordCollection
				dataRecordCollection)
		throws Exception {

		return _applyComponentServiceObjects(
			_dataRecordCollectionResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataRecordCollectionResource ->
				dataRecordCollectionResource.
					postDataDefinitionDataRecordCollection(
						dataDefinitionId, dataRecordCollection));
	}

	@GraphQLInvokeDetached
	public void deleteDataRecordCollection(
			@GraphQLName("data-record-collection-id") Long
				dataRecordCollectionId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_dataRecordCollectionResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataRecordCollectionResource ->
				dataRecordCollectionResource.deleteDataRecordCollection(
					dataRecordCollectionId));
	}

	@GraphQLInvokeDetached
	public DataRecordCollection putDataRecordCollection(
			@GraphQLName("data-record-collection-id") Long
				dataRecordCollectionId,
			@GraphQLName("DataRecordCollection") DataRecordCollection
				dataRecordCollection)
		throws Exception {

		return _applyComponentServiceObjects(
			_dataRecordCollectionResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataRecordCollectionResource ->
				dataRecordCollectionResource.putDataRecordCollection(
					dataRecordCollectionId, dataRecordCollection));
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

	private <T, E1 extends Throwable, E2 extends Throwable> void
			_applyVoidComponentServiceObjects(
				ComponentServiceObjects<T> componentServiceObjects,
				UnsafeConsumer<T, E1> unsafeConsumer,
				UnsafeConsumer<T, E2> unsafeFunction)
		throws E1, E2 {

		T resource = componentServiceObjects.getService();

		try {
			unsafeConsumer.accept(resource);

			unsafeFunction.accept(resource);
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