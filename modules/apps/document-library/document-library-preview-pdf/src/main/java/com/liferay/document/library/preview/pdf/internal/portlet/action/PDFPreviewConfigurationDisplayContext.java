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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.util.PortalUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alicia García
 */
public class PDFPreviewConfigurationDisplayContext {

	public PDFPreviewConfigurationDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletResponse liferayPortletResponse,
		PDFPreviewManagedServiceFactory pdfPreviewManagedServiceFactory,
		String scope, long scopePK) {

		_httpServletRequest = httpServletRequest;
		_liferayPortletResponse = liferayPortletResponse;
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

	public int getMaxLimitSize() throws PortalException {
		return _pdfPreviewManagedServiceFactory.getMaxLimitOfPages(
			_scope, _scopePK);
	}

	public int getMaxNumberOfPages() throws PortalException {
		return _pdfPreviewManagedServiceFactory.getMaxNumberOfPages(
			_scope, _scopePK);
	}

	public String getSuperiorScopeLabel() {
		if (_scope.equals(
				ExtendedObjectClassDefinition.Scope.COMPANY.getValue())) {

			return LanguageUtil.get(_httpServletRequest, "system-settings");
		}
		else if (_scope.equals(
					ExtendedObjectClassDefinition.Scope.GROUP.getValue())) {

			return LanguageUtil.get(_httpServletRequest, "instance-settings");
		}

		return StringPool.BLANK;
	}

	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final PDFPreviewManagedServiceFactory
		_pdfPreviewManagedServiceFactory;
	private final String _scope;
	private final long _scopePK;

}