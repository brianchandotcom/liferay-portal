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

package com.liferay.asset.internal.info.renderer;

import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.info.item.renderer.InfoItemRenderer;
import com.liferay.portal.kernel.language.Language;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge Ferrer
 */
@Component(
	property = "service.ranking:Integer=200", service = InfoItemRenderer.class
)
public class AssetEntryFullContentInfoItemRenderer
	extends BaseAssetEntryInfoItemRenderer {

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "full-content");
	}

	@Override
	protected String getTemplate() {
		return AssetRenderer.TEMPLATE_FULL_CONTENT;
	}

	@Reference
	private Language _language;

}