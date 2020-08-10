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

package com.liferay.commerce.frontend.clay.data.set;

/**
 * @author Marco Leo
 */
public class ClayDataSetAction {

	public ClayDataSetAction(
		String cssClasses, String href, String icon, String label,
		String onClick, boolean quickAction, boolean separator) {

		_cssClasses = cssClasses;
		_href = href;
		_icon = icon;
		_label = label;
		_onClick = onClick;
		_quickAction = quickAction;
		_separator = separator;
	}

	public String getCssClasses() {
		return _cssClasses;
	}

	public String getHref() {
		return _href;
	}

	public String getIcon() {
		return _icon;
	}

	public String getId() {
		return _id;
	}

	public String getLabel() {
		return _label;
	}

	public String getMethod() {
		return _method;
	}

	public String getOnClick() {
		return _onClick;
	}

	public int getOrder() {
		return _order;
	}

	public boolean getQuickAction() {
		return _quickAction;
	}

	public boolean getSeparator() {
		return _separator;
	}

	public String getTarget() {
		return _target;
	}

	public void setHref(String href) {
		_href = href;
	}

	public void setIcon(String icon) {
		_icon = icon;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setLabel(String label) {
		_label = label;
	}

	public void setMethod(String method) {
		_method = method;
	}

	public void setOrder(int order) {
		_order = order;
	}

	public void setQuickAction(boolean quickAction) {
		_quickAction = quickAction;
	}

	public void setSeparator(boolean separator) {
		_separator = separator;
	}

	public void setTarget(String target) {
		_target = target;
	}

	private final String _cssClasses;
	private String _href;
	private String _icon;
	private String _id;
	private String _label;
	private String _method;
	private final String _onClick;
	private int _order;
	private boolean _quickAction;
	private boolean _separator;
	private String _target;

}