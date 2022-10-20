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

package com.liferay.frontend.taglib.servlet.taglib;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.aui.ButtonTag;
import com.liferay.taglib.util.IncludeTag;

/**
 * @author Eudaldo Alonso
 */
public class EditFormButtonsTag extends IncludeTag {

	public String getRedirect() {
		return _redirect;
	}

	public void setRedirect(String redirect) {
		_redirect = redirect;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_redirect = null;
	}

	@Override
	protected int processEndTag() throws Exception {
		ButtonTag cancelButtonTag = new ButtonTag();

		cancelButtonTag.setType("cancel");

		if (Validator.isNotNull(getRedirect())) {
			cancelButtonTag.setHref(getRedirect());
		}

		cancelButtonTag.doTag(pageContext);

		ButtonTag submitButtonTag = new ButtonTag();

		submitButtonTag.setType("submit");

		submitButtonTag.doTag(pageContext);

		return EVAL_PAGE;
	}

	private String _redirect;

}