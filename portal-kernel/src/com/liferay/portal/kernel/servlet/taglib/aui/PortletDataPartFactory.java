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

package com.liferay.portal.kernel.servlet.taglib.aui;

import java.util.Collection;

/**
 * @author Iván Zaera Avellón
 */
public interface PortletDataPartFactory {

	/**
	 * Create a JavaScript snippet that relies on one or more AMD required
	 * modules.
	 *
	 * The amdRequire parameter must be a comma separated list of things like:
	 *
	 * 			frontend-js-web@1.0.0/index as frontendJsWeb
	 *
	 * Or, alternatively (but discouraged):
	 *
	 *			frontend-js-web@1.0.0/index
	 *
	 * Where the left hand side of the 'as' is the name of the AMD module to
	 * import. The right hand side is the name of the variable to use to refer
	 * to the import.
	 *
	 * In the second case, where no variable is given, the variable name is
	 * automatically generated using {@link
	 * VariableUtil#generateVariable(String)}.
	 *
	 * @deprecated As of Cavanaugh (7.4.x), use {@link
	 *             #createESMPortletDataPart(String, Collection)} if possible
	 * @review
	 */
	@Deprecated
	public PortletDataPart createAMDPortletDataPart(
		String content, String amdRequire);

	/**
	 * Create a JavaScript snippet that relies on one or more AUI modules.
	 *
	 * The modules parameter must be a comma separated list like:
	 *
	 *			aui-base, querystring
	 *
	 * The obtained modules can be accessed using the A global symbol of AUI.
	 *
	 * @deprecated As of Cavanaugh (7.4.x), use {@link
	 *             #createESMPortletDataPart(String, Collection)} if possible
	 * @review
	 */
	@Deprecated
	public PortletDataPart createAUIPortletDataPart(
		String content, String modules);

	/**
	 * Create a JavaScript snippet that relies on one or more ESM imports.
	 *
	 * @review
	 */
	public PortletDataPart createESMPortletDataPart(
		String content, Collection<ESMImport> esmImports);

	/**
	 * Create a JavaScript snippet that relies both on AMD and ESM imports.
	 *
	 * This method is provided as a temporary workaround while we move all AMD
	 * code to ESM, so it may be removed in the future once the migration is
	 * complete.
	 *
	 * See {@link
	 * PortletDataPartFactory#createAMDPortletDataPart(String, String)} for an
	 * explanation on the syntax of the 'amdRequire' parameter.
	 *
	 * @deprecated As of Cavanaugh (7.4.x), use {@link
	 *             #createESMPortletDataPart(String, Collection)} if possible
	 * @review
	 */
	@Deprecated
	public PortletDataPart createMixedPortletDataPart(
		String content, String amdRequire, Collection<ESMImport> esmImports);

	/**
	 * Create a pure JavaScript snippet that doesn't rely on any external
	 * module.
	 *
	 * @review
	 */
	public PortletDataPart createSimplePortletDataPart(String content);

}