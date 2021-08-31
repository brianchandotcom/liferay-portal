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
import com.liferay.info.field.InfoFieldSet;
import com.liferay.info.field.InfoFieldSetEntry;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.layout.admin.web.internal.info.item.LayoutInfoItemFields;
import com.liferay.layout.admin.web.internal.util.InfoFieldUtil;
import com.liferay.portal.kernel.model.Layout;

import java.util.ArrayList;
import java.util.List;

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
		return InfoForm.builder(
		).infoFieldSetEntry(
			_getBasicInformationInfoFieldSet()
		).build();
	}

	@Override
	public InfoForm getInfoForm(Layout layout) {
		if (!layout.isTypeContent()) {
			return getInfoForm();
		}

		return InfoForm.builder(
		).infoFieldSetEntry(
			_getBasicInformationInfoFieldSet()
		).infoFieldSetEntry(
			_getLayoutInfoFieldSet(layout)
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

	private InfoFieldSet _getLayoutInfoFieldSet(Layout layout) {
		return InfoFieldSet.builder(
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(getClass(), "inline-content")
		).infoFieldSetEntries(
			_getLayoutInfoFieldSetEntries(layout)
		).name(
			"inline-content"
		).build();
	}

	private List<InfoFieldSetEntry> _getLayoutInfoFieldSetEntries(
		Layout layout) {

		List<InfoFieldSetEntry> infoFieldSetEntries = new ArrayList<>();

		InfoFieldUtil.forEachInfoField(
			_fragmentRendererController, layout,
			(name, infoField, unsafeSupplier) -> infoFieldSetEntries.add(
				infoField));

		return infoFieldSetEntries;
	}

	@Reference
	private FragmentRendererController _fragmentRendererController;

}