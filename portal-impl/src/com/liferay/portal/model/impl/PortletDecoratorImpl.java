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

package com.liferay.portal.model.impl;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.model.PortletDecorator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Eduardo García
 */
public class PortletDecoratorImpl implements PortletDecorator {

	public PortletDecoratorImpl() {
		this(null, null, null);
	}

	public PortletDecoratorImpl(String portletDecoratorId) {
		this(portletDecoratorId, null, null);
	}

	public PortletDecoratorImpl(
		String portletDecoratorId, String name, String cssClass) {

		_portletDecoratorId = portletDecoratorId;
		_name = name;
		_cssClass = cssClass;
	}

	@Override
	public int compareTo(PortletDecorator portletDecorator) {
		return getName().compareTo(portletDecorator.getName());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof PortletDecorator)) {
			return false;
		}

		PortletDecorator portletDecorator = (PortletDecorator)obj;

		if (getPortletDecoratorId().equals(
				portletDecorator.getPortletDecoratorId())) {

			return true;
		}

		return false;
	}

	@Override
	public String getCssClass() {
		return _cssClass;
	}

	@Override
	public String getName() {
		if (Validator.isNull(_name)) {
			return _portletDecoratorId;
		}

		return _name;
	}

	@Override
	public String getPortletDecoratorId() {
		return _portletDecoratorId;
	}

	@Override
	public String getPortletDecoratorThumbnailPath() {
		if (Validator.isNotNull(_cssClass) &&
			Validator.isNotNull(_portletDecoratorThumbnailPath)) {

			int pos = _cssClass.indexOf(CharPool.SPACE);

			if ((pos > 0) &&
				_portletDecoratorThumbnailPath.endsWith(
					_cssClass.substring(0, pos))) {

				String subclassPath = StringUtil.replace(
					_cssClass, CharPool.SPACE, CharPool.SLASH);

				return _portletDecoratorThumbnailPath +
					subclassPath.substring(pos);
			}
		}

		return _portletDecoratorThumbnailPath;
	}

	@Override
	public int hashCode() {
		return _portletDecoratorId.hashCode();
	}

	@Override
	public boolean isDefaultPortletDecorator() {
		return _defaultPortletDecorator;
	}

	@Override
	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	@Override
	public void setDefaultPortletDecorator(boolean defaultPortletDecorator) {
		_defaultPortletDecorator = defaultPortletDecorator;
	}

	@Override
	public void setName(String name) {
		_name = name;
	}

	@Override
	public void setPortletDecoratorThumbnailPath(
		String portletDecoratorThumbnailPath) {

		_portletDecoratorThumbnailPath = portletDecoratorThumbnailPath;
	}

	private String _cssClass;
	private boolean _defaultPortletDecorator;
	private String _name;
	private final String _portletDecoratorId;
	private String _portletDecoratorThumbnailPath =
		"${images-path}/portlet_decorators/${portlet-decorator-css-class}";

}