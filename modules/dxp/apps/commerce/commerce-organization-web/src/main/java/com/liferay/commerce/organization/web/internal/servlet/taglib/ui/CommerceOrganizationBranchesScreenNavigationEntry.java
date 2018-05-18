/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.organization.web.internal.servlet.taglib.ui;

import com.liferay.commerce.organization.constants.CommerceOrganizationConstants;
import com.liferay.commerce.organization.service.CommerceOrganizationService;
import com.liferay.commerce.organization.util.CommerceOrganizationHelper;
import com.liferay.commerce.organization.web.internal.display.context.CommerceOrganizationBranchesDisplayContext;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	property = "screen.navigation.entry.order:Integer=30",
	service = ScreenNavigationEntry.class
)
public class CommerceOrganizationBranchesScreenNavigationEntry
	implements ScreenNavigationEntry<Organization> {

	@Override
	public String getCategoryKey() {
		return CommerceOrganizationScreenNavigationConstants.CATEGORY_DETAILS;
	}

	@Override
	public String getEntryKey() {
		return CommerceOrganizationScreenNavigationConstants.
			ENTRY_KEY_ORGANIZATION_BRANCHES;
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(resourceBundle, "branches");
	}

	@Override
	public String getScreenNavigationKey() {
		return CommerceOrganizationScreenNavigationConstants.
			SCREEN_NAVIGATION_KEY;
	}

	@Override
	public boolean isVisible(User user, Organization organization) {
		String type = organization.getType();

		if (type.equals(CommerceOrganizationConstants.TYPE_ACCOUNT)) {
			return true;
		}

		return false;
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		CommerceOrganizationBranchesDisplayContext
			commerceOrganizationBranchesDisplayContext =
				new CommerceOrganizationBranchesDisplayContext(
					_commerceOrganizationHelper, _commerceOrganizationService,
					httpServletRequest, _portal);

		httpServletRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT,
			commerceOrganizationBranchesDisplayContext);

		_jspRenderer.renderJSP(
			httpServletRequest, httpServletResponse, "/view_branches.jsp");
	}

	@Reference
	private CommerceOrganizationHelper _commerceOrganizationHelper;

	@Reference
	private CommerceOrganizationService _commerceOrganizationService;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference
	private Portal _portal;

}