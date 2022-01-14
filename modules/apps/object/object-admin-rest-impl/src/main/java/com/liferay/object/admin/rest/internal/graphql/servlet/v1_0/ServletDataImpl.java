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

package com.liferay.object.admin.rest.internal.graphql.servlet.v1_0;

import com.liferay.object.admin.rest.internal.graphql.mutation.v1_0.Mutation;
import com.liferay.object.admin.rest.internal.graphql.query.v1_0.Query;
import com.liferay.object.admin.rest.resource.v1_0.ObjectActionResource;
import com.liferay.object.admin.rest.resource.v1_0.ObjectDefinitionResource;
import com.liferay.object.admin.rest.resource.v1_0.ObjectFieldResource;
import com.liferay.object.admin.rest.resource.v1_0.ObjectLayoutResource;
import com.liferay.object.admin.rest.resource.v1_0.ObjectRelationshipResource;
import com.liferay.object.admin.rest.resource.v1_0.ObjectViewResource;
import com.liferay.portal.vulcan.graphql.servlet.ServletData;

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
		Mutation.setObjectActionResourceComponentServiceObjects(
			_objectActionResourceComponentServiceObjects);
		Mutation.setObjectDefinitionResourceComponentServiceObjects(
			_objectDefinitionResourceComponentServiceObjects);
		Mutation.setObjectFieldResourceComponentServiceObjects(
			_objectFieldResourceComponentServiceObjects);
		Mutation.setObjectLayoutResourceComponentServiceObjects(
			_objectLayoutResourceComponentServiceObjects);
		Mutation.setObjectRelationshipResourceComponentServiceObjects(
			_objectRelationshipResourceComponentServiceObjects);
		Mutation.setObjectViewResourceComponentServiceObjects(
			_objectViewResourceComponentServiceObjects);

		Query.setObjectActionResourceComponentServiceObjects(
			_objectActionResourceComponentServiceObjects);
		Query.setObjectDefinitionResourceComponentServiceObjects(
			_objectDefinitionResourceComponentServiceObjects);
		Query.setObjectFieldResourceComponentServiceObjects(
			_objectFieldResourceComponentServiceObjects);
		Query.setObjectLayoutResourceComponentServiceObjects(
			_objectLayoutResourceComponentServiceObjects);
		Query.setObjectRelationshipResourceComponentServiceObjects(
			_objectRelationshipResourceComponentServiceObjects);
		Query.setObjectViewResourceComponentServiceObjects(
			_objectViewResourceComponentServiceObjects);
	}

	@Override
	public Mutation getMutation() {
		return new Mutation();
	}

	@Override
	public String getPath() {
		return "/object-admin-graphql/v1_0";
	}

	@Override
	public Query getQuery() {
		return new Query();
	}

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<ObjectActionResource>
		_objectActionResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<ObjectDefinitionResource>
		_objectDefinitionResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<ObjectFieldResource>
		_objectFieldResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<ObjectLayoutResource>
		_objectLayoutResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<ObjectRelationshipResource>
		_objectRelationshipResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<ObjectViewResource>
		_objectViewResourceComponentServiceObjects;

}