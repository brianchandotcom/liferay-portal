/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.headless.delivery.dto.v1_0.util;

import com.liferay.headless.delivery.dto.v1_0.PageTemplateCollection;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Jürgen Kappler
 */
public class PageTemplateCollectionUtil {

	public static PageTemplateCollection toPageTemplateCollection(
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