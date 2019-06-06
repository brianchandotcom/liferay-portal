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
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLInvokeDetached;
import graphql.annotations.annotationTypes.GraphQLName;

import graphql.schema.DataFetchingEnvironment;

import java.util.Collection;
import java.util.function.Function;

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
	public Form getForm(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("formId") Long formId)
		throws Exception {

		return _applyComponentServiceObjects(
			_formResourceComponentServiceObjects,
			formResource -> _populateResourceContext(
				dataFetchingEnvironment, formResource),
			formResource -> formResource.getForm(formId));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<Form> getSiteFormsPage(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_formResourceComponentServiceObjects,
			formResource -> _populateResourceContext(
				dataFetchingEnvironment, formResource),
			formResource -> {
				Page paginationPage = formResource.getSiteFormsPage(
					siteId, Pagination.of(pageSize, page));

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public FormDocument getFormDocument(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("formDocumentId") Long formDocumentId)
		throws Exception {

		return _applyComponentServiceObjects(
			_formDocumentResourceComponentServiceObjects,
			formDocumentResource -> _populateResourceContext(
				dataFetchingEnvironment, formDocumentResource),
			formDocumentResource -> formDocumentResource.getFormDocument(
				formDocumentId));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public FormRecord getFormRecord(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("formRecordId") Long formRecordId)
		throws Exception {

		return _applyComponentServiceObjects(
			_formRecordResourceComponentServiceObjects,
			formRecordResource -> _populateResourceContext(
				dataFetchingEnvironment, formRecordResource),
			formRecordResource -> formRecordResource.getFormRecord(
				formRecordId));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<FormRecord> getFormFormRecordsPage(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("formId") Long formId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_formRecordResourceComponentServiceObjects,
			formRecordResource -> _populateResourceContext(
				dataFetchingEnvironment, formRecordResource),
			formRecordResource -> {
				Page paginationPage = formRecordResource.getFormFormRecordsPage(
					formId, Pagination.of(pageSize, page));

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public FormRecord getFormFormRecordByLatestDraft(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("formId") Long formId)
		throws Exception {

		return _applyComponentServiceObjects(
			_formRecordResourceComponentServiceObjects,
			formRecordResource -> _populateResourceContext(
				dataFetchingEnvironment, formRecordResource),
			formRecordResource ->
				formRecordResource.getFormFormRecordByLatestDraft(formId));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public FormStructure getFormStructure(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("formStructureId") Long formStructureId)
		throws Exception {

		return _applyComponentServiceObjects(
			_formStructureResourceComponentServiceObjects,
			formStructureResource -> _populateResourceContext(
				dataFetchingEnvironment, formStructureResource),
			formStructureResource -> formStructureResource.getFormStructure(
				formStructureId));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<FormStructure> getSiteFormStructuresPage(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_formStructureResourceComponentServiceObjects,
			formStructureResource -> _populateResourceContext(
				dataFetchingEnvironment, formStructureResource),
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

	private void _populateResourceContext(
			DataFetchingEnvironment dataFetchingEnvironment,
			FormResource formResource)
		throws Exception {

		formResource.setContextAcceptLanguage(
			_acceptLanguageFunction.apply(
				dataFetchingEnvironment.getContext()));

		formResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	private void _populateResourceContext(
			DataFetchingEnvironment dataFetchingEnvironment,
			FormDocumentResource formDocumentResource)
		throws Exception {

		formDocumentResource.setContextAcceptLanguage(
			_acceptLanguageFunction.apply(
				dataFetchingEnvironment.getContext()));

		formDocumentResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	private void _populateResourceContext(
			DataFetchingEnvironment dataFetchingEnvironment,
			FormRecordResource formRecordResource)
		throws Exception {

		formRecordResource.setContextAcceptLanguage(
			_acceptLanguageFunction.apply(
				dataFetchingEnvironment.getContext()));

		formRecordResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	private void _populateResourceContext(
			DataFetchingEnvironment dataFetchingEnvironment,
			FormStructureResource formStructureResource)
		throws Exception {

		formStructureResource.setContextAcceptLanguage(
			_acceptLanguageFunction.apply(
				dataFetchingEnvironment.getContext()));

		formStructureResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	public static void setAcceptLanguageFunction(
		Function<Object, AcceptLanguage> acceptLanguageFunction) {

		_acceptLanguageFunction = acceptLanguageFunction;
	}

	private static Function<Object, AcceptLanguage> _acceptLanguageFunction;
	private static ComponentServiceObjects<FormResource>
		_formResourceComponentServiceObjects;
	private static ComponentServiceObjects<FormDocumentResource>
		_formDocumentResourceComponentServiceObjects;
	private static ComponentServiceObjects<FormRecordResource>
		_formRecordResourceComponentServiceObjects;
	private static ComponentServiceObjects<FormStructureResource>
		_formStructureResourceComponentServiceObjects;

}