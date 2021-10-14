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

package com.liferay.search.experiences.rest.internal.graphql.servlet.v1_0;

import com.liferay.portal.vulcan.graphql.servlet.ServletData;
import com.liferay.search.experiences.rest.internal.graphql.mutation.v1_0.Mutation;
import com.liferay.search.experiences.rest.internal.graphql.query.v1_0.Query;
import com.liferay.search.experiences.rest.resource.v1_0.SXPBlueprintResource;
import com.liferay.search.experiences.rest.resource.v1_0.SXPElementResource;
import com.liferay.search.experiences.rest.resource.v1_0.SearchResponseResource;

import javax.annotation.Generated;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentServiceObjects;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceScope;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(immediate = true, service = ServletData.class)
@Generated("")
public class ServletDataImpl implements ServletData {

	@Activate
	public void activate(BundleContext bundleContext) {
		Mutation.setSXPBlueprintResourceComponentServiceObjects(
			_sxpBlueprintResourceComponentServiceObjects);
		Mutation.setSXPElementResourceComponentServiceObjects(
			_sxpElementResourceComponentServiceObjects);
		Mutation.setSearchResponseResourceComponentServiceObjects(
			_searchResponseResourceComponentServiceObjects);

		Query.setSXPBlueprintResourceComponentServiceObjects(
			_sxpBlueprintResourceComponentServiceObjects);
		Query.setSXPElementResourceComponentServiceObjects(
			_sxpElementResourceComponentServiceObjects);
	}

	@Override
	public Mutation getMutation() {
		return new Mutation();
	}

	@Override
	public String getPath() {
		return "/search-experiences-rest-graphql/v1_0";
	}

	@Override
	public Query getQuery() {
		return new Query();
	}

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<SXPBlueprintResource>
		_sxpBlueprintResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<SXPElementResource>
		_sxpElementResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<SearchResponseResource>
		_searchResponseResourceComponentServiceObjects;

}