/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.headless.delivery.dto.v1_0.util;

import com.liferay.headless.delivery.dto.v1_0.UtilityPageTemplate;
import com.liferay.layout.utility.page.converter.LayoutUtilityPageEntryTypeConverter;
import com.liferay.layout.utility.page.model.LayoutUtilityPageEntry;

/**
 * @author Bárbara Cabrera
 */
public class UtilityPageTemplateUtil {

	public static UtilityPageTemplate toUtilityPageTemplate(
		LayoutUtilityPageEntry layoutUtilityPageEntry) {

		return new UtilityPageTemplate() {
			{
				defaultTemplate =
					layoutUtilityPageEntry.isDefaultLayoutUtilityPageEntry();
				externalReferenceCode =
					layoutUtilityPageEntry.getExternalReferenceCode();
				name = layoutUtilityPageEntry.getName();
				type = Type.create(
					LayoutUtilityPageEntryTypeConverter.convertToExternalValue(
						layoutUtilityPageEntry.getType()));
			}
		};
	}

}