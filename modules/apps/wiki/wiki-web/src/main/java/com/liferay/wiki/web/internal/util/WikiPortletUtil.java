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

package com.liferay.wiki.web.internal.util;

import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.util.comparator.NodeLastPostDateComparator;
import com.liferay.wiki.util.comparator.NodeNameComparator;
import com.liferay.wiki.util.comparator.PageCreateDateComparator;
import com.liferay.wiki.util.comparator.PageModifiedDateComparator;
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

		if (orderByCol.equals("lastPostDate")) {
			return new NodeLastPostDateComparator(orderByAsc);
		}

		if (orderByCol.equals("name")) {
			return new NodeNameComparator(orderByAsc);
		}

		return null;
	}

	public static OrderByComparator<WikiPage> getPageOrderByComparator(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		if (orderByCol.equals("createDate")) {
			return new PageCreateDateComparator(orderByAsc);
		}

		if (orderByCol.equals("modifiedDate")) {
			return new PageModifiedDateComparator(orderByAsc);
		}

		if (orderByCol.equals("title")) {
			return new PageTitleComparator(orderByAsc);
		}

		if (orderByCol.equals("version")) {
			return new PageVersionComparator(orderByAsc);
		}

		return null;
	}

}