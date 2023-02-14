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

import com.liferay.document.library.preview.pdf.internal.configuration.admin.service.PDFPreviewManagedServiceFactory;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.util.PortalUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alicia García
 */
public class PDFPreviewConfigurationDisplayContext {

	public PDFPreviewConfigurationDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletResponse liferayPortletResponse,
		GroupService groupService,
		PDFPreviewManagedServiceFactory pdfPreviewManagedServiceFactory,
		String scope, long scopePK) {

		_httpServletRequest = httpServletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_groupService = groupService;
		_pdfPreviewManagedServiceFactory = pdfPreviewManagedServiceFactory;
		_scope = scope;
		_scopePK = scopePK;
	}

	public String getEditPDFPreviewConfigurationURL() {
		return PortletURLBuilder.createActionURL(
			_liferayPortletResponse
		).setActionName(
			"/instance_settings/edit_pdf_preview"
		).setRedirect(
			PortalUtil.getCurrentURL(_httpServletRequest)
		).setParameter(
			"scope", _scope
		).setParameter(
			"scopePK", _scopePK
		).buildString();
	}

	public long getMaxNumberOfPages() throws PortalException {
		if (_scope.equals(
				ExtendedObjectClassDefinition.Scope.COMPANY.getValue())) {

			long companyMaxNumberOfPages =
				_pdfPreviewManagedServiceFactory.getCompanyMaxNumberOfPages(
					_scopePK);

			long systemMaxNumberOfPages =
				_pdfPreviewManagedServiceFactory.getSystemMaxNumberOfPages();

			if ((companyMaxNumberOfPages != 0) &&
				(companyMaxNumberOfPages < systemMaxNumberOfPages)) {

				return companyMaxNumberOfPages;
			}

			return systemMaxNumberOfPages;
		}
		else if (_scope.equals(
					ExtendedObjectClassDefinition.Scope.GROUP.getValue())) {

			long groupMaxNumberOfPages =
				_pdfPreviewManagedServiceFactory.getGroupMaxNumberOfPages(
					_scopePK);

			Group group = _groupService.getGroup(_scopePK);

			long companyMaxNumberOfPages =
				_pdfPreviewManagedServiceFactory.getCompanyMaxNumberOfPages(
					group.getCompanyId());

			long systemMaxNumberOfPages =
				_pdfPreviewManagedServiceFactory.getSystemMaxNumberOfPages();

			if ((groupMaxNumberOfPages != 0) &&
				(groupMaxNumberOfPages < systemMaxNumberOfPages) &&
				(groupMaxNumberOfPages < companyMaxNumberOfPages)) {

				return groupMaxNumberOfPages;
			}

			if ((companyMaxNumberOfPages != 0) &&
				(companyMaxNumberOfPages < systemMaxNumberOfPages)) {

				return companyMaxNumberOfPages;
			}

			return systemMaxNumberOfPages;
		}
		else if (_scope.equals(
					ExtendedObjectClassDefinition.Scope.SYSTEM.getValue())) {

			return _pdfPreviewManagedServiceFactory.getSystemMaxNumberOfPages();
		}

		throw new IllegalArgumentException("Unsupported scope: " + _scope);
	}

	private final GroupService _groupService;
	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final PDFPreviewManagedServiceFactory
		_pdfPreviewManagedServiceFactory;
	private final String _scope;
	private final long _scopePK;

}