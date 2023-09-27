/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.preview.pdf.internal.portlet.action;

import com.liferay.document.library.preview.pdf.internal.configuration.admin.service.helper.PDFPreviewConfigurationHelper;
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
		PDFPreviewConfigurationHelper pdfPreviewConfigurationHelper,
		String scope, long scopePK) {

		_httpServletRequest = httpServletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_pdfPreviewConfigurationHelper = pdfPreviewConfigurationHelper;
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
		return _pdfPreviewConfigurationHelper.getMaxLimitOfPages(
			_scope, _scopePK);
	}

	public int getMaxNumberOfPages() throws PortalException {
		return _pdfPreviewConfigurationHelper.getMaxNumberOfPages(
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
	private final PDFPreviewConfigurationHelper _pdfPreviewConfigurationHelper;
	private final String _scope;
	private final long _scopePK;

}