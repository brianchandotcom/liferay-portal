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

package com.liferay.frontend.taglib.clay.servlet.taglib.soy;

import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.model.BaseModel;

/**
 * @author Eudaldo Alonso
 */
public abstract class BaseBaseClayCard implements BaseClayCard {

	public BaseBaseClayCard(BaseModel<?> baseModel, RowChecker rowChecker) {
		this.baseModel = baseModel;
		this.rowChecker = rowChecker;
	}

	@Override
	public String getInputName() {
		if (rowChecker == null) {
			return null;
		}

		return rowChecker.getRowIds();
	}

	@Override
	public String getInputValue() {
		if (rowChecker == null) {
			return null;
		}

		return String.valueOf(baseModel.getPrimaryKeyObj());
	}

	@Override
	public boolean isDisabled() {
		if (rowChecker == null) {
			return false;
		}

		return rowChecker.isDisabled(baseModel);
	}

	@Override
	public boolean isSelectable() {
		if (rowChecker == null) {
			return false;
		}

		return true;
	}

	@Override
	public boolean isSelected() {
		if (rowChecker == null) {
			return false;
		}

		return rowChecker.isChecked(baseModel);
	}

	protected final BaseModel<?> baseModel;
	protected final RowChecker rowChecker;

}