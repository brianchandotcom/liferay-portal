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

package com.liferay.portal.kernel.model;

import com.liferay.portal.kernel.util.Accessor;

import java.io.Serializable;

/**
 * @author Eduardo García
 */
public interface PortletDecorator
	extends Comparable<PortletDecorator>, Serializable {

	public static final Accessor<PortletDecorator, String> NAME_ACCESSOR =
		new Accessor<PortletDecorator, String>() {

			@Override
			public String get(PortletDecorator portletDecorator) {
				return portletDecorator.getName();
			}

			@Override
			public Class<String> getAttributeClass() {
				return String.class;
			}

			@Override
			public Class<PortletDecorator> getTypeClass() {
				return PortletDecorator.class;
			}

		};

	public String getCssClass();

	public String getName();

	public String getPortletDecoratorId();

	public String getPortletDecoratorThumbnailPath();

	public boolean isDefaultPortletDecorator();

	public void setCssClass(String cssClass);

	public void setDefaultPortletDecorator(boolean defaultPortletDecorator);

	public void setName(String name);

	public void setPortletDecoratorThumbnailPath(
		String portletDecoratorThumbnailPath);

}