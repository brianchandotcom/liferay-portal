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

package com.liferay.portal.verify.model;

import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.portal.kernel.verify.model.VerifiableResourcedModel;

/**
 * @author Michael Bowerman
 */
public class DLFileEntryTypeVerifiableModel
	implements VerifiableResourcedModel {

	@Override
	public String getModelName() {
		return DLFileEntryType.class.getName();
	}

	@Override
	public String getPrimaryKeyColumnName() {
		return "fileEntryTypeId";
	}

	@Override
	public String getTableName() {
		return "DLFileEntryType";
	}

	@Override
	public String getUserIdColumnName() {
		return "userId";
	}

}