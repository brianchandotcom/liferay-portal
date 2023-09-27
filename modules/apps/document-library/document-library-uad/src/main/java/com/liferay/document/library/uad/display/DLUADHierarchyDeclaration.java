/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.uad.display;

import com.liferay.portal.kernel.language.Language;
import com.liferay.user.associated.data.display.UADDisplay;
import com.liferay.user.associated.data.display.UADHierarchyDeclaration;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(service = UADHierarchyDeclaration.class)
public class DLUADHierarchyDeclaration implements UADHierarchyDeclaration {

	@Override
	public UADDisplay<?>[] getContainerUADDisplays() {
		return new UADDisplay<?>[] {_dlFolderUADDisplay};
	}

	@Override
	public String getEntitiesTypeLabel(Locale locale) {
		return _language.get(locale, "folders-and-files");
	}

	@Override
	public String[] getExtraColumnNames() {
		return new String[] {"description"};
	}

	@Override
	public UADDisplay<?>[] getNoncontainerUADDisplays() {
		return new UADDisplay<?>[] {_dlFileEntryUADDisplay};
	}

	@Reference(
		target = "(component.name=com.liferay.document.library.uad.display.DLFileEntryUADDisplay)"
	)
	private UADDisplay<?> _dlFileEntryUADDisplay;

	@Reference(
		target = "(component.name=com.liferay.document.library.uad.display.DLFolderUADDisplay)"
	)
	private UADDisplay<?> _dlFolderUADDisplay;

	@Reference
	private Language _language;

}