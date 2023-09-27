/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.change.tracking.spi.resolver;

import com.liferay.change.tracking.spi.resolver.ConstraintResolver;
import com.liferay.change.tracking.spi.resolver.context.ConstraintResolverContext;
import com.liferay.layout.model.LayoutLocalization;
import com.liferay.layout.service.LayoutLocalizationLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.language.LanguageResources;

import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(service = ConstraintResolver.class)
public class LayoutLocalizationConstraintResolver
	implements ConstraintResolver<LayoutLocalization> {

	@Override
	public String getConflictDescriptionKey() {
		return "duplicate-layout-localization";
	}

	@Override
	public Class<LayoutLocalization> getModelClass() {
		return LayoutLocalization.class;
	}

	@Override
	public String getResolutionDescriptionKey() {
		return "duplicate-layout-localization-was-removed";
	}

	@Override
	public ResourceBundle getResourceBundle(Locale locale) {
		return LanguageResources.getResourceBundle(locale);
	}

	@Override
	public String[] getUniqueIndexColumnNames() {
		return new String[] {"languageId", "plid"};
	}

	@Override
	public void resolveConflict(
			ConstraintResolverContext<LayoutLocalization>
				constraintResolverContext)
		throws PortalException {

		_layoutLocalizationLocalService.deleteLayoutLocalization(
			constraintResolverContext.getSourceCTModel());
	}

	@Reference
	private LayoutLocalizationLocalService _layoutLocalizationLocalService;

}