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

package com.liferay.exportimport.changeset.taglib.servlet.taglib;

import com.liferay.exportimport.changeset.Changeset;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.TagSupport;

import javax.servlet.jsp.JspException;

/**
 * @author Máté Thurzó
 */
public class CreateTag extends TagSupport {

	@Override
	public int doEndTag() throws JspException {
		Changeset changeset = _rawBuilder.build();

		pageContext.setAttribute(_var, changeset);

		return EVAL_PAGE;
	}

	@Override
	public int doStartTag() throws JspException {
		if (Validator.isNull(_var)) {
			throw new JspException("Var is null");
		}

		_rawBuilder = Changeset.createRaw();

		return EVAL_BODY_INCLUDE;
	}

	public Changeset.RawBuilder getRawBuilder() {
		return _rawBuilder;
	}

	public void setVar(String var) {
		_var = var;
	}

	private Changeset.RawBuilder _rawBuilder;
	private String _var;

}