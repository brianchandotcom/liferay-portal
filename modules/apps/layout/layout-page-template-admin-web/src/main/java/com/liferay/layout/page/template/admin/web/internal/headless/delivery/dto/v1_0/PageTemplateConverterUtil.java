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

import com.liferay.headless.delivery.dto.v1_0.PageTemplate;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionServiceUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;

/**
 * @author Rubén Pulido
 */
public class PageTemplateConverterUtil {

	public static PageTemplate toPageTemplate(
		LayoutPageTemplateEntry layoutPageTemplateEntry) {

		return new PageTemplate() {
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
				setPageDefinition(
					() -> {
						Layout layout = LayoutLocalServiceUtil.fetchLayout(
							layoutPageTemplateEntry.getPlid());

						if (layout == null) {
							return null;
						}

						return PageDefinitionConverterUtil.toPageDefinition(
							layout);
					});
			}
		};
	}

}