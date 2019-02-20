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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class Query {

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<Form> getContentSpaceFormsPage(
	@GraphQLName("content-space-id") Long contentSpaceId,@GraphQLName("pageSize") int pageSize,@GraphQLName("page") int page)
			throws Exception {

				FormResource formResource = _getFormResource();

				formResource.setContextCompany(
					CompanyLocalServiceUtil.getCompany(CompanyThreadLocal.getCompanyId()));

				Page paginationPage = formResource.getContentSpaceFormsPage(
					contentSpaceId,Pagination.of(pageSize, page));

				return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Form getForm(
	@GraphQLName("form-id") Long formId)
			throws Exception {

				FormResource formResource = _getFormResource();

				formResource.setContextCompany(
					CompanyLocalServiceUtil.getCompany(CompanyThreadLocal.getCompanyId()));

				return formResource.getForm(
					formId);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Form getFormFetchLatestDraft(
	@GraphQLName("form-id") Long formId)
			throws Exception {

				FormResource formResource = _getFormResource();

				formResource.setContextCompany(
					CompanyLocalServiceUtil.getCompany(CompanyThreadLocal.getCompanyId()));

				return formResource.getFormFetchLatestDraft(
					formId);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public FormDocument getFormDocument(
	@GraphQLName("form-document-id") Long formDocumentId)
			throws Exception {

				FormDocumentResource formDocumentResource = _getFormDocumentResource();

				formDocumentResource.setContextCompany(
					CompanyLocalServiceUtil.getCompany(CompanyThreadLocal.getCompanyId()));

				return formDocumentResource.getFormDocument(
					formDocumentId);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public FormRecord getFormRecord(
	@GraphQLName("form-record-id") Long formRecordId)
			throws Exception {

				FormRecordResource formRecordResource = _getFormRecordResource();

				formRecordResource.setContextCompany(
					CompanyLocalServiceUtil.getCompany(CompanyThreadLocal.getCompanyId()));

				return formRecordResource.getFormRecord(
					formRecordId);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<FormRecord> getFormFormRecordsPage(
	@GraphQLName("form-id") Long formId,@GraphQLName("pageSize") int pageSize,@GraphQLName("page") int page)
			throws Exception {

				FormRecordResource formRecordResource = _getFormRecordResource();

				formRecordResource.setContextCompany(
					CompanyLocalServiceUtil.getCompany(CompanyThreadLocal.getCompanyId()));

				Page paginationPage = formRecordResource.getFormFormRecordsPage(
					formId,Pagination.of(pageSize, page));

				return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<FormStructure> getContentSpaceFormStructuresPage(
	@GraphQLName("content-space-id") Long contentSpaceId,@GraphQLName("pageSize") int pageSize,@GraphQLName("page") int page)
			throws Exception {

				FormStructureResource formStructureResource = _getFormStructureResource();

				formStructureResource.setContextCompany(
					CompanyLocalServiceUtil.getCompany(CompanyThreadLocal.getCompanyId()));

				Page paginationPage = formStructureResource.getContentSpaceFormStructuresPage(
					contentSpaceId,Pagination.of(pageSize, page));

				return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public FormStructure getFormStructure(
	@GraphQLName("form-structure-id") Long formStructureId)
			throws Exception {

				FormStructureResource formStructureResource = _getFormStructureResource();

				formStructureResource.setContextCompany(
					CompanyLocalServiceUtil.getCompany(CompanyThreadLocal.getCompanyId()));

				return formStructureResource.getFormStructure(
					formStructureId);
	}

	private static FormResource _getFormResource() {
			return _formResourceServiceTracker.getService();
	}

	private static final ServiceTracker<FormResource, FormResource>
			_formResourceServiceTracker;
	private static FormDocumentResource _getFormDocumentResource() {
			return _formDocumentResourceServiceTracker.getService();
	}

	private static final ServiceTracker<FormDocumentResource, FormDocumentResource>
			_formDocumentResourceServiceTracker;
	private static FormRecordResource _getFormRecordResource() {
			return _formRecordResourceServiceTracker.getService();
	}

	private static final ServiceTracker<FormRecordResource, FormRecordResource>
			_formRecordResourceServiceTracker;
	private static FormStructureResource _getFormStructureResource() {
			return _formStructureResourceServiceTracker.getService();
	}

	private static final ServiceTracker<FormStructureResource, FormStructureResource>
			_formStructureResourceServiceTracker;

		static {
			Bundle bundle = FrameworkUtil.getBundle(Query.class);

				ServiceTracker<FormResource, FormResource>
					formResourceServiceTracker =
						new ServiceTracker<>(
							bundle.getBundleContext(),
							FormResource.class, null);

				formResourceServiceTracker.open();

				_formResourceServiceTracker =
					formResourceServiceTracker;
				ServiceTracker<FormDocumentResource, FormDocumentResource>
					formDocumentResourceServiceTracker =
						new ServiceTracker<>(
							bundle.getBundleContext(),
							FormDocumentResource.class, null);

				formDocumentResourceServiceTracker.open();

				_formDocumentResourceServiceTracker =
					formDocumentResourceServiceTracker;
				ServiceTracker<FormRecordResource, FormRecordResource>
					formRecordResourceServiceTracker =
						new ServiceTracker<>(
							bundle.getBundleContext(),
							FormRecordResource.class, null);

				formRecordResourceServiceTracker.open();

				_formRecordResourceServiceTracker =
					formRecordResourceServiceTracker;
				ServiceTracker<FormStructureResource, FormStructureResource>
					formStructureResourceServiceTracker =
						new ServiceTracker<>(
							bundle.getBundleContext(),
							FormStructureResource.class, null);

				formStructureResourceServiceTracker.open();

				_formStructureResourceServiceTracker =
					formStructureResourceServiceTracker;
	}

}