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
	configurationPid = "com.liferay.data.cleanup.internal.upgrade.DataCleanUpConfiguration",
	immediate = true, service = UpgradeStepRegistrator.class
)
public class DataCleanUp implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		try {
			_removeModuleData(
				_dataCleanUpConfiguration::removeChatModuleData,
				"com.liferay.chat.service", CleanUpChat::new);

			_removeModuleData(
				_dataCleanUpConfiguration::removeDictionaryModuleData,
				"com.liferay.dictionary.web", CleanUpDictionary::new);

			_removeModuleData(
				_dataCleanUpConfiguration::removeDirectoryModuleData,
				"com.liferay.directory.web", CleanUpDirectory::new);

			_removeModuleData(
				_dataCleanUpConfiguration::removeInvitationModuleData,
				"com.liferay.invitation.web", CleanUpInvitation::new);

			_removeModuleData(
				_dataCleanUpConfiguration::removeMailReaderModuleData,
				"com.liferay.mail.reader.service", CleanUpMailReader::new);

			_removeModuleData(
				_dataCleanUpConfiguration::removePrivateMessagingModuleData,
				"com.liferay.social.privatemessaging.service",
				() -> new CleanUpPrivateMessaging(_mbThreadLocalService));

			_removeModuleData(
				_dataCleanUpConfiguration::removeShoppingModuleData,
				"com.liferay.shopping.service",
				() -> new CleanUpShopping(_imageLocalService));

			_removeModuleData(
				_dataCleanUpConfiguration::removeTwitterModuleData,
				"com.liferay.twitter.service", CleanUpTwitter::new);
		}
		catch (UpgradeException upgradeException) {
			ReflectionUtil.throwException(upgradeException);
		}
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_dataCleanUpConfiguration = ConfigurableUtil.createConfigurable(
			DataCleanUpConfiguration.class, properties);
	}

	private void _removeModuleData(
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

	private DataCleanUpConfiguration _dataCleanUpConfiguration;

	@Reference
	private ImageLocalService _imageLocalService;

	@Reference
	private MBThreadLocalService _mbThreadLocalService;

	@Reference
	private ReleaseLocalService _releaseLocalService;

}