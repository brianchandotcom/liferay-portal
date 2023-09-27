/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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