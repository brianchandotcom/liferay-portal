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

package com.liferay.asset.display.template.web.internal.display.context;

import com.liferay.dynamic.data.mapping.util.DDMDisplayRegistry;
import com.liferay.dynamic.data.mapping.util.DDMTemplateHelper;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Pavel Savinov
 */
public class AssetDisplayTemplateDisplayContext {

	public AssetDisplayTemplateDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse,
		HttpServletRequest request, DDMDisplayRegistry ddmDisplayRegistry,
		DDMTemplateHelper ddmTemplateHelper) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_request = request;

		_ddmDisplayRegistry = ddmDisplayRegistry;
		_ddmTemplateHelper = ddmTemplateHelper;
	}

	public String getKeywords() {
		if (Validator.isNotNull(_keywords)) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(_request, "keywords", null);

		return _keywords;
	}

	public boolean isShowSearch() {
		if (Validator.isNotNull(getKeywords())) {
			return true;
		}

		return false;
	}

	private final DDMDisplayRegistry _ddmDisplayRegistry;
	private final DDMTemplateHelper _ddmTemplateHelper;
	private String _keywords;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final HttpServletRequest _request;

}