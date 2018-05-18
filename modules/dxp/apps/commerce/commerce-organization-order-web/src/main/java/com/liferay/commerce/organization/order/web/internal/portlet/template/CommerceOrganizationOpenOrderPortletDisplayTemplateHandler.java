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

package com.liferay.commerce.organization.order.web.internal.portlet.template;

import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.organization.order.web.internal.display.context.CommerceOrganizationOrderDisplayContext;
import com.liferay.commerce.organization.order.web.internal.portlet.CommerceOrganizationOpenOrderPortlet;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.service.CommerceOrderService;
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
	property = "javax.portlet.name=" + CommercePortletKeys.COMMERCE_ORGANIZATION_OPEN_ORDER,
	service = TemplateHandler.class
)
public class CommerceOrganizationOpenOrderPortletDisplayTemplateHandler
	extends BasePortletDisplayTemplateHandler {

	@Override
	public String getClassName() {
		return CommerceOrganizationOpenOrderPortlet.class.getName();
	}

	@Override
	public String getName(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		String portletTitle = _portal.getPortletTitle(
			CommercePortletKeys.COMMERCE_ORGANIZATION_OPEN_ORDER,
			resourceBundle);

		return portletTitle.concat(StringPool.SPACE).concat(
			LanguageUtil.get(locale, "template"));
	}

	@Override
	public String getResourceName() {
		return CommercePortletKeys.COMMERCE_ORGANIZATION_OPEN_ORDER;
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
			"commerce-organization-order-display-context",
			CommerceOrganizationOrderDisplayContext.class,
			"commerceOrganizationOrderDisplayContext");
		templateVariableGroup.addCollectionVariable(
			"commerce-orders", List.class,
			PortletDisplayTemplateConstants.ENTRIES, "commerceOrder",
			CommerceOrder.class, "curCommerceOrder", "commerceOrderId");

		String[] restrictedVariables = getRestrictedVariables(language);

		TemplateVariableGroup commerceOrderServicesTemplateVariableGroup =
			new TemplateVariableGroup(
				"commerce-order-services", restrictedVariables);

		commerceOrderServicesTemplateVariableGroup.setAutocompleteEnabled(
			false);

		commerceOrderServicesTemplateVariableGroup.addServiceLocatorVariables(
			CommerceOrderLocalService.class, CommerceOrderService.class);

		templateVariableGroups.put(
			commerceOrderServicesTemplateVariableGroup.getLabel(),
			commerceOrderServicesTemplateVariableGroup);

		return templateVariableGroups;
	}

	@Reference
	private Portal _portal;

}