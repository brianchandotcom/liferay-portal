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

package com.liferay.commerce.address.content.web.internal.portlet.template;

import com.liferay.commerce.address.content.web.internal.display.context.CommerceAddressDisplayContext;
import com.liferay.commerce.address.content.web.internal.portlet.CommerceAddressContentPortlet;
import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.service.CommerceAddressLocalService;
import com.liferay.commerce.service.CommerceAddressService;
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
	property = "javax.portlet.name=" + CommercePortletKeys.COMMERCE_ADDRESS_CONTENT,
	service = TemplateHandler.class
)
public class CommerceAddressContentPortletDisplayTemplateHandler
	extends BasePortletDisplayTemplateHandler {

	@Override
	public String getClassName() {
		return CommerceAddressContentPortlet.class.getName();
	}

	@Override
	public String getName(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		String portletTitle = _portal.getPortletTitle(
			CommercePortletKeys.COMMERCE_ADDRESS_CONTENT, resourceBundle);

		return portletTitle.concat(StringPool.SPACE).concat(
			LanguageUtil.get(locale, "template"));
	}

	@Override
	public String getResourceName() {
		return CommercePortletKeys.COMMERCE_ADDRESS_CONTENT;
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
			"commerce-address-display-context",
			CommerceAddressDisplayContext.class,
			"commerceAddressDisplayContext");
		templateVariableGroup.addCollectionVariable(
			"commerce-addresses", List.class,
			PortletDisplayTemplateConstants.ENTRIES, "commerce-address",
			CommerceAddress.class, "curCommerceAddress", "CommerceAddressId");

		String[] restrictedVariables = getRestrictedVariables(language);

		TemplateVariableGroup commerceAddressServicesTemplateVariableGroup =
			new TemplateVariableGroup(
				"commerce-address-services", restrictedVariables);

		commerceAddressServicesTemplateVariableGroup.setAutocompleteEnabled(
			false);

		commerceAddressServicesTemplateVariableGroup.addServiceLocatorVariables(
			CommerceAddressLocalService.class, CommerceAddressService.class);

		templateVariableGroups.put(
			commerceAddressServicesTemplateVariableGroup.getLabel(),
			commerceAddressServicesTemplateVariableGroup);

		return templateVariableGroups;
	}

	@Override
	protected String getTemplatesConfigPath() {
		return "com/liferay/commerce/address/content/web/internal/portlet" +
			"/template/dependencies/portlet-display-templates.xml";
	}

	@Reference
	private Portal _portal;

}