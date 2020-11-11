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

package com.liferay.headless.admin.content.internal.graphql.extension;

import com.liferay.headless.admin.content.dto.v1_0.Version;
import com.liferay.headless.admin.content.internal.dto.v1_0.extension.ExtendedStructuredContent;
import com.liferay.headless.delivery.dto.v1_0.StructuredContent;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLTypeExtension;

import org.osgi.service.component.annotations.Component;

/**
 * @author Luis Miguel Barcos
 */
@Component(immediate = true, service = {})
public class StructuredContentQueryContributor {

	@GraphQLTypeExtension(StructuredContent.class)
	public class ExtendedStructuredContentExtension {

		public ExtendedStructuredContentExtension(
			StructuredContent structuredContent) {

			_structuredContent = structuredContent;
		}

		@GraphQLField
		public Version version() {
			if (_structuredContent instanceof ExtendedStructuredContent) {
				ExtendedStructuredContent extendedStructuredContent =
					(ExtendedStructuredContent)_structuredContent;

				return extendedStructuredContent.getVersion();
			}

			return null;
		}

		private final StructuredContent _structuredContent;

	}

}