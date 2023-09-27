/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.internal.portlet.decorator;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.model.PortletDecorator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Objects;

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
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof PortletDecorator)) {
			return false;
		}

		PortletDecorator portletDecorator = (PortletDecorator)object;

		if (Objects.equals(
				getPortletDecoratorId(),
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