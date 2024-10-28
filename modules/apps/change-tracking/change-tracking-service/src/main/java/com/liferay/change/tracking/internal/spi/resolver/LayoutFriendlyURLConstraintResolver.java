/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.spi.resolver;

import com.liferay.change.tracking.spi.resolver.ConstraintResolver;
import com.liferay.change.tracking.spi.resolver.context.ConstraintResolverContext;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.LayoutFriendlyURL;
import com.liferay.portal.kernel.service.LayoutFriendlyURLLocalService;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Truong
 */
@Component(service = ConstraintResolver.class)
public class LayoutFriendlyURLConstraintResolver
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
		return "duplicate-friendly-page-url-was-removed";
	}

	@Override
	public ResourceBundle getResourceBundle(Locale locale) {
		return ResourceBundleUtil.getBundle(
			locale, LayoutFriendlyURLConstraintResolver.class);
	}

	@Override
	public String[] getUniqueIndexColumnNames() {
		return new String[] {"plid", "languageId"};
	}

	@Override
	public void resolveConflict(
			ConstraintResolverContext<LayoutFriendlyURL>
				constraintResolverContext)
		throws PortalException {

		LayoutFriendlyURL layoutFriendlyURL =
			constraintResolverContext.getTargetCTModel();

		_layoutFriendlyURLLocalService.deleteLayoutFriendlyURL(
			layoutFriendlyURL);
	}

	@Reference
	private LayoutFriendlyURLLocalService _layoutFriendlyURLLocalService;

}