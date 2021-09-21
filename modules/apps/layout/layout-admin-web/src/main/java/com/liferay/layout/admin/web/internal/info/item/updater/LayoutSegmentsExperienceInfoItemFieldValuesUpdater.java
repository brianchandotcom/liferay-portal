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

package com.liferay.layout.admin.web.internal.info.item.updater;

import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.updater.InfoItemFieldValuesUpdater;
import com.liferay.layout.admin.web.internal.info.item.helper.LayoutInfoItemFieldValuesUpdaterHelper;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.segments.model.SegmentsExperience;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = "item.class.name=com.liferay.segments.model.SegmentsExperience",
	service = InfoItemFieldValuesUpdater.class
)
public class LayoutSegmentsExperienceInfoItemFieldValuesUpdater
	implements InfoItemFieldValuesUpdater<SegmentsExperience> {

	@Override
	public SegmentsExperience updateFromInfoItemFieldValues(
		SegmentsExperience segmentsExperience,
		InfoItemFieldValues infoItemFieldValues) {

		_layoutInfoItemFieldValuesUpdaterHelper.updateFromInfoItemFieldValues(
			_getLayout(segmentsExperience), infoItemFieldValues);

		return segmentsExperience;
	}

	@Activate
	@Modified
	protected void activate() {
		_layoutInfoItemFieldValuesUpdaterHelper =
			new LayoutInfoItemFieldValuesUpdaterHelper(
				_fragmentEntryLinkLocalService, _layoutLocalService);
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

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	private volatile LayoutInfoItemFieldValuesUpdaterHelper
		_layoutInfoItemFieldValuesUpdaterHelper;

	@Reference
	private LayoutLocalService _layoutLocalService;

}