/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.resource.util.layout.structure;

import com.liferay.headless.admin.site.dto.v1_0.PageElement;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Eudaldo Alonso
 */
public class LayoutStructureUtil {

	public static String getParentExternalReferenceCode(
		PageElement pageElement, LayoutStructure layoutStructure) {

		String parentExternalReferenceCode =
			pageElement.getParentExternalReferenceCode();

		if (Validator.isNotNull(parentExternalReferenceCode)) {
			return parentExternalReferenceCode;
		}

		return layoutStructure.getMainItemId();
	}

}