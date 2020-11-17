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

package com.liferay.commerce.account.web.internal.servlet.taglib.ui;

import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.frontend.taglib.form.navigator.BaseJSPFormNavigatorEntry;
import com.liferay.frontend.taglib.form.navigator.FormNavigatorEntry;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.taglib.util.CustomAttributesUtil;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Ethan Bustad
 */
@Component(
	enabled = false, property = "form.navigator.entry.order:Integer=10",
	service = FormNavigatorEntry.class
)
public class CommerceAccountDetailsCustomFieldsFormNavigatorEntry
	extends BaseJSPFormNavigatorEntry<CommerceAccount> {

	@Override
	public String getCategoryKey() {
		return "details";
	}

	@Override
	public String getFormNavigatorId() {
		return "commerce.accounts.form";
	}

	@Override
	public String getKey() {
		return "custom-fields";
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, getKey());
	}

	@Override
	public boolean isVisible(
		User user, CommerceAccount cpDefinitionSpecificationOptionValue) {

		boolean hasCustomAttributesAvailable = false;

		try {
			long classPK = 0;

			if (cpDefinitionSpecificationOptionValue != null) {
				classPK =
					cpDefinitionSpecificationOptionValue.getCommerceAccountId();
			}

			hasCustomAttributesAvailable =
				CustomAttributesUtil.hasCustomAttributes(
					user.getCompanyId(), CommerceAccount.class.getName(),
					classPK, null);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
		}

		return hasCustomAttributesAvailable;
	}

	@Override
	protected String getJspPath() {
		return "/account/custom_fields.jsp";
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceAccountDetailsCustomFieldsFormNavigatorEntry.class);

}