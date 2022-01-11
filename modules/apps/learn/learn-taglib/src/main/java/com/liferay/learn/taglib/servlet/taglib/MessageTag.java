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

import com.liferay.learn.LearnMessage;
import com.liferay.learn.LearnMessageUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

/**
 * @author Brian Wing Shun Chan
 */
public class MessageTag extends IncludeTag {

	public String getKey() {
		return _key;
	}

	public String getResource() {
		return _resource;
	}

	public String getVar() {
		return _var;
	}

	@Override
	public int processEndTag() throws Exception {
		LearnMessage learnMessage = LearnMessageUtil.getLearnMessage(
			_key,
			LanguageUtil.getLanguageId(
				(HttpServletRequest)pageContext.getRequest()),
			_resource);

		String html = learnMessage.getHTML();

		if (Validator.isNotNull(html)) {
			if (Validator.isNotNull(_var)) {
				pageContext.setAttribute(_var, html);
			}
			else {
				JspWriter jspWriter = pageContext.getOut();

				jspWriter.write(html);
			}
		}

		return EVAL_PAGE;
	}

	public void setKey(String key) {
		_key = key;
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

		_key = null;
		_resource = null;
		_var = null;
	}

	private String _key;
	private String _resource;
	private String _var;

}