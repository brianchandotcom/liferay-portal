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

package com.liferay.wiki.web.util;

import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.util.comparator.NodeNameComparator;
import com.liferay.wiki.util.comparator.PageCreateDateComparator;
import com.liferay.wiki.util.comparator.PageTitleComparator;
import com.liferay.wiki.util.comparator.PageVersionComparator;

/**
 * @author Sergio González
 */
public class WikiPortletUtil {

	public static OrderByComparator<WikiNode> getNodeOrderByComparator(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<WikiNode> orderByComparator = null;

		if (orderByCol.equals("name")) {
			orderByComparator = new NodeNameComparator(orderByAsc);
		}

		return orderByComparator;
	}

	public static OrderByComparator<WikiPage> getPageOrderByComparator(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<WikiPage> orderByComparator = null;

		if (orderByCol.equals("modifiedDate")) {
			orderByComparator = new PageCreateDateComparator(orderByAsc);
		}
		else if (orderByCol.equals("title")) {
			orderByComparator = new PageTitleComparator(orderByAsc);
		}
		else if (orderByCol.equals("version")) {
			orderByComparator = new PageVersionComparator(orderByAsc);
		}

		return orderByComparator;
	}

}