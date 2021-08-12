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

package com.liferay.blogs.internal.upgrade.v1_1_0;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.portal.kernel.model.ModelHintsUtil;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Adolfo Pérez
 */
public class BlogsEntryUpgradeProcess extends UpgradeProcess {

	public BlogsEntryUpgradeProcess(
		ClassNameLocalService classNameLocalService,
		FriendlyURLEntryLocalService friendlyURLEntryLocalService) {

		_classNameLocalService = classNameLocalService;
		_friendlyURLEntryLocalService = friendlyURLEntryLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select groupId, entryId, urlTitle from BlogsEntry")) {

			ResultSet resultSet = preparedStatement1.executeQuery();

			while (resultSet.next()) {
				long groupId = resultSet.getLong(1);
				long classPK = resultSet.getLong(2);
				String urlTitle = resultSet.getString(3);

				long classNameId = PortalUtil.getClassNameId(
					BlogsEntry.class.getName());

				FriendlyURLEntry existingFriendlyURLEntry =
					_friendlyURLEntryLocalService.fetchFriendlyURLEntry(
						groupId, classNameId, urlTitle);

				if (existingFriendlyURLEntry != null) {
					urlTitle = _friendlyURLEntryLocalService.getUniqueUrlTitle(
						groupId, classNameId, classPK, urlTitle);
				}

				urlTitle = _getUniqueUrlTitle(classPK, groupId, urlTitle);

				_friendlyURLEntryLocalService.addFriendlyURLEntry(
					groupId, BlogsEntry.class, classPK, urlTitle,
					new ServiceContext());
			}
		}
	}

	private String _getUniqueUrlTitle(
		long entryId, long groupId, String title) {

		String urlTitle = null;

		if (title == null) {
			urlTitle = String.valueOf(entryId);
		}
		else {
			urlTitle = StringUtil.toLowerCase(title.trim());

			if (Validator.isNull(urlTitle) || Validator.isNumber(urlTitle) ||
				urlTitle.equals("rss")) {

				urlTitle = String.valueOf(entryId);
			}
			else {
				urlTitle =
					FriendlyURLNormalizerUtil.normalizeWithPeriodsAndSlashes(
						urlTitle);
			}

			urlTitle = ModelHintsUtil.trimString(
				BlogsEntry.class.getName(), "urlTitle", urlTitle);
		}

		long classNameId = _classNameLocalService.getClassNameId(
			BlogsEntry.class);

		return _friendlyURLEntryLocalService.getUniqueUrlTitle(
			groupId, classNameId, entryId, urlTitle, null);
	}

	private final ClassNameLocalService _classNameLocalService;
	private final FriendlyURLEntryLocalService _friendlyURLEntryLocalService;

}