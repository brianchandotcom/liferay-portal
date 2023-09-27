/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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