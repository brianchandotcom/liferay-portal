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

import com.liferay.headless.form.internal.dto.v1_0.FormImpl;
import com.liferay.headless.form.internal.dto.v1_0.FormRecordImpl;
import com.liferay.headless.form.internal.resource.v1_0.FormDocumentResourceImpl;
import com.liferay.headless.form.internal.resource.v1_0.FormRecordResourceImpl;
import com.liferay.headless.form.internal.resource.v1_0.FormResourceImpl;

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

	@GraphQLField
	@GraphQLInvokeDetached
	public FormImpl postFormEvaluateContext(
	@GraphQLName("form-id") Long formId,@GraphQLName("Form") FormImpl formImpl)
			throws Exception {

				return (FormImpl)_getFormResourceImpl().postFormEvaluateContext(
					formId,formImpl);
	}
	@GraphQLField
	@GraphQLInvokeDetached
	public FormImpl postFormUploadFile(
	@GraphQLName("form-id") Long formId,@GraphQLName("Form") FormImpl formImpl)
			throws Exception {

				return (FormImpl)_getFormResourceImpl().postFormUploadFile(
					formId,formImpl);
	}
	@GraphQLInvokeDetached
	public boolean deleteFormDocument(
	@GraphQLName("form-document-id") Long formDocumentId)
			throws Exception {

				return (boolean)_getFormDocumentResourceImpl().deleteFormDocument(
					formDocumentId);
	}
	@GraphQLInvokeDetached
	public FormRecordImpl putFormRecord(
	@GraphQLName("form-record-id") Long formRecordId,@GraphQLName("FormRecord") FormRecordImpl formRecordImpl)
			throws Exception {

				return (FormRecordImpl)_getFormRecordResourceImpl().putFormRecord(
					formRecordId,formRecordImpl);
	}
	@GraphQLField
	@GraphQLInvokeDetached
	public FormRecordImpl postFormFormRecord(
	@GraphQLName("form-id") Long formId,@GraphQLName("FormRecord") FormRecordImpl formRecordImpl)
			throws Exception {

				return (FormRecordImpl)_getFormRecordResourceImpl().postFormFormRecord(
					formId,formRecordImpl);
	}

	private static FormResourceImpl _getFormResourceImpl() {
			return new FormResourceImpl();
	}
	private static FormDocumentResourceImpl _getFormDocumentResourceImpl() {
			return new FormDocumentResourceImpl();
	}
	private static FormRecordResourceImpl _getFormRecordResourceImpl() {
			return new FormRecordResourceImpl();
	}

}