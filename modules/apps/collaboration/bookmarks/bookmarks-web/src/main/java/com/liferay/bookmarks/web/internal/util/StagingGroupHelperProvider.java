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

package com.liferay.bookmarks.web.internal.util;

import com.liferay.staging.StagingGroupHelper;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mate Thurzo
 */
@Component(immediate = true)
public class StagingGroupHelperProvider {

	public static StagingGroupHelperProvider getStagingGroupHelperProvider() {
		return _stagingGroupHelperProvider;
	}

	public StagingGroupHelper getStagingGroupHelper() {
		return _stagingGroupHelper;
	}

	@Reference(unbind = "-")
	public void setStagingGroupHelper(StagingGroupHelper stagingGroupHelper) {
		_stagingGroupHelper = stagingGroupHelper;
	}

	@Activate
	protected void activate() {
		_stagingGroupHelperProvider = this;
	}

	@Deactivate
	protected void deactivate() {
		_stagingGroupHelperProvider = null;
	}

	private static StagingGroupHelper _stagingGroupHelper;
	private static StagingGroupHelperProvider _stagingGroupHelperProvider;

}