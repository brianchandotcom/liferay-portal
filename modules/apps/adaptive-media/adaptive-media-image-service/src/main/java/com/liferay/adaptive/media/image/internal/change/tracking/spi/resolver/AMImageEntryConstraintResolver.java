/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.image.internal.change.tracking.spi.resolver;

import com.liferay.adaptive.media.image.model.AMImageEntry;
import com.liferay.adaptive.media.image.service.AMImageEntryLocalService;
import com.liferay.change.tracking.spi.resolver.ConstraintResolver;
import com.liferay.change.tracking.spi.resolver.context.ConstraintResolverContext;
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
public class AMImageEntryConstraintResolver
	implements ConstraintResolver<AMImageEntry> {

	@Override
	public String getConflictDescriptionKey() {
		return "duplicate-adaptive-media-image";
	}

	@Override
	public Class<AMImageEntry> getModelClass() {
		return AMImageEntry.class;
	}

	@Override
	public String getResolutionDescriptionKey() {
		return "duplicate-adaptive-media-image-was-removed";
	}

	@Override
	public ResourceBundle getResourceBundle(Locale locale) {
		return ResourceBundleUtil.getBundle(locale, getClass());
	}

	@Override
	public String[] getUniqueIndexColumnNames() {
		return new String[] {"configurationUuid", "fileVersionId"};
	}

	@Override
	public void resolveConflict(
			ConstraintResolverContext<AMImageEntry> constraintResolverContext)
		throws PortalException {

		AMImageEntry amImageEntry =
			constraintResolverContext.getTargetCTModel();

		_amImageEntryLocalService.deleteAMImageEntry(amImageEntry);
	}

	@Reference
	private AMImageEntryLocalService _amImageEntryLocalService;

}