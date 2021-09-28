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

package com.liferay.translation.web.internal.display.context;

import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alicia García
 */
public class ImportTranslationResultsDisplayContext {

	public ImportTranslationResultsDisplayContext(
		long classNameId, long classPK, long groupId,
		HttpServletRequest httpServletRequest, Map<String, String> importErrors,
		List<String> importSuccess,
		LiferayPortletResponse liferayPortletResponse, String title,
		String uploadedFileName) {

		_classNameId = classNameId;
		_classPK = classPK;
		_groupId = groupId;
		_httpServletRequest = httpServletRequest;
		_importErrors = importErrors;
		_importSuccess = importSuccess;
		_liferayPortletResponse = liferayPortletResponse;
		_title = title;
		_uploadedFileName = uploadedFileName;
	}

	private final long _classNameId;
	private final long _classPK;
	private final long _groupId;
	private final HttpServletRequest _httpServletRequest;
	private final Map<String, String> _importErrors;
	private final List<String> _importSuccess;
	private final LiferayPortletResponse _liferayPortletResponse;
	private String _redirect;
	private final String _title;
	private final String _uploadedFileName;

}