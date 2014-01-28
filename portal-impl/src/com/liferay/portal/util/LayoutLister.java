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

	public List<LayoutDescription> getLayoutDescriptions(
			long groupId, boolean privateLayout, String rootNodeName,
			Locale locale)
		throws PortalException, SystemException {

		_locale = locale;

		_list = new ArrayList<LayoutDescription>();

		_list.add(
			new LayoutDescription(
				LayoutConstants.DEFAULT_PLID, rootNodeName, 0));

		List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
			groupId, privateLayout);

		_createList(layouts, LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, 0);

		return _list;
	}

	private void _createList(
			List<Layout> layouts, long parentLayoutId, int depth)
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
			}

			_list.add(
				new LayoutDescription(
					layout.getPlid(), layout.getName(_locale), depth));

			_createList(layouts, layout.getLayoutId(), depth);
		}
	}

	private List<LayoutDescription> _list;
	private Locale _locale;

}