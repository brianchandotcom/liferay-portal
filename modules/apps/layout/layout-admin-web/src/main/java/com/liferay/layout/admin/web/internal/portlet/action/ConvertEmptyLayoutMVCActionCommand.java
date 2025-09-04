/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.admin.web.internal.portlet.action;

import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.layout.admin.web.internal.handler.LayoutExceptionRequestHandlerUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import jakarta.portlet.ActionRequest;
import jakarta.portlet.ActionResponse;
import jakarta.portlet.PortletRequest;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brooke Dalton
 */
@Component(
	property = {
		"jakarta.portlet.name=" + LayoutAdminPortletKeys.GROUP_PAGES,
		"mvc.command.name=/layout_admin/convert_empty_layout"
	},
	service = MVCActionCommand.class
)
public class ConvertEmptyLayoutMVCActionCommand
	extends BaseAddLayoutMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			String type = ParamUtil.getString(actionRequest, "type");

			if (type.equals(LayoutConstants.TYPE_EMBEDDED) ||
				type.equals(LayoutConstants.TYPE_LINK_TO_LAYOUT)) {

				SessionErrors.add(actionRequest, PortalException.class);

				return;
			}

			long selPlid = ParamUtil.getLong(actionRequest, "selPlid");

			Map<Locale, String> nameMap = HashMapBuilder.put(
				LocaleUtil.getSiteDefault(),
				ParamUtil.getString(actionRequest, "name")
			).build();

			Layout layout = _layoutLocalService.fetchLayout(selPlid);

			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				Layout.class.getName(), actionRequest);

			layout = _layoutLocalService.updateLayout(
				layout.getGroupId(), layout.isPrivateLayout(),
				layout.getLayoutId(), layout.getParentLayoutId(), nameMap,
				layout.getTitleMap(), layout.getDescriptionMap(),
				layout.getKeywordsMap(), layout.getRobotsMap(), type,
				layout.isHidden(), layout.getFriendlyURLMap(),
				layout.isIconImage(), null, layout.getStyleBookEntryId(),
				layout.getFaviconFileEntryId(), layout.getMasterLayoutPlid(),
				serviceContext);

			String redirect = null;

			if (!Objects.equals(type, LayoutConstants.TYPE_CONTENT)) {
				ThemeDisplay themeDisplay =
					(ThemeDisplay)actionRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

				redirect = PortletURLBuilder.createRenderURL(
					_portal.getLiferayPortletResponse(actionResponse)
				).setMVCRenderCommandName(
					"/layout_admin/edit_layout"
				).setBackURL(
					() -> _portal.getControlPanelPortletURL(
						actionRequest, LayoutAdminPortletKeys.GROUP_PAGES,
						PortletRequest.RENDER_PHASE)
				).setParameter(
					"backURLTitle", layout.getName()
				).setParameter(
					"groupId", themeDisplay.getScopeGroupId()
				).setParameter(
					"privateLayout", layout.isPrivateLayout()
				).setParameter(
					"selPlid", selPlid
				).buildString();
			}

			JSONPortletResponseUtil.writeJSON(
				actionRequest, actionResponse,
				JSONUtil.put("redirectURL", redirect));
		}
		catch (Exception exception) {
			LayoutExceptionRequestHandlerUtil.handleException(
				actionRequest, actionResponse, exception);
		}
	}

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

}