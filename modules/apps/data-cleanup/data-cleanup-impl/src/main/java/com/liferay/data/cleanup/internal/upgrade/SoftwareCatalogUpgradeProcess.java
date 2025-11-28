/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.cleanup.internal.upgrade;

import com.liferay.message.boards.service.MBMessageLocalService;
import com.liferay.portal.kernel.service.ImageLocalService;
import com.liferay.ratings.kernel.service.RatingsStatsLocalService;
import com.liferay.subscription.service.SubscriptionLocalService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Kevin Lee
 */
public class SoftwareCatalogUpgradeProcess extends BaseUpgradeProcess {

	public SoftwareCatalogUpgradeProcess(
		ImageLocalService imageLocalService,
		MBMessageLocalService mbMessageLocalService,
		RatingsStatsLocalService ratingsStatsLocalService,
		SubscriptionLocalService subscriptionLocalService) {

		_imageLocalService = imageLocalService;
		_mbMessageLocalService = mbMessageLocalService;
		_ratingsStatsLocalService = ratingsStatsLocalService;
		_subscriptionLocalService = subscriptionLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_deleteImages();
		_deleteSocial();

		removePortletData(
			null, new String[] {"98"},
			new String[] {"com.liferay.portlet.softwarecatalog"});

		removeServiceData(
			null, new String[] {"com.liferay.softwarecatalog.service"},
			new String[] {
				"com.liferay.portlet.softwarecatalog",
				"com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion",
				"com.liferay.portlet.softwarecatalog.model.SCLicense",
				"com.liferay.portlet.softwarecatalog.model.SCProductEntry",
				"com.liferay.portlet.softwarecatalog.model.SCProductScreenshot",
				"com.liferay.portlet.softwarecatalog.model.SCProductVersion"
			},
			new String[] {
				"SCFrameworkVersion", "SCFrameworkVersi_SCProductVers",
				"SCLicense", "SCLicenses_SCProductEntries", "SCProductEntry",
				"SCProductScreenshot", "SCProductVersion"
			});
	}

	private void _deleteImages() throws Exception {
		if (!hasTable("SCProductScreenshot")) {
			return;
		}

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select fullImageId, thumbnailId from SCProductScreenshot");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				long fullImageId = resultSet.getLong("fullImageId");

				_imageLocalService.deleteImage(fullImageId);

				long thumbnailId = resultSet.getLong("thumbnailId");

				_imageLocalService.deleteImage(thumbnailId);
			}
		}
	}

	private void _deleteSocial() throws Exception {
		if (!hasTable("SCProductEntry")) {
			return;
		}

		String className =
			"com.liferay.portlet.softwarecatalog.model.SCProductEntry";

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select companyId, productEntryId from SCProductEntry");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				long productEntryId = resultSet.getLong("productEntryId");

				_mbMessageLocalService.deleteDiscussionMessages(
					className, productEntryId);

				_ratingsStatsLocalService.deleteStats(
					className, productEntryId);

				long companyId = resultSet.getLong("companyId");

				_subscriptionLocalService.deleteSubscriptions(
					companyId, className, productEntryId);
			}
		}
	}

	private final ImageLocalService _imageLocalService;
	private final MBMessageLocalService _mbMessageLocalService;
	private final RatingsStatsLocalService _ratingsStatsLocalService;
	private final SubscriptionLocalService _subscriptionLocalService;

}