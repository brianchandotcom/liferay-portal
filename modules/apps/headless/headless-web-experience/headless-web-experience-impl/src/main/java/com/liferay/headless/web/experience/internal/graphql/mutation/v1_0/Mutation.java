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

import com.liferay.headless.web.experience.dto.v1_0.StructuredContent;
import com.liferay.headless.web.experience.resource.v1_0.StructuredContentImageResource;
import com.liferay.headless.web.experience.resource.v1_0.StructuredContentResource;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLInvokeDetached;
import graphql.annotations.annotationTypes.GraphQLName;

import javax.annotation.Generated;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class Mutation {

	@GraphQLField
	@GraphQLInvokeDetached
	public StructuredContent postContentSpaceStructuredContent(
	@GraphQLName("content-space-id") Long contentSpaceId,@GraphQLName("StructuredContent") StructuredContent structuredContent)
			throws Exception {

				return _getStructuredContentResource().postContentSpaceStructuredContent(
					contentSpaceId,structuredContent);
	}
	@GraphQLInvokeDetached
	public boolean deleteStructuredContent(
	@GraphQLName("structured-content-id") Long structuredContentId)
			throws Exception {

				return _getStructuredContentResource().deleteStructuredContent(
					structuredContentId);
	}
	@GraphQLInvokeDetached
	public StructuredContent patchStructuredContent(
	@GraphQLName("structured-content-id") Long structuredContentId,@GraphQLName("StructuredContent") StructuredContent structuredContent)
			throws Exception {

				return _getStructuredContentResource().patchStructuredContent(
					structuredContentId,structuredContent);
	}
	@GraphQLInvokeDetached
	public StructuredContent putStructuredContent(
	@GraphQLName("structured-content-id") Long structuredContentId,@GraphQLName("StructuredContent") StructuredContent structuredContent)
			throws Exception {

				return _getStructuredContentResource().putStructuredContent(
					structuredContentId,structuredContent);
	}
	@GraphQLInvokeDetached
	public boolean deleteStructuredContentContentDocument(
	@GraphQLName("structured-content-id") Long structuredContentId,@GraphQLName("content-document-id") Long contentDocumentId)
			throws Exception {

				return _getStructuredContentImageResource().deleteStructuredContentContentDocument(
					structuredContentId,contentDocumentId);
	}

	private static StructuredContentResource _getStructuredContentResource() {
			return _structuredContentResourceServiceTracker.getService();
	}

	private static final ServiceTracker<StructuredContentResource, StructuredContentResource>
			_structuredContentResourceServiceTracker;
	private static StructuredContentImageResource _getStructuredContentImageResource() {
			return _structuredContentImageResourceServiceTracker.getService();
	}

	private static final ServiceTracker<StructuredContentImageResource, StructuredContentImageResource>
			_structuredContentImageResourceServiceTracker;

		static {
			Bundle bundle = FrameworkUtil.getBundle(Mutation.class);

				ServiceTracker<StructuredContentResource, StructuredContentResource>
					structuredContentResourceServiceTracker =
						new ServiceTracker<>(
							bundle.getBundleContext(),
							StructuredContentResource.class, null);

				structuredContentResourceServiceTracker.open();

				_structuredContentResourceServiceTracker =
					structuredContentResourceServiceTracker;
				ServiceTracker<StructuredContentImageResource, StructuredContentImageResource>
					structuredContentImageResourceServiceTracker =
						new ServiceTracker<>(
							bundle.getBundleContext(),
							StructuredContentImageResource.class, null);

				structuredContentImageResourceServiceTracker.open();

				_structuredContentImageResourceServiceTracker =
					structuredContentImageResourceServiceTracker;
	}

}