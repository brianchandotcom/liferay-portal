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

package com.liferay.portal.kernel.dao.search;

import com.liferay.petra.string.StringPool;

/**
 * @author Chema Balsas
 */
public class RowMoverDropTarget {

	public RowMoverDropTarget() {
	}

	public String getAction() {
		return _action;
	}

	public String getActiveCssClass() {
		return _activeCssClass;
	}

	public String getContainer() {
		return _container;
	}

	public String getInfoCssClass() {
		return _infoCssClass;
	}

	public String getSelector() {
		return _selector;
	}

	public void setAction(String action) {
		_action = action;
	}

	public void setActiveCssClass(String activeCssClass) {
		_activeCssClass = activeCssClass;
	}

	public void setContainer(String container) {
		_container = container;
	}

	public void setInfoCssClass(String infoCssClass) {
		_infoCssClass = infoCssClass;
	}

	public void setSelector(String selector) {
		_selector = selector;
	}

	private String _action = StringPool.BLANK;
	private String _activeCssClass = StringPool.BLANK;
	private String _container = StringPool.BLANK;
	private String _infoCssClass = StringPool.BLANK;
	private String _selector = StringPool.BLANK;

}