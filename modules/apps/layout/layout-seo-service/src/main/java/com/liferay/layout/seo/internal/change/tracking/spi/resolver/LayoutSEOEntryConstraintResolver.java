/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.layout.seo.internal.change.tracking.spi.resolver;

import com.liferay.change.tracking.spi.resolver.ConstraintResolver;
import com.liferay.change.tracking.spi.resolver.context.ConstraintResolverContext;
import com.liferay.layout.seo.model.LayoutSEOEntry;
import com.liferay.layout.seo.service.LayoutSEOEntryLocalService;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

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

	@Reference
	private LayoutSEOEntryLocalService _layoutSEOEntryLocalService;

}