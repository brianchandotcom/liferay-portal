/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.osgi.web.servlet.jsp.compiler.internal;

import java.util.Collection;

import javax.tools.JavaFileObject;

/**
 * @author Raymond Augé
 */
public interface JavaFileObjectResolver {

	public Collection<JavaFileObject> resolveClasses(
		boolean recurse, String packagePath);

}