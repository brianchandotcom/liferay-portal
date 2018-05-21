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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.portlet.configuration.icon.PortletConfigurationIcon;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.workflow.kaleo.designer.web.constants.KaleoDesignerPortletKeys;
import com.liferay.portal.workflow.kaleo.designer.web.internal.constants.KaleoDesignerWebKeys;
import com.liferay.portal.workflow.kaleo.designer.web.internal.permission.KaleoDefinitionVersionPermission;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Configuration icon to allow the deletion of a workflow definition.
 *
 * @author Adam Brandizzi
 * @review
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + KaleoDesignerPortletKeys.KALEO_DESIGNER,
		"path=/designer/edit_kaleo_definition_version.jsp"
	},
	service = PortletConfigurationIcon.class
)
public class DeleteDefinitionPortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	@Override
	public String getMessage(PortletRequest portletRequest) {
		return LanguageUtil.get(getLocale(portletRequest), "delete");
	}

	@Override
	public String getOnClick(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		String portletId = _portal.getPortletId(portletRequest);

		String portletNamespace = _portal.getPortletNamespace(portletId);

		String deleteURL = getURL(portletRequest, portletResponse);

		StringBundler sb = new StringBundler(4);

		sb.append(portletNamespace);
		sb.append("confirmDeleteDefinition('");
		sb.append(deleteURL);
		sb.append("'); return false;");

		return sb.toString();
	}

	/**
	 * Creates and returns an action URL passing the workflow definition name
	 * and version as parameters.
	 *
	 * @review
	 */
	@Override
	public String getURL(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		PortletURL portletURL = _portal.getControlPanelPortletURL(
			portletRequest, KaleoDesignerPortletKeys.KALEO_DESIGNER,
			PortletRequest.ACTION_PHASE);

		portletURL.setParameter(
			ActionRequest.ACTION_NAME, "deleteKaleoDefinitionVersion");
		portletURL.setParameter("name", portletRequest.getParameter("name"));
		portletURL.setParameter(
			"draftVersion", portletRequest.getParameter("draftVersion"));

		return portletURL.toString();
	}

	@Override
	public boolean isShow(PortletRequest portletRequest) {
		KaleoDefinitionVersion kaleoDefinitionVersion =
			(KaleoDefinitionVersion)portletRequest.getAttribute(
				KaleoDesignerWebKeys.KALEO_DRAFT_DEFINITION);

		if (kaleoDefinitionVersion == null) {
			return false;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		boolean hasDeletePermission = KaleoDefinitionVersionPermission.contains(
			themeDisplay.getPermissionChecker(), kaleoDefinitionVersion,
			ActionKeys.DELETE);

		try {
			KaleoDefinition kaleoDefinition =
				kaleoDefinitionVersion.getKaleoDefinition();

			if (hasDeletePermission && !kaleoDefinition.isActive()) {
				return true;
			}

			return false;
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}
		}

		if (hasDeletePermission && kaleoDefinitionVersion.isDraft()) {
			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DeleteDefinitionPortletConfigurationIcon.class);

	@Reference
	private Portal _portal;

}