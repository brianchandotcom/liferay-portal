/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.osgi.web.servlet;

import java.util.List;

import javax.servlet.ServletContext;

import org.osgi.framework.Bundle;

/**
 * @author Tina Tian
 */
public interface JSPTaglibHelper {

	public void scanTLDs(
		Bundle bundle, ServletContext servletContext,
		List<String> listenerClassNames);

}