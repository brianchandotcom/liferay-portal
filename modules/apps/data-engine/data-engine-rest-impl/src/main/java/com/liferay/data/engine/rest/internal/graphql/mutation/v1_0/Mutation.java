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
import com.liferay.data.engine.rest.dto.v1_0.DataLayoutPermission;
import com.liferay.data.engine.rest.dto.v1_0.DataRecord;
import com.liferay.data.engine.rest.dto.v1_0.DataRecordCollection;
import com.liferay.data.engine.rest.dto.v1_0.DataRecordCollectionPermission;
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

	@GraphQLInvokeDetached
	public void deleteDataDefinition(
			@GraphQLName("dataDefinitionId") Long dataDefinitionId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_dataDefinitionResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataDefinitionResource ->
				dataDefinitionResource.deleteDataDefinition(dataDefinitionId));
	}

	@GraphQLInvokeDetached
	public DataDefinition putDataDefinition(
			@GraphQLName("dataDefinitionId") Long dataDefinitionId,
			@GraphQLName("dataDefinition") DataDefinition dataDefinition)
		throws Exception {

		return _applyComponentServiceObjects(
			_dataDefinitionResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataDefinitionResource -> dataDefinitionResource.putDataDefinition(
				dataDefinitionId, dataDefinition));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public void postDataDefinitionDataDefinitionPermission(
			@GraphQLName("dataDefinitionId") Long dataDefinitionId,
			@GraphQLName("operation") String operation,
			@GraphQLName("dataDefinitionPermission") DataDefinitionPermission
				dataDefinitionPermission)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_dataDefinitionResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataDefinitionResource ->
				dataDefinitionResource.
					postDataDefinitionDataDefinitionPermission(
						dataDefinitionId, operation, dataDefinitionPermission));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public void postSiteDataDefinitionPermission(
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("operation") String operation,
			@GraphQLName("dataDefinitionPermission") DataDefinitionPermission
				dataDefinitionPermission)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_dataDefinitionResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataDefinitionResource ->
				dataDefinitionResource.postSiteDataDefinitionPermission(
					siteId, operation, dataDefinitionPermission));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public DataDefinition postSiteDataDefinition(
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("dataDefinition") DataDefinition dataDefinition)
		throws Exception {

		return _applyComponentServiceObjects(
			_dataDefinitionResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataDefinitionResource ->
				dataDefinitionResource.postSiteDataDefinition(
					siteId, dataDefinition));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public DataLayout postDataDefinitionDataLayout(
			@GraphQLName("dataDefinitionId") Long dataDefinitionId,
			@GraphQLName("dataLayout") DataLayout dataLayout)
		throws Exception {

		return _applyComponentServiceObjects(
			_dataLayoutResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataLayoutResource ->
				dataLayoutResource.postDataDefinitionDataLayout(
					dataDefinitionId, dataLayout));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public void postDataLayoutDataLayoutPermission(
			@GraphQLName("dataLayoutId") Long dataLayoutId,
			@GraphQLName("operation") String operation,
			@GraphQLName("dataLayoutPermission") DataLayoutPermission
				dataLayoutPermission)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_dataLayoutResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataLayoutResource ->
				dataLayoutResource.postDataLayoutDataLayoutPermission(
					dataLayoutId, operation, dataLayoutPermission));
	}

	@GraphQLInvokeDetached
	public void deleteDataLayout(@GraphQLName("dataLayoutId") Long dataLayoutId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_dataLayoutResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataLayoutResource -> dataLayoutResource.deleteDataLayout(
				dataLayoutId));
	}

	@GraphQLInvokeDetached
	public DataLayout putDataLayout(
			@GraphQLName("dataLayoutId") Long dataLayoutId,
			@GraphQLName("dataLayout") DataLayout dataLayout)
		throws Exception {

		return _applyComponentServiceObjects(
			_dataLayoutResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataLayoutResource -> dataLayoutResource.putDataLayout(
				dataLayoutId, dataLayout));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public void postSiteDataLayoutPermission(
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("operation") String operation,
			@GraphQLName("dataLayoutPermission") DataLayoutPermission
				dataLayoutPermission)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_dataLayoutResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataLayoutResource ->
				dataLayoutResource.postSiteDataLayoutPermission(
					siteId, operation, dataLayoutPermission));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public DataRecord postDataDefinitionDataRecord(
			@GraphQLName("dataDefinitionId") Long dataDefinitionId,
			@GraphQLName("dataRecord") DataRecord dataRecord)
		throws Exception {

		return _applyComponentServiceObjects(
			_dataRecordResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataRecordResource ->
				dataRecordResource.postDataDefinitionDataRecord(
					dataDefinitionId, dataRecord));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public DataRecord postDataRecordCollectionDataRecord(
			@GraphQLName("dataRecordCollectionId") Long dataRecordCollectionId,
			@GraphQLName("dataRecord") DataRecord dataRecord)
		throws Exception {

		return _applyComponentServiceObjects(
			_dataRecordResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataRecordResource ->
				dataRecordResource.postDataRecordCollectionDataRecord(
					dataRecordCollectionId, dataRecord));
	}

	@GraphQLInvokeDetached
	public void deleteDataRecord(@GraphQLName("dataRecordId") Long dataRecordId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_dataRecordResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataRecordResource -> dataRecordResource.deleteDataRecord(
				dataRecordId));
	}

	@GraphQLInvokeDetached
	public DataRecord putDataRecord(
			@GraphQLName("dataRecordId") Long dataRecordId,
			@GraphQLName("dataRecord") DataRecord dataRecord)
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
			@GraphQLName("dataDefinitionId") Long dataDefinitionId,
			@GraphQLName("dataRecordCollection") DataRecordCollection
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
			@GraphQLName("dataRecordCollectionId") Long dataRecordCollectionId)
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
			@GraphQLName("dataRecordCollectionId") Long dataRecordCollectionId,
			@GraphQLName("dataRecordCollection") DataRecordCollection
				dataRecordCollection)
		throws Exception {

		return _applyComponentServiceObjects(
			_dataRecordCollectionResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataRecordCollectionResource ->
				dataRecordCollectionResource.putDataRecordCollection(
					dataRecordCollectionId, dataRecordCollection));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public void postDataRecordCollectionDataRecordCollectionPermission(
			@GraphQLName("dataRecordCollectionId") Long dataRecordCollectionId,
			@GraphQLName("operation") String operation,
			@GraphQLName("dataRecordCollectionPermission")
				DataRecordCollectionPermission dataRecordCollectionPermission)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_dataRecordCollectionResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataRecordCollectionResource ->
				dataRecordCollectionResource.
					postDataRecordCollectionDataRecordCollectionPermission(
						dataRecordCollectionId, operation,
						dataRecordCollectionPermission));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public void postSiteDataRecordCollectionPermission(
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("operation") String operation,
			@GraphQLName("dataRecordCollectionPermission")
				DataRecordCollectionPermission dataRecordCollectionPermission)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_dataRecordCollectionResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataRecordCollectionResource ->
				dataRecordCollectionResource.
					postSiteDataRecordCollectionPermission(
						siteId, operation, dataRecordCollectionPermission));
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