/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package org.eclipse.equinox.http.servlet.internal.context;

import javax.servlet.ServletContext;
import java.util.Dictionary;

/**
 * @author Dante Wang
 */
public interface ServletContextHelperDataContext {

	public void createContextAttributes(
		ContextController contextController);

	public void destroy();

	public void destroyContextAttributes(
		ContextController contextController);

	public Dictionary<String, Object> getContextAttributes(
		ContextController contextController);

	public ServletContext getServletContext();

}