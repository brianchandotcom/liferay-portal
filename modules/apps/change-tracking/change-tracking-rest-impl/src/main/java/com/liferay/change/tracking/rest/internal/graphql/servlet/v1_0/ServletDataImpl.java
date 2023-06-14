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

package com.liferay.change.tracking.rest.internal.graphql.servlet.v1_0;

import com.liferay.change.tracking.rest.internal.graphql.mutation.v1_0.Mutation;
import com.liferay.change.tracking.rest.internal.graphql.query.v1_0.Query;
import com.liferay.change.tracking.rest.internal.resource.v1_0.CTCollectionResourceImpl;
import com.liferay.change.tracking.rest.internal.resource.v1_0.CTEntryResourceImpl;
import com.liferay.change.tracking.rest.internal.resource.v1_0.CTProcessResourceImpl;
import com.liferay.change.tracking.rest.resource.v1_0.CTCollectionResource;
import com.liferay.change.tracking.rest.resource.v1_0.CTEntryResource;
import com.liferay.change.tracking.rest.resource.v1_0.CTProcessResource;
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
 * @author David Truong
 * @generated
 */
@Component(service = ServletData.class)
@Generated("")
public class ServletDataImpl implements ServletData {

	@Activate
	public void activate(BundleContext bundleContext) {
		Mutation.setCTCollectionResourceComponentServiceObjects(
			_ctCollectionResourceComponentServiceObjects);
		Mutation.setCTProcessResourceComponentServiceObjects(
			_ctProcessResourceComponentServiceObjects);

		Query.setCTCollectionResourceComponentServiceObjects(
			_ctCollectionResourceComponentServiceObjects);
		Query.setCTEntryResourceComponentServiceObjects(
			_ctEntryResourceComponentServiceObjects);
		Query.setCTProcessResourceComponentServiceObjects(
			_ctProcessResourceComponentServiceObjects);
	}

	public String getApplicationName() {
		return "Liferay.Change.Tracking.REST";
	}

	@Override
	public Mutation getMutation() {
		return new Mutation();
	}

	@Override
	public String getPath() {
		return "/change-tracking-rest-graphql/v1_0";
	}

	@Override
	public Query getQuery() {
		return new Query();
	}

	public ObjectValuePair<Class<?>, String> getResourceMethodObjectValuePair(
		String methodName, boolean mutation) {

		if (mutation) {
			return _resourceMethodObjectValuePairs.get(
				"mutation#" + methodName);
		}

		return _resourceMethodObjectValuePairs.get("query#" + methodName);
	}

	private static final Map<String, ObjectValuePair<Class<?>, String>>
		_resourceMethodObjectValuePairs =
			new HashMap<String, ObjectValuePair<Class<?>, String>>() {
				{
					put(
						"mutation#createCTCollectionsPageExportBatch",
						new ObjectValuePair<>(
							CTCollectionResourceImpl.class,
							"postCTCollectionsPageExportBatch"));
					put(
						"mutation#createCTCollection",
						new ObjectValuePair<>(
							CTCollectionResourceImpl.class,
							"postCTCollection"));
					put(
						"mutation#createCTCollectionBatch",
						new ObjectValuePair<>(
							CTCollectionResourceImpl.class,
							"postCTCollectionBatch"));
					put(
						"mutation#deleteCTCollection",
						new ObjectValuePair<>(
							CTCollectionResourceImpl.class,
							"deleteCTCollection"));
					put(
						"mutation#deleteCTCollectionBatch",
						new ObjectValuePair<>(
							CTCollectionResourceImpl.class,
							"deleteCTCollectionBatch"));
					put(
						"mutation#patchCTCollection",
						new ObjectValuePair<>(
							CTCollectionResourceImpl.class,
							"patchCTCollection"));
					put(
						"mutation#updateCTCollection",
						new ObjectValuePair<>(
							CTCollectionResourceImpl.class, "putCTCollection"));
					put(
						"mutation#updateCTCollectionBatch",
						new ObjectValuePair<>(
							CTCollectionResourceImpl.class,
							"putCTCollectionBatch"));
					put(
						"mutation#createCTCollectionCheckout",
						new ObjectValuePair<>(
							CTCollectionResourceImpl.class,
							"postCTCollectionCheckout"));
					put(
						"mutation#createCTCollectionPublish",
						new ObjectValuePair<>(
							CTCollectionResourceImpl.class,
							"postCTCollectionPublish"));
					put(
						"mutation#createCTCollectionSchedulePublish",
						new ObjectValuePair<>(
							CTCollectionResourceImpl.class,
							"postCTCollectionSchedulePublish"));
					put(
						"mutation#createCTProcessesPageExportBatch",
						new ObjectValuePair<>(
							CTProcessResourceImpl.class,
							"postCTProcessesPageExportBatch"));
					put(
						"mutation#createCTProcessRevert",
						new ObjectValuePair<>(
							CTProcessResourceImpl.class,
							"postCTProcessRevert"));

					put(
						"query#cTCollections",
						new ObjectValuePair<>(
							CTCollectionResourceImpl.class,
							"getCTCollectionsPage"));
					put(
						"query#cTCollection",
						new ObjectValuePair<>(
							CTCollectionResourceImpl.class, "getCTCollection"));
					put(
						"query#ctCollectionCTEntries",
						new ObjectValuePair<>(
							CTEntryResourceImpl.class,
							"getCtCollectionCTEntriesPage"));
					put(
						"query#cTEntry",
						new ObjectValuePair<>(
							CTEntryResourceImpl.class, "getCTEntry"));
					put(
						"query#cTProcesses",
						new ObjectValuePair<>(
							CTProcessResourceImpl.class, "getCTProcessesPage"));
					put(
						"query#cTProcess",
						new ObjectValuePair<>(
							CTProcessResourceImpl.class, "getCTProcess"));

					put(
						"query#CTEntry.cTCollection",
						new ObjectValuePair<>(
							CTCollectionResourceImpl.class, "getCTCollection"));
					put(
						"query#CTCollection.ctCollectionCTEntries",
						new ObjectValuePair<>(
							CTEntryResourceImpl.class,
							"getCtCollectionCTEntriesPage"));
				}
			};

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<CTCollectionResource>
		_ctCollectionResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<CTProcessResource>
		_ctProcessResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<CTEntryResource>
		_ctEntryResourceComponentServiceObjects;

}