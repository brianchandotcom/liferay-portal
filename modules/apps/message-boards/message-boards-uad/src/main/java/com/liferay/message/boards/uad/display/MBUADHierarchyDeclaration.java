/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.uad.display;

import com.liferay.portal.kernel.language.Language;
import com.liferay.user.associated.data.display.UADDisplay;
import com.liferay.user.associated.data.display.UADHierarchyDeclaration;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(service = UADHierarchyDeclaration.class)
public class MBUADHierarchyDeclaration implements UADHierarchyDeclaration {

	@Override
	public UADDisplay<?>[] getContainerUADDisplays() {
		return new UADDisplay<?>[] {_mbCategoryUADDisplay, _mbThreadUADDisplay};
	}

	@Override
	public String getEntitiesTypeLabel(Locale locale) {
		return _language.get(locale, "categories-and-threads");
	}

	@Override
	public String[] getExtraColumnNames() {
		return new String[] {"content"};
	}

	@Override
	public UADDisplay<?>[] getNoncontainerUADDisplays() {
		return new UADDisplay<?>[] {_mbMessageUADDisplay};
	}

	@Reference
	private Language _language;

	@Reference(
		target = "(component.name=com.liferay.message.boards.uad.display.MBCategoryUADDisplay)"
	)
	private UADDisplay<?> _mbCategoryUADDisplay;

	@Reference(
		target = "(component.name=com.liferay.message.boards.uad.display.MBMessageUADDisplay)"
	)
	private UADDisplay<?> _mbMessageUADDisplay;

	@Reference(
		target = "(component.name=com.liferay.message.boards.uad.display.MBThreadUADDisplay)"
	)
	private UADDisplay<?> _mbThreadUADDisplay;

}