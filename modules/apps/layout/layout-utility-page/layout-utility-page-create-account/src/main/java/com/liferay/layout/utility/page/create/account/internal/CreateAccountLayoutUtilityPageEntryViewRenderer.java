/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.utility.page.create.account.internal;

import com.liferay.layout.utility.page.kernel.LayoutUtilityPageEntryViewRenderer;
import com.liferay.layout.utility.page.kernel.constants.LayoutUtilityPageEntryConstants;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.language.Language;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alvaro Saugar
 */
@Component(
	property = "utility.page.type=" + LayoutUtilityPageEntryConstants.TYPE_CREATE_ACCOUNT,
	service = LayoutUtilityPageEntryViewRenderer.class
)
public class CreateAccountLayoutUtilityPageEntryViewRenderer
	implements LayoutUtilityPageEntryViewRenderer {

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "create-account");
	}

	@Override
	public String getType() {
		return LayoutUtilityPageEntryConstants.TYPE_CREATE_ACCOUNT;
	}

	@Override
	public boolean isEnabled() {
		return FeatureFlagManagerUtil.isEnabled("LPD-6378");
	}

	@Reference
	private Language _language;

}