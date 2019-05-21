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

package com.liferay.asset.list.internal.dynamic.data.mapping.util;

import com.liferay.dynamic.data.mapping.util.DDMIndexer;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(immediate = true, service = {})
public class DDMIndexerUtil {

	public static String encodeName(
		long ddmStructureId, String name, Locale locale) {

		return _ddmIndexer.encodeName(ddmStructureId, name, locale);
	}

	@Reference(unbind = "-")
	protected void se(DDMIndexer ddmIndexer) {
		_ddmIndexer = ddmIndexer;
	}

	private static DDMIndexer _ddmIndexer;

}