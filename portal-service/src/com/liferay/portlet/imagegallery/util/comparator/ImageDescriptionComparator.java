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

package com.liferay.portlet.imagegallery.util.comparator;

import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portlet.imagegallery.model.IGImage;

/**
 * @author Brian Wing Shun Chan
 */
public class ImageDescriptionComparator extends OrderByComparator {

	public static String ORDER_BY_ASC = "IGImage.description ASC";

	public static String ORDER_BY_DESC = "IGImage.description DESC";

	public static String[] ORDER_BY_FIELDS = {"description"};

	public ImageDescriptionComparator() {
		this(false);
	}

	public ImageDescriptionComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(Object obj1, Object obj2) {
		IGImage image1 = (IGImage)obj1;
		IGImage image2 = (IGImage)obj2;

		int value =
			image1.getDescription().toLowerCase().compareTo(
				image2.getDescription().toLowerCase());

		if (_ascending) {
			return value;
		}
		else {
			return -value;
		}
	}

	@Override
	public String getOrderBy() {
		if (_ascending) {
			return ORDER_BY_ASC;
		}
		else {
			return ORDER_BY_DESC;
		}
	}

	@Override
	public String[] getOrderByFields() {
		return ORDER_BY_FIELDS;
	}

	@Override
	public boolean isAscending() {
		return _ascending;
	}

	private boolean _ascending;

}