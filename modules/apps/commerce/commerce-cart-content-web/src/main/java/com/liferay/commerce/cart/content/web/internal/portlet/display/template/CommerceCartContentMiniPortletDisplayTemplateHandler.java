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

package com.liferay.commerce.cart.content.web.internal.portlet.display.template;

import com.liferay.commerce.cart.content.web.internal.display.context.CommerceCartContentMiniDisplayContext;
import com.liferay.commerce.cart.content.web.internal.portlet.CommerceCartContentMiniPortlet;
import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.service.CommerceOrderItemLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portletdisplaytemplate.BasePortletDisplayTemplateHandler;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.template.TemplateVariableGroup;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portlet.display.template.constants.PortletDisplayTemplateConstants;

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
	enabled = false, immediate = true,
	property = "javax.portlet.name=" + CommercePortletKeys.COMMERCE_CART_CONTENT_MINI,
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
		StringBundler sb = new StringBundler(3);

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		sb.append(
			_portal.getPortletTitle(
				CommercePortletKeys.COMMERCE_CART_CONTENT_MINI,
				resourceBundle));

		sb.append(StringPool.SPACE);
		sb.append(LanguageUtil.get(locale, "template"));

		return sb.toString();
	}

	@Override
	public String getResourceName() {
		return CommercePortletKeys.COMMERCE_CART_CONTENT_MINI;
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
			"commerce-cart-content-mini-display-context",
			CommerceCartContentMiniDisplayContext.class,
			"commerceCartContentMiniDisplayContext");
		templateVariableGroup.addCollectionVariable(
			"commerce-order-items", List.class,
			PortletDisplayTemplateConstants.ENTRIES, "commerce-order-item",
			CommerceOrderItem.class, "curCommerceOrderItem",
			"CommerceOrderItemId");

		TemplateVariableGroup commerceOrderItemsServicesTemplateVariableGroup =
			new TemplateVariableGroup(
				"commerce-order-item-services",
				getRestrictedVariables(language));

		commerceOrderItemsServicesTemplateVariableGroup.setAutocompleteEnabled(
			false);

		commerceOrderItemsServicesTemplateVariableGroup.
			addServiceLocatorVariables(CommerceOrderItemLocalService.class);

		templateVariableGroups.put(
			commerceOrderItemsServicesTemplateVariableGroup.getLabel(),
			commerceOrderItemsServicesTemplateVariableGroup);

		return templateVariableGroups;
	}

	@Override
	protected String getTemplatesConfigPath() {
		return "com/liferay/commerce/cart/content/web/internal/portlet" +
			"/display/template/dependencies/cart_mini/portlet-display-" +
				"templates.xml";
	}

	@Reference
	private Portal _portal;

}