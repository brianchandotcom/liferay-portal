/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.seo.internal.change.tracking.spi.resolver;

import com.liferay.change.tracking.spi.resolver.ConstraintResolver;
import com.liferay.change.tracking.spi.resolver.context.ConstraintResolverContext;
import com.liferay.layout.seo.model.LayoutSEOEntry;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;

/**
 * @author Samuel Trong Tran
 */
@Component(service = ConstraintResolver.class)
public class LayoutSEOEntryConstraintResolver
	implements ConstraintResolver<LayoutSEOEntry> {

	@Override
	public String getConflictDescriptionKey() {
		return "duplicate-page-seo-entry";
	}

	@Override
	public Class<LayoutSEOEntry> getModelClass() {
		return LayoutSEOEntry.class;
	}

	@Override
	public String getResolutionDescriptionKey() {
		return "discard-the-page-seo-entry-in-the-publication";
	}

	@Override
	public ResourceBundle getResourceBundle(Locale locale) {
		return ResourceBundleUtil.getBundle(locale, getClass());
	}

	@Override
	public String[] getUniqueIndexColumnNames() {
		return new String[] {"groupId", "privateLayout", "layoutId"};
	}

	@Override
	public void resolveConflict(
		ConstraintResolverContext<LayoutSEOEntry> constraintResolverContext) {
	}

}