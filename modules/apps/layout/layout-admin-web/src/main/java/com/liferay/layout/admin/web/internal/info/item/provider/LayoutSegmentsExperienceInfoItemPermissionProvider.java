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

package com.liferay.layout.admin.web.internal.info.item.provider;

import com.liferay.info.exception.InfoItemPermissionException;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.provider.InfoItemPermissionProvider;
import com.liferay.layout.admin.web.internal.info.item.helper.LayoutInfoItemPermissionProviderHelper;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = InfoItemPermissionProvider.class)
public class LayoutSegmentsExperienceInfoItemPermissionProvider
	implements InfoItemPermissionProvider<SegmentsExperience> {

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker,
			InfoItemReference infoItemReference, String actionId)
		throws InfoItemPermissionException {

		ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
			(ClassPKInfoItemIdentifier)
				infoItemReference.getInfoItemIdentifier();

		return _layoutInfoItemPermissionProviderHelper.hasPermission(
			permissionChecker,
			_getInfoItemReference(classPKInfoItemIdentifier.getClassPK()),
			actionId);
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker,
			SegmentsExperience segmentsExperience, String actionId)
		throws InfoItemPermissionException {

		return _layoutInfoItemPermissionProviderHelper.hasPermission(
			permissionChecker, _getLayout(segmentsExperience), actionId);
	}

	@Activate
	@Modified
	protected void activate() {
		_layoutInfoItemPermissionProviderHelper =
			new LayoutInfoItemPermissionProviderHelper(
				_layoutModelResourcePermission);
	}

	private InfoItemReference _getInfoItemReference(long segmentsExperienceId) {
		try {
			SegmentsExperience segmentsExperience =
				_segmentsExperienceLocalService.getSegmentsExperience(
					segmentsExperienceId);

			Layout layout = _getLayout(segmentsExperience);

			return new InfoItemReference(
				Layout.class.getName(),
				new ClassPKInfoItemIdentifier(layout.getPlid()));
		}
		catch (PortalException portalException) {
			return ReflectionUtil.throwException(portalException);
		}
	}

	private Layout _getLayout(SegmentsExperience segmentsExperience) {
		try {
			Layout layout = _layoutLocalService.getLayout(
				segmentsExperience.getClassPK());

			if (layout.isDraftLayout()) {
				return layout;
			}

			Layout draftLayout = layout.fetchDraftLayout();

			if (draftLayout != null) {
				return draftLayout;
			}

			return layout;
		}
		catch (PortalException portalException) {
			return ReflectionUtil.throwException(portalException);
		}
	}

	private volatile LayoutInfoItemPermissionProviderHelper
		_layoutInfoItemPermissionProviderHelper;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.portal.kernel.model.Layout)"
	)
	private ModelResourcePermission<Layout> _layoutModelResourcePermission;

	@Reference
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

}