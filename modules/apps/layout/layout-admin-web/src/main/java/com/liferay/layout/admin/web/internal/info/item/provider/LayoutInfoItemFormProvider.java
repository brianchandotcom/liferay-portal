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

import com.liferay.fragment.renderer.FragmentRendererController;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.layout.admin.web.internal.info.item.helper.LayoutInfoItemFormProviderHelper;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = InfoItemFormProvider.class)
public class LayoutInfoItemFormProvider
	implements InfoItemFormProvider<Layout> {

	@Override
	public InfoForm getInfoForm() {
		return _layoutInfoItemFormProviderHelper.getInfoForm();
	}

	@Override
	public InfoForm getInfoForm(Layout layout) {
		long defaultSegmentsExperienceId =
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				layout.getPlid());

		return _layoutInfoItemFormProviderHelper.getInfoForm(
			layout, defaultSegmentsExperienceId);
	}

	@Activate
	@Modified
	protected void activate() {
		_layoutInfoItemFormProviderHelper =
			new LayoutInfoItemFormProviderHelper(_fragmentRendererController);
	}

	@Reference
	private FragmentRendererController _fragmentRendererController;

	private volatile LayoutInfoItemFormProviderHelper
		_layoutInfoItemFormProviderHelper;

	@Reference
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

}