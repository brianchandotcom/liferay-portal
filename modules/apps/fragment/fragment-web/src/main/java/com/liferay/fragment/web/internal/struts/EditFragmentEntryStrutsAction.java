/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.web.internal.struts;

import com.liferay.fragment.constants.FragmentActionKeys;
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.constants.FragmentPortletKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import jakarta.portlet.PortletRequest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sandro Chinea
 */
@Component(
	property = "path=/portal/fragment/edit_fragment_entry",
	service = StrutsAction.class
)
public class EditFragmentEntryStrutsAction implements StrutsAction {

	@Override
	public String execute(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		Group group = null;

		try {
			group = _groupLocalService.getGroupByExternalReferenceCode(
				ParamUtil.getString(
					httpServletRequest, "siteExternalReferenceCode"),
				_portal.getCompanyId(httpServletRequest));

			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			_portletResourcePermission.check(
				themeDisplay.getPermissionChecker(), group.getGroupId(),
				FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES);
		}
		catch (PortalException portalException) {
			_portal.sendError(
				portalException, httpServletRequest, httpServletResponse);

			return null;
		}

		httpServletResponse.sendRedirect(
			PortletURLBuilder.create(
				_portal.getControlPanelPortletURL(
					httpServletRequest, group, FragmentPortletKeys.FRAGMENT, 0,
					0, PortletRequest.RENDER_PHASE)
			).setMVCRenderCommandName(
				"/fragment/edit_fragment_entry"
			).setParameter(
				"externalReferenceCode",
				ParamUtil.getString(httpServletRequest, "externalReferenceCode")
			).buildString());

		return null;
	}

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(resource.name=" + FragmentConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

}