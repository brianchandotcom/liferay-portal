/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.preview.pdf.internal.portlet.action;

import com.liferay.configuration.admin.constants.ConfigurationAdminPortletKeys;
import com.liferay.document.library.preview.pdf.exception.PDFPreviewException;
import com.liferay.document.library.preview.pdf.internal.configuration.admin.service.helper.PDFPreviewConfigurationHelper;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alicia García
 */
@Component(
	property = {
		"javax.portlet.name=" + ConfigurationAdminPortletKeys.INSTANCE_SETTINGS,
		"javax.portlet.name=" + ConfigurationAdminPortletKeys.SITE_SETTINGS,
		"javax.portlet.name=" + ConfigurationAdminPortletKeys.SYSTEM_SETTINGS,
		"mvc.command.name=/instance_settings/edit_pdf_preview"
	},
	service = MVCActionCommand.class
)
public class EditPDFPreviewMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String scope = ParamUtil.getString(actionRequest, "scope");

		if (Validator.isNull(scope)) {
			throw new PortalException("Unsupported scope: " + scope);
		}

		long scopePK = ParamUtil.getLong(actionRequest, "scopePK");

		if ((scopePK == 0) &&
			!scope.equals(
				ExtendedObjectClassDefinition.Scope.SYSTEM.getValue())) {

			throw new PortalException(
				"Invalid scope primary key 0 for " + scope + " scope");
		}

		try {
			int maxNumberOfPages = ParamUtil.getInteger(
				actionRequest, "maxNumberOfPages");

			if (maxNumberOfPages < 0) {
				throw new PDFPreviewException(
					"Maximum number of pages limit must be greater than or " +
						"equal to 0 ");
			}

			_pdfPreviewConfigurationHelper.updatePDFPreview(
				maxNumberOfPages, scope, scopePK);
		}
		catch (ConfigurationModelListenerException | PDFPreviewException
					exception) {

			SessionErrors.add(actionRequest, exception.getClass(), exception);

			actionResponse.sendRedirect(
				ParamUtil.getString(actionRequest, "redirect"));
		}
	}

	@Reference
	private PDFPreviewConfigurationHelper _pdfPreviewConfigurationHelper;

}