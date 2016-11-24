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

package com.liferay.portal.upgrade.util;

import com.liferay.portal.kernel.upgrade.util.BaseUpgradeColumnImpl;
import com.liferay.portal.kernel.upgrade.util.UpgradeColumn;

/**
 * @author     Brian Wing Shun Chan
 * @deprecated As of 7.0.0, with no direct replacement
 */
@Deprecated
public class BlogsEntryUrlTitleUpgradeColumnImpl extends BaseUpgradeColumnImpl {

	public BlogsEntryUrlTitleUpgradeColumnImpl(
		UpgradeColumn entryIdColumn, UpgradeColumn titleColumn) {

		super(null);

		throw new UnsupportedOperationException();
	}

	@Override
	public Object getNewValue(Object oldValue) throws Exception {
		throw new UnsupportedOperationException();
	}

	protected String getUrlTitle(long entryId, String title) {
		throw new UnsupportedOperationException();
	}

}