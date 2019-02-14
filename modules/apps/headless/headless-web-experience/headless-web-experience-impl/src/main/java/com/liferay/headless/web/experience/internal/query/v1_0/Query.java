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

package com.liferay.headless.web.experience.internal.query.v1_0;

import com.liferay.headless.web.experience.dto.v1_0.AggregateRating;
import com.liferay.headless.web.experience.dto.v1_0.Comment;
import com.liferay.headless.web.experience.dto.v1_0.ContentDocument;
import com.liferay.headless.web.experience.dto.v1_0.ContentStructure;
import com.liferay.headless.web.experience.dto.v1_0.StructuredContent;
import com.liferay.headless.web.experience.resource.v1_0.AggregateRatingResource;
import com.liferay.headless.web.experience.resource.v1_0.CommentResource;
import com.liferay.headless.web.experience.resource.v1_0.ContentDocumentResource;
import com.liferay.headless.web.experience.resource.v1_0.ContentStructureResource;
import com.liferay.headless.web.experience.resource.v1_0.StructuredContentResource;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
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
	public AggregateRating getAggregateRating( @GraphQLName("aggregate-rating-id") Long aggregateRatingId ) throws Exception {

		return _getAggregateRatingResource().getAggregateRating( aggregateRatingId );

	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Comment getComment( @GraphQLName("comment-id") Long commentId ) throws Exception {

		return _getCommentResource().getComment( commentId );

	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<Comment> getCommentCommentsPage( @GraphQLName("comment-id") Long commentId , @GraphQLName("per_page") int perPage , @GraphQLName("page") int page ) throws Exception {

		return _getCommentResource().getCommentCommentsPage( commentId , Pagination.of(perPage, page) ).getItems();

	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<Comment> getStructuredContentCommentsPage( @GraphQLName("structured-content-id") Long structuredContentId , @GraphQLName("per_page") int perPage , @GraphQLName("page") int page ) throws Exception {

		return _getCommentResource().getStructuredContentCommentsPage( structuredContentId , Pagination.of(perPage, page) ).getItems();

	}

	@GraphQLField
	@GraphQLInvokeDetached
	public ContentDocument getContentDocument( @GraphQLName("content-document-id") Long contentDocumentId ) throws Exception {

		return _getContentDocumentResource().getContentDocument( contentDocumentId );

	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<ContentStructure> getContentSpaceContentStructuresPage( @GraphQLName("content-space-id") Long contentSpaceId , @GraphQLName("filter") Filter filter , @GraphQLName("per_page") int perPage , @GraphQLName("page") int page , @GraphQLName("Sort[]") Sort[] sorts ) throws Exception {

		return _getContentStructureResource().getContentSpaceContentStructuresPage( contentSpaceId , filter , Pagination.of(perPage, page) , sorts ).getItems();

	}

	@GraphQLField
	@GraphQLInvokeDetached
	public ContentStructure getContentStructure( @GraphQLName("content-structure-id") Long contentStructureId ) throws Exception {

		return _getContentStructureResource().getContentStructure( contentStructureId );

	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<StructuredContent> getContentSpaceContentStructureStructuredContentsPage( @GraphQLName("content-space-id") Long contentSpaceId , @GraphQLName("content-structure-id") Long contentStructureId , @GraphQLName("filter") Filter filter , @GraphQLName("per_page") int perPage , @GraphQLName("page") int page , @GraphQLName("Sort[]") Sort[] sorts ) throws Exception {

		return _getStructuredContentResource().getContentSpaceContentStructureStructuredContentsPage( contentSpaceId , contentStructureId , filter , Pagination.of(perPage, page) , sorts ).getItems();

	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<StructuredContent> getContentSpaceStructuredContentsPage( @GraphQLName("content-space-id") Long contentSpaceId , @GraphQLName("filter") Filter filter , @GraphQLName("per_page") int perPage , @GraphQLName("page") int page , @GraphQLName("Sort[]") Sort[] sorts ) throws Exception {

		return _getStructuredContentResource().getContentSpaceStructuredContentsPage( contentSpaceId , filter , Pagination.of(perPage, page) , sorts ).getItems();

	}

	@GraphQLField
	@GraphQLInvokeDetached
	public StructuredContent getStructuredContent( @GraphQLName("structured-content-id") Long structuredContentId ) throws Exception {

		return _getStructuredContentResource().getStructuredContent( structuredContentId );

	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<Long> getStructuredContentCategoriesPage( @GraphQLName("structured-content-id") Long structuredContentId , @GraphQLName("per_page") int perPage , @GraphQLName("page") int page ) throws Exception {

		return _getStructuredContentResource().getStructuredContentCategoriesPage( structuredContentId , Pagination.of(perPage, page) ).getItems();

	}

	private static AggregateRatingResource _getAggregateRatingResource() {
			return _aggregateRatingResourceServiceTracker.getService();
	}

	private static final ServiceTracker<AggregateRatingResource, AggregateRatingResource> _aggregateRatingResourceServiceTracker;

	private static CommentResource _getCommentResource() {
			return _commentResourceServiceTracker.getService();
	}

	private static final ServiceTracker<CommentResource, CommentResource> _commentResourceServiceTracker;

	private static ContentDocumentResource _getContentDocumentResource() {
			return _contentDocumentResourceServiceTracker.getService();
	}

	private static final ServiceTracker<ContentDocumentResource, ContentDocumentResource> _contentDocumentResourceServiceTracker;

	private static ContentStructureResource _getContentStructureResource() {
			return _contentStructureResourceServiceTracker.getService();
	}

	private static final ServiceTracker<ContentStructureResource, ContentStructureResource> _contentStructureResourceServiceTracker;

	private static StructuredContentResource _getStructuredContentResource() {
			return _structuredContentResourceServiceTracker.getService();
	}

	private static final ServiceTracker<StructuredContentResource, StructuredContentResource> _structuredContentResourceServiceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(Query.class);

		ServiceTracker<AggregateRatingResource, AggregateRatingResource> aggregateRatingResourceServiceTracker =
			new ServiceTracker<AggregateRatingResource, AggregateRatingResource>(bundle.getBundleContext(), AggregateRatingResource.class, null);

		aggregateRatingResourceServiceTracker.open();

		_aggregateRatingResourceServiceTracker = aggregateRatingResourceServiceTracker;

		ServiceTracker<CommentResource, CommentResource> commentResourceServiceTracker =
			new ServiceTracker<CommentResource, CommentResource>(bundle.getBundleContext(), CommentResource.class, null);

		commentResourceServiceTracker.open();

		_commentResourceServiceTracker = commentResourceServiceTracker;

		ServiceTracker<ContentDocumentResource, ContentDocumentResource> contentDocumentResourceServiceTracker =
			new ServiceTracker<ContentDocumentResource, ContentDocumentResource>(bundle.getBundleContext(), ContentDocumentResource.class, null);

		contentDocumentResourceServiceTracker.open();

		_contentDocumentResourceServiceTracker = contentDocumentResourceServiceTracker;

		ServiceTracker<ContentStructureResource, ContentStructureResource> contentStructureResourceServiceTracker =
			new ServiceTracker<ContentStructureResource, ContentStructureResource>(bundle.getBundleContext(), ContentStructureResource.class, null);

		contentStructureResourceServiceTracker.open();

		_contentStructureResourceServiceTracker = contentStructureResourceServiceTracker;

		ServiceTracker<StructuredContentResource, StructuredContentResource> structuredContentResourceServiceTracker =
			new ServiceTracker<StructuredContentResource, StructuredContentResource>(bundle.getBundleContext(), StructuredContentResource.class, null);

		structuredContentResourceServiceTracker.open();

		_structuredContentResourceServiceTracker = structuredContentResourceServiceTracker;

	}

}