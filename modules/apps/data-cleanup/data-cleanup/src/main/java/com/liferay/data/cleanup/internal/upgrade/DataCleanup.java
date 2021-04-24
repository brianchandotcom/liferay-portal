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

package com.liferay.data.cleanup.internal.upgrade;

import com.liferay.data.cleanup.internal.configuration.DataCleanupConfiguration;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.message.boards.service.MBThreadLocalService;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.service.ImageLocalService;
import com.liferay.portal.kernel.service.ReleaseLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import java.util.Map;
import java.util.function.Supplier;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(
	configurationPid = "com.liferay.data.cleanup.internal.configuration.DataCleanupConfiguration",
	immediate = true, service = UpgradeStepRegistrator.class
)
public class DataCleanup implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		try {
			_cleanUpModuleData(
				_dataCleanupConfiguration::cleanUpChatModuleData,
				"com.liferay.chat.service", ChatUpgradeProcess::new);

			_cleanUpModuleData(
				_dataCleanupConfiguration::cleanUpDictionaryModuleData,
				"com.liferay.dictionary.web", DictionaryUpgradeProcess::new);

			_cleanUpModuleData(
				_dataCleanupConfiguration::cleanUpDirectoryModuleData,
				"com.liferay.directory.web", DirectoryUpgradeProcess::new);

			_cleanUpModuleData(
				_dataCleanupConfiguration::cleanUpImageEditorModuleData,
				"com.liferay.frontend.image.editor.web",
				ImageEditorUpgradeProcess::new);

			_cleanUpModuleData(
				_dataCleanupConfiguration::cleanUpHelloWorldModuleData,
				"com.liferay.hello.world.web", UpgradeHelloWorld::new);

			_cleanUpModuleData(
				_dataCleanupConfiguration::cleanUpInvitationModuleData,
				"com.liferay.invitation.web", InvitationUpgradeProcess::new);

			_cleanUpModuleData(
				_dataCleanupConfiguration::cleanUpMailReaderModuleData,
				"com.liferay.mail.reader.service",
				MailReaderUpgradeProcess::new);

			_cleanUpModuleData(
				_dataCleanupConfiguration::cleanUpPrivateMessagingModuleData,
				"com.liferay.social.privatemessaging.service",
				() -> new PrivateMessagingUpgradeProcess(
					_mbThreadLocalService));

			_cleanUpModuleData(
				_dataCleanupConfiguration::cleanUpShoppingModuleData,
				"com.liferay.shopping.service",
				() -> new ShoppingUpgradeProcess(_imageLocalService));

			_cleanUpModuleData(
				_dataCleanupConfiguration::cleanUpTwitterModuleData,
				"com.liferay.twitter.service", TwitterUpgradeProcess::new);
		}
		catch (UpgradeException upgradeException) {
			ReflectionUtil.throwException(upgradeException);
		}
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_dataCleanupConfiguration = ConfigurableUtil.createConfigurable(
			DataCleanupConfiguration.class, properties);
	}

	private void _cleanUpModuleData(
			Supplier<Boolean> booleanSupplier, String servletContextName,
			Supplier<UpgradeProcess> upgradeProcessSupplier)
		throws UpgradeException {

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

	private DataCleanupConfiguration _dataCleanupConfiguration;

	@Reference
	private ImageLocalService _imageLocalService;

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

	@Reference
	private MBThreadLocalService _mbThreadLocalService;

	@Reference
	private ReleaseLocalService _releaseLocalService;

}