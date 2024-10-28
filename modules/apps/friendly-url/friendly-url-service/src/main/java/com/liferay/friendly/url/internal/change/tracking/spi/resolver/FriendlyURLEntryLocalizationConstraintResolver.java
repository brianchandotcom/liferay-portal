/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.friendly.url.internal.change.tracking.spi.resolver;

import com.liferay.change.tracking.spi.resolver.ConstraintResolver;
import com.liferay.change.tracking.spi.resolver.context.ConstraintResolverContext;
import com.liferay.friendly.url.model.FriendlyURLEntryLocalization;
import com.liferay.friendly.url.service.persistence.FriendlyURLEntryLocalizationPersistence;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(service = ConstraintResolver.class)
public class FriendlyURLEntryLocalizationConstraintResolver
	implements ConstraintResolver<FriendlyURLEntryLocalization> {

	@Override
	public String getConflictDescriptionKey() {
		return "duplicate-friendly-url-entry-localization";
	}

	@Override
	public Class<FriendlyURLEntryLocalization> getModelClass() {
		return FriendlyURLEntryLocalization.class;
	}

	@Override
	public String getResolutionDescriptionKey() {
		return "duplicate-friendly-url-entry-localization-was-removed";
	}

	@Override
	public ResourceBundle getResourceBundle(Locale locale) {
		return ResourceBundleUtil.getBundle(locale, getClass());
	}

	@Override
	public String[] getUniqueIndexColumnNames() {
		return new String[] {
			"groupId", "classNameId", "languageId", "urlTitle"
		};
	}

	@Override
	public void resolveConflict(
			ConstraintResolverContext<FriendlyURLEntryLocalization>
				constraintResolverContext)
		throws PortalException {

		FriendlyURLEntryLocalization friendlyURLEntryLocalization =
			constraintResolverContext.getTargetCTModel();

		_friendlyURLEntryLocalizationPersistence.remove(
			friendlyURLEntryLocalization);
	}

	@Reference
	private FriendlyURLEntryLocalizationPersistence
		_friendlyURLEntryLocalizationPersistence;

}