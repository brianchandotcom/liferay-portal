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
import com.liferay.headless.form.dto.v1_0.FormPage;
import com.liferay.headless.form.dto.v1_0.FormRecord;
import com.liferay.headless.form.dto.v1_0.FormStructure;
import com.liferay.headless.form.resource.v1_0.FormDocumentResource;
import com.liferay.headless.form.resource.v1_0.FormRecordResource;
import com.liferay.headless.form.resource.v1_0.FormResource;
import com.liferay.headless.form.resource.v1_0.FormStructureResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.List;
import java.util.function.BiFunction;

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
	public Form getForm(@GraphQLName("formId") Long formId) throws Exception {
		return _applyComponentServiceObjects(
			_formResourceComponentServiceObjects,
			this::_populateResourceContext,
			formResource -> formResource.getForm(formId));
	}

	@GraphQLField
	public FormPage getSiteFormsPage(
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_formResourceComponentServiceObjects,
			this::_populateResourceContext,
			formResource -> new FormPage(
				formResource.getSiteFormsPage(
					siteId, Pagination.of(page, pageSize))));
	}

	@GraphQLField
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
	public FormRecordPage getFormFormRecordsPage(
			@GraphQLName("formId") Long formId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_formRecordResourceComponentServiceObjects,
			this::_populateResourceContext,
			formRecordResource -> new FormRecordPage(
				formRecordResource.getFormFormRecordsPage(
					formId, Pagination.of(page, pageSize))));
	}

	@GraphQLField
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
	public FormStructurePage getSiteFormStructuresPage(
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_formStructureResourceComponentServiceObjects,
			this::_populateResourceContext,
			formStructureResource -> new FormStructurePage(
				formStructureResource.getSiteFormStructuresPage(
					siteId, Pagination.of(page, pageSize))));
	}

	@GraphQLName("FormPage")
	public class FormPage {

		public FormPage(Page formPage) {
			items = formPage.getItems();
			page = formPage.getPage();
			pageSize = formPage.getPageSize();
			totalCount = formPage.getTotalCount();
		}

		@GraphQLField
		protected java.util.Collection<Form> items;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("FormDocumentPage")
	public class FormDocumentPage {

		public FormDocumentPage(Page formDocumentPage) {
			items = formDocumentPage.getItems();
			page = formDocumentPage.getPage();
			pageSize = formDocumentPage.getPageSize();
			totalCount = formDocumentPage.getTotalCount();
		}

		@GraphQLField
		protected java.util.Collection<FormDocument> items;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("FormRecordPage")
	public class FormRecordPage {

		public FormRecordPage(Page formRecordPage) {
			items = formRecordPage.getItems();
			page = formRecordPage.getPage();
			pageSize = formRecordPage.getPageSize();
			totalCount = formRecordPage.getTotalCount();
		}

		@GraphQLField
		protected java.util.Collection<FormRecord> items;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("FormStructurePage")
	public class FormStructurePage {

		public FormStructurePage(Page formStructurePage) {
			items = formStructurePage.getItems();
			page = formStructurePage.getPage();
			pageSize = formStructurePage.getPageSize();
			totalCount = formStructurePage.getTotalCount();
		}

		@GraphQLField
		protected java.util.Collection<FormStructure> items;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

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

		formResource.setContextAcceptLanguage(_acceptLanguage);
		formResource.setContextCompany(_company);
		formResource.setContextUser(_user);
	}

	private void _populateResourceContext(
			FormDocumentResource formDocumentResource)
		throws Exception {

		formDocumentResource.setContextAcceptLanguage(_acceptLanguage);
		formDocumentResource.setContextCompany(_company);
		formDocumentResource.setContextUser(_user);
	}

	private void _populateResourceContext(FormRecordResource formRecordResource)
		throws Exception {

		formRecordResource.setContextAcceptLanguage(_acceptLanguage);
		formRecordResource.setContextCompany(_company);
		formRecordResource.setContextUser(_user);
	}

	private void _populateResourceContext(
			FormStructureResource formStructureResource)
		throws Exception {

		formStructureResource.setContextAcceptLanguage(_acceptLanguage);
		formStructureResource.setContextCompany(_company);
		formStructureResource.setContextUser(_user);
	}

	private static ComponentServiceObjects<FormResource>
		_formResourceComponentServiceObjects;
	private static ComponentServiceObjects<FormDocumentResource>
		_formDocumentResourceComponentServiceObjects;
	private static ComponentServiceObjects<FormRecordResource>
		_formRecordResourceComponentServiceObjects;
	private static ComponentServiceObjects<FormStructureResource>
		_formStructureResourceComponentServiceObjects;

	private AcceptLanguage _acceptLanguage;
	private BiFunction<Object, String, Filter> _filterBiFunction;
	private BiFunction<Object, String, List<Sort>> _sortsBiFunction;
	private Company _company;
	private User _user;

}