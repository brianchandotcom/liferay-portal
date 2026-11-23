/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.admin.web.internal.info.item.provider;

import com.liferay.fragment.renderer.FragmentRendererController;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.layout.admin.web.internal.util.LayoutInfoItemFieldValuesProviderUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = InfoItemFieldValuesProvider.class)
public class LayoutInfoItemFieldValuesProvider
	implements InfoItemFieldValuesProvider<Layout> {

	@Override
	public InfoItemFieldValues getInfoItemFieldValues(Layout layout) {
		long defaultSegmentsExperienceId =
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				layout.getPlid());

		return LayoutInfoItemFieldValuesProviderUtil.getInfoItemFieldValues(
			_fragmentRendererController, layout, defaultSegmentsExperienceId);
	}

	@Reference
	private FragmentRendererController _fragmentRendererController;

	@Reference
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

}