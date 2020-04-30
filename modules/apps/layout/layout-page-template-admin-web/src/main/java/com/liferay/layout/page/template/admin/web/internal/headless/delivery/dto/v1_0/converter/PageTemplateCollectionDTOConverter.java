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

package com.liferay.layout.page.template.admin.web.internal.headless.delivery.dto.v1_0.converter;

import com.liferay.headless.delivery.dto.v1_0.PageTemplateCollection;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Jürgen Kappler
 */
public class PageTemplateCollectionDTOConverter {

	public static PageTemplateCollection toDTO(
		LayoutPageTemplateCollection layoutPageTemplateCollection) {

		return new PageTemplateCollection() {
			{
				name = layoutPageTemplateCollection.getName();

				setDescription(
					() -> {
						String description =
							layoutPageTemplateCollection.getDescription();

						if (Validator.isNull(description)) {
							return null;
						}

						return description;
					});
			}
		};
	}

}