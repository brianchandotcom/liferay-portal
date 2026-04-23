/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.portlet;

import com.liferay.fragment.listener.FragmentEntryLinkListenerRegistry;
import com.liferay.fragment.renderer.FragmentRendererRegistry;
import com.liferay.fragment.service.FragmentEntryLinkService;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.search.InfoSearchClassMapperRegistry;
import com.liferay.layout.manager.FormManager;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryService;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.site.cms.site.initializer.internal.util.ActionUtil;

import jakarta.portlet.Portlet;
import jakarta.portlet.PortletException;
import jakarta.portlet.RenderRequest;
import jakarta.portlet.RenderResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = {
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.single-page-application=false",
		"jakarta.portlet.display-name=CMS Object Entry",
		"jakarta.portlet.expiration-cache=0",
		"jakarta.portlet.name=com_liferay_site_cms_site_initializer_internal_portlet_CMSObjectEntryPortlet",
		"jakarta.portlet.resource-bundle=content.Language",
		"jakarta.portlet.security-role-ref=power-user,user",
		"jakarta.portlet.version=4.0"
	},
	service = Portlet.class
)
public class CMSObjectEntryPortlet extends MVCPortlet {

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		try {
			HttpServletRequest httpServletRequest =
				_portal.getHttpServletRequest(renderRequest);

			ObjectEntry objectEntry = _objectEntryService.getObjectEntry(
				ParamUtil.getLong(renderRequest, "objectEntryId"));

			String editURL = ActionUtil.getEditURL(
				_formManager, _fragmentEntryLinkListenerRegistry,
				_fragmentEntryLinkService, _fragmentRendererRegistry,
				httpServletRequest,
				String.valueOf(objectEntry.getObjectEntryId()),
				_infoItemServiceRegistry, _infoSearchClassMapperRegistry,
				_objectDefinitionLocalService.getObjectDefinition(
					objectEntry.getObjectDefinitionId()));

			String redirect = ParamUtil.getString(
				httpServletRequest, "redirect");

			if (Validator.isNotNull(redirect)) {
				editURL = HttpComponentsUtil.addParameter(
					editURL, "redirect", redirect);
			}

			String layoutMode = ParamUtil.getString(
				httpServletRequest, "p_l_mode");

			if (Objects.equals(layoutMode, Constants.READ)) {
				editURL = HttpComponentsUtil.addParameter(
					editURL, "p_l_mode", layoutMode);
			}

			String windowState = ParamUtil.getString(
				httpServletRequest, "p_p_state");

			if (Validator.isNotNull(windowState)) {
				editURL = HttpComponentsUtil.addParameter(
					editURL, "p_p_state", windowState);
			}

			int version = ParamUtil.getInteger(httpServletRequest, "version");

			if (version > 0) {
				editURL = HttpComponentsUtil.addParameter(
					editURL, "version", version);
			}

			HttpServletResponse httpServletResponse =
				_portal.getHttpServletResponse(renderResponse);

			httpServletResponse.sendRedirect(editURL);
		}
		catch (Exception exception) {
			throw new PortletException(exception);
		}
	}

	@Reference
	private FormManager _formManager;

	@Reference
	private FragmentEntryLinkListenerRegistry
		_fragmentEntryLinkListenerRegistry;

	@Reference
	private FragmentEntryLinkService _fragmentEntryLinkService;

	@Reference
	private FragmentRendererRegistry _fragmentRendererRegistry;

	@Reference
	private InfoItemServiceRegistry _infoItemServiceRegistry;

	@Reference
	private InfoSearchClassMapperRegistry _infoSearchClassMapperRegistry;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryService _objectEntryService;

	@Reference
	private Portal _portal;

}