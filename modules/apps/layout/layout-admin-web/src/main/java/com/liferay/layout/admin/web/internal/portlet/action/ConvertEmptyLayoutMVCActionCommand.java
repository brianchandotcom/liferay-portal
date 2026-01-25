/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.admin.web.internal.portlet.action;

import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.layout.admin.web.internal.handler.LayoutExceptionRequestHandlerUtil;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.service.LayoutService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import jakarta.portlet.ActionRequest;
import jakarta.portlet.ActionResponse;
import jakarta.portlet.PortletRequest;

import java.util.Locale;
import java.util.Map;

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

			Layout layout = _layoutService.getLayout(selPlid);

			long layoutPageTemplateEntryId = ParamUtil.getLong(
				actionRequest, "layoutPageTemplateEntryId");

			String masterLayoutPageTemplateEntryERC = null;
			long masterLayoutPlid = 0;

			if (layoutPageTemplateEntryId > 0) {
				LayoutPageTemplateEntry masterLayoutPageTemplateEntry =
					_layoutPageTemplateEntryLocalService.
						fetchLayoutPageTemplateEntry(layoutPageTemplateEntryId);

				if (masterLayoutPageTemplateEntry.getLayoutPrototypeId() == 0) {
					type = LayoutConstants.TYPE_CONTENT;
				}
				else {
					type = LayoutConstants.TYPE_PORTLET;
				}

				masterLayoutPageTemplateEntryERC =
					masterLayoutPageTemplateEntry.getExternalReferenceCode();
				masterLayoutPlid = masterLayoutPageTemplateEntry.getPlid();
			}

			Map<Locale, String> nameMap = HashMapBuilder.put(
				LocaleUtil.getSiteDefault(),
				ParamUtil.getString(actionRequest, "name")
			).build();

			String redirect = null;

			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				Layout.class.getName(), actionRequest);

			ThemeDisplay themeDisplay =
				(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

			layout = _layoutService.convertEmptyLayout(
				layout.getPlid(), nameMap, type, layout.getClassNameId(),
				layout.getClassPK(), masterLayoutPageTemplateEntryERC,
				serviceContext);

			if (!layout.isTypeContent()) {
				if (masterLayoutPlid > 0) {
					layoutLocalService.copyLayoutContent(
						layoutLocalService.getLayout(masterLayoutPlid), layout);
				}

				redirect = PortletURLBuilder.createRenderURL(
					_portal.getLiferayPortletResponse(actionResponse)
				).setMVCRenderCommandName(
					"/layout_admin/edit_layout"
				).setBackURL(
					_getBackURL(actionRequest)
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
			else {
				Layout draftLayout = layout.fetchDraftLayout();

				redirect = HttpComponentsUtil.addParameters(
					PortalUtil.getLayoutFullURL(draftLayout, themeDisplay),
					"p_l_back_url", _getBackURL(actionRequest),
					"p_l_back_url_title",
					_language.get(themeDisplay.getLocale(), "pages"),
					"p_l_mode", Constants.EDIT);
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

	private String _getBackURL(ActionRequest actionRequest) {
		return PortletURLBuilder.create(
			_portal.getControlPanelPortletURL(
				actionRequest, LayoutAdminPortletKeys.GROUP_PAGES,
				PortletRequest.RENDER_PHASE)
		).buildString();
	}

	@Reference
	private Language _language;

	@Reference
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Reference
	private LayoutService _layoutService;

	@Reference
	private Portal _portal;

}