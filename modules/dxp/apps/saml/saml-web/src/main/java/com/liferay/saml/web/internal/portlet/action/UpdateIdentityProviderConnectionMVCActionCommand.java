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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.upload.FileItem;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.saml.constants.SamlPortletKeys;
import com.liferay.saml.opensaml.integration.field.expression.handler.registry.SamlSpIdpConnectionFieldExpressionHandlerRegistry;
import com.liferay.saml.opensaml.integration.processor.SamlSpIdpConnectionProcessor;
import com.liferay.saml.opensaml.integration.processor.factory.SamlSpIdpConnectionProcessorFactory;
import com.liferay.saml.persistence.model.SamlSpIdpConnection;
import com.liferay.saml.persistence.service.SamlSpIdpConnectionLocalService;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + SamlPortletKeys.SAML_ADMIN,
		"mvc.command.name=/admin/update_identity_provider_connection"
	},
	service = MVCActionCommand.class
)
public class UpdateIdentityProviderConnectionMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		UploadPortletRequest uploadPortletRequest =
			_portal.getUploadPortletRequest(actionRequest);

		long samlSpIdpConnectionId = ParamUtil.getLong(
			uploadPortletRequest, "samlSpIdpConnectionId");

		SamlSpIdpConnection samlSpIdpConnection;

		if (samlSpIdpConnectionId <= 0) {
			samlSpIdpConnection =
				_samlSpIdpConnectionLocalService.createSamlSpIdpConnection(0);
		}
		else {
			samlSpIdpConnection =
				_samlSpIdpConnectionLocalService.fetchSamlSpIdpConnection(
					samlSpIdpConnectionId);
		}

		SamlSpIdpConnectionProcessor samlSpIdpConnectionProcessor =
			_samlSpIdpConnectionProcessorFactory.create(
				samlSpIdpConnection,
				_samlSpIdpConnectionFieldExpressionHandlerRegistry);

		Map<String, List<String>> regularParameterMap =
			uploadPortletRequest.getRegularParameterMap();

		for (Map.Entry<String, List<String>> entry :
				regularParameterMap.entrySet()) {

			List<String> value = entry.getValue();

			samlSpIdpConnectionProcessor.setValueArray(
				entry.getKey(), value.toArray(new String[0]));
		}

		final String[] defaultValue = {StringPool.BLANK};

		for (String booleanField : _booleanFields) {
			if (!regularParameterMap.containsKey(booleanField)) {
				samlSpIdpConnectionProcessor.setValueArray(
					booleanField, defaultValue);
			}
		}

		Map<String, FileItem[]> multipartParameterMap =
			uploadPortletRequest.getMultipartParameterMap();

		multipartParameterMap.forEach(
			samlSpIdpConnectionProcessor::setFileItemArray);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			SamlSpIdpConnection.class.getName(), actionRequest);

		samlSpIdpConnectionProcessor.process(serviceContext);
	}

	private final List<String> _booleanFields = Arrays.asList(
		"assertionSignatureRequired", "enabled", "forceAuthn",
		"ldapImportEnabled", "unknownUsersAreStrangers", "signAuthnRequest");

	@Reference
	private Portal _portal;

	@Reference
	private SamlSpIdpConnectionFieldExpressionHandlerRegistry
		_samlSpIdpConnectionFieldExpressionHandlerRegistry;

	@Reference
	private SamlSpIdpConnectionLocalService _samlSpIdpConnectionLocalService;

	@Reference
	private SamlSpIdpConnectionProcessorFactory
		_samlSpIdpConnectionProcessorFactory;

}