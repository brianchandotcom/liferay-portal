/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.segments.asah.rest.internal.graphql.servlet.v1_0;

import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.vulcan.graphql.servlet.ServletData;
import com.liferay.segments.asah.rest.internal.graphql.mutation.v1_0.Mutation;
import com.liferay.segments.asah.rest.internal.graphql.query.v1_0.Query;
import com.liferay.segments.asah.rest.internal.resource.v1_0.ExperimentResourceImpl;
import com.liferay.segments.asah.rest.internal.resource.v1_0.ExperimentRunResourceImpl;
import com.liferay.segments.asah.rest.internal.resource.v1_0.StatusResourceImpl;
import com.liferay.segments.asah.rest.resource.v1_0.ExperimentResource;
import com.liferay.segments.asah.rest.resource.v1_0.ExperimentRunResource;
import com.liferay.segments.asah.rest.resource.v1_0.StatusResource;

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
 * @author Javier Gamarra
 * @generated
 */
@Component(service = ServletData.class)
@Generated("")
public class ServletDataImpl implements ServletData {

	@Activate
	public void activate(BundleContext bundleContext) {
		Mutation.setExperimentResourceComponentServiceObjects(
			_experimentResourceComponentServiceObjects);
		Mutation.setExperimentRunResourceComponentServiceObjects(
			_experimentRunResourceComponentServiceObjects);
		Mutation.setStatusResourceComponentServiceObjects(
			_statusResourceComponentServiceObjects);

		Query.setExperimentResourceComponentServiceObjects(
			_experimentResourceComponentServiceObjects);
	}

	public String getApplicationName() {
		return "Liferay.Segments.Asah.REST";
	}

	@Override
	public Mutation getMutation() {
		return new Mutation();
	}

	@Override
	public String getPath() {
		return "/segments-asah-graphql/v1_0";
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
			"mutation#deleteExperiment",
			new ObjectValuePair<>(
				ExperimentResourceImpl.class, "deleteExperiment"));
		_resourceMethodPairs.put(
			"mutation#deleteExperimentBatch",
			new ObjectValuePair<>(
				ExperimentResourceImpl.class, "deleteExperimentBatch"));
		_resourceMethodPairs.put(
			"mutation#createExperimentRun",
			new ObjectValuePair<>(
				ExperimentRunResourceImpl.class, "postExperimentRun"));
		_resourceMethodPairs.put(
			"mutation#createExperimentStatus",
			new ObjectValuePair<>(
				StatusResourceImpl.class, "postExperimentStatus"));
		_resourceMethodPairs.put(
			"mutation#createExperimentStatusBatch",
			new ObjectValuePair<>(
				StatusResourceImpl.class, "postExperimentStatusBatch"));
		_resourceMethodPairs.put(
			"query#experiment",
			new ObjectValuePair<>(
				ExperimentResourceImpl.class, "getExperiment"));
	}

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<ExperimentResource>
		_experimentResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<ExperimentRunResource>
		_experimentRunResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<StatusResource>
		_statusResourceComponentServiceObjects;

}