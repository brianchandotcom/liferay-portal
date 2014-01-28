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
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.service.LayoutLocalServiceUtil;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class LayoutLister {

	public List<LayoutDescription> getLayoutDescriptions(
			long groupId, boolean privateLayout, String rootNodeName,
			Locale locale)
		throws PortalException, SystemException {

		List<LayoutDescription> list = new ArrayList<LayoutDescription>();

		List<Layout> layouts = new ArrayList<Layout>(
			LayoutLocalServiceUtil.getLayouts(groupId, privateLayout));

		Deque<ObjectValuePair<Layout, Integer>> deque =
			new LinkedList<ObjectValuePair<Layout, Integer>>();

		Layout rootLayout = new LayoutImpl();

		rootLayout.setPlid(LayoutConstants.DEFAULT_PLID);
		rootLayout.setName(rootNodeName);

		deque.push(new ObjectValuePair<Layout, Integer>(rootLayout, 0));

		ObjectValuePair<Layout, Integer> current = null;

		while ((current = deque.pollFirst()) != null) {
			Layout currentLayout = current.getKey();

			Integer currentDepth = current.getValue();

			list.add(
				new LayoutDescription(
					currentLayout.getPlid(), currentLayout.getName(locale),
					currentDepth));

			ListIterator<Layout> listIterator = layouts.listIterator(
				layouts.size());

			while (listIterator.hasPrevious()) {
				Layout layout = listIterator.previous();

				if (layout.getParentLayoutId() == currentLayout.getLayoutId()) {
					listIterator.remove();

					deque.push(
						new ObjectValuePair<Layout, Integer>(
							layout, currentDepth + 1));
				}
			}
		}

		return list;
	}

}