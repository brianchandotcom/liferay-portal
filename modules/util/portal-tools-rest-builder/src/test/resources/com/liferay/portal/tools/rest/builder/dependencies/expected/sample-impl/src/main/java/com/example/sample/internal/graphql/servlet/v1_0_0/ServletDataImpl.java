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

package com.example.sample.internal.graphql.servlet.v1_0_0;

import com.example.sample.internal.graphql.mutation.v1_0_0.Mutation;
import com.example.sample.internal.graphql.query.v1_0_0.Query;
import com.example.sample.internal.resource.v1_0_0.DocumentResourceImpl;
import com.example.sample.internal.resource.v1_0_0.FolderResourceImpl;
import com.example.sample.internal.resource.v1_0_0.TestResourceImpl;
import com.example.sample.resource.v1_0_0.DocumentResource;
import com.example.sample.resource.v1_0_0.FolderResource;
import com.example.sample.resource.v1_0_0.TestResource;

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
 * @author John Doe
 * @generated
 */
@Component(service = ServletData.class)
@Generated("")
public class ServletDataImpl implements ServletData {

	@Activate
	public void activate(BundleContext bundleContext) {
		Query.setDocumentResourceComponentServiceObjects(
			_documentResourceComponentServiceObjects);
		Query.setFolderResourceComponentServiceObjects(
			_folderResourceComponentServiceObjects);
		Query.setTestResourceComponentServiceObjects(
			_testResourceComponentServiceObjects);
	}

	public String getApplicationName() {
		return "sample-application";
	}

	@Override
	public Mutation getMutation() {
		return new Mutation();
	}

	@Override
	public String getPath() {
		return "/sample-graphql/v1_0_0";
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
						"query#document",
						new ObjectValuePair<>(
							DocumentResourceImpl.class, "getDocument"));
					put(
						"query#folder",
						new ObjectValuePair<>(
							FolderResourceImpl.class, "getFolderPage"));
					put(
						"query#folder",
						new ObjectValuePair<>(
							FolderResourceImpl.class, "getFolder"));
					put(
						"query#test",
						new ObjectValuePair<>(
							TestResourceImpl.class, "getTest"));

					put(
						"query#Document.folder",
						new ObjectValuePair<>(
							FolderResourceImpl.class, "getFolder"));
				}
			};

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<DocumentResource>
		_documentResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<FolderResource>
		_folderResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<TestResource>
		_testResourceComponentServiceObjects;

}