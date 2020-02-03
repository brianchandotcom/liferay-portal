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

package com.liferay.layout.page.template.admin.web.internal.util;

import com.liferay.headless.delivery.dto.v1_0.PageTemplateDefinition;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionServiceUtil;

/**
 * @author Rubén Pulido
 */
public class PageTemplateDefinitionConverterUtil {

	public static PageTemplateDefinition toPageTemplateDefinition(
		LayoutPageTemplateEntry layoutPageTemplateEntry) {

		return new PageTemplateDefinition() {
			{
				dateCreated = layoutPageTemplateEntry.getCreateDate();
				dateModified = layoutPageTemplateEntry.getModifiedDate();
				name = layoutPageTemplateEntry.getName();
				setCollectionName(
					() -> {
						LayoutPageTemplateCollection
							layoutPageTemplateCollection =
								LayoutPageTemplateCollectionServiceUtil.
									fetchLayoutPageTemplateCollection(
										layoutPageTemplateEntry.
											getLayoutPageTemplateCollectionId());

						if (layoutPageTemplateCollection == null) {
							return null;
						}

						return layoutPageTemplateCollection.getName();
					});
			}
		};
	}

}