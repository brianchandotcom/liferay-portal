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

package com.liferay.blogs.demo.data.creator.internal;

import com.liferay.blogs.demo.data.creator.BlogsEntryDemoDataCreator;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Sergio González
 */
@Component(
	property = "source=creative-commons",
	service = BlogsEntryDemoDataCreator.class
)
public class CreativeCommonsBlogsEntryDemoDataCreatorImpl
	extends BaseBlogsEntryDemoDataCreator {

	@Activate
	public void activate(BundleContext bundleContext) {
		Collections.addAll(
			_availableIndexes, new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10});

		Collections.shuffle(_availableIndexes);
	}

	@Override
	public BlogsEntry create(long userId, long groupId)
		throws IOException, PortalException {

		int index = _getNextIndex();

		String title = _getRandomTitle(index);
		String subtitle = _getRandomSubtitle(index);
		String content = _getRandomContent(index);

		return createBlogsEntry(userId, groupId, title, subtitle, content);
	}

	private int _getNextIndex() {
		int index = _atomicInteger.getAndIncrement();

		if (index == (_availableIndexes.size() - 1)) {
			_atomicInteger.set(0);
		}

		return _availableIndexes.get(index);
	}

	private String _getRandomContent(int index) throws IOException {
		Class<?> clazz = getClass();

		String titlePath = StringBundler.concat(
			"com/liferay/blogs/demo/data/creator/internal/dependencies",
			"/creative/commons/content_", index, ".txt");

		return StringUtil.read(clazz.getClassLoader(), titlePath, false);
	}

	private String _getRandomSubtitle(int index) throws IOException {
		Class<?> clazz = getClass();

		String titlePath = StringBundler.concat(
			"com/liferay/blogs/demo/data/creator/internal/dependencies",
			"/creative/commons/subtitle_", index, ".txt");

		return StringUtil.read(clazz.getClassLoader(), titlePath, false);
	}

	private String _getRandomTitle(int index) throws IOException {
		Class<?> clazz = getClass();

		String titlePath = StringBundler.concat(
			"com/liferay/blogs/demo/data/creator/internal/dependencies",
			"/creative/commons/title_", index, ".txt");

		return StringUtil.read(clazz.getClassLoader(), titlePath, false);
	}

	private final AtomicInteger _atomicInteger = new AtomicInteger(0);
	private final List<Integer> _availableIndexes =
		new CopyOnWriteArrayList<>();

}