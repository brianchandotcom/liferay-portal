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

package com.liferay.document.library.web.internal.info.display.contributor;

import com.liferay.asset.info.display.contributor.BaseAssetInfoDisplayContributor;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.info.display.contributor.InfoDisplayContributor;
import com.liferay.portal.kernel.repository.model.FileEntry;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Tardín
 */
@Component(service = InfoDisplayContributor.class)
public class DLFileEntryAssetInfoDisplayContributor
	extends BaseAssetInfoDisplayContributor<FileEntry> {

	@Override
	public String getClassName() {
		return DLFileEntryConstants.getClassName();
	}

	@Override
	public String getInfoURLSeparator() {
		return "/d/";
	}

	@Override
	protected Map<String, Object> getClassTypeValues(
		FileEntry fileEntry, Locale locale) {

		return new HashMap<>();
	}

}