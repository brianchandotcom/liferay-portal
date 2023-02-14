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

package com.liferay.document.library.preview.pdf.internal.portlet.action;

import com.liferay.configuration.admin.constants.ConfigurationAdminPortletKeys;
import com.liferay.document.library.preview.pdf.exception.PDFPreviewException;
import com.liferay.document.library.preview.pdf.internal.configuration.admin.service.PDFPreviewManagedServiceFactory;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.GroupService;
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
			_updatePDFPreview(actionRequest, scope, scopePK);
		}
		catch (ConfigurationModelListenerException | PDFPreviewException
					exception) {

			SessionErrors.add(actionRequest, exception.getClass(), exception);

			actionResponse.sendRedirect(
				ParamUtil.getString(actionRequest, "redirect"));
		}
	}

	private void _updatePDFPreview(
			ActionRequest actionRequest, String scope, long scopePK)
		throws Exception {

		long maxNumberOfPages = ParamUtil.getLong(
			actionRequest, "maxNumberOfPages");

		long systemMaxNumberOfPages =
			_pdfPreviewManagedServiceFactory.getSystemMaxNumberOfPages();

		if (scope.equals(
				ExtendedObjectClassDefinition.Scope.COMPANY.getValue())) {

			if ((systemMaxNumberOfPages != 0) &&
				(systemMaxNumberOfPages < maxNumberOfPages)) {

				throw new PDFPreviewException(systemMaxNumberOfPages);
			}

			_pdfPreviewManagedServiceFactory.
				updateCompanyPDFPreviewConfiguration(scopePK, maxNumberOfPages);
		}
		else if (scope.equals(
					ExtendedObjectClassDefinition.Scope.GROUP.getValue())) {

			if ((systemMaxNumberOfPages != 0) &&
				(systemMaxNumberOfPages < maxNumberOfPages)) {

				throw new PDFPreviewException(systemMaxNumberOfPages);
			}

			Group group = _groupService.getGroup(scopePK);

			long companyMaxNumberOfPages =
				_pdfPreviewManagedServiceFactory.getCompanyMaxNumberOfPages(
					group.getCompanyId());

			if ((companyMaxNumberOfPages != 0) &&
				(companyMaxNumberOfPages < maxNumberOfPages)) {

				throw new PDFPreviewException(companyMaxNumberOfPages);
			}

			_pdfPreviewManagedServiceFactory.updateGroupPDFPreviewConfiguration(
				scopePK, maxNumberOfPages);
		}
		else if (scope.equals(
					ExtendedObjectClassDefinition.Scope.SYSTEM.getValue())) {

			_pdfPreviewManagedServiceFactory.
				updateSystemPDFPreviewConfiguration(maxNumberOfPages);
		}
		else {
			throw new PortalException("Unsupported scope: " + scope);
		}
	}

	@Reference
	private GroupService _groupService;

	@Reference
	private PDFPreviewManagedServiceFactory _pdfPreviewManagedServiceFactory;

}