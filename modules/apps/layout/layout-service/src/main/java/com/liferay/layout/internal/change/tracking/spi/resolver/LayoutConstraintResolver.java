/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.change.tracking.spi.resolver;

import com.liferay.change.tracking.configuration.CTSettingsConfiguration;
import com.liferay.change.tracking.spi.resolver.ConstraintResolver;
import com.liferay.change.tracking.spi.resolver.context.ConstraintResolverContext;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.language.LanguageResources;

import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Truong
 */
@Component(service = ConstraintResolver.class)
public class LayoutConstraintResolver implements ConstraintResolver<Layout> {

	@Override
	public String getConflictDescriptionKey() {
		return "duplicate-friendly-page-url";
	}

	@Override
	public Class<Layout> getModelClass() {
		return Layout.class;
	}

	@Override
	public String getResolutionDescriptionKey() {
		if (_resolved) {
			return "duplicate-friendly-page-url-was-renamed";
		}

		return "duplicate-friendly-page-url-was-not-automatically-resolved";
	}

	@Override
	public ResourceBundle getResourceBundle(Locale locale) {
		return LanguageResources.getResourceBundle(locale);
	}

	@Override
	public String[] getUniqueIndexColumnNames() {
		return new String[] {"groupId", "privateLayout", "friendlyURL"};
	}

	@Override
	public void resolveConflict(
			ConstraintResolverContext<Layout> constraintResolverContext)
		throws PortalException {

		Layout layout = constraintResolverContext.getSourceCTModel();

		CTSettingsConfiguration ctSettingsConfiguration =
			_configurationProvider.getCompanyConfiguration(
				CTSettingsConfiguration.class, layout.getCompanyId());

		if (!ctSettingsConfiguration.automaticFriendlyURLConflictResolution()) {
			_resolved = false;

			return;
		}

		String friendlyURL = layout.getFriendlyURL();

		String uniqueFriendlyURL = constraintResolverContext.getInTarget(
			() -> {
				String newFriendlyURL = friendlyURL;

				Layout existingLayout =
					_layoutLocalService.fetchLayoutByFriendlyURL(
						layout.getGroupId(), layout.isPrivateLayout(),
						newFriendlyURL);

				for (int i = 1; existingLayout != null; i++) {
					newFriendlyURL = friendlyURL + StringPool.DASH + i;

					existingLayout =
						_layoutLocalService.fetchLayoutByFriendlyURL(
							layout.getGroupId(), layout.isPrivateLayout(),
							newFriendlyURL);
				}

				return newFriendlyURL;
			});

		layout.setFriendlyURL(uniqueFriendlyURL);

		_layoutLocalService.updateLayout(layout);

		_resolved = true;
	}

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private LayoutLocalService _layoutLocalService;

	private boolean _resolved;

}