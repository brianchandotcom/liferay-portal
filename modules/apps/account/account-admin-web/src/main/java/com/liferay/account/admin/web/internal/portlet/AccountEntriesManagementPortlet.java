/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.admin.web.internal.portlet;

import com.liferay.account.admin.web.internal.constants.AccountWebKeys;
import com.liferay.account.constants.AccountConstants;
import com.liferay.account.constants.AccountPortletKeys;
import com.liferay.account.settings.AccountEntryGroupSettings;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Portal;

import java.io.IOException;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(
	property = {
		"com.liferay.portlet.display-category=category.accounts",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Account Management",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/META-INF/resources/",
		"javax.portlet.init-param.view-template=/account_entries_admin/view.jsp",
		"javax.portlet.name=" + AccountPortletKeys.ACCOUNT_ENTRIES_MANAGEMENT,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator,power-user,user",
		"javax.portlet.version=3.0"
	},
	service = Portlet.class
)
public class AccountEntriesManagementPortlet extends MVCPortlet {

	@Override
	protected void doDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		try {
			if (FeatureFlagManagerUtil.isEnabled("COMMERCE-10890")) {
				renderRequest.setAttribute(
					AccountWebKeys.ACCOUNT_ENTRY_ALLOWED_TYPES,
					ArrayUtil.append(
						_accountEntryGroupSettings.getAllowedTypes(
							_portal.getScopeGroupId(renderRequest)),
						AccountConstants.ACCOUNT_ENTRY_TYPE_SUPPLIER));
			}
			else {
				renderRequest.setAttribute(
					AccountWebKeys.ACCOUNT_ENTRY_ALLOWED_TYPES,
					_accountEntryGroupSettings.getAllowedTypes(
						_portal.getScopeGroupId(renderRequest)));
			}

			super.doDispatch(renderRequest, renderResponse);
		}
		catch (PortalException portalException) {
			throw new PortletException(portalException);
		}
	}

	@Reference
	private AccountEntryGroupSettings _accountEntryGroupSettings;

	@Reference
	private Portal _portal;

}