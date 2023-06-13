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

package com.liferay.expando.internal.change.tracking.spi.resolver;

import com.liferay.change.tracking.spi.resolver.ConstraintResolver;
import com.liferay.change.tracking.spi.resolver.context.ConstraintResolverContext;
import com.liferay.expando.kernel.model.ExpandoRow;
import com.liferay.expando.kernel.service.ExpandoRowLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.language.LanguageResources;

import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Truong
 */
@Component(service = ConstraintResolver.class)
public class ExpandoRowConstraintResolver
	implements ConstraintResolver<ExpandoRow> {

	@Override
	public String getConflictDescriptionKey() {
		return "duplicate-expando-row";
	}

	@Override
	public Class<ExpandoRow> getModelClass() {
		return ExpandoRow.class;
	}

	@Override
	public String getResolutionDescriptionKey() {
		return "the-conflicting-expando-row-was-deleted";
	}

	@Override
	public ResourceBundle getResourceBundle(Locale locale) {
		return LanguageResources.getResourceBundle(locale);
	}

	@Override
	public String[] getUniqueIndexColumnNames() {
		return new String[] {"tableId", "classPK"};
	}

	@Override
	public void resolveConflict(
			ConstraintResolverContext<ExpandoRow> constraintResolverContext)
		throws PortalException {

		ExpandoRow expandoRow = constraintResolverContext.getTargetCTModel();

		_expandoRowLocalService.deleteExpandoRow(expandoRow);
	}

	@Reference
	private ExpandoRowLocalService _expandoRowLocalService;

}