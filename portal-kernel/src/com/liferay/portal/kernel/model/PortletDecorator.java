/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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