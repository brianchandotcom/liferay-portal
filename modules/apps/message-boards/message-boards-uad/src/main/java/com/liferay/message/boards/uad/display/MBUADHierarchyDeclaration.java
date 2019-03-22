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

package com.liferay.message.boards.uad.display;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.user.associated.data.display.UADDisplay;
import com.liferay.user.associated.data.display.UADHierarchyDeclaration;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(immediate = true, service = UADHierarchyDeclaration.class)
public class MBUADHierarchyDeclaration implements UADHierarchyDeclaration {

	@Override
	public UADDisplay<?>[] getContainerUADDisplays() {
		return new UADDisplay<?>[] {_mbCategoryUADDisplay, _mbThreadUADDisplay};
	}

	@Override
	public String getEntitiesTypeLabel(Locale locale) {
		return LanguageUtil.get(
			ResourceBundleUtil.getBundle(
				locale, MBUADHierarchyDeclaration.class),
			"categories-and-threads");
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
	private MBCategoryUADDisplay _mbCategoryUADDisplay;

	@Reference
	private MBMessageUADDisplay _mbMessageUADDisplay;

	@Reference
	private MBThreadUADDisplay _mbThreadUADDisplay;

}