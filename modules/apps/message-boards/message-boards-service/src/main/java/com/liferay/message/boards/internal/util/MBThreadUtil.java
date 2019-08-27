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

package com.liferay.message.boards.internal.util;

import com.liferay.message.boards.constants.MBCategoryConstants;
import com.liferay.message.boards.model.MBThread;
import com.liferay.message.boards.service.persistence.MBThreadPersistence;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;

import java.util.List;

/**
 * @author Preston Crary
 */
public class MBThreadUtil {

	public static List<MBThread> getGroupThreads(
		MBThreadPersistence mbThreadPersistence, long groupId,
		QueryDefinition<MBThread> queryDefinition) {

		if (queryDefinition.isExcludeStatus()) {
			return mbThreadPersistence.findByG_NotC_NotS(
				groupId, MBCategoryConstants.DISCUSSION_CATEGORY_ID,
				queryDefinition.getStatus(), queryDefinition.getStart(),
				queryDefinition.getEnd());
		}

		return mbThreadPersistence.findByG_NotC_S(
			groupId, MBCategoryConstants.DISCUSSION_CATEGORY_ID,
			queryDefinition.getStatus(), queryDefinition.getStart(),
			queryDefinition.getEnd());
	}

	private MBThreadUtil() {
	}

}