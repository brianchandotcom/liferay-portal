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

package com.liferay.data.cleanup.internal.upgrade.executor;

import com.liferay.data.cleanup.internal.configuration.DataCleanupConfiguration;
import com.liferay.data.cleanup.internal.upgrade.ChatUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.DictionaryUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.DirectoryUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.ImageEditorUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.InvitationUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.MailReaderUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.OpenSocialUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.PrivateMessagingUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.ShoppingUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.SoftwareCatalogUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.TwitterUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.UpgradeHelloWorld;
import com.liferay.data.cleanup.internal.upgrade.util.ConfigurationUtil;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.message.boards.service.MBMessageLocalService;
import com.liferay.message.boards.service.MBThreadLocalService;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.service.ImageLocalService;
import com.liferay.portal.kernel.service.ReleaseLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.ratings.kernel.service.RatingsStatsLocalService;
import com.liferay.subscription.service.SubscriptionLocalService;

import java.util.Map;
import java.util.function.Supplier;

import org.apache.felix.cm.PersistenceManager;

import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(
	configurationPid = "com.liferay.data.cleanup.internal.configuration.DataCleanupConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE, service = {}
)
public class DataCleanupExecutor {

	@Activate
	protected void activate(Map<String, Object> properties) throws Exception {
		DataCleanupConfiguration dataCleanupConfiguration =
			ConfigurableUtil.createConfigurable(
				DataCleanupConfiguration.class, properties);

		ConfigurationUtil.deleteConfiguration(
			_configurationAdmin, _persistenceManager,
			DataCleanupConfiguration.class.getName());

		_cleanUpModuleData(
			dataCleanupConfiguration::cleanUpChatModuleData,
			"com.liferay.chat.service", ChatUpgradeProcess::new);
		_cleanUpModuleData(
			dataCleanupConfiguration::cleanUpDictionaryModuleData,
			"com.liferay.dictionary.web", DictionaryUpgradeProcess::new);
		_cleanUpModuleData(
			dataCleanupConfiguration::cleanUpDirectoryModuleData,
			"com.liferay.directory.web", DirectoryUpgradeProcess::new);
		_cleanUpModuleData(
			dataCleanupConfiguration::cleanUpImageEditorModuleData,
			"com.liferay.frontend.image.editor.web",
			ImageEditorUpgradeProcess::new);
		_cleanUpModuleData(
			dataCleanupConfiguration::cleanUpHelloWorldModuleData,
			"com.liferay.hello.world.web", UpgradeHelloWorld::new);
		_cleanUpModuleData(
			dataCleanupConfiguration::cleanUpInvitationModuleData,
			"com.liferay.invitation.web", InvitationUpgradeProcess::new);
		_cleanUpModuleData(
			dataCleanupConfiguration::cleanUpMailReaderModuleData,
			"com.liferay.mail.reader.service", MailReaderUpgradeProcess::new);
		_cleanUpModuleData(
			dataCleanupConfiguration::cleanUpShoppingModuleData,
			"com.liferay.shopping.service",
			() -> new ShoppingUpgradeProcess(_imageLocalService));
		_cleanUpModuleData(
			dataCleanupConfiguration::cleanUpPrivateMessagingModuleData,
			"com.liferay.social.privatemessaging.service",
			() -> new PrivateMessagingUpgradeProcess(_mbThreadLocalService));
		_cleanUpModuleData(
			dataCleanupConfiguration::cleanUpSoftwareCatalogModuleData,
			"com.liferay.softwarecatalog.service",
			() -> new SoftwareCatalogUpgradeProcess(
				_imageLocalService, _mbMessageLocalService,
				_ratingsStatsLocalService, _subscriptionLocalService));
		_cleanUpModuleData(
			dataCleanupConfiguration::cleanUpTwitterModuleData,
			"com.liferay.twitter.service", TwitterUpgradeProcess::new);
		_cleanUpModuleData(
			dataCleanupConfiguration::cleanUpOpenSocialModuleData,
			"opensocial-portlet",
			() -> new OpenSocialUpgradeProcess(_expandoTableLocalService));
	}

	private void _cleanUpModuleData(
			Supplier<Boolean> booleanSupplier, String servletContextName,
			Supplier<UpgradeProcess> upgradeProcessSupplier)
		throws Exception {

		if (booleanSupplier.get()) {
			Release release = _releaseLocalService.fetchRelease(
				servletContextName);

			if (release != null) {
				UpgradeProcess upgradeProcess = upgradeProcessSupplier.get();

				upgradeProcess.upgrade();

				CacheRegistryUtil.clear();
			}
		}
	}

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	@Reference
	private ExpandoTableLocalService _expandoTableLocalService;

	@Reference
	private ImageLocalService _imageLocalService;

	@Reference
	private MBMessageLocalService _mbMessageLocalService;

	@Reference
	private MBThreadLocalService _mbThreadLocalService;

	@Reference
	private PersistenceManager _persistenceManager;

	@Reference
	private RatingsStatsLocalService _ratingsStatsLocalService;

	@Reference
	private ReleaseLocalService _releaseLocalService;

	@Reference
	private SubscriptionLocalService _subscriptionLocalService;

}