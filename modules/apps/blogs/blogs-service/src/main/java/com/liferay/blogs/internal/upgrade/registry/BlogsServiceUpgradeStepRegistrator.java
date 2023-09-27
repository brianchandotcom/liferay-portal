/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.blogs.internal.upgrade.registry;

import com.liferay.blogs.internal.upgrade.v1_1_0.UpgradeClassNames;
import com.liferay.blogs.internal.upgrade.v1_1_2.BlogsImagesUpgradeProcess;
import com.liferay.blogs.internal.upgrade.v2_0_0.util.BlogsEntryTable;
import com.liferay.blogs.internal.upgrade.v2_0_0.util.BlogsStatsUserTable;
import com.liferay.blogs.internal.upgrade.v2_2_0.BlogsEntryExternalReferenceCodeUpgradeProcess;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.comment.upgrade.DiscussionSubscriptionClassNameUpgradeProcess;
import com.liferay.document.library.kernel.store.Store;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.message.boards.model.MBDiscussion;
import com.liferay.petra.function.UnsafeBiFunction;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepository;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ImageLocalService;
import com.liferay.portal.kernel.upgrade.BaseSQLServerDatetimeUpgradeProcess;
import com.liferay.portal.kernel.upgrade.CTModelUpgradeProcess;
import com.liferay.portal.kernel.upgrade.DummyUpgradeProcess;
import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
import com.liferay.portal.kernel.upgrade.MVCCVersionUpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.subscription.service.SubscriptionLocalService;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = UpgradeStepRegistrator.class)
public class BlogsServiceUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register("0.0.1", "1.0.0", new UpgradeClassNames());

		registry.register(
			"1.0.0", "1.1.0",
			new com.liferay.blogs.internal.upgrade.v1_1_0.
				BlogsEntryUpgradeProcess(
					_classNameLocalService, _friendlyURLEntryLocalService));

		registry.register(
			"1.1.0", "1.1.1",
			UpgradeProcessFactory.alterColumnType(
				"BlogsEntry", "urlTitle", "VARCHAR(255) null"));

		registry.register(
			"1.1.1", "1.1.2",
			new BlogsImagesUpgradeProcess(
				_imageLocalService, _portletFileRepository));

		registry.register(
			"1.1.2", "1.1.3",
			new DiscussionSubscriptionClassNameUpgradeProcess(
				_classNameLocalService, _subscriptionLocalService,
				BlogsEntry.class.getName(),
				_getUpgradeDiscussionSubscriptionClassNameUnsafeBiFunction()));

		registry.register(
			"1.1.3", "2.0.0",
			new BaseSQLServerDatetimeUpgradeProcess(
				new Class<?>[] {
					BlogsEntryTable.class, BlogsStatsUserTable.class
				}));

		registry.register("2.0.0", "2.0.1", new DummyUpgradeProcess());

		registry.register(
			"2.0.1", "2.1.0",
			new MVCCVersionUpgradeProcess() {

				@Override
				protected String[] getTableNames() {
					return new String[] {"BlogsEntry", "BlogsStatsUser"};
				}

			});

		registry.register(
			"2.1.0", "2.1.1",
			UpgradeProcessFactory.alterColumnType(
				"BlogsEntry", "title", "VARCHAR(255) null"));

		registry.register("2.1.1", "2.1.2", new DummyUpgradeStep());

		registry.register(
			"2.1.2", "2.2.0",
			new BlogsEntryExternalReferenceCodeUpgradeProcess());

		registry.register(
			"2.2.0", "3.0.0",
			UpgradeProcessFactory.dropTables("BlogsStatsUser"));

		registry.register(
			"3.0.0", "3.1.0", new CTModelUpgradeProcess("BlogsEntry"));
	}

	private UnsafeBiFunction<String, Connection, Boolean, Exception>
		_getUpgradeDiscussionSubscriptionClassNameUnsafeBiFunction() {

		return (className, connection) -> {
			try (PreparedStatement preparedStatement =
					connection.prepareStatement(
						SQLTransformer.transform(
							StringBundler.concat(
								"update Subscription set classNameId = ? ",
								"where classNameId = ? and classPK not in ",
								"(select groupId from Group_ where site = ",
								"[$TRUE$])")))) {

				preparedStatement.setLong(
					1,
					_classNameLocalService.getClassNameId(
						MBDiscussion.class.getName() + StringPool.UNDERLINE +
							BlogsEntry.class.getName()));
				preparedStatement.setLong(
					2, _classNameLocalService.getClassNameId(className));

				preparedStatement.executeUpdate();
			}

			return true;
		};
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private FriendlyURLEntryLocalService _friendlyURLEntryLocalService;

	@Reference
	private ImageLocalService _imageLocalService;

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED)
	private ModuleServiceLifecycle _moduleServiceLifecycle;

	@Reference
	private PortletFileRepository _portletFileRepository;

	@Reference(target = "(default=true)")
	private Store _store;

	@Reference
	private SubscriptionLocalService _subscriptionLocalService;

}