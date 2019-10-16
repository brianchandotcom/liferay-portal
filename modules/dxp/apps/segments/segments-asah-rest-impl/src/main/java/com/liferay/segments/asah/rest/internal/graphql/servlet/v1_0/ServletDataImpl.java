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

package com.liferay.segments.asah.rest.internal.graphql.servlet.v1_0;

import com.liferay.portal.vulcan.graphql.servlet.ServletData;
import com.liferay.segments.asah.rest.internal.graphql.mutation.v1_0.Mutation;
import com.liferay.segments.asah.rest.internal.graphql.query.v1_0.Query;
import com.liferay.segments.asah.rest.resource.v1_0.ExperimentResource;
import com.liferay.segments.asah.rest.resource.v1_0.ExperimentRunResource;
import com.liferay.segments.asah.rest.resource.v1_0.StatusResource;

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
@Component(immediate = true, service = ServletData.class)
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
	}

	@Override
	public Mutation getMutation() {
		return new Mutation();
	}

	/**
	 * @deprecated
	 */
	@Override
	@Deprecated
	public String getPath() {
		return "/segments-asah-graphql/v1_0";
	}

	@Override
	public Query getQuery() {
		return new Query();
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