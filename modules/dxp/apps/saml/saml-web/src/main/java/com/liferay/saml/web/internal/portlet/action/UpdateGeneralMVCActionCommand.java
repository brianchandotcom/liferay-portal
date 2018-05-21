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

package com.liferay.saml.web.internal.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropertiesParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.saml.runtime.configuration.SamlProviderConfigurationHelper;
import com.liferay.saml.runtime.credential.KeyStoreManager;
import com.liferay.saml.runtime.metadata.LocalEntityManager;
import com.liferay.saml.util.PortletPropsKeys;
import com.liferay.saml.web.internal.constants.SamlAdminPortletKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	configurationPid = "com.liferay.saml.runtime.configuration.SamlKeyStoreManagerConfiguration",
	immediate = true,
	property = {
		"javax.portlet.name=" + SamlAdminPortletKeys.SAML_ADMIN,
		"mvc.command.name=/admin/updateGeneral"
	},
	service = MVCActionCommand.class
)
public class UpdateGeneralMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		UnicodeProperties properties = PropertiesParamUtil.getProperties(
			actionRequest, "settings--");

		boolean enabled = GetterUtil.getBoolean(
			properties.getProperty(PortletPropsKeys.SAML_ENABLED),
			_samlProviderConfigurationHelper.isEnabled());
		String samlEntityId = properties.getProperty(
			PortletPropsKeys.SAML_ENTITY_ID);

		if (enabled &&
			!StringUtil.equalsIgnoreCase(
				_localEntityManager.getLocalEntityId(), samlEntityId)) {

			SessionErrors.add(actionRequest, "entityIdInUse");

			return;
		}

		if (Validator.isNotNull(samlEntityId) &&
			(samlEntityId.length() > 1024)) {

			SessionErrors.add(actionRequest, "entityIdTooLong");

			return;
		}

		if (enabled &&
			(_localEntityManager.getLocalEntityCertificate() == null)) {

			SessionErrors.add(actionRequest, "certificateInvalid");

			return;
		}

		String samlRole = properties.getProperty(
			PortletPropsKeys.SAML_ROLE, StringPool.BLANK);

		if (enabled && samlRole.equals("sp") &&
			!_localEntityManager.hasDefaultIdpRole()) {

			SessionErrors.add(actionRequest, "identityProviderInvalid");

			return;
		}

		_samlProviderConfigurationHelper.updateProperties(properties);

		actionResponse.setRenderParameter("mvcRenderCommandName", "/admin");
		actionResponse.setRenderParameter("tabs1", "general");
	}

	@Reference(name = "KeyStoreManager", target = "(default=true)")
	private KeyStoreManager _keyStoreManager;

	@Reference
	private LocalEntityManager _localEntityManager;

	@Reference
	private SamlProviderConfigurationHelper _samlProviderConfigurationHelper;

}