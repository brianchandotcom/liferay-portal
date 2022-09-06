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
import com.liferay.object.admin.rest.internal.resource.v1_0.ObjectActionResourceImpl;
import com.liferay.object.admin.rest.internal.resource.v1_0.ObjectDefinitionResourceImpl;
import com.liferay.object.admin.rest.internal.resource.v1_0.ObjectFieldResourceImpl;
import com.liferay.object.admin.rest.internal.resource.v1_0.ObjectLayoutResourceImpl;
import com.liferay.object.admin.rest.internal.resource.v1_0.ObjectRelationshipResourceImpl;
import com.liferay.object.admin.rest.internal.resource.v1_0.ObjectValidationRuleResourceImpl;
import com.liferay.object.admin.rest.internal.resource.v1_0.ObjectViewResourceImpl;
import com.liferay.object.admin.rest.resource.v1_0.ObjectActionResource;
import com.liferay.object.admin.rest.resource.v1_0.ObjectDefinitionResource;
import com.liferay.object.admin.rest.resource.v1_0.ObjectFieldResource;
import com.liferay.object.admin.rest.resource.v1_0.ObjectLayoutResource;
import com.liferay.object.admin.rest.resource.v1_0.ObjectRelationshipResource;
import com.liferay.object.admin.rest.resource.v1_0.ObjectValidationRuleResource;
import com.liferay.object.admin.rest.resource.v1_0.ObjectViewResource;
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
 * @author Javier Gamarra
 * @generated
 */
@Component(service = ServletData.class)
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
		Mutation.setObjectValidationRuleResourceComponentServiceObjects(
			_objectValidationRuleResourceComponentServiceObjects);
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
		Query.setObjectValidationRuleResourceComponentServiceObjects(
			_objectValidationRuleResourceComponentServiceObjects);
		Query.setObjectViewResourceComponentServiceObjects(
			_objectViewResourceComponentServiceObjects);
	}

	public String getApplicationName() {
		return "Liferay.Object.Admin.REST";
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
			"mutation#deleteObjectAction",
			new ObjectValuePair<>(
				ObjectActionResourceImpl.class, "deleteObjectAction"));
		_resourceMethodPairs.put(
			"mutation#deleteObjectActionBatch",
			new ObjectValuePair<>(
				ObjectActionResourceImpl.class, "deleteObjectActionBatch"));
		_resourceMethodPairs.put(
			"mutation#patchObjectAction",
			new ObjectValuePair<>(
				ObjectActionResourceImpl.class, "patchObjectAction"));
		_resourceMethodPairs.put(
			"mutation#updateObjectAction",
			new ObjectValuePair<>(
				ObjectActionResourceImpl.class, "putObjectAction"));
		_resourceMethodPairs.put(
			"mutation#updateObjectActionBatch",
			new ObjectValuePair<>(
				ObjectActionResourceImpl.class, "putObjectActionBatch"));
		_resourceMethodPairs.put(
			"mutation#createObjectDefinitionObjectAction",
			new ObjectValuePair<>(
				ObjectActionResourceImpl.class,
				"postObjectDefinitionObjectAction"));
		_resourceMethodPairs.put(
			"mutation#createObjectDefinitionObjectActionBatch",
			new ObjectValuePair<>(
				ObjectActionResourceImpl.class,
				"postObjectDefinitionObjectActionBatch"));
		_resourceMethodPairs.put(
			"mutation#createObjectDefinition",
			new ObjectValuePair<>(
				ObjectDefinitionResourceImpl.class, "postObjectDefinition"));
		_resourceMethodPairs.put(
			"mutation#createObjectDefinitionBatch",
			new ObjectValuePair<>(
				ObjectDefinitionResourceImpl.class,
				"postObjectDefinitionBatch"));
		_resourceMethodPairs.put(
			"mutation#updateObjectDefinitionByExternalReferenceCode",
			new ObjectValuePair<>(
				ObjectDefinitionResourceImpl.class,
				"putObjectDefinitionByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#deleteObjectDefinition",
			new ObjectValuePair<>(
				ObjectDefinitionResourceImpl.class, "deleteObjectDefinition"));
		_resourceMethodPairs.put(
			"mutation#deleteObjectDefinitionBatch",
			new ObjectValuePair<>(
				ObjectDefinitionResourceImpl.class,
				"deleteObjectDefinitionBatch"));
		_resourceMethodPairs.put(
			"mutation#patchObjectDefinition",
			new ObjectValuePair<>(
				ObjectDefinitionResourceImpl.class, "patchObjectDefinition"));
		_resourceMethodPairs.put(
			"mutation#updateObjectDefinition",
			new ObjectValuePair<>(
				ObjectDefinitionResourceImpl.class, "putObjectDefinition"));
		_resourceMethodPairs.put(
			"mutation#updateObjectDefinitionBatch",
			new ObjectValuePair<>(
				ObjectDefinitionResourceImpl.class,
				"putObjectDefinitionBatch"));
		_resourceMethodPairs.put(
			"mutation#createObjectDefinitionPublish",
			new ObjectValuePair<>(
				ObjectDefinitionResourceImpl.class,
				"postObjectDefinitionPublish"));
		_resourceMethodPairs.put(
			"mutation#createObjectDefinitionObjectField",
			new ObjectValuePair<>(
				ObjectFieldResourceImpl.class,
				"postObjectDefinitionObjectField"));
		_resourceMethodPairs.put(
			"mutation#createObjectDefinitionObjectFieldBatch",
			new ObjectValuePair<>(
				ObjectFieldResourceImpl.class,
				"postObjectDefinitionObjectFieldBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteObjectField",
			new ObjectValuePair<>(
				ObjectFieldResourceImpl.class, "deleteObjectField"));
		_resourceMethodPairs.put(
			"mutation#deleteObjectFieldBatch",
			new ObjectValuePair<>(
				ObjectFieldResourceImpl.class, "deleteObjectFieldBatch"));
		_resourceMethodPairs.put(
			"mutation#patchObjectField",
			new ObjectValuePair<>(
				ObjectFieldResourceImpl.class, "patchObjectField"));
		_resourceMethodPairs.put(
			"mutation#updateObjectField",
			new ObjectValuePair<>(
				ObjectFieldResourceImpl.class, "putObjectField"));
		_resourceMethodPairs.put(
			"mutation#updateObjectFieldBatch",
			new ObjectValuePair<>(
				ObjectFieldResourceImpl.class, "putObjectFieldBatch"));
		_resourceMethodPairs.put(
			"mutation#createObjectDefinitionObjectLayout",
			new ObjectValuePair<>(
				ObjectLayoutResourceImpl.class,
				"postObjectDefinitionObjectLayout"));
		_resourceMethodPairs.put(
			"mutation#createObjectDefinitionObjectLayoutBatch",
			new ObjectValuePair<>(
				ObjectLayoutResourceImpl.class,
				"postObjectDefinitionObjectLayoutBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteObjectLayout",
			new ObjectValuePair<>(
				ObjectLayoutResourceImpl.class, "deleteObjectLayout"));
		_resourceMethodPairs.put(
			"mutation#deleteObjectLayoutBatch",
			new ObjectValuePair<>(
				ObjectLayoutResourceImpl.class, "deleteObjectLayoutBatch"));
		_resourceMethodPairs.put(
			"mutation#updateObjectLayout",
			new ObjectValuePair<>(
				ObjectLayoutResourceImpl.class, "putObjectLayout"));
		_resourceMethodPairs.put(
			"mutation#updateObjectLayoutBatch",
			new ObjectValuePair<>(
				ObjectLayoutResourceImpl.class, "putObjectLayoutBatch"));
		_resourceMethodPairs.put(
			"mutation#createObjectDefinitionObjectRelationship",
			new ObjectValuePair<>(
				ObjectRelationshipResourceImpl.class,
				"postObjectDefinitionObjectRelationship"));
		_resourceMethodPairs.put(
			"mutation#createObjectDefinitionObjectRelationshipBatch",
			new ObjectValuePair<>(
				ObjectRelationshipResourceImpl.class,
				"postObjectDefinitionObjectRelationshipBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteObjectRelationship",
			new ObjectValuePair<>(
				ObjectRelationshipResourceImpl.class,
				"deleteObjectRelationship"));
		_resourceMethodPairs.put(
			"mutation#deleteObjectRelationshipBatch",
			new ObjectValuePair<>(
				ObjectRelationshipResourceImpl.class,
				"deleteObjectRelationshipBatch"));
		_resourceMethodPairs.put(
			"mutation#updateObjectRelationship",
			new ObjectValuePair<>(
				ObjectRelationshipResourceImpl.class, "putObjectRelationship"));
		_resourceMethodPairs.put(
			"mutation#updateObjectRelationshipBatch",
			new ObjectValuePair<>(
				ObjectRelationshipResourceImpl.class,
				"putObjectRelationshipBatch"));
		_resourceMethodPairs.put(
			"mutation#createObjectDefinitionObjectValidationRule",
			new ObjectValuePair<>(
				ObjectValidationRuleResourceImpl.class,
				"postObjectDefinitionObjectValidationRule"));
		_resourceMethodPairs.put(
			"mutation#createObjectDefinitionObjectValidationRuleBatch",
			new ObjectValuePair<>(
				ObjectValidationRuleResourceImpl.class,
				"postObjectDefinitionObjectValidationRuleBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteObjectValidationRule",
			new ObjectValuePair<>(
				ObjectValidationRuleResourceImpl.class,
				"deleteObjectValidationRule"));
		_resourceMethodPairs.put(
			"mutation#deleteObjectValidationRuleBatch",
			new ObjectValuePair<>(
				ObjectValidationRuleResourceImpl.class,
				"deleteObjectValidationRuleBatch"));
		_resourceMethodPairs.put(
			"mutation#patchObjectValidationRule",
			new ObjectValuePair<>(
				ObjectValidationRuleResourceImpl.class,
				"patchObjectValidationRule"));
		_resourceMethodPairs.put(
			"mutation#updateObjectValidationRule",
			new ObjectValuePair<>(
				ObjectValidationRuleResourceImpl.class,
				"putObjectValidationRule"));
		_resourceMethodPairs.put(
			"mutation#updateObjectValidationRuleBatch",
			new ObjectValuePair<>(
				ObjectValidationRuleResourceImpl.class,
				"putObjectValidationRuleBatch"));
		_resourceMethodPairs.put(
			"mutation#createObjectDefinitionObjectView",
			new ObjectValuePair<>(
				ObjectViewResourceImpl.class,
				"postObjectDefinitionObjectView"));
		_resourceMethodPairs.put(
			"mutation#createObjectDefinitionObjectViewBatch",
			new ObjectValuePair<>(
				ObjectViewResourceImpl.class,
				"postObjectDefinitionObjectViewBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteObjectView",
			new ObjectValuePair<>(
				ObjectViewResourceImpl.class, "deleteObjectView"));
		_resourceMethodPairs.put(
			"mutation#deleteObjectViewBatch",
			new ObjectValuePair<>(
				ObjectViewResourceImpl.class, "deleteObjectViewBatch"));
		_resourceMethodPairs.put(
			"mutation#updateObjectView",
			new ObjectValuePair<>(
				ObjectViewResourceImpl.class, "putObjectView"));
		_resourceMethodPairs.put(
			"mutation#updateObjectViewBatch",
			new ObjectValuePair<>(
				ObjectViewResourceImpl.class, "putObjectViewBatch"));
		_resourceMethodPairs.put(
			"mutation#createObjectViewCopy",
			new ObjectValuePair<>(
				ObjectViewResourceImpl.class, "postObjectViewCopy"));
		_resourceMethodPairs.put(
			"query#objectAction",
			new ObjectValuePair<>(
				ObjectActionResourceImpl.class, "getObjectAction"));
		_resourceMethodPairs.put(
			"query#objectDefinitionObjectActions",
			new ObjectValuePair<>(
				ObjectActionResourceImpl.class,
				"getObjectDefinitionObjectActionsPage"));
		_resourceMethodPairs.put(
			"query#objectDefinitions",
			new ObjectValuePair<>(
				ObjectDefinitionResourceImpl.class,
				"getObjectDefinitionsPage"));
		_resourceMethodPairs.put(
			"query#objectDefinitionByExternalReferenceCode",
			new ObjectValuePair<>(
				ObjectDefinitionResourceImpl.class,
				"getObjectDefinitionByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"query#objectDefinition",
			new ObjectValuePair<>(
				ObjectDefinitionResourceImpl.class, "getObjectDefinition"));
		_resourceMethodPairs.put(
			"query#objectDefinitionObjectFields",
			new ObjectValuePair<>(
				ObjectFieldResourceImpl.class,
				"getObjectDefinitionObjectFieldsPage"));
		_resourceMethodPairs.put(
			"query#objectField",
			new ObjectValuePair<>(
				ObjectFieldResourceImpl.class, "getObjectField"));
		_resourceMethodPairs.put(
			"query#objectDefinitionObjectLayouts",
			new ObjectValuePair<>(
				ObjectLayoutResourceImpl.class,
				"getObjectDefinitionObjectLayoutsPage"));
		_resourceMethodPairs.put(
			"query#objectLayout",
			new ObjectValuePair<>(
				ObjectLayoutResourceImpl.class, "getObjectLayout"));
		_resourceMethodPairs.put(
			"query#objectDefinitionObjectRelationships",
			new ObjectValuePair<>(
				ObjectRelationshipResourceImpl.class,
				"getObjectDefinitionObjectRelationshipsPage"));
		_resourceMethodPairs.put(
			"query#objectRelationship",
			new ObjectValuePair<>(
				ObjectRelationshipResourceImpl.class, "getObjectRelationship"));
		_resourceMethodPairs.put(
			"query#objectDefinitionObjectValidationRules",
			new ObjectValuePair<>(
				ObjectValidationRuleResourceImpl.class,
				"getObjectDefinitionObjectValidationRulesPage"));
		_resourceMethodPairs.put(
			"query#objectValidationRule",
			new ObjectValuePair<>(
				ObjectValidationRuleResourceImpl.class,
				"getObjectValidationRule"));
		_resourceMethodPairs.put(
			"query#objectDefinitionObjectViews",
			new ObjectValuePair<>(
				ObjectViewResourceImpl.class,
				"getObjectDefinitionObjectViewsPage"));
		_resourceMethodPairs.put(
			"query#objectView",
			new ObjectValuePair<>(
				ObjectViewResourceImpl.class, "getObjectView"));
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
	private ComponentServiceObjects<ObjectValidationRuleResource>
		_objectValidationRuleResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<ObjectViewResource>
		_objectViewResourceComponentServiceObjects;

}