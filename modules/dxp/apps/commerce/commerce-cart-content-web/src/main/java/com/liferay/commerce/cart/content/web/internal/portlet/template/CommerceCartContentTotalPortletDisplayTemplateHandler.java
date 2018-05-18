/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.cart.content.web.internal.portlet.template;

import com.liferay.commerce.cart.content.web.internal.display.context.CommerceCartContentTotalDisplayContext;
import com.liferay.commerce.cart.content.web.internal.portlet.CommerceCartContentTotalPortlet;
import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.service.CommerceOrderItemLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portletdisplaytemplate.BasePortletDisplayTemplateHandler;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.template.TemplateVariableGroup;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
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
	property = "javax.portlet.name=" + CommercePortletKeys.COMMERCE_CART_CONTENT_TOTAL,
	service = TemplateHandler.class
)
public class CommerceCartContentTotalPortletDisplayTemplateHandler
	extends BasePortletDisplayTemplateHandler {

	@Override
	public String getClassName() {
		return CommerceCartContentTotalPortlet.class.getName();
	}

	@Override
	public String getName(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		String portletTitle = _portal.getPortletTitle(
			CommercePortletKeys.COMMERCE_CART_CONTENT_TOTAL, resourceBundle);

		return portletTitle.concat(StringPool.SPACE).concat(
			LanguageUtil.get(locale, "template"));
	}

	@Override
	public String getResourceName() {
		return CommercePortletKeys.COMMERCE_CART_CONTENT_TOTAL;
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
			"commerce-cart-content-total-display-context",
			CommerceCartContentTotalDisplayContext.class,
			"commerceCartContentTotalDisplayContext");
		templateVariableGroup.addCollectionVariable(
			"commerce-order-items", List.class,
			PortletDisplayTemplateConstants.ENTRIES, "commerce-order-item",
			CommerceOrderItem.class, "curCommerceOrderItem",
			"CommerceOrderItemId");

		String[] restrictedVariables = getRestrictedVariables(language);

		TemplateVariableGroup commerceOrderItemsServicesTemplateVariableGroup =
			new TemplateVariableGroup(
				"commerce-order-item-services", restrictedVariables);

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
			"/template/dependencies/cart_total/portlet-display-templates.xml";
	}

	@Reference
	private Portal _portal;

}