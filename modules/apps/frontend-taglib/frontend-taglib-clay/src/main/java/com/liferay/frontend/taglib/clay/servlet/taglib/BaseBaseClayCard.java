/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.clay.servlet.taglib;

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