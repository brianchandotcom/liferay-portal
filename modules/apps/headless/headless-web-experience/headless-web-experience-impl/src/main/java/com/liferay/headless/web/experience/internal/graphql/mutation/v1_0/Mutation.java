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

package com.liferay.headless.web.experience.internal.graphql.mutation.v1_0;

import com.liferay.headless.web.experience.internal.dto.v1_0.StructuredContentImpl;
import com.liferay.headless.web.experience.internal.resource.v1_0.StructuredContentImageResourceImpl;
import com.liferay.headless.web.experience.internal.resource.v1_0.StructuredContentResourceImpl;

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
	public boolean deleteStructuredContent(
			@GraphQLName("structured-content-id") Long structuredContentId)
		throws Exception {

		return (boolean)
			_getStructuredContentResourceImpl().deleteStructuredContent(
				structuredContentId);
	}

	@GraphQLInvokeDetached
	public boolean deleteStructuredContentContentDocument(
			@GraphQLName("structured-content-id") Long structuredContentId,
			@GraphQLName("content-document-id") Long contentDocumentId)
		throws Exception {

		return (boolean)
			_getStructuredContentImageResourceImpl().
				deleteStructuredContentContentDocument(
					structuredContentId, contentDocumentId);
	}

	@GraphQLInvokeDetached
	public StructuredContentImpl patchStructuredContent(
			@GraphQLName("structured-content-id") Long structuredContentId,
			@GraphQLName("StructuredContent") StructuredContentImpl
				structuredContentImpl)
		throws Exception {

		return (StructuredContentImpl)
			_getStructuredContentResourceImpl().patchStructuredContent(
				structuredContentId, structuredContentImpl);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public StructuredContentImpl postContentSpaceStructuredContent(
			@GraphQLName("content-space-id") Long contentSpaceId,
			@GraphQLName("StructuredContent") StructuredContentImpl
				structuredContentImpl)
		throws Exception {

		return (StructuredContentImpl)
			_getStructuredContentResourceImpl().
				postContentSpaceStructuredContent(
					contentSpaceId, structuredContentImpl);
	}

	@GraphQLInvokeDetached
	public StructuredContentImpl putStructuredContent(
			@GraphQLName("structured-content-id") Long structuredContentId,
			@GraphQLName("StructuredContent") StructuredContentImpl
				structuredContentImpl)
		throws Exception {

		return (StructuredContentImpl)
			_getStructuredContentResourceImpl().putStructuredContent(
				structuredContentId, structuredContentImpl);
	}

	private static StructuredContentImageResourceImpl
		_getStructuredContentImageResourceImpl() {

		return new StructuredContentImageResourceImpl();
	}

	private static StructuredContentResourceImpl
		_getStructuredContentResourceImpl() {

		return new StructuredContentResourceImpl();
	}

}