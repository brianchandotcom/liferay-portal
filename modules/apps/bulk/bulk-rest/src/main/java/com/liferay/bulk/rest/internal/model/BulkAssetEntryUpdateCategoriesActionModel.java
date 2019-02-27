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

package com.liferay.bulk.rest.internal.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Alejandro Tardín
 */
@XmlRootElement
public class BulkAssetEntryUpdateCategoriesActionModel
	extends BulkAssetEntryActionModel {

	@XmlElement
	public boolean getAppend() {
		return _append;
	}

	@XmlElement
	public long[] getToAddCategoryIds() {
		return _toAddCategoryIds;
	}

	@XmlElement
	public long[] getToRemoveCategoryIds() {
		return _toRemoveCategoryIds;
	}

	public void setAppend(boolean append) {
		_append = append;
	}

	public void setToAddCategoryIds(long[] toAddCategoryIds) {
		_toAddCategoryIds = toAddCategoryIds;
	}

	public void setToRemoveCategoryIds(long[] toRemoveCategoryIds) {
		_toRemoveCategoryIds = toRemoveCategoryIds;
	}

	private boolean _append;
	private long[] _toAddCategoryIds;
	private long[] _toRemoveCategoryIds;

}