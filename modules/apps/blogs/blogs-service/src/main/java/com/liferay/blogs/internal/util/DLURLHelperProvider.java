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

package com.liferay.blogs.internal.util;

import com.liferay.document.library.util.DLURLHelper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = {})
public class DLURLHelperProvider {

	public static DLURLHelper getDLURLHelper() {
		return _dlurlHelperProvider._dlurlHelper;
	}

	public DLURLHelperProvider() {
		_dlurlHelperProvider = this;
	}

	private static DLURLHelperProvider _dlurlHelperProvider;

	@Reference
	private DLURLHelper _dlurlHelper;

}