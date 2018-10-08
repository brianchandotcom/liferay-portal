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

package com.liferay.deprecated.modules.upgrade.internal;

import com.liferay.message.boards.service.MBThreadLocalService;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.service.ImageLocalService;
import com.liferay.portal.kernel.service.ReleaseLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(
	configurationPid = "com.liferay.deprecated.modules.upgrade.internal.DeprecatedModulesUpgradeConfiguration",
	immediate = true, service = UpgradeStepRegistrator.class
)
public class DeprecatedModulesUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		try {
			if (_deprecatedModulesUpgradeConfiguration.removeChatModuleData()) {
				Release release = _releaseLocalService.fetchRelease(
					"com.liferay.chat.service");

				if (release != null) {
					UpgradeChat upgradeChat = new UpgradeChat();

					upgradeChat.upgrade();

					CacheRegistryUtil.clear();
				}
			}

			if (_deprecatedModulesUpgradeConfiguration.
					removeMailReaderModuleData()) {

				Release release = _releaseLocalService.fetchRelease(
					"com.liferay.mail.reader.service");

				if (release != null) {
					UpgradeMailReader upgradeMailReader =
						new UpgradeMailReader();

					upgradeMailReader.upgrade();

					CacheRegistryUtil.clear();
				}
			}

			if (_deprecatedModulesUpgradeConfiguration.
					removeShoppingModuleData()) {

				Release release = _releaseLocalService.fetchRelease(
					"com.liferay.shopping.service");

				if (release != null) {
					UpgradeShopping upgradeShopping = new UpgradeShopping(
						_imageLocalService);

					upgradeShopping.upgrade();

					CacheRegistryUtil.clear();
				}
			}

			if (_deprecatedModulesUpgradeConfiguration.
					removePrivateMessagingModuleData()) {

				Release release = _releaseLocalService.fetchRelease(
					"com.liferay.social.privatemessaging.service");

				if (release != null) {
					UpgradePrivateMessaging upgradePrivateMessaging =
						new UpgradePrivateMessaging(_mbThreadLocalService);

					upgradePrivateMessaging.upgrade();

					CacheRegistryUtil.clear();
				}
			}

			if (_deprecatedModulesUpgradeConfiguration.
					removeTwitterModuleData()) {

				Release release = _releaseLocalService.fetchRelease(
					"com.liferay.twitter.service");

				if (release != null) {
					UpgradeTwitter upgradeTwitter = new UpgradeTwitter();

					upgradeTwitter.upgrade();

					CacheRegistryUtil.clear();
				}
			}
		}
		catch (UpgradeException ue) {
			ReflectionUtil.throwException(ue);
		}
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_deprecatedModulesUpgradeConfiguration =
			ConfigurableUtil.createConfigurable(
				DeprecatedModulesUpgradeConfiguration.class, properties);
	}

	private DeprecatedModulesUpgradeConfiguration
		_deprecatedModulesUpgradeConfiguration;

	@Reference
	private ImageLocalService _imageLocalService;

	@Reference
	private MBThreadLocalService _mbThreadLocalService;

	@Reference
	private ReleaseLocalService _releaseLocalService;

}