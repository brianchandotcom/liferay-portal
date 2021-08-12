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

package com.liferay.blogs.internal.upgrade;

import com.liferay.blogs.internal.upgrade.v1_1_0.UpgradeClassNames;
import com.liferay.blogs.internal.upgrade.v1_1_2.BlogsImagesUpgradeProcess;
import com.liferay.blogs.internal.upgrade.v2_0_0.util.BlogsEntryTable;
import com.liferay.blogs.internal.upgrade.v2_0_0.util.BlogsStatsUserTable;
import com.liferay.blogs.internal.upgrade.v2_2_0.BlogsEntryExternalReferenceCodeUpgradeProcess;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.comment.upgrade.UpgradeDiscussionSubscriptionClassName;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.message.boards.model.MBDiscussion;
import com.liferay.petra.function.UnsafeBiFunction;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepository;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ImageLocalService;
import com.liferay.portal.kernel.upgrade.BaseSQLServerDatetimeUpgradeProcess;
import com.liferay.portal.kernel.upgrade.DummyUpgradeProcess;
import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
import com.liferay.portal.kernel.upgrade.MVCCVersionUpgradeProcess;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portlet.documentlibrary.store.StoreFactory;
import com.liferay.subscription.service.SubscriptionLocalService;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class BlogsServiceUpgrade implements UpgradeStepRegistrator {

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
			new com.liferay.blogs.internal.upgrade.v1_1_1.
				BlogsEntryUpgradeProcess());

		registry.register(
			"1.1.1", "1.1.2",
			new BlogsImagesUpgradeProcess(
				_imageLocalService, _portletFileRepository));

		registry.register(
			"1.1.2", "1.1.3",
			new UpgradeDiscussionSubscriptionClassName(
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
				protected String[] getModuleTableNames() {
					return new String[] {"BlogsEntry", "BlogsStatsUser"};
				}

			});

		registry.register(
			"2.1.0", "2.1.1",
			new com.liferay.blogs.internal.upgrade.v2_1_1.
				BlogsEntryUpgradeProcess());

		registry.register("2.1.1", "2.1.2", new DummyUpgradeStep());

		registry.register(
			"2.1.2", "2.2.0",
			new BlogsEntryExternalReferenceCodeUpgradeProcess());
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
	private GroupLocalService _groupLocalService;

	@Reference
	private ImageLocalService _imageLocalService;

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED)
	private ModuleServiceLifecycle _moduleServiceLifecycle;

	@Reference
	private PortletFileRepository _portletFileRepository;

	@Reference(target = "(dl.store.impl.enabled=true)")
	private StoreFactory _storeFactory;

	@Reference
	private SubscriptionLocalService _subscriptionLocalService;

}