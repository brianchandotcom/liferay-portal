/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.change.tracking.spi.resolver;

import com.liferay.change.tracking.spi.resolver.ConstraintResolver;
import com.liferay.change.tracking.spi.resolver.context.ConstraintResolverContext;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.LayoutFriendlyURL;
import com.liferay.portal.kernel.service.LayoutFriendlyURLLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Truong
 */
@Component(service = ConstraintResolver.class)
public class
	GroupIdPrivateLayoutFriendlyURLAndLanguageIdLayoutFriendlyURLConstraintResolver
		extends BaseLayoutFriendlyURLConstraintResolver {

	@Override
	public String[] getUniqueIndexColumnNames() {
		return new String[] {
			"groupId", "privateLayout", "friendlyURL", "languageId"
		};
	}

	@Override
	protected void doResolveConflict(
			ConstraintResolverContext<LayoutFriendlyURL>
				constraintResolverContext)
		throws PortalException {

		LayoutFriendlyURL layoutFriendlyURL =
			constraintResolverContext.getSourceCTModel();

		String friendlyURL = layoutFriendlyURL.getFriendlyURL();

		String uniqueFriendlyURL = constraintResolverContext.getInTarget(
			() -> {
				String newFriendlyURL = friendlyURL;

				LayoutFriendlyURL existingLayoutFriendlyURL =
					_layoutFriendlyURLLocalService.fetchLayoutFriendlyURL(
						layoutFriendlyURL.getGroupId(),
						layoutFriendlyURL.isPrivateLayout(), newFriendlyURL,
						layoutFriendlyURL.getLanguageId());

				for (int i = 1; existingLayoutFriendlyURL != null; i++) {
					newFriendlyURL = friendlyURL + StringPool.DASH + i;

					existingLayoutFriendlyURL =
						_layoutFriendlyURLLocalService.fetchLayoutFriendlyURL(
							layoutFriendlyURL.getGroupId(),
							layoutFriendlyURL.isPrivateLayout(), newFriendlyURL,
							layoutFriendlyURL.getLanguageId());
				}

				return newFriendlyURL;
			});

		layoutFriendlyURL.setFriendlyURL(uniqueFriendlyURL);

		_layoutFriendlyURLLocalService.updateLayoutFriendlyURL(
			layoutFriendlyURL);
	}

	@Override
	protected String getAutomaticResolutionDescriptionKey() {
		return "duplicate-friendly-page-url-was-renamed";
	}

	@Reference
	private LayoutFriendlyURLLocalService _layoutFriendlyURLLocalService;

}