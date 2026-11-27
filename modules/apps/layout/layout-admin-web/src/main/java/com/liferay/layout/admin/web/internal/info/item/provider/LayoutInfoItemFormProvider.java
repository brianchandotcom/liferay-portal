/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.admin.web.internal.info.item.provider;

import com.liferay.fragment.renderer.FragmentRendererController;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.layout.admin.web.internal.util.LayoutInfoItemFormProviderUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = InfoItemFormProvider.class)
public class LayoutInfoItemFormProvider
	implements InfoItemFormProvider<Layout> {

	@Override
	public InfoForm getInfoForm() {
		return LayoutInfoItemFormProviderUtil.getInfoForm();
	}

	@Override
	public InfoForm getInfoForm(Layout layout) {
		long defaultSegmentsExperienceId =
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				layout.getPlid());

		return LayoutInfoItemFormProviderUtil.getInfoForm(
			_fragmentRendererController, layout, defaultSegmentsExperienceId);
	}

	@Reference
	private FragmentRendererController _fragmentRendererController;

	@Reference
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

}