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

package com.liferay.commerce.cart.content.web.internal.portlet.template;

import com.liferay.commerce.cart.constants.CommerceCartPortletKeys;
import com.liferay.commerce.cart.content.web.internal.display.context.CommerceCartContentDisplayContext;
import com.liferay.commerce.cart.content.web.internal.portlet.CommerceCartContentMiniPortlet;
import com.liferay.commerce.cart.model.CommerceCartItem;
import com.liferay.commerce.cart.service.CommerceCartItemLocalService;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portletdisplaytemplate.BasePortletDisplayTemplateHandler;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.template.TemplateVariableGroup;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portlet.display.template.PortletDisplayTemplateConstants;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + CommerceCartPortletKeys.COMMERCE_CART_CONTENT_MINI,
	service = TemplateHandler.class
)
public class CommerceCartContentMiniPortletDisplayTemplateHandler
	extends BasePortletDisplayTemplateHandler {

	@Override
	public String getClassName() {
		return CommerceCartContentMiniPortlet.class.getName();
	}

	@Override
	public String getName(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		String portletTitle = _portal.getPortletTitle(
			CommerceCartPortletKeys.COMMERCE_CART_CONTENT_MINI, resourceBundle);

		return portletTitle.concat(StringPool.SPACE).concat(
			LanguageUtil.get(locale, "template"));
	}

	@Override
	public String getResourceName() {
		return CommerceCartPortletKeys.COMMERCE_CART_CONTENT_MINI;
	}

	@Override
	public Map<String, TemplateVariableGroup> getTemplateVariableGroups(
			long classPK, String language, Locale locale)
		throws Exception {

		Map<String, TemplateVariableGroup> templateVariableGroups =
			super.getTemplateVariableGroups(classPK, language, locale);

		TemplateVariableGroup templateVariableGroup =
			templateVariableGroups.get("fields");

		templateVariableGroup.empty();

		templateVariableGroup.addVariable(
			"commerce-cart-content-display-context",
			CommerceCartContentDisplayContext.class,
			"commerceCartContentDisplayContext");
		templateVariableGroup.addCollectionVariable(
			"commerce-cart-items", List.class,
			PortletDisplayTemplateConstants.ENTRIES, "commerce-cart-item",
			CommerceCartItem.class, "curCommerceCartItem",
			"CommerceCartItemId");

		String[] restrictedVariables = getRestrictedVariables(language);

		TemplateVariableGroup cpDefinitionsServicesTemplateVariableGroup =
			new TemplateVariableGroup(
				"commerce-cart-item-services", restrictedVariables);

		cpDefinitionsServicesTemplateVariableGroup.setAutocompleteEnabled(
			false);

		cpDefinitionsServicesTemplateVariableGroup.addServiceLocatorVariables(
			CommerceCartItemLocalService.class);

		templateVariableGroups.put(
			cpDefinitionsServicesTemplateVariableGroup.getLabel(),
			cpDefinitionsServicesTemplateVariableGroup);

		return templateVariableGroups;
	}

	@Override
	protected String getTemplatesConfigPath() {
		return "com/liferay/commerce/cart/content/web/internal/portlet" +
			"/template/dependencies/portlet-display-templates.xml";
	}

	@Reference
	private Portal _portal;

}