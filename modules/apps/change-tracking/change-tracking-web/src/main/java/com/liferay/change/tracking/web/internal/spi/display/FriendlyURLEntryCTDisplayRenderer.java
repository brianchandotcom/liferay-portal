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
import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Truong
 */
@Component(immediate = true, service = CTDisplayRenderer.class)
public class FriendlyURLEntryCTDisplayRenderer
	extends BaseCTDisplayRenderer<FriendlyURLEntry> {

	@Override
	public Class<FriendlyURLEntry> getModelClass() {
		return FriendlyURLEntry.class;
	}

	@Override
	public String getTitle(Locale locale, FriendlyURLEntry friendlyURLEntry) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			locale, getClass());

		return _language.format(
			resourceBundle, "x-for-x",
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