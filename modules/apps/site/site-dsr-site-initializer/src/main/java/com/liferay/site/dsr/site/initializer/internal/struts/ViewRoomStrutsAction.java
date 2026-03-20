/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.dsr.site.initializer.internal.struts;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.Objects;

/**
 * @author Stefano Motta
 */
@Component(property = "path=/dsr/view_room", service = StrutsAction.class)
public class ViewRoomStrutsAction implements StrutsAction {

	@Override
	public String execute(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		Group group = _groupLocalService.getGroup(
			ParamUtil.getLong(httpServletRequest, "siteId"));

		if (!GroupPermissionUtil.contains(
			PermissionThreadLocal.getPermissionChecker(), group,
			ActionKeys.VIEW)){

			SessionErrors.add(httpServletRequest,
				PrincipalException.MustHavePermission.class);

			httpServletResponse.sendRedirect(
				httpServletRequest.getHeader(HttpHeaders.REFERER));

			return null;
		}

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					"L_DSR_ROOM", group.getCompanyId());

		if (!Objects.equals(
				group.getClassName(), objectDefinition.getClassName())) {

			return null;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		String groupFriendlyURL = _portal.getGroupFriendlyURL(
			group.getPublicLayoutSet(), themeDisplay, false, false);

		if (Objects.equals(
				ParamUtil.getString(httpServletRequest, "mode", "view"),
				"edit")) {

			httpServletResponse.sendRedirect(
				StringBundler.concat(
					groupFriendlyURL, "?p_l_back_url=", groupFriendlyURL,
					"&p_l_mode=edit"));
		}
		else {
			httpServletResponse.sendRedirect(groupFriendlyURL);
		}

		return null;
	}

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private Portal _portal;
}