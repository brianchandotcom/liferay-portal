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

package com.liferay.layout.admin.web.internal.info.item.helper;

import com.liferay.fragment.renderer.FragmentRendererController;
import com.liferay.info.field.InfoFieldSet;
import com.liferay.info.field.InfoFieldSetEntry;
import com.liferay.info.form.InfoForm;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.layout.admin.web.internal.info.item.LayoutInfoItemFields;
import com.liferay.layout.admin.web.internal.util.InfoFieldUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.segments.service.SegmentsExperienceLocalServiceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Adolfo Pérez
 */
public class LayoutInfoItemFormProviderHelper {

	public LayoutInfoItemFormProviderHelper(
		FragmentRendererController fragmentRendererController) {

		_fragmentRendererController = fragmentRendererController;
	}

	public InfoForm getInfoForm() {
		return InfoForm.builder(
		).infoFieldSetEntry(
			_getBasicInformationInfoFieldSet()
		).build();
	}

	public InfoForm getInfoForm(Layout layout, long segmentsExperienceId) {
		if (!layout.isTypeContent()) {
			return getInfoForm();
		}

		long defaultSegmentsExperienceId =
			SegmentsExperienceLocalServiceUtil.fetchDefaultSegmentsExperienceId(
				layout.getPlid());

		if (segmentsExperienceId != defaultSegmentsExperienceId) {
			return InfoForm.builder(
			).infoFieldSetEntry(
				_getLayoutInfoFieldSet(layout, segmentsExperienceId)
			).build();
		}

		return InfoForm.builder(
		).infoFieldSetEntry(
			_getBasicInformationInfoFieldSet()
		).infoFieldSetEntry(
			_getLayoutInfoFieldSet(layout, segmentsExperienceId)
		).build();
	}

	private InfoFieldSet _getBasicInformationInfoFieldSet() {
		return InfoFieldSet.builder(
		).infoFieldSetEntry(
			LayoutInfoItemFields.nameInfoField
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(getClass(), "basic-information")
		).name(
			"basic-information"
		).build();
	}

	private InfoFieldSet _getLayoutInfoFieldSet(
		Layout layout, long segmentsExperienceId) {

		return InfoFieldSet.builder(
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(getClass(), "inline-content")
		).infoFieldSetEntries(
			_getLayoutInfoFieldSetEntries(layout, segmentsExperienceId)
		).name(
			"inline-content"
		).build();
	}

	private List<InfoFieldSetEntry> _getLayoutInfoFieldSetEntries(
		Layout layout, long segmentsExperienceId) {

		List<InfoFieldSetEntry> infoFieldSetEntries = new ArrayList<>();

		InfoFieldUtil.forEachInfoField(
			_fragmentRendererController, layout, segmentsExperienceId,
			(name, infoField, unsafeSupplier) -> infoFieldSetEntries.add(
				infoField));

		return infoFieldSetEntries;
	}

	private final FragmentRendererController _fragmentRendererController;

}