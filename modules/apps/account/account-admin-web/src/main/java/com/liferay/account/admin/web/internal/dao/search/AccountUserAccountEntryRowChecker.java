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

package com.liferay.account.admin.web.internal.dao.search;

import com.liferay.account.admin.web.internal.display.AccountEntryDisplay;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalServiceUtil;
import com.liferay.account.service.AccountEntryUserRelLocalServiceUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Validator;

import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Albert Lee
 */
public class AccountUserAccountEntryRowChecker extends EmptyOnClickRowChecker {

	public AccountUserAccountEntryRowChecker(
		PortletResponse portletResponse, long accountUserId) {

		super(portletResponse);

		_accountUserId = accountUserId;
	}

	@Override
	public boolean isChecked(Object object) {
		return isDisabled(object);
	}

	@Override
	public boolean isDisabled(Object object) {
		AccountEntryDisplay accountEntryDisplay = (AccountEntryDisplay)object;

		return AccountEntryUserRelLocalServiceUtil.hasAccountEntryUserRel(
			accountEntryDisplay.getAccountEntryId(), _accountUserId);
	}

	@Override
	protected String getRowCheckBox(
		HttpServletRequest httpServletRequest, boolean checked,
		boolean disabled, String name, String value, String checkBoxRowIds,
		String checkBoxAllRowIds, String checkBoxPostOnClick) {

		StringBundler sb = new StringBundler(20);

		sb.append("<input ");

		if (checked) {
			sb.append("checked ");
		}

		sb.append("class=\"");
		sb.append(getCssClass());
		sb.append("\" ");

		AccountEntry accountEntry =
			AccountEntryLocalServiceUtil.fetchAccountEntry(
				GetterUtil.getLong(value));

		sb.append("data-entityid=\"");
		sb.append(value);
		sb.append("\" data-entityname=\"");
		sb.append(accountEntry.getName());
		sb.append("\" ");

		if (disabled) {
			sb.append("disabled ");
		}

		sb.append("name=\"");
		sb.append(name);
		sb.append("\" title=\"");
		sb.append(LanguageUtil.get(httpServletRequest.getLocale(), "select"));
		sb.append("\" type=\"checkbox\" value=\"");
		sb.append(HtmlUtil.escapeAttribute(value));
		sb.append("\" ");

		if (Validator.isNotNull(getAllRowIds())) {
			sb.append(
				getOnClick(
					checkBoxRowIds, checkBoxAllRowIds, checkBoxPostOnClick));
		}

		sb.append(">");

		return sb.toString();
	}

	private final long _accountUserId;

}