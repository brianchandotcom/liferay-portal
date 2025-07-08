/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.web.internal.display.context;

import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ListUtil;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
 * @author Jorge González
 */
public class ImportErrorsDisplayContext {

	public ImportErrorsDisplayContext(HttpServletRequest httpServletRequest) {
		_httpServletRequest = httpServletRequest;
	}

	public String getAPIURL() {
		return "/group/__mocks__/get-import-error-list";
	}

	public List<FDSActionDropdownItem> getFDSActionDropdownItems() {
		return ListUtil.fromArray(
			new FDSActionDropdownItem(
				"/export_import/view_import_error_detail&errorId={errorId}",
				"view", "view", LanguageUtil.get(_httpServletRequest, "view"),
				"get", null, "link"));
	}

	private final HttpServletRequest _httpServletRequest;

}