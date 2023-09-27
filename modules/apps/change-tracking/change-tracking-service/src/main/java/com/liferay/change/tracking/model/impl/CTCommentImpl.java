/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.model.impl;

import java.util.Date;

/**
 * @author Preston Crary
 */
public class CTCommentImpl extends CTCommentBaseImpl {

	@Override
	public boolean isEdited() {
		Date modifiedDate = getModifiedDate();

		if (modifiedDate.after(getCreateDate())) {
			return true;
		}

		return false;
	}

}