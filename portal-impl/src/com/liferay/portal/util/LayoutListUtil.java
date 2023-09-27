/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cache.thread.local.Lifecycle;
import com.liferay.portal.kernel.cache.thread.local.ThreadLocalCache;
import com.liferay.portal.kernel.cache.thread.local.ThreadLocalCacheManager;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.impl.LayoutImpl;

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

		String cacheKey = buildCacheKey(
			groupId, privateLayout, rootNodeName, locale);

		List<LayoutDescription> layoutDescriptions = threadLocalCache.get(
			cacheKey);

		if (layoutDescriptions == null) {
			layoutDescriptions = doGetLayoutDescriptions(
				groupId, privateLayout, rootNodeName, locale);

			threadLocalCache.put(cacheKey, layoutDescriptions);
		}

		return layoutDescriptions;
	}

	protected static String buildCacheKey(
		long groupId, boolean privateLayout, String rootNodeName,
		Locale locale) {

		return StringBundler.concat(
			StringUtil.toHexString(groupId), StringPool.POUND, privateLayout,
			StringPool.POUND, rootNodeName, StringPool.POUND,
			LocaleUtil.toLanguageId(locale));
	}

	protected static List<LayoutDescription> doGetLayoutDescriptions(
		long groupId, boolean privateLayout, String rootNodeName,
		Locale locale) {

		List<LayoutDescription> layoutDescriptions = new ArrayList<>();

		List<Layout> layouts = new ArrayList<>(
			LayoutLocalServiceUtil.getLayouts(groupId, privateLayout));

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