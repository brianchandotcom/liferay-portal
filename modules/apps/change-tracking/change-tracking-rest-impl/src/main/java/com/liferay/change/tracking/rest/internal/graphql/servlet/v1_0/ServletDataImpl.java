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
import com.liferay.change.tracking.rest.internal.resource.v1_0.PublicationResourceImpl;
import com.liferay.change.tracking.rest.resource.v1_0.PublicationResource;
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
		Mutation.setPublicationResourceComponentServiceObjects(
			_publicationResourceComponentServiceObjects);

		Query.setPublicationResourceComponentServiceObjects(
			_publicationResourceComponentServiceObjects);
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
						"mutation#createPublicationsPageExportBatch",
						new ObjectValuePair<>(
							PublicationResourceImpl.class,
							"postPublicationsPageExportBatch"));
					put(
						"mutation#createPublication",
						new ObjectValuePair<>(
							PublicationResourceImpl.class, "postPublication"));
					put(
						"mutation#createPublicationBatch",
						new ObjectValuePair<>(
							PublicationResourceImpl.class,
							"postPublicationBatch"));
					put(
						"mutation#deletePublication",
						new ObjectValuePair<>(
							PublicationResourceImpl.class,
							"deletePublication"));
					put(
						"mutation#deletePublicationBatch",
						new ObjectValuePair<>(
							PublicationResourceImpl.class,
							"deletePublicationBatch"));
					put(
						"mutation#patchPublication",
						new ObjectValuePair<>(
							PublicationResourceImpl.class, "patchPublication"));
					put(
						"mutation#updatePublication",
						new ObjectValuePair<>(
							PublicationResourceImpl.class, "putPublication"));
					put(
						"mutation#updatePublicationBatch",
						new ObjectValuePair<>(
							PublicationResourceImpl.class,
							"putPublicationBatch"));
					put(
						"mutation#createPublicationCheckout",
						new ObjectValuePair<>(
							PublicationResourceImpl.class,
							"postPublicationCheckout"));
					put(
						"mutation#createPublicationPublish",
						new ObjectValuePair<>(
							PublicationResourceImpl.class,
							"postPublicationPublish"));
					put(
						"mutation#createPublicationSchedulePublish",
						new ObjectValuePair<>(
							PublicationResourceImpl.class,
							"postPublicationSchedulePublish"));

					put(
						"query#publications",
						new ObjectValuePair<>(
							PublicationResourceImpl.class,
							"getPublicationsPage"));
					put(
						"query#publication",
						new ObjectValuePair<>(
							PublicationResourceImpl.class, "getPublication"));
				}
			};

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<PublicationResource>
		_publicationResourceComponentServiceObjects;

}