/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.portlet;

import com.liferay.portal.kernel.model.Portlet;

import javax.portlet.PortletContext;

import javax.servlet.ServletContext;

/**
 * @author Michael C. Han
 */
public interface PortletContextFactory {

	public PortletContext create(
		Portlet portlet, ServletContext servletContext);

	public PortletContext createUntrackedInstance(
		Portlet portlet, ServletContext servletContext);

	public void destroy(Portlet portlet);

}