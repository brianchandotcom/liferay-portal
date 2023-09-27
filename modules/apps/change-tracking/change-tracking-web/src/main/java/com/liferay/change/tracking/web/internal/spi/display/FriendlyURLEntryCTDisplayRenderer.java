/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.web.internal.spi.display;

import com.liferay.change.tracking.spi.display.BaseCTDisplayRenderer;
import com.liferay.change.tracking.spi.display.CTDisplayRenderer;
import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.portal.kernel.language.Language;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Truong
 */
@Component(service = CTDisplayRenderer.class)
public class FriendlyURLEntryCTDisplayRenderer
	extends BaseCTDisplayRenderer<FriendlyURLEntry> {

	@Override
	public String[] getAvailableLanguageIds(FriendlyURLEntry friendlyURLEntry) {
		return friendlyURLEntry.getAvailableLanguageIds();
	}

	@Override
	public String getDefaultLanguageId(FriendlyURLEntry friendlyURLEntry) {
		return friendlyURLEntry.getDefaultLanguageId();
	}

	@Override
	public Class<FriendlyURLEntry> getModelClass() {
		return FriendlyURLEntry.class;
	}

	@Override
	public String getTitle(Locale locale, FriendlyURLEntry friendlyURLEntry) {
		return _language.format(
			locale, "x-for-x",
			new String[] {
				"model.resource." + FriendlyURLEntry.class.getName(),
				friendlyURLEntry.getUrlTitle(locale.getLanguage())
			});
	}

	@Override
	public boolean isHideable(FriendlyURLEntry friendlyURLEntry) {
		return true;
	}

	@Reference
	private Language _language;

}