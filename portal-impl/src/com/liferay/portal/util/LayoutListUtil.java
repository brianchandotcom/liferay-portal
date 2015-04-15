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

package com.liferay.portal.util;

import com.liferay.portal.kernel.cache.Lifecycle;
import com.liferay.portal.kernel.cache.ThreadLocalCache;
import com.liferay.portal.kernel.cache.ThreadLocalCacheManager;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
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
public class LayoutListUtil {

	public static List<LayoutDescription> getLayoutDescriptions(
		long groupId, boolean privateLayout, String rootNodeName,
		Locale locale) {

		ThreadLocalCache<List<LayoutDescription>> threadLocalCache =
			ThreadLocalCacheManager.getThreadLocalCache(
				Lifecycle.REQUEST, LayoutListUtil.class.getName());

		String cacheKey = buildCacheKey(groupId, rootNodeName, locale);

		List<LayoutDescription> layoutDescriptions = threadLocalCache.get(
			cacheKey);

		if (layoutDescriptions == null) {
			layoutDescriptions = doGetLayoutDescriptions(
				groupId, rootNodeName, locale);

			threadLocalCache.put(cacheKey, layoutDescriptions);
		}

		return layoutDescriptions;
	}

	protected static String buildCacheKey(
		long groupId, String rootNodeName, Locale locale) {

		StringBundler sb = new StringBundler(7);

		sb.append(StringUtil.toHexString(groupId));
		sb.append(StringPool.POUND);
		sb.append(rootNodeName);
		sb.append(StringPool.POUND);
		sb.append(LocaleUtil.toLanguageId(locale));

		return sb.toString();
	}

	protected static List<LayoutDescription> doGetLayoutDescriptions(
		long groupId, String rootNodeName, Locale locale) {

		List<LayoutDescription> layoutDescriptions = new ArrayList<>();

		List<Layout> layouts = new ArrayList<>(
			LayoutLocalServiceUtil.getLayouts(groupId));

		Deque<ObjectValuePair<Layout, Integer>> deque = new LinkedList<>();

		Layout rootLayout = new LayoutImpl();

		rootLayout.setPlid(LayoutConstants.DEFAULT_PLID);
		rootLayout.setName(rootNodeName);

		deque.push(new ObjectValuePair<Layout, Integer>(rootLayout, 0));

		ObjectValuePair<Layout, Integer> objectValuePair = null;

		while ((objectValuePair = deque.pollFirst()) != null) {
			Layout currentLayout = objectValuePair.getKey();

			Integer currentDepth = objectValuePair.getValue();

			layoutDescriptions.add(
				new LayoutDescription(
					currentLayout.getPlid(), currentLayout.getName(locale),
					currentDepth));

			ListIterator<Layout> listIterator = layouts.listIterator(
				layouts.size());

			while (listIterator.hasPrevious()) {
				Layout previousLayout = listIterator.previous();

				if (previousLayout.getParentLayoutId() ==
						currentLayout.getLayoutId()) {

					listIterator.remove();

					deque.push(
						new ObjectValuePair<Layout, Integer>(
							previousLayout, currentDepth + 1));
				}
			}
		}

		return layoutDescriptions;
	}

}