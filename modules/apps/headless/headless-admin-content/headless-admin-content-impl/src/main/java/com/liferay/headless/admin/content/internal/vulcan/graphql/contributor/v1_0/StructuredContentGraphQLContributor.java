/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
@Component(service = GraphQLContributor.class)
public class StructuredContentGraphQLContributor implements GraphQLContributor {

	@Override
	public String getPath() {
		return "/headless-admin-content-graphql/v1_0";
	}

	@Override
	public StructuredContentQuery getQuery() {
		return new StructuredContentQuery();
	}

	@Override
	public boolean isJaxRsResourceInvocation() {
		return false;
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