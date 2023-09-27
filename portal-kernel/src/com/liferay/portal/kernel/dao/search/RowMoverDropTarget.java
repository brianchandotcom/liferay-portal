/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.dao.search;

import com.liferay.petra.string.StringPool;

/**
 * @author Chema Balsas
 */
public class RowMoverDropTarget {

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