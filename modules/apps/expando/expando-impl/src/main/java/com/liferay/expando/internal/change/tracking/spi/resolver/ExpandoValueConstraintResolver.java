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
import com.liferay.expando.kernel.model.ExpandoValue;
import com.liferay.expando.kernel.service.ExpandoValueLocalService;
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
public class ExpandoValueConstraintResolver
	implements ConstraintResolver<ExpandoValue> {

	@Override
	public String getConflictDescriptionKey() {
		return "duplicate-expando-value";
	}

	@Override
	public Class<ExpandoValue> getModelClass() {
		return ExpandoValue.class;
	}

	@Override
	public String getResolutionDescriptionKey() {
		return "the-conflicting-expando-value-was-deleted";
	}

	@Override
	public ResourceBundle getResourceBundle(Locale locale) {
		return LanguageResources.getResourceBundle(locale);
	}

	@Override
	public String[] getUniqueIndexColumnNames() {
		return new String[] {"tableId", "columnId", "classPK"};
	}

	@Override
	public void resolveConflict(
			ConstraintResolverContext<ExpandoValue> constraintResolverContext)
		throws PortalException {

		ExpandoValue expandoValue =
			constraintResolverContext.getTargetCTModel();

		_expandoValueLocalService.deleteExpandoValue(expandoValue);
	}

	@Reference
	private ExpandoValueLocalService _expandoValueLocalService;

}