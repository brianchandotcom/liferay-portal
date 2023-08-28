/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.admin.web.internal.portlet.action;

import com.liferay.item.selector.ItemSelector;
import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.layout.admin.web.internal.display.context.LayoutsAdminDisplayContext;
import com.liferay.layout.admin.web.internal.display.context.MillerColumnsDisplayContext;
import com.liferay.layout.admin.web.internal.helper.LayoutActionsHelper;
import com.liferay.layout.admin.web.internal.servlet.taglib.util.LayoutActionDropdownItemsProvider;
import com.liferay.layout.helper.LayoutCopyHelper;
import com.liferay.layout.set.prototype.helper.LayoutSetPrototypeHelper;
import com.liferay.layout.util.template.LayoutConverterRegistry;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.staging.StagingGroupHelper;
import com.liferay.translation.security.permission.TranslationPermission;
import com.liferay.translation.url.provider.TranslationURLProvider;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = {
		"javax.portlet.name=" + LayoutAdminPortletKeys.GROUP_PAGES,
		"mvc.command.name=/layout_admin/get_layout_children"
	},
	service = MVCActionCommand.class
)
public class GetLayoutChildrenMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		LayoutActionsHelper layoutActionsHelper = new LayoutActionsHelper(
			_layoutConverterRegistry, themeDisplay, _translationPermission);

		LayoutsAdminDisplayContext layoutsAdminDisplayContext =
			new LayoutsAdminDisplayContext(
				_itemSelector, layoutActionsHelper, _layoutCopyHelper,
				_layoutSetPrototypeHelper,
				_portal.getLiferayPortletRequest(actionRequest),
				_portal.getLiferayPortletResponse(actionResponse));

		MillerColumnsDisplayContext millerColumnsDisplayContext =
			new MillerColumnsDisplayContext(
				new LayoutActionDropdownItemsProvider(
					_portal.getHttpServletRequest(actionRequest),
					layoutActionsHelper, layoutsAdminDisplayContext,
					_translationURLProvider),
				_layoutSetPrototypeHelper, layoutsAdminDisplayContext,
				_portal.getLiferayPortletRequest(actionRequest),
				_portal.getLiferayPortletResponse(actionResponse));

		JSONPortletResponseUtil.writeJSON(
			actionRequest, actionResponse,
			JSONUtil.put(
				"children",
				() -> {
					long plid = ParamUtil.getLong(actionRequest, "plid");

					Layout layout = _layoutLocalService.fetchLayout(plid);

					return millerColumnsDisplayContext.getLayoutsJSONArray(
						layout.getLayoutId(), layout.isPrivateLayout());
				}));
	}

	@Reference
	private ItemSelector _itemSelector;

	@Reference
	private LayoutConverterRegistry _layoutConverterRegistry;

	@Reference
	private LayoutCopyHelper _layoutCopyHelper;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutSetPrototypeHelper _layoutSetPrototypeHelper;

	@Reference
	private Portal _portal;

	@Reference
	private StagingGroupHelper _stagingGroupHelper;

	@Reference
	private TranslationPermission _translationPermission;

	@Reference
	private TranslationURLProvider _translationURLProvider;

}