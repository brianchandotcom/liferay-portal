/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.model.impl;

import com.liferay.object.model.ObjectViewColumn;
import com.liferay.object.model.ObjectViewFilterColumn;
import com.liferay.object.model.ObjectViewSortColumn;

import java.util.List;

/**
 * @author Gabriel Albuquerque
 */
public class ObjectViewImpl extends ObjectViewBaseImpl {

	@Override
	public List<ObjectViewColumn> getObjectViewColumns() {
		return _objectViewColumns;
	}

	public List<ObjectViewFilterColumn> getObjectViewFilterColumns() {
		return _objectViewFilterColumns;
	}

	public List<ObjectViewSortColumn> getObjectViewSortColumns() {
		return _objectViewSortColumns;
	}

	@Override
	public void setObjectViewColumns(List<ObjectViewColumn> objectViewColumns) {
		_objectViewColumns = objectViewColumns;
	}

	public void setObjectViewFilterColumns(
		List<ObjectViewFilterColumn> objectViewFilterColumns) {

		_objectViewFilterColumns = objectViewFilterColumns;
	}

	public void setObjectViewSortColumns(
		List<ObjectViewSortColumn> objectViewSortColumns) {

		_objectViewSortColumns = objectViewSortColumns;
	}

	private List<ObjectViewColumn> _objectViewColumns;
	private List<ObjectViewFilterColumn> _objectViewFilterColumns;
	private List<ObjectViewSortColumn> _objectViewSortColumns;

}