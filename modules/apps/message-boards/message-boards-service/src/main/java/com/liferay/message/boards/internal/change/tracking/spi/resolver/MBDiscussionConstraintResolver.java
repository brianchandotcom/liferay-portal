/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.internal.change.tracking.spi.resolver;

import com.liferay.change.tracking.spi.resolver.ConstraintResolver;
import com.liferay.change.tracking.spi.resolver.context.ConstraintResolverContext;
import com.liferay.message.boards.model.MBDiscussion;
import com.liferay.message.boards.service.MBDiscussionLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brooke Dalton
 */
@Component(service = ConstraintResolver.class)
public class MBDiscussionConstraintResolver
	implements ConstraintResolver<MBDiscussion> {

	@Override
	public String getConflictDescriptionKey() {
		return "duplicate-comment";
	}

	@Override
	public Class<MBDiscussion> getModelClass() {
		return MBDiscussion.class;
	}

	@Override
	public String getResolutionDescriptionKey() {
		return "the-conflicting-comment-was-deleted";
	}

	@Override
	public ResourceBundle getResourceBundle(Locale locale) {
		return ResourceBundleUtil.getBundle(
			locale, MBDiscussionConstraintResolver.class);
	}

	@Override
	public String[] getUniqueIndexColumnNames() {
		return new String[] {"classNameId", "classPK"};
	}

	@Override
	public void resolveConflict(
			ConstraintResolverContext<MBDiscussion> constraintResolverContext)
		throws PortalException {

		MBDiscussion mbDiscussion =
			constraintResolverContext.getSourceCTModel();

		_mbDiscussionLocalService.deleteMBDiscussion(mbDiscussion);
	}

	@Reference
	private MBDiscussionLocalService _mbDiscussionLocalService;

}