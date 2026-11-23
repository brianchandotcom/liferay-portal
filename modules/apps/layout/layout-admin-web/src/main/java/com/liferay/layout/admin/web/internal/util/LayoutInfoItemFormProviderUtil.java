/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.admin.web.internal.util;

import com.liferay.fragment.renderer.FragmentRendererController;
import com.liferay.info.field.InfoFieldSet;
import com.liferay.info.field.InfoFieldSetEntry;
import com.liferay.info.form.InfoForm;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.layout.admin.web.internal.info.item.LayoutInfoItemFields;
import com.liferay.layout.util.InfoFieldUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.segments.service.SegmentsExperienceLocalServiceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Adolfo Pérez
 */
public class LayoutInfoItemFormProviderUtil {

	public static InfoForm getInfoForm() {
		return InfoForm.builder(
		).infoFieldSetEntry(
			_getBasicInformationInfoFieldSet()
		).build();
	}

	public static InfoForm getInfoForm(
		FragmentRendererController fragmentRendererController, Layout layout,
		long segmentsExperienceId) {

		if (!layout.isTypeContent()) {
			return getInfoForm();
		}

		long defaultSegmentsExperienceId =
			SegmentsExperienceLocalServiceUtil.fetchDefaultSegmentsExperienceId(
				layout.getPlid());

		if (segmentsExperienceId != defaultSegmentsExperienceId) {
			return InfoForm.builder(
			).infoFieldSetEntry(
				_getLayoutInfoFieldSet(
					fragmentRendererController, layout, segmentsExperienceId)
			).build();
		}

		return InfoForm.builder(
		).infoFieldSetEntry(
			_getBasicInformationInfoFieldSet()
		).infoFieldSetEntry(
			_getLayoutInfoFieldSet(
				fragmentRendererController, layout, segmentsExperienceId)
		).build();
	}

	private static InfoFieldSet _getBasicInformationInfoFieldSet() {
		return InfoFieldSet.builder(
		).infoFieldSetEntry(
			LayoutInfoItemFields.nameInfoField
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				LayoutInfoItemFormProviderUtil.class, "basic-information")
		).name(
			"basic-information"
		).build();
	}

	private static InfoFieldSet _getLayoutInfoFieldSet(
		FragmentRendererController fragmentRendererController, Layout layout,
		long segmentsExperienceId) {

		return InfoFieldSet.builder(
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				LayoutInfoItemFormProviderUtil.class, "inline-content")
		).infoFieldSetEntries(
			_getLayoutInfoFieldSetEntries(
				fragmentRendererController, layout, segmentsExperienceId)
		).name(
			"inline-content"
		).build();
	}

	private static List<InfoFieldSetEntry> _getLayoutInfoFieldSetEntries(
		FragmentRendererController fragmentRendererController, Layout layout,
		long segmentsExperienceId) {

		List<InfoFieldSetEntry> infoFieldSetEntries = new ArrayList<>();

		InfoFieldUtil.forEachInfoField(
			fragmentRendererController, layout, segmentsExperienceId,
			(infoField, type, unsafeSupplier) -> infoFieldSetEntries.add(
				infoField));

		return infoFieldSetEntries;
	}

}