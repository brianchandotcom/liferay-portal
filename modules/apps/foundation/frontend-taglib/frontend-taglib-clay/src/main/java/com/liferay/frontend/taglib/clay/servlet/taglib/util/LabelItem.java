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

package com.liferay.frontend.taglib.clay.servlet.taglib.util;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.Validator;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * @author Carlos Lancha
 */
public class LabelItem extends HashMap<String, String> {
	public LabelItem() {
		_request = null;
	}

	public LabelItem(HttpServletRequest request) {
		_request = request;
	}


	public void setLabel(String label) {
		if (Validator.isNotNull(_request)) {
			label = LanguageUtil.get(_request, label);
		}

		put("label", label);
	}

	public void setStyle(String style) {
		put("style", style);
	}

	private final HttpServletRequest _request;

}