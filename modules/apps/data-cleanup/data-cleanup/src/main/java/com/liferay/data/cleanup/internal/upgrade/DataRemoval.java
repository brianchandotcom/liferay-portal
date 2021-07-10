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

import com.liferay.data.cleanup.internal.configuration.DataRemovalConfiguration;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.service.ReleaseLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import java.io.IOException;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Map;
import java.util.function.Supplier;

import org.apache.felix.cm.PersistenceManager;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Kevin Lee
 */
@Component(
	configurationPid = "com.liferay.data.cleanup.internal.configuration.DataRemovalConfiguration",
	immediate = true, service = UpgradeStepRegistrator.class
)
public class DataRemoval implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		try {
			_resetConfiguration();

			_removeModuleData(
				_dataRemovalConfiguration::removeExpiredJournalArticles,
				"com.liferay.journal.service",
				() -> new ExpiredJournalArticleUpgradeProcess(
					_journalArticleLocalService));
		}
		catch (Exception exception) {
			ReflectionUtil.throwException(exception);
		}
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_dataRemovalConfiguration = ConfigurableUtil.createConfigurable(
			DataRemovalConfiguration.class, properties);
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

	private void _resetConfiguration() throws IOException {
		Dictionary<String, Object> properties = _persistenceManager.load(
			DataRemovalConfiguration.class.getName());

		if (properties == null) {
			return;
		}

		Dictionary<String, Object> dictionary = new HashMapDictionary<>();

		for (Enumeration<String> enumeration = properties.keys();
			 enumeration.hasMoreElements();) {

			String key = enumeration.nextElement();

			dictionary.put(
				key, key.startsWith("remove") ? false : properties.get(key));
		}

		_persistenceManager.store(
			DataRemovalConfiguration.class.getName(), dictionary);
	}

	private DataRemovalConfiguration _dataRemovalConfiguration;

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

	@Reference
	private PersistenceManager _persistenceManager;

	@Reference
	private ReleaseLocalService _releaseLocalService;

}