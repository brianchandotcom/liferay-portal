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

import com.liferay.commerce.organization.service.CommerceOrganizationService;
import com.liferay.commerce.organization.util.CommerceOrganizationHelper;
import com.liferay.commerce.organization.web.internal.display.context.CommerceOrganizationDetailDisplayContext;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationCategory;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.users.admin.configuration.UserFileUploadsConfiguration;

import java.io.IOException;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	configurationPid = "com.liferay.user.admin.configuration.UserFileUploadsConfiguration",
	property = "screen.navigation.entry.order:Integer=10",
	service = {ScreenNavigationCategory.class, ScreenNavigationEntry.class}
)
public class CommerceOrganizationDetailsScreenNavigationEntry
	implements ScreenNavigationEntry<Organization>, ScreenNavigationCategory {

	@Override
	public String getCategoryKey() {
		return CommerceOrganizationScreenNavigationConstants.CATEGORY_DETAILS;
	}

	@Override
	public String getEntryKey() {
		return CommerceOrganizationScreenNavigationConstants.
			ENTRY_KEY_ORGANIZATION_DETAIL;
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(resourceBundle, "organization-detail");
	}

	@Override
	public String getScreenNavigationKey() {
		return CommerceOrganizationScreenNavigationConstants.
			SCREEN_NAVIGATION_KEY;
	}

	@Override
	public boolean isVisible(User user, Organization organization) {
		if (organization == null) {
			return false;
		}

		return true;
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		CommerceOrganizationDetailDisplayContext
			commerceOrganizationDetailDisplayContext =
				new CommerceOrganizationDetailDisplayContext(
					_commerceOrganizationHelper, _commerceOrganizationService,
					httpServletRequest, _organizationLocalService, _portal,
					_userFileUploadsConfiguration);

		httpServletRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT,
			commerceOrganizationDetailDisplayContext);

		_jspRenderer.renderJSP(
			httpServletRequest, httpServletResponse,
			"/edit_organization_detail.jsp");
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_userFileUploadsConfiguration = ConfigurableUtil.createConfigurable(
			UserFileUploadsConfiguration.class, properties);
	}

	@Reference
	private CommerceOrganizationHelper _commerceOrganizationHelper;

	@Reference
	private CommerceOrganizationService _commerceOrganizationService;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference
	private OrganizationLocalService _organizationLocalService;

	@Reference
	private Portal _portal;

	private volatile UserFileUploadsConfiguration _userFileUploadsConfiguration;

}