/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.web.internal.servlet.taglib.definition;

import com.liferay.data.engine.taglib.servlet.taglib.definition.DataLayoutBuilderDefinition;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = "content.type=document-library",
	service = DataLayoutBuilderDefinition.class
)
public class DocumentLibraryDataLayoutBuilderDefinition
	implements DataLayoutBuilderDefinition {

	@Override
	public boolean allowMultiplePages() {
		return false;
	}

	@Override
	public String[] getDisabledProperties() {
		return new String[] {"objectFieldName", "requiredErrorMessage"};
	}

	@Override
	public String[] getDisabledTabs() {
		return new String[] {"Autocomplete"};
	}

	@Override
	public String[] getUnimplementedProperties() {
		return new String[] {
			"allowGuestUsers", "fieldNamespace", "hideField", "inputMask",
			"readOnly", "requireConfirmation", "validation",
			"visibilityExpression"
		};
	}

	@Override
	public String[] getVisibleProperties() {
		return new String[] {"localizable"};
	}

}