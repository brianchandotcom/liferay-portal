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

package com.liferay.headless.form.internal.graphql.servlet.v1_0;

import com.liferay.headless.form.internal.graphql.mutation.v1_0.Mutation;
import com.liferay.headless.form.internal.graphql.query.v1_0.Query;
import com.liferay.headless.form.internal.resource.v1_0.FormDocumentResourceImpl;
import com.liferay.headless.form.internal.resource.v1_0.FormRecordResourceImpl;
import com.liferay.headless.form.internal.resource.v1_0.FormResourceImpl;
import com.liferay.headless.form.internal.resource.v1_0.FormStructureResourceImpl;
import com.liferay.headless.form.resource.v1_0.FormDocumentResource;
import com.liferay.headless.form.resource.v1_0.FormRecordResource;
import com.liferay.headless.form.resource.v1_0.FormResource;
import com.liferay.headless.form.resource.v1_0.FormStructureResource;
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
		Mutation.setFormResourceComponentServiceObjects(
			_formResourceComponentServiceObjects);
		Mutation.setFormDocumentResourceComponentServiceObjects(
			_formDocumentResourceComponentServiceObjects);
		Mutation.setFormRecordResourceComponentServiceObjects(
			_formRecordResourceComponentServiceObjects);

		Query.setFormResourceComponentServiceObjects(
			_formResourceComponentServiceObjects);
		Query.setFormDocumentResourceComponentServiceObjects(
			_formDocumentResourceComponentServiceObjects);
		Query.setFormRecordResourceComponentServiceObjects(
			_formRecordResourceComponentServiceObjects);
		Query.setFormStructureResourceComponentServiceObjects(
			_formStructureResourceComponentServiceObjects);
	}

	public String getApplicationName() {
		return "Liferay.Headless.Form";
	}

	@Override
	public Mutation getMutation() {
		return new Mutation();
	}

	@Override
	public String getPath() {
		return "/headless-form-graphql/v1_0";
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
			"mutation#createFormEvaluateContext",
			new ObjectValuePair<>(
				FormResourceImpl.class, "postFormEvaluateContext"));
		_resourceMethodPairs.put(
			"mutation#createFormFormDocument",
			new ObjectValuePair<>(
				FormResourceImpl.class, "postFormFormDocument"));
		_resourceMethodPairs.put(
			"mutation#deleteFormDocument",
			new ObjectValuePair<>(
				FormDocumentResourceImpl.class, "deleteFormDocument"));
		_resourceMethodPairs.put(
			"mutation#deleteFormDocumentBatch",
			new ObjectValuePair<>(
				FormDocumentResourceImpl.class, "deleteFormDocumentBatch"));
		_resourceMethodPairs.put(
			"mutation#updateFormRecord",
			new ObjectValuePair<>(
				FormRecordResourceImpl.class, "putFormRecord"));
		_resourceMethodPairs.put(
			"mutation#updateFormRecordBatch",
			new ObjectValuePair<>(
				FormRecordResourceImpl.class, "putFormRecordBatch"));
		_resourceMethodPairs.put(
			"mutation#createFormFormRecord",
			new ObjectValuePair<>(
				FormRecordResourceImpl.class, "postFormFormRecord"));
		_resourceMethodPairs.put(
			"mutation#createFormFormRecordBatch",
			new ObjectValuePair<>(
				FormRecordResourceImpl.class, "postFormFormRecordBatch"));
		_resourceMethodPairs.put(
			"query#form",
			new ObjectValuePair<>(FormResourceImpl.class, "getForm"));
		_resourceMethodPairs.put(
			"query#forms",
			new ObjectValuePair<>(FormResourceImpl.class, "getSiteFormsPage"));
		_resourceMethodPairs.put(
			"query#formDocument",
			new ObjectValuePair<>(
				FormDocumentResourceImpl.class, "getFormDocument"));
		_resourceMethodPairs.put(
			"query#formRecord",
			new ObjectValuePair<>(
				FormRecordResourceImpl.class, "getFormRecord"));
		_resourceMethodPairs.put(
			"query#formFormRecords",
			new ObjectValuePair<>(
				FormRecordResourceImpl.class, "getFormFormRecordsPage"));
		_resourceMethodPairs.put(
			"query#formFormRecordByLatestDraft",
			new ObjectValuePair<>(
				FormRecordResourceImpl.class,
				"getFormFormRecordByLatestDraft"));
		_resourceMethodPairs.put(
			"query#formStructure",
			new ObjectValuePair<>(
				FormStructureResourceImpl.class, "getFormStructure"));
		_resourceMethodPairs.put(
			"query#formStructures",
			new ObjectValuePair<>(
				FormStructureResourceImpl.class, "getSiteFormStructuresPage"));
	}

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<FormResource>
		_formResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<FormDocumentResource>
		_formDocumentResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<FormRecordResource>
		_formRecordResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<FormStructureResource>
		_formStructureResourceComponentServiceObjects;

}