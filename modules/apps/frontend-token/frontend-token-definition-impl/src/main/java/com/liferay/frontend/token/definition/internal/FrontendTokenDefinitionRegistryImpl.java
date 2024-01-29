/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.token.definition.internal;

import com.liferay.client.extension.constants.ClientExtensionEntryConstants;
import com.liferay.client.extension.model.ClientExtensionEntryRel;
import com.liferay.client.extension.service.ClientExtensionEntryRelLocalService;
import com.liferay.client.extension.type.ThemeCSSCET;
import com.liferay.frontend.token.definition.FrontendTokenDefinition;
import com.liferay.frontend.token.definition.FrontendTokenDefinitionRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.concurrent.DCLSingleton;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.util.Portal;

import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.BundleTracker;

/**
 * @author Iván Zaera
 */
@Component(service = FrontendTokenDefinitionRegistry.class)
public class FrontendTokenDefinitionRegistryImpl
	implements FrontendTokenDefinitionRegistry {

	@Override
	public FrontendTokenDefinition getFrontendTokenDefinition(
		LayoutSet layoutSet) {

		return getFrontendTokenDefinition(
			layoutSet.getCompanyId(),
			_getLayoutSetThemeCSSCETExternalReferenceCode(
				layoutSet.getLayoutSetId()),
			layoutSet.getThemeId());
	}

	@Override
	public FrontendTokenDefinition getFrontendTokenDefinition(
		long companyId, String externalReferenceCode, String themeId) {

		if (FeatureFlagManagerUtil.isEnabled("LPD-10773") &&
			(externalReferenceCode != null)) {

			FrontendTokenDefinition frontendTokenDefinition =
				_frontendTokenDefinitionManager.getFrontendTokenDefinition(
					companyId, externalReferenceCode);

			if (frontendTokenDefinition != null) {
				return frontendTokenDefinition;
			}
		}

		return _frontendTokenDefinitionManager.getFrontendTokenDefinition(
			_openBundleTracker, themeId);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_frontendTokenDefinitionManager = new FrontendTokenDefinitionManager(
			_jsonFactory, new DCLSingleton<>(), new ConcurrentHashMap<>(),
			new ConcurrentHashMap<>());

		_bundleTracker = new BundleTracker<>(
			bundleContext, Bundle.ACTIVE,
			new ThemeBundleTrackerCustomizer(
				_frontendTokenDefinitionManager, _portal));

		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, ThemeCSSCET.class, "external.reference.code",
			new ThemeClientExtensionServiceTracker(
				bundleContext, _frontendTokenDefinitionManager));
	}

	@Deactivate
	protected void deactivate() {
		_bundleTracker.close();

		_serviceTrackerMap.close();
	}

	private String _getLayoutSetThemeCSSCETExternalReferenceCode(
		long layoutSetId) {

		ClientExtensionEntryRel clientExtensionEntryRel =
			_clientExtensionEntryRelLocalService.fetchClientExtensionEntryRel(
				_portal.getClassNameId(LayoutSet.class), layoutSetId,
				ClientExtensionEntryConstants.TYPE_THEME_CSS);

		if (clientExtensionEntryRel != null) {
			return clientExtensionEntryRel.getCETExternalReferenceCode();
		}

		return null;
	}

	private BundleTracker<FrontendTokenDefinitionImpl> _bundleTracker;

	@Reference
	private ClientExtensionEntryRelLocalService
		_clientExtensionEntryRelLocalService;

	private FrontendTokenDefinitionManager _frontendTokenDefinitionManager;

	@Reference
	private JSONFactory _jsonFactory;

	private final Runnable _openBundleTracker = () -> _bundleTracker.open();

	@Reference
	private Portal _portal;

	private ServiceTrackerMap<String, ThemeCSSCET> _serviceTrackerMap;

}