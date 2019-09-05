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

package com.liferay.sharing.model.impl;

import com.liferay.sharing.security.permission.SharingEntryAction;

/**
 * The extended model implementation for the {@code SharingEntry} service.
 * Represents a row in the {@code SharingEntry} database table, with each column
 * mapped to a property of this class.
 *
 * <p>
 * Helper methods and all application logic should be put in this class.
 * Whenever methods are added, rerun Service Builder to copy their definitions
 * into the {@code com.liferay.sharing.model.SharingEntry} interface.
 * </p>
 *
 * @author Brian Wing Shun Chan
 */
public class SharingEntryImpl extends SharingEntryBaseImpl {

	/**
	 * Returns {@code true} if the sharing entry has the sharing entry action.
	 *
	 * @param  sharingEntryAction the sharing entry action
	 * @return {@code true} if the sharing entry has the sharing entry action;
	 *         {@code false} otherwise
	 * @review
	 */
	@Override
	public boolean hasSharingPermission(SharingEntryAction sharingEntryAction) {
		if ((getActionIds() & sharingEntryAction.getBitwiseValue()) != 0) {
			return true;
		}

		return false;
	}

}