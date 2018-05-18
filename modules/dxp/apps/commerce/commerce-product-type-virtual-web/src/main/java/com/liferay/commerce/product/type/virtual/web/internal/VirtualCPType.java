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

package com.liferay.commerce.product.type.virtual.web.internal;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.type.CPType;
import com.liferay.commerce.product.type.virtual.constants.VirtualCPTypeConstants;
import com.liferay.commerce.product.type.virtual.service.CPDefinitionVirtualSettingLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	immediate = true,
	property = {
		"commerce.product.type.display.order:Integer=15",
		"commerce.product.type.name=" + VirtualCPTypeConstants.NAME
	},
	service = CPType.class
)
public class VirtualCPType implements CPType {

	@Override
	public void deleteCPDefinition(long cpDefinitionId) throws PortalException {
		_cpDefinitionVirtualSettingLocalService.
			deleteCPDefinitionVirtualSettingByCPDefinitionId(cpDefinitionId);
	}

	@Override
	public String getCPDefinitionEditUrl(
			long cpDefinitionId, HttpServletRequest httpServletRequest)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletURL portletURL = PortletProviderUtil.getPortletURL(
			httpServletRequest, themeDisplay.getScopeGroup(),
			CPDefinition.class.getName(), PortletProvider.Action.EDIT);

		portletURL.setParameter(
			"mvcRenderCommandName", "editProductDefinitionVirtualSetting");
		portletURL.setParameter(
			"cpDefinitionId", String.valueOf(cpDefinitionId));

		String redirect = ParamUtil.getString(httpServletRequest, "redirect");

		if (Validator.isNotNull(redirect)) {
			portletURL.setParameter("redirect", redirect);
		}

		String backURL = ParamUtil.getString(httpServletRequest, "backURL");

		if (Validator.isNotNull(backURL)) {
			portletURL.setParameter("backURL", backURL);
		}

		return portletURL.toString();
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(resourceBundle, VirtualCPTypeConstants.NAME);
	}

	@Override
	public String getName() {
		return VirtualCPTypeConstants.NAME;
	}

	@Reference
	private CPDefinitionVirtualSettingLocalService
		_cpDefinitionVirtualSettingLocalService;

}