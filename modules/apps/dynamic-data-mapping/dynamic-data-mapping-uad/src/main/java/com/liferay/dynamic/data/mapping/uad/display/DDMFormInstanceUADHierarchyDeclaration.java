/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.uad.display;

import com.liferay.portal.kernel.language.Language;
import com.liferay.user.associated.data.display.UADDisplay;
import com.liferay.user.associated.data.display.UADHierarchyDeclaration;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcos Martins
 */
@Component(service = UADHierarchyDeclaration.class)
public class DDMFormInstanceUADHierarchyDeclaration
	implements UADHierarchyDeclaration {

	@Override
	public UADDisplay<?>[] getContainerUADDisplays() {
		return new UADDisplay<?>[] {_ddmFormInstanceUADDisplay};
	}

	@Override
	public String getEntitiesTypeLabel(Locale locale) {
		return _language.get(locale, "ddm-form-instance");
	}

	@Override
	public String[] getExtraColumnNames() {
		return new String[] {"createDate"};
	}

	@Override
	public UADDisplay<?>[] getNoncontainerUADDisplays() {
		return new UADDisplay<?>[] {_ddmFormInstanceRecordUADDisplay};
	}

	@Reference(
		target = "(component.name=com.liferay.dynamic.data.mapping.uad.display.DDMFormInstanceRecordUADDisplay)"
	)
	private UADDisplay<?> _ddmFormInstanceRecordUADDisplay;

	@Reference(
		target = "(component.name=com.liferay.dynamic.data.mapping.uad.display.DDMFormInstanceUADDisplay)"
	)
	private UADDisplay<?> _ddmFormInstanceUADDisplay;

	@Reference
	private Language _language;

}