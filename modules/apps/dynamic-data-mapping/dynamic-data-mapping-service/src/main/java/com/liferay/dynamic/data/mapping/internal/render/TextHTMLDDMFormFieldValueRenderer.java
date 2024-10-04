/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.render;

import com.liferay.dynamic.data.mapping.model.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.render.ValueAccessor;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.content.security.policy.ContentSecurityPolicyNonceProviderUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Locale;

/**
 * @author Marcellus Tavares
 */
public class TextHTMLDDMFormFieldValueRenderer
	extends BaseTextDDMFormFieldValueRenderer {

	@Override
	public String getSupportedDDMFormFieldType() {
		return DDMFormFieldType.TEXT_HTML;
	}

	@Override
	protected ValueAccessor getValueAccessor(Locale locale) {
		return new ValueAccessor(locale) {

			@Override
			public String get(DDMFormFieldValue ddmFormFieldValue) {
				Value value = ddmFormFieldValue.getValue();

				StringBundler sb = new StringBundler(13);
				String id = StringUtil.randomId();

				sb.append("<a href=\"javascript:void(0);\" id=\"");
				sb.append(id);
				sb.append("\">(");
				sb.append(LanguageUtil.get(locale, "preview"));
				sb.append(")</a><script");
				sb.append(
					ContentSecurityPolicyNonceProviderUtil.getNonceAttribute(
						null));
				sb.append(">document.getElementById('");
				sb.append(id);
				sb.append("').onclick=function() {Liferay.DDLUtil.");
				sb.append("openPreviewDialog('");
				sb.append(HtmlUtil.escapeJS(value.getString(locale)));
				sb.append("');");
				sb.append("}</script>");

				return sb.toString();
			}

		};
	}

}