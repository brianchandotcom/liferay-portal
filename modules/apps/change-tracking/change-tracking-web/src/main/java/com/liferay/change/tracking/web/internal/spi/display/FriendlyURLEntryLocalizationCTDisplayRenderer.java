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

package com.liferay.change.tracking.web.internal.spi.display;

import com.liferay.change.tracking.spi.display.BaseCTDisplayRenderer;
import com.liferay.change.tracking.spi.display.CTDisplayRenderer;
import com.liferay.friendly.url.model.FriendlyURLEntryLocalization;
import com.liferay.portal.kernel.language.Language;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Truong
 */
@Component(service = CTDisplayRenderer.class)
public class FriendlyURLEntryLocalizationCTDisplayRenderer
	extends BaseCTDisplayRenderer<FriendlyURLEntryLocalization> {

	@Override
	public Class<FriendlyURLEntryLocalization> getModelClass() {
		return FriendlyURLEntryLocalization.class;
	}

	@Override
	public String getTitle(
		Locale locale,
		FriendlyURLEntryLocalization friendlyURLEntryLocalization) {

		return _language.format(
			locale, "x-for-x",
			new String[] {
				"model.resource." +
					FriendlyURLEntryLocalization.class.getName(),
				friendlyURLEntryLocalization.getLanguageId()
			});
	}

	@Override
	public boolean isHideable(
		FriendlyURLEntryLocalization friendlyURLEntryLocalization) {

		return true;
	}

	@Reference
	private Language _language;

}