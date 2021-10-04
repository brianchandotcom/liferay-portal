/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.commerce.order.rule.web.internal.display.context;

import com.liferay.commerce.frontend.model.HeaderActionModel;
import com.liferay.commerce.order.rule.constants.CommerceOrderRuleEntryActionKeys;
import com.liferay.commerce.order.rule.constants.CommerceOrderRuleEntryPortletKeys;
import com.liferay.commerce.order.rule.model.CommerceOrderRuleEntry;
import com.liferay.commerce.order.rule.service.CommerceOrderRuleEntryService;
import com.liferay.commerce.order.rule.web.internal.display.context.util.CommerceOrderRuleEntryRequestHelper;
import com.liferay.frontend.taglib.clay.data.set.servlet.taglib.util.ClayDataSetActionDropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalServiceUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceOrderRuleEntryDisplayContext {

	public CommerceOrderRuleEntryDisplayContext(
		HttpServletRequest httpServletRequest,
		ModelResourcePermission<CommerceOrderRuleEntry>
			commerceOrderRuleEntryModelResourcePermission,
		CommerceOrderRuleEntryService commerceOrderRuleEntryService,
		Portal portal) {

		this.httpServletRequest = httpServletRequest;
		_commerceOrderRuleEntryModelResourcePermission =
			commerceOrderRuleEntryModelResourcePermission;
		_commerceOrderRuleEntryService = commerceOrderRuleEntryService;
		_portal = portal;

		commerceOrderRuleEntryRequestHelper =
			new CommerceOrderRuleEntryRequestHelper(httpServletRequest);
	}

	public String getAddCommerceOrderRuleEntryRenderURL() throws Exception {
		return PortletURLBuilder.createRenderURL(
			commerceOrderRuleEntryRequestHelper.getLiferayPortletResponse()
		).setMVCRenderCommandName(
			"/commerce_order_rule_entry/add_commerce_order_rule_entry"
		).setWindowState(
			LiferayWindowState.POP_UP
		).buildString();
	}

	public CommerceOrderRuleEntry getCommerceOrderRuleEntry()
		throws PortalException {

		long commerceOrderRuleEntryId = ParamUtil.getLong(
			commerceOrderRuleEntryRequestHelper.getRequest(),
			"commerceOrderRuleEntryId");

		if (commerceOrderRuleEntryId == 0) {
			return null;
		}

		return _commerceOrderRuleEntryService.fetchCommerceOrderRuleEntry(
			commerceOrderRuleEntryId);
	}

	public List<ClayDataSetActionDropdownItem>
			getCommerceOrderRuleEntryClayDataSetActionDropdownItems()
		throws PortalException {

		return ListUtil.fromArray(
			new ClayDataSetActionDropdownItem(
				PortletURLBuilder.create(
					PortletProviderUtil.getPortletURL(
						httpServletRequest,
						CommerceOrderRuleEntry.class.getName(),
						PortletProvider.Action.MANAGE)
				).setMVCRenderCommandName(
					"/commerce_order_rule_entry/edit_commerce_order_rule_entry"
				).setRedirect(
					commerceOrderRuleEntryRequestHelper.getCurrentURL()
				).setParameter(
					"commerceOrderRuleEntryId", "{id}"
				).buildString(),
				"pencil", "edit", LanguageUtil.get(httpServletRequest, "edit"),
				"get", null, null),
			new ClayDataSetActionDropdownItem(
				null, "trash", "delete",
				LanguageUtil.get(httpServletRequest, "delete"), "delete",
				"delete", "headless"),
			new ClayDataSetActionDropdownItem(
				_getManagePermissionsURL(), null, "permissions",
				LanguageUtil.get(httpServletRequest, "permissions"), "get",
				"permissions", "modal-permissions"));
	}

	public long getCommerceOrderRuleEntryId() throws PortalException {
		CommerceOrderRuleEntry commerceOrderRuleEntry =
			getCommerceOrderRuleEntry();

		if (commerceOrderRuleEntry == null) {
			return 0;
		}

		return commerceOrderRuleEntry.getCommerceOrderRuleEntryId();
	}

	public CreationMenu getCreationMenu() throws Exception {
		CreationMenu creationMenu = new CreationMenu();

		if (hasAddPermission()) {
			creationMenu.addDropdownItem(
				dropdownItem -> {
					dropdownItem.setHref(
						getAddCommerceOrderRuleEntryRenderURL());
					dropdownItem.setLabel(
						LanguageUtil.get(
							commerceOrderRuleEntryRequestHelper.getRequest(),
							"add-order-rule"));
					dropdownItem.setTarget("modal");
				});
		}

		return creationMenu;
	}

	public String getEditCommerceOrderRuleEntryActionURL() throws Exception {
		CommerceOrderRuleEntry commerceOrderRuleEntry =
			getCommerceOrderRuleEntry();

		if (commerceOrderRuleEntry == null) {
			return StringPool.BLANK;
		}

		return PortletURLBuilder.create(
			_portal.getControlPanelPortletURL(
				commerceOrderRuleEntryRequestHelper.getRequest(),
				CommerceOrderRuleEntryPortletKeys.COMMERCE_ORDER_RULE_ENTRY,
				PortletRequest.ACTION_PHASE)
		).setActionName(
			"/commerce_order_rule_entry/edit_commerce_order_rule_entry"
		).setCMD(
			Constants.UPDATE
		).setParameter(
			"commerceOrderRuleEntryId",
			commerceOrderRuleEntry.getCommerceOrderRuleEntryId()
		).setWindowState(
			LiferayWindowState.POP_UP
		).buildString();
	}

	public PortletURL getEditCommerceOrderRuleEntryRenderURL() {
		return PortletURLBuilder.create(
			_portal.getControlPanelPortletURL(
				commerceOrderRuleEntryRequestHelper.getRequest(),
				CommerceOrderRuleEntryPortletKeys.COMMERCE_ORDER_RULE_ENTRY,
				PortletRequest.RENDER_PHASE)
		).setMVCRenderCommandName(
			"/commerce_order_rule_entry/edit_commerce_order_rule_entry"
		).buildPortletURL();
	}

	public List<HeaderActionModel> getHeaderActionModels() throws Exception {
		List<HeaderActionModel> headerActionModels = new ArrayList<>();

		LiferayPortletResponse liferayPortletResponse =
			commerceOrderRuleEntryRequestHelper.getLiferayPortletResponse();

		String saveButtonLabel = "save";

		CommerceOrderRuleEntry commerceOrderRuleEntry =
			getCommerceOrderRuleEntry();

		if ((commerceOrderRuleEntry == null) ||
			commerceOrderRuleEntry.isDraft() ||
			commerceOrderRuleEntry.isApproved() ||
			commerceOrderRuleEntry.isExpired() ||
			commerceOrderRuleEntry.isScheduled()) {

			saveButtonLabel = "save-as-draft";
		}

		HeaderActionModel saveAsDraftHeaderActionModel = new HeaderActionModel(
			null, liferayPortletResponse.getNamespace() + "fm",
			PortletURLBuilder.createActionURL(
				liferayPortletResponse
			).setActionName(
				"/commerce_order_rule_entry/edit_commerce_order_rule_entry"
			).buildString(),
			null, saveButtonLabel);

		headerActionModels.add(saveAsDraftHeaderActionModel);

		String publishButtonLabel = "publish";

		if (WorkflowDefinitionLinkLocalServiceUtil.hasWorkflowDefinitionLink(
				commerceOrderRuleEntryRequestHelper.getCompanyId(),
				commerceOrderRuleEntryRequestHelper.getScopeGroupId(),
				CommerceOrderRuleEntry.class.getName())) {

			publishButtonLabel = "submit-for-publication";
		}

		String additionalClasses = "btn-primary";

		if ((commerceOrderRuleEntry != null) &&
			commerceOrderRuleEntry.isPending()) {

			additionalClasses = additionalClasses + " disabled";
		}

		HeaderActionModel publishHeaderActionModel = new HeaderActionModel(
			additionalClasses, liferayPortletResponse.getNamespace() + "fm",
			PortletURLBuilder.createActionURL(
				liferayPortletResponse
			).setActionName(
				"/commerce_order_rule_entry/edit_commerce_order_rule_entry"
			).buildString(),
			liferayPortletResponse.getNamespace() + "publishButton",
			publishButtonLabel);

		headerActionModels.add(publishHeaderActionModel);

		return headerActionModels;
	}

	public PortletURL getPortletURL() {
		LiferayPortletResponse liferayPortletResponse =
			commerceOrderRuleEntryRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		String redirect = ParamUtil.getString(httpServletRequest, "redirect");

		if (Validator.isNotNull(redirect)) {
			portletURL.setParameter("redirect", redirect);
		}

		long commerceOrderRuleEntryId = ParamUtil.getLong(
			httpServletRequest, "commerceOrderRuleEntryId");

		if (commerceOrderRuleEntryId > 0) {
			portletURL.setParameter(
				"commerceOrderRuleEntryId",
				String.valueOf(commerceOrderRuleEntryId));
		}

		return portletURL;
	}

	public boolean hasAddPermission() throws PortalException {
		PortletResourcePermission portletResourcePermission =
			_commerceOrderRuleEntryModelResourcePermission.
				getPortletResourcePermission();

		return portletResourcePermission.contains(
			commerceOrderRuleEntryRequestHelper.getPermissionChecker(), null,
			CommerceOrderRuleEntryActionKeys.ADD_COMMERCE_ORDER_RULE);
	}

	public boolean hasPermission(String actionId) throws PortalException {
		return _commerceOrderRuleEntryModelResourcePermission.contains(
			commerceOrderRuleEntryRequestHelper.getPermissionChecker(),
			getCommerceOrderRuleEntryId(), actionId);
	}

	protected final CommerceOrderRuleEntryRequestHelper
		commerceOrderRuleEntryRequestHelper;
	protected final HttpServletRequest httpServletRequest;

	private String _getManagePermissionsURL() throws PortalException {
		return PortletURLBuilder.create(
			_portal.getControlPanelPortletURL(
				httpServletRequest,
				"com_liferay_portlet_configuration_web_portlet_" +
					"PortletConfigurationPortlet",
				ActionRequest.RENDER_PHASE)
		).setMVCPath(
			"/edit_permissions.jsp"
		).setRedirect(
			commerceOrderRuleEntryRequestHelper.getCurrentURL()
		).setParameter(
			"modelResource", CommerceOrderRuleEntry.class.getName()
		).setParameter(
			"modelResourceDescription", "{name}"
		).setParameter(
			"resourcePrimKey", "{id}"
		).setWindowState(
			LiferayWindowState.POP_UP
		).buildString();
	}

	private final ModelResourcePermission<CommerceOrderRuleEntry>
		_commerceOrderRuleEntryModelResourcePermission;
	private final CommerceOrderRuleEntryService _commerceOrderRuleEntryService;
	private final Portal _portal;

}