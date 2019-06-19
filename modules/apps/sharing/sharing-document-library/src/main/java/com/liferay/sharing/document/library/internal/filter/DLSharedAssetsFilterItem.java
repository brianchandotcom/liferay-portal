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

package com.liferay.sharing.document.library.internal.filter;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.portal.kernel.language.Language;
import com.liferay.sharing.filter.SharedAssetsFilterItem;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(
	immediate = true, property = "navigation.item.order:Integer=1000",
	service = SharedAssetsFilterItem.class
)
public class DLSharedAssetsFilterItem implements SharedAssetsFilterItem {

	@Override
	public String getClassName() {
		return DLFileEntry.class.getName();
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "documents");
	}

	@Reference
	private Language _language;

}