/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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