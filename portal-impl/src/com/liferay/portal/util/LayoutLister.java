/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.service.LayoutLocalServiceUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Brian Wing Shun Chan
 */
public class LayoutLister {

	public LayoutView getLayoutView(
			long groupId, boolean privateLayout, String rootNodeName,
			Locale locale)
		throws PortalException, SystemException {

		_locale = locale;
		_nodeId = 1;

		_list = new ArrayList<String>();

		_list.add(
			"1|0|0|" + LayoutConstants.DEFAULT_PLID + "|" + rootNodeName +
				"|0");

		List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
			groupId, privateLayout);

		_createList(
			layouts, LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, _nodeId, 0);

		return new LayoutView(_list, _depth);
	}

	private void _createList(
			List<Layout> layouts, long parentLayoutId, int parentId, int depth)
		throws PortalException, SystemException {

		List<Layout> matchedLayouts = new ArrayList<Layout>();

		for (Layout layout : layouts) {
			if (layout.getParentLayoutId() == parentLayoutId) {
				matchedLayouts.add(layout);
			}
		}

		for (int i = 0; i < matchedLayouts.size(); i++) {
			Layout layout = matchedLayouts.get(i);

			if (i == 0) {
				depth++;

				if (depth > _depth) {
					_depth = depth;
				}
			}

			StringBundler sb = new StringBundler(13);

			sb.append(++_nodeId);
			sb.append("|");
			sb.append(parentId);
			sb.append("|");

			if ((i + 1) == matchedLayouts.size()) {
				sb.append("1");
			}
			else {
				sb.append("0");
			}

			sb.append("|");
			sb.append(layout.getPlid());
			sb.append("|");
			sb.append(layout.getName(_locale));
			sb.append("|");
			//sb.append("9");
			sb.append("11");
			sb.append("|");
			sb.append(depth);

			_list.add(sb.toString());

			_createList(layouts, layout.getLayoutId(), _nodeId, depth);
		}
	}

	private int _depth;
	private List<String> _list;
	private Locale _locale;
	private int _nodeId;

}