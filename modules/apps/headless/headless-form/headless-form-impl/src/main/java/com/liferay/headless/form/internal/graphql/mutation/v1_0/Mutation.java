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

package com.liferay.headless.form.internal.graphql.mutation.v1_0;

import com.liferay.headless.form.dto.v1_0.Form;
import com.liferay.headless.form.dto.v1_0.FormRecord;
import com.liferay.headless.form.internal.resource.v1_0.FormDocumentResourceImpl;
import com.liferay.headless.form.internal.resource.v1_0.FormRecordResourceImpl;
import com.liferay.headless.form.internal.resource.v1_0.FormResourceImpl;
import com.liferay.headless.form.resource.v1_0.FormDocumentResource;
import com.liferay.headless.form.resource.v1_0.FormRecordResource;
import com.liferay.headless.form.resource.v1_0.FormResource;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLInvokeDetached;
import graphql.annotations.annotationTypes.GraphQLName;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class Mutation {

	@GraphQLInvokeDetached
	public boolean deleteFormDocument(
			@GraphQLName("form-document-id") Long formDocumentId)
		throws Exception {

		FormDocumentResource formDocumentResource =
			_createFormDocumentResource();

		return formDocumentResource.deleteFormDocument(formDocumentId);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Form postFormEvaluateContext(
			@GraphQLName("form-id") Long formId, @GraphQLName("Form") Form form)
		throws Exception {

		FormResource formResource = _createFormResource();

		return formResource.postFormEvaluateContext(formId, form);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public FormRecord postFormFormRecord(
			@GraphQLName("form-id") Long formId,
			@GraphQLName("FormRecord") FormRecord formRecord)
		throws Exception {

		FormRecordResource formRecordResource = _createFormRecordResource();

		return formRecordResource.postFormFormRecord(formId, formRecord);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Form postFormUploadFile(
			@GraphQLName("form-id") Long formId, @GraphQLName("Form") Form form)
		throws Exception {

		FormResource formResource = _createFormResource();

		return formResource.postFormUploadFile(formId, form);
	}

	@GraphQLInvokeDetached
	public FormRecord putFormRecord(
			@GraphQLName("form-record-id") Long formRecordId,
			@GraphQLName("FormRecord") FormRecord formRecord)
		throws Exception {

		FormRecordResource formRecordResource = _createFormRecordResource();

		return formRecordResource.putFormRecord(formRecordId, formRecord);
	}

	private static FormDocumentResource _createFormDocumentResource() {
		return new FormDocumentResourceImpl();
	}

	private static FormRecordResource _createFormRecordResource() {
		return new FormRecordResourceImpl();
	}

	private static FormResource _createFormResource() {
		return new FormResourceImpl();
	}

}