/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.term.internal.entry.type;

import com.liferay.commerce.term.constants.CommerceTermEntryConstants;
import com.liferay.commerce.term.entry.type.CommerceTermEntryType;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "commerce.term.entry.type.key=" + CommerceTermEntryConstants.TYPE_PAYMENT_TERMS,
	service = CommerceTermEntryType.class
)
public class PaymentTermsCommerceTermEntryType
	implements CommerceTermEntryType {

	@Override
	public String getKey() {
		return CommerceTermEntryConstants.TYPE_PAYMENT_TERMS;
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return _language.get(
			resourceBundle, CommerceTermEntryConstants.TYPE_PAYMENT_TERMS);
	}

	@Reference
	private Language _language;

}