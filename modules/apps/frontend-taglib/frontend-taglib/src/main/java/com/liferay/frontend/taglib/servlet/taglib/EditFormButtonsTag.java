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

	public String getSubmitId() {
		return _submitId;
	}

	public String getSubmitLabel() {
		return _submitLabel;
	}

	public String getSubmitOnClick() {
		return _submitOnClick;
	}

	public boolean isSubmitDisabled() {
		return _submitDisabled;
	}

	public void setRedirect(String redirect) {
		_redirect = redirect;
	}

	public void setSubmitDisabled(boolean submitDisabled) {
		_submitDisabled = submitDisabled;
	}

	public void setSubmitId(String submitId) {
		_submitId = submitId;
	}

	public void setSubmitLabel(String submitLabel) {
		_submitLabel = submitLabel;
	}

	public void setSubmitOnClick(String submitOnClick) {
		_submitOnClick = submitOnClick;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_redirect = null;
		_submitDisabled = false;
		_submitId = null;
		_submitLabel = null;
		_submitOnClick = null;
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

		submitButtonTag.setDisabled(isSubmitDisabled());
		submitButtonTag.setId(getSubmitId());
		submitButtonTag.setOnClick(getSubmitOnClick());
		submitButtonTag.setType("submit");
		submitButtonTag.setValue(_getSubmitLabel());

		submitButtonTag.doTag(pageContext);

		return EVAL_PAGE;
	}

	private String _getSubmitLabel() {
		String submitLabel = getSubmitLabel();

		if (Validator.isNotNull(submitLabel)) {
			return submitLabel;
		}

		return "save";
	}

	private String _redirect;
	private boolean _submitDisabled;
	private String _submitId;
	private String _submitLabel;
	private String _submitOnClick;

}