/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.internal.change.tracking.spi.resolver;

import com.liferay.change.tracking.spi.resolver.ConstraintResolver;
import com.liferay.change.tracking.spi.resolver.context.ConstraintResolverContext;
import com.liferay.fragment.model.FragmentEntryVersion;
import com.liferay.fragment.service.persistence.FragmentEntryVersionPersistence;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gislayne Vitorino
 */
@Component(service = ConstraintResolver.class)
public class FragmentEntryVersionConstraintResolver
	implements ConstraintResolver<FragmentEntryVersion> {

	@Override
	public String getConflictDescriptionKey() {
		return "duplicate-fragment-entry-version";
	}

	@Override
	public Class<FragmentEntryVersion> getModelClass() {
		return FragmentEntryVersion.class;
	}

	@Override
	public String getResolutionDescriptionKey() {
		return "duplicate-fragment-entry-version-was-removed";
	}

	@Override
	public ResourceBundle getResourceBundle(Locale locale) {
		return ResourceBundleUtil.getBundle(
			locale, FragmentEntryVersionConstraintResolver.class);
	}

	@Override
	public String[] getUniqueIndexColumnNames() {
		return new String[] {"fragmentEntryId", "version"};
	}

	@Override
	public void resolveConflict(
			ConstraintResolverContext<FragmentEntryVersion>
				constraintResolverContext)
		throws PortalException {

		FragmentEntryVersion fragmentEntryVersion =
			constraintResolverContext.getTargetCTModel();

		fragmentEntryVersionPersistence.removeByFragmentEntryId_Version(
			fragmentEntryVersion.getFragmentEntryId(),
			fragmentEntryVersion.getVersion());
	}

	@Reference
	protected FragmentEntryVersionPersistence fragmentEntryVersionPersistence;

}