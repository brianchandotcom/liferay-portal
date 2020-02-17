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

package com.liferay.layout.page.template.admin.web.internal.headless.delivery.dto.v1_0;

import com.liferay.headless.delivery.dto.v1_0.PageTemplateCollection;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;

/**
 * @author Jürgen Kappler
 */
public class PageTemplateCollectionConverterUtil {

	public static PageTemplateCollection toPageTemplateCollection(
		LayoutPageTemplateCollection layoutPageTemplateCollection) {

		return new PageTemplateCollection() {
			{
				description = layoutPageTemplateCollection.getDescription();
				name = layoutPageTemplateCollection.getName();
			}
		};
	}

}