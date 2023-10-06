/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.feature.flag;

import com.liferay.asset.util.AssetHelper;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.portal.kernel.feature.flag.FeatureFlagListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.search.experiences.internal.model.listener.AssetEntrySXPBlueprintInfoCollectionProviderSXPBlueprintModelListener;
import com.liferay.search.experiences.internal.model.listener.InfoCollectionProviderSXPBlueprintModelListener;
import com.liferay.search.experiences.internal.model.listener.JournalArticleSXPBlueprintInfoCollectionProviderSXPBlueprintModelListener;
import com.liferay.search.experiences.service.SXPBlueprintLocalService;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shuyang Zhou
 */
@Component(
	enabled = false,
	property = {"featureFlagKey=LPS-129412", "featureFlagKey=LPS-193551"},
	service = FeatureFlagListener.class
)
public class SXPBlueprintFeatureFlagListener implements FeatureFlagListener {

	@Override
	public void onValue(
		long companyId, String featureFlagKey, boolean enabled) {

		Map.Entry
			<InfoCollectionProviderSXPBlueprintModelListener,
			 ServiceRegistration<?>> entry = null;

		if (enabled) {
			InfoCollectionProviderSXPBlueprintModelListener
				infoCollectionProviderSXPBlueprintModelListener = null;

			if (Objects.equals(featureFlagKey, "LPS-193551")) {
				infoCollectionProviderSXPBlueprintModelListener =
					new JournalArticleSXPBlueprintInfoCollectionProviderSXPBlueprintModelListener(
						_bundleContext, _companyLocalService,
						_sxpBlueprintLocalService, _assetHelper,
						_journalArticleService, _searcher,
						_searchRequestBuilderFactory);
			}
			else if (Objects.equals(featureFlagKey, "LPS-129412")) {
				infoCollectionProviderSXPBlueprintModelListener =
					new AssetEntrySXPBlueprintInfoCollectionProviderSXPBlueprintModelListener(
						_bundleContext, _companyLocalService,
						_sxpBlueprintLocalService, _assetHelper, _searcher,
						_searchRequestBuilderFactory);
			}

			infoCollectionProviderSXPBlueprintModelListener.start();

			entry = _serviceRegistrationEntries.put(
				featureFlagKey,
				new AbstractMap.SimpleImmutableEntry<>(
					infoCollectionProviderSXPBlueprintModelListener,
					_bundleContext.registerService(
						ModelListener.class,
						infoCollectionProviderSXPBlueprintModelListener,
						null)));
		}
		else {
			entry = _serviceRegistrationEntries.remove(featureFlagKey);
		}

		if (entry != null) {
			ServiceRegistration<?> serviceRegistration = entry.getValue();

			serviceRegistration.unregister();

			InfoCollectionProviderSXPBlueprintModelListener
				infoCollectionProviderSXPBlueprintModelListener =
					entry.getKey();

			infoCollectionProviderSXPBlueprintModelListener.stop();
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	@Reference
	private AssetHelper _assetHelper;

	private BundleContext _bundleContext;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private JournalArticleService _journalArticleService;

	@Reference
	private Searcher _searcher;

	@Reference
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

	private final Map
		<String,
		 Map.Entry
			 <InfoCollectionProviderSXPBlueprintModelListener,
			  ServiceRegistration<?>>> _serviceRegistrationEntries =
				new ConcurrentHashMap<>();

	@Reference
	private SXPBlueprintLocalService _sxpBlueprintLocalService;

}