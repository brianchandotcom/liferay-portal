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

import com.liferay.headless.delivery.dto.v1_0.PageDefinition;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionServiceUtil;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalServiceUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;

/**
 * @author Rubén Pulido
 */
public class PageDefinitionConverterUtil {

	public static PageDefinition toPageDefinition(long plid) {
		Layout layout = LayoutLocalServiceUtil.fetchLayout(plid);

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			LayoutPageTemplateEntryLocalServiceUtil.
				fetchLayoutPageTemplateEntryByPlid(plid);

		return new PageDefinition() {
			{
				dateCreated = layout.getCreateDate();
				dateModified = layout.getModifiedDate();
				setCollectionName(
					() -> {
						if (layoutPageTemplateEntry == null) {
							return null;
						}

						LayoutPageTemplateCollection
							layoutPageTemplateCollection =
								LayoutPageTemplateCollectionServiceUtil.
									fetchLayoutPageTemplateCollection(
										layoutPageTemplateEntry.
											getLayoutPageTemplateCollectionId());

						return layoutPageTemplateCollection.getName();
					});
				setName(
					() -> {
						if (layoutPageTemplateEntry == null) {
							return layout.getName();
						}

						return layoutPageTemplateEntry.getName();
					});
			}
		};
	}

}