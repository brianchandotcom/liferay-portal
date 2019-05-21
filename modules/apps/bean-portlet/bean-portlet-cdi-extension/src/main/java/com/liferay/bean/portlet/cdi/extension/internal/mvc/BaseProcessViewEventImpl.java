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

package com.liferay.bean.portlet.cdi.extension.internal.mvc;

import javax.mvc.engine.ViewEngine;

/**
 * @author  Neil Griffin
 */
public class BaseProcessViewEventImpl {

	public BaseProcessViewEventImpl(
		String view, Class<? extends ViewEngine> viewEngine) {

		_view = view;
		_viewEngine = viewEngine;
	}

	public Class<? extends ViewEngine> getEngine() {
		return _viewEngine;
	}

	public String getView() {
		return _view;
	}

	private final String _view;
	private final Class<? extends ViewEngine> _viewEngine;

}