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

package com.liferay.message.boards.demo.data.creator.internal;

import com.liferay.message.boards.constants.MBCategoryConstants;
import com.liferay.message.boards.demo.data.creator.RootMBCategoryDemoDataCreator;
import com.liferay.message.boards.model.MBCategory;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.RandomUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

import java.util.Arrays;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Sergio González
 */
@Component(
	property = "source=liferay", service = RootMBCategoryDemoDataCreator.class
)
public class LiferayRootMBCategoryDemoDataCreatorImpl
	extends BaseMBCategoryDemoDataCreatorImpl
	implements RootMBCategoryDemoDataCreator {

	@Override
	public MBCategory create(long userId, long groupId)
		throws IOException, PortalException {

		int randomIndex = RandomUtil.nextInt(
			Math.min(_names.size(), _descriptions.size()));

		String name = _names.get(randomIndex);
		String description = _descriptions.get(randomIndex);

		return createCategory(
			userId, groupId, MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
			name, description);
	}

	private static List<String> _read(String fileName) {
		return Arrays.asList(
			StringUtil.split(
				StringUtil.read(
					LiferayRootMBCategoryDemoDataCreatorImpl.class,
					"dependencies/liferay/" + fileName + ".txt"),
				CharPool.NEW_LINE));
	}

	private static final List<String> _descriptions = _read("descriptions");
	private static final List<String> _names = _read("names");

}