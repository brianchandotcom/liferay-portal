/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.util.structure;

import com.liferay.layout.util.constants.LayoutDataItemTypeConstants;

/**
 * @author Víctor Galán
 */
public class FormStepContainerStyledLayoutStructureItem
	extends StyledLayoutStructureItem {

	public FormStepContainerStyledLayoutStructureItem(String parentItemId) {
		super(parentItemId);
	}

	public FormStepContainerStyledLayoutStructureItem(
		String itemId, String parentId) {

		super(itemId, parentId);
	}

	@Override
	public String getItemType() {
		return LayoutDataItemTypeConstants.TYPE_FORM_STEP_CONTAINER;
	}

}