/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.blogs.demo.data.creator.internal;

import com.liferay.blogs.demo.data.creator.BlogsEntryDemoDataCreator;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.RandomUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

import java.util.Arrays;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Hernández
 */
@Component(
	property = "source=lorem-ipsum", service = BlogsEntryDemoDataCreator.class
)
public class LoremIpsumBlogsEntryDemoDataCreatorImpl
	extends BaseBlogsEntryDemoDataCreator {

	@Override
	public BlogsEntry create(long userId, long groupId)
		throws IOException, PortalException {

		String title = _getRandomElement(_titles);
		String subtitle = _getRandomElement(_subtitles);
		String content = _getRandomContent();

		return createBlogsEntry(userId, groupId, title, subtitle, content);
	}

	private static List<String> _read(String fileName) {
		return Arrays.asList(
			StringUtil.split(
				StringUtil.read(
					LoremIpsumBlogsEntryDemoDataCreatorImpl.class,
					"dependencies/lorem/ipsum/" + fileName + ".txt"),
				CharPool.NEW_LINE));
	}

	private String _getRandomContent() {
		int count = RandomUtil.nextInt(5) + 3;

		StringBundler sb = new StringBundler(count * 3);

		for (int i = 0; i < count; i++) {
			sb.append("<p>");
			sb.append(_getRandomElement(_paragraphs));
			sb.append("</p>");
		}

		return sb.toString();
	}

	private String _getRandomElement(List<String> list) {
		return list.get(RandomUtil.nextInt(list.size()));
	}

	private static final List<String> _paragraphs = _read("paragraphs");
	private static final List<String> _subtitles = _read("subtitles");
	private static final List<String> _titles = _read("titles");

}