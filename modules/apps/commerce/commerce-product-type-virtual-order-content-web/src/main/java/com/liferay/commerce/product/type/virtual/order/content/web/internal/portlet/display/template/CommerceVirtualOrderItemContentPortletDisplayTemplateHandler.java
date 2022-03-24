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

package com.liferay.commerce.product.type.virtual.order.content.web.internal.portlet.display.template;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.product.type.virtual.order.constants.CommerceVirtualOrderPortletKeys;
import com.liferay.commerce.product.type.virtual.order.content.web.internal.display.context.CommerceVirtualOrderItemContentDisplayContext;
import com.liferay.commerce.product.type.virtual.order.content.web.internal.portlet.CommerceVirtualOrderItemContentPortlet;
import com.liferay.commerce.product.type.virtual.order.service.CommerceVirtualOrderItemLocalService;
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
	property = "javax.portlet.name=" + CommerceVirtualOrderPortletKeys.COMMERCE_VIRTUAL_ORDER_ITEM_CONTENT,
	service = TemplateHandler.class
)
public class CommerceVirtualOrderItemContentPortletDisplayTemplateHandler
	extends BasePortletDisplayTemplateHandler {

	@Override
	public String getClassName() {
		return CommerceVirtualOrderItemContentPortlet.class.getName();
	}

	@Override
	public String getName(Locale locale) {
		StringBundler sb = new StringBundler(3);

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		sb.append(
			_portal.getPortletTitle(
				CommerceVirtualOrderPortletKeys.
					COMMERCE_VIRTUAL_ORDER_ITEM_CONTENT,
				resourceBundle));

		sb.append(StringPool.SPACE);
		sb.append(LanguageUtil.get(locale, "template"));

		return sb.toString();
	}

	@Override
	public String getResourceName() {
		return CommerceVirtualOrderPortletKeys.
			COMMERCE_VIRTUAL_ORDER_ITEM_CONTENT;
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
			"commerce-virtual-order-item-content-display-context",
			CommerceVirtualOrderItemContentDisplayContext.class,
			"commerceVirtualOrderItemContentDisplayContext");
		templateVariableGroup.addCollectionVariable(
			"commerce-virtual-order-items", List.class,
			PortletDisplayTemplateConstants.ENTRIES, "commerceVitualOrderItem",
			CommerceOrder.class, "curCommerceVitualOrderItem",
			"commerceVirtualOrderItemId");

		TemplateVariableGroup
			commerceVirtualOrderItemServicesTemplateVariableGroup =
				new TemplateVariableGroup(
					"commerce-virtual-order-item-services",
					getRestrictedVariables(language));

		commerceVirtualOrderItemServicesTemplateVariableGroup.
			setAutocompleteEnabled(false);

		commerceVirtualOrderItemServicesTemplateVariableGroup.
			addServiceLocatorVariables(
				CommerceVirtualOrderItemLocalService.class);

		templateVariableGroups.put(
			commerceVirtualOrderItemServicesTemplateVariableGroup.getLabel(),
			commerceVirtualOrderItemServicesTemplateVariableGroup);

		return templateVariableGroups;
	}

	@Reference
	private Portal _portal;

}