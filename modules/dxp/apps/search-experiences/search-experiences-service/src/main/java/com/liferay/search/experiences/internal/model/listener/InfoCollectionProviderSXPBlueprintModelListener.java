/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.model.listener;

import com.liferay.info.collection.provider.InfoCollectionProvider;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.search.experiences.model.SXPBlueprint;
import com.liferay.search.experiences.service.SXPBlueprintLocalService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Shuyang Zhou
 */
public abstract class InfoCollectionProviderSXPBlueprintModelListener
	extends BaseModelListener<SXPBlueprint> {

	public InfoCollectionProviderSXPBlueprintModelListener(
		BundleContext bundleContext, CompanyLocalService companyLocalService,
		SXPBlueprintLocalService sxpBlueprintLocalService) {

		_bundleContext = bundleContext;
		_companyLocalService = companyLocalService;
		_sxpBlueprintLocalService = sxpBlueprintLocalService;
	}

	@Override
	public Class<?> getModelClass() {
		return SXPBlueprint.class;
	}

	@Override
	public void onAfterCreate(SXPBlueprint sxpBlueprint) {
		ServiceRegistration<?> serviceRegistration = _serviceRegistrations.put(
			sxpBlueprint.getSXPBlueprintId(),
			_bundleContext.registerService(
				InfoCollectionProvider.class,
				createInfoCollectionProvider(sxpBlueprint),
				HashMapDictionaryBuilder.<String, Object>put(
					"company.id", sxpBlueprint.getCompanyId()
				).put(
					"item.class.name", getItemClassName()
				).build()));

		if (serviceRegistration != null) {
			serviceRegistration.unregister();
		}
	}

	@Override
	public void onAfterRemove(SXPBlueprint sxpBlueprint) {
		ServiceRegistration<?> serviceRegistration =
			_serviceRegistrations.remove(sxpBlueprint.getSXPBlueprintId());

		if (serviceRegistration != null) {
			serviceRegistration.unregister();
		}
	}

	@Override
	public void onAfterUpdate(
		SXPBlueprint originalSXPBlueprint, SXPBlueprint newSXPBlueprint) {

		onAfterRemove(originalSXPBlueprint);

		onAfterCreate(newSXPBlueprint);
	}

	public void start() {
		_companyLocalService.forEachCompanyId(
			companyId -> {
				List<SXPBlueprint> sxpBlueprints =
					_sxpBlueprintLocalService.getSXPBlueprints(companyId);

				for (SXPBlueprint sxpBlueprint : sxpBlueprints) {
					onAfterCreate(sxpBlueprint);
				}
			});
	}

	public void stop() {
		for (ServiceRegistration<?> serviceRegistration :
				_serviceRegistrations.values()) {

			serviceRegistration.unregister();
		}

		_serviceRegistrations.clear();
	}

	protected abstract InfoCollectionProvider<?> createInfoCollectionProvider(
		SXPBlueprint sxpBlueprint);

	protected abstract String getItemClassName();

	private final BundleContext _bundleContext;
	private final CompanyLocalService _companyLocalService;
	private final Map<Long, ServiceRegistration<?>> _serviceRegistrations =
		new ConcurrentHashMap<>();
	private final SXPBlueprintLocalService _sxpBlueprintLocalService;

}