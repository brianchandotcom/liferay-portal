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

package com.liferay.learn.taglib.servlet.taglib;

import com.liferay.learn.taglib.internal.web.cache.JSONObjectWebCacheItem;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.jsp.JspWriter;

/**
 * @author Brian Wing Shun Chan
 */
public class JSONTag extends IncludeTag {

	public String getResource() {
		return _resource;
	}

	public String getVar() {
		return _var;
	}

	@Override
	public int processEndTag() throws Exception {
		JSONObject jsonObject = JSONObjectWebCacheItem.get(_resource);

		if (Validator.isNotNull(_var)) {
			pageContext.setAttribute(_var, jsonObject.toString());
		}
		else {
			JspWriter jspWriter = pageContext.getOut();

			jspWriter.write(jsonObject.toString());
		}

		return EVAL_PAGE;
	}

	public void setResource(String resource) {
		_resource = resource;
	}

	public void setVar(String var) {
		_var = var;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_resource = null;
		_var = null;
	}

	private String _resource;
	private String _var;

}