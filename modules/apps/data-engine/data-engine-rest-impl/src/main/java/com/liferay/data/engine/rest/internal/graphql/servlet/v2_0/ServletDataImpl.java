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

package com.liferay.data.engine.rest.internal.graphql.servlet.v2_0;

import com.liferay.data.engine.rest.internal.graphql.mutation.v2_0.Mutation;
import com.liferay.data.engine.rest.internal.graphql.query.v2_0.Query;
import com.liferay.data.engine.rest.internal.resource.v2_0.DataDefinitionFieldLinkResourceImpl;
import com.liferay.data.engine.rest.internal.resource.v2_0.DataDefinitionResourceImpl;
import com.liferay.data.engine.rest.internal.resource.v2_0.DataLayoutResourceImpl;
import com.liferay.data.engine.rest.internal.resource.v2_0.DataListViewResourceImpl;
import com.liferay.data.engine.rest.internal.resource.v2_0.DataRecordCollectionResourceImpl;
import com.liferay.data.engine.rest.internal.resource.v2_0.DataRecordResourceImpl;
import com.liferay.data.engine.rest.resource.v2_0.DataDefinitionFieldLinkResource;
import com.liferay.data.engine.rest.resource.v2_0.DataDefinitionResource;
import com.liferay.data.engine.rest.resource.v2_0.DataLayoutResource;
import com.liferay.data.engine.rest.resource.v2_0.DataListViewResource;
import com.liferay.data.engine.rest.resource.v2_0.DataRecordCollectionResource;
import com.liferay.data.engine.rest.resource.v2_0.DataRecordResource;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.vulcan.graphql.servlet.ServletData;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentServiceObjects;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceScope;

/**
 * @author Jeyvison Nascimento
 * @generated
 */
@Component(service = ServletData.class)
@Generated("")
public class ServletDataImpl implements ServletData {

	@Activate
	public void activate(BundleContext bundleContext) {
		Mutation.setDataDefinitionResourceComponentServiceObjects(
			_dataDefinitionResourceComponentServiceObjects);
		Mutation.setDataLayoutResourceComponentServiceObjects(
			_dataLayoutResourceComponentServiceObjects);
		Mutation.setDataListViewResourceComponentServiceObjects(
			_dataListViewResourceComponentServiceObjects);
		Mutation.setDataRecordResourceComponentServiceObjects(
			_dataRecordResourceComponentServiceObjects);
		Mutation.setDataRecordCollectionResourceComponentServiceObjects(
			_dataRecordCollectionResourceComponentServiceObjects);

		Query.setDataDefinitionResourceComponentServiceObjects(
			_dataDefinitionResourceComponentServiceObjects);
		Query.setDataDefinitionFieldLinkResourceComponentServiceObjects(
			_dataDefinitionFieldLinkResourceComponentServiceObjects);
		Query.setDataLayoutResourceComponentServiceObjects(
			_dataLayoutResourceComponentServiceObjects);
		Query.setDataListViewResourceComponentServiceObjects(
			_dataListViewResourceComponentServiceObjects);
		Query.setDataRecordResourceComponentServiceObjects(
			_dataRecordResourceComponentServiceObjects);
		Query.setDataRecordCollectionResourceComponentServiceObjects(
			_dataRecordCollectionResourceComponentServiceObjects);
	}

	public String getApplicationName() {
		return "Liferay.Data.Engine.REST";
	}

	@Override
	public Mutation getMutation() {
		return new Mutation();
	}

	@Override
	public String getPath() {
		return "/data-engine-graphql/v2_0";
	}

	@Override
	public Query getQuery() {
		return new Query();
	}

	public ObjectValuePair<Class<?>, String> getResourceMethodPair(
		String methodName, boolean mutation) {

		if (mutation) {
			return _resourceMethodPairs.get("mutation#" + methodName);
		}

		return _resourceMethodPairs.get("query#" + methodName);
	}

	private static final Map<String, ObjectValuePair<Class<?>, String>>
		_resourceMethodPairs = new HashMap<>();

	static {
		_resourceMethodPairs.put(
			"mutation#createDataDefinitionByContentType",
			new ObjectValuePair<>(
				DataDefinitionResourceImpl.class,
				"postDataDefinitionByContentType"));
		_resourceMethodPairs.put(
			"mutation#deleteDataDefinition",
			new ObjectValuePair<>(
				DataDefinitionResourceImpl.class, "deleteDataDefinition"));
		_resourceMethodPairs.put(
			"mutation#deleteDataDefinitionBatch",
			new ObjectValuePair<>(
				DataDefinitionResourceImpl.class, "deleteDataDefinitionBatch"));
		_resourceMethodPairs.put(
			"mutation#patchDataDefinition",
			new ObjectValuePair<>(
				DataDefinitionResourceImpl.class, "patchDataDefinition"));
		_resourceMethodPairs.put(
			"mutation#updateDataDefinition",
			new ObjectValuePair<>(
				DataDefinitionResourceImpl.class, "putDataDefinition"));
		_resourceMethodPairs.put(
			"mutation#updateDataDefinitionBatch",
			new ObjectValuePair<>(
				DataDefinitionResourceImpl.class, "putDataDefinitionBatch"));
		_resourceMethodPairs.put(
			"mutation#updateDataDefinitionPermissionsPage",
			new ObjectValuePair<>(
				DataDefinitionResourceImpl.class,
				"putDataDefinitionPermissionsPage"));
		_resourceMethodPairs.put(
			"mutation#createSiteDataDefinitionByContentType",
			new ObjectValuePair<>(
				DataDefinitionResourceImpl.class,
				"postSiteDataDefinitionByContentType"));
		_resourceMethodPairs.put(
			"mutation#deleteDataDefinitionDataLayout",
			new ObjectValuePair<>(
				DataLayoutResourceImpl.class,
				"deleteDataDefinitionDataLayout"));
		_resourceMethodPairs.put(
			"mutation#createDataDefinitionDataLayout",
			new ObjectValuePair<>(
				DataLayoutResourceImpl.class, "postDataDefinitionDataLayout"));
		_resourceMethodPairs.put(
			"mutation#createDataDefinitionDataLayoutBatch",
			new ObjectValuePair<>(
				DataLayoutResourceImpl.class,
				"postDataDefinitionDataLayoutBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteDataLayout",
			new ObjectValuePair<>(
				DataLayoutResourceImpl.class, "deleteDataLayout"));
		_resourceMethodPairs.put(
			"mutation#deleteDataLayoutBatch",
			new ObjectValuePair<>(
				DataLayoutResourceImpl.class, "deleteDataLayoutBatch"));
		_resourceMethodPairs.put(
			"mutation#updateDataLayout",
			new ObjectValuePair<>(
				DataLayoutResourceImpl.class, "putDataLayout"));
		_resourceMethodPairs.put(
			"mutation#updateDataLayoutBatch",
			new ObjectValuePair<>(
				DataLayoutResourceImpl.class, "putDataLayoutBatch"));
		_resourceMethodPairs.put(
			"mutation#createDataLayoutContext",
			new ObjectValuePair<>(
				DataLayoutResourceImpl.class, "postDataLayoutContext"));
		_resourceMethodPairs.put(
			"mutation#deleteDataDefinitionDataListView",
			new ObjectValuePair<>(
				DataListViewResourceImpl.class,
				"deleteDataDefinitionDataListView"));
		_resourceMethodPairs.put(
			"mutation#createDataDefinitionDataListView",
			new ObjectValuePair<>(
				DataListViewResourceImpl.class,
				"postDataDefinitionDataListView"));
		_resourceMethodPairs.put(
			"mutation#createDataDefinitionDataListViewBatch",
			new ObjectValuePair<>(
				DataListViewResourceImpl.class,
				"postDataDefinitionDataListViewBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteDataListView",
			new ObjectValuePair<>(
				DataListViewResourceImpl.class, "deleteDataListView"));
		_resourceMethodPairs.put(
			"mutation#deleteDataListViewBatch",
			new ObjectValuePair<>(
				DataListViewResourceImpl.class, "deleteDataListViewBatch"));
		_resourceMethodPairs.put(
			"mutation#updateDataListView",
			new ObjectValuePair<>(
				DataListViewResourceImpl.class, "putDataListView"));
		_resourceMethodPairs.put(
			"mutation#updateDataListViewBatch",
			new ObjectValuePair<>(
				DataListViewResourceImpl.class, "putDataListViewBatch"));
		_resourceMethodPairs.put(
			"mutation#createDataDefinitionDataRecord",
			new ObjectValuePair<>(
				DataRecordResourceImpl.class, "postDataDefinitionDataRecord"));
		_resourceMethodPairs.put(
			"mutation#createDataDefinitionDataRecordBatch",
			new ObjectValuePair<>(
				DataRecordResourceImpl.class,
				"postDataDefinitionDataRecordBatch"));
		_resourceMethodPairs.put(
			"mutation#createDataRecordCollectionDataRecord",
			new ObjectValuePair<>(
				DataRecordResourceImpl.class,
				"postDataRecordCollectionDataRecord"));
		_resourceMethodPairs.put(
			"mutation#createDataRecordCollectionDataRecordBatch",
			new ObjectValuePair<>(
				DataRecordResourceImpl.class,
				"postDataRecordCollectionDataRecordBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteDataRecord",
			new ObjectValuePair<>(
				DataRecordResourceImpl.class, "deleteDataRecord"));
		_resourceMethodPairs.put(
			"mutation#deleteDataRecordBatch",
			new ObjectValuePair<>(
				DataRecordResourceImpl.class, "deleteDataRecordBatch"));
		_resourceMethodPairs.put(
			"mutation#patchDataRecord",
			new ObjectValuePair<>(
				DataRecordResourceImpl.class, "patchDataRecord"));
		_resourceMethodPairs.put(
			"mutation#updateDataRecord",
			new ObjectValuePair<>(
				DataRecordResourceImpl.class, "putDataRecord"));
		_resourceMethodPairs.put(
			"mutation#updateDataRecordBatch",
			new ObjectValuePair<>(
				DataRecordResourceImpl.class, "putDataRecordBatch"));
		_resourceMethodPairs.put(
			"mutation#createDataDefinitionDataRecordCollection",
			new ObjectValuePair<>(
				DataRecordCollectionResourceImpl.class,
				"postDataDefinitionDataRecordCollection"));
		_resourceMethodPairs.put(
			"mutation#createDataDefinitionDataRecordCollectionBatch",
			new ObjectValuePair<>(
				DataRecordCollectionResourceImpl.class,
				"postDataDefinitionDataRecordCollectionBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteDataRecordCollection",
			new ObjectValuePair<>(
				DataRecordCollectionResourceImpl.class,
				"deleteDataRecordCollection"));
		_resourceMethodPairs.put(
			"mutation#deleteDataRecordCollectionBatch",
			new ObjectValuePair<>(
				DataRecordCollectionResourceImpl.class,
				"deleteDataRecordCollectionBatch"));
		_resourceMethodPairs.put(
			"mutation#updateDataRecordCollection",
			new ObjectValuePair<>(
				DataRecordCollectionResourceImpl.class,
				"putDataRecordCollection"));
		_resourceMethodPairs.put(
			"mutation#updateDataRecordCollectionBatch",
			new ObjectValuePair<>(
				DataRecordCollectionResourceImpl.class,
				"putDataRecordCollectionBatch"));
		_resourceMethodPairs.put(
			"mutation#updateDataRecordCollectionPermissionsPage",
			new ObjectValuePair<>(
				DataRecordCollectionResourceImpl.class,
				"putDataRecordCollectionPermissionsPage"));
		_resourceMethodPairs.put(
			"query#dataDefinitionByContentTypeContentType",
			new ObjectValuePair<>(
				DataDefinitionResourceImpl.class,
				"getDataDefinitionByContentTypeContentTypePage"));
		_resourceMethodPairs.put(
			"query#dataDefinitionDataDefinitionFieldFieldTypes",
			new ObjectValuePair<>(
				DataDefinitionResourceImpl.class,
				"getDataDefinitionDataDefinitionFieldFieldTypes"));
		_resourceMethodPairs.put(
			"query#dataDefinition",
			new ObjectValuePair<>(
				DataDefinitionResourceImpl.class, "getDataDefinition"));
		_resourceMethodPairs.put(
			"query#dataDefinitionPermissions",
			new ObjectValuePair<>(
				DataDefinitionResourceImpl.class,
				"getDataDefinitionPermissionsPage"));
		_resourceMethodPairs.put(
			"query#siteDataDefinitionByContentTypeContentType",
			new ObjectValuePair<>(
				DataDefinitionResourceImpl.class,
				"getSiteDataDefinitionByContentTypeContentTypePage"));
		_resourceMethodPairs.put(
			"query#dataDefinitionByContentTypeByDataDefinitionKey",
			new ObjectValuePair<>(
				DataDefinitionResourceImpl.class,
				"getSiteDataDefinitionByContentTypeByDataDefinitionKey"));
		_resourceMethodPairs.put(
			"query#dataDefinitionDataDefinitionFieldLinks",
			new ObjectValuePair<>(
				DataDefinitionFieldLinkResourceImpl.class,
				"getDataDefinitionDataDefinitionFieldLinksPage"));
		_resourceMethodPairs.put(
			"query#dataDefinitionDataLayouts",
			new ObjectValuePair<>(
				DataLayoutResourceImpl.class,
				"getDataDefinitionDataLayoutsPage"));
		_resourceMethodPairs.put(
			"query#dataLayout",
			new ObjectValuePair<>(
				DataLayoutResourceImpl.class, "getDataLayout"));
		_resourceMethodPairs.put(
			"query#dataLayoutByContentTypeByDataLayoutKey",
			new ObjectValuePair<>(
				DataLayoutResourceImpl.class,
				"getSiteDataLayoutByContentTypeByDataLayoutKey"));
		_resourceMethodPairs.put(
			"query#dataDefinitionDataListViews",
			new ObjectValuePair<>(
				DataListViewResourceImpl.class,
				"getDataDefinitionDataListViewsPage"));
		_resourceMethodPairs.put(
			"query#dataListView",
			new ObjectValuePair<>(
				DataListViewResourceImpl.class, "getDataListView"));
		_resourceMethodPairs.put(
			"query#dataDefinitionDataRecords",
			new ObjectValuePair<>(
				DataRecordResourceImpl.class,
				"getDataDefinitionDataRecordsPage"));
		_resourceMethodPairs.put(
			"query#dataRecordCollectionDataRecords",
			new ObjectValuePair<>(
				DataRecordResourceImpl.class,
				"getDataRecordCollectionDataRecordsPage"));
		_resourceMethodPairs.put(
			"query#dataRecordCollectionDataRecordExport",
			new ObjectValuePair<>(
				DataRecordResourceImpl.class,
				"getDataRecordCollectionDataRecordExport"));
		_resourceMethodPairs.put(
			"query#dataRecord",
			new ObjectValuePair<>(
				DataRecordResourceImpl.class, "getDataRecord"));
		_resourceMethodPairs.put(
			"query#dataDefinitionDataRecordCollection",
			new ObjectValuePair<>(
				DataRecordCollectionResourceImpl.class,
				"getDataDefinitionDataRecordCollection"));
		_resourceMethodPairs.put(
			"query#dataDefinitionDataRecordCollections",
			new ObjectValuePair<>(
				DataRecordCollectionResourceImpl.class,
				"getDataDefinitionDataRecordCollectionsPage"));
		_resourceMethodPairs.put(
			"query#dataRecordCollection",
			new ObjectValuePair<>(
				DataRecordCollectionResourceImpl.class,
				"getDataRecordCollection"));
		_resourceMethodPairs.put(
			"query#dataRecordCollectionPermissions",
			new ObjectValuePair<>(
				DataRecordCollectionResourceImpl.class,
				"getDataRecordCollectionPermissionsPage"));
		_resourceMethodPairs.put(
			"query#dataRecordCollectionPermissionByCurrentUser",
			new ObjectValuePair<>(
				DataRecordCollectionResourceImpl.class,
				"getDataRecordCollectionPermissionByCurrentUser"));
		_resourceMethodPairs.put(
			"query#dataRecordCollectionByDataRecordCollectionKey",
			new ObjectValuePair<>(
				DataRecordCollectionResourceImpl.class,
				"getSiteDataRecordCollectionByDataRecordCollectionKey"));
	}

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<DataDefinitionResource>
		_dataDefinitionResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<DataLayoutResource>
		_dataLayoutResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<DataListViewResource>
		_dataListViewResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<DataRecordResource>
		_dataRecordResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<DataRecordCollectionResource>
		_dataRecordCollectionResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<DataDefinitionFieldLinkResource>
		_dataDefinitionFieldLinkResourceComponentServiceObjects;

}