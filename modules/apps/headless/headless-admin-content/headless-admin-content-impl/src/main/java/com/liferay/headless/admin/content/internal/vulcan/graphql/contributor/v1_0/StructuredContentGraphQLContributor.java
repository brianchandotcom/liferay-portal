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

package com.liferay.headless.admin.content.internal.vulcan.graphql.contributor.v1_0;

import com.liferay.headless.admin.content.dto.v1_0.Version;
import com.liferay.headless.admin.content.internal.dto.v1_0.extension.ExtensionStructuredContent;
import com.liferay.headless.delivery.dto.v1_0.StructuredContent;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLTypeExtension;
import com.liferay.portal.vulcan.graphql.contributor.GraphQLContributor;

import org.osgi.service.component.annotations.Component;

/**
 * @author Luis Miguel Barcos
 */
@Component(immediate = true, service = GraphQLContributor.class)
public class StructuredContentGraphQLContributor implements GraphQLContributor {

	@Override
	public String getPath() {
		return "/headless-admin-content-graphql/v1_0";
	}

	@Override
	public StructuredContentQuery getQuery() {
		return new StructuredContentQuery();
	}

	public static class StructuredContentQuery {

		@GraphQLTypeExtension(StructuredContent.class)
		public class StructuredContentGraphQLTypeExtension {

			public StructuredContentGraphQLTypeExtension(
				StructuredContent structuredContent) {

				_structuredContent = structuredContent;
			}

			@GraphQLField
			public Version version() {
				if (_structuredContent instanceof ExtensionStructuredContent) {
					ExtensionStructuredContent extensionStructuredContent =
						(ExtensionStructuredContent)_structuredContent;

					return extensionStructuredContent.getVersion();
				}

				return null;
			}

			private final StructuredContent _structuredContent;

		}

	}

}