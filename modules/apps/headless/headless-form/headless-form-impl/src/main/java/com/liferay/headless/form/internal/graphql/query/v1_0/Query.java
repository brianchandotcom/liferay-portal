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

import com.liferay.headless.form.internal.dto.v1_0.FormDocumentImpl;
import com.liferay.headless.form.internal.dto.v1_0.FormImpl;
import com.liferay.headless.form.internal.dto.v1_0.FormRecordImpl;
import com.liferay.headless.form.internal.dto.v1_0.FormStructureImpl;
import com.liferay.headless.form.internal.resource.v1_0.FormDocumentResourceImpl;
import com.liferay.headless.form.internal.resource.v1_0.FormRecordResourceImpl;
import com.liferay.headless.form.internal.resource.v1_0.FormResourceImpl;
import com.liferay.headless.form.internal.resource.v1_0.FormStructureResourceImpl;
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
	public Collection<FormImpl> getContentSpaceFormsPage(
	@GraphQLName("content-space-id") Long contentSpaceId,@GraphQLName("pageSize") int pageSize,@GraphQLName("page") int page)
			throws Exception {

				FormResourceImpl formResourceImpl = _getFormResourceImpl();

				formResourceImpl.setContextCompany(
					CompanyLocalServiceUtil.getCompany(CompanyThreadLocal.getCompanyId()));

				Page paginationPage = formResourceImpl.getContentSpaceFormsPage(
					contentSpaceId,Pagination.of(pageSize, page));

				return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public FormImpl getForm(
	@GraphQLName("form-id") Long formId)
			throws Exception {

				FormResourceImpl formResourceImpl = _getFormResourceImpl();

				formResourceImpl.setContextCompany(
					CompanyLocalServiceUtil.getCompany(CompanyThreadLocal.getCompanyId()));

				return (FormImpl)formResourceImpl.getForm(
					formId);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public FormImpl getFormFetchLatestDraft(
	@GraphQLName("form-id") Long formId)
			throws Exception {

				FormResourceImpl formResourceImpl = _getFormResourceImpl();

				formResourceImpl.setContextCompany(
					CompanyLocalServiceUtil.getCompany(CompanyThreadLocal.getCompanyId()));

				return (FormImpl)formResourceImpl.getFormFetchLatestDraft(
					formId);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public FormDocumentImpl getFormDocument(
	@GraphQLName("form-document-id") Long formDocumentId)
			throws Exception {

				FormDocumentResourceImpl formDocumentResourceImpl = _getFormDocumentResourceImpl();

				formDocumentResourceImpl.setContextCompany(
					CompanyLocalServiceUtil.getCompany(CompanyThreadLocal.getCompanyId()));

				return (FormDocumentImpl)formDocumentResourceImpl.getFormDocument(
					formDocumentId);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public FormRecordImpl getFormRecord(
	@GraphQLName("form-record-id") Long formRecordId)
			throws Exception {

				FormRecordResourceImpl formRecordResourceImpl = _getFormRecordResourceImpl();

				formRecordResourceImpl.setContextCompany(
					CompanyLocalServiceUtil.getCompany(CompanyThreadLocal.getCompanyId()));

				return (FormRecordImpl)formRecordResourceImpl.getFormRecord(
					formRecordId);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<FormRecordImpl> getFormFormRecordsPage(
	@GraphQLName("form-id") Long formId,@GraphQLName("pageSize") int pageSize,@GraphQLName("page") int page)
			throws Exception {

				FormRecordResourceImpl formRecordResourceImpl = _getFormRecordResourceImpl();

				formRecordResourceImpl.setContextCompany(
					CompanyLocalServiceUtil.getCompany(CompanyThreadLocal.getCompanyId()));

				Page paginationPage = formRecordResourceImpl.getFormFormRecordsPage(
					formId,Pagination.of(pageSize, page));

				return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<FormStructureImpl> getContentSpaceFormStructuresPage(
	@GraphQLName("content-space-id") Long contentSpaceId,@GraphQLName("pageSize") int pageSize,@GraphQLName("page") int page)
			throws Exception {

				FormStructureResourceImpl formStructureResourceImpl = _getFormStructureResourceImpl();

				formStructureResourceImpl.setContextCompany(
					CompanyLocalServiceUtil.getCompany(CompanyThreadLocal.getCompanyId()));

				Page paginationPage = formStructureResourceImpl.getContentSpaceFormStructuresPage(
					contentSpaceId,Pagination.of(pageSize, page));

				return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public FormStructureImpl getFormStructure(
	@GraphQLName("form-structure-id") Long formStructureId)
			throws Exception {

				FormStructureResourceImpl formStructureResourceImpl = _getFormStructureResourceImpl();

				formStructureResourceImpl.setContextCompany(
					CompanyLocalServiceUtil.getCompany(CompanyThreadLocal.getCompanyId()));

				return (FormStructureImpl)formStructureResourceImpl.getFormStructure(
					formStructureId);
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
	private static FormStructureResourceImpl _getFormStructureResourceImpl() {
			return new FormStructureResourceImpl();
	}

}