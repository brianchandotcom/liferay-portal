/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		pageContext.setAttribute(_var, _rawBuilder.build());

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