/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.cleanup.internal.upgrade.executor;

import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.change.tracking.store.service.CTSContentLocalService;
import com.liferay.data.cleanup.internal.configuration.DataRemovalConfiguration;
import com.liferay.data.cleanup.internal.upgrade.DLPreviewCTSContentDataUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.ExpiredJournalArticleUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.OutdatedPublishedCTCollectionUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.PublishedCTSContentDataUpgradeProcess;
import com.liferay.data.cleanup.internal.upgrade.util.ConfigurationUtil;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.service.ReleaseLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.Portal;

import java.util.Map;
import java.util.function.Supplier;

import org.apache.felix.cm.PersistenceManager;

import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shuyang Zhou
 */
@Component(
	configurationPid = "com.liferay.data.cleanup.internal.configuration.DataRemovalConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE, service = {}
)
public class DataRemovalExecutor {

	@Activate
	protected void activate(Map<String, Object> properties) throws Exception {
		DataRemovalConfiguration dataRemovalConfiguration =
			ConfigurableUtil.createConfigurable(
				DataRemovalConfiguration.class, properties);

		ConfigurationUtil.deleteConfiguration(
			_configurationAdmin, _persistenceManager,
			DataRemovalConfiguration.class.getName());

		_removeModuleData(
			dataRemovalConfiguration::removeDLPreviewCTSContentData,
			"com.liferay.change.tracking.service",
			() -> new DLPreviewCTSContentDataUpgradeProcess(
				_ctCollectionLocalService, _ctEntryLocalService, _portal));
		_removeModuleData(
			dataRemovalConfiguration::removeOutdatedPublishedCTCollections,
			"com.liferay.change.tracking.service",
			() -> new OutdatedPublishedCTCollectionUpgradeProcess(
				_ctCollectionLocalService));
		_removeModuleData(
			dataRemovalConfiguration::removePublishedCTSContentData,
			"com.liferay.change.tracking.store.service",
			() -> new PublishedCTSContentDataUpgradeProcess(
				_ctsContentLocalService, _portal));
		_removeModuleData(
			dataRemovalConfiguration::removeExpiredJournalArticles,
			"com.liferay.journal.service",
			() -> new ExpiredJournalArticleUpgradeProcess(
				_journalArticleLocalService));
	}

	private void _removeModuleData(
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
	private CTCollectionLocalService _ctCollectionLocalService;

	@Reference
	private CTEntryLocalService _ctEntryLocalService;

	@Reference
	private CTSContentLocalService _ctsContentLocalService;

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

	@Reference
	private PersistenceManager _persistenceManager;

	@Reference
	private Portal _portal;

	@Reference
	private ReleaseLocalService _releaseLocalService;

}