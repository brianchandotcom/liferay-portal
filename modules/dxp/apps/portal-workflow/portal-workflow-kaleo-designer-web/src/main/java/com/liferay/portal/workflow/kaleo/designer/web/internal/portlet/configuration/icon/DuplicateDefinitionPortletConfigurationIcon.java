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

package com.liferay.portal.workflow.kaleo.designer.web.internal.portlet.configuration.icon;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.portlet.configuration.icon.PortletConfigurationIcon;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.workflow.kaleo.designer.web.constants.KaleoDesignerPortletKeys;
import com.liferay.portal.workflow.kaleo.designer.web.internal.constants.KaleoDesignerWebKeys;
import com.liferay.portal.workflow.kaleo.designer.web.internal.permission.KaleoDefinitionVersionPermission;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;

import java.util.ResourceBundle;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Defines the icon triggering duplication of the workflow definition.
 *
 * @author Jeyvison Nascimento
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + KaleoDesignerPortletKeys.KALEO_DESIGNER,
		"path=/designer/edit_kaleo_definition_version.jsp"
	},
	service = PortletConfigurationIcon.class
)
public class DuplicateDefinitionPortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	@Override
	public String getMessage(PortletRequest portletRequest) {
		ResourceBundle resourceBundle =
			_resourceBundleLoader.loadResourceBundle(getLocale(portletRequest));

		return LanguageUtil.get(resourceBundle, "duplicate");
	}

	@Override
	public String getOnClick(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		String portletId = _portal.getPortletId(portletRequest);

		String portletNamespace = _portal.getPortletNamespace(portletId);

		return "Liferay.fire('" + portletNamespace + "duplicateDefinition');";
	}

	@Override
	public String getURL(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		return "javascript:;";
	}

	@Override
	public boolean isShow(PortletRequest portletRequest) {
		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		KaleoDefinitionVersion kaleoDefinitionVersion =
			getKaleoDefinitionVersion(portletRequest);

		if (kaleoDefinitionVersion == null) {
			return false;
		}

		return KaleoDefinitionVersionPermission.hasViewPermission(
			themeDisplay.getPermissionChecker(), kaleoDefinitionVersion,
			themeDisplay.getCompanyGroupId());
	}

	protected KaleoDefinitionVersion getKaleoDefinitionVersion(
		PortletRequest portletRequest) {

		return (KaleoDefinitionVersion)portletRequest.getAttribute(
			KaleoDesignerWebKeys.KALEO_DRAFT_DEFINITION);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DuplicateDefinitionPortletConfigurationIcon.class);

	@Reference
	private Portal _portal;

	@Reference(
		target = "(bundle.symbolic.name=com.liferay.portal.workflow.web)"
	)
	private ResourceBundleLoader _resourceBundleLoader;

}