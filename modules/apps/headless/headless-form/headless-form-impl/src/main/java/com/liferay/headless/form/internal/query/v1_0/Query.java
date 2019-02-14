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

package com.liferay.headless.form.internal.query.v1_0;

import com.liferay.headless.form.dto.v1_0.Creator;
import com.liferay.headless.form.dto.v1_0.Form;
import com.liferay.headless.form.dto.v1_0.FormDocument;
import com.liferay.headless.form.dto.v1_0.FormRecord;
import com.liferay.headless.form.dto.v1_0.FormStructure;
import com.liferay.headless.form.resource.v1_0.CreatorResource;
import com.liferay.headless.form.resource.v1_0.FormDocumentResource;
import com.liferay.headless.form.resource.v1_0.FormRecordResource;
import com.liferay.headless.form.resource.v1_0.FormResource;
import com.liferay.headless.form.resource.v1_0.FormStructureResource;
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
	public Creator getCreator( @GraphQLName("creator-id") Long creatorId ) throws Exception {

		return _getCreatorResource().getCreator( creatorId );

	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<Form> getContentSpaceFormsPage( @GraphQLName("content-space-id") Long contentSpaceId , @GraphQLName("per_page") int perPage , @GraphQLName("page") int page ) throws Exception {

		return _getFormResource().getContentSpaceFormsPage( contentSpaceId , Pagination.of(perPage, page) ).getItems();

	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Form getForm( @GraphQLName("form-id") Long formId ) throws Exception {

		return _getFormResource().getForm( formId );

	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Form getFormFetchLatestDraft( @GraphQLName("form-id") Long formId ) throws Exception {

		return _getFormResource().getFormFetchLatestDraft( formId );

	}

	@GraphQLField
	@GraphQLInvokeDetached
	public FormDocument getFormDocument( @GraphQLName("form-document-id") Long formDocumentId ) throws Exception {

		return _getFormDocumentResource().getFormDocument( formDocumentId );

	}

	@GraphQLField
	@GraphQLInvokeDetached
	public FormRecord getFormRecord( @GraphQLName("form-record-id") Long formRecordId ) throws Exception {

		return _getFormRecordResource().getFormRecord( formRecordId );

	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<FormRecord> getFormFormRecordsPage( @GraphQLName("form-id") Long formId , @GraphQLName("per_page") int perPage , @GraphQLName("page") int page ) throws Exception {

		return _getFormRecordResource().getFormFormRecordsPage( formId , Pagination.of(perPage, page) ).getItems();

	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<FormStructure> getContentSpaceFormStructuresPage( @GraphQLName("content-space-id") Long contentSpaceId , @GraphQLName("per_page") int perPage , @GraphQLName("page") int page ) throws Exception {

		return _getFormStructureResource().getContentSpaceFormStructuresPage( contentSpaceId , Pagination.of(perPage, page) ).getItems();

	}

	@GraphQLField
	@GraphQLInvokeDetached
	public FormStructure getFormStructure( @GraphQLName("form-structure-id") Long formStructureId ) throws Exception {

		return _getFormStructureResource().getFormStructure( formStructureId );

	}

	private static CreatorResource _getCreatorResource() {
			return _creatorResourceServiceTracker.getService();
	}

	private static final ServiceTracker<CreatorResource, CreatorResource> _creatorResourceServiceTracker;

	private static FormResource _getFormResource() {
			return _formResourceServiceTracker.getService();
	}

	private static final ServiceTracker<FormResource, FormResource> _formResourceServiceTracker;

	private static FormDocumentResource _getFormDocumentResource() {
			return _formDocumentResourceServiceTracker.getService();
	}

	private static final ServiceTracker<FormDocumentResource, FormDocumentResource> _formDocumentResourceServiceTracker;

	private static FormRecordResource _getFormRecordResource() {
			return _formRecordResourceServiceTracker.getService();
	}

	private static final ServiceTracker<FormRecordResource, FormRecordResource> _formRecordResourceServiceTracker;

	private static FormStructureResource _getFormStructureResource() {
			return _formStructureResourceServiceTracker.getService();
	}

	private static final ServiceTracker<FormStructureResource, FormStructureResource> _formStructureResourceServiceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(Query.class);

		ServiceTracker<CreatorResource, CreatorResource> creatorResourceServiceTracker =
			new ServiceTracker<CreatorResource, CreatorResource>(bundle.getBundleContext(), CreatorResource.class, null);

		creatorResourceServiceTracker.open();

		_creatorResourceServiceTracker = creatorResourceServiceTracker;

		ServiceTracker<FormResource, FormResource> formResourceServiceTracker =
			new ServiceTracker<FormResource, FormResource>(bundle.getBundleContext(), FormResource.class, null);

		formResourceServiceTracker.open();

		_formResourceServiceTracker = formResourceServiceTracker;

		ServiceTracker<FormDocumentResource, FormDocumentResource> formDocumentResourceServiceTracker =
			new ServiceTracker<FormDocumentResource, FormDocumentResource>(bundle.getBundleContext(), FormDocumentResource.class, null);

		formDocumentResourceServiceTracker.open();

		_formDocumentResourceServiceTracker = formDocumentResourceServiceTracker;

		ServiceTracker<FormRecordResource, FormRecordResource> formRecordResourceServiceTracker =
			new ServiceTracker<FormRecordResource, FormRecordResource>(bundle.getBundleContext(), FormRecordResource.class, null);

		formRecordResourceServiceTracker.open();

		_formRecordResourceServiceTracker = formRecordResourceServiceTracker;

		ServiceTracker<FormStructureResource, FormStructureResource> formStructureResourceServiceTracker =
			new ServiceTracker<FormStructureResource, FormStructureResource>(bundle.getBundleContext(), FormStructureResource.class, null);

		formStructureResourceServiceTracker.open();

		_formStructureResourceServiceTracker = formStructureResourceServiceTracker;

	}

}