/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.helper;

import com.liferay.layout.helper.LayoutCopyHelper;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(service = LayoutCopyHelper.class)
public class LayoutCopyHelperImpl implements LayoutCopyHelper {

	@Override
	public Layout copyLayoutContent(Layout sourceLayout, Layout targetLayout)
		throws Exception {

		return _layoutLocalService.copyLayoutContent(
			sourceLayout, targetLayout);
	}

	@Override
	public Layout copyLayoutContent(
			long segmentsExperienceId, Layout sourceLayout, Layout targetLayout)
		throws Exception {

		return _layoutLocalService.copyLayoutContent(
			segmentsExperienceId, sourceLayout, targetLayout);
	}

	@Override
	public Layout copyLayoutContent(
			long[] segmentsExperiencesIds, Layout sourceLayout,
			Layout targetLayout)
		throws Exception {

		return _layoutLocalService.copyLayoutContent(
			segmentsExperiencesIds, sourceLayout, targetLayout);
	}

	@Reference
	private LayoutLocalService _layoutLocalService;

}