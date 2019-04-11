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

package com.liferay.journal.web.asset.model;

import com.liferay.asset.kernel.model.BaseDDMStructureClassTypeReader;
import com.liferay.asset.kernel.model.ClassType;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.portal.kernel.model.Group;
import com.liferay.staging.StagingGroupHelper;
import com.liferay.staging.StagingGroupHelperUtil;

import java.util.List;
import java.util.Locale;

/**
 * @author Balázs Sáfrány-Kovalik
 */
public class JournalArticleClassTypeReader
	extends BaseDDMStructureClassTypeReader {

	public JournalArticleClassTypeReader(String className) {
		super(className);
	}

	@Override
	public List<ClassType> getAvailableClassTypes(
		long[] groupIds, Locale locale) {

		groupIds = _replaceGroupIds(groupIds);

		return super.getAvailableClassTypes(groupIds, locale);
	}

	private long[] _replaceGroupIds(long[] groupIds) {
		StagingGroupHelper stagingGroupHelper =
			StagingGroupHelperUtil.getStagingGroupHelper();

		groupIds = groupIds.clone();

		for (int i = 0; i < groupIds.length; i++) {
			if (stagingGroupHelper.isLocalStagingGroup(groupIds[i]) &&
				!stagingGroupHelper.isStagedPortlet(
					groupIds[i], JournalPortletKeys.JOURNAL)) {

				Group group = stagingGroupHelper.fetchLocalLiveGroup(
					groupIds[i]);

				groupIds[i] = group.getGroupId();
			}
		}

		return groupIds;
	}

}