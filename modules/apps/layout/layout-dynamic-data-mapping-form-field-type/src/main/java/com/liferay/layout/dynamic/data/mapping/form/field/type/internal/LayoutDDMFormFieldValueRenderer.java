/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.dynamic.data.mapping.form.field.type.internal;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueRenderer;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.layout.dynamic.data.mapping.form.field.type.constants.LayoutDDMFormFieldTypeConstants;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutService;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	property = "ddm.form.field.type.name=" + LayoutDDMFormFieldTypeConstants.LINK_TO_LAYOUT,
	service = DDMFormFieldValueRenderer.class
)
public class LayoutDDMFormFieldValueRenderer
	implements DDMFormFieldValueRenderer {

	@Override
	public String render(DDMFormFieldValue ddmFormFieldValue, Locale locale) {
		Value value = ddmFormFieldValue.getValue();

		if ((value == null) || Validator.isNull(value.getString(locale))) {
			return StringPool.BLANK;
		}

		try {
			JSONObject jsonObject = _jsonFactory.createJSONObject(
				value.getString(locale));

			Layout layout = _layoutService.fetchLayout(
				jsonObject.getLong("groupId"),
				jsonObject.getBoolean("privateLayout"),
				jsonObject.getLong("layoutId"));

			if (layout == null) {
				return StringPool.BLANK;
			}

			return layout.getName(locale);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return _language.format(
				locale, "is-temporarily-unavailable", "page");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutDDMFormFieldValueRenderer.class);

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

	@Reference
	private LayoutService _layoutService;

}