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

package com.liferay.headless.form.internal.graphql.query.v1_0;

import com.liferay.headless.form.dto.v1_0.Form;
import com.liferay.headless.form.dto.v1_0.FormDocument;
import com.liferay.headless.form.dto.v1_0.FormRecord;
import com.liferay.headless.form.dto.v1_0.FormStructure;
import com.liferay.headless.form.internal.resource.v1_0.FormDocumentResourceImpl;
import com.liferay.headless.form.internal.resource.v1_0.FormRecordResourceImpl;
import com.liferay.headless.form.internal.resource.v1_0.FormResourceImpl;
import com.liferay.headless.form.internal.resource.v1_0.FormStructureResourceImpl;
import com.liferay.headless.form.resource.v1_0.FormDocumentResource;
import com.liferay.headless.form.resource.v1_0.FormRecordResource;
import com.liferay.headless.form.resource.v1_0.FormResource;
import com.liferay.headless.form.resource.v1_0.FormStructureResource;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLInvokeDetached;
import graphql.annotations.annotationTypes.GraphQLName;

import java.util.Collection;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class Query {

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<Form> getContentSpaceFormsPage(
			@GraphQLName("content-space-id") Long contentSpaceId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		FormResource formResource = _createFormResource();

		formResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		Page paginationPage = formResource.getContentSpaceFormsPage(
			contentSpaceId, Pagination.of(pageSize, page));

		return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<FormStructure> getContentSpaceFormStructuresPage(
			@GraphQLName("content-space-id") Long contentSpaceId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		FormStructureResource formStructureResource =
			_createFormStructureResource();

		formStructureResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		Page paginationPage =
			formStructureResource.getContentSpaceFormStructuresPage(
				contentSpaceId, Pagination.of(pageSize, page));

		return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Form getForm(@GraphQLName("form-id") Long formId) throws Exception {
		FormResource formResource = _createFormResource();

		formResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		return formResource.getForm(formId);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public FormDocument getFormDocument(
			@GraphQLName("form-document-id") Long formDocumentId)
		throws Exception {

		FormDocumentResource formDocumentResource =
			_createFormDocumentResource();

		formDocumentResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		return formDocumentResource.getFormDocument(formDocumentId);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Form getFormFetchLatestDraft(@GraphQLName("form-id") Long formId)
		throws Exception {

		FormResource formResource = _createFormResource();

		formResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		return formResource.getFormFetchLatestDraft(formId);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<FormRecord> getFormFormRecordsPage(
			@GraphQLName("form-id") Long formId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		FormRecordResource formRecordResource = _createFormRecordResource();

		formRecordResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		Page paginationPage = formRecordResource.getFormFormRecordsPage(
			formId, Pagination.of(pageSize, page));

		return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public FormRecord getFormRecord(
			@GraphQLName("form-record-id") Long formRecordId)
		throws Exception {

		FormRecordResource formRecordResource = _createFormRecordResource();

		formRecordResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		return formRecordResource.getFormRecord(formRecordId);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public FormStructure getFormStructure(
			@GraphQLName("form-structure-id") Long formStructureId)
		throws Exception {

		FormStructureResource formStructureResource =
			_createFormStructureResource();

		formStructureResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		return formStructureResource.getFormStructure(formStructureId);
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

	private static FormStructureResource _createFormStructureResource() {
		return new FormStructureResourceImpl();
	}

}