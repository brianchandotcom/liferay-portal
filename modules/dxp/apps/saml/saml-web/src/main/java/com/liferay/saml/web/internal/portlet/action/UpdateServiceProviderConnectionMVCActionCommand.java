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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.saml.persistence.model.SamlIdpSpConnection;
import com.liferay.saml.persistence.service.SamlIdpSpConnectionLocalService;
import com.liferay.saml.web.internal.constants.SamlAdminPortletKeys;

import java.io.InputStream;

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
		"javax.portlet.name=" + SamlAdminPortletKeys.SAML_ADMIN,
		"mvc.command.name=/admin/updateServiceProviderConnection"
	},
	service = MVCActionCommand.class
)
public class UpdateServiceProviderConnectionMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		UploadPortletRequest uploadPortletRequest =
			_portal.getUploadPortletRequest(actionRequest);

		long samlIdpSpConnectionId = ParamUtil.getLong(
			uploadPortletRequest, "samlIdpSpConnectionId");

		String samlSpEntityId = ParamUtil.getString(
			uploadPortletRequest, "samlSpEntityId");
		int assertionLifetime = ParamUtil.getInteger(
			uploadPortletRequest, "assertionLifetime");
		String attributeNames = ParamUtil.getString(
			uploadPortletRequest, "attributeNames");
		boolean attributesEnabled = ParamUtil.getBoolean(
			uploadPortletRequest, "attributesEnabled");
		boolean attributesNamespaceEnabled = ParamUtil.getBoolean(
			uploadPortletRequest, "attributesNamespaceEnabled");
		boolean enabled = ParamUtil.getBoolean(uploadPortletRequest, "enabled");
		String metadataUrl = ParamUtil.getString(
			uploadPortletRequest, "metadataUrl");
		InputStream metadataXmlInputStream =
			uploadPortletRequest.getFileAsStream("metadataXml");
		String name = ParamUtil.getString(uploadPortletRequest, "name");
		String nameIdAttribute = ParamUtil.getString(
			uploadPortletRequest, "nameIdAttribute");
		String nameIdFormat = ParamUtil.getString(
			uploadPortletRequest, "nameIdFormat");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			SamlIdpSpConnection.class.getName(), uploadPortletRequest);

		SamlIdpSpConnection samlIdpSpConnection = null;

		try {
			if (samlIdpSpConnectionId <= 0) {
				samlIdpSpConnection =
					_samlIdpSpConnectionLocalService.addSamlIdpSpConnection(
						samlSpEntityId, assertionLifetime, attributeNames,
						attributesEnabled, attributesNamespaceEnabled, enabled,
						metadataUrl, metadataXmlInputStream, name,
						nameIdAttribute, nameIdFormat, serviceContext);
			}
			else {
				samlIdpSpConnection =
					_samlIdpSpConnectionLocalService.updateSamlIdpSpConnection(
						samlIdpSpConnectionId, samlSpEntityId,
						assertionLifetime, attributeNames, attributesEnabled,
						attributesNamespaceEnabled, enabled, metadataUrl,
						metadataXmlInputStream, name, nameIdAttribute,
						nameIdFormat, serviceContext);
			}

			actionRequest.setAttribute(
				"samlIdpSpConnection", samlIdpSpConnection);

			String redirect = ParamUtil.getString(
				uploadPortletRequest, "redirect");

			if (Validator.isNotNull(redirect)) {
				redirect = _portal.escapeRedirect(redirect);

				actionRequest.setAttribute(WebKeys.REDIRECT, redirect);
			}

			sendRedirect(actionRequest, actionResponse);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}

			SessionErrors.add(actionRequest, e.getClass());

			actionResponse.setRenderParameter(
				"mvcRenderCommandName",
				"/admin/edit_service_provider_connection");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpdateServiceProviderConnectionMVCActionCommand.class);

	@Reference
	private Portal _portal;

	@Reference
	private SamlIdpSpConnectionLocalService _samlIdpSpConnectionLocalService;

}