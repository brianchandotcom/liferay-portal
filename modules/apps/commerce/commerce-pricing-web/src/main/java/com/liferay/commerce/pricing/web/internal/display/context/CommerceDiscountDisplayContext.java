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

package com.liferay.commerce.pricing.web.internal.display.context;

import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalService;
import com.liferay.commerce.discount.constants.CommerceDiscountActionKeys;
import com.liferay.commerce.discount.constants.CommerceDiscountConstants;
import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.model.CommerceDiscountRule;
import com.liferay.commerce.discount.rule.type.CommerceDiscountRuleType;
import com.liferay.commerce.discount.rule.type.CommerceDiscountRuleTypeRegistry;
import com.liferay.commerce.discount.service.CommerceDiscountRuleService;
import com.liferay.commerce.discount.service.CommerceDiscountService;
import com.liferay.commerce.discount.target.CommerceDiscountTarget;
import com.liferay.commerce.discount.target.CommerceDiscountTargetRegistry;
import com.liferay.commerce.frontend.ClayCreationMenu;
import com.liferay.commerce.frontend.ClayCreationMenuActionItem;
import com.liferay.commerce.frontend.ClayMenuActionItem;
import com.liferay.commerce.frontend.clay.data.set.ClayHeadlessDataSetActionTemplate;
import com.liferay.commerce.frontend.model.HeaderActionModel;
import com.liferay.commerce.percentage.PercentageFormatter;
import com.liferay.commerce.pricing.constants.CommercePricingPortletKeys;
import com.liferay.commerce.pricing.model.CommercePricingClass;
import com.liferay.commerce.pricing.web.servlet.taglib.ui.CommerceDiscountScreenNavigationConstants;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.PortalPermissionUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.util.CustomAttributesUtil;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.portlet.ActionRequest;
import javax.portlet.ActionURL;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;
import javax.portlet.RenderURL;
import javax.portlet.WindowStateException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceDiscountDisplayContext extends BasePricingDisplayContext {

	public CommerceDiscountDisplayContext(
		CommerceCurrencyLocalService commerceCurrencyLocalService,
		ModelResourcePermission<CommerceDiscount>
			commerceDiscountModelResourcePermission,
		CommerceDiscountService commerceDiscountService,
		CommerceDiscountRuleService commerceDiscountRuleService,
		CommerceDiscountRuleTypeRegistry commerceDiscountRuleTypeRegistry,
		CommerceDiscountTargetRegistry commerceDiscountTargetRegistry,
		PercentageFormatter percentageFormatter,
		HttpServletRequest httpServletRequest, Portal portal) {

		super(httpServletRequest);

		_commerceCurrencyLocalService = commerceCurrencyLocalService;
		_commerceDiscountModelResourcePermission =
			commerceDiscountModelResourcePermission;
		_commerceDiscountService = commerceDiscountService;
		_commerceDiscountRuleService = commerceDiscountRuleService;
		_commerceDiscountRuleTypeRegistry = commerceDiscountRuleTypeRegistry;
		_commerceDiscountTargetRegistry = commerceDiscountTargetRegistry;
		_percentageFormatter = percentageFormatter;
		_portal = portal;
	}

	public String getAddCommerceDiscountRenderURL() throws Exception {
		LiferayPortletResponse liferayPortletResponse =
			commercePricingRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter("mvcRenderCommandName", "addCommerceDiscount");
		portletURL.setWindowState(LiferayWindowState.POP_UP);

		return portletURL.toString();
	}

	public String getAddCommerceDiscountRuleRenderURL() throws Exception {
		LiferayPortletResponse liferayPortletResponse =
			commercePricingRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter(
			"mvcRenderCommandName", "addCommerceDiscountRule");
		portletURL.setParameter(
			"commerceDiscountId", String.valueOf(getCommerceDiscountId()));
		portletURL.setWindowState(LiferayWindowState.POP_UP);

		return portletURL.toString();
	}

	public ClayCreationMenu getClayCreationDiscountMenu() throws Exception {
		ClayCreationMenu clayCreationMenu = new ClayCreationMenu();

		if (hasAddPermission()) {
			clayCreationMenu.addClayCreationMenuActionItem(
				new ClayCreationMenuActionItem(
					getAddCommerceDiscountRenderURL(),
					LanguageUtil.get(
						commercePricingRequestHelper.getRequest(),
						"add-discount"),
					ClayMenuActionItem.CLAY_MENU_ACTION_ITEM_TARGET_MODAL));
		}

		return clayCreationMenu;
	}

	public ClayCreationMenu getClayCreationDiscountRuleMenu() throws Exception {
		ClayCreationMenu clayCreationMenu = new ClayCreationMenu();

		if (hasPermission(ActionKeys.UPDATE)) {
			clayCreationMenu.addClayCreationMenuActionItem(
				new ClayCreationMenuActionItem(
					getAddCommerceDiscountRuleRenderURL(),
					LanguageUtil.get(
						commercePricingRequestHelper.getRequest(),
						"add-discount-rule"),
					ClayMenuActionItem.CLAY_MENU_ACTION_ITEM_TARGET_MODAL));
		}

		return clayCreationMenu;
	}

	public List<ClayHeadlessDataSetActionTemplate>
			getClayHeadlessDataSetActionDiscountCategoryTemplates()
		throws PortalException {

		List<ClayHeadlessDataSetActionTemplate>
			clayHeadlessDataSetActionTemplates = new ArrayList<>();

		clayHeadlessDataSetActionTemplates.add(
			new ClayHeadlessDataSetActionTemplate(
				null, "trash", "delete",
				LanguageUtil.get(httpServletRequest, "delete"), "delete",
				"delete",
				ClayMenuActionItem.CLAY_MENU_ACTION_ITEM_TARGET_HEADLESS));

		return clayHeadlessDataSetActionTemplates;
	}

	public List<ClayHeadlessDataSetActionTemplate>
			getClayHeadlessDataSetActionDiscountCPDefinitionTemplates()
		throws PortalException {

		PortletURL portletURL = PortletProviderUtil.getPortletURL(
			httpServletRequest, CPDefinition.class.getName(),
			PortletProvider.Action.MANAGE);

		portletURL.setParameter(
			"mvcRenderCommandName", "editProductDefinition");
		portletURL.setParameter(
			"redirect", commercePricingRequestHelper.getCurrentURL());
		portletURL.setParameter("cpDefinitionId", "{product.id}");
		portletURL.setParameter("screenNavigationCategoryKey", "details");

		return getClayHeadlessDataSetActionTemplates(
			portletURL.toString(), false);
	}

	public List<ClayHeadlessDataSetActionTemplate>
			getClayHeadlessDataSetActionDiscountPricingClassTemplates()
		throws PortalException {

		PortletURL portletURL = PortletProviderUtil.getPortletURL(
			httpServletRequest, CommercePricingClass.class.getName(),
			PortletProvider.Action.MANAGE);

		portletURL.setParameter(
			"mvcRenderCommandName", "editCommercePricingClass");
		portletURL.setParameter(
			"redirect", commercePricingRequestHelper.getCurrentURL());
		portletURL.setParameter("commercePricingClassId", "{productGroupId}");
		portletURL.setParameter("screenNavigationCategoryKey", "details");

		return getClayHeadlessDataSetActionTemplates(
			portletURL.toString(), false);
	}

	public List<ClayHeadlessDataSetActionTemplate>
			getClayHeadlessDataSetActionDiscountRulesTemplates()
		throws PortalException {

		PortletURL portletURL = PortletProviderUtil.getPortletURL(
			httpServletRequest, CommerceDiscount.class.getName(),
			PortletProvider.Action.EDIT);

		portletURL.setParameter(
			"mvcRenderCommandName", "editCommerceDiscountRule");
		portletURL.setParameter(
			"redirect", commercePricingRequestHelper.getCurrentURL());
		portletURL.setParameter("commerceDiscountRuleId", "{id}");
		portletURL.setParameter(
			"screenNavigationCategoryKey",
			CommerceDiscountScreenNavigationConstants.CATEGORY_KEY_DETAILS);

		try {
			portletURL.setWindowState(LiferayWindowState.POP_UP);
		}
		catch (WindowStateException wse) {
			_log.error(wse, wse);
		}

		return getClayHeadlessDataSetActionTemplates(
			portletURL.toString(), true);
	}

	public List<ClayHeadlessDataSetActionTemplate>
			getClayHeadlessDataSetActionDiscountTemplates()
		throws PortalException {

		List<ClayHeadlessDataSetActionTemplate>
			clayHeadlessDataSetActionTemplates = new ArrayList<>();

		PortletURL portletURL = PortletProviderUtil.getPortletURL(
			httpServletRequest, CommerceDiscount.class.getName(),
			PortletProvider.Action.MANAGE);

		portletURL.setParameter("mvcRenderCommandName", "editCommerceDiscount");
		portletURL.setParameter(
			"redirect", commercePricingRequestHelper.getCurrentURL());
		portletURL.setParameter("commerceDiscountId", "{id}");
		portletURL.setParameter("usePercentage", "{usePercentage}");
		portletURL.setParameter(
			"screenNavigationCategoryKey",
			CommerceDiscountScreenNavigationConstants.CATEGORY_KEY_DETAILS);

		clayHeadlessDataSetActionTemplates.add(
			new ClayHeadlessDataSetActionTemplate(
				portletURL.toString(), "pencil", "edit",
				LanguageUtil.get(httpServletRequest, "edit"), "get", null,
				null));

		clayHeadlessDataSetActionTemplates.add(
			new ClayHeadlessDataSetActionTemplate(
				null, "trash", "delete",
				LanguageUtil.get(httpServletRequest, "delete"), "delete",
				"delete",
				ClayMenuActionItem.CLAY_MENU_ACTION_ITEM_TARGET_HEADLESS));

		clayHeadlessDataSetActionTemplates.add(
			new ClayHeadlessDataSetActionTemplate(
				_getManageDiscountPermissionsURL(), null, "permissions",
				LanguageUtil.get(httpServletRequest, "permissions"), "get",
				"permissions",
				ClayMenuActionItem.
					CLAY_MENU_ACTION_ITEM_TARGET_MODAL_PERMISSIONS));

		return clayHeadlessDataSetActionTemplates;
	}

	public CommerceDiscount getCommerceDiscount() throws PortalException {
		if (_commerceDiscount != null) {
			return _commerceDiscount;
		}

		long commerceDiscountId = ParamUtil.getLong(
			commercePricingRequestHelper.getRequest(), "commerceDiscountId");

		if (commerceDiscountId > 0) {
			_commerceDiscount = _commerceDiscountService.getCommerceDiscount(
				commerceDiscountId);
		}

		return _commerceDiscount;
	}

	public String getCommerceDiscountAmount(Locale locale)
		throws PortalException {

		CommerceDiscount commerceDiscount = getCommerceDiscount();

		if (commerceDiscount == null) {
			return null;
		}

		if (Objects.equals(
				commerceDiscount.getLevel(),
				CommerceDiscountConstants.LEVEL_L1)) {

			return _getCommerceDiscountAmountFormatted(
				commerceDiscount.isUsePercentage(),
				commerceDiscount.getLevel1(), locale);
		}

		if (Objects.equals(
				commerceDiscount.getLevel(),
				CommerceDiscountConstants.LEVEL_L2)) {

			return _getCommerceDiscountAmountFormatted(
				commerceDiscount.isUsePercentage(),
				commerceDiscount.getLevel2(), locale);
		}

		if (Objects.equals(
				commerceDiscount.getLevel(),
				CommerceDiscountConstants.LEVEL_L3)) {

			return _getCommerceDiscountAmountFormatted(
				commerceDiscount.isUsePercentage(),
				commerceDiscount.getLevel3(), locale);
		}

		if (Objects.equals(
				commerceDiscount.getLevel(),
				CommerceDiscountConstants.LEVEL_L4)) {

			return _getCommerceDiscountAmountFormatted(
				commerceDiscount.isUsePercentage(),
				commerceDiscount.getLevel4(), locale);
		}

		return _getCommerceDiscountAmountFormatted(
			commerceDiscount.isUsePercentage(), BigDecimal.ZERO, locale);
	}

	public long getCommerceDiscountId() throws PortalException {
		CommerceDiscount commerceDiscount = getCommerceDiscount();

		if (commerceDiscount == null) {
			return 0;
		}

		return commerceDiscount.getCommerceDiscountId();
	}

	public CommerceDiscountRule getCommerceDiscountRule()
		throws PortalException {

		if (_commerceDiscountRule != null) {
			return _commerceDiscountRule;
		}

		long commerceDiscountRuleId = ParamUtil.getLong(
			commercePricingRequestHelper.getRequest(),
			"commerceDiscountRuleId");

		if (commerceDiscountRuleId > 0) {
			_commerceDiscountRule =
				_commerceDiscountRuleService.getCommerceDiscountRule(
					commerceDiscountRuleId);
		}

		return _commerceDiscountRule;
	}

	public List<CommerceDiscountRuleType> getCommerceDiscountRuleTypes() {
		return _commerceDiscountRuleTypeRegistry.getCommerceDiscountRuleTypes();
	}

	public List<CommerceDiscountTarget> getCommerceDiscountTargets() {
		return _commerceDiscountTargetRegistry.getCommerceDiscountTargets();
	}

	public String getDefaultCommerceCurrencyCode() {
		CommerceCurrency commerceCurrency =
			_commerceCurrencyLocalService.fetchPrimaryCommerceCurrency(
				commercePricingRequestHelper.getCompanyId());

		if (commerceCurrency == null) {
			return StringPool.BLANK;
		}

		return commerceCurrency.getCode();
	}

	public String getDiscountCategoriesApiUrl() throws PortalException {
		return "/o/headless-commerce-admin-pricing/v2.0/discounts/" +
			getCommerceDiscountId() +
				"/discount-categories?nestedFields=category";
	}

	public String getDiscountCPDefinitionApiUrl() throws PortalException {
		return "/o/headless-commerce-admin-pricing/v2.0/discounts/" +
			getCommerceDiscountId() + "/discount-products?nestedFields=product";
	}

	public String getDiscountPricingClassesApiUrl() throws PortalException {
		return "/o/headless-commerce-admin-pricing/v2.0/discounts/" +
			getCommerceDiscountId() +
				"/discount-product-groups?nestedFields=productGroup";
	}

	public String getDiscountRulesApiUrl() throws PortalException {
		return "/o/headless-commerce-admin-pricing/v2.0/discounts/" +
			getCommerceDiscountId() + "/discount-rules";
	}

	public String getEditCommerceDiscountActionURL() throws Exception {
		CommerceDiscount commerceDiscount = getCommerceDiscount();

		if (commerceDiscount == null) {
			return StringPool.BLANK;
		}

		PortletURL portletURL = _portal.getControlPanelPortletURL(
			commercePricingRequestHelper.getRequest(),
			CommercePricingPortletKeys.COMMERCE_DISCOUNT,
			PortletRequest.ACTION_PHASE);

		portletURL.setParameter(
			ActionRequest.ACTION_NAME, "editCommerceDiscount");
		portletURL.setParameter(Constants.CMD, Constants.UPDATE);
		portletURL.setParameter(
			"commerceDiscountId",
			String.valueOf(commerceDiscount.getCommerceDiscountId()));
		portletURL.setWindowState(LiferayWindowState.POP_UP);

		return portletURL.toString();
	}

	public PortletURL getEditCommerceDiscountRenderURL() {
		PortletURL portletURL = _portal.getControlPanelPortletURL(
			commercePricingRequestHelper.getRequest(),
			CommercePricingPortletKeys.COMMERCE_DISCOUNT,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter("mvcRenderCommandName", "editCommerceDiscount");

		return portletURL;
	}

	public List<HeaderActionModel> getHeaderActionModels() throws Exception {
		List<HeaderActionModel> headerActionModels = new ArrayList<>();

		RenderResponse renderResponse =
			commercePricingRequestHelper.getRenderResponse();

		RenderURL cancelURL = renderResponse.createRenderURL();

		HeaderActionModel cancelHeaderActionModel = new HeaderActionModel(
			null, cancelURL.toString(), null, "cancel");

		headerActionModels.add(cancelHeaderActionModel);

		String saveButtonLabel = "save";

		CommerceDiscount commerceDiscount = getCommerceDiscount();

		if ((commerceDiscount == null) || commerceDiscount.isDraft() ||
			commerceDiscount.isApproved() || commerceDiscount.isExpired() ||
			commerceDiscount.isScheduled()) {

			saveButtonLabel = "save-as-draft";
		}

		ActionURL actionURL = renderResponse.createActionURL();

		actionURL.setParameter(
			ActionRequest.ACTION_NAME, "editCommerceDiscount");

		HeaderActionModel saveAsDraftHeaderActionModel = new HeaderActionModel(
			null, renderResponse.getNamespace() + "fm", actionURL.toString(),
			null, saveButtonLabel);

		headerActionModels.add(saveAsDraftHeaderActionModel);

		String publishButtonLabel = "publish";

		if (WorkflowDefinitionLinkLocalServiceUtil.hasWorkflowDefinitionLink(
				commercePricingRequestHelper.getCompanyId(),
				commercePricingRequestHelper.getScopeGroupId(),
				CommerceDiscount.class.getName())) {

			publishButtonLabel = "submit-for-publication";
		}

		String additionalClasses = "btn-primary";

		if ((commerceDiscount != null) && commerceDiscount.isPending()) {
			additionalClasses = additionalClasses + " disabled";
		}

		HeaderActionModel publishHeaderActionModel = new HeaderActionModel(
			additionalClasses, renderResponse.getNamespace() + "fm",
			actionURL.toString(),
			renderResponse.getNamespace() + "publishButton",
			publishButtonLabel);

		headerActionModels.add(publishHeaderActionModel);

		return headerActionModels;
	}

	public String getLocalizedPercentage(BigDecimal percentage, Locale locale)
		throws PortalException {

		CommerceCurrency commerceCurrency =
			_commerceCurrencyLocalService.fetchPrimaryCommerceCurrency(
				commercePricingRequestHelper.getCompanyId());

		String localizedPercentage =
			_percentageFormatter.getLocalizedPercentage(
				locale, commerceCurrency.getMaxFractionDigits(),
				commerceCurrency.getMinFractionDigits(), percentage);

		return localizedPercentage.replace(
			StringPool.PERCENT, StringPool.BLANK);
	}

	public PortletURL getPortletDiscountRuleURL() {
		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		String redirect = ParamUtil.getString(httpServletRequest, "redirect");

		if (Validator.isNotNull(redirect)) {
			portletURL.setParameter("redirect", redirect);
		}

		long commerceDiscountRuleId = ParamUtil.getLong(
			httpServletRequest, "commerceDiscountRuleId");

		if (commerceDiscountRuleId > 0) {
			portletURL.setParameter(
				"commerceDiscountRuleId",
				String.valueOf(commerceDiscountRuleId));
		}

		return portletURL;
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		String redirect = ParamUtil.getString(httpServletRequest, "redirect");

		if (Validator.isNotNull(redirect)) {
			portletURL.setParameter("redirect", redirect);
		}

		long commerceDiscountId = ParamUtil.getLong(
			httpServletRequest, "commerceDiscountId");

		if (commerceDiscountId > 0) {
			portletURL.setParameter(
				"commerceDiscountId", String.valueOf(commerceDiscountId));
		}

		return portletURL;
	}

	public boolean getUsePercentage(String commerceDiscountType) {
		if (Objects.equals(
				commerceDiscountType,
				CommerceDiscountConstants.TYPE_PERCENTAGE)) {

			return true;
		}

		return false;
	}

	public boolean hasAddPermission() throws PortalException {
		return PortalPermissionUtil.contains(
			commercePricingRequestHelper.getPermissionChecker(),
			CommerceDiscountActionKeys.ADD_COMMERCE_DISCOUNT);
	}

	public boolean hasCustomAttributesAvailable() throws Exception {
		return CustomAttributesUtil.hasCustomAttributes(
			commercePricingRequestHelper.getCompanyId(),
			CommerceDiscount.class.getName(), getCommerceDiscountId(), null);
	}

	public boolean hasPermission(String actionId) throws PortalException {
		return _commerceDiscountModelResourcePermission.contains(
			commercePricingRequestHelper.getPermissionChecker(),
			getCommerceDiscountId(), actionId);
	}

	public BigDecimal round(BigDecimal value) {
		if (value == null) {
			value = BigDecimal.ZERO;
		}

		CommerceCurrency commerceCurrency =
			_commerceCurrencyLocalService.fetchPrimaryCommerceCurrency(
				commercePricingRequestHelper.getCompanyId());

		if (commerceCurrency == null) {
			return value;
		}

		return commerceCurrency.round(value);
	}

	protected List<ClayHeadlessDataSetActionTemplate>
		getClayHeadlessDataSetActionTemplates(
			String portletURL, boolean isSidePanel) {

		List<ClayHeadlessDataSetActionTemplate>
			clayHeadlessDataSetActionTemplates = new ArrayList<>();

		ClayHeadlessDataSetActionTemplate clayHeadlessDataSetActionTemplate =
			new ClayHeadlessDataSetActionTemplate(
				portletURL, "pencil", "edit",
				LanguageUtil.get(httpServletRequest, "edit"), "get", null,
				null);

		if (isSidePanel) {
			clayHeadlessDataSetActionTemplate.setTarget("sidePanel");
		}

		clayHeadlessDataSetActionTemplates.add(
			clayHeadlessDataSetActionTemplate);

		clayHeadlessDataSetActionTemplates.add(
			new ClayHeadlessDataSetActionTemplate(
				null, "trash", "remove",
				LanguageUtil.get(httpServletRequest, "remove"), "delete",
				"delete",
				ClayMenuActionItem.CLAY_MENU_ACTION_ITEM_TARGET_HEADLESS));

		return clayHeadlessDataSetActionTemplates;
	}

	private String _getCommerceDiscountAmountFormatted(
			boolean isUsePercentage, BigDecimal commerceDiscountAmount,
			Locale locale)
		throws PortalException {

		if (commerceDiscountAmount == null) {
			commerceDiscountAmount = BigDecimal.ZERO;
		}

		if (isUsePercentage) {
			return getLocalizedPercentage(commerceDiscountAmount, locale);
		}

		return String.valueOf(round(commerceDiscountAmount));
	}

	private String _getManageDiscountPermissionsURL() throws PortalException {
		PortletURL portletURL = _portal.getControlPanelPortletURL(
			httpServletRequest,
			"com_liferay_portlet_configuration_web_portlet_" +
				"PortletConfigurationPortlet",
			ActionRequest.RENDER_PHASE);

		portletURL.setParameter("mvcPath", "/edit_permissions.jsp");
		portletURL.setParameter(
			"redirect", commercePricingRequestHelper.getCurrentURL());
		portletURL.setParameter(
			"modelResource", CommerceDiscount.class.getName());
		portletURL.setParameter("modelResourceDescription", "{name}");
		portletURL.setParameter("resourcePrimKey", "{id}");

		try {
			portletURL.setWindowState(LiferayWindowState.POP_UP);
		}
		catch (WindowStateException wse) {
			throw new PortalException(wse);
		}

		return portletURL.toString();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceDiscountDisplayContext.class);

	private final CommerceCurrencyLocalService _commerceCurrencyLocalService;
	private CommerceDiscount _commerceDiscount;
	private final ModelResourcePermission<CommerceDiscount>
		_commerceDiscountModelResourcePermission;
	private CommerceDiscountRule _commerceDiscountRule;
	private final CommerceDiscountRuleService _commerceDiscountRuleService;
	private final CommerceDiscountRuleTypeRegistry
		_commerceDiscountRuleTypeRegistry;
	private final CommerceDiscountService _commerceDiscountService;
	private final CommerceDiscountTargetRegistry
		_commerceDiscountTargetRegistry;
	private final PercentageFormatter _percentageFormatter;
	private final Portal _portal;

}