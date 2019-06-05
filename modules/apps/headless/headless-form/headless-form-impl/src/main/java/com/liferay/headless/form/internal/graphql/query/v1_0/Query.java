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
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLInvokeDetached;
import graphql.annotations.annotationTypes.GraphQLName;

import javax.annotation.Generated;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class Query {

	public static void setFormResourceComponentServiceObjects(
		ComponentServiceObjects<FormResource>
			formResourceComponentServiceObjects) {

		_formResourceComponentServiceObjects =
			formResourceComponentServiceObjects;
	}

	public static void setFormDocumentResourceComponentServiceObjects(
		ComponentServiceObjects<FormDocumentResource>
			formDocumentResourceComponentServiceObjects) {

		_formDocumentResourceComponentServiceObjects =
			formDocumentResourceComponentServiceObjects;
	}

	public static void setFormRecordResourceComponentServiceObjects(
		ComponentServiceObjects<FormRecordResource>
			formRecordResourceComponentServiceObjects) {

		_formRecordResourceComponentServiceObjects =
			formRecordResourceComponentServiceObjects;
	}

	public static void setFormStructureResourceComponentServiceObjects(
		ComponentServiceObjects<FormStructureResource>
			formStructureResourceComponentServiceObjects) {

		_formStructureResourceComponentServiceObjects =
			formStructureResourceComponentServiceObjects;
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Form getForm(@GraphQLName("formId") Long formId) throws Exception {
		return _applyComponentServiceObjects(
			_formResourceComponentServiceObjects,
			this::_populateResourceContext,
			formResource -> formResource.getForm(formId));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public java.util.Collection<Form> getSiteFormsPage(
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_formResourceComponentServiceObjects,
			this::_populateResourceContext,
			formResource -> {
				Page paginationPage = formResource.getSiteFormsPage(
					siteId, Pagination.of(pageSize, page));

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public FormDocument getFormDocument(
			@GraphQLName("formDocumentId") Long formDocumentId)
		throws Exception {

		return _applyComponentServiceObjects(
			_formDocumentResourceComponentServiceObjects,
			this::_populateResourceContext,
			formDocumentResource -> formDocumentResource.getFormDocument(
				formDocumentId));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public FormRecord getFormRecord(
			@GraphQLName("formRecordId") Long formRecordId)
		throws Exception {

		return _applyComponentServiceObjects(
			_formRecordResourceComponentServiceObjects,
			this::_populateResourceContext,
			formRecordResource -> formRecordResource.getFormRecord(
				formRecordId));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public java.util.Collection<FormRecord> getFormFormRecordsPage(
			@GraphQLName("formId") Long formId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_formRecordResourceComponentServiceObjects,
			this::_populateResourceContext,
			formRecordResource -> {
				Page paginationPage = formRecordResource.getFormFormRecordsPage(
					formId, Pagination.of(pageSize, page));

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public FormRecord getFormFormRecordByLatestDraft(
			@GraphQLName("formId") Long formId)
		throws Exception {

		return _applyComponentServiceObjects(
			_formRecordResourceComponentServiceObjects,
			this::_populateResourceContext,
			formRecordResource ->
				formRecordResource.getFormFormRecordByLatestDraft(formId));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public FormStructure getFormStructure(
			@GraphQLName("formStructureId") Long formStructureId)
		throws Exception {

		return _applyComponentServiceObjects(
			_formStructureResourceComponentServiceObjects,
			this::_populateResourceContext,
			formStructureResource -> formStructureResource.getFormStructure(
				formStructureId));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public java.util.Collection<FormStructure> getSiteFormStructuresPage(
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_formStructureResourceComponentServiceObjects,
			this::_populateResourceContext,
			formStructureResource -> {
				Page paginationPage =
					formStructureResource.getSiteFormStructuresPage(
						siteId, Pagination.of(pageSize, page));

				return paginationPage.getItems();
			});
	}

	private <T, R, E1 extends Throwable, E2 extends Throwable> R
			_applyComponentServiceObjects(
				ComponentServiceObjects<T> componentServiceObjects,
				UnsafeConsumer<T, E1> unsafeConsumer,
				UnsafeFunction<T, R, E2> unsafeFunction)
		throws E1, E2 {

		T resource = componentServiceObjects.getService();

		try {
			unsafeConsumer.accept(resource);

			return unsafeFunction.apply(resource);
		}
		finally {
			componentServiceObjects.ungetService(resource);
		}
	}

	private void _populateResourceContext(FormResource formResource)
		throws Exception {

		formResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	private void _populateResourceContext(
			FormDocumentResource formDocumentResource)
		throws Exception {

		formDocumentResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	private void _populateResourceContext(FormRecordResource formRecordResource)
		throws Exception {

		formRecordResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	private void _populateResourceContext(
			FormStructureResource formStructureResource)
		throws Exception {

		formStructureResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	private static ComponentServiceObjects<FormResource>
		_formResourceComponentServiceObjects;
	private static ComponentServiceObjects<FormDocumentResource>
		_formDocumentResourceComponentServiceObjects;
	private static ComponentServiceObjects<FormRecordResource>
		_formRecordResourceComponentServiceObjects;
	private static ComponentServiceObjects<FormStructureResource>
		_formStructureResourceComponentServiceObjects;

}