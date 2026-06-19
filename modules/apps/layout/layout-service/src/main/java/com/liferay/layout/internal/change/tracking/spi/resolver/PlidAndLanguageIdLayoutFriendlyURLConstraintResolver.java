/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.change.tracking.spi.resolver;

import com.liferay.change.tracking.spi.resolver.ConstraintResolver;
import com.liferay.change.tracking.spi.resolver.context.ConstraintResolverContext;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.LayoutFriendlyURL;

import org.osgi.service.component.annotations.Component;

/**
 * @author David Truong
 */
@Component(service = ConstraintResolver.class)
public class PlidAndLanguageIdLayoutFriendlyURLConstraintResolver
	extends BaseLayoutFriendlyURLConstraintResolver {

	@Override
	public String[] getUniqueIndexColumnNames() {
		return new String[] {"plid", "languageId"};
	}

	@Override
	protected void doResolveConflict(
			ConstraintResolverContext<LayoutFriendlyURL>
				constraintResolverContext)
		throws PortalException {

		constraintResolverContext.mergeSourceCTModelIntoTargetCTModel();
	}

	@Override
	protected String getAutomaticResolutionDescriptionKey() {
		return "duplicate-friendly-page-url-was-removed";
	}

}