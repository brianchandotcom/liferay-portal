/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.util.comparator;

import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.Layout;

/**
 * @author Brian Wing Shun Chan
 */
public class LayoutPriorityComparator extends OrderByComparator {

	public static final String ORDER_BY_ASC = "Layout.priority ASC";

	public static final String[] ORDER_BY_FIELDS = {"priority"};

	public LayoutPriorityComparator(Layout layout, boolean lessThan) {
		_layout = layout;
		_lessThan = lessThan;
	}

	@Override
	public int compare(Object obj1, Object obj2) {
		Layout layout1 = (Layout)obj1;
		Layout layout2 = (Layout)obj2;

		int priority1 = layout1.getPriority();
		int priority2 = layout2.getPriority();

		if (priority1 > priority2) {
			return 1;
		}
		else if (priority1 < priority2) {
			return -1;
		}
		else {
			if (_layout.equals(layout1)) {
				if (_lessThan) {
					return 1;
				}
				else {
					return -1;
				}
			}
			else if (_layout.equals(layout2)) {
				if (_lessThan) {
					return -1;
				}
				else {
					return 1;
				}
			}

			return 0;
		}
	}

	@Override
	public String getOrderBy() {
		return ORDER_BY_ASC;
	}

	@Override
	public String[] getOrderByFields() {
		return ORDER_BY_FIELDS;
	}

	@Override
	public boolean isAscending() {
		return true;
	}

	private Layout _layout;
	private boolean _lessThan;

}