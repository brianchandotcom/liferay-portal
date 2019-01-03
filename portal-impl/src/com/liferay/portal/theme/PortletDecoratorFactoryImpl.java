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

package com.liferay.portal.theme;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.PortletDecorator;
import com.liferay.portal.kernel.theme.PortletDecoratorFactory;
import com.liferay.portal.model.impl.PortletDecoratorImpl;
import com.liferay.portal.util.PropsValues;

/**
 * @author Eduardo García
 */
public class PortletDecoratorFactoryImpl implements PortletDecoratorFactory {

	@Override
	public PortletDecorator getDefaultPortletDecorator() {
		return new PortletDecoratorImpl(
			getDefaultPortletDecoratorId(), StringPool.BLANK,
			getDefaultPortletDecoratorCssClass());
	}

	@Override
	public String getDefaultPortletDecoratorCssClass() {
		return PropsValues.DEFAULT_PORTLET_DECORATOR_CSS_CLASS;
	}

	@Override
	public String getDefaultPortletDecoratorId() {
		return PropsValues.DEFAULT_PORTLET_DECORATOR_ID;
	}

	@Override
	public PortletDecorator getPortletDecorator() {
		return new PortletDecoratorImpl();
	}

	@Override
	public PortletDecorator getPortletDecorator(String portletDecoratorId) {
		return new PortletDecoratorImpl(portletDecoratorId);
	}

	@Override
	public PortletDecorator getPortletDecorator(
		String portletDecoratorId, String name, String cssClass) {

		return new PortletDecoratorImpl(portletDecoratorId, name, cssClass);
	}

}