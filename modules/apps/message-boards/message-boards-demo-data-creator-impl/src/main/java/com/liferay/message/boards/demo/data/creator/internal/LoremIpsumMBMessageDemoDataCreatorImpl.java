/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.demo.data.creator.internal;

import com.liferay.message.boards.constants.MBMessageConstants;
import com.liferay.message.boards.demo.data.creator.MBMessageDemoDataCreator;
import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.service.MBCategoryLocalService;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.RandomUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

import java.util.Arrays;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(
	property = "source=lorem-ipsum", service = MBMessageDemoDataCreator.class
)
public class LoremIpsumMBMessageDemoDataCreatorImpl
	extends BaseMBMessageDemoDataCreator {

	@Override
	public MBMessage create(long userId, long categoryId)
		throws IOException, PortalException {

		MBCategory category = _mbCategoryLocalService.getCategory(categoryId);

		String title = _getRandomElement(_titles);
		String content = _getRandomContent();

		return createMessage(
			userId, category.getGroupId(), categoryId,
			MBMessageConstants.DEFAULT_PARENT_MESSAGE_ID, title, content);
	}

	@Override
	public MBMessage create(long userId, long categoryId, long parentMessageId)
		throws IOException, PortalException {

		MBCategory category = _mbCategoryLocalService.getCategory(categoryId);

		String title = _getRandomElement(_titles);
		String content = _getRandomContent();

		return createMessage(
			userId, category.getGroupId(), categoryId, parentMessageId, title,
			content);
	}

	private static List<String> _read(String fileName) {
		return Arrays.asList(
			StringUtil.split(
				StringUtil.read(
					LoremIpsumMBMessageDemoDataCreatorImpl.class,
					"dependencies/lorem/ipsum/" + fileName + ".txt"),
				CharPool.NEW_LINE));
	}

	private String _getRandomContent() {
		int count = RandomUtil.nextInt(5) + 3;

		StringBundler sb = new StringBundler(count * 2);

		for (int i = 0; i < count; i++) {
			sb.append(_getRandomElement(_paragraphs));
			sb.append("\n");
		}

		return sb.toString();
	}

	private String _getRandomElement(List<String> list) {
		return list.get(RandomUtil.nextInt(list.size()));
	}

	private static final List<String> _paragraphs = _read("paragraphs");
	private static final List<String> _titles = _read("titles");

	@Reference
	private MBCategoryLocalService _mbCategoryLocalService;

}