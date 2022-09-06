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

package com.liferay.headless.admin.list.type.internal.graphql.servlet.v1_0;

import com.liferay.headless.admin.list.type.internal.graphql.mutation.v1_0.Mutation;
import com.liferay.headless.admin.list.type.internal.graphql.query.v1_0.Query;
import com.liferay.headless.admin.list.type.internal.resource.v1_0.ListTypeDefinitionResourceImpl;
import com.liferay.headless.admin.list.type.internal.resource.v1_0.ListTypeEntryResourceImpl;
import com.liferay.headless.admin.list.type.resource.v1_0.ListTypeDefinitionResource;
import com.liferay.headless.admin.list.type.resource.v1_0.ListTypeEntryResource;
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
 * @author Gabriel Albuquerque
 * @generated
 */
@Component(service = ServletData.class)
@Generated("")
public class ServletDataImpl implements ServletData {

	@Activate
	public void activate(BundleContext bundleContext) {
		Mutation.setListTypeDefinitionResourceComponentServiceObjects(
			_listTypeDefinitionResourceComponentServiceObjects);
		Mutation.setListTypeEntryResourceComponentServiceObjects(
			_listTypeEntryResourceComponentServiceObjects);

		Query.setListTypeDefinitionResourceComponentServiceObjects(
			_listTypeDefinitionResourceComponentServiceObjects);
		Query.setListTypeEntryResourceComponentServiceObjects(
			_listTypeEntryResourceComponentServiceObjects);
	}

	public String getApplicationName() {
		return "Liferay.Headless.Admin.List.Type";
	}

	@Override
	public Mutation getMutation() {
		return new Mutation();
	}

	@Override
	public String getPath() {
		return "/headless-admin-list-type-graphql/v1_0";
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
			"mutation#createListTypeDefinition",
			new ObjectValuePair<>(
				ListTypeDefinitionResourceImpl.class,
				"postListTypeDefinition"));
		_resourceMethodPairs.put(
			"mutation#createListTypeDefinitionBatch",
			new ObjectValuePair<>(
				ListTypeDefinitionResourceImpl.class,
				"postListTypeDefinitionBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteListTypeDefinition",
			new ObjectValuePair<>(
				ListTypeDefinitionResourceImpl.class,
				"deleteListTypeDefinition"));
		_resourceMethodPairs.put(
			"mutation#deleteListTypeDefinitionBatch",
			new ObjectValuePair<>(
				ListTypeDefinitionResourceImpl.class,
				"deleteListTypeDefinitionBatch"));
		_resourceMethodPairs.put(
			"mutation#patchListTypeDefinition",
			new ObjectValuePair<>(
				ListTypeDefinitionResourceImpl.class,
				"patchListTypeDefinition"));
		_resourceMethodPairs.put(
			"mutation#updateListTypeDefinition",
			new ObjectValuePair<>(
				ListTypeDefinitionResourceImpl.class, "putListTypeDefinition"));
		_resourceMethodPairs.put(
			"mutation#updateListTypeDefinitionBatch",
			new ObjectValuePair<>(
				ListTypeDefinitionResourceImpl.class,
				"putListTypeDefinitionBatch"));
		_resourceMethodPairs.put(
			"mutation#createListTypeDefinitionListTypeEntry",
			new ObjectValuePair<>(
				ListTypeEntryResourceImpl.class,
				"postListTypeDefinitionListTypeEntry"));
		_resourceMethodPairs.put(
			"mutation#createListTypeDefinitionListTypeEntryBatch",
			new ObjectValuePair<>(
				ListTypeEntryResourceImpl.class,
				"postListTypeDefinitionListTypeEntryBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteListTypeEntry",
			new ObjectValuePair<>(
				ListTypeEntryResourceImpl.class, "deleteListTypeEntry"));
		_resourceMethodPairs.put(
			"mutation#deleteListTypeEntryBatch",
			new ObjectValuePair<>(
				ListTypeEntryResourceImpl.class, "deleteListTypeEntryBatch"));
		_resourceMethodPairs.put(
			"mutation#updateListTypeEntry",
			new ObjectValuePair<>(
				ListTypeEntryResourceImpl.class, "putListTypeEntry"));
		_resourceMethodPairs.put(
			"mutation#updateListTypeEntryBatch",
			new ObjectValuePair<>(
				ListTypeEntryResourceImpl.class, "putListTypeEntryBatch"));
		_resourceMethodPairs.put(
			"query#listTypeDefinitions",
			new ObjectValuePair<>(
				ListTypeDefinitionResourceImpl.class,
				"getListTypeDefinitionsPage"));
		_resourceMethodPairs.put(
			"query#listTypeDefinition",
			new ObjectValuePair<>(
				ListTypeDefinitionResourceImpl.class, "getListTypeDefinition"));
		_resourceMethodPairs.put(
			"query#listTypeDefinitionListTypeEntries",
			new ObjectValuePair<>(
				ListTypeEntryResourceImpl.class,
				"getListTypeDefinitionListTypeEntriesPage"));
		_resourceMethodPairs.put(
			"query#listTypeEntry",
			new ObjectValuePair<>(
				ListTypeEntryResourceImpl.class, "getListTypeEntry"));
	}

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<ListTypeDefinitionResource>
		_listTypeDefinitionResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<ListTypeEntryResource>
		_listTypeEntryResourceComponentServiceObjects;

}