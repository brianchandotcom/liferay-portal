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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.RandomUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Hernández
 */
@Component(
	property = {"source=lorem-ipsum"}, service = BlogsEntryDemoDataCreator.class
)
public class LoremIpsumBlogsEntryDemoDataCreatorImpl
	extends BaseBlogsEntryDemoDataCreator {

	@Override
	public BlogsEntry create(long userId, long groupId)
		throws IOException, PortalException {

		String title = _getRandomElement(_entryTitles);

		String subtitle = _getRandomElement(_entrySubtitles);

		String content = _getRandomContent();

		return createBlogsEntry(userId, groupId, title, subtitle, content);
	}

	private String _getRandomContent() {
		int numberOfParagraphs = RandomUtil.nextInt(5) + 3;

		StringBundler sb = new StringBundler(numberOfParagraphs * 3);

		for (int i = 0; i < numberOfParagraphs; i++) {
			sb.append("<p>");
			sb.append(_getRandomElement(_entryParagraphs));
			sb.append("</p>");
		}

		return sb.toString();
	}

	private String _getRandomElement(List<String> list) {
		return list.get(RandomUtil.nextInt(list.size()));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LoremIpsumBlogsEntryDemoDataCreatorImpl.class);

	private static List<String> _entryParagraphs = new ArrayList<>();
	private static List<String> _entrySubtitles = new ArrayList<>();
	private static List<String> _entryTitles = new ArrayList<>();

	static {
		_entryParagraphs.addAll(
			_getAllLines("dependencies/lorem/ipsum/paragraphs.txt"));
		_entrySubtitles.addAll(
			_getAllLines("dependencies/lorem/ipsum/subtitles.txt"));
		_entryTitles.addAll(
			_getAllLines("dependencies/lorem/ipsum/titles.txt"));
	}

	private static List<String> _getAllLines(String fileName) {
		try (InputStream is =
				LoremIpsumBlogsEntryDemoDataCreatorImpl.class.
					getResourceAsStream(fileName)) {

			String fileContent = StringUtil.read(is);

			return Arrays.asList(
				StringUtil.split(fileContent, CharPool.NEW_LINE));
		}
		catch (IOException ioe) {
			if (_log.isWarnEnabled()) {
				_log.warn(ioe);
			}

			return new ArrayList<>();
		}
	}

}