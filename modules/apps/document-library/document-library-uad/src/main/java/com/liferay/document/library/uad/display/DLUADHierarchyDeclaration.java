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

package com.liferay.document.library.uad.display;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
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
		return LanguageUtil.get(
			ResourceBundleUtil.getBundle(
				locale, DLUADHierarchyDeclaration.class),
			"folders-and-files");
	}

	@Override
	public String[] getExtraColumnNames() {
		return new String[] {"description"};
	}

	@Override
	public UADDisplay<?>[] getNoncontainerUADDisplays() {
		return new UADDisplay<?>[] {_dlFileEntryUADDisplay};
	}

	@Reference
	private DLFileEntryUADDisplay _dlFileEntryUADDisplay;

	@Reference
	private DLFolderUADDisplay _dlFolderUADDisplay;

}