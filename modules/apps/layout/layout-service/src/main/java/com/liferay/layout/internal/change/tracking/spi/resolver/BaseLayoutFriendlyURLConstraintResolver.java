/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.change.tracking.spi.resolver;

import com.liferay.change.tracking.configuration.CTSettingsConfiguration;
import com.liferay.change.tracking.spi.resolver.ConstraintResolver;
import com.liferay.change.tracking.spi.resolver.context.ConstraintResolverContext;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.LayoutFriendlyURL;
import com.liferay.portal.language.LanguageResources;

import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Reference;

/**
 * @author David Truong
 */
public abstract class BaseLayoutFriendlyURLConstraintResolver
	implements ConstraintResolver<LayoutFriendlyURL> {

	@Override
	public String getConflictDescriptionKey() {
		return "duplicate-friendly-page-url";
	}

	@Override
	public Class<LayoutFriendlyURL> getModelClass() {
		return LayoutFriendlyURL.class;
	}

	@Override
	public String getResolutionDescriptionKey() {
		if (_resolved) {
			return getAutomaticResolutionDescriptionKey();
		}

		return "duplicate-friendly-page-url-was-not-automatically-resolved";
	}

	@Override
	public ResourceBundle getResourceBundle(Locale locale) {
		return LanguageResources.getResourceBundle(locale);
	}

	@Override
	public void resolveConflict(
			ConstraintResolverContext<LayoutFriendlyURL>
				constraintResolverContext)
		throws PortalException {

		LayoutFriendlyURL layoutFriendlyURL =
			constraintResolverContext.getSourceCTModel();

		CTSettingsConfiguration ctSettingsConfiguration =
			configurationProvider.getCompanyConfiguration(
				CTSettingsConfiguration.class,
				layoutFriendlyURL.getCompanyId());

		if (!ctSettingsConfiguration.automaticFriendlyURLConflictResolution()) {
			_resolved = false;

			return;
		}

		doResolveConflict(constraintResolverContext);

		_resolved = true;
	}

	protected abstract void doResolveConflict(
			ConstraintResolverContext<LayoutFriendlyURL>
				constraintResolverContext)
		throws PortalException;

	protected abstract String getAutomaticResolutionDescriptionKey();

	@Reference
	protected ConfigurationProvider configurationProvider;

	private boolean _resolved;

}